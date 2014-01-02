/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.util;

import com.vss.cardservice.api.IAlertService;
import com.vss.cardservice.api.IPartnerService;
import com.vss.cardservice.dto.Partner;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author zannami
 */
public class MailServiceUtil {

    private static IAlertService alertService;
    private static ResourceBundle bundle;
    private static IPartnerService partnerService;
    private static List<Partner> listPartner;

    private static ResourceBundle getBundle() {
        if (bundle == null) { 
            bundle = ResourceBundle.getBundle("configure");
        }
        return bundle;
    }
    
    private static final boolean useService = Boolean.valueOf(getBundle().getString("mail.usespring"));

    public static String getString(String key) {
        try {
            return getBundle().getString(key);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static List<Partner> getListPartner() {
        if (listPartner == null) {
            listPartner = partnerService.getListPartner();
        }
        return listPartner;
    }

    public void setPartnerService(IPartnerService partnerService) {
        MailServiceUtil.partnerService = partnerService;
    }

    public void setAlertService(IAlertService alertService) {
        MailServiceUtil.alertService = alertService;
    }

    public static void mailAlertToPartner(String subject, String content) {
        try {
            List<Partner> ps = partnerService.getListPartner();
            if (ps != null && !ps.isEmpty()) {
                for (Partner p : ps) {
                    String emails = p.getMailAddress();
                    if (emails != null && emails.length() > 0) {
                        String mailTo = "";
                        String[] cc = null;
                        if (emails.indexOf(";") > -1) {
                            mailTo = emails.split(";")[0];
                            cc = emails.replaceAll(mailTo + ";", "").split(";");
                        } else {
                            mailTo = emails;
                        }
                        if (!mailTo.isEmpty() && (cc == null || cc.length == 0)) {
                            alertService.sendMail(mailTo, subject, content);
                        }
                        if (!mailTo.isEmpty() && cc != null && cc.length > 0) {
                            alertService.sendMail(mailTo, cc, subject, content);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void mailAlert(String subject, String content) {
        try {
            String emails = getBundle().getString("emails");
            if (emails != null && emails.length() > 0) {
                String mailTo = "";
                String[] cc = null;
                if (emails.indexOf(";") > -1) {
                    mailTo = emails.split(";")[0];
                    cc = emails.replaceAll(mailTo + ";", "").split(";");
                } else {
                    mailTo = emails;
                }
                if (!mailTo.isEmpty() && (cc == null || cc.length == 0)) {
                    if (useService) {
                        alertService.sendMail(mailTo, subject, content);
                    } else {
                        sendByCode(subject, content, mailTo);
                    }
                }
                if (!mailTo.isEmpty() && cc != null && cc.length > 0) {
                    if (useService) {
                        alertService.sendMail(mailTo, cc, subject, content);
                    } else {
                        sendByCode(subject, content, mailTo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void smsAlert(String content, String phones) {
        try {
            if (phones == null || phones.isEmpty()) {
                phones = getBundle().getString("phones");
            }
            if (phones != null && phones.length() > 0) {
                for (String phone : phones.split(";")) {
                    alertService.sendAlertMessage(phone, content);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void smsAlertToPartner(String content) {
        try {
            List<Partner> ps = partnerService.getListPartner();
            if (ps != null && !ps.isEmpty()) {
                for (Partner p : ps) {
                    try {
                        if (p.getKD_DT() != null && !p.getKT_DT().isEmpty()) {
                            alertService.sendAlertMessage(p.getKD_DT(), content);
                        }
                        if (p.getKT_DT() != null && !p.getKT_DT().isEmpty()) {
                            alertService.sendAlertMessage(p.getKT_DT(), content);
                        }
                        if (p.getDS_DT() != null && !p.getDS_DT().isEmpty()) {
                            alertService.sendAlertMessage(p.getDS_DT(), content);
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendAlert(String subject, String mailContent, String smsContent, String phone, boolean sendToPartner) {
        String alertType = getBundle().getString("alert_type");
        if (alertType == null || alertType.isEmpty()) {
            alertType = "0";
        }
        try {
            switch (Integer.valueOf(alertType)) {
                case 0:
                    mailAlert(subject, mailContent);
                    break;
                case 1:
                    smsAlert(smsContent, phone);
                    if (sendToPartner) {
                        smsAlertToPartner(smsContent);
                    }
                    break;
                case 2:
                    smsAlert(smsContent, phone);
                    if (sendToPartner) {
                        smsAlertToPartner(smsContent);
                    }
                    mailAlert(subject, mailContent);
                    break;
                default:
                    mailAlert(subject, mailContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mailAlert(subject, mailContent);
        }
    }

    public static void mailAlertTransaction(String subject, String mailContent) {
        String alertType = getBundle().getString("alert_type");
        if (alertType == null || alertType.isEmpty()) {
            alertType = "0";
        }
        try {
            mailAlert(subject, mailContent);
        } catch (Exception e) {
            e.printStackTrace();
            mailAlert(subject, mailContent);
        }
    }
    static final String from = bundle.getString("smtp.gmail.from");
    static final String password = bundle.getString("smtp.gmail.pwd");
    static final String to = bundle.getString("smtp.gmail.to");
    static final String port = bundle.getString("smtp.gmail.port");
    static final String auth = bundle.getString("mail.smtps.auth");
    static final String protocol = bundle.getString("mail.transport.protocol");
    static final String host = bundle.getString("mail.smtps.host");
//    static final String 

    public static void sendByCode(String subject, String content, String maillTo) {
        try {
//            final String SMTP_HOST_NAME = "smtp.gmail.com";
//            final int SMTP_HOST_PORT = 465;
//            final String SMTP_AUTH_USER = "ds@ahha.vn";
//            final String SMTP_AUTH_PWD = "@doisoat@123";
            Properties props = new Properties();

            props.put("mail.transport.protocol", protocol);
            props.put("mail.smtps.host", host);
            props.put("mail.smtps.auth", auth);
            // props.put("mail.smtps.quitwait", "false");

            Session mailSession = Session.getDefaultInstance(props);
//            mailSession.setDebug(true);
            Transport transport = mailSession.getTransport();

            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(subject);
            message.setContent(content, "text/plain");

            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(maillTo));
            transport.connect(host, Integer.valueOf(port), from, password);

            transport.sendMessage(message,
                    message.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        sendByCode("", "", "kt@ahha.vn");
    }
}
