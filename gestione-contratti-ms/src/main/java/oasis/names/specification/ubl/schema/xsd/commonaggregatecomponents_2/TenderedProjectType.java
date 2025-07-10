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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FeeAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FeeDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TenderEnvelopeIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TenderEnvelopeTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.VariantIDType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per TenderedProjectType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TenderedProjectType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}VariantID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}FeeAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}FeeDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TenderEnvelopeID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TenderEnvelopeTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AdditionalFee" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ProcurementProjectLot" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}EvidenceDocumentReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TaxTotal" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}LegalMonetaryTotal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TenderLine" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AwardingCriterionResponse" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TenderedProjectType", propOrder = {
    "ublExtensions",
    "variantID",
    "feeAmount",
    "feeDescription",
    "tenderEnvelopeID",
    "tenderEnvelopeTypeCode",
    "additionalFee",
    "procurementProjectLot",
    "evidenceDocumentReference",
    "taxTotal",
    "legalMonetaryTotal",
    "tenderLine",
    "awardingCriterionResponse"
})
public class TenderedProjectType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "VariantID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected VariantIDType variantID;
    @XmlElement(name = "FeeAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected FeeAmountType feeAmount;
    @XmlElement(name = "FeeDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<FeeDescriptionType> feeDescription;
    @XmlElement(name = "TenderEnvelopeID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TenderEnvelopeIDType tenderEnvelopeID;
    @XmlElement(name = "TenderEnvelopeTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TenderEnvelopeTypeCodeType tenderEnvelopeTypeCode;
    @XmlElement(name = "AdditionalFee")
    protected List<FeeType> additionalFee;
    @XmlElement(name = "ProcurementProjectLot")
    protected List<ProcurementProjectLotType> procurementProjectLot;
    @XmlElement(name = "EvidenceDocumentReference")
    protected List<DocumentReferenceType> evidenceDocumentReference;
    @XmlElement(name = "TaxTotal")
    protected List<TaxTotalType> taxTotal;
    @XmlElement(name = "LegalMonetaryTotal")
    protected MonetaryTotalType legalMonetaryTotal;
    @XmlElement(name = "TenderLine")
    protected List<TenderLineType> tenderLine;
    @XmlElement(name = "AwardingCriterionResponse")
    protected List<AwardingCriterionResponseType> awardingCriterionResponse;

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
     * Recupera il valore della proprietà variantID.
     * 
     * @return
     *     possible object is
     *     {@link VariantIDType }
     *     
     */
    public VariantIDType getVariantID() {
        return variantID;
    }

    /**
     * Imposta il valore della proprietà variantID.
     * 
     * @param value
     *     allowed object is
     *     {@link VariantIDType }
     *     
     */
    public void setVariantID(VariantIDType value) {
        this.variantID = value;
    }

    /**
     * Recupera il valore della proprietà feeAmount.
     * 
     * @return
     *     possible object is
     *     {@link FeeAmountType }
     *     
     */
    public FeeAmountType getFeeAmount() {
        return feeAmount;
    }

    /**
     * Imposta il valore della proprietà feeAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link FeeAmountType }
     *     
     */
    public void setFeeAmount(FeeAmountType value) {
        this.feeAmount = value;
    }

    /**
     * Gets the value of the feeDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the feeDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getFeeDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link FeeDescriptionType }
     * 
     * 
     */
    public List<FeeDescriptionType> getFeeDescription() {
        if (feeDescription == null) {
            feeDescription = new ArrayList<FeeDescriptionType>();
        }
        return this.feeDescription;
    }

    /**
     * Recupera il valore della proprietà tenderEnvelopeID.
     * 
     * @return
     *     possible object is
     *     {@link TenderEnvelopeIDType }
     *     
     */
    public TenderEnvelopeIDType getTenderEnvelopeID() {
        return tenderEnvelopeID;
    }

    /**
     * Imposta il valore della proprietà tenderEnvelopeID.
     * 
     * @param value
     *     allowed object is
     *     {@link TenderEnvelopeIDType }
     *     
     */
    public void setTenderEnvelopeID(TenderEnvelopeIDType value) {
        this.tenderEnvelopeID = value;
    }

    /**
     * Recupera il valore della proprietà tenderEnvelopeTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link TenderEnvelopeTypeCodeType }
     *     
     */
    public TenderEnvelopeTypeCodeType getTenderEnvelopeTypeCode() {
        return tenderEnvelopeTypeCode;
    }

    /**
     * Imposta il valore della proprietà tenderEnvelopeTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TenderEnvelopeTypeCodeType }
     *     
     */
    public void setTenderEnvelopeTypeCode(TenderEnvelopeTypeCodeType value) {
        this.tenderEnvelopeTypeCode = value;
    }

    /**
     * Gets the value of the additionalFee property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the additionalFee property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAdditionalFee().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link FeeType }
     * 
     * 
     */
    public List<FeeType> getAdditionalFee() {
        if (additionalFee == null) {
            additionalFee = new ArrayList<FeeType>();
        }
        return this.additionalFee;
    }

    /**
     * Gets the value of the procurementProjectLot property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the procurementProjectLot property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getProcurementProjectLot().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ProcurementProjectLotType }
     * 
     * 
     */
    public List<ProcurementProjectLotType> getProcurementProjectLot() {
        if (procurementProjectLot == null) {
            procurementProjectLot = new ArrayList<ProcurementProjectLotType>();
        }
        return this.procurementProjectLot;
    }

    /**
     * Gets the value of the evidenceDocumentReference property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the evidenceDocumentReference property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getEvidenceDocumentReference().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    public List<DocumentReferenceType> getEvidenceDocumentReference() {
        if (evidenceDocumentReference == null) {
            evidenceDocumentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.evidenceDocumentReference;
    }

    /**
     * Gets the value of the taxTotal property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the taxTotal property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTaxTotal().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TaxTotalType }
     * 
     * 
     */
    public List<TaxTotalType> getTaxTotal() {
        if (taxTotal == null) {
            taxTotal = new ArrayList<TaxTotalType>();
        }
        return this.taxTotal;
    }

    /**
     * Recupera il valore della proprietà legalMonetaryTotal.
     * 
     * @return
     *     possible object is
     *     {@link MonetaryTotalType }
     *     
     */
    public MonetaryTotalType getLegalMonetaryTotal() {
        return legalMonetaryTotal;
    }

    /**
     * Imposta il valore della proprietà legalMonetaryTotal.
     * 
     * @param value
     *     allowed object is
     *     {@link MonetaryTotalType }
     *     
     */
    public void setLegalMonetaryTotal(MonetaryTotalType value) {
        this.legalMonetaryTotal = value;
    }

    /**
     * Gets the value of the tenderLine property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the tenderLine property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTenderLine().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TenderLineType }
     * 
     * 
     */
    public List<TenderLineType> getTenderLine() {
        if (tenderLine == null) {
            tenderLine = new ArrayList<TenderLineType>();
        }
        return this.tenderLine;
    }

    /**
     * Gets the value of the awardingCriterionResponse property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the awardingCriterionResponse property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAwardingCriterionResponse().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AwardingCriterionResponseType }
     * 
     * 
     */
    public List<AwardingCriterionResponseType> getAwardingCriterionResponse() {
        if (awardingCriterionResponse == null) {
            awardingCriterionResponse = new ArrayList<AwardingCriterionResponseType>();
        }
        return this.awardingCriterionResponse;
    }

}
