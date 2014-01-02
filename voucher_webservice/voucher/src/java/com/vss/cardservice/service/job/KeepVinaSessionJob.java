///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package com.vss.cardservice.service.job;
//
//import com.elcom.vchgws.message.LoginResponse;
//import com.vss.cardservice.service.util.WebParameter;
//import javax.xml.bind.JAXBElement;
//import vcardserverproxy.VCardServerProxy;
//
///**
// *
// * @author zannami
// */
//public class KeepVinaSessionJob extends AbstractCronTriggerJob {
//
//    public KeepVinaSessionJob() {
//        super();
//        excute();
//    }
//
//    public void excute() {
//        try {
//            if (WebParameter.vinaSessionValue == null) {
//                LoginResponse login = VCardServerProxy.login();
//                JAXBElement<String> sessionid = login.getSessionid();
//                WebParameter.vinaSessionValue = sessionid.getValue();
//            }
//            if(WebParameter.session_login_date==null){
//                WebParameter.session_login_date = System.currentTimeMillis();
//            }
//            if(System.currentTimeMillis() - WebParameter.session_login_date >= WebParameter.session_timeout){
//                VCardServerProxy.logout(WebParameter.vinaSessionValue);
//                WebParameter.vinaSessionValue = null;
//            }
//        } catch (Exception e) {
//            WebParameter.vinaSessionValue = null;
//            e.printStackTrace();
//        }
//    }
//
//}
