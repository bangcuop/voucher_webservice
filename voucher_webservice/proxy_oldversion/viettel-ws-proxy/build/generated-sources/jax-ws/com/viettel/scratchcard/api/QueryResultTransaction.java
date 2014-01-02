
package com.viettel.scratchcard.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for queryResultTransaction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="queryResultTransaction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="parnerID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="passphase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="originTransId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryResultTransaction", propOrder = {
    "parnerID",
    "passphase",
    "originTransId",
    "transId"
})
public class QueryResultTransaction {

    protected String parnerID;
    protected String passphase;
    protected String originTransId;
    protected String transId;

    /**
     * Gets the value of the parnerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParnerID() {
        return parnerID;
    }

    /**
     * Sets the value of the parnerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParnerID(String value) {
        this.parnerID = value;
    }

    /**
     * Gets the value of the passphase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassphase() {
        return passphase;
    }

    /**
     * Sets the value of the passphase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassphase(String value) {
        this.passphase = value;
    }

    /**
     * Gets the value of the originTransId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginTransId() {
        return originTransId;
    }

    /**
     * Sets the value of the originTransId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginTransId(String value) {
        this.originTransId = value;
    }

    /**
     * Gets the value of the transId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransId() {
        return transId;
    }

    /**
     * Sets the value of the transId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransId(String value) {
        this.transId = value;
    }

}
