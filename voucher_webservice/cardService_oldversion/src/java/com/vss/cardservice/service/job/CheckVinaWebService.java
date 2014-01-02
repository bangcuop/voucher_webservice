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
public class CheckVinaWebService extends AbstractCronTriggerJob {

    public CheckVinaWebService() {
        super();
    }

    public void excute() {
//        System.out.println("-------------- CheckVinaWebService ------------->>> ServiceUtil.vinaWSStatus:" + ServiceUtil.vinaWSStatus + "| Time:" + new Date());
        boolean sendToPartner = (ServiceUtil.sendToPartner!=null && ServiceUtil.sendToPartner.equalsIgnoreCase("true"));
        try {
            URL myURL = new URL(ServiceUtil.getString("ws-url-vinaphone"));
            URLConnection myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            if (!ServiceUtil.vinaWSStatus) {
                System.out.println("-------------- CheckVinaWebService - UP ------------>>> ServiceUtil.vinaWSStatus:" + ServiceUtil.vinaWSStatus + "| Time:" + new Date());
                ServiceUtil.vinaWSStatus = true;
                MailServiceUtil.sendAlert(
                        ServiceUtil.reconnectSubject.replaceAll("telco", "VINAPHONE"),
                        ServiceUtil.reconnectContent.replaceAll("telco", "VINAPHONE"),
                        ServiceUtil.smsReconnect.replaceAll("telco", "VINAPHONE"), null, sendToPartner);
            }
        } catch (MalformedURLException e) {     // new URL() failed
            if (ServiceUtil.vinaWSStatus) {
                System.out.println("-------------- CheckVinaWebService - DOWN ------------>>> ServiceUtil.vinaWSStatus:" + ServiceUtil.vinaWSStatus + "| Time:" + new Date());
                ServiceUtil.vinaWSStatus = false;
                MailServiceUtil.sendAlert(ServiceUtil.disconnectSubject.replaceAll("telco", "VINAPHONE"),
                        ServiceUtil.disconnectContent.replaceAll("telco", "VINAPHONE"),
                        ServiceUtil.smsDisconnect.replaceAll("telco", "VINAPHONE"), null, sendToPartner);
            }
        } catch (IOException e) {               // openConnection() failed
            if (ServiceUtil.vinaWSStatus) {
                System.out.println("-------------- CheckVinaWebService - DOWN ------------>>> ServiceUtil.vinaWSStatus:" + ServiceUtil.vinaWSStatus + "| Time:" + new Date());
                ServiceUtil.vinaWSStatus = false;
                MailServiceUtil.sendAlert(ServiceUtil.disconnectSubject.replaceAll("telco", "VINAPHONE"),
                        ServiceUtil.disconnectContent.replaceAll("telco", "VINAPHONE"),
                        ServiceUtil.smsDisconnect.replaceAll("telco", "VINAPHONE"), null, sendToPartner);
            }
        }
    }
}
