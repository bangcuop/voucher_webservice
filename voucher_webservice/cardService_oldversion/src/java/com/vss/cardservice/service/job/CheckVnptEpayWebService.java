/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.job;

import com.vss.cardservice.service.util.MailServiceUtil;
import com.vss.cardservice.service.util.ServiceUtil;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author zannami
 */
public class CheckVnptEpayWebService extends AbstractCronTriggerJob {

    public CheckVnptEpayWebService() {
        super();
    }

    public void excute() {
        boolean sendToPartner = (ServiceUtil.sendToPartner!=null && ServiceUtil.sendToPartner.equalsIgnoreCase("true"));
        try {
            URL myURL = new URL(ServiceUtil.getString("ws-vnpt-epay-url"));
            URLConnection myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            if (!ServiceUtil.vnptEpayWSStatus) {
                ServiceUtil.vnptEpayWSStatus = true;
                MailServiceUtil.sendAlert(ServiceUtil.reconnectSubject.replaceAll("telco", "VNPTEPAY"),
                        ServiceUtil.reconnectContent.replaceAll("telco", "VNPTEPAY"),
                        ServiceUtil.smsReconnect.replaceAll("telco", "VNPTEPAY"), null, sendToPartner);
            }
        } catch (MalformedURLException e) {     // new URL() failed
            if (ServiceUtil.vnptEpayWSStatus) {
                ServiceUtil.vnptEpayWSStatus = false;
                MailServiceUtil.sendAlert(ServiceUtil.disconnectSubject.replaceAll("telco", "VNPTEPAY"),
                        ServiceUtil.disconnectContent.replaceAll("telco", "VNPTEPAY"),
                        ServiceUtil.smsDisconnect.replaceAll("telco", "VNPTEPAY"), null, sendToPartner);
            }
        } catch (IOException e) {               // openConnection() failed
            if (ServiceUtil.vnptEpayWSStatus) {
                ServiceUtil.vnptEpayWSStatus = false;
                MailServiceUtil.sendAlert(ServiceUtil.disconnectSubject.replaceAll("telco", "VNPTEPAY"),
                        ServiceUtil.disconnectContent.replaceAll("telco", "VNPTEPAY"),
                        ServiceUtil.smsDisconnect.replaceAll("telco", "VNPTEPAY"), null, sendToPartner);
            }
        }
    }
}
