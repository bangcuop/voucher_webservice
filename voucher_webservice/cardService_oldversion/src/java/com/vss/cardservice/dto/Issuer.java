/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zannami
 */
public class Issuer implements Serializable {

    public static String VTC_COIN = "VCOIN";
    public static String GATE = "BAC";
    public static String MOBIPHONE = "MOBI";
    public static String VIETTEL = "VT";
    public static String VINAPHONE = "VINA";
    String issuer;
    Integer issuerId;
    String par;
    String cardId;
    String sessionValue;
    String validCardCode;
    boolean checkSerial;
    String validCardSerial;
    private List<Integer> codeLengthList = new ArrayList<Integer>();
    private List<Integer> serialLengthList = new ArrayList<Integer>();

    public List<Integer> getCodeLengthList() {
        return codeLengthList;
    }

    public void setCodeLengthList(List<Integer> codeLengthList) {
        this.codeLengthList = codeLengthList;
    }

    public List<Integer> getSerialLengthList() {
        return serialLengthList;
    }

    public void setSerialLengthList(List<Integer> serialLengthList) {
        this.serialLengthList = serialLengthList;
    }

    public boolean isCheckSerial() {
        return checkSerial;
    }

    public void setCheckSerial(boolean checkSerial) {
        this.checkSerial = checkSerial;
    }

    public String getValidCardSerial() {
        return validCardSerial;
    }

    public void setValidCardSerial(String validCardSerial) {
        this.validCardSerial = validCardSerial;
    }

    public String getValidCardCode() {
        return validCardCode;
    }

    public void setValidCardCode(String validCardCode) {
        this.validCardCode = validCardCode;
    }

    public String getSessionValue() {
        return sessionValue;
    }

    public void setSessionValue(String sessionValue) {
        this.sessionValue = sessionValue;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Integer getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(Integer issuerId) {
        this.issuerId = issuerId;
    }

    public String getPar() {
        return par;
    }

    public void setPar(String par) {
        this.par = par;
    }
}
