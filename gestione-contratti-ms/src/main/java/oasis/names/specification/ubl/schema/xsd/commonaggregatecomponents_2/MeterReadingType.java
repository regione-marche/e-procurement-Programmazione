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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DeliveredQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LatestMeterQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LatestMeterReadingDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LatestMeterReadingMethodCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LatestMeterReadingMethodType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MeterReadingCommentsType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MeterReadingTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MeterReadingTypeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PreviousMeterQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PreviousMeterReadingDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PreviousMeterReadingMethodCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PreviousMeterReadingMethodType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per MeterReadingType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="MeterReadingType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MeterReadingType" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MeterReadingTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PreviousMeterReadingDate"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PreviousMeterQuantity"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LatestMeterReadingDate"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LatestMeterQuantity"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PreviousMeterReadingMethod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PreviousMeterReadingMethodCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LatestMeterReadingMethod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LatestMeterReadingMethodCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MeterReadingComments" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DeliveredQuantity"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeterReadingType", propOrder = {
    "ublExtensions",
    "id",
    "meterReadingType",
    "meterReadingTypeCode",
    "previousMeterReadingDate",
    "previousMeterQuantity",
    "latestMeterReadingDate",
    "latestMeterQuantity",
    "previousMeterReadingMethod",
    "previousMeterReadingMethodCode",
    "latestMeterReadingMethod",
    "latestMeterReadingMethodCode",
    "meterReadingComments",
    "deliveredQuantity"
})
public class MeterReadingType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "MeterReadingType", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MeterReadingTypeType meterReadingType;
    @XmlElement(name = "MeterReadingTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MeterReadingTypeCodeType meterReadingTypeCode;
    @XmlElement(name = "PreviousMeterReadingDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected PreviousMeterReadingDateType previousMeterReadingDate;
    @XmlElement(name = "PreviousMeterQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected PreviousMeterQuantityType previousMeterQuantity;
    @XmlElement(name = "LatestMeterReadingDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected LatestMeterReadingDateType latestMeterReadingDate;
    @XmlElement(name = "LatestMeterQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected LatestMeterQuantityType latestMeterQuantity;
    @XmlElement(name = "PreviousMeterReadingMethod", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PreviousMeterReadingMethodType previousMeterReadingMethod;
    @XmlElement(name = "PreviousMeterReadingMethodCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PreviousMeterReadingMethodCodeType previousMeterReadingMethodCode;
    @XmlElement(name = "LatestMeterReadingMethod", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected LatestMeterReadingMethodType latestMeterReadingMethod;
    @XmlElement(name = "LatestMeterReadingMethodCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected LatestMeterReadingMethodCodeType latestMeterReadingMethodCode;
    @XmlElement(name = "MeterReadingComments", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<MeterReadingCommentsType> meterReadingComments;
    @XmlElement(name = "DeliveredQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected DeliveredQuantityType deliveredQuantity;

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
     * Recupera il valore della proprietà meterReadingType.
     * 
     * @return
     *     possible object is
     *     {@link MeterReadingTypeType }
     *     
     */
    public MeterReadingTypeType getMeterReadingType() {
        return meterReadingType;
    }

    /**
     * Imposta il valore della proprietà meterReadingType.
     * 
     * @param value
     *     allowed object is
     *     {@link MeterReadingTypeType }
     *     
     */
    public void setMeterReadingType(MeterReadingTypeType value) {
        this.meterReadingType = value;
    }

    /**
     * Recupera il valore della proprietà meterReadingTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link MeterReadingTypeCodeType }
     *     
     */
    public MeterReadingTypeCodeType getMeterReadingTypeCode() {
        return meterReadingTypeCode;
    }

    /**
     * Imposta il valore della proprietà meterReadingTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link MeterReadingTypeCodeType }
     *     
     */
    public void setMeterReadingTypeCode(MeterReadingTypeCodeType value) {
        this.meterReadingTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà previousMeterReadingDate.
     * 
     * @return
     *     possible object is
     *     {@link PreviousMeterReadingDateType }
     *     
     */
    public PreviousMeterReadingDateType getPreviousMeterReadingDate() {
        return previousMeterReadingDate;
    }

    /**
     * Imposta il valore della proprietà previousMeterReadingDate.
     * 
     * @param value
     *     allowed object is
     *     {@link PreviousMeterReadingDateType }
     *     
     */
    public void setPreviousMeterReadingDate(PreviousMeterReadingDateType value) {
        this.previousMeterReadingDate = value;
    }

    /**
     * Recupera il valore della proprietà previousMeterQuantity.
     * 
     * @return
     *     possible object is
     *     {@link PreviousMeterQuantityType }
     *     
     */
    public PreviousMeterQuantityType getPreviousMeterQuantity() {
        return previousMeterQuantity;
    }

    /**
     * Imposta il valore della proprietà previousMeterQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link PreviousMeterQuantityType }
     *     
     */
    public void setPreviousMeterQuantity(PreviousMeterQuantityType value) {
        this.previousMeterQuantity = value;
    }

    /**
     * Recupera il valore della proprietà latestMeterReadingDate.
     * 
     * @return
     *     possible object is
     *     {@link LatestMeterReadingDateType }
     *     
     */
    public LatestMeterReadingDateType getLatestMeterReadingDate() {
        return latestMeterReadingDate;
    }

    /**
     * Imposta il valore della proprietà latestMeterReadingDate.
     * 
     * @param value
     *     allowed object is
     *     {@link LatestMeterReadingDateType }
     *     
     */
    public void setLatestMeterReadingDate(LatestMeterReadingDateType value) {
        this.latestMeterReadingDate = value;
    }

    /**
     * Recupera il valore della proprietà latestMeterQuantity.
     * 
     * @return
     *     possible object is
     *     {@link LatestMeterQuantityType }
     *     
     */
    public LatestMeterQuantityType getLatestMeterQuantity() {
        return latestMeterQuantity;
    }

    /**
     * Imposta il valore della proprietà latestMeterQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link LatestMeterQuantityType }
     *     
     */
    public void setLatestMeterQuantity(LatestMeterQuantityType value) {
        this.latestMeterQuantity = value;
    }

    /**
     * Recupera il valore della proprietà previousMeterReadingMethod.
     * 
     * @return
     *     possible object is
     *     {@link PreviousMeterReadingMethodType }
     *     
     */
    public PreviousMeterReadingMethodType getPreviousMeterReadingMethod() {
        return previousMeterReadingMethod;
    }

    /**
     * Imposta il valore della proprietà previousMeterReadingMethod.
     * 
     * @param value
     *     allowed object is
     *     {@link PreviousMeterReadingMethodType }
     *     
     */
    public void setPreviousMeterReadingMethod(PreviousMeterReadingMethodType value) {
        this.previousMeterReadingMethod = value;
    }

    /**
     * Recupera il valore della proprietà previousMeterReadingMethodCode.
     * 
     * @return
     *     possible object is
     *     {@link PreviousMeterReadingMethodCodeType }
     *     
     */
    public PreviousMeterReadingMethodCodeType getPreviousMeterReadingMethodCode() {
        return previousMeterReadingMethodCode;
    }

    /**
     * Imposta il valore della proprietà previousMeterReadingMethodCode.
     * 
     * @param value
     *     allowed object is
     *     {@link PreviousMeterReadingMethodCodeType }
     *     
     */
    public void setPreviousMeterReadingMethodCode(PreviousMeterReadingMethodCodeType value) {
        this.previousMeterReadingMethodCode = value;
    }

    /**
     * Recupera il valore della proprietà latestMeterReadingMethod.
     * 
     * @return
     *     possible object is
     *     {@link LatestMeterReadingMethodType }
     *     
     */
    public LatestMeterReadingMethodType getLatestMeterReadingMethod() {
        return latestMeterReadingMethod;
    }

    /**
     * Imposta il valore della proprietà latestMeterReadingMethod.
     * 
     * @param value
     *     allowed object is
     *     {@link LatestMeterReadingMethodType }
     *     
     */
    public void setLatestMeterReadingMethod(LatestMeterReadingMethodType value) {
        this.latestMeterReadingMethod = value;
    }

    /**
     * Recupera il valore della proprietà latestMeterReadingMethodCode.
     * 
     * @return
     *     possible object is
     *     {@link LatestMeterReadingMethodCodeType }
     *     
     */
    public LatestMeterReadingMethodCodeType getLatestMeterReadingMethodCode() {
        return latestMeterReadingMethodCode;
    }

    /**
     * Imposta il valore della proprietà latestMeterReadingMethodCode.
     * 
     * @param value
     *     allowed object is
     *     {@link LatestMeterReadingMethodCodeType }
     *     
     */
    public void setLatestMeterReadingMethodCode(LatestMeterReadingMethodCodeType value) {
        this.latestMeterReadingMethodCode = value;
    }

    /**
     * Gets the value of the meterReadingComments property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the meterReadingComments property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getMeterReadingComments().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link MeterReadingCommentsType }
     * 
     * 
     */
    public List<MeterReadingCommentsType> getMeterReadingComments() {
        if (meterReadingComments == null) {
            meterReadingComments = new ArrayList<MeterReadingCommentsType>();
        }
        return this.meterReadingComments;
    }

    /**
     * Recupera il valore della proprietà deliveredQuantity.
     * 
     * @return
     *     possible object is
     *     {@link DeliveredQuantityType }
     *     
     */
    public DeliveredQuantityType getDeliveredQuantity() {
        return deliveredQuantity;
    }

    /**
     * Imposta il valore della proprietà deliveredQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveredQuantityType }
     *     
     */
    public void setDeliveredQuantity(DeliveredQuantityType value) {
        this.deliveredQuantity = value;
    }

}
