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
    private String issuer;
    private Integer issuerId;
    private Integer par;
    private Integer cardId;
    String sessionValue;
    String validCardCode;
    boolean checkSerial;
    String validCardSerial;
    private List<Integer> codeLengthList = new ArrayList<Integer>();
    private List<Integer> serialLengthList = new ArrayList<Integer>();

    public Issuer() {
    }

    public Issuer(Integer issuerId, String issuer) {
        this.issuerId = issuerId;
        this.issuer = issuer;
    }

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

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
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

    public Integer getPar() {
        return par;
    }

    public void setPar(Integer par) {
        this.par = par;
    }
}
