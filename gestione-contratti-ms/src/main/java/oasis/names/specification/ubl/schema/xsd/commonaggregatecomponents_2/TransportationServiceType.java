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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FreightRateClassCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NominationDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NominationTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PriorityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SequenceNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TariffClassCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TransportServiceCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TransportationServiceDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TransportationServiceDetailsURIType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per TransportationServiceType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TransportationServiceType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TransportServiceCode"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TariffClassCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Priority" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}FreightRateClassCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TransportationServiceDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TransportationServiceDetailsURI" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}NominationDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}NominationTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Name" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SequenceNumeric" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TransportEquipment" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SupportedTransportEquipment" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}UnsupportedTransportEquipment" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CommodityClassification" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SupportedCommodityClassification" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}UnsupportedCommodityClassification" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TotalCapacityDimension" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ShipmentStage" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TransportEvent" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ResponsibleTransportServiceProviderParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}EnvironmentalEmission" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}EstimatedDurationPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ScheduledServiceFrequency" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransportationServiceType", propOrder = {
    "ublExtensions",
    "transportServiceCode",
    "tariffClassCode",
    "priority",
    "freightRateClassCode",
    "transportationServiceDescription",
    "transportationServiceDetailsURI",
    "nominationDate",
    "nominationTime",
    "name",
    "sequenceNumeric",
    "transportEquipment",
    "supportedTransportEquipment",
    "unsupportedTransportEquipment",
    "commodityClassification",
    "supportedCommodityClassification",
    "unsupportedCommodityClassification",
    "totalCapacityDimension",
    "shipmentStage",
    "transportEvent",
    "responsibleTransportServiceProviderParty",
    "environmentalEmission",
    "estimatedDurationPeriod",
    "scheduledServiceFrequency"
})
public class TransportationServiceType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "TransportServiceCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected TransportServiceCodeType transportServiceCode;
    @XmlElement(name = "TariffClassCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TariffClassCodeType tariffClassCode;
    @XmlElement(name = "Priority", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PriorityType priority;
    @XmlElement(name = "FreightRateClassCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected FreightRateClassCodeType freightRateClassCode;
    @XmlElement(name = "TransportationServiceDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<TransportationServiceDescriptionType> transportationServiceDescription;
    @XmlElement(name = "TransportationServiceDetailsURI", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TransportationServiceDetailsURIType transportationServiceDetailsURI;
    @XmlElement(name = "NominationDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NominationDateType nominationDate;
    @XmlElement(name = "NominationTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NominationTimeType nominationTime;
    @XmlElement(name = "Name", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NameType name;
    @XmlElement(name = "SequenceNumeric", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SequenceNumericType sequenceNumeric;
    @XmlElement(name = "TransportEquipment")
    protected List<TransportEquipmentType> transportEquipment;
    @XmlElement(name = "SupportedTransportEquipment")
    protected List<TransportEquipmentType> supportedTransportEquipment;
    @XmlElement(name = "UnsupportedTransportEquipment")
    protected List<TransportEquipmentType> unsupportedTransportEquipment;
    @XmlElement(name = "CommodityClassification")
    protected List<CommodityClassificationType> commodityClassification;
    @XmlElement(name = "SupportedCommodityClassification")
    protected List<CommodityClassificationType> supportedCommodityClassification;
    @XmlElement(name = "UnsupportedCommodityClassification")
    protected List<CommodityClassificationType> unsupportedCommodityClassification;
    @XmlElement(name = "TotalCapacityDimension")
    protected DimensionType totalCapacityDimension;
    @XmlElement(name = "ShipmentStage")
    protected List<ShipmentStageType> shipmentStage;
    @XmlElement(name = "TransportEvent")
    protected List<TransportEventType> transportEvent;
    @XmlElement(name = "ResponsibleTransportServiceProviderParty")
    protected PartyType responsibleTransportServiceProviderParty;
    @XmlElement(name = "EnvironmentalEmission")
    protected List<EnvironmentalEmissionType> environmentalEmission;
    @XmlElement(name = "EstimatedDurationPeriod")
    protected PeriodType estimatedDurationPeriod;
    @XmlElement(name = "ScheduledServiceFrequency")
    protected List<ServiceFrequencyType> scheduledServiceFrequency;

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
     * Recupera il valore della proprietà transportServiceCode.
     * 
     * @return
     *     possible object is
     *     {@link TransportServiceCodeType }
     *     
     */
    public TransportServiceCodeType getTransportServiceCode() {
        return transportServiceCode;
    }

    /**
     * Imposta il valore della proprietà transportServiceCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportServiceCodeType }
     *     
     */
    public void setTransportServiceCode(TransportServiceCodeType value) {
        this.transportServiceCode = value;
    }

    /**
     * Recupera il valore della proprietà tariffClassCode.
     * 
     * @return
     *     possible object is
     *     {@link TariffClassCodeType }
     *     
     */
    public TariffClassCodeType getTariffClassCode() {
        return tariffClassCode;
    }

    /**
     * Imposta il valore della proprietà tariffClassCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TariffClassCodeType }
     *     
     */
    public void setTariffClassCode(TariffClassCodeType value) {
        this.tariffClassCode = value;
    }

    /**
     * Recupera il valore della proprietà priority.
     * 
     * @return
     *     possible object is
     *     {@link PriorityType }
     *     
     */
    public PriorityType getPriority() {
        return priority;
    }

    /**
     * Imposta il valore della proprietà priority.
     * 
     * @param value
     *     allowed object is
     *     {@link PriorityType }
     *     
     */
    public void setPriority(PriorityType value) {
        this.priority = value;
    }

    /**
     * Recupera il valore della proprietà freightRateClassCode.
     * 
     * @return
     *     possible object is
     *     {@link FreightRateClassCodeType }
     *     
     */
    public FreightRateClassCodeType getFreightRateClassCode() {
        return freightRateClassCode;
    }

    /**
     * Imposta il valore della proprietà freightRateClassCode.
     * 
     * @param value
     *     allowed object is
     *     {@link FreightRateClassCodeType }
     *     
     */
    public void setFreightRateClassCode(FreightRateClassCodeType value) {
        this.freightRateClassCode = value;
    }

    /**
     * Gets the value of the transportationServiceDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the transportationServiceDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTransportationServiceDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TransportationServiceDescriptionType }
     * 
     * 
     */
    public List<TransportationServiceDescriptionType> getTransportationServiceDescription() {
        if (transportationServiceDescription == null) {
            transportationServiceDescription = new ArrayList<TransportationServiceDescriptionType>();
        }
        return this.transportationServiceDescription;
    }

    /**
     * Recupera il valore della proprietà transportationServiceDetailsURI.
     * 
     * @return
     *     possible object is
     *     {@link TransportationServiceDetailsURIType }
     *     
     */
    public TransportationServiceDetailsURIType getTransportationServiceDetailsURI() {
        return transportationServiceDetailsURI;
    }

    /**
     * Imposta il valore della proprietà transportationServiceDetailsURI.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportationServiceDetailsURIType }
     *     
     */
    public void setTransportationServiceDetailsURI(TransportationServiceDetailsURIType value) {
        this.transportationServiceDetailsURI = value;
    }

    /**
     * Recupera il valore della proprietà nominationDate.
     * 
     * @return
     *     possible object is
     *     {@link NominationDateType }
     *     
     */
    public NominationDateType getNominationDate() {
        return nominationDate;
    }

    /**
     * Imposta il valore della proprietà nominationDate.
     * 
     * @param value
     *     allowed object is
     *     {@link NominationDateType }
     *     
     */
    public void setNominationDate(NominationDateType value) {
        this.nominationDate = value;
    }

    /**
     * Recupera il valore della proprietà nominationTime.
     * 
     * @return
     *     possible object is
     *     {@link NominationTimeType }
     *     
     */
    public NominationTimeType getNominationTime() {
        return nominationTime;
    }

    /**
     * Imposta il valore della proprietà nominationTime.
     * 
     * @param value
     *     allowed object is
     *     {@link NominationTimeType }
     *     
     */
    public void setNominationTime(NominationTimeType value) {
        this.nominationTime = value;
    }

    /**
     * Recupera il valore della proprietà name.
     * 
     * @return
     *     possible object is
     *     {@link NameType }
     *     
     */
    public NameType getName() {
        return name;
    }

    /**
     * Imposta il valore della proprietà name.
     * 
     * @param value
     *     allowed object is
     *     {@link NameType }
     *     
     */
    public void setName(NameType value) {
        this.name = value;
    }

    /**
     * Recupera il valore della proprietà sequenceNumeric.
     * 
     * @return
     *     possible object is
     *     {@link SequenceNumericType }
     *     
     */
    public SequenceNumericType getSequenceNumeric() {
        return sequenceNumeric;
    }

    /**
     * Imposta il valore della proprietà sequenceNumeric.
     * 
     * @param value
     *     allowed object is
     *     {@link SequenceNumericType }
     *     
     */
    public void setSequenceNumeric(SequenceNumericType value) {
        this.sequenceNumeric = value;
    }

    /**
     * Gets the value of the transportEquipment property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the transportEquipment property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTransportEquipment().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TransportEquipmentType }
     * 
     * 
     */
    public List<TransportEquipmentType> getTransportEquipment() {
        if (transportEquipment == null) {
            transportEquipment = new ArrayList<TransportEquipmentType>();
        }
        return this.transportEquipment;
    }

    /**
     * Gets the value of the supportedTransportEquipment property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the supportedTransportEquipment property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSupportedTransportEquipment().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TransportEquipmentType }
     * 
     * 
     */
    public List<TransportEquipmentType> getSupportedTransportEquipment() {
        if (supportedTransportEquipment == null) {
            supportedTransportEquipment = new ArrayList<TransportEquipmentType>();
        }
        return this.supportedTransportEquipment;
    }

    /**
     * Gets the value of the unsupportedTransportEquipment property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the unsupportedTransportEquipment property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getUnsupportedTransportEquipment().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TransportEquipmentType }
     * 
     * 
     */
    public List<TransportEquipmentType> getUnsupportedTransportEquipment() {
        if (unsupportedTransportEquipment == null) {
            unsupportedTransportEquipment = new ArrayList<TransportEquipmentType>();
        }
        return this.unsupportedTransportEquipment;
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
     * Gets the value of the supportedCommodityClassification property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the supportedCommodityClassification property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSupportedCommodityClassification().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CommodityClassificationType }
     * 
     * 
     */
    public List<CommodityClassificationType> getSupportedCommodityClassification() {
        if (supportedCommodityClassification == null) {
            supportedCommodityClassification = new ArrayList<CommodityClassificationType>();
        }
        return this.supportedCommodityClassification;
    }

    /**
     * Gets the value of the unsupportedCommodityClassification property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the unsupportedCommodityClassification property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getUnsupportedCommodityClassification().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CommodityClassificationType }
     * 
     * 
     */
    public List<CommodityClassificationType> getUnsupportedCommodityClassification() {
        if (unsupportedCommodityClassification == null) {
            unsupportedCommodityClassification = new ArrayList<CommodityClassificationType>();
        }
        return this.unsupportedCommodityClassification;
    }

    /**
     * Recupera il valore della proprietà totalCapacityDimension.
     * 
     * @return
     *     possible object is
     *     {@link DimensionType }
     *     
     */
    public DimensionType getTotalCapacityDimension() {
        return totalCapacityDimension;
    }

    /**
     * Imposta il valore della proprietà totalCapacityDimension.
     * 
     * @param value
     *     allowed object is
     *     {@link DimensionType }
     *     
     */
    public void setTotalCapacityDimension(DimensionType value) {
        this.totalCapacityDimension = value;
    }

    /**
     * Gets the value of the shipmentStage property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the shipmentStage property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getShipmentStage().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ShipmentStageType }
     * 
     * 
     */
    public List<ShipmentStageType> getShipmentStage() {
        if (shipmentStage == null) {
            shipmentStage = new ArrayList<ShipmentStageType>();
        }
        return this.shipmentStage;
    }

    /**
     * Gets the value of the transportEvent property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the transportEvent property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTransportEvent().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TransportEventType }
     * 
     * 
     */
    public List<TransportEventType> getTransportEvent() {
        if (transportEvent == null) {
            transportEvent = new ArrayList<TransportEventType>();
        }
        return this.transportEvent;
    }

    /**
     * Recupera il valore della proprietà responsibleTransportServiceProviderParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getResponsibleTransportServiceProviderParty() {
        return responsibleTransportServiceProviderParty;
    }

    /**
     * Imposta il valore della proprietà responsibleTransportServiceProviderParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setResponsibleTransportServiceProviderParty(PartyType value) {
        this.responsibleTransportServiceProviderParty = value;
    }

    /**
     * Gets the value of the environmentalEmission property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the environmentalEmission property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getEnvironmentalEmission().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link EnvironmentalEmissionType }
     * 
     * 
     */
    public List<EnvironmentalEmissionType> getEnvironmentalEmission() {
        if (environmentalEmission == null) {
            environmentalEmission = new ArrayList<EnvironmentalEmissionType>();
        }
        return this.environmentalEmission;
    }

    /**
     * Recupera il valore della proprietà estimatedDurationPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getEstimatedDurationPeriod() {
        return estimatedDurationPeriod;
    }

    /**
     * Imposta il valore della proprietà estimatedDurationPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setEstimatedDurationPeriod(PeriodType value) {
        this.estimatedDurationPeriod = value;
    }

    /**
     * Gets the value of the scheduledServiceFrequency property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the scheduledServiceFrequency property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getScheduledServiceFrequency().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceFrequencyType }
     * 
     * 
     */
    public List<ServiceFrequencyType> getScheduledServiceFrequency() {
        if (scheduledServiceFrequency == null) {
            scheduledServiceFrequency = new ArrayList<ServiceFrequencyType>();
        }
        return this.scheduledServiceFrequency;
    }

}
