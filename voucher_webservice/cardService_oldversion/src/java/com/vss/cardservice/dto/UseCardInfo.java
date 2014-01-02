/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.dto;

/**
 *
 * @author zannami
 */
public class UseCardInfo {

    String issuer;
    String cardSerial;
    String cardCode;
    String transRef;
    String partnerCode;
    String password;
    String signature;
    String responseToPartner;

    public String getResponseToPartner() {
        return responseToPartner;
    }

    public void setResponseToPartner(String responseToPartner) {
        this.responseToPartner = responseToPartner;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardSerial() {
        return cardSerial;
    }

    public void setCardSerial(String cardSerial) {
        this.cardSerial = cardSerial;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTransRef() {
        return transRef;
    }

    public void setTransRef(String transRef) {
        this.transRef = transRef;
    }
}
