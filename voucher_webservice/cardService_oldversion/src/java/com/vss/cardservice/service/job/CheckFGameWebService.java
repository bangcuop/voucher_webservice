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
import java.util.ResourceBundle;

/**
 *
 * @author zannami
 */
public class CheckFGameWebService extends AbstractCronTriggerJob {

    private ResourceBundle configure = ResourceBundle.getBundle("FGameDtuConfig");

    public CheckFGameWebService() {
        super();
    }

    public void excute() {
        boolean sendToPartner = (ServiceUtil.sendToPartner != null && ServiceUtil.sendToPartner.equalsIgnoreCase("true"));
        try {
            String s = configure.getString("VTC");
            String[] strArray = s.split("\\/");
            String endPointUrl = "";
            for (int i = 0; i < strArray.length; i++) {
                String st = strArray[i];
                if (i < 3) {
                    if (i == 0) {
                        endPointUrl = st;
                    } else {
                        endPointUrl = endPointUrl + "/" + st;
                    }
                }
            }
            URL myURL = new URL(endPointUrl);
            URLConnection myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            if (!ServiceUtil.fgameWSStatus) {
                System.out.println("-------------- CheckFGameWebService - UP ------------>>> ServiceUtil.fgameWSStatus:" + ServiceUtil.fgameWSStatus + "| Time:" + new Date());
                ServiceUtil.fgameWSStatus = true;
                MailServiceUtil.sendAlert(
                        ServiceUtil.reconnectSubject.replaceAll("telco", "FGame"),
                        ServiceUtil.reconnectContent.replaceAll("telco", "FGame"),
                        ServiceUtil.smsReconnect.replaceAll("telco", "FGame"), null, sendToPartner);
            }
        } catch (MalformedURLException e) {     // new URL() failed
            if (ServiceUtil.fgameWSStatus) {
                System.out.println("-------------- CheckFGameWebService - DOWN ------------>>> ServiceUtil.fgameWSStatus:" + ServiceUtil.fgameWSStatus + "| Time:" + new Date());
                ServiceUtil.fgameWSStatus = false;
                MailServiceUtil.sendAlert(ServiceUtil.disconnectSubject.replaceAll("telco", "FGame"),
                        ServiceUtil.disconnectContent.replaceAll("telco", "FGame"),
                        ServiceUtil.smsDisconnect.replaceAll("telco", "FGame"), null, sendToPartner);
            }
        } catch (IOException e) {               // openConnection() failed
            if (ServiceUtil.fgameWSStatus) {
                System.out.println("-------------- CheckFGameWebService - DOWN ------------>>> ServiceUtil.fgameWSStatus:" + ServiceUtil.fgameWSStatus + "| Time:" + new Date());
                ServiceUtil.fgameWSStatus = false;
                MailServiceUtil.sendAlert(ServiceUtil.disconnectSubject.replaceAll("telco", "FGame"),
                        ServiceUtil.disconnectContent.replaceAll("telco", "FGame"),
                        ServiceUtil.smsDisconnect.replaceAll("telco", "FGame"), null, sendToPartner);
            }
        }
    }
}
