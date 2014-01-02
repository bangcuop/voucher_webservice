
package com.vss.vcard.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for comparePartner complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="comparePartner">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="companyIncome" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="companyValue" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="comparePartnerId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="originalVolume" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="partnerIncome" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="partnerValue" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="partnerVolume" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="paymentId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="vietpayVolume" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "comparePartner", propOrder = {
    "amount",
    "companyIncome",
    "companyValue",
    "comparePartnerId",
    "originalVolume",
    "partnerIncome",
    "partnerValue",
    "partnerVolume",
    "paymentId",
    "vietpayVolume"
})
public class ComparePartner {

    protected Integer amount;
    protected Integer companyIncome;
    protected Integer companyValue;
    protected Integer comparePartnerId;
    protected Integer originalVolume;
    protected Integer partnerIncome;
    protected Integer partnerValue;
    protected Integer partnerVolume;
    protected Integer paymentId;
    protected Integer vietpayVolume;

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAmount(Integer value) {
        this.amount = value;
    }

    /**
     * Gets the value of the companyIncome property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCompanyIncome() {
        return companyIncome;
    }

    /**
     * Sets the value of the companyIncome property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCompanyIncome(Integer value) {
        this.companyIncome = value;
    }

    /**
     * Gets the value of the companyValue property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCompanyValue() {
        return companyValue;
    }

    /**
     * Sets the value of the companyValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCompanyValue(Integer value) {
        this.companyValue = value;
    }

    /**
     * Gets the value of the comparePartnerId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getComparePartnerId() {
        return comparePartnerId;
    }

    /**
     * Sets the value of the comparePartnerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setComparePartnerId(Integer value) {
        this.comparePartnerId = value;
    }

    /**
     * Gets the value of the originalVolume property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOriginalVolume() {
        return originalVolume;
    }

    /**
     * Sets the value of the originalVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOriginalVolume(Integer value) {
        this.originalVolume = value;
    }

    /**
     * Gets the value of the partnerIncome property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPartnerIncome() {
        return partnerIncome;
    }

    /**
     * Sets the value of the partnerIncome property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPartnerIncome(Integer value) {
        this.partnerIncome = value;
    }

    /**
     * Gets the value of the partnerValue property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPartnerValue() {
        return partnerValue;
    }

    /**
     * Sets the value of the partnerValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPartnerValue(Integer value) {
        this.partnerValue = value;
    }

    /**
     * Gets the value of the partnerVolume property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPartnerVolume() {
        return partnerVolume;
    }

    /**
     * Sets the value of the partnerVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPartnerVolume(Integer value) {
        this.partnerVolume = value;
    }

    /**
     * Gets the value of the paymentId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPaymentId() {
        return paymentId;
    }

    /**
     * Sets the value of the paymentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPaymentId(Integer value) {
        this.paymentId = value;
    }

    /**
     * Gets the value of the vietpayVolume property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVietpayVolume() {
        return vietpayVolume;
    }

    /**
     * Sets the value of the vietpayVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVietpayVolume(Integer value) {
        this.vietpayVolume = value;
    }

}
