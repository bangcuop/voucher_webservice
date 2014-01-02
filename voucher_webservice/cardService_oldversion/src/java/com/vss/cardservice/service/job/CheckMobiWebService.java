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
import java.util.Date;

/**
 *
 * @author zannami
 */
public class CheckMobiWebService extends AbstractCronTriggerJob {

    public CheckMobiWebService() {
        super();
    }

    public void excute() {
//        System.out.println("-------------- CheckMobiWebService ------------->>> ServiceUtil.mobiWSStatus:" + ServiceUtil.mobiWSStatus + "| Time:" + new Date());
        boolean sendToPartner = (ServiceUtil.sendToPartner != null && ServiceUtil.sendToPartner.equalsIgnoreCase("true"));
        try {
            URL myURL = new URL(ServiceUtil.getString("ws_url"));
            URLConnection myURLConnection = myURL.openConnection();
            myURLConnection.connect();

            if (!ServiceUtil.mobiWSStatus) {
                System.out.println("-------------- CheckMobiWebService - UP ------------>>> ServiceUtil.mobiWSStatus:" + ServiceUtil.mobiWSStatus);
                ServiceUtil.mobiWSStatus = true;
                MailServiceUtil.sendAlert(ServiceUtil.reconnectSubject.replaceAll("telco", "MOBIPHONE"),
                        ServiceUtil.reconnectContent.replaceAll("telco", "MOBIPHONE"),
                        ServiceUtil.smsReconnect.replaceAll("telco", "MOBIPHONE"), null, sendToPartner);
            }
        } catch (MalformedURLException e) {     // new URL() failed
            if (ServiceUtil.mobiWSStatus) {
                System.out.println("-------------- CheckMobiWebService - DOWN ------------>>> ServiceUtil.mobiWSStatus:" + ServiceUtil.mobiWSStatus);
                ServiceUtil.mobiWSStatus = false;
                MailServiceUtil.sendAlert(ServiceUtil.disconnectSubject.replaceAll("telco", "MOBIPHONE"),
                        ServiceUtil.disconnectContent.replaceAll("telco", "MOBIPHONE"),
                        ServiceUtil.smsDisconnect.replaceAll("telco", "MOBIPHONE"), null, sendToPartner);
            }
        } catch (IOException e) {               // openConnection() failed
            if (ServiceUtil.mobiWSStatus) {
                System.out.println("-------------- CheckMobiWebService - DOWN ------------>>> ServiceUtil.mobiWSStatus:" + ServiceUtil.mobiWSStatus);
                ServiceUtil.mobiWSStatus = false;
                MailServiceUtil.sendAlert(ServiceUtil.disconnectSubject.replaceAll("telco", "MOBIPHONE"),
                        ServiceUtil.disconnectContent.replaceAll("telco", "MOBIPHONE"),
                        ServiceUtil.smsDisconnect.replaceAll("telco", "MOBIPHONE"), null, sendToPartner);
            }
        }
    }
}
