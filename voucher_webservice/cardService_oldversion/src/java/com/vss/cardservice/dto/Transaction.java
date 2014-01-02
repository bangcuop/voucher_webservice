package com.vss.cardservice.dto;

import com.vss.cardservice.service.util.ServiceUtil;
import com.vss.cardservice.service.util.WebParameter;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author zannami
 * 
 *         Jul 13, 2011 2:46:11 PM
 */
public class Transaction implements Serializable {

    public static String CREATE_TRANS = "1";
    public static String INVALID_TRANS = "2";
    public static String VALID_TRANS = "3";
    public static String VALID_UPDATE = "4";
    public static String INVALID_UPDATE = "5";
    String transactionId;
    String partnerId;
    String cardId;
    String cardCode;
    String transRefId;
    Date requestTime;
    Date responseTime;
    String responseStatus;
    String responseDescription;
    String amount;
    String checkCardResponse;
    String useCardResponse;
    String issuer;
    String responseToPartner;
    String cardSerial;
    String telcoTransRefId;
    String accountId;
    String requestIp;
    String status;
    String partnerCode;
    Integer providerId;
    boolean telcoResponse = false;

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public boolean isTelcoResponse() {
        return telcoResponse;
    }

    public void setTelcoResponse(boolean telcoResponse) {
        this.telcoResponse = telcoResponse;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCardSerial() {
        return cardSerial;
    }

    public void setCardSerial(String cardSerial) {
        this.cardSerial = cardSerial;
    }

    public String getTelcoTransRefId() {
        return telcoTransRefId;
    }

    public void setTelcoTransRefId(String telcoTransRefId) {
        this.telcoTransRefId = telcoTransRefId;
    }

    public void setResponseToPartner(String responseToPartner) {
        this.responseToPartner = responseToPartner;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getCheckCardResponse() {
        return checkCardResponse;
    }

    public void setCheckCardResponse(String checkCardResponse) {
        this.checkCardResponse = checkCardResponse;
    }

    public String getUseCardResponse() {
        return useCardResponse;
    }

    public String getResponseToPartner(boolean getTransactionDetail) {
        if (!getTransactionDetail) {
            if (getResponseStatus() != null && getResponseStatus().equals(WebParameter.GIAO_DICH_THANH_CONG.split("\\|")[0])) {
                return getResponseStatus() + "|" + getResponseDescription() + "|" + getAmount();
            } else {
                return getResponseStatus() + "|" + getResponseDescription();
            }
        } else {
            return (getResponseStatus() == null ? "26" : getResponseStatus()) + "|" +
                    (getTransRefId() == null ? "" : getTransRefId()) + "|" +
                    (getCardCode() == null ? "" : getCardCode()) + "|" +
                    (getAmount() == null ? "0" : getAmount());
        }
    }

    public void setUseCardResponse(String useCardResponse) {
        this.useCardResponse = useCardResponse;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getTransRefId() {
        return transRefId;
    }

    public void setTransRefId(String transRefId) {
        this.transRefId = transRefId;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseDescription() {
        if (responseStatus != null && !responseStatus.isEmpty()) {
            try {
                responseDescription = ServiceUtil.CARD_SERVICE.getString(responseStatus);
            } catch (Exception e) {
                responseDescription = "";
            }
        }
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
