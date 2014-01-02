
package com.vss.vcard.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for partner complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="partner">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bankAccount0" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankAccount1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankAccountHolder0" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankAccountHolder1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankAddress0" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankAddress1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankName0" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankName1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessContactEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessContactName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessContactPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="className" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="compareContactEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="compareContactName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="compareContactPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isLock" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isProvider" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="jarName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mailAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partnerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partnerId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="partnerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secretKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="techniqueContactEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="techniqueContactName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="techniqueContactPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="validIps" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="website" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "partner", propOrder = {
    "bankAccount0",
    "bankAccount1",
    "bankAccountHolder0",
    "bankAccountHolder1",
    "bankAddress0",
    "bankAddress1",
    "bankName0",
    "bankName1",
    "businessContactEmail",
    "businessContactName",
    "businessContactPhone",
    "className",
    "compareContactEmail",
    "compareContactName",
    "compareContactPhone",
    "createDate",
    "isLock",
    "isProvider",
    "jarName",
    "mailAddress",
    "partnerCode",
    "partnerId",
    "partnerName",
    "password",
    "phone",
    "secretKey",
    "techniqueContactEmail",
    "techniqueContactName",
    "techniqueContactPhone",
    "validIps",
    "website"
})
public class Partner {

    protected String bankAccount0;
    protected String bankAccount1;
    protected String bankAccountHolder0;
    protected String bankAccountHolder1;
    protected String bankAddress0;
    protected String bankAddress1;
    protected String bankName0;
    protected String bankName1;
    protected String businessContactEmail;
    protected String businessContactName;
    protected String businessContactPhone;
    protected String className;
    protected String compareContactEmail;
    protected String compareContactName;
    protected String compareContactPhone;
    protected String createDate;
    protected boolean isLock;
    protected Boolean isProvider;
    protected String jarName;
    protected String mailAddress;
    protected String partnerCode;
    protected int partnerId;
    protected String partnerName;
    protected String password;
    protected String phone;
    protected String secretKey;
    protected String techniqueContactEmail;
    protected String techniqueContactName;
    protected String techniqueContactPhone;
    protected String validIps;
    protected String website;

    /**
     * Gets the value of the bankAccount0 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAccount0() {
        return bankAccount0;
    }

    /**
     * Sets the value of the bankAccount0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAccount0(String value) {
        this.bankAccount0 = value;
    }

    /**
     * Gets the value of the bankAccount1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAccount1() {
        return bankAccount1;
    }

    /**
     * Sets the value of the bankAccount1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAccount1(String value) {
        this.bankAccount1 = value;
    }

    /**
     * Gets the value of the bankAccountHolder0 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAccountHolder0() {
        return bankAccountHolder0;
    }

    /**
     * Sets the value of the bankAccountHolder0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAccountHolder0(String value) {
        this.bankAccountHolder0 = value;
    }

    /**
     * Gets the value of the bankAccountHolder1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAccountHolder1() {
        return bankAccountHolder1;
    }

    /**
     * Sets the value of the bankAccountHolder1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAccountHolder1(String value) {
        this.bankAccountHolder1 = value;
    }

    /**
     * Gets the value of the bankAddress0 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAddress0() {
        return bankAddress0;
    }

    /**
     * Sets the value of the bankAddress0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAddress0(String value) {
        this.bankAddress0 = value;
    }

    /**
     * Gets the value of the bankAddress1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAddress1() {
        return bankAddress1;
    }

    /**
     * Sets the value of the bankAddress1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAddress1(String value) {
        this.bankAddress1 = value;
    }

    /**
     * Gets the value of the bankName0 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankName0() {
        return bankName0;
    }

    /**
     * Sets the value of the bankName0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankName0(String value) {
        this.bankName0 = value;
    }

    /**
     * Gets the value of the bankName1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankName1() {
        return bankName1;
    }

    /**
     * Sets the value of the bankName1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankName1(String value) {
        this.bankName1 = value;
    }

    /**
     * Gets the value of the businessContactEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessContactEmail() {
        return businessContactEmail;
    }

    /**
     * Sets the value of the businessContactEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessContactEmail(String value) {
        this.businessContactEmail = value;
    }

    /**
     * Gets the value of the businessContactName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessContactName() {
        return businessContactName;
    }

    /**
     * Sets the value of the businessContactName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessContactName(String value) {
        this.businessContactName = value;
    }

    /**
     * Gets the value of the businessContactPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessContactPhone() {
        return businessContactPhone;
    }

    /**
     * Sets the value of the businessContactPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessContactPhone(String value) {
        this.businessContactPhone = value;
    }

    /**
     * Gets the value of the className property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets the value of the className property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassName(String value) {
        this.className = value;
    }

    /**
     * Gets the value of the compareContactEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompareContactEmail() {
        return compareContactEmail;
    }

    /**
     * Sets the value of the compareContactEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompareContactEmail(String value) {
        this.compareContactEmail = value;
    }

    /**
     * Gets the value of the compareContactName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompareContactName() {
        return compareContactName;
    }

    /**
     * Sets the value of the compareContactName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompareContactName(String value) {
        this.compareContactName = value;
    }

    /**
     * Gets the value of the compareContactPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompareContactPhone() {
        return compareContactPhone;
    }

    /**
     * Sets the value of the compareContactPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompareContactPhone(String value) {
        this.compareContactPhone = value;
    }

    /**
     * Gets the value of the createDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * Sets the value of the createDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateDate(String value) {
        this.createDate = value;
    }

    /**
     * Gets the value of the isLock property.
     * 
     */
    public boolean isIsLock() {
        return isLock;
    }

    /**
     * Sets the value of the isLock property.
     * 
     */
    public void setIsLock(boolean value) {
        this.isLock = value;
    }

    /**
     * Gets the value of the isProvider property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsProvider() {
        return isProvider;
    }

    /**
     * Sets the value of the isProvider property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsProvider(Boolean value) {
        this.isProvider = value;
    }

    /**
     * Gets the value of the jarName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJarName() {
        return jarName;
    }

    /**
     * Sets the value of the jarName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJarName(String value) {
        this.jarName = value;
    }

    /**
     * Gets the value of the mailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailAddress() {
        return mailAddress;
    }

    /**
     * Sets the value of the mailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailAddress(String value) {
        this.mailAddress = value;
    }

    /**
     * Gets the value of the partnerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerCode() {
        return partnerCode;
    }

    /**
     * Sets the value of the partnerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerCode(String value) {
        this.partnerCode = value;
    }

    /**
     * Gets the value of the partnerId property.
     * 
     */
    public int getPartnerId() {
        return partnerId;
    }

    /**
     * Sets the value of the partnerId property.
     * 
     */
    public void setPartnerId(int value) {
        this.partnerId = value;
    }

    /**
     * Gets the value of the partnerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerName() {
        return partnerName;
    }

    /**
     * Sets the value of the partnerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerName(String value) {
        this.partnerName = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the phone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * Gets the value of the secretKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * Sets the value of the secretKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecretKey(String value) {
        this.secretKey = value;
    }

    /**
     * Gets the value of the techniqueContactEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTechniqueContactEmail() {
        return techniqueContactEmail;
    }

    /**
     * Sets the value of the techniqueContactEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTechniqueContactEmail(String value) {
        this.techniqueContactEmail = value;
    }

    /**
     * Gets the value of the techniqueContactName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTechniqueContactName() {
        return techniqueContactName;
    }

    /**
     * Sets the value of the techniqueContactName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTechniqueContactName(String value) {
        this.techniqueContactName = value;
    }

    /**
     * Gets the value of the techniqueContactPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTechniqueContactPhone() {
        return techniqueContactPhone;
    }

    /**
     * Sets the value of the techniqueContactPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTechniqueContactPhone(String value) {
        this.techniqueContactPhone = value;
    }

    /**
     * Gets the value of the validIps property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidIps() {
        return validIps;
    }

    /**
     * Sets the value of the validIps property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidIps(String value) {
        this.validIps = value;
    }

    /**
     * Gets the value of the website property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Sets the value of the website property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebsite(String value) {
        this.website = value;
    }

}
