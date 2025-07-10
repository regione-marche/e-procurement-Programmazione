//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AmountRateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.GuaranteeTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LiabilityAmountType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per FinancialGuaranteeType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="FinancialGuaranteeType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}GuaranteeTypeCode"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Description" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LiabilityAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AmountRate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ConstitutionPeriod" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FinancialGuaranteeType", propOrder = {
    "ublExtensions",
    "guaranteeTypeCode",
    "description",
    "liabilityAmount",
    "amountRate",
    "constitutionPeriod"
})
public class FinancialGuaranteeType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "GuaranteeTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected GuaranteeTypeCodeType guaranteeTypeCode;
    @XmlElement(name = "Description", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DescriptionType> description;
    @XmlElement(name = "LiabilityAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected LiabilityAmountType liabilityAmount;
    @XmlElement(name = "AmountRate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected AmountRateType amountRate;
    @XmlElement(name = "ConstitutionPeriod")
    protected PeriodType constitutionPeriod;

    /**
     * Recupera il valore della proprietà ublExtensions.
     * 
     * @return
     *     possible object is
     *     {@link UBLExtensionsType }
     *     
     */
    public UBLExtensionsType getUBLExtensions() {
        return ublExtensions;
    }

    /**
     * Imposta il valore della proprietà ublExtensions.
     * 
     * @param value
     *     allowed object is
     *     {@link UBLExtensionsType }
     *     
     */
    public void setUBLExtensions(UBLExtensionsType value) {
        this.ublExtensions = value;
    }

    /**
     * Recupera il valore della proprietà guaranteeTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link GuaranteeTypeCodeType }
     *     
     */
    public GuaranteeTypeCodeType getGuaranteeTypeCode() {
        return guaranteeTypeCode;
    }

    /**
     * Imposta il valore della proprietà guaranteeTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link GuaranteeTypeCodeType }
     *     
     */
    public void setGuaranteeTypeCode(GuaranteeTypeCodeType value) {
        this.guaranteeTypeCode = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the description property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DescriptionType }
     * 
     * 
     */
    public List<DescriptionType> getDescription() {
        if (description == null) {
            description = new ArrayList<DescriptionType>();
        }
        return this.description;
    }

    /**
     * Recupera il valore della proprietà liabilityAmount.
     * 
     * @return
     *     possible object is
     *     {@link LiabilityAmountType }
     *     
     */
    public LiabilityAmountType getLiabilityAmount() {
        return liabilityAmount;
    }

    /**
     * Imposta il valore della proprietà liabilityAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link LiabilityAmountType }
     *     
     */
    public void setLiabilityAmount(LiabilityAmountType value) {
        this.liabilityAmount = value;
    }

    /**
     * Recupera il valore della proprietà amountRate.
     * 
     * @return
     *     possible object is
     *     {@link AmountRateType }
     *     
     */
    public AmountRateType getAmountRate() {
        return amountRate;
    }

    /**
     * Imposta il valore della proprietà amountRate.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountRateType }
     *     
     */
    public void setAmountRate(AmountRateType value) {
        this.amountRate = value;
    }

    /**
     * Recupera il valore della proprietà constitutionPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getConstitutionPeriod() {
        return constitutionPeriod;
    }

    /**
     * Imposta il valore della proprietà constitutionPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setConstitutionPeriod(PeriodType value) {
        this.constitutionPeriod = value;
    }

}
