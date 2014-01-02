/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdpay.vtc.dto;

/**
 *
 * @author zannami
 */
public class RequestResponse {

    String xml;
    int ResponseStatus;
    String Description;

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getResponseStatus() {
        return ResponseStatus;
    }

    public void setResponseStatus(int ResponseStatus) {
        this.ResponseStatus = ResponseStatus;
    }
}
