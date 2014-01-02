
package com.vss.vcard.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for statisticDay complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="statisticDay">
 *   &lt;complexContent>
 *     &lt;extension base="{http://com/vss/vcard/dto}cardVolume">
 *       &lt;sequence>
 *         &lt;element name="agentId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cardId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="issuerId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="issuerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loinhuan" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="margin" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="partnerId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="phaithu" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="phaitra" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="providerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="providerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tileban" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="tilemua" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "statisticDay", propOrder = {
    "agentId",
    "agentName",
    "cardId",
    "issuerId",
    "issuerName",
    "loinhuan",
    "margin",
    "partnerId",
    "phaithu",
    "phaitra",
    "providerId",
    "providerName",
    "tileban",
    "tilemua"
})
public class StatisticDay
    extends CardVolume
{

    protected String agentId;
    protected String agentName;
    protected Integer cardId;
    protected Integer issuerId;
    protected String issuerName;
    protected Long loinhuan;
    protected Double margin;
    protected Integer partnerId;
    protected Long phaithu;
    protected Long phaitra;
    protected String providerId;
    protected String providerName;
    protected Float tileban;
    protected Float tilemua;

    /**
     * Gets the value of the agentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentId() {
        return agentId;
    }

    /**
     * Sets the value of the agentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentId(String value) {
        this.agentId = value;
    }

    /**
     * Gets the value of the agentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * Sets the value of the agentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentName(String value) {
        this.agentName = value;
    }

    /**
     * Gets the value of the cardId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCardId() {
        return cardId;
    }

    /**
     * Sets the value of the cardId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCardId(Integer value) {
        this.cardId = value;
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
     * Gets the value of the issuerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuerName() {
        return issuerName;
    }

    /**
     * Sets the value of the issuerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuerName(String value) {
        this.issuerName = value;
    }

    /**
     * Gets the value of the loinhuan property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLoinhuan() {
        return loinhuan;
    }

    /**
     * Sets the value of the loinhuan property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLoinhuan(Long value) {
        this.loinhuan = value;
    }

    /**
     * Gets the value of the margin property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMargin() {
        return margin;
    }

    /**
     * Sets the value of the margin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMargin(Double value) {
        this.margin = value;
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

    /**
     * Gets the value of the phaithu property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPhaithu() {
        return phaithu;
    }

    /**
     * Sets the value of the phaithu property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPhaithu(Long value) {
        this.phaithu = value;
    }

    /**
     * Gets the value of the phaitra property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPhaitra() {
        return phaitra;
    }

    /**
     * Sets the value of the phaitra property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPhaitra(Long value) {
        this.phaitra = value;
    }

    /**
     * Gets the value of the providerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * Sets the value of the providerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderId(String value) {
        this.providerId = value;
    }

    /**
     * Gets the value of the providerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderName() {
        return providerName;
    }

    /**
     * Sets the value of the providerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderName(String value) {
        this.providerName = value;
    }

    /**
     * Gets the value of the tileban property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getTileban() {
        return tileban;
    }

    /**
     * Sets the value of the tileban property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setTileban(Float value) {
        this.tileban = value;
    }

    /**
     * Gets the value of the tilemua property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getTilemua() {
        return tilemua;
    }

    /**
     * Sets the value of the tilemua property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setTilemua(Float value) {
        this.tilemua = value;
    }

}
