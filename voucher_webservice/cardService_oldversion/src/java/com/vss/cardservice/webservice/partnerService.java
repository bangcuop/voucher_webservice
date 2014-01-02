/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.webservice;

import com.vss.cardservice.api.IPartnerService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author zannami
 */
//@WebService(serviceName = "partnerService", targetNamespace = "http://vcard.vn/cardservice")
public class partnerService extends SpringBeanAutowiringSupport {

    @Autowired
    private IPartnerService partnerService;

//    @WebMethod(operationName = "updatePartner")
    public String updatePartner(
            @WebParam(name = "partnerCode") String partnerCode,
            @WebParam(name = "validips") String validips,
            @WebParam(name = "method") String method) {
//        if (method != null && method.equalsIgnoreCase("update")) {
//            partnerService.updatePartner(partnerCode, validips);
//        }
//        return partnerService.getPartner(partnerCode).getValidIps();
        return "";
    }
}
