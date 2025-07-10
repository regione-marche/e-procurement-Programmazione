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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BestBeforeDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ManufactureDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ManufactureTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ProductTraceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RegistrationIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SerialIDType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per ItemInstanceType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ItemInstanceType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ProductTraceID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ManufactureDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ManufactureTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}BestBeforeDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RegistrationID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SerialID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AdditionalItemProperty" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}LotIdentification" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemInstanceType", propOrder = {
    "ublExtensions",
    "productTraceID",
    "manufactureDate",
    "manufactureTime",
    "bestBeforeDate",
    "registrationID",
    "serialID",
    "additionalItemProperty",
    "lotIdentification"
})
public class ItemInstanceType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ProductTraceID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ProductTraceIDType productTraceID;
    @XmlElement(name = "ManufactureDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ManufactureDateType manufactureDate;
    @XmlElement(name = "ManufactureTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ManufactureTimeType manufactureTime;
    @XmlElement(name = "BestBeforeDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected BestBeforeDateType bestBeforeDate;
    @XmlElement(name = "RegistrationID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RegistrationIDType registrationID;
    @XmlElement(name = "SerialID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SerialIDType serialID;
    @XmlElement(name = "AdditionalItemProperty")
    protected List<ItemPropertyType> additionalItemProperty;
    @XmlElement(name = "LotIdentification")
    protected LotIdentificationType lotIdentification;

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
     * Recupera il valore della proprietà productTraceID.
     * 
     * @return
     *     possible object is
     *     {@link ProductTraceIDType }
     *     
     */
    public ProductTraceIDType getProductTraceID() {
        return productTraceID;
    }

    /**
     * Imposta il valore della proprietà productTraceID.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductTraceIDType }
     *     
     */
    public void setProductTraceID(ProductTraceIDType value) {
        this.productTraceID = value;
    }

    /**
     * Recupera il valore della proprietà manufactureDate.
     * 
     * @return
     *     possible object is
     *     {@link ManufactureDateType }
     *     
     */
    public ManufactureDateType getManufactureDate() {
        return manufactureDate;
    }

    /**
     * Imposta il valore della proprietà manufactureDate.
     * 
     * @param value
     *     allowed object is
     *     {@link ManufactureDateType }
     *     
     */
    public void setManufactureDate(ManufactureDateType value) {
        this.manufactureDate = value;
    }

    /**
     * Recupera il valore della proprietà manufactureTime.
     * 
     * @return
     *     possible object is
     *     {@link ManufactureTimeType }
     *     
     */
    public ManufactureTimeType getManufactureTime() {
        return manufactureTime;
    }

    /**
     * Imposta il valore della proprietà manufactureTime.
     * 
     * @param value
     *     allowed object is
     *     {@link ManufactureTimeType }
     *     
     */
    public void setManufactureTime(ManufactureTimeType value) {
        this.manufactureTime = value;
    }

    /**
     * Recupera il valore della proprietà bestBeforeDate.
     * 
     * @return
     *     possible object is
     *     {@link BestBeforeDateType }
     *     
     */
    public BestBeforeDateType getBestBeforeDate() {
        return bestBeforeDate;
    }

    /**
     * Imposta il valore della proprietà bestBeforeDate.
     * 
     * @param value
     *     allowed object is
     *     {@link BestBeforeDateType }
     *     
     */
    public void setBestBeforeDate(BestBeforeDateType value) {
        this.bestBeforeDate = value;
    }

    /**
     * Recupera il valore della proprietà registrationID.
     * 
     * @return
     *     possible object is
     *     {@link RegistrationIDType }
     *     
     */
    public RegistrationIDType getRegistrationID() {
        return registrationID;
    }

    /**
     * Imposta il valore della proprietà registrationID.
     * 
     * @param value
     *     allowed object is
     *     {@link RegistrationIDType }
     *     
     */
    public void setRegistrationID(RegistrationIDType value) {
        this.registrationID = value;
    }

    /**
     * Recupera il valore della proprietà serialID.
     * 
     * @return
     *     possible object is
     *     {@link SerialIDType }
     *     
     */
    public SerialIDType getSerialID() {
        return serialID;
    }

    /**
     * Imposta il valore della proprietà serialID.
     * 
     * @param value
     *     allowed object is
     *     {@link SerialIDType }
     *     
     */
    public void setSerialID(SerialIDType value) {
        this.serialID = value;
    }

    /**
     * Gets the value of the additionalItemProperty property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the additionalItemProperty property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAdditionalItemProperty().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ItemPropertyType }
     * 
     * 
     */
    public List<ItemPropertyType> getAdditionalItemProperty() {
        if (additionalItemProperty == null) {
            additionalItemProperty = new ArrayList<ItemPropertyType>();
        }
        return this.additionalItemProperty;
    }

    /**
     * Recupera il valore della proprietà lotIdentification.
     * 
     * @return
     *     possible object is
     *     {@link LotIdentificationType }
     *     
     */
    public LotIdentificationType getLotIdentification() {
        return lotIdentification;
    }

    /**
     * Imposta il valore della proprietà lotIdentification.
     * 
     * @param value
     *     allowed object is
     *     {@link LotIdentificationType }
     *     
     */
    public void setLotIdentification(LotIdentificationType value) {
        this.lotIdentification = value;
    }

}
