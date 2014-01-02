/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.clientws.dto;

/**
 *
 * @author zannami
 */
public class LoginInfo {

    String partner;
    String mPin;
    String user;
    String password;

    public String getmPin() {
        return mPin;
    }

    public void setmPin(String mPin) {
        this.mPin = mPin;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
