//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ActualPickupDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ActualPickupTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EarliestPickupDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EarliestPickupTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LatestPickupDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LatestPickupTimeType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per PickupType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="PickupType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ActualPickupDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ActualPickupTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}EarliestPickupDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}EarliestPickupTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LatestPickupDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LatestPickupTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PickupLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PickupParty" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PickupType", propOrder = {
    "ublExtensions",
    "id",
    "actualPickupDate",
    "actualPickupTime",
    "earliestPickupDate",
    "earliestPickupTime",
    "latestPickupDate",
    "latestPickupTime",
    "pickupLocation",
    "pickupParty"
})
public class PickupType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "ActualPickupDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ActualPickupDateType actualPickupDate;
    @XmlElement(name = "ActualPickupTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ActualPickupTimeType actualPickupTime;
    @XmlElement(name = "EarliestPickupDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected EarliestPickupDateType earliestPickupDate;
    @XmlElement(name = "EarliestPickupTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected EarliestPickupTimeType earliestPickupTime;
    @XmlElement(name = "LatestPickupDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected LatestPickupDateType latestPickupDate;
    @XmlElement(name = "LatestPickupTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected LatestPickupTimeType latestPickupTime;
    @XmlElement(name = "PickupLocation")
    protected LocationType pickupLocation;
    @XmlElement(name = "PickupParty")
    protected PartyType pickupParty;

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
     * Recupera il valore della proprietà actualPickupDate.
     * 
     * @return
     *     possible object is
     *     {@link ActualPickupDateType }
     *     
     */
    public ActualPickupDateType getActualPickupDate() {
        return actualPickupDate;
    }

    /**
     * Imposta il valore della proprietà actualPickupDate.
     * 
     * @param value
     *     allowed object is
     *     {@link ActualPickupDateType }
     *     
     */
    public void setActualPickupDate(ActualPickupDateType value) {
        this.actualPickupDate = value;
    }

    /**
     * Recupera il valore della proprietà actualPickupTime.
     * 
     * @return
     *     possible object is
     *     {@link ActualPickupTimeType }
     *     
     */
    public ActualPickupTimeType getActualPickupTime() {
        return actualPickupTime;
    }

    /**
     * Imposta il valore della proprietà actualPickupTime.
     * 
     * @param value
     *     allowed object is
     *     {@link ActualPickupTimeType }
     *     
     */
    public void setActualPickupTime(ActualPickupTimeType value) {
        this.actualPickupTime = value;
    }

    /**
     * Recupera il valore della proprietà earliestPickupDate.
     * 
     * @return
     *     possible object is
     *     {@link EarliestPickupDateType }
     *     
     */
    public EarliestPickupDateType getEarliestPickupDate() {
        return earliestPickupDate;
    }

    /**
     * Imposta il valore della proprietà earliestPickupDate.
     * 
     * @param value
     *     allowed object is
     *     {@link EarliestPickupDateType }
     *     
     */
    public void setEarliestPickupDate(EarliestPickupDateType value) {
        this.earliestPickupDate = value;
    }

    /**
     * Recupera il valore della proprietà earliestPickupTime.
     * 
     * @return
     *     possible object is
     *     {@link EarliestPickupTimeType }
     *     
     */
    public EarliestPickupTimeType getEarliestPickupTime() {
        return earliestPickupTime;
    }

    /**
     * Imposta il valore della proprietà earliestPickupTime.
     * 
     * @param value
     *     allowed object is
     *     {@link EarliestPickupTimeType }
     *     
     */
    public void setEarliestPickupTime(EarliestPickupTimeType value) {
        this.earliestPickupTime = value;
    }

    /**
     * Recupera il valore della proprietà latestPickupDate.
     * 
     * @return
     *     possible object is
     *     {@link LatestPickupDateType }
     *     
     */
    public LatestPickupDateType getLatestPickupDate() {
        return latestPickupDate;
    }

    /**
     * Imposta il valore della proprietà latestPickupDate.
     * 
     * @param value
     *     allowed object is
     *     {@link LatestPickupDateType }
     *     
     */
    public void setLatestPickupDate(LatestPickupDateType value) {
        this.latestPickupDate = value;
    }

    /**
     * Recupera il valore della proprietà latestPickupTime.
     * 
     * @return
     *     possible object is
     *     {@link LatestPickupTimeType }
     *     
     */
    public LatestPickupTimeType getLatestPickupTime() {
        return latestPickupTime;
    }

    /**
     * Imposta il valore della proprietà latestPickupTime.
     * 
     * @param value
     *     allowed object is
     *     {@link LatestPickupTimeType }
     *     
     */
    public void setLatestPickupTime(LatestPickupTimeType value) {
        this.latestPickupTime = value;
    }

    /**
     * Recupera il valore della proprietà pickupLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getPickupLocation() {
        return pickupLocation;
    }

    /**
     * Imposta il valore della proprietà pickupLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setPickupLocation(LocationType value) {
        this.pickupLocation = value;
    }

    /**
     * Recupera il valore della proprietà pickupParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getPickupParty() {
        return pickupParty;
    }

    /**
     * Imposta il valore della proprietà pickupParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setPickupParty(PartyType value) {
        this.pickupParty = value;
    }

}
