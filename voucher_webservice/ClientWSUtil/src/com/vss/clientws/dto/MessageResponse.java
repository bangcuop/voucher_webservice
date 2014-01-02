/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.clientws.dto;


/**
 *
 * @author zannami
 */
public class MessageResponse {

    String message;
    String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
