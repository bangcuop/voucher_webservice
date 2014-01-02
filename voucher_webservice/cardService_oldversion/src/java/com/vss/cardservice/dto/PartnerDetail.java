/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.dto;

/**
 *
 * @author koziwa
 */
public class PartnerDetail extends Partner {

    String telcoServiceName;
    String telcoAccount;
    String telcoPassword;
    String telcoSecretKey;
    String telcoWSURL;

    public String getTelcoAccount() {
        return telcoAccount;
    }

    public void setTelcoAccount(String telcoAccount) {
        this.telcoAccount = telcoAccount;
    }

    public String getTelcoPassword() {
        return telcoPassword;
    }

    public void setTelcoPassword(String telcoPassword) {
        this.telcoPassword = telcoPassword;
    }

    public String getTelcoSecretKey() {
        return telcoSecretKey;
    }

    public void setTelcoSecretKey(String telcoSecretKey) {
        this.telcoSecretKey = telcoSecretKey;
    }

    public String getTelcoServiceName() {
        return telcoServiceName;
    }

    public void setTelcoServiceName(String telcoServiceName) {
        this.telcoServiceName = telcoServiceName;
    }

    public String getTelcoWSURL() {
        return telcoWSURL;
    }

    public void setTelcoWSURL(String telcoWSURL) {
        this.telcoWSURL = telcoWSURL;
    }
}
