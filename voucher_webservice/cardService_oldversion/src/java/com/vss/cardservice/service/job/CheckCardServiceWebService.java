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
public class CheckCardServiceWebService extends AbstractCronTriggerJob {

    public CheckCardServiceWebService() {
        super();
    }

    public void excute() {
        boolean sendToPartner = (ServiceUtil.sendToPartner!=null && ServiceUtil.sendToPartner.equalsIgnoreCase("true"));
        try {
            URL myURL = new URL(ServiceUtil.getString("voucherservice-ws-url"));
            URLConnection myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            if (!ServiceUtil.cardWSStatus) {
                ServiceUtil.cardWSStatus = true;
                MailServiceUtil.sendAlert(ServiceUtil.reconnectSubject.replaceAll("telco", "CardService"),
                        ServiceUtil.reconnectContent.replaceAll("telco", "CardService"),
                        ServiceUtil.smsReconnect.replaceAll("telco", "CardService"), null, sendToPartner);
            }
        } catch (MalformedURLException e) {     // new URL() failed
            if (ServiceUtil.cardWSStatus) {
                ServiceUtil.cardWSStatus = false;
                MailServiceUtil.sendAlert(ServiceUtil.disconnectSubject.replaceAll("telco", "CardService"),
                        ServiceUtil.disconnectContent.replaceAll("telco", "CardService"),
                        ServiceUtil.smsDisconnect.replaceAll("telco", "CardService"), null, sendToPartner);
            }
        } catch (IOException e) {               // openConnection() failed
            if (ServiceUtil.cardWSStatus) {
                ServiceUtil.cardWSStatus = false;
                MailServiceUtil.sendAlert(ServiceUtil.disconnectSubject.replaceAll("telco", "CardService"),
                        ServiceUtil.disconnectContent.replaceAll("telco", "CardService"),
                        ServiceUtil.smsDisconnect.replaceAll("telco", "CardService"), null, sendToPartner);
            }
        }
    }
}
