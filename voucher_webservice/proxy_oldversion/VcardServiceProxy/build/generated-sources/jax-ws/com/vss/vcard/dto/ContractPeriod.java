
package com.vss.vcard.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for contractPeriod complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="contractPeriod">
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
 *         &lt;element name="compareDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contractId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="contractPeriodId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="endPeriodDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="f1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="f2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="f3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="f4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="f5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="f6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="f7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="f8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="income" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="office" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="originalValue" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="paid" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="paymentDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paymentMethod" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="progress" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="proposalDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="proposalPerson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="savedValue" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="startPeriodDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statusId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="totalByWords" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="vat" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="voucherDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="voucherNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="voucherType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contractPeriod", propOrder = {
    "bankAccount0",
    "bankAccount1",
    "bankAccountHolder0",
    "bankAccountHolder1",
    "bankAddress0",
    "bankAddress1",
    "bankName0",
    "bankName1",
    "compareDate",
    "contractId",
    "contractPeriodId",
    "endPeriodDate",
    "f1",
    "f2",
    "f3",
    "f4",
    "f5",
    "f6",
    "f7",
    "f8",
    "income",
    "office",
    "originalValue",
    "paid",
    "paymentDate",
    "paymentMethod",
    "progress",
    "proposalDate",
    "proposalPerson",
    "savedValue",
    "startPeriodDate",
    "status",
    "statusId",
    "total",
    "totalByWords",
    "value",
    "vat",
    "voucherDescription",
    "voucherNumber",
    "voucherType"
})
@XmlSeeAlso({
    Payment.class
})
public class ContractPeriod {

    protected String bankAccount0;
    protected String bankAccount1;
    protected String bankAccountHolder0;
    protected String bankAccountHolder1;
    protected String bankAddress0;
    protected String bankAddress1;
    protected String bankName0;
    protected String bankName1;
    protected String compareDate;
    protected Integer contractId;
    protected Integer contractPeriodId;
    protected String endPeriodDate;
    protected String f1;
    protected String f2;
    protected String f3;
    protected String f4;
    protected String f5;
    protected String f6;
    protected String f7;
    protected String f8;
    protected Long income;
    protected String office;
    protected Long originalValue;
    protected Double paid;
    protected String paymentDate;
    protected Integer paymentMethod;
    protected Float progress;
    protected String proposalDate;
    protected String proposalPerson;
    protected Long savedValue;
    protected String startPeriodDate;
    protected String status;
    protected Integer statusId;
    protected Long total;
    protected String totalByWords;
    protected Long value;
    protected Long vat;
    protected String voucherDescription;
    protected String voucherNumber;
    protected Integer voucherType;

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
     * Gets the value of the compareDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompareDate() {
        return compareDate;
    }

    /**
     * Sets the value of the compareDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompareDate(String value) {
        this.compareDate = value;
    }

    /**
     * Gets the value of the contractId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getContractId() {
        return contractId;
    }

    /**
     * Sets the value of the contractId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setContractId(Integer value) {
        this.contractId = value;
    }

    /**
     * Gets the value of the contractPeriodId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getContractPeriodId() {
        return contractPeriodId;
    }

    /**
     * Sets the value of the contractPeriodId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setContractPeriodId(Integer value) {
        this.contractPeriodId = value;
    }

    /**
     * Gets the value of the endPeriodDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndPeriodDate() {
        return endPeriodDate;
    }

    /**
     * Sets the value of the endPeriodDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndPeriodDate(String value) {
        this.endPeriodDate = value;
    }

    /**
     * Gets the value of the f1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getF1() {
        return f1;
    }

    /**
     * Sets the value of the f1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setF1(String value) {
        this.f1 = value;
    }

    /**
     * Gets the value of the f2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getF2() {
        return f2;
    }

    /**
     * Sets the value of the f2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setF2(String value) {
        this.f2 = value;
    }

    /**
     * Gets the value of the f3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getF3() {
        return f3;
    }

    /**
     * Sets the value of the f3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setF3(String value) {
        this.f3 = value;
    }

    /**
     * Gets the value of the f4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getF4() {
        return f4;
    }

    /**
     * Sets the value of the f4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setF4(String value) {
        this.f4 = value;
    }

    /**
     * Gets the value of the f5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getF5() {
        return f5;
    }

    /**
     * Sets the value of the f5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setF5(String value) {
        this.f5 = value;
    }

    /**
     * Gets the value of the f6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getF6() {
        return f6;
    }

    /**
     * Sets the value of the f6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setF6(String value) {
        this.f6 = value;
    }

    /**
     * Gets the value of the f7 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getF7() {
        return f7;
    }

    /**
     * Sets the value of the f7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setF7(String value) {
        this.f7 = value;
    }

    /**
     * Gets the value of the f8 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getF8() {
        return f8;
    }

    /**
     * Sets the value of the f8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setF8(String value) {
        this.f8 = value;
    }

    /**
     * Gets the value of the income property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIncome() {
        return income;
    }

    /**
     * Sets the value of the income property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIncome(Long value) {
        this.income = value;
    }

    /**
     * Gets the value of the office property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOffice() {
        return office;
    }

    /**
     * Sets the value of the office property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOffice(String value) {
        this.office = value;
    }

    /**
     * Gets the value of the originalValue property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOriginalValue() {
        return originalValue;
    }

    /**
     * Sets the value of the originalValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOriginalValue(Long value) {
        this.originalValue = value;
    }

    /**
     * Gets the value of the paid property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPaid() {
        return paid;
    }

    /**
     * Sets the value of the paid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPaid(Double value) {
        this.paid = value;
    }

    /**
     * Gets the value of the paymentDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentDate() {
        return paymentDate;
    }

    /**
     * Sets the value of the paymentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentDate(String value) {
        this.paymentDate = value;
    }

    /**
     * Gets the value of the paymentMethod property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets the value of the paymentMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPaymentMethod(Integer value) {
        this.paymentMethod = value;
    }

    /**
     * Gets the value of the progress property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getProgress() {
        return progress;
    }

    /**
     * Sets the value of the progress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setProgress(Float value) {
        this.progress = value;
    }

    /**
     * Gets the value of the proposalDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProposalDate() {
        return proposalDate;
    }

    /**
     * Sets the value of the proposalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProposalDate(String value) {
        this.proposalDate = value;
    }

    /**
     * Gets the value of the proposalPerson property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProposalPerson() {
        return proposalPerson;
    }

    /**
     * Sets the value of the proposalPerson property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProposalPerson(String value) {
        this.proposalPerson = value;
    }

    /**
     * Gets the value of the savedValue property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSavedValue() {
        return savedValue;
    }

    /**
     * Sets the value of the savedValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSavedValue(Long value) {
        this.savedValue = value;
    }

    /**
     * Gets the value of the startPeriodDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartPeriodDate() {
        return startPeriodDate;
    }

    /**
     * Sets the value of the startPeriodDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartPeriodDate(String value) {
        this.startPeriodDate = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the statusId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStatusId() {
        return statusId;
    }

    /**
     * Sets the value of the statusId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStatusId(Integer value) {
        this.statusId = value;
    }

    /**
     * Gets the value of the total property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTotal(Long value) {
        this.total = value;
    }

    /**
     * Gets the value of the totalByWords property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalByWords() {
        return totalByWords;
    }

    /**
     * Sets the value of the totalByWords property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalByWords(String value) {
        this.totalByWords = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setValue(Long value) {
        this.value = value;
    }

    /**
     * Gets the value of the vat property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVat() {
        return vat;
    }

    /**
     * Sets the value of the vat property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVat(Long value) {
        this.vat = value;
    }

    /**
     * Gets the value of the voucherDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoucherDescription() {
        return voucherDescription;
    }

    /**
     * Sets the value of the voucherDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoucherDescription(String value) {
        this.voucherDescription = value;
    }

    /**
     * Gets the value of the voucherNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoucherNumber() {
        return voucherNumber;
    }

    /**
     * Sets the value of the voucherNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoucherNumber(String value) {
        this.voucherNumber = value;
    }

    /**
     * Gets the value of the voucherType property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVoucherType() {
        return voucherType;
    }

    /**
     * Sets the value of the voucherType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVoucherType(Integer value) {
        this.voucherType = value;
    }

}
