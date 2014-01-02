/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.clientws.dto;

/**
 *
 * @author zannami
 * <element name="amount" nillable="true" type="xsd:string"/>
<element name="message" nillable="true" type="xsd:string"/>
<element name="responseamount" nillable="true" type="xsd:string"/>
<element name="status" nillable="true" type="xsd:string"/>
<element name="transid" nillable="true" type="xsd:string"/>
 */
public class ChargeReponse extends MessageResponse {

    String amount;
    String responseamount;
    String transid;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getResponseamount() {
        return responseamount;
    }

    public void setResponseamount(String responseamount) {
        this.responseamount = responseamount;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }
}
