/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.dto;

/**
 *
 * @author thibt
 */
public class ProviderParameter {

    public static int TELCO = 1;
    public static int GATE = 2;
    public static int VTC = 3;
    private Integer type;
    private Integer issuerId;
    private String transactionId;
    private Long amount;
    private String signature;
    private String ipRequest;
    private String updateTransResponse;

    public Integer getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(Integer issuerId) {
        this.issuerId = issuerId;
    }

    public String getIpRequest() {
        return ipRequest;
    }

    public void setIpRequest(String ipRequest) {
        this.ipRequest = ipRequest;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUpdateTransResponse() {
        return updateTransResponse;
    }

    public void setUpdateTransResponse(String updateTransResponse) {
        this.updateTransResponse = updateTransResponse;
    }
}
