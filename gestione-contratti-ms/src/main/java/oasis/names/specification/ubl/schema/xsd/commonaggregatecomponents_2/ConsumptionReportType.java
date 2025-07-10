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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BasicConsumedQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ConsumersEnergyLevelCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ConsumersEnergyLevelType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ConsumptionTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ConsumptionTypeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HeatingTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HeatingTypeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResidenceTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResidenceTypeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResidentOccupantsNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalConsumedQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per ConsumptionReportType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ConsumptionReportType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ConsumptionType" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ConsumptionTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Description" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TotalConsumedQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}BasicConsumedQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ResidentOccupantsNumeric" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ConsumersEnergyLevelCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ConsumersEnergyLevel" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ResidenceType" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ResidenceTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HeatingType" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HeatingTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Period" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}GuidanceDocumentReference" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DocumentReference" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ConsumptionReportReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ConsumptionHistory" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsumptionReportType", propOrder = {
    "ublExtensions",
    "id",
    "consumptionType",
    "consumptionTypeCode",
    "description",
    "totalConsumedQuantity",
    "basicConsumedQuantity",
    "residentOccupantsNumeric",
    "consumersEnergyLevelCode",
    "consumersEnergyLevel",
    "residenceType",
    "residenceTypeCode",
    "heatingType",
    "heatingTypeCode",
    "period",
    "guidanceDocumentReference",
    "documentReference",
    "consumptionReportReference",
    "consumptionHistory"
})
public class ConsumptionReportType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected IDType id;
    @XmlElement(name = "ConsumptionType", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ConsumptionTypeType consumptionType;
    @XmlElement(name = "ConsumptionTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ConsumptionTypeCodeType consumptionTypeCode;
    @XmlElement(name = "Description", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DescriptionType> description;
    @XmlElement(name = "TotalConsumedQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TotalConsumedQuantityType totalConsumedQuantity;
    @XmlElement(name = "BasicConsumedQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected BasicConsumedQuantityType basicConsumedQuantity;
    @XmlElement(name = "ResidentOccupantsNumeric", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ResidentOccupantsNumericType residentOccupantsNumeric;
    @XmlElement(name = "ConsumersEnergyLevelCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ConsumersEnergyLevelCodeType consumersEnergyLevelCode;
    @XmlElement(name = "ConsumersEnergyLevel", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ConsumersEnergyLevelType consumersEnergyLevel;
    @XmlElement(name = "ResidenceType", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ResidenceTypeType residenceType;
    @XmlElement(name = "ResidenceTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ResidenceTypeCodeType residenceTypeCode;
    @XmlElement(name = "HeatingType", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected HeatingTypeType heatingType;
    @XmlElement(name = "HeatingTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected HeatingTypeCodeType heatingTypeCode;
    @XmlElement(name = "Period")
    protected PeriodType period;
    @XmlElement(name = "GuidanceDocumentReference")
    protected DocumentReferenceType guidanceDocumentReference;
    @XmlElement(name = "DocumentReference")
    protected DocumentReferenceType documentReference;
    @XmlElement(name = "ConsumptionReportReference")
    protected List<ConsumptionReportReferenceType> consumptionReportReference;
    @XmlElement(name = "ConsumptionHistory")
    protected List<ConsumptionHistoryType> consumptionHistory;

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
     * Recupera il valore della proprietà consumptionType.
     * 
     * @return
     *     possible object is
     *     {@link ConsumptionTypeType }
     *     
     */
    public ConsumptionTypeType getConsumptionType() {
        return consumptionType;
    }

    /**
     * Imposta il valore della proprietà consumptionType.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsumptionTypeType }
     *     
     */
    public void setConsumptionType(ConsumptionTypeType value) {
        this.consumptionType = value;
    }

    /**
     * Recupera il valore della proprietà consumptionTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link ConsumptionTypeCodeType }
     *     
     */
    public ConsumptionTypeCodeType getConsumptionTypeCode() {
        return consumptionTypeCode;
    }

    /**
     * Imposta il valore della proprietà consumptionTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsumptionTypeCodeType }
     *     
     */
    public void setConsumptionTypeCode(ConsumptionTypeCodeType value) {
        this.consumptionTypeCode = value;
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
     * Recupera il valore della proprietà totalConsumedQuantity.
     * 
     * @return
     *     possible object is
     *     {@link TotalConsumedQuantityType }
     *     
     */
    public TotalConsumedQuantityType getTotalConsumedQuantity() {
        return totalConsumedQuantity;
    }

    /**
     * Imposta il valore della proprietà totalConsumedQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalConsumedQuantityType }
     *     
     */
    public void setTotalConsumedQuantity(TotalConsumedQuantityType value) {
        this.totalConsumedQuantity = value;
    }

    /**
     * Recupera il valore della proprietà basicConsumedQuantity.
     * 
     * @return
     *     possible object is
     *     {@link BasicConsumedQuantityType }
     *     
     */
    public BasicConsumedQuantityType getBasicConsumedQuantity() {
        return basicConsumedQuantity;
    }

    /**
     * Imposta il valore della proprietà basicConsumedQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link BasicConsumedQuantityType }
     *     
     */
    public void setBasicConsumedQuantity(BasicConsumedQuantityType value) {
        this.basicConsumedQuantity = value;
    }

    /**
     * Recupera il valore della proprietà residentOccupantsNumeric.
     * 
     * @return
     *     possible object is
     *     {@link ResidentOccupantsNumericType }
     *     
     */
    public ResidentOccupantsNumericType getResidentOccupantsNumeric() {
        return residentOccupantsNumeric;
    }

    /**
     * Imposta il valore della proprietà residentOccupantsNumeric.
     * 
     * @param value
     *     allowed object is
     *     {@link ResidentOccupantsNumericType }
     *     
     */
    public void setResidentOccupantsNumeric(ResidentOccupantsNumericType value) {
        this.residentOccupantsNumeric = value;
    }

    /**
     * Recupera il valore della proprietà consumersEnergyLevelCode.
     * 
     * @return
     *     possible object is
     *     {@link ConsumersEnergyLevelCodeType }
     *     
     */
    public ConsumersEnergyLevelCodeType getConsumersEnergyLevelCode() {
        return consumersEnergyLevelCode;
    }

    /**
     * Imposta il valore della proprietà consumersEnergyLevelCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsumersEnergyLevelCodeType }
     *     
     */
    public void setConsumersEnergyLevelCode(ConsumersEnergyLevelCodeType value) {
        this.consumersEnergyLevelCode = value;
    }

    /**
     * Recupera il valore della proprietà consumersEnergyLevel.
     * 
     * @return
     *     possible object is
     *     {@link ConsumersEnergyLevelType }
     *     
     */
    public ConsumersEnergyLevelType getConsumersEnergyLevel() {
        return consumersEnergyLevel;
    }

    /**
     * Imposta il valore della proprietà consumersEnergyLevel.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsumersEnergyLevelType }
     *     
     */
    public void setConsumersEnergyLevel(ConsumersEnergyLevelType value) {
        this.consumersEnergyLevel = value;
    }

    /**
     * Recupera il valore della proprietà residenceType.
     * 
     * @return
     *     possible object is
     *     {@link ResidenceTypeType }
     *     
     */
    public ResidenceTypeType getResidenceType() {
        return residenceType;
    }

    /**
     * Imposta il valore della proprietà residenceType.
     * 
     * @param value
     *     allowed object is
     *     {@link ResidenceTypeType }
     *     
     */
    public void setResidenceType(ResidenceTypeType value) {
        this.residenceType = value;
    }

    /**
     * Recupera il valore della proprietà residenceTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link ResidenceTypeCodeType }
     *     
     */
    public ResidenceTypeCodeType getResidenceTypeCode() {
        return residenceTypeCode;
    }

    /**
     * Imposta il valore della proprietà residenceTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ResidenceTypeCodeType }
     *     
     */
    public void setResidenceTypeCode(ResidenceTypeCodeType value) {
        this.residenceTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà heatingType.
     * 
     * @return
     *     possible object is
     *     {@link HeatingTypeType }
     *     
     */
    public HeatingTypeType getHeatingType() {
        return heatingType;
    }

    /**
     * Imposta il valore della proprietà heatingType.
     * 
     * @param value
     *     allowed object is
     *     {@link HeatingTypeType }
     *     
     */
    public void setHeatingType(HeatingTypeType value) {
        this.heatingType = value;
    }

    /**
     * Recupera il valore della proprietà heatingTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link HeatingTypeCodeType }
     *     
     */
    public HeatingTypeCodeType getHeatingTypeCode() {
        return heatingTypeCode;
    }

    /**
     * Imposta il valore della proprietà heatingTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link HeatingTypeCodeType }
     *     
     */
    public void setHeatingTypeCode(HeatingTypeCodeType value) {
        this.heatingTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà period.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getPeriod() {
        return period;
    }

    /**
     * Imposta il valore della proprietà period.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setPeriod(PeriodType value) {
        this.period = value;
    }

    /**
     * Recupera il valore della proprietà guidanceDocumentReference.
     * 
     * @return
     *     possible object is
     *     {@link DocumentReferenceType }
     *     
     */
    public DocumentReferenceType getGuidanceDocumentReference() {
        return guidanceDocumentReference;
    }

    /**
     * Imposta il valore della proprietà guidanceDocumentReference.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentReferenceType }
     *     
     */
    public void setGuidanceDocumentReference(DocumentReferenceType value) {
        this.guidanceDocumentReference = value;
    }

    /**
     * Recupera il valore della proprietà documentReference.
     * 
     * @return
     *     possible object is
     *     {@link DocumentReferenceType }
     *     
     */
    public DocumentReferenceType getDocumentReference() {
        return documentReference;
    }

    /**
     * Imposta il valore della proprietà documentReference.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentReferenceType }
     *     
     */
    public void setDocumentReference(DocumentReferenceType value) {
        this.documentReference = value;
    }

    /**
     * Gets the value of the consumptionReportReference property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the consumptionReportReference property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getConsumptionReportReference().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ConsumptionReportReferenceType }
     * 
     * 
     */
    public List<ConsumptionReportReferenceType> getConsumptionReportReference() {
        if (consumptionReportReference == null) {
            consumptionReportReference = new ArrayList<ConsumptionReportReferenceType>();
        }
        return this.consumptionReportReference;
    }

    /**
     * Gets the value of the consumptionHistory property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the consumptionHistory property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getConsumptionHistory().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ConsumptionHistoryType }
     * 
     * 
     */
    public List<ConsumptionHistoryType> getConsumptionHistory() {
        if (consumptionHistory == null) {
            consumptionHistory = new ArrayList<ConsumptionHistoryType>();
        }
        return this.consumptionHistory;
    }

}
