/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vss.cardservice.dto;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zannami
 */
public class IssuerConfig {
    String issuerCode;
     /**
     * Kieu chay
     * 0: Only
     * 1: Cluster
     * 2: Time
     * 3: Volume
     */
    Integer runType;
    /**
     * Key: Provider code -> getService
     * Value:
     *  runType = 1: So giao dich da chay
     *  runType = 2: So gio da chay
     *  runType = 3: San luong da chay (trieu)
     */
    Map<String, Integer> supportService = new HashMap<String, Integer>();

    public String getIssuerCode() {
        return issuerCode;
    }

    public void setIssuerCode(String issuerCode) {
        this.issuerCode = issuerCode;
    }

    public Integer getRunType() {
        return runType;
    }

    public void setRunType(Integer runType) {
        this.runType = runType;
    }

    public Map<String, Integer> getSupportService() {
        return supportService;
    }

    public void setSupportService(Map<String, Integer> supportService) {
        this.supportService = supportService;
    }
   
}
