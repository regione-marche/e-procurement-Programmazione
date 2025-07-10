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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ConditionCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IndicationIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PercentType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReferenceDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReferenceTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReliabilityPercentType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SequenceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.StatusReasonCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.StatusReasonType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TextType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per StatusType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="StatusType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ConditionCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ReferenceDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ReferenceTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Description" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}StatusReasonCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}StatusReason" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SequenceID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Text" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}IndicationIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Percent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ReliabilityPercent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SubStatus" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Condition" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatusType", propOrder = {
    "ublExtensions",
    "conditionCode",
    "referenceDate",
    "referenceTime",
    "description",
    "statusReasonCode",
    "statusReason",
    "sequenceID",
    "text",
    "indicationIndicator",
    "percent",
    "reliabilityPercent",
    "subStatus",
    "condition"
})
public class StatusType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ConditionCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ConditionCodeType conditionCode;
    @XmlElement(name = "ReferenceDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ReferenceDateType referenceDate;
    @XmlElement(name = "ReferenceTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ReferenceTimeType referenceTime;
    @XmlElement(name = "Description", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DescriptionType> description;
    @XmlElement(name = "StatusReasonCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected StatusReasonCodeType statusReasonCode;
    @XmlElement(name = "StatusReason", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<StatusReasonType> statusReason;
    @XmlElement(name = "SequenceID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SequenceIDType sequenceID;
    @XmlElement(name = "Text", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<TextType> text;
    @XmlElement(name = "IndicationIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IndicationIndicatorType indicationIndicator;
    @XmlElement(name = "Percent", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PercentType percent;
    @XmlElement(name = "ReliabilityPercent", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ReliabilityPercentType reliabilityPercent;
    @XmlElement(name = "SubStatus")
    protected List<StatusType> subStatus;
    @XmlElement(name = "Condition")
    protected List<ConditionType> condition;

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
     * Recupera il valore della proprietà conditionCode.
     * 
     * @return
     *     possible object is
     *     {@link ConditionCodeType }
     *     
     */
    public ConditionCodeType getConditionCode() {
        return conditionCode;
    }

    /**
     * Imposta il valore della proprietà conditionCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ConditionCodeType }
     *     
     */
    public void setConditionCode(ConditionCodeType value) {
        this.conditionCode = value;
    }

    /**
     * Recupera il valore della proprietà referenceDate.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceDateType }
     *     
     */
    public ReferenceDateType getReferenceDate() {
        return referenceDate;
    }

    /**
     * Imposta il valore della proprietà referenceDate.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceDateType }
     *     
     */
    public void setReferenceDate(ReferenceDateType value) {
        this.referenceDate = value;
    }

    /**
     * Recupera il valore della proprietà referenceTime.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceTimeType }
     *     
     */
    public ReferenceTimeType getReferenceTime() {
        return referenceTime;
    }

    /**
     * Imposta il valore della proprietà referenceTime.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceTimeType }
     *     
     */
    public void setReferenceTime(ReferenceTimeType value) {
        this.referenceTime = value;
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
     * Recupera il valore della proprietà statusReasonCode.
     * 
     * @return
     *     possible object is
     *     {@link StatusReasonCodeType }
     *     
     */
    public StatusReasonCodeType getStatusReasonCode() {
        return statusReasonCode;
    }

    /**
     * Imposta il valore della proprietà statusReasonCode.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusReasonCodeType }
     *     
     */
    public void setStatusReasonCode(StatusReasonCodeType value) {
        this.statusReasonCode = value;
    }

    /**
     * Gets the value of the statusReason property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the statusReason property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getStatusReason().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link StatusReasonType }
     * 
     * 
     */
    public List<StatusReasonType> getStatusReason() {
        if (statusReason == null) {
            statusReason = new ArrayList<StatusReasonType>();
        }
        return this.statusReason;
    }

    /**
     * Recupera il valore della proprietà sequenceID.
     * 
     * @return
     *     possible object is
     *     {@link SequenceIDType }
     *     
     */
    public SequenceIDType getSequenceID() {
        return sequenceID;
    }

    /**
     * Imposta il valore della proprietà sequenceID.
     * 
     * @param value
     *     allowed object is
     *     {@link SequenceIDType }
     *     
     */
    public void setSequenceID(SequenceIDType value) {
        this.sequenceID = value;
    }

    /**
     * Gets the value of the text property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the text property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getText().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TextType }
     * 
     * 
     */
    public List<TextType> getText() {
        if (text == null) {
            text = new ArrayList<TextType>();
        }
        return this.text;
    }

    /**
     * Recupera il valore della proprietà indicationIndicator.
     * 
     * @return
     *     possible object is
     *     {@link IndicationIndicatorType }
     *     
     */
    public IndicationIndicatorType getIndicationIndicator() {
        return indicationIndicator;
    }

    /**
     * Imposta il valore della proprietà indicationIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link IndicationIndicatorType }
     *     
     */
    public void setIndicationIndicator(IndicationIndicatorType value) {
        this.indicationIndicator = value;
    }

    /**
     * Recupera il valore della proprietà percent.
     * 
     * @return
     *     possible object is
     *     {@link PercentType }
     *     
     */
    public PercentType getPercent() {
        return percent;
    }

    /**
     * Imposta il valore della proprietà percent.
     * 
     * @param value
     *     allowed object is
     *     {@link PercentType }
     *     
     */
    public void setPercent(PercentType value) {
        this.percent = value;
    }

    /**
     * Recupera il valore della proprietà reliabilityPercent.
     * 
     * @return
     *     possible object is
     *     {@link ReliabilityPercentType }
     *     
     */
    public ReliabilityPercentType getReliabilityPercent() {
        return reliabilityPercent;
    }

    /**
     * Imposta il valore della proprietà reliabilityPercent.
     * 
     * @param value
     *     allowed object is
     *     {@link ReliabilityPercentType }
     *     
     */
    public void setReliabilityPercent(ReliabilityPercentType value) {
        this.reliabilityPercent = value;
    }

    /**
     * Gets the value of the subStatus property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the subStatus property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSubStatus().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link StatusType }
     * 
     * 
     */
    public List<StatusType> getSubStatus() {
        if (subStatus == null) {
            subStatus = new ArrayList<StatusType>();
        }
        return this.subStatus;
    }

    /**
     * Gets the value of the condition property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the condition property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCondition().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ConditionType }
     * 
     * 
     */
    public List<ConditionType> getCondition() {
        if (condition == null) {
            condition = new ArrayList<ConditionType>();
        }
        return this.condition;
    }

}
