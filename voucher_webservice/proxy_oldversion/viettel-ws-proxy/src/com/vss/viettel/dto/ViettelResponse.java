/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.viettel.dto;

import com.viettel.scratchcard.api.PnResponse;
import java.util.Date;

/**
 *
 * @author zannami
 */
public class ViettelResponse {

    String amount;
    String cardSerial;
    String errorCode;
    String errorMessage;
    String originTransId;
    Date responseTime;
    String transId;
    String transactionId;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCardSerial() {
        return cardSerial;
    }

    public void setCardSerial(String cardSerial) {
        this.cardSerial = cardSerial;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getOriginTransId() {
        return originTransId;
    }

    public void setOriginTransId(String originTransId) {
        this.originTransId = originTransId;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }
}
