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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EstimatedOverallContractQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FeeDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NoteType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ProcurementSubTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ProcurementTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.QualityControlCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RequestedDeliveryDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RequiredFeeAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SMESuitableIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per ProcurementProjectType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ProcurementProjectType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Name" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Description" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ProcurementTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ProcurementSubTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}QualityControlCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RequiredFeeAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}FeeDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RequestedDeliveryDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}EstimatedOverallContractQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Note" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SMESuitableIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ProcurementAdditionalType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RequestedTenderTotal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}MainCommodityClassification" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AdditionalCommodityClassification" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RealizedLocation" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PlannedPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ContractExtension" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RequestForTenderLine" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcurementProjectType", propOrder = {
    "ublExtensions",
    "id",
    "name",
    "description",
    "procurementTypeCode",
    "procurementSubTypeCode",
    "qualityControlCode",
    "requiredFeeAmount",
    "feeDescription",
    "requestedDeliveryDate",
    "estimatedOverallContractQuantity",
    "note",
    "smeSuitableIndicator",
    "procurementAdditionalType",
    "requestedTenderTotal",
    "mainCommodityClassification",
    "additionalCommodityClassification",
    "realizedLocation",
    "plannedPeriod",
    "contractExtension",
    "requestForTenderLine"
})
public class ProcurementProjectType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "Name", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<NameType> name;
    @XmlElement(name = "Description", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DescriptionType> description;
    @XmlElement(name = "ProcurementTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ProcurementTypeCodeType procurementTypeCode;
    @XmlElement(name = "ProcurementSubTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ProcurementSubTypeCodeType procurementSubTypeCode;
    @XmlElement(name = "QualityControlCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected QualityControlCodeType qualityControlCode;
    @XmlElement(name = "RequiredFeeAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RequiredFeeAmountType requiredFeeAmount;
    @XmlElement(name = "FeeDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<FeeDescriptionType> feeDescription;
    @XmlElement(name = "RequestedDeliveryDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RequestedDeliveryDateType requestedDeliveryDate;
    @XmlElement(name = "EstimatedOverallContractQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected EstimatedOverallContractQuantityType estimatedOverallContractQuantity;
    @XmlElement(name = "Note", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<NoteType> note;
    @XmlElement(name = "SMESuitableIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SMESuitableIndicatorType smeSuitableIndicator;
    @XmlElement(name = "ProcurementAdditionalType")
    protected List<ProcurementAdditionalTypeType> procurementAdditionalType;
    @XmlElement(name = "RequestedTenderTotal")
    protected RequestedTenderTotalType requestedTenderTotal;
    @XmlElement(name = "MainCommodityClassification")
    protected List<CommodityClassificationType> mainCommodityClassification;
    @XmlElement(name = "AdditionalCommodityClassification")
    protected List<CommodityClassificationType> additionalCommodityClassification;
    @XmlElement(name = "RealizedLocation")
    protected List<LocationType> realizedLocation;
    @XmlElement(name = "PlannedPeriod")
    protected PeriodType plannedPeriod;
    @XmlElement(name = "ContractExtension")
    protected ContractExtensionType contractExtension;
    @XmlElement(name = "RequestForTenderLine")
    protected List<RequestForTenderLineType> requestForTenderLine;

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
     * Recupera il valore della proprietà id.
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getID() {
        return id;
    }

    /**
     * Imposta il valore della proprietà id.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setID(IDType value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the name property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getName().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link NameType }
     * 
     * 
     */
    public List<NameType> getName() {
        if (name == null) {
            name = new ArrayList<NameType>();
        }
        return this.name;
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
     * Recupera il valore della proprietà procurementTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link ProcurementTypeCodeType }
     *     
     */
    public ProcurementTypeCodeType getProcurementTypeCode() {
        return procurementTypeCode;
    }

    /**
     * Imposta il valore della proprietà procurementTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcurementTypeCodeType }
     *     
     */
    public void setProcurementTypeCode(ProcurementTypeCodeType value) {
        this.procurementTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà procurementSubTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link ProcurementSubTypeCodeType }
     *     
     */
    public ProcurementSubTypeCodeType getProcurementSubTypeCode() {
        return procurementSubTypeCode;
    }

    /**
     * Imposta il valore della proprietà procurementSubTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcurementSubTypeCodeType }
     *     
     */
    public void setProcurementSubTypeCode(ProcurementSubTypeCodeType value) {
        this.procurementSubTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà qualityControlCode.
     * 
     * @return
     *     possible object is
     *     {@link QualityControlCodeType }
     *     
     */
    public QualityControlCodeType getQualityControlCode() {
        return qualityControlCode;
    }

    /**
     * Imposta il valore della proprietà qualityControlCode.
     * 
     * @param value
     *     allowed object is
     *     {@link QualityControlCodeType }
     *     
     */
    public void setQualityControlCode(QualityControlCodeType value) {
        this.qualityControlCode = value;
    }

    /**
     * Recupera il valore della proprietà requiredFeeAmount.
     * 
     * @return
     *     possible object is
     *     {@link RequiredFeeAmountType }
     *     
     */
    public RequiredFeeAmountType getRequiredFeeAmount() {
        return requiredFeeAmount;
    }

    /**
     * Imposta il valore della proprietà requiredFeeAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link RequiredFeeAmountType }
     *     
     */
    public void setRequiredFeeAmount(RequiredFeeAmountType value) {
        this.requiredFeeAmount = value;
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
     * Recupera il valore della proprietà requestedDeliveryDate.
     * 
     * @return
     *     possible object is
     *     {@link RequestedDeliveryDateType }
     *     
     */
    public RequestedDeliveryDateType getRequestedDeliveryDate() {
        return requestedDeliveryDate;
    }

    /**
     * Imposta il valore della proprietà requestedDeliveryDate.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestedDeliveryDateType }
     *     
     */
    public void setRequestedDeliveryDate(RequestedDeliveryDateType value) {
        this.requestedDeliveryDate = value;
    }

    /**
     * Recupera il valore della proprietà estimatedOverallContractQuantity.
     * 
     * @return
     *     possible object is
     *     {@link EstimatedOverallContractQuantityType }
     *     
     */
    public EstimatedOverallContractQuantityType getEstimatedOverallContractQuantity() {
        return estimatedOverallContractQuantity;
    }

    /**
     * Imposta il valore della proprietà estimatedOverallContractQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link EstimatedOverallContractQuantityType }
     *     
     */
    public void setEstimatedOverallContractQuantity(EstimatedOverallContractQuantityType value) {
        this.estimatedOverallContractQuantity = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the note property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getNote().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link NoteType }
     * 
     * 
     */
    public List<NoteType> getNote() {
        if (note == null) {
            note = new ArrayList<NoteType>();
        }
        return this.note;
    }

    /**
     * Recupera il valore della proprietà smeSuitableIndicator.
     * 
     * @return
     *     possible object is
     *     {@link SMESuitableIndicatorType }
     *     
     */
    public SMESuitableIndicatorType getSMESuitableIndicator() {
        return smeSuitableIndicator;
    }

    /**
     * Imposta il valore della proprietà smeSuitableIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link SMESuitableIndicatorType }
     *     
     */
    public void setSMESuitableIndicator(SMESuitableIndicatorType value) {
        this.smeSuitableIndicator = value;
    }

    /**
     * Gets the value of the procurementAdditionalType property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the procurementAdditionalType property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getProcurementAdditionalType().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ProcurementAdditionalTypeType }
     * 
     * 
     */
    public List<ProcurementAdditionalTypeType> getProcurementAdditionalType() {
        if (procurementAdditionalType == null) {
            procurementAdditionalType = new ArrayList<ProcurementAdditionalTypeType>();
        }
        return this.procurementAdditionalType;
    }

    /**
     * Recupera il valore della proprietà requestedTenderTotal.
     * 
     * @return
     *     possible object is
     *     {@link RequestedTenderTotalType }
     *     
     */
    public RequestedTenderTotalType getRequestedTenderTotal() {
        return requestedTenderTotal;
    }

    /**
     * Imposta il valore della proprietà requestedTenderTotal.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestedTenderTotalType }
     *     
     */
    public void setRequestedTenderTotal(RequestedTenderTotalType value) {
        this.requestedTenderTotal = value;
    }

    /**
     * Gets the value of the mainCommodityClassification property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the mainCommodityClassification property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getMainCommodityClassification().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CommodityClassificationType }
     * 
     * 
     */
    public List<CommodityClassificationType> getMainCommodityClassification() {
        if (mainCommodityClassification == null) {
            mainCommodityClassification = new ArrayList<CommodityClassificationType>();
        }
        return this.mainCommodityClassification;
    }

    /**
     * Gets the value of the additionalCommodityClassification property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the additionalCommodityClassification property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAdditionalCommodityClassification().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CommodityClassificationType }
     * 
     * 
     */
    public List<CommodityClassificationType> getAdditionalCommodityClassification() {
        if (additionalCommodityClassification == null) {
            additionalCommodityClassification = new ArrayList<CommodityClassificationType>();
        }
        return this.additionalCommodityClassification;
    }

    /**
     * Gets the value of the realizedLocation property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the realizedLocation property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getRealizedLocation().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link LocationType }
     * 
     * 
     */
    public List<LocationType> getRealizedLocation() {
        if (realizedLocation == null) {
            realizedLocation = new ArrayList<LocationType>();
        }
        return this.realizedLocation;
    }

    /**
     * Recupera il valore della proprietà plannedPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getPlannedPeriod() {
        return plannedPeriod;
    }

    /**
     * Imposta il valore della proprietà plannedPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setPlannedPeriod(PeriodType value) {
        this.plannedPeriod = value;
    }

    /**
     * Recupera il valore della proprietà contractExtension.
     * 
     * @return
     *     possible object is
     *     {@link ContractExtensionType }
     *     
     */
    public ContractExtensionType getContractExtension() {
        return contractExtension;
    }

    /**
     * Imposta il valore della proprietà contractExtension.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractExtensionType }
     *     
     */
    public void setContractExtension(ContractExtensionType value) {
        this.contractExtension = value;
    }

    /**
     * Gets the value of the requestForTenderLine property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the requestForTenderLine property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getRequestForTenderLine().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link RequestForTenderLineType }
     * 
     * 
     */
    public List<RequestForTenderLineType> getRequestForTenderLine() {
        if (requestForTenderLine == null) {
            requestForTenderLine = new ArrayList<RequestForTenderLineType>();
        }
        return this.requestForTenderLine;
    }

}
