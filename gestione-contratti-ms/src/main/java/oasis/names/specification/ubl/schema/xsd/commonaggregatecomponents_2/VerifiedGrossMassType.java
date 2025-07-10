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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.GrossMassMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.WeighingDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.WeighingDeviceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.WeighingDeviceTypeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.WeighingMethodCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.WeighingTimeType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per VerifiedGrossMassType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="VerifiedGrossMassType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}WeighingDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}WeighingTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}WeighingMethodCode"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}WeighingDeviceID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}WeighingDeviceType" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}GrossMassMeasure"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}WeighingParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ShipperParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ResponsibleParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DocumentReference" maxOccurs="unbounded"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerifiedGrossMassType", propOrder = {
    "ublExtensions",
    "id",
    "weighingDate",
    "weighingTime",
    "weighingMethodCode",
    "weighingDeviceID",
    "weighingDeviceType",
    "grossMassMeasure",
    "weighingParty",
    "shipperParty",
    "responsibleParty",
    "documentReference"
})
public class VerifiedGrossMassType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "WeighingDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected WeighingDateType weighingDate;
    @XmlElement(name = "WeighingTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected WeighingTimeType weighingTime;
    @XmlElement(name = "WeighingMethodCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected WeighingMethodCodeType weighingMethodCode;
    @XmlElement(name = "WeighingDeviceID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected WeighingDeviceIDType weighingDeviceID;
    @XmlElement(name = "WeighingDeviceType", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected WeighingDeviceTypeType weighingDeviceType;
    @XmlElement(name = "GrossMassMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected GrossMassMeasureType grossMassMeasure;
    @XmlElement(name = "WeighingParty")
    protected PartyType weighingParty;
    @XmlElement(name = "ShipperParty")
    protected PartyType shipperParty;
    @XmlElement(name = "ResponsibleParty")
    protected PartyType responsibleParty;
    @XmlElement(name = "DocumentReference", required = true)
    protected List<DocumentReferenceType> documentReference;

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
     * Recupera il valore della proprietà weighingDate.
     * 
     * @return
     *     possible object is
     *     {@link WeighingDateType }
     *     
     */
    public WeighingDateType getWeighingDate() {
        return weighingDate;
    }

    /**
     * Imposta il valore della proprietà weighingDate.
     * 
     * @param value
     *     allowed object is
     *     {@link WeighingDateType }
     *     
     */
    public void setWeighingDate(WeighingDateType value) {
        this.weighingDate = value;
    }

    /**
     * Recupera il valore della proprietà weighingTime.
     * 
     * @return
     *     possible object is
     *     {@link WeighingTimeType }
     *     
     */
    public WeighingTimeType getWeighingTime() {
        return weighingTime;
    }

    /**
     * Imposta il valore della proprietà weighingTime.
     * 
     * @param value
     *     allowed object is
     *     {@link WeighingTimeType }
     *     
     */
    public void setWeighingTime(WeighingTimeType value) {
        this.weighingTime = value;
    }

    /**
     * Recupera il valore della proprietà weighingMethodCode.
     * 
     * @return
     *     possible object is
     *     {@link WeighingMethodCodeType }
     *     
     */
    public WeighingMethodCodeType getWeighingMethodCode() {
        return weighingMethodCode;
    }

    /**
     * Imposta il valore della proprietà weighingMethodCode.
     * 
     * @param value
     *     allowed object is
     *     {@link WeighingMethodCodeType }
     *     
     */
    public void setWeighingMethodCode(WeighingMethodCodeType value) {
        this.weighingMethodCode = value;
    }

    /**
     * Recupera il valore della proprietà weighingDeviceID.
     * 
     * @return
     *     possible object is
     *     {@link WeighingDeviceIDType }
     *     
     */
    public WeighingDeviceIDType getWeighingDeviceID() {
        return weighingDeviceID;
    }

    /**
     * Imposta il valore della proprietà weighingDeviceID.
     * 
     * @param value
     *     allowed object is
     *     {@link WeighingDeviceIDType }
     *     
     */
    public void setWeighingDeviceID(WeighingDeviceIDType value) {
        this.weighingDeviceID = value;
    }

    /**
     * Recupera il valore della proprietà weighingDeviceType.
     * 
     * @return
     *     possible object is
     *     {@link WeighingDeviceTypeType }
     *     
     */
    public WeighingDeviceTypeType getWeighingDeviceType() {
        return weighingDeviceType;
    }

    /**
     * Imposta il valore della proprietà weighingDeviceType.
     * 
     * @param value
     *     allowed object is
     *     {@link WeighingDeviceTypeType }
     *     
     */
    public void setWeighingDeviceType(WeighingDeviceTypeType value) {
        this.weighingDeviceType = value;
    }

    /**
     * Recupera il valore della proprietà grossMassMeasure.
     * 
     * @return
     *     possible object is
     *     {@link GrossMassMeasureType }
     *     
     */
    public GrossMassMeasureType getGrossMassMeasure() {
        return grossMassMeasure;
    }

    /**
     * Imposta il valore della proprietà grossMassMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link GrossMassMeasureType }
     *     
     */
    public void setGrossMassMeasure(GrossMassMeasureType value) {
        this.grossMassMeasure = value;
    }

    /**
     * Recupera il valore della proprietà weighingParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getWeighingParty() {
        return weighingParty;
    }

    /**
     * Imposta il valore della proprietà weighingParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setWeighingParty(PartyType value) {
        this.weighingParty = value;
    }

    /**
     * Recupera il valore della proprietà shipperParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getShipperParty() {
        return shipperParty;
    }

    /**
     * Imposta il valore della proprietà shipperParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setShipperParty(PartyType value) {
        this.shipperParty = value;
    }

    /**
     * Recupera il valore della proprietà responsibleParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getResponsibleParty() {
        return responsibleParty;
    }

    /**
     * Imposta il valore della proprietà responsibleParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setResponsibleParty(PartyType value) {
        this.responsibleParty = value;
    }

    /**
     * Gets the value of the documentReference property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the documentReference property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDocumentReference().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    public List<DocumentReferenceType> getDocumentReference() {
        if (documentReference == null) {
            documentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.documentReference;
    }

}
