package com.vss.cardservice.webservice;

import com.vss.cardservice.dto.PartnerInfo;
import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * POST FORM 
 * useCard
 * issuer
 * cardCode
 * cardserial
 * partnerCode
 * password
 * signature
 * transRef
 * getTransactionDetail
 * partnerCode
 * password
 * signature
 * transRef
 * 
 * @author zannami
 */
//@WebService(serviceName = "cardService", targetNamespace = "http://vcard.vn/cardservice")
//@HandlerChain(file = "handlers.xml")
public class cardService extends SpringBeanAutowiringSupport {

    @Resource
    WebServiceContext wsc;
    @Autowired
    private VoucherService voucherService;

//    public cardService() {
//        if (voucher == null) {
//            voucher = new VoucherService();
//        }
//    }
    @WebMethod(operationName = "useCard")
    public String useCard(@WebParam(name = "issuer") String issuer, @WebParam(name = "partnerInfo") PartnerInfo partnerInfo,
            @WebParam(name = "cardCode") String cardCode, @WebParam(name = "transRef") String transRef) {
        HttpServletRequest req = (HttpServletRequest) this.wsc.getMessageContext().get("javax.xml.ws.servlet.request");
        voucherService.setReq(req);
        return voucherService.useCard(issuer, null, cardCode, transRef, partnerInfo.getPartnerCode(),
                partnerInfo.getPassword(), partnerInfo.getSignature());
    }

    @WebMethod(operationName = "checkCard")
    public String checkCard(@WebParam(name = "issuer") String issuer, @WebParam(name = "partnerInfo") PartnerInfo partnerInfo, @WebParam(name = "cardCode") String cardCode, @WebParam(name = "transRef") String transRef) {
        HttpServletRequest req = (HttpServletRequest) this.wsc.getMessageContext().get("javax.xml.ws.servlet.request");
        voucherService.setReq(req);
        return voucherService.checkCard(issuer, partnerInfo, cardCode, transRef);
    }

    @WebMethod(operationName = "getTransactionDetail")
    public String getTransactionDetail(@WebParam(name = "partnerInfo") PartnerInfo partnerInfo, @WebParam(name = "transRef") String transRef) {
        HttpServletRequest req = (HttpServletRequest) this.wsc.getMessageContext().get("javax.xml.ws.servlet.request");
        voucherService.setReq(req);
        return voucherService.getTransactionDetail(transRef, partnerInfo.getPartnerCode(),
                partnerInfo.getPassword(), partnerInfo.getSignature());
    }
}
