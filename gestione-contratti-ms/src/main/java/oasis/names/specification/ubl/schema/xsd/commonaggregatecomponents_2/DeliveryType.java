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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ActualDeliveryDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ActualDeliveryTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LatestDeliveryDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LatestDeliveryTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MinimumQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.QuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReleaseIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TrackingIDType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per DeliveryType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="DeliveryType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Quantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MinimumQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MaximumQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ActualDeliveryDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ActualDeliveryTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LatestDeliveryDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LatestDeliveryTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ReleaseID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TrackingID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DeliveryAddress" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DeliveryLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AlternativeDeliveryLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RequestedDeliveryPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PromisedDeliveryPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}EstimatedDeliveryPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CarrierParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DeliveryParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}NotifyParty" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Despatch" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DeliveryTerms" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}MinimumDeliveryUnit" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}MaximumDeliveryUnit" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Shipment" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeliveryType", propOrder = {
    "ublExtensions",
    "id",
    "quantity",
    "minimumQuantity",
    "maximumQuantity",
    "actualDeliveryDate",
    "actualDeliveryTime",
    "latestDeliveryDate",
    "latestDeliveryTime",
    "releaseID",
    "trackingID",
    "deliveryAddress",
    "deliveryLocation",
    "alternativeDeliveryLocation",
    "requestedDeliveryPeriod",
    "promisedDeliveryPeriod",
    "estimatedDeliveryPeriod",
    "carrierParty",
    "deliveryParty",
    "notifyParty",
    "despatch",
    "deliveryTerms",
    "minimumDeliveryUnit",
    "maximumDeliveryUnit",
    "shipment"
})
public class DeliveryType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "Quantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected QuantityType quantity;
    @XmlElement(name = "MinimumQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MinimumQuantityType minimumQuantity;
    @XmlElement(name = "MaximumQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MaximumQuantityType maximumQuantity;
    @XmlElement(name = "ActualDeliveryDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ActualDeliveryDateType actualDeliveryDate;
    @XmlElement(name = "ActualDeliveryTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ActualDeliveryTimeType actualDeliveryTime;
    @XmlElement(name = "LatestDeliveryDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected LatestDeliveryDateType latestDeliveryDate;
    @XmlElement(name = "LatestDeliveryTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected LatestDeliveryTimeType latestDeliveryTime;
    @XmlElement(name = "ReleaseID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ReleaseIDType releaseID;
    @XmlElement(name = "TrackingID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TrackingIDType trackingID;
    @XmlElement(name = "DeliveryAddress")
    protected AddressType deliveryAddress;
    @XmlElement(name = "DeliveryLocation")
    protected LocationType deliveryLocation;
    @XmlElement(name = "AlternativeDeliveryLocation")
    protected LocationType alternativeDeliveryLocation;
    @XmlElement(name = "RequestedDeliveryPeriod")
    protected PeriodType requestedDeliveryPeriod;
    @XmlElement(name = "PromisedDeliveryPeriod")
    protected PeriodType promisedDeliveryPeriod;
    @XmlElement(name = "EstimatedDeliveryPeriod")
    protected PeriodType estimatedDeliveryPeriod;
    @XmlElement(name = "CarrierParty")
    protected PartyType carrierParty;
    @XmlElement(name = "DeliveryParty")
    protected PartyType deliveryParty;
    @XmlElement(name = "NotifyParty")
    protected List<PartyType> notifyParty;
    @XmlElement(name = "Despatch")
    protected DespatchType despatch;
    @XmlElement(name = "DeliveryTerms")
    protected List<DeliveryTermsType> deliveryTerms;
    @XmlElement(name = "MinimumDeliveryUnit")
    protected DeliveryUnitType minimumDeliveryUnit;
    @XmlElement(name = "MaximumDeliveryUnit")
    protected DeliveryUnitType maximumDeliveryUnit;
    @XmlElement(name = "Shipment")
    protected ShipmentType shipment;

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
     * Recupera il valore della proprietà quantity.
     * 
     * @return
     *     possible object is
     *     {@link QuantityType }
     *     
     */
    public QuantityType getQuantity() {
        return quantity;
    }

    /**
     * Imposta il valore della proprietà quantity.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *     
     */
    public void setQuantity(QuantityType value) {
        this.quantity = value;
    }

    /**
     * Recupera il valore della proprietà minimumQuantity.
     * 
     * @return
     *     possible object is
     *     {@link MinimumQuantityType }
     *     
     */
    public MinimumQuantityType getMinimumQuantity() {
        return minimumQuantity;
    }

    /**
     * Imposta il valore della proprietà minimumQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link MinimumQuantityType }
     *     
     */
    public void setMinimumQuantity(MinimumQuantityType value) {
        this.minimumQuantity = value;
    }

    /**
     * Recupera il valore della proprietà maximumQuantity.
     * 
     * @return
     *     possible object is
     *     {@link MaximumQuantityType }
     *     
     */
    public MaximumQuantityType getMaximumQuantity() {
        return maximumQuantity;
    }

    /**
     * Imposta il valore della proprietà maximumQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumQuantityType }
     *     
     */
    public void setMaximumQuantity(MaximumQuantityType value) {
        this.maximumQuantity = value;
    }

    /**
     * Recupera il valore della proprietà actualDeliveryDate.
     * 
     * @return
     *     possible object is
     *     {@link ActualDeliveryDateType }
     *     
     */
    public ActualDeliveryDateType getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    /**
     * Imposta il valore della proprietà actualDeliveryDate.
     * 
     * @param value
     *     allowed object is
     *     {@link ActualDeliveryDateType }
     *     
     */
    public void setActualDeliveryDate(ActualDeliveryDateType value) {
        this.actualDeliveryDate = value;
    }

    /**
     * Recupera il valore della proprietà actualDeliveryTime.
     * 
     * @return
     *     possible object is
     *     {@link ActualDeliveryTimeType }
     *     
     */
    public ActualDeliveryTimeType getActualDeliveryTime() {
        return actualDeliveryTime;
    }

    /**
     * Imposta il valore della proprietà actualDeliveryTime.
     * 
     * @param value
     *     allowed object is
     *     {@link ActualDeliveryTimeType }
     *     
     */
    public void setActualDeliveryTime(ActualDeliveryTimeType value) {
        this.actualDeliveryTime = value;
    }

    /**
     * Recupera il valore della proprietà latestDeliveryDate.
     * 
     * @return
     *     possible object is
     *     {@link LatestDeliveryDateType }
     *     
     */
    public LatestDeliveryDateType getLatestDeliveryDate() {
        return latestDeliveryDate;
    }

    /**
     * Imposta il valore della proprietà latestDeliveryDate.
     * 
     * @param value
     *     allowed object is
     *     {@link LatestDeliveryDateType }
     *     
     */
    public void setLatestDeliveryDate(LatestDeliveryDateType value) {
        this.latestDeliveryDate = value;
    }

    /**
     * Recupera il valore della proprietà latestDeliveryTime.
     * 
     * @return
     *     possible object is
     *     {@link LatestDeliveryTimeType }
     *     
     */
    public LatestDeliveryTimeType getLatestDeliveryTime() {
        return latestDeliveryTime;
    }

    /**
     * Imposta il valore della proprietà latestDeliveryTime.
     * 
     * @param value
     *     allowed object is
     *     {@link LatestDeliveryTimeType }
     *     
     */
    public void setLatestDeliveryTime(LatestDeliveryTimeType value) {
        this.latestDeliveryTime = value;
    }

    /**
     * Recupera il valore della proprietà releaseID.
     * 
     * @return
     *     possible object is
     *     {@link ReleaseIDType }
     *     
     */
    public ReleaseIDType getReleaseID() {
        return releaseID;
    }

    /**
     * Imposta il valore della proprietà releaseID.
     * 
     * @param value
     *     allowed object is
     *     {@link ReleaseIDType }
     *     
     */
    public void setReleaseID(ReleaseIDType value) {
        this.releaseID = value;
    }

    /**
     * Recupera il valore della proprietà trackingID.
     * 
     * @return
     *     possible object is
     *     {@link TrackingIDType }
     *     
     */
    public TrackingIDType getTrackingID() {
        return trackingID;
    }

    /**
     * Imposta il valore della proprietà trackingID.
     * 
     * @param value
     *     allowed object is
     *     {@link TrackingIDType }
     *     
     */
    public void setTrackingID(TrackingIDType value) {
        this.trackingID = value;
    }

    /**
     * Recupera il valore della proprietà deliveryAddress.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * Imposta il valore della proprietà deliveryAddress.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setDeliveryAddress(AddressType value) {
        this.deliveryAddress = value;
    }

    /**
     * Recupera il valore della proprietà deliveryLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getDeliveryLocation() {
        return deliveryLocation;
    }

    /**
     * Imposta il valore della proprietà deliveryLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setDeliveryLocation(LocationType value) {
        this.deliveryLocation = value;
    }

    /**
     * Recupera il valore della proprietà alternativeDeliveryLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getAlternativeDeliveryLocation() {
        return alternativeDeliveryLocation;
    }

    /**
     * Imposta il valore della proprietà alternativeDeliveryLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setAlternativeDeliveryLocation(LocationType value) {
        this.alternativeDeliveryLocation = value;
    }

    /**
     * Recupera il valore della proprietà requestedDeliveryPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getRequestedDeliveryPeriod() {
        return requestedDeliveryPeriod;
    }

    /**
     * Imposta il valore della proprietà requestedDeliveryPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setRequestedDeliveryPeriod(PeriodType value) {
        this.requestedDeliveryPeriod = value;
    }

    /**
     * Recupera il valore della proprietà promisedDeliveryPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getPromisedDeliveryPeriod() {
        return promisedDeliveryPeriod;
    }

    /**
     * Imposta il valore della proprietà promisedDeliveryPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setPromisedDeliveryPeriod(PeriodType value) {
        this.promisedDeliveryPeriod = value;
    }

    /**
     * Recupera il valore della proprietà estimatedDeliveryPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getEstimatedDeliveryPeriod() {
        return estimatedDeliveryPeriod;
    }

    /**
     * Imposta il valore della proprietà estimatedDeliveryPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setEstimatedDeliveryPeriod(PeriodType value) {
        this.estimatedDeliveryPeriod = value;
    }

    /**
     * Recupera il valore della proprietà carrierParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getCarrierParty() {
        return carrierParty;
    }

    /**
     * Imposta il valore della proprietà carrierParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setCarrierParty(PartyType value) {
        this.carrierParty = value;
    }

    /**
     * Recupera il valore della proprietà deliveryParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getDeliveryParty() {
        return deliveryParty;
    }

    /**
     * Imposta il valore della proprietà deliveryParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setDeliveryParty(PartyType value) {
        this.deliveryParty = value;
    }

    /**
     * Gets the value of the notifyParty property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the notifyParty property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getNotifyParty().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PartyType }
     * 
     * 
     */
    public List<PartyType> getNotifyParty() {
        if (notifyParty == null) {
            notifyParty = new ArrayList<PartyType>();
        }
        return this.notifyParty;
    }

    /**
     * Recupera il valore della proprietà despatch.
     * 
     * @return
     *     possible object is
     *     {@link DespatchType }
     *     
     */
    public DespatchType getDespatch() {
        return despatch;
    }

    /**
     * Imposta il valore della proprietà despatch.
     * 
     * @param value
     *     allowed object is
     *     {@link DespatchType }
     *     
     */
    public void setDespatch(DespatchType value) {
        this.despatch = value;
    }

    /**
     * Gets the value of the deliveryTerms property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the deliveryTerms property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDeliveryTerms().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DeliveryTermsType }
     * 
     * 
     */
    public List<DeliveryTermsType> getDeliveryTerms() {
        if (deliveryTerms == null) {
            deliveryTerms = new ArrayList<DeliveryTermsType>();
        }
        return this.deliveryTerms;
    }

    /**
     * Recupera il valore della proprietà minimumDeliveryUnit.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryUnitType }
     *     
     */
    public DeliveryUnitType getMinimumDeliveryUnit() {
        return minimumDeliveryUnit;
    }

    /**
     * Imposta il valore della proprietà minimumDeliveryUnit.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryUnitType }
     *     
     */
    public void setMinimumDeliveryUnit(DeliveryUnitType value) {
        this.minimumDeliveryUnit = value;
    }

    /**
     * Recupera il valore della proprietà maximumDeliveryUnit.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryUnitType }
     *     
     */
    public DeliveryUnitType getMaximumDeliveryUnit() {
        return maximumDeliveryUnit;
    }

    /**
     * Imposta il valore della proprietà maximumDeliveryUnit.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryUnitType }
     *     
     */
    public void setMaximumDeliveryUnit(DeliveryUnitType value) {
        this.maximumDeliveryUnit = value;
    }

    /**
     * Recupera il valore della proprietà shipment.
     * 
     * @return
     *     possible object is
     *     {@link ShipmentType }
     *     
     */
    public ShipmentType getShipment() {
        return shipment;
    }

    /**
     * Imposta il valore della proprietà shipment.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipmentType }
     *     
     */
    public void setShipment(ShipmentType value) {
        this.shipment = value;
    }

}
