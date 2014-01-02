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
import vdccardserviceproxy.Config;

/**
 *
 * @author zannami
 */
public class CheckVDCWebService extends AbstractCronTriggerJob {

    public CheckVDCWebService() {
        super();
    }

    public void excute() {
        boolean sendToPartner = (ServiceUtil.sendToPartner != null && ServiceUtil.sendToPartner.equalsIgnoreCase("true"));
        try {
            String endPointUrl = Config.END_POINT;
            URL myURL = new URL(endPointUrl);
            URLConnection myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            if (!ServiceUtil.vdcWSStatus) {
                System.out.println("-------------- CheckVDCWebService - UP ------------>>> ServiceUtil.vdcWSStatus:" + ServiceUtil.vdcWSStatus + "| Time:" + new Date());
                ServiceUtil.vdcWSStatus = true;
                MailServiceUtil.sendAlert(
                        ServiceUtil.reconnectSubject.replaceAll("telco", "VDC"),
                        ServiceUtil.reconnectContent.replaceAll("telco", "VDC"),
                        ServiceUtil.smsReconnect.replaceAll("telco", "VDC"), null, sendToPartner);
            }
        } catch (MalformedURLException e) {     // new URL() failed
            if (ServiceUtil.vdcWSStatus) {
                System.out.println("-------------- CheckVDCWebService - DOWN ------------>>> ServiceUtil.vdcWSStatus:" + ServiceUtil.vdcWSStatus + "| Time:" + new Date());
                ServiceUtil.vdcWSStatus = false;
                MailServiceUtil.sendAlert(ServiceUtil.disconnectSubject.replaceAll("telco", "VDC"),
                        ServiceUtil.disconnectContent.replaceAll("telco", "VDC"),
                        ServiceUtil.smsDisconnect.replaceAll("telco", "VDC"), null, sendToPartner);
            }
        } catch (IOException e) {               // openConnection() failed
            if (ServiceUtil.vdcWSStatus) {
                System.out.println("-------------- CheckVDCWebService - DOWN ------------>>> ServiceUtil.vdcWSStatus:" + ServiceUtil.vdcWSStatus + "| Time:" + new Date());
                ServiceUtil.vdcWSStatus = false;
                MailServiceUtil.sendAlert(ServiceUtil.disconnectSubject.replaceAll("telco", "VDC"),
                        ServiceUtil.disconnectContent.replaceAll("telco", "VDC"),
                        ServiceUtil.smsDisconnect.replaceAll("telco", "VDC"), null, sendToPartner);
            }
        }
    }
}
