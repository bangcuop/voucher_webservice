/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.api;

import java.io.File;
import javax.mail.MessagingException;
import org.springframework.mail.MailException;

/**
 *
 * @author dotd
 */
public interface IMailService {

    public void sendMail(String emailAddress, String subject, String message) throws MailException;

    public void sendMIME(String emailAddress, String[] cc, String subject, String message) throws MailException, MessagingException;

    public void sendMIME(String emailAddress, String subject, String message, File file) throws MailException, MessagingException;

    public void sendMIME(String emailAddress, String[] cc, String subject, String message, File file) throws MailException, MessagingException;

    public void sendMIME(String emailAddress, String[] cc, String subject, String message, File file, String fileName) throws MailException, MessagingException;

    public void sendMail(String emailAddress, String[] cc, String subject, String message) throws MailException;
}
