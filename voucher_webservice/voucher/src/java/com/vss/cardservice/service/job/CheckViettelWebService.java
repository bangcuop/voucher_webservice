///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package com.vss.cardservice.service.job;
//
//import com.vss.cardservice.service.util.MailServiceUtil;
//import com.vss.cardservice.service.util.ServiceUtil;
//import com.vss.viettel.proxy.ViettelServiceProxy;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author zannami
// */
//public class CheckViettelWebService extends AbstractCronTriggerJob {
//
//    public CheckViettelWebService() {
//        super();
//    }
//
//    public void excute() {
////        System.out.println("-------------- CheckMobiWebService ------------->>> ServiceUtil.viettelWSStatus:" + ServiceUtil.viettelWSStatus + "| Time:" + new Date());
//        boolean sendToPartner = (ServiceUtil.sendToPartner != null && ServiceUtil.sendToPartner.equalsIgnoreCase("true"));
//        try {
//            try {
//                ViettelServiceProxy.installMyPolicy();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//            URL myURL = new URL(ServiceUtil.getString("viettel-ws-url"));
//            URLConnection myURLConnection = myURL.openConnection();
//            myURLConnection.connect();
//            if (!ServiceUtil.viettelWSStatus) {
//                System.out.println("-------------- CheckViettelWebService - UP ------------>>> ServiceUtil.viettelWSStatus:" + ServiceUtil.viettelWSStatus);
//                ServiceUtil.viettelWSStatus = true;
//                MailServiceUtil.sendAlert(ServiceUtil.reconnectSubject.replaceAll("telco", "Viettel"),
//                        ServiceUtil.reconnectContent.replaceAll("telco", "Viettel"),
//                        ServiceUtil.smsReconnect.replaceAll("telco", "Viettel"), null, sendToPartner);
//            }
//        } catch (MalformedURLException e) {     // new URL() failed
//            if (ServiceUtil.viettelWSStatus) {
//                System.out.println("-------------- CheckViettelWebService - DOWN ------------>>> ServiceUtil.viettelWSStatus:" + ServiceUtil.viettelWSStatus);
//                ServiceUtil.viettelWSStatus = false;
//                MailServiceUtil.sendAlert(ServiceUtil.disconnectSubject.replaceAll("telco", "Viettel"),
//                        ServiceUtil.disconnectContent.replaceAll("telco", "Viettel"),
//                        ServiceUtil.smsDisconnect.replaceAll("telco", "Viettel"), null, sendToPartner);
//            }
//        } catch (IOException e) {               // openConnection() failed
//            if (ServiceUtil.viettelWSStatus) {
//                System.out.println("-------------- CheckViettelWebService - DOWN ------------>>> ServiceUtil.viettelWSStatus:" + ServiceUtil.viettelWSStatus);
//                ServiceUtil.viettelWSStatus = false;
//                MailServiceUtil.sendAlert(ServiceUtil.disconnectSubject.replaceAll("telco", "Viettel"),
//                        ServiceUtil.disconnectContent.replaceAll("telco", "Viettel"),
//                        ServiceUtil.smsDisconnect.replaceAll("telco", "Viettel"), null, sendToPartner);
//            }
//        }
//    }
//
//}
