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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DamageRemarksType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HandlingCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HandlingInstructionsType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HazardousRiskIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ShippingMarksType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalGoodsItemQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalPackageQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TraceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TransportHandlingUnitTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per TransportHandlingUnitType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TransportHandlingUnitType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TransportHandlingUnitTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HandlingCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HandlingInstructions" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HazardousRiskIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TotalGoodsItemQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TotalPackageQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DamageRemarks" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ShippingMarks" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TraceID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}HandlingUnitDespatchLine" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ActualPackage" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ReceivedHandlingUnitReceiptLine" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TransportEquipment" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TransportMeans" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}HazardousGoodsTransit" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}MeasurementDimension" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}MinimumTemperature" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}MaximumTemperature" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}GoodsItem" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}FloorSpaceMeasurementDimension" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PalletSpaceMeasurementDimension" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ShipmentDocumentReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Status" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CustomsDeclaration" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ReferencedShipment" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Package" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransportHandlingUnitType", propOrder = {
    "ublExtensions",
    "id",
    "transportHandlingUnitTypeCode",
    "handlingCode",
    "handlingInstructions",
    "hazardousRiskIndicator",
    "totalGoodsItemQuantity",
    "totalPackageQuantity",
    "damageRemarks",
    "shippingMarks",
    "traceID",
    "handlingUnitDespatchLine",
    "actualPackage",
    "receivedHandlingUnitReceiptLine",
    "transportEquipment",
    "transportMeans",
    "hazardousGoodsTransit",
    "measurementDimension",
    "minimumTemperature",
    "maximumTemperature",
    "goodsItem",
    "floorSpaceMeasurementDimension",
    "palletSpaceMeasurementDimension",
    "shipmentDocumentReference",
    "status",
    "customsDeclaration",
    "referencedShipment",
    "_package"
})
public class TransportHandlingUnitType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "TransportHandlingUnitTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TransportHandlingUnitTypeCodeType transportHandlingUnitTypeCode;
    @XmlElement(name = "HandlingCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected HandlingCodeType handlingCode;
    @XmlElement(name = "HandlingInstructions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<HandlingInstructionsType> handlingInstructions;
    @XmlElement(name = "HazardousRiskIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected HazardousRiskIndicatorType hazardousRiskIndicator;
    @XmlElement(name = "TotalGoodsItemQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TotalGoodsItemQuantityType totalGoodsItemQuantity;
    @XmlElement(name = "TotalPackageQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TotalPackageQuantityType totalPackageQuantity;
    @XmlElement(name = "DamageRemarks", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DamageRemarksType> damageRemarks;
    @XmlElement(name = "ShippingMarks", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<ShippingMarksType> shippingMarks;
    @XmlElement(name = "TraceID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TraceIDType traceID;
    @XmlElement(name = "HandlingUnitDespatchLine")
    protected List<DespatchLineType> handlingUnitDespatchLine;
    @XmlElement(name = "ActualPackage")
    protected List<PackageType> actualPackage;
    @XmlElement(name = "ReceivedHandlingUnitReceiptLine")
    protected List<ReceiptLineType> receivedHandlingUnitReceiptLine;
    @XmlElement(name = "TransportEquipment")
    protected List<TransportEquipmentType> transportEquipment;
    @XmlElement(name = "TransportMeans")
    protected List<TransportMeansType> transportMeans;
    @XmlElement(name = "HazardousGoodsTransit")
    protected List<HazardousGoodsTransitType> hazardousGoodsTransit;
    @XmlElement(name = "MeasurementDimension")
    protected List<DimensionType> measurementDimension;
    @XmlElement(name = "MinimumTemperature")
    protected TemperatureType minimumTemperature;
    @XmlElement(name = "MaximumTemperature")
    protected TemperatureType maximumTemperature;
    @XmlElement(name = "GoodsItem")
    protected List<GoodsItemType> goodsItem;
    @XmlElement(name = "FloorSpaceMeasurementDimension")
    protected DimensionType floorSpaceMeasurementDimension;
    @XmlElement(name = "PalletSpaceMeasurementDimension")
    protected DimensionType palletSpaceMeasurementDimension;
    @XmlElement(name = "ShipmentDocumentReference")
    protected List<DocumentReferenceType> shipmentDocumentReference;
    @XmlElement(name = "Status")
    protected List<StatusType> status;
    @XmlElement(name = "CustomsDeclaration")
    protected List<CustomsDeclarationType> customsDeclaration;
    @XmlElement(name = "ReferencedShipment")
    protected List<ShipmentType> referencedShipment;
    @XmlElement(name = "Package")
    protected List<PackageType> _package;

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
     * Recupera il valore della proprietà transportHandlingUnitTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link TransportHandlingUnitTypeCodeType }
     *     
     */
    public TransportHandlingUnitTypeCodeType getTransportHandlingUnitTypeCode() {
        return transportHandlingUnitTypeCode;
    }

    /**
     * Imposta il valore della proprietà transportHandlingUnitTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportHandlingUnitTypeCodeType }
     *     
     */
    public void setTransportHandlingUnitTypeCode(TransportHandlingUnitTypeCodeType value) {
        this.transportHandlingUnitTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà handlingCode.
     * 
     * @return
     *     possible object is
     *     {@link HandlingCodeType }
     *     
     */
    public HandlingCodeType getHandlingCode() {
        return handlingCode;
    }

    /**
     * Imposta il valore della proprietà handlingCode.
     * 
     * @param value
     *     allowed object is
     *     {@link HandlingCodeType }
     *     
     */
    public void setHandlingCode(HandlingCodeType value) {
        this.handlingCode = value;
    }

    /**
     * Gets the value of the handlingInstructions property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the handlingInstructions property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getHandlingInstructions().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link HandlingInstructionsType }
     * 
     * 
     */
    public List<HandlingInstructionsType> getHandlingInstructions() {
        if (handlingInstructions == null) {
            handlingInstructions = new ArrayList<HandlingInstructionsType>();
        }
        return this.handlingInstructions;
    }

    /**
     * Recupera il valore della proprietà hazardousRiskIndicator.
     * 
     * @return
     *     possible object is
     *     {@link HazardousRiskIndicatorType }
     *     
     */
    public HazardousRiskIndicatorType getHazardousRiskIndicator() {
        return hazardousRiskIndicator;
    }

    /**
     * Imposta il valore della proprietà hazardousRiskIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link HazardousRiskIndicatorType }
     *     
     */
    public void setHazardousRiskIndicator(HazardousRiskIndicatorType value) {
        this.hazardousRiskIndicator = value;
    }

    /**
     * Recupera il valore della proprietà totalGoodsItemQuantity.
     * 
     * @return
     *     possible object is
     *     {@link TotalGoodsItemQuantityType }
     *     
     */
    public TotalGoodsItemQuantityType getTotalGoodsItemQuantity() {
        return totalGoodsItemQuantity;
    }

    /**
     * Imposta il valore della proprietà totalGoodsItemQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalGoodsItemQuantityType }
     *     
     */
    public void setTotalGoodsItemQuantity(TotalGoodsItemQuantityType value) {
        this.totalGoodsItemQuantity = value;
    }

    /**
     * Recupera il valore della proprietà totalPackageQuantity.
     * 
     * @return
     *     possible object is
     *     {@link TotalPackageQuantityType }
     *     
     */
    public TotalPackageQuantityType getTotalPackageQuantity() {
        return totalPackageQuantity;
    }

    /**
     * Imposta il valore della proprietà totalPackageQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalPackageQuantityType }
     *     
     */
    public void setTotalPackageQuantity(TotalPackageQuantityType value) {
        this.totalPackageQuantity = value;
    }

    /**
     * Gets the value of the damageRemarks property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the damageRemarks property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDamageRemarks().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DamageRemarksType }
     * 
     * 
     */
    public List<DamageRemarksType> getDamageRemarks() {
        if (damageRemarks == null) {
            damageRemarks = new ArrayList<DamageRemarksType>();
        }
        return this.damageRemarks;
    }

    /**
     * Gets the value of the shippingMarks property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the shippingMarks property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getShippingMarks().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ShippingMarksType }
     * 
     * 
     */
    public List<ShippingMarksType> getShippingMarks() {
        if (shippingMarks == null) {
            shippingMarks = new ArrayList<ShippingMarksType>();
        }
        return this.shippingMarks;
    }

    /**
     * Recupera il valore della proprietà traceID.
     * 
     * @return
     *     possible object is
     *     {@link TraceIDType }
     *     
     */
    public TraceIDType getTraceID() {
        return traceID;
    }

    /**
     * Imposta il valore della proprietà traceID.
     * 
     * @param value
     *     allowed object is
     *     {@link TraceIDType }
     *     
     */
    public void setTraceID(TraceIDType value) {
        this.traceID = value;
    }

    /**
     * Gets the value of the handlingUnitDespatchLine property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the handlingUnitDespatchLine property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getHandlingUnitDespatchLine().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DespatchLineType }
     * 
     * 
     */
    public List<DespatchLineType> getHandlingUnitDespatchLine() {
        if (handlingUnitDespatchLine == null) {
            handlingUnitDespatchLine = new ArrayList<DespatchLineType>();
        }
        return this.handlingUnitDespatchLine;
    }

    /**
     * Gets the value of the actualPackage property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the actualPackage property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getActualPackage().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PackageType }
     * 
     * 
     */
    public List<PackageType> getActualPackage() {
        if (actualPackage == null) {
            actualPackage = new ArrayList<PackageType>();
        }
        return this.actualPackage;
    }

    /**
     * Gets the value of the receivedHandlingUnitReceiptLine property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the receivedHandlingUnitReceiptLine property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getReceivedHandlingUnitReceiptLine().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ReceiptLineType }
     * 
     * 
     */
    public List<ReceiptLineType> getReceivedHandlingUnitReceiptLine() {
        if (receivedHandlingUnitReceiptLine == null) {
            receivedHandlingUnitReceiptLine = new ArrayList<ReceiptLineType>();
        }
        return this.receivedHandlingUnitReceiptLine;
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
     * Gets the value of the transportMeans property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the transportMeans property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTransportMeans().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TransportMeansType }
     * 
     * 
     */
    public List<TransportMeansType> getTransportMeans() {
        if (transportMeans == null) {
            transportMeans = new ArrayList<TransportMeansType>();
        }
        return this.transportMeans;
    }

    /**
     * Gets the value of the hazardousGoodsTransit property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the hazardousGoodsTransit property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getHazardousGoodsTransit().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link HazardousGoodsTransitType }
     * 
     * 
     */
    public List<HazardousGoodsTransitType> getHazardousGoodsTransit() {
        if (hazardousGoodsTransit == null) {
            hazardousGoodsTransit = new ArrayList<HazardousGoodsTransitType>();
        }
        return this.hazardousGoodsTransit;
    }

    /**
     * Gets the value of the measurementDimension property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the measurementDimension property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getMeasurementDimension().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DimensionType }
     * 
     * 
     */
    public List<DimensionType> getMeasurementDimension() {
        if (measurementDimension == null) {
            measurementDimension = new ArrayList<DimensionType>();
        }
        return this.measurementDimension;
    }

    /**
     * Recupera il valore della proprietà minimumTemperature.
     * 
     * @return
     *     possible object is
     *     {@link TemperatureType }
     *     
     */
    public TemperatureType getMinimumTemperature() {
        return minimumTemperature;
    }

    /**
     * Imposta il valore della proprietà minimumTemperature.
     * 
     * @param value
     *     allowed object is
     *     {@link TemperatureType }
     *     
     */
    public void setMinimumTemperature(TemperatureType value) {
        this.minimumTemperature = value;
    }

    /**
     * Recupera il valore della proprietà maximumTemperature.
     * 
     * @return
     *     possible object is
     *     {@link TemperatureType }
     *     
     */
    public TemperatureType getMaximumTemperature() {
        return maximumTemperature;
    }

    /**
     * Imposta il valore della proprietà maximumTemperature.
     * 
     * @param value
     *     allowed object is
     *     {@link TemperatureType }
     *     
     */
    public void setMaximumTemperature(TemperatureType value) {
        this.maximumTemperature = value;
    }

    /**
     * Gets the value of the goodsItem property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the goodsItem property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getGoodsItem().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link GoodsItemType }
     * 
     * 
     */
    public List<GoodsItemType> getGoodsItem() {
        if (goodsItem == null) {
            goodsItem = new ArrayList<GoodsItemType>();
        }
        return this.goodsItem;
    }

    /**
     * Recupera il valore della proprietà floorSpaceMeasurementDimension.
     * 
     * @return
     *     possible object is
     *     {@link DimensionType }
     *     
     */
    public DimensionType getFloorSpaceMeasurementDimension() {
        return floorSpaceMeasurementDimension;
    }

    /**
     * Imposta il valore della proprietà floorSpaceMeasurementDimension.
     * 
     * @param value
     *     allowed object is
     *     {@link DimensionType }
     *     
     */
    public void setFloorSpaceMeasurementDimension(DimensionType value) {
        this.floorSpaceMeasurementDimension = value;
    }

    /**
     * Recupera il valore della proprietà palletSpaceMeasurementDimension.
     * 
     * @return
     *     possible object is
     *     {@link DimensionType }
     *     
     */
    public DimensionType getPalletSpaceMeasurementDimension() {
        return palletSpaceMeasurementDimension;
    }

    /**
     * Imposta il valore della proprietà palletSpaceMeasurementDimension.
     * 
     * @param value
     *     allowed object is
     *     {@link DimensionType }
     *     
     */
    public void setPalletSpaceMeasurementDimension(DimensionType value) {
        this.palletSpaceMeasurementDimension = value;
    }

    /**
     * Gets the value of the shipmentDocumentReference property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the shipmentDocumentReference property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getShipmentDocumentReference().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    public List<DocumentReferenceType> getShipmentDocumentReference() {
        if (shipmentDocumentReference == null) {
            shipmentDocumentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.shipmentDocumentReference;
    }

    /**
     * Gets the value of the status property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the status property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getStatus().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link StatusType }
     * 
     * 
     */
    public List<StatusType> getStatus() {
        if (status == null) {
            status = new ArrayList<StatusType>();
        }
        return this.status;
    }

    /**
     * Gets the value of the customsDeclaration property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the customsDeclaration property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCustomsDeclaration().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CustomsDeclarationType }
     * 
     * 
     */
    public List<CustomsDeclarationType> getCustomsDeclaration() {
        if (customsDeclaration == null) {
            customsDeclaration = new ArrayList<CustomsDeclarationType>();
        }
        return this.customsDeclaration;
    }

    /**
     * Gets the value of the referencedShipment property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the referencedShipment property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getReferencedShipment().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ShipmentType }
     * 
     * 
     */
    public List<ShipmentType> getReferencedShipment() {
        if (referencedShipment == null) {
            referencedShipment = new ArrayList<ShipmentType>();
        }
        return this.referencedShipment;
    }

    /**
     * Gets the value of the package property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the package property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPackage().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PackageType }
     * 
     * 
     */
    public List<PackageType> getPackage() {
        if (_package == null) {
            _package = new ArrayList<PackageType>();
        }
        return this._package;
    }

}
