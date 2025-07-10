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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CriterionTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EvaluationMethodTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FulfilmentIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FulfilmentIndicatorTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.WeightNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.WeightingConsiderationDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per TenderingCriterionType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TenderingCriterionType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CriterionTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Name" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Description" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}WeightNumeric" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}FulfilmentIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}FulfilmentIndicatorTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}EvaluationMethodTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}WeightingConsiderationDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ProcurementProjectLotReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CommodityClassification" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SubTenderingCriterion" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Legislation" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TenderingCriterionPropertyGroup" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TenderingCriterionType", propOrder = {
    "ublExtensions",
    "id",
    "criterionTypeCode",
    "name",
    "description",
    "weightNumeric",
    "fulfilmentIndicator",
    "fulfilmentIndicatorTypeCode",
    "evaluationMethodTypeCode",
    "weightingConsiderationDescription",
    "procurementProjectLotReference",
    "commodityClassification",
    "subTenderingCriterion",
    "legislation",
    "tenderingCriterionPropertyGroup"
})
public class TenderingCriterionType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "CriterionTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CriterionTypeCodeType criterionTypeCode;
    @XmlElement(name = "Name", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<NameType> name;
    @XmlElement(name = "Description", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DescriptionType> description;
    @XmlElement(name = "WeightNumeric", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected WeightNumericType weightNumeric;
    @XmlElement(name = "FulfilmentIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected FulfilmentIndicatorType fulfilmentIndicator;
    @XmlElement(name = "FulfilmentIndicatorTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected FulfilmentIndicatorTypeCodeType fulfilmentIndicatorTypeCode;
    @XmlElement(name = "EvaluationMethodTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected EvaluationMethodTypeCodeType evaluationMethodTypeCode;
    @XmlElement(name = "WeightingConsiderationDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<WeightingConsiderationDescriptionType> weightingConsiderationDescription;
    @XmlElement(name = "ProcurementProjectLotReference")
    protected List<ProcurementProjectLotReferenceType> procurementProjectLotReference;
    @XmlElement(name = "CommodityClassification")
    protected List<CommodityClassificationType> commodityClassification;
    @XmlElement(name = "SubTenderingCriterion")
    protected List<TenderingCriterionType> subTenderingCriterion;
    @XmlElement(name = "Legislation")
    protected List<LegislationType> legislation;
    @XmlElement(name = "TenderingCriterionPropertyGroup")
    protected List<TenderingCriterionPropertyGroupType> tenderingCriterionPropertyGroup;

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
     * Recupera il valore della proprietà criterionTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link CriterionTypeCodeType }
     *     
     */
    public CriterionTypeCodeType getCriterionTypeCode() {
        return criterionTypeCode;
    }

    /**
     * Imposta il valore della proprietà criterionTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link CriterionTypeCodeType }
     *     
     */
    public void setCriterionTypeCode(CriterionTypeCodeType value) {
        this.criterionTypeCode = value;
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
     * Recupera il valore della proprietà weightNumeric.
     * 
     * @return
     *     possible object is
     *     {@link WeightNumericType }
     *     
     */
    public WeightNumericType getWeightNumeric() {
        return weightNumeric;
    }

    /**
     * Imposta il valore della proprietà weightNumeric.
     * 
     * @param value
     *     allowed object is
     *     {@link WeightNumericType }
     *     
     */
    public void setWeightNumeric(WeightNumericType value) {
        this.weightNumeric = value;
    }

    /**
     * Recupera il valore della proprietà fulfilmentIndicator.
     * 
     * @return
     *     possible object is
     *     {@link FulfilmentIndicatorType }
     *     
     */
    public FulfilmentIndicatorType getFulfilmentIndicator() {
        return fulfilmentIndicator;
    }

    /**
     * Imposta il valore della proprietà fulfilmentIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link FulfilmentIndicatorType }
     *     
     */
    public void setFulfilmentIndicator(FulfilmentIndicatorType value) {
        this.fulfilmentIndicator = value;
    }

    /**
     * Recupera il valore della proprietà fulfilmentIndicatorTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link FulfilmentIndicatorTypeCodeType }
     *     
     */
    public FulfilmentIndicatorTypeCodeType getFulfilmentIndicatorTypeCode() {
        return fulfilmentIndicatorTypeCode;
    }

    /**
     * Imposta il valore della proprietà fulfilmentIndicatorTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link FulfilmentIndicatorTypeCodeType }
     *     
     */
    public void setFulfilmentIndicatorTypeCode(FulfilmentIndicatorTypeCodeType value) {
        this.fulfilmentIndicatorTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà evaluationMethodTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link EvaluationMethodTypeCodeType }
     *     
     */
    public EvaluationMethodTypeCodeType getEvaluationMethodTypeCode() {
        return evaluationMethodTypeCode;
    }

    /**
     * Imposta il valore della proprietà evaluationMethodTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link EvaluationMethodTypeCodeType }
     *     
     */
    public void setEvaluationMethodTypeCode(EvaluationMethodTypeCodeType value) {
        this.evaluationMethodTypeCode = value;
    }

    /**
     * Gets the value of the weightingConsiderationDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the weightingConsiderationDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getWeightingConsiderationDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link WeightingConsiderationDescriptionType }
     * 
     * 
     */
    public List<WeightingConsiderationDescriptionType> getWeightingConsiderationDescription() {
        if (weightingConsiderationDescription == null) {
            weightingConsiderationDescription = new ArrayList<WeightingConsiderationDescriptionType>();
        }
        return this.weightingConsiderationDescription;
    }

    /**
     * Gets the value of the procurementProjectLotReference property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the procurementProjectLotReference property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getProcurementProjectLotReference().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ProcurementProjectLotReferenceType }
     * 
     * 
     */
    public List<ProcurementProjectLotReferenceType> getProcurementProjectLotReference() {
        if (procurementProjectLotReference == null) {
            procurementProjectLotReference = new ArrayList<ProcurementProjectLotReferenceType>();
        }
        return this.procurementProjectLotReference;
    }

    /**
     * Gets the value of the commodityClassification property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the commodityClassification property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCommodityClassification().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CommodityClassificationType }
     * 
     * 
     */
    public List<CommodityClassificationType> getCommodityClassification() {
        if (commodityClassification == null) {
            commodityClassification = new ArrayList<CommodityClassificationType>();
        }
        return this.commodityClassification;
    }

    /**
     * Gets the value of the subTenderingCriterion property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the subTenderingCriterion property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSubTenderingCriterion().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TenderingCriterionType }
     * 
     * 
     */
    public List<TenderingCriterionType> getSubTenderingCriterion() {
        if (subTenderingCriterion == null) {
            subTenderingCriterion = new ArrayList<TenderingCriterionType>();
        }
        return this.subTenderingCriterion;
    }

    /**
     * Gets the value of the legislation property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the legislation property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getLegislation().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link LegislationType }
     * 
     * 
     */
    public List<LegislationType> getLegislation() {
        if (legislation == null) {
            legislation = new ArrayList<LegislationType>();
        }
        return this.legislation;
    }

    /**
     * Gets the value of the tenderingCriterionPropertyGroup property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the tenderingCriterionPropertyGroup property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTenderingCriterionPropertyGroup().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TenderingCriterionPropertyGroupType }
     * 
     * 
     */
    public List<TenderingCriterionPropertyGroupType> getTenderingCriterionPropertyGroup() {
        if (tenderingCriterionPropertyGroup == null) {
            tenderingCriterionPropertyGroup = new ArrayList<TenderingCriterionPropertyGroupType>();
        }
        return this.tenderingCriterionPropertyGroup;
    }

}
