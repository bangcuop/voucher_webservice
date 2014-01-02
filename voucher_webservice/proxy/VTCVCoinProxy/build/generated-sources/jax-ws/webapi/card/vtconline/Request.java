
package webapi.card.vtconline;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PartnerID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RequestData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "partnerID",
    "requestData"
})
@XmlRootElement(name = "Request")
public class Request {

    @XmlElement(name = "PartnerID")
    protected int partnerID;
    @XmlElement(name = "RequestData")
    protected String requestData;

    /**
     * Gets the value of the partnerID property.
     * 
     */
    public int getPartnerID() {
        return partnerID;
    }

    /**
     * Sets the value of the partnerID property.
     * 
     */
    public void setPartnerID(int value) {
        this.partnerID = value;
    }

    /**
     * Gets the value of the requestData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestData() {
        return requestData;
    }

    /**
     * Sets the value of the requestData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestData(String value) {
        this.requestData = value;
    }

}
