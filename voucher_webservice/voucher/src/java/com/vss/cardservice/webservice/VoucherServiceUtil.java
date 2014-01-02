/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.webservice;

import com.vss.cardservice.dto.Issuer;
import com.vss.cardservice.dto.Partner;
import com.vss.cardservice.dto.PartnerInfo;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.exception.*;
import com.vss.cardservice.service.util.*;
import com.vss.message.util.LoggingUtil;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author thang.tranquyet
 */
public class VoucherServiceUtil {

    public String useCard(String issuerCode, String cardSerial, String cardCode, String transRef, String partnerCode, String password, String signature, HttpServletRequest req) {
        Transaction tran = null;
        String response;
        PartnerInfo partnerInfo = new PartnerInfo();
        partnerInfo.setPartnerCode(partnerCode);
        partnerInfo.setPassword(password);
        partnerInfo.setSignature(signature);

        StringBuilder prefix = new StringBuilder("[");
        prefix.append(ServiceUtil.getCurrentDate());
        prefix.append("][partnerCode:");
        prefix.append(partnerCode);
        prefix.append("][transRef:");
        prefix.append(transRef);
        prefix.append("][issuer:");
        prefix.append(issuerCode);
        prefix.append("][cardCode:");
        prefix.append(cardCode);
        prefix.append("][cardSerial:");
        prefix.append(cardSerial);
        prefix.append("][password:");
        prefix.append(password);
        Partner provider = null;
        try {
//            String ip = req.getRemoteAddr();
            String ip = req.getHeader("clientip_pay");
            if (ip == null) {
                ip = req.getRemoteAddr();
            }

            prefix.append("[ip:");
            prefix.append(ip);
            prefix.append("]");

            ValidateUtils.validateInput(issuerCode, cardCode, cardSerial, transRef);

            Partner partner = PartnerServiceUtil.validateSignature(partnerInfo, ip, issuerCode, cardCode, transRef);

            issuerCode = issuerCode.toUpperCase();

            //looking this card in the database before saving this request
            tran = TransactionServiceUtil.createTransaction(partner.getPartnerId(), partnerInfo.getPartnerCode(), cardCode, cardSerial, issuerCode, ip, transRef);

            long start = System.currentTimeMillis();

            provider = PartnerServiceUtil.getProcessPartner(ServiceUtil.issuerMap.get(issuerCode));
            prefix.append("[provider:");
            prefix.append(provider == null ? "null" : provider.getPartnerCode());
            prefix.append("]");
            if (provider == null) {
                throw new IssuerConnectionException();
            }
            tran.setProviderId(provider.getPartnerId());
            ThreadManager.execute(tran, provider);
            String transKey = partnerInfo.getPartnerCode() + tran.getIssuer() + tran.getCardCode() + tran.getTransRefId();
            while (ServiceUtil.transactionQueue.get(transKey) == null) {
                Thread.sleep(100);
                if (System.currentTimeMillis() - start >= ServiceUtil.session_timeout) {
                    throw new ConnectionTimeoutException();
                }
            }
            tran = ServiceUtil.transactionQueue.get(transKey);
            ServiceUtil.transactionQueue.remove(transKey);

            response = tran.getResponseToPartner();
            response = (response == null) || (response.isEmpty()) ? WebParameter.LOI_GOI_HAM_PROVIDER : response;
        } catch (Exception ex) {
            prefix.append("[Exception:");
            prefix.append(ex.toString().replaceAll("com.vss.cardservice.service.exception.", ""));
            if (ex.getMessage() != null) {
                prefix.append(" : ");
                prefix.append(ex.getMessage());
            }
            prefix.append("]");
            try {
                if (tran != null) {
                    tran = ErrorUtil.processException(tran, ex);
                    TransactionServiceUtil.updateTransaction(tran);
                    response = tran.getResponseToPartner();
                } else {
                    response = ErrorUtil.captureResponseFromException(ex);
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = ErrorUtil.captureResponseFromException(e);
            }
//            System.out.println("------------------------>>>>" + prefix + "[response]:" + response);
        }

        prefix.append("[Ket qua gach the response:");
        prefix.append(response);
        prefix.append("]");
        
        if (response.startsWith(WebParameter.LOI_GOI_HAM_PROVIDER)
                || response.startsWith(WebParameter.LOI_KET_NOI_PROVIDER)
                || response.startsWith(WebParameter.LOI_KHONG_XAC_DINH)
                || response.startsWith(WebParameter.CONNECTION_TIMEOUT)) {
            MailServiceUtil.mailAlert(ServiceUtil.mailSubject, prefix.toString());
            if (provider != null) {
                PartnerServiceUtil.checkLockIssuerPartner(new Issuer(ServiceUtil.issuerMap.get(issuerCode), issuerCode), provider);
            }
        }
        if (response.startsWith(WebParameter.THE_KHONG_TON_TAI)) {
            ServiceUtil.checkLockPartner(partnerInfo);
        } else if (response.startsWith(WebParameter.GIAO_DICH_THANH_CONG)) {
            ServiceUtil.partnerCollection.get(partnerInfo.getPartnerCode()).setFailedCount(0);
        }
        LoggingUtil.log(prefix.toString(), "useCard_transaction");
        return response;
    }

//    public String checkCard(String issuer, PartnerInfo partnerInfo, String cardCode, String transRef) {
//        ValidateUtils.validateInput(issuer, cardCode, partnerInfo, null);
//        LoggingUtil.log(fm.format(new Date()) + " " + partnerInfo.getPartnerCode() + " invoke cardservice with issuer=" + issuer
//                + ";cardCode=" + cardCode + ";transRef=" + transRef + " forward to useCard", "checkCard_transaction");
//        try {
//            String key = partnerInfo.getPartnerCode() + cardCode + transRef;
//            CardResponse dto = new CardResponse();
//            dto.setPartnerCode(partnerInfo.getPartnerCode());
//            dto.setTransRefId(transRef);
//            dto.setResponse(useCard(issuer,
//                    null, cardCode, transRef, partnerInfo.getPartnerCode(),
//                    partnerInfo.getPassword(), partnerInfo.getSignature()));
//            cardResponse.put(key, dto);
//            return cardResponse.get(key).getResponse();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ErrorUtil.captureResponseFromException(e);
//        }
//    }
    public String getTransactionDetail(String transRef, String partnerCode, String password, String signature, HttpServletRequest req) {
        StringBuilder prefix = new StringBuilder("[");
        prefix.append(ServiceUtil.getCurrentDate());
        prefix.append("][GetTransactionDetail][partnerCode:");
        prefix.append(partnerCode);
        prefix.append("][transRef:");
        prefix.append(transRef);
        prefix.append("][password:");
        prefix.append(password);

        PartnerInfo partnerInfo = new PartnerInfo();
        partnerInfo.setPartnerCode(partnerCode);
        partnerInfo.setPassword(password);
        partnerInfo.setSignature(signature);
        String result;
        try {
            String ip = req.getHeader("clientip_pay");
            if (ip == null) {
                ip = req.getRemoteAddr();
            }
            Partner p = PartnerServiceUtil.validateSignature(partnerInfo, ip, null, null, transRef);
            Transaction tran = TransactionServiceUtil.getTransactionDetail(p.getPartnerId(), transRef);
            if (tran == null) {
                throw new TransRefExistException();
            }
//            return tran.getResponseToPartner(true);
            result = tran.getResponseToPartner();
        } catch (Exception e) {
            result = ErrorUtil.captureResponseFromException(e);
        }
        prefix.append("][Ket qua check : ");
        prefix.append(result);
        LoggingUtil.log(prefix.toString(), "useCard_transaction");
        return result;
    }
}
