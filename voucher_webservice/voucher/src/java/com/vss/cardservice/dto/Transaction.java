package com.vss.cardservice.dto;

import java.util.Date;

/**
 *
 * @author zannami
 *
 * Jul 13, 2011 2:46:11 PM
 */
public class Transaction {

    private Integer transactionId;
    private Integer partnerId;
    private String cardId;
    private String cardCode;
    private String transRefId;
    private Date requestTime;
    private Date responseTime;
    private String responseStatus;
    private String responseDescription;
    private String amount;
    private String checkCardResponse;
    private String useCardResponse;
    private String issuer;
    private Integer issuerId;
    private String responseToPartner;
    private String cardSerial;
    private String telcoTransRefId;
    private String accountId;
    private String requestIp;
    private String status;
    private String partnerCode;
    private Integer providerId;
    private String serverIp;
    private String serverCode;
    private String companyCode;

    public String getServerCode() {
        return serverCode;
    }

    public void setServerCode(String serverCode) {
        this.serverCode = serverCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public Integer getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(Integer issuerId) {
        this.issuerId = issuerId;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardSerial() {
        return cardSerial;
    }

    public void setCardSerial(String cardSerial) {
        this.cardSerial = cardSerial;
    }

    public String getCheckCardResponse() {
        return checkCardResponse;
    }

    public void setCheckCardResponse(String checkCardResponse) {
        this.checkCardResponse = checkCardResponse;
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

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public String getResponseToPartner() {
        return responseToPartner;
    }

    public void setResponseToPartner(String responseToPartner) {
        this.responseToPartner = responseToPartner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelcoTransRefId() {
        return telcoTransRefId;
    }

    public void setTelcoTransRefId(String telcoTransRefId) {
        this.telcoTransRefId = telcoTransRefId;
    }

    public String getTransRefId() {
        return transRefId;
    }

    public void setTransRefId(String transRefId) {
        this.transRefId = transRefId;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getUseCardResponse() {
        return useCardResponse;
    }

    public void setUseCardResponse(String useCardResponse) {
        this.useCardResponse = useCardResponse;
    }
}
