/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.webservice;

import com.vss.cardservice.api.IIssuerService;
import com.vss.cardservice.api.IPartnerService;
import com.vss.cardservice.api.ITransactionService;
import com.vss.cardservice.dto.CardResponse;
import com.vss.cardservice.dto.Partner;
import com.vss.cardservice.dto.PartnerInfo;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.exception.ConnectionTimeoutException;
import com.vss.cardservice.service.exception.DuplicatedRequestRefException;
import com.vss.cardservice.service.exception.ErrorUtil;
import com.vss.cardservice.service.exception.InvalidRequestRefException;
import com.vss.cardservice.service.util.ServiceUtil;
import com.vss.cardservice.service.util.ThreadManager;
import com.vss.cardservice.service.util.ValidateUtils;
import com.vss.cardservice.service.util.WebParameter;
import com.vss.message.util.LoggingUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import javax.jws.WebService;

/**
 *
 * @author hoangha2503
 * @modified Huy Pham
 */
    @WebService(serviceName = "VoucherService", targetNamespace = "http://vcard.vn/voucherservice")
//@WebService(serviceName = "VoucherService", targetNamespace = "http://pasolutions.vn/voucherservice")
//@WebService(serviceName = "VoucherService", targetNamespace = "http://ahha.vn/voucherservice")
//@HandlerChain(file = "handlers.xml")
public class VoucherService extends SpringBeanAutowiringSupport {

    @Autowired
    private IPartnerService partnerService;
    @Autowired
    private IIssuerService issuerService;
    @Autowired
    private ITransactionService transactionService;
    ServiceUtil serviceUtil = new ServiceUtil();
    @Resource
    WebServiceContext wsc;
    private HttpServletRequest request;

    public void setIssuerService(IIssuerService issuerService) {
        this.issuerService = issuerService;
    }

    public void setPartnerService(IPartnerService partnerService) {
        this.partnerService = partnerService;
    }

    public void setTransactionService(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void setReq(HttpServletRequest req) {
        request = req;
    }
    private static Map<String, CardResponse> cardResponse = new HashMap<String, CardResponse>();
    private SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @WebMethod(operationName = "useCard")
    public String useCard(
            @WebParam(name = "issuer") String issuer,
            @WebParam(name = "cardSerial") String cardSerial,
            @WebParam(name = "cardCode") String cardCode,
            @WebParam(name = "transRef") String transRef,
            @WebParam(name = "partnerCode") String partnerCode,
            @WebParam(name = "password") String password,
            @WebParam(name = "signature") String signature) {
        Transaction tran = null;
        String response = null;
        HttpServletRequest req = null;
        boolean update = false;
        PartnerInfo partnerInfo = new PartnerInfo();
        partnerInfo.setPartnerCode(partnerCode);
        partnerInfo.setPassword(password);
        partnerInfo.setSignature(signature);

        StringBuilder prefix = new StringBuilder("[");
        prefix.append(ValidateUtils.getCurrentDate());
        prefix.append("][VoucherService][useCard][issuer:");
        prefix.append(issuer);
        prefix.append("][cardSerial:");
        prefix.append(cardSerial);
        prefix.append("][cardCode:");
        prefix.append(cardCode);
        prefix.append("][transRef:");
        prefix.append(transRef);
        prefix.append("][partnerCode:");
        prefix.append(partnerCode);
        prefix.append("][password:");
        prefix.append(password);
        prefix.append("]");

        StringBuilder prefix_ = new StringBuilder(prefix.toString());
        try {
            if (wsc == null) {
                req = request;
            } else {
                req = (HttpServletRequest) this.wsc.getMessageContext().get("javax.xml.ws.servlet.request");
            }
            String ip = req.getRemoteAddr();
            prefix_.append("[ip:");
            prefix_.append(ip);
            prefix_.append("]");
            LoggingUtil.log(prefix_.toString(), "useCard_transaction");

            ServiceUtil.getIssuerCollection(this.issuerService);

            ValidateUtils.validateInput(issuer, cardCode, partnerInfo, cardSerial);
//            LoggingUtil.log(prefix_.toString(), "useCard_transaction");

            String key = partnerInfo.getPartnerCode() + cardCode + transRef;
            if (cardResponse.containsKey(key)) {
                response = cardResponse.get(key).getResponse();
                cardResponse.remove(key);
                return response;
            }

            if ((transRef == null) || (transRef.trim().isEmpty()) || (transRef.trim().length() > 30)) {
                throw new InvalidRequestRefException();
            }

            Partner validPartner = ValidateUtils.validateSignature(partnerService, partnerInfo, ip, issuer, cardCode, transRef);

            if (!ServiceUtil.partnerCollection.containsKey(validPartner.getPartnerCode())) {
                ServiceUtil.partnerCollection.put(validPartner.getPartnerCode(), validPartner);
            }

            //looking this card in the database before saving this request
            transactionService.checkTransaction(cardCode, cardSerial, issuer.toUpperCase());

            tran = ValidateUtils.createTransaction(transactionService, tran, validPartner.getPartnerID(), partnerInfo.getPartnerCode(), cardCode, cardSerial, issuer, ip, transRef);

            if ((tran.getTransactionId() == null) || (tran.getTransactionId().equals("-1"))) {
                throw new DuplicatedRequestRefException();
            }

            long start = System.currentTimeMillis();
            long timeout = 0;
            ThreadManager.execute(tran);
            String transKey = partnerInfo.getPartnerCode() + tran.getIssuer() + tran.getCardCode() + tran.getTransRefId();
            while (ServiceUtil.transactionQueue.get(transKey) == null) {
                timeout = System.currentTimeMillis() - start;
                if (timeout >= WebParameter.session_timeout) {
                    throw new ConnectionTimeoutException();
                }
            }
            tran = ServiceUtil.transactionQueue.get(transKey);
            ServiceUtil.transactionQueue.remove(transKey);

            if (ServiceUtil.PPCARD_GATEWAY_IP.indexOf(ip) == -1) {
                response = tran.getResponseToPartner(false);
            } else {
                response = ValidateUtils.responseToVietpay(tran);
            }
            this.transactionService.updateTransaction(tran);
            response = (response == null) || (response.isEmpty()) ? WebParameter.LOI_GOI_HAM_PROVIDER : response;
        } catch (Exception ex) {
            response = ErrorUtil.captureResponseFromException(ex);
            System.out.println("------------------------>>>>" + prefix + "[response]:" + response);
            update = true;
        }
        if (update && tran != null && response != null) {
            try {
                tran.setResponseStatus(response);
                tran.setResponseToPartner(response);
                this.transactionService.updateTransaction(tran);
            } catch (Exception e) {
                response = ErrorUtil.captureResponseFromException(e);
                System.out.println("------------------------>>>>" + prefix + "[response]:" + response);
            }
        }

        if (response == null || response.isEmpty()) {
            response = WebParameter.LOI_KHONG_XAC_DINH;
        }

        if (response.indexOf(WebParameter.LOI_GOI_HAM_PROVIDER) > -1 ||
                response.equals(WebParameter.LOI_KET_NOI_SERVER) ||
                response.equals(WebParameter.LOI_KHONG_XAC_DINH) ||
                response.equals(WebParameter.HE_THONG_DANG_BAN)) {
            serviceUtil.logTransaction(tran, null);
        }

        prefix_ = new StringBuilder(prefix.toString());
        prefix_.append("[Ket qua gach the response:");
        prefix_.append(response);
        prefix_.append("]");
        LoggingUtil.log(prefix_.toString(), "useCard_transaction");
        if (response.startsWith(WebParameter.THE_KHONG_TON_TAI)) {
            ServiceUtil.checkLockPartner(partnerInfo);
        } else if (response.startsWith(WebParameter.GIAO_DICH_THANH_CONG)) {
            ServiceUtil.partnerCollection.remove(partnerInfo.getPartnerCode());
        }
        return response;
    }

    public String checkCard(String issuer, PartnerInfo partnerInfo, String cardCode, String transRef) {
        ValidateUtils.validateInput(issuer, cardCode, partnerInfo, null);
        LoggingUtil.log(fm.format(new Date()) + " " + partnerInfo.getPartnerCode() + " invoke cardservice with issuer=" + issuer +
                ";cardCode=" + cardCode + ";transRef=" + transRef + " forward to useCard", "checkCard_transaction");
        try {
            String key = partnerInfo.getPartnerCode() + cardCode + transRef;
            CardResponse dto = new CardResponse();
            dto.setPartnerCode(partnerInfo.getPartnerCode());
            dto.setTransRefId(transRef);
            dto.setResponse(useCard(issuer,
                    null, cardCode, transRef, partnerInfo.getPartnerCode(),
                    partnerInfo.getPassword(), partnerInfo.getSignature()));
            cardResponse.put(key, dto);
            return cardResponse.get(key).getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorUtil.captureResponseFromException(e);
        }
    }

    @WebMethod(operationName = "getTransactionDetail")
    public String getTransactionDetail(
            @WebParam(name = "transRef") String transRef,
            @WebParam(name = "partnerCode") String partnerCode,
            @WebParam(name = "password") String password,
            @WebParam(name = "signature") String signature) {
        PartnerInfo partnerInfo = new PartnerInfo();
        partnerInfo.setPartnerCode(partnerCode);
        partnerInfo.setPassword(password);
        partnerInfo.setSignature(signature);
        try {
            HttpServletRequest req = null;
            if (wsc == null) {
                req = request;
            } else {
                req = (HttpServletRequest) this.wsc.getMessageContext().get("javax.xml.ws.servlet.request");
            }
            String ip = req.getRemoteAddr();
            Partner p = ValidateUtils.validateSignature(partnerService, partnerInfo, ip, null, null, transRef);
            Transaction tran = this.transactionService.getTransactionDetail(p.getPartnerID(), transRef);
            if (tran == null) {
                throw new InvalidRequestRefException();
            }
            return tran.getResponseToPartner(true);
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorUtil.captureResponseFromException(e);
        }
    }
    
}
