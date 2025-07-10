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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per CustomsDeclarationType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="CustomsDeclarationType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ValidityPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ApplicableTerritoryAddress" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Shipment" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CustomsExitOfficeLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}IssuerParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ConsignorParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ConsigneeParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}FreightForwarderParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CustomsParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PreviousCustomsDeclaration" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AdditionalDocumentReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomsDeclarationType", propOrder = {
    "ublExtensions",
    "id",
    "validityPeriod",
    "applicableTerritoryAddress",
    "shipment",
    "customsExitOfficeLocation",
    "issuerParty",
    "consignorParty",
    "consigneeParty",
    "freightForwarderParty",
    "customsParty",
    "previousCustomsDeclaration",
    "additionalDocumentReference"
})
public class CustomsDeclarationType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected IDType id;
    @XmlElement(name = "ValidityPeriod")
    protected PeriodType validityPeriod;
    @XmlElement(name = "ApplicableTerritoryAddress")
    protected AddressType applicableTerritoryAddress;
    @XmlElement(name = "Shipment")
    protected ShipmentType shipment;
    @XmlElement(name = "CustomsExitOfficeLocation")
    protected LocationType customsExitOfficeLocation;
    @XmlElement(name = "IssuerParty")
    protected PartyType issuerParty;
    @XmlElement(name = "ConsignorParty")
    protected PartyType consignorParty;
    @XmlElement(name = "ConsigneeParty")
    protected PartyType consigneeParty;
    @XmlElement(name = "FreightForwarderParty")
    protected PartyType freightForwarderParty;
    @XmlElement(name = "CustomsParty")
    protected PartyType customsParty;
    @XmlElement(name = "PreviousCustomsDeclaration")
    protected CustomsDeclarationType previousCustomsDeclaration;
    @XmlElement(name = "AdditionalDocumentReference")
    protected List<DocumentReferenceType> additionalDocumentReference;

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
     * Recupera il valore della proprietà validityPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getValidityPeriod() {
        return validityPeriod;
    }

    /**
     * Imposta il valore della proprietà validityPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setValidityPeriod(PeriodType value) {
        this.validityPeriod = value;
    }

    /**
     * Recupera il valore della proprietà applicableTerritoryAddress.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getApplicableTerritoryAddress() {
        return applicableTerritoryAddress;
    }

    /**
     * Imposta il valore della proprietà applicableTerritoryAddress.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setApplicableTerritoryAddress(AddressType value) {
        this.applicableTerritoryAddress = value;
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

    /**
     * Recupera il valore della proprietà customsExitOfficeLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getCustomsExitOfficeLocation() {
        return customsExitOfficeLocation;
    }

    /**
     * Imposta il valore della proprietà customsExitOfficeLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setCustomsExitOfficeLocation(LocationType value) {
        this.customsExitOfficeLocation = value;
    }

    /**
     * Recupera il valore della proprietà issuerParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getIssuerParty() {
        return issuerParty;
    }

    /**
     * Imposta il valore della proprietà issuerParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setIssuerParty(PartyType value) {
        this.issuerParty = value;
    }

    /**
     * Recupera il valore della proprietà consignorParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getConsignorParty() {
        return consignorParty;
    }

    /**
     * Imposta il valore della proprietà consignorParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setConsignorParty(PartyType value) {
        this.consignorParty = value;
    }

    /**
     * Recupera il valore della proprietà consigneeParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getConsigneeParty() {
        return consigneeParty;
    }

    /**
     * Imposta il valore della proprietà consigneeParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setConsigneeParty(PartyType value) {
        this.consigneeParty = value;
    }

    /**
     * Recupera il valore della proprietà freightForwarderParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getFreightForwarderParty() {
        return freightForwarderParty;
    }

    /**
     * Imposta il valore della proprietà freightForwarderParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setFreightForwarderParty(PartyType value) {
        this.freightForwarderParty = value;
    }

    /**
     * Recupera il valore della proprietà customsParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getCustomsParty() {
        return customsParty;
    }

    /**
     * Imposta il valore della proprietà customsParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setCustomsParty(PartyType value) {
        this.customsParty = value;
    }

    /**
     * Recupera il valore della proprietà previousCustomsDeclaration.
     * 
     * @return
     *     possible object is
     *     {@link CustomsDeclarationType }
     *     
     */
    public CustomsDeclarationType getPreviousCustomsDeclaration() {
        return previousCustomsDeclaration;
    }

    /**
     * Imposta il valore della proprietà previousCustomsDeclaration.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomsDeclarationType }
     *     
     */
    public void setPreviousCustomsDeclaration(CustomsDeclarationType value) {
        this.previousCustomsDeclaration = value;
    }

    /**
     * Gets the value of the additionalDocumentReference property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the additionalDocumentReference property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAdditionalDocumentReference().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    public List<DocumentReferenceType> getAdditionalDocumentReference() {
        if (additionalDocumentReference == null) {
            additionalDocumentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.additionalDocumentReference;
    }

}
