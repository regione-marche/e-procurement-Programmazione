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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ConsumptionIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NoteType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SpecificationTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalMeteredQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per SubscriberConsumptionType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="SubscriberConsumptionType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ConsumptionID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SpecificationTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Note" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TotalMeteredQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SubscriberParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}UtilityConsumptionPoint"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OnAccountPayment" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Consumption" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SupplierConsumption" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubscriberConsumptionType", propOrder = {
    "ublExtensions",
    "consumptionID",
    "specificationTypeCode",
    "note",
    "totalMeteredQuantity",
    "subscriberParty",
    "utilityConsumptionPoint",
    "onAccountPayment",
    "consumption",
    "supplierConsumption"
})
public class SubscriberConsumptionType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ConsumptionID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ConsumptionIDType consumptionID;
    @XmlElement(name = "SpecificationTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SpecificationTypeCodeType specificationTypeCode;
    @XmlElement(name = "Note", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<NoteType> note;
    @XmlElement(name = "TotalMeteredQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TotalMeteredQuantityType totalMeteredQuantity;
    @XmlElement(name = "SubscriberParty")
    protected PartyType subscriberParty;
    @XmlElement(name = "UtilityConsumptionPoint", required = true)
    protected ConsumptionPointType utilityConsumptionPoint;
    @XmlElement(name = "OnAccountPayment")
    protected List<OnAccountPaymentType> onAccountPayment;
    @XmlElement(name = "Consumption")
    protected ConsumptionType consumption;
    @XmlElement(name = "SupplierConsumption")
    protected List<SupplierConsumptionType> supplierConsumption;

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
     * Recupera il valore della proprietà consumptionID.
     * 
     * @return
     *     possible object is
     *     {@link ConsumptionIDType }
     *     
     */
    public ConsumptionIDType getConsumptionID() {
        return consumptionID;
    }

    /**
     * Imposta il valore della proprietà consumptionID.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsumptionIDType }
     *     
     */
    public void setConsumptionID(ConsumptionIDType value) {
        this.consumptionID = value;
    }

    /**
     * Recupera il valore della proprietà specificationTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link SpecificationTypeCodeType }
     *     
     */
    public SpecificationTypeCodeType getSpecificationTypeCode() {
        return specificationTypeCode;
    }

    /**
     * Imposta il valore della proprietà specificationTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link SpecificationTypeCodeType }
     *     
     */
    public void setSpecificationTypeCode(SpecificationTypeCodeType value) {
        this.specificationTypeCode = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the note property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getNote().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link NoteType }
     * 
     * 
     */
    public List<NoteType> getNote() {
        if (note == null) {
            note = new ArrayList<NoteType>();
        }
        return this.note;
    }

    /**
     * Recupera il valore della proprietà totalMeteredQuantity.
     * 
     * @return
     *     possible object is
     *     {@link TotalMeteredQuantityType }
     *     
     */
    public TotalMeteredQuantityType getTotalMeteredQuantity() {
        return totalMeteredQuantity;
    }

    /**
     * Imposta il valore della proprietà totalMeteredQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalMeteredQuantityType }
     *     
     */
    public void setTotalMeteredQuantity(TotalMeteredQuantityType value) {
        this.totalMeteredQuantity = value;
    }

    /**
     * Recupera il valore della proprietà subscriberParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getSubscriberParty() {
        return subscriberParty;
    }

    /**
     * Imposta il valore della proprietà subscriberParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setSubscriberParty(PartyType value) {
        this.subscriberParty = value;
    }

    /**
     * Recupera il valore della proprietà utilityConsumptionPoint.
     * 
     * @return
     *     possible object is
     *     {@link ConsumptionPointType }
     *     
     */
    public ConsumptionPointType getUtilityConsumptionPoint() {
        return utilityConsumptionPoint;
    }

    /**
     * Imposta il valore della proprietà utilityConsumptionPoint.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsumptionPointType }
     *     
     */
    public void setUtilityConsumptionPoint(ConsumptionPointType value) {
        this.utilityConsumptionPoint = value;
    }

    /**
     * Gets the value of the onAccountPayment property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the onAccountPayment property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getOnAccountPayment().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link OnAccountPaymentType }
     * 
     * 
     */
    public List<OnAccountPaymentType> getOnAccountPayment() {
        if (onAccountPayment == null) {
            onAccountPayment = new ArrayList<OnAccountPaymentType>();
        }
        return this.onAccountPayment;
    }

    /**
     * Recupera il valore della proprietà consumption.
     * 
     * @return
     *     possible object is
     *     {@link ConsumptionType }
     *     
     */
    public ConsumptionType getConsumption() {
        return consumption;
    }

    /**
     * Imposta il valore della proprietà consumption.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsumptionType }
     *     
     */
    public void setConsumption(ConsumptionType value) {
        this.consumption = value;
    }

    /**
     * Gets the value of the supplierConsumption property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the supplierConsumption property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSupplierConsumption().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link SupplierConsumptionType }
     * 
     * 
     */
    public List<SupplierConsumptionType> getSupplierConsumption() {
        if (supplierConsumption == null) {
            supplierConsumption = new ArrayList<SupplierConsumptionType>();
        }
        return this.supplierConsumption;
    }

}
