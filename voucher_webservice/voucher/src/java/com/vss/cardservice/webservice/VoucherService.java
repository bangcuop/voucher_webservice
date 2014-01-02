/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.webservice;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author thang.tranquyet
 */
//@WebService(serviceName = "VoucherService", targetNamespace = "http://vcard.vn/voucherservice")
@WebService(serviceName = "VoucherService", targetNamespace = "http://paydirect.vn/voucherservice")
public class VoucherService extends SpringBeanAutowiringSupport {

    @Resource
    WebServiceContext wsc;

    @WebMethod(operationName = "useCard")
    public String useCard(
            @WebParam(name = "issuer") String issuer,
            @WebParam(name = "cardSerial") String cardSerial,
            @WebParam(name = "cardCode") String cardCode,
            @WebParam(name = "transRef") String transRef,
            @WebParam(name = "partnerCode") String partnerCode,
            @WebParam(name = "password") String password,
            @WebParam(name = "signature") String signature) {
        return new VoucherServiceUtil().useCard(issuer, cardSerial, cardCode, transRef, partnerCode, password, signature, (HttpServletRequest) wsc.getMessageContext().get("javax.xml.ws.servlet.request"));
    }

    @WebMethod(operationName = "getTransactionDetail")
    public String getTransactionDetail(
            @WebParam(name = "transRef") String transRef,
            @WebParam(name = "partnerCode") String partnerCode,
            @WebParam(name = "password") String password,
            @WebParam(name = "signature") String signature) {
        return new VoucherServiceUtil().getTransactionDetail(transRef, partnerCode, password, signature, (HttpServletRequest) wsc.getMessageContext().get("javax.xml.ws.servlet.request"));
    }
}
