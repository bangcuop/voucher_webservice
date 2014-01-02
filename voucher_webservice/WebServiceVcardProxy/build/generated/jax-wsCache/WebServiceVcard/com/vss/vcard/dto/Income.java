
package com.vss.vcard.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for income complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="income">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fee" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="incomeValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="originalValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "income", propOrder = {
    "fee",
    "incomeValue",
    "name",
    "originalValue"
})
@XmlSeeAlso({
    PartnerIncome.class,
    MonthIncome.class
})
public class Income {

    protected Float fee;
    protected Double incomeValue;
    protected String name;
    protected Double originalValue;

    /**
     * Gets the value of the fee property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getFee() {
        return fee;
    }

    /**
     * Sets the value of the fee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setFee(Float value) {
        this.fee = value;
    }

    /**
     * Gets the value of the incomeValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getIncomeValue() {
        return incomeValue;
    }

    /**
     * Sets the value of the incomeValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setIncomeValue(Double value) {
        this.incomeValue = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the originalValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOriginalValue() {
        return originalValue;
    }

    /**
     * Sets the value of the originalValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOriginalValue(Double value) {
        this.originalValue = value;
    }

}
