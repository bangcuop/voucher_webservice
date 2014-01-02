/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdpay.vtc.dto;

/**
 *
 * @author zannami
 */
public class CardRequest {

    String Function;
    String CardID;
    String CardCode;
    String Description;

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String CardCode) {
        this.CardCode = CardCode;
    }

    public String getCardID() {
        return CardID;
    }

    public void setCardID(String CardID) {
        this.CardID = CardID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getFunction() {
        return Function;
    }

    public void setFunction(String Function) {
        this.Function = Function;
    }
}
