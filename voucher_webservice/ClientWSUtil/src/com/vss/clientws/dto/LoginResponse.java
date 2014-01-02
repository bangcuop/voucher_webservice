/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.clientws.dto;

/**
 *
 * @author zannami
 * 
 */
public class LoginResponse extends MessageResponse {

    String sessionid;
    String transid;

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

}
