
package com.vss.vcard.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getIssuerPartnerList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getIssuerPartnerList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="viewType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getIssuerPartnerList", propOrder = {
    "viewType"
})
public class GetIssuerPartnerList {

    protected int viewType;

    /**
     * Gets the value of the viewType property.
     * 
     */
    public int getViewType() {
        return viewType;
    }

    /**
     * Sets the value of the viewType property.
     * 
     */
    public void setViewType(int value) {
        this.viewType = value;
    }

}
