
package com.vss.vcard.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for issuerPartner complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="issuerPartner">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="applySalesLimit" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="applySalesPercent" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="configSalesLimit" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="configSalesPercent" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="currentSales" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="currentSalesPercent" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="isLock" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="issuer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="issuerId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="partner" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partnerId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "issuerPartner", propOrder = {
    "applySalesLimit",
    "applySalesPercent",
    "configSalesLimit",
    "configSalesPercent",
    "currentSales",
    "currentSalesPercent",
    "isLock",
    "issuer",
    "issuerId",
    "partner",
    "partnerId"
})
public class IssuerPartner {

    protected Integer applySalesLimit;
    protected Float applySalesPercent;
    protected Integer configSalesLimit;
    protected Float configSalesPercent;
    protected Integer currentSales;
    protected Float currentSalesPercent;
    protected Boolean isLock;
    protected String issuer;
    protected Integer issuerId;
    protected String partner;
    protected Integer partnerId;

    /**
     * Gets the value of the applySalesLimit property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getApplySalesLimit() {
        return applySalesLimit;
    }

    /**
     * Sets the value of the applySalesLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setApplySalesLimit(Integer value) {
        this.applySalesLimit = value;
    }

    /**
     * Gets the value of the applySalesPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getApplySalesPercent() {
        return applySalesPercent;
    }

    /**
     * Sets the value of the applySalesPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setApplySalesPercent(Float value) {
        this.applySalesPercent = value;
    }

    /**
     * Gets the value of the configSalesLimit property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getConfigSalesLimit() {
        return configSalesLimit;
    }

    /**
     * Sets the value of the configSalesLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setConfigSalesLimit(Integer value) {
        this.configSalesLimit = value;
    }

    /**
     * Gets the value of the configSalesPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getConfigSalesPercent() {
        return configSalesPercent;
    }

    /**
     * Sets the value of the configSalesPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setConfigSalesPercent(Float value) {
        this.configSalesPercent = value;
    }

    /**
     * Gets the value of the currentSales property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCurrentSales() {
        return currentSales;
    }

    /**
     * Sets the value of the currentSales property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCurrentSales(Integer value) {
        this.currentSales = value;
    }

    /**
     * Gets the value of the currentSalesPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getCurrentSalesPercent() {
        return currentSalesPercent;
    }

    /**
     * Sets the value of the currentSalesPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setCurrentSalesPercent(Float value) {
        this.currentSalesPercent = value;
    }

    /**
     * Gets the value of the isLock property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsLock() {
        return isLock;
    }

    /**
     * Sets the value of the isLock property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsLock(Boolean value) {
        this.isLock = value;
    }

    /**
     * Gets the value of the issuer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * Sets the value of the issuer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuer(String value) {
        this.issuer = value;
    }

    /**
     * Gets the value of the issuerId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIssuerId() {
        return issuerId;
    }

    /**
     * Sets the value of the issuerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIssuerId(Integer value) {
        this.issuerId = value;
    }

    /**
     * Gets the value of the partner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartner() {
        return partner;
    }

    /**
     * Sets the value of the partner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartner(String value) {
        this.partner = value;
    }

    /**
     * Gets the value of the partnerId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPartnerId() {
        return partnerId;
    }

    /**
     * Sets the value of the partnerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPartnerId(Integer value) {
        this.partnerId = value;
    }

}
