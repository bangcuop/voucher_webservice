package com.vss.cardservice.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author zannami
 * 
 *         Jul 13, 2011 2:41:53 PM
 */
public class Partner implements Serializable {

    String partnerID;
    String partnerCode;
    String partnerName;
    String secretKey;
    Date createDate;
    String password;
    String isLock;
    String notes;
    String validIps;
    String mailAddress;
    String phone;
    String KT_DT;
    String KT_Email;
    String KT_HoTen;
    String KD_DT;
    String KD_Email;
    String KD_HoTen;
    String DS_DT;
    String DS_Email;
    String DS_HoTen;
    int failedCount;
    long startLockTime;

    public long getStartLockTime() {
        return startLockTime;
    }

    public void setStartLockTime(long startLockTime) {
        this.startLockTime = startLockTime;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }

    public String getDS_DT() {
        return DS_DT;
    }

    public void setDS_DT(String DS_DT) {
        this.DS_DT = DS_DT;
    }

    public String getDS_Email() {
        return DS_Email;
    }

    public void setDS_Email(String DS_Email) {
        this.DS_Email = DS_Email;
    }

    public String getDS_HoTen() {
        return DS_HoTen;
    }

    public void setDS_HoTen(String DS_HoTen) {
        this.DS_HoTen = DS_HoTen;
    }

    public String getKD_DT() {
        return KD_DT;
    }

    public void setKD_DT(String KD_DT) {
        this.KD_DT = KD_DT;
    }

    public String getKD_Email() {
        return KD_Email;
    }

    public void setKD_Email(String KD_Email) {
        this.KD_Email = KD_Email;
    }

    public String getKD_HoTen() {
        return KD_HoTen;
    }

    public void setKD_HoTen(String KD_HoTen) {
        this.KD_HoTen = KD_HoTen;
    }

    public String getKT_DT() {
        return KT_DT;
    }

    public void setKT_DT(String KT_DT) {
        this.KT_DT = KT_DT;
    }

    public String getKT_Email() {
        return KT_Email;
    }

    public void setKT_Email(String KT_Email) {
        this.KT_Email = KT_Email;
    }

    public String getKT_HoTen() {
        return KT_HoTen;
    }

    public void setKT_HoTen(String KT_HoTen) {
        this.KT_HoTen = KT_HoTen;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPartnerID() {
        return partnerID;
    }

    public void setPartnerID(String partnerID) {
        this.partnerID = partnerID;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getValidIps() {
        return validIps;
    }

    public void setValidIps(String validIps) {
        this.validIps = validIps;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
}
