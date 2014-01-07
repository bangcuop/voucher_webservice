/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.thread;

import com.vss.cardservice.service.util.MailServiceUtil;

/**
 *
 * @author thangtq
 */
public class AlertThread extends Thread {

    private String subject;
    private String mailContent;
    private String smsContent;
    private String phone;
    private boolean sendToPartner;

    public AlertThread(String subject, String mailContent) {
        super();
        this.subject = subject;
        this.mailContent = mailContent;

    }

    public AlertThread(String subject, String mailContent, String smsContent, String phone, boolean sendToPartner) {
        this(subject, mailContent);
        this.smsContent = smsContent;
        this.phone = phone;
        this.sendToPartner = sendToPartner;
    }

    @Override
    public void run() {
        if (smsContent == null) {
            MailServiceUtil.mailAlert(subject, mailContent);
        } else {
            MailServiceUtil.sendAlert(subject, mailContent, smsContent, phone, sendToPartner);
        }
    }
}
