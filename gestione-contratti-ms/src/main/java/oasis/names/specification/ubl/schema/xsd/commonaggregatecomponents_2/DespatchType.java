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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ActualDespatchDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ActualDespatchTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EstimatedDespatchDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EstimatedDespatchTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.GuaranteedDespatchDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.GuaranteedDespatchTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InstructionsType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReleaseIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RequestedDespatchDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RequestedDespatchTimeType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per DespatchType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="DespatchType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RequestedDespatchDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RequestedDespatchTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}EstimatedDespatchDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}EstimatedDespatchTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ActualDespatchDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ActualDespatchTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}GuaranteedDespatchDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}GuaranteedDespatchTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ReleaseID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Instructions" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DespatchAddress" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DespatchLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DespatchParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CarrierParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}NotifyParty" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Contact" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}EstimatedDespatchPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RequestedDespatchPeriod" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DespatchType", propOrder = {
    "ublExtensions",
    "id",
    "requestedDespatchDate",
    "requestedDespatchTime",
    "estimatedDespatchDate",
    "estimatedDespatchTime",
    "actualDespatchDate",
    "actualDespatchTime",
    "guaranteedDespatchDate",
    "guaranteedDespatchTime",
    "releaseID",
    "instructions",
    "despatchAddress",
    "despatchLocation",
    "despatchParty",
    "carrierParty",
    "notifyParty",
    "contact",
    "estimatedDespatchPeriod",
    "requestedDespatchPeriod"
})
public class DespatchType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "RequestedDespatchDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RequestedDespatchDateType requestedDespatchDate;
    @XmlElement(name = "RequestedDespatchTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RequestedDespatchTimeType requestedDespatchTime;
    @XmlElement(name = "EstimatedDespatchDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected EstimatedDespatchDateType estimatedDespatchDate;
    @XmlElement(name = "EstimatedDespatchTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected EstimatedDespatchTimeType estimatedDespatchTime;
    @XmlElement(name = "ActualDespatchDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ActualDespatchDateType actualDespatchDate;
    @XmlElement(name = "ActualDespatchTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ActualDespatchTimeType actualDespatchTime;
    @XmlElement(name = "GuaranteedDespatchDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected GuaranteedDespatchDateType guaranteedDespatchDate;
    @XmlElement(name = "GuaranteedDespatchTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected GuaranteedDespatchTimeType guaranteedDespatchTime;
    @XmlElement(name = "ReleaseID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ReleaseIDType releaseID;
    @XmlElement(name = "Instructions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<InstructionsType> instructions;
    @XmlElement(name = "DespatchAddress")
    protected AddressType despatchAddress;
    @XmlElement(name = "DespatchLocation")
    protected LocationType despatchLocation;
    @XmlElement(name = "DespatchParty")
    protected PartyType despatchParty;
    @XmlElement(name = "CarrierParty")
    protected PartyType carrierParty;
    @XmlElement(name = "NotifyParty")
    protected List<PartyType> notifyParty;
    @XmlElement(name = "Contact")
    protected ContactType contact;
    @XmlElement(name = "EstimatedDespatchPeriod")
    protected PeriodType estimatedDespatchPeriod;
    @XmlElement(name = "RequestedDespatchPeriod")
    protected PeriodType requestedDespatchPeriod;

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
     * Recupera il valore della proprietà requestedDespatchDate.
     * 
     * @return
     *     possible object is
     *     {@link RequestedDespatchDateType }
     *     
     */
    public RequestedDespatchDateType getRequestedDespatchDate() {
        return requestedDespatchDate;
    }

    /**
     * Imposta il valore della proprietà requestedDespatchDate.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestedDespatchDateType }
     *     
     */
    public void setRequestedDespatchDate(RequestedDespatchDateType value) {
        this.requestedDespatchDate = value;
    }

    /**
     * Recupera il valore della proprietà requestedDespatchTime.
     * 
     * @return
     *     possible object is
     *     {@link RequestedDespatchTimeType }
     *     
     */
    public RequestedDespatchTimeType getRequestedDespatchTime() {
        return requestedDespatchTime;
    }

    /**
     * Imposta il valore della proprietà requestedDespatchTime.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestedDespatchTimeType }
     *     
     */
    public void setRequestedDespatchTime(RequestedDespatchTimeType value) {
        this.requestedDespatchTime = value;
    }

    /**
     * Recupera il valore della proprietà estimatedDespatchDate.
     * 
     * @return
     *     possible object is
     *     {@link EstimatedDespatchDateType }
     *     
     */
    public EstimatedDespatchDateType getEstimatedDespatchDate() {
        return estimatedDespatchDate;
    }

    /**
     * Imposta il valore della proprietà estimatedDespatchDate.
     * 
     * @param value
     *     allowed object is
     *     {@link EstimatedDespatchDateType }
     *     
     */
    public void setEstimatedDespatchDate(EstimatedDespatchDateType value) {
        this.estimatedDespatchDate = value;
    }

    /**
     * Recupera il valore della proprietà estimatedDespatchTime.
     * 
     * @return
     *     possible object is
     *     {@link EstimatedDespatchTimeType }
     *     
     */
    public EstimatedDespatchTimeType getEstimatedDespatchTime() {
        return estimatedDespatchTime;
    }

    /**
     * Imposta il valore della proprietà estimatedDespatchTime.
     * 
     * @param value
     *     allowed object is
     *     {@link EstimatedDespatchTimeType }
     *     
     */
    public void setEstimatedDespatchTime(EstimatedDespatchTimeType value) {
        this.estimatedDespatchTime = value;
    }

    /**
     * Recupera il valore della proprietà actualDespatchDate.
     * 
     * @return
     *     possible object is
     *     {@link ActualDespatchDateType }
     *     
     */
    public ActualDespatchDateType getActualDespatchDate() {
        return actualDespatchDate;
    }

    /**
     * Imposta il valore della proprietà actualDespatchDate.
     * 
     * @param value
     *     allowed object is
     *     {@link ActualDespatchDateType }
     *     
     */
    public void setActualDespatchDate(ActualDespatchDateType value) {
        this.actualDespatchDate = value;
    }

    /**
     * Recupera il valore della proprietà actualDespatchTime.
     * 
     * @return
     *     possible object is
     *     {@link ActualDespatchTimeType }
     *     
     */
    public ActualDespatchTimeType getActualDespatchTime() {
        return actualDespatchTime;
    }

    /**
     * Imposta il valore della proprietà actualDespatchTime.
     * 
     * @param value
     *     allowed object is
     *     {@link ActualDespatchTimeType }
     *     
     */
    public void setActualDespatchTime(ActualDespatchTimeType value) {
        this.actualDespatchTime = value;
    }

    /**
     * Recupera il valore della proprietà guaranteedDespatchDate.
     * 
     * @return
     *     possible object is
     *     {@link GuaranteedDespatchDateType }
     *     
     */
    public GuaranteedDespatchDateType getGuaranteedDespatchDate() {
        return guaranteedDespatchDate;
    }

    /**
     * Imposta il valore della proprietà guaranteedDespatchDate.
     * 
     * @param value
     *     allowed object is
     *     {@link GuaranteedDespatchDateType }
     *     
     */
    public void setGuaranteedDespatchDate(GuaranteedDespatchDateType value) {
        this.guaranteedDespatchDate = value;
    }

    /**
     * Recupera il valore della proprietà guaranteedDespatchTime.
     * 
     * @return
     *     possible object is
     *     {@link GuaranteedDespatchTimeType }
     *     
     */
    public GuaranteedDespatchTimeType getGuaranteedDespatchTime() {
        return guaranteedDespatchTime;
    }

    /**
     * Imposta il valore della proprietà guaranteedDespatchTime.
     * 
     * @param value
     *     allowed object is
     *     {@link GuaranteedDespatchTimeType }
     *     
     */
    public void setGuaranteedDespatchTime(GuaranteedDespatchTimeType value) {
        this.guaranteedDespatchTime = value;
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
     * Gets the value of the instructions property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the instructions property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getInstructions().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link InstructionsType }
     * 
     * 
     */
    public List<InstructionsType> getInstructions() {
        if (instructions == null) {
            instructions = new ArrayList<InstructionsType>();
        }
        return this.instructions;
    }

    /**
     * Recupera il valore della proprietà despatchAddress.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getDespatchAddress() {
        return despatchAddress;
    }

    /**
     * Imposta il valore della proprietà despatchAddress.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setDespatchAddress(AddressType value) {
        this.despatchAddress = value;
    }

    /**
     * Recupera il valore della proprietà despatchLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getDespatchLocation() {
        return despatchLocation;
    }

    /**
     * Imposta il valore della proprietà despatchLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setDespatchLocation(LocationType value) {
        this.despatchLocation = value;
    }

    /**
     * Recupera il valore della proprietà despatchParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getDespatchParty() {
        return despatchParty;
    }

    /**
     * Imposta il valore della proprietà despatchParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setDespatchParty(PartyType value) {
        this.despatchParty = value;
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
     * Recupera il valore della proprietà contact.
     * 
     * @return
     *     possible object is
     *     {@link ContactType }
     *     
     */
    public ContactType getContact() {
        return contact;
    }

    /**
     * Imposta il valore della proprietà contact.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactType }
     *     
     */
    public void setContact(ContactType value) {
        this.contact = value;
    }

    /**
     * Recupera il valore della proprietà estimatedDespatchPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getEstimatedDespatchPeriod() {
        return estimatedDespatchPeriod;
    }

    /**
     * Imposta il valore della proprietà estimatedDespatchPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setEstimatedDespatchPeriod(PeriodType value) {
        this.estimatedDespatchPeriod = value;
    }

    /**
     * Recupera il valore della proprietà requestedDespatchPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getRequestedDespatchPeriod() {
        return requestedDespatchPeriod;
    }

    /**
     * Imposta il valore della proprietà requestedDespatchPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setRequestedDespatchPeriod(PeriodType value) {
        this.requestedDespatchPeriod = value;
    }

}
