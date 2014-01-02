/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.webservice;

import com.vss.cardservice.api.IAlertService;
import com.vss.cardservice.service.util.MailServiceUtil;
import com.vss.cardservice.service.util.ServiceUtil;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author koziwa
 */
@WebService(serviceName = "AlertService", targetNamespace = "http://vcard.vn/cardservice")
public class AlertService extends SpringBeanAutowiringSupport {

    IAlertService alertService;

    @WebMethod(operationName = "sendSMSAlert")
    public void sendSMSAlert(
            @WebParam(name = "content") String content,
            @WebParam(name = "toPartner") boolean toPartner,
            @WebParam(name = "signature") String signature) {
        String data = content;
        if (ServiceUtil.hashData(data).equals(signature)) {
            MailServiceUtil.smsAlert(content, null);
            if (toPartner) {
                MailServiceUtil.smsAlertToPartner(content);
            }
        }
    }

    @WebMethod(operationName = "sendMailAlert")
    public void sendMailAlert(
            @WebParam(name = "subject") String subject,
            @WebParam(name = "content") String content,
            @WebParam(name = "toPartner") boolean toPartner,
            @WebParam(name = "signature") String signature) {
        String data = subject + content;
        if (ServiceUtil.hashData(data).equals(signature)) {
            MailServiceUtil.mailAlert(subject, content);
            if (toPartner) {
                MailServiceUtil.mailAlertToPartner(subject, content);
            }
        }
    }
}
