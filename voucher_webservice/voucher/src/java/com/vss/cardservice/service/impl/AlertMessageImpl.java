/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.impl;

import com.vss.cardservice.api.IAlertService;
import com.vss.sms.proxy.SMSPartnerProxy;
import java.io.File;
import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 *
 * @author zannami
 */
public class AlertMessageImpl implements IAlertService {

    private JavaMailSender javaMailSender;
    private MailSender mailSender;
    private SimpleMailMessage tmpMail;
    private String fromMime;
    private static final int SMS_PARNTER_ID = 1;

    public void sendAlertMessage(String phone, String message) throws Exception {
        SMSPartnerProxy.sendSms(phone, message, SMS_PARNTER_ID, String.valueOf(System.currentTimeMillis()), null);
//        NewsAgencyProxy.sendSMS(description, message, null);
    }

    public void sendAlertMessages(String[] description, String message) throws Exception {
        for (String s : description) {
            if (s != null && !s.isEmpty()) {
                SMSPartnerProxy.sendSms(s, message, SMS_PARNTER_ID, String.valueOf(System.currentTimeMillis()), null);
            }
        }
    }

    public void sendMail(String emailAddress, String[] cc, String subject, String message) throws MailException {
        SimpleMailMessage msg = new SimpleMailMessage(this.tmpMail);
        msg.setTo(emailAddress);
        msg.setCc(cc);
        msg.setText(message);
        msg.setSubject(subject);
        this.mailSender.send(msg);
    }

    public void sendMail(String emailAddress, String subject, String message) throws MailException {
        SimpleMailMessage msg = new SimpleMailMessage(this.tmpMail);
        msg.setTo(emailAddress);
        msg.setText(message);
        msg.setSubject(subject);
        this.mailSender.send(msg);
    }

    public void sendMIME(String emailAddress, String[] cc, String subject, String message) throws MailException, MessagingException {
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mail, true, "utf-8");
        mimeMessageHelper.setFrom(fromMime);
        mimeMessageHelper.setTo(emailAddress);
        mimeMessageHelper.setCc(cc);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(message);
        this.javaMailSender.send(mail);
    }

    public void sendMIME(String emailAddress, String subject, String message, File file) throws MailException, MessagingException {
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mail, true, "utf-8");
        mimeMessageHelper.setFrom(fromMime);
        mimeMessageHelper.setTo(emailAddress);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(message);
        mimeMessageHelper.addAttachment(file.getName(), file);
        this.javaMailSender.send(mail);
    }

    public void sendMIME(String emailAddress, String[] cc, String subject, String message, File file) throws MailException, MessagingException {
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mail, true, "utf-8");
        mimeMessageHelper.setFrom(fromMime);
        mimeMessageHelper.setTo(emailAddress);
        mimeMessageHelper.setCc(cc);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(message);
        mimeMessageHelper.addAttachment(file.getName(), file);
        this.javaMailSender.send(mail);
    }

    public void sendMIME(String emailAddress, String[] cc, String subject, String message, File file, String fileName) throws MailException, MessagingException {
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mail, true, "utf-8");
        mimeMessageHelper.setFrom(fromMime);
        mimeMessageHelper.setTo(emailAddress);
        mimeMessageHelper.setCc(cc);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(message);
        mimeMessageHelper.addAttachment(fileName, file);
        this.javaMailSender.send(mail);
    }

    public String getFromMime() {
        return fromMime;
    }

    public void setFromMime(String fromMime) {
        this.fromMime = fromMime;
    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public MailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public SimpleMailMessage getTmpMail() {
        return tmpMail;
    }

    public void setTmpMail(SimpleMailMessage tmpMail) {
        this.tmpMail = tmpMail;
    }
}
