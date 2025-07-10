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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EstimatedGeneratedUntilNextPortMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaxDedicatedStorageCapacityMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RetainedOnBoardMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ToBeDeliveredMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.WasteTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per MaritimeWasteType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="MaritimeWasteType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Description" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}WasteTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ToBeDeliveredMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RetainedOnBoardMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MaxDedicatedStorageCapacityMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}EstimatedGeneratedUntilNextPortMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RemainingWasteDeliveryPortLocation" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaritimeWasteType", propOrder = {
    "ublExtensions",
    "id",
    "description",
    "wasteTypeCode",
    "toBeDeliveredMeasure",
    "retainedOnBoardMeasure",
    "maxDedicatedStorageCapacityMeasure",
    "estimatedGeneratedUntilNextPortMeasure",
    "remainingWasteDeliveryPortLocation"
})
public class MaritimeWasteType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "Description", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DescriptionType> description;
    @XmlElement(name = "WasteTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected WasteTypeCodeType wasteTypeCode;
    @XmlElement(name = "ToBeDeliveredMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ToBeDeliveredMeasureType toBeDeliveredMeasure;
    @XmlElement(name = "RetainedOnBoardMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RetainedOnBoardMeasureType retainedOnBoardMeasure;
    @XmlElement(name = "MaxDedicatedStorageCapacityMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MaxDedicatedStorageCapacityMeasureType maxDedicatedStorageCapacityMeasure;
    @XmlElement(name = "EstimatedGeneratedUntilNextPortMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected EstimatedGeneratedUntilNextPortMeasureType estimatedGeneratedUntilNextPortMeasure;
    @XmlElement(name = "RemainingWasteDeliveryPortLocation")
    protected List<LocationType> remainingWasteDeliveryPortLocation;

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
     * Gets the value of the description property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the description property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DescriptionType }
     * 
     * 
     */
    public List<DescriptionType> getDescription() {
        if (description == null) {
            description = new ArrayList<DescriptionType>();
        }
        return this.description;
    }

    /**
     * Recupera il valore della proprietà wasteTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link WasteTypeCodeType }
     *     
     */
    public WasteTypeCodeType getWasteTypeCode() {
        return wasteTypeCode;
    }

    /**
     * Imposta il valore della proprietà wasteTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link WasteTypeCodeType }
     *     
     */
    public void setWasteTypeCode(WasteTypeCodeType value) {
        this.wasteTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà toBeDeliveredMeasure.
     * 
     * @return
     *     possible object is
     *     {@link ToBeDeliveredMeasureType }
     *     
     */
    public ToBeDeliveredMeasureType getToBeDeliveredMeasure() {
        return toBeDeliveredMeasure;
    }

    /**
     * Imposta il valore della proprietà toBeDeliveredMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link ToBeDeliveredMeasureType }
     *     
     */
    public void setToBeDeliveredMeasure(ToBeDeliveredMeasureType value) {
        this.toBeDeliveredMeasure = value;
    }

    /**
     * Recupera il valore della proprietà retainedOnBoardMeasure.
     * 
     * @return
     *     possible object is
     *     {@link RetainedOnBoardMeasureType }
     *     
     */
    public RetainedOnBoardMeasureType getRetainedOnBoardMeasure() {
        return retainedOnBoardMeasure;
    }

    /**
     * Imposta il valore della proprietà retainedOnBoardMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link RetainedOnBoardMeasureType }
     *     
     */
    public void setRetainedOnBoardMeasure(RetainedOnBoardMeasureType value) {
        this.retainedOnBoardMeasure = value;
    }

    /**
     * Recupera il valore della proprietà maxDedicatedStorageCapacityMeasure.
     * 
     * @return
     *     possible object is
     *     {@link MaxDedicatedStorageCapacityMeasureType }
     *     
     */
    public MaxDedicatedStorageCapacityMeasureType getMaxDedicatedStorageCapacityMeasure() {
        return maxDedicatedStorageCapacityMeasure;
    }

    /**
     * Imposta il valore della proprietà maxDedicatedStorageCapacityMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link MaxDedicatedStorageCapacityMeasureType }
     *     
     */
    public void setMaxDedicatedStorageCapacityMeasure(MaxDedicatedStorageCapacityMeasureType value) {
        this.maxDedicatedStorageCapacityMeasure = value;
    }

    /**
     * Recupera il valore della proprietà estimatedGeneratedUntilNextPortMeasure.
     * 
     * @return
     *     possible object is
     *     {@link EstimatedGeneratedUntilNextPortMeasureType }
     *     
     */
    public EstimatedGeneratedUntilNextPortMeasureType getEstimatedGeneratedUntilNextPortMeasure() {
        return estimatedGeneratedUntilNextPortMeasure;
    }

    /**
     * Imposta il valore della proprietà estimatedGeneratedUntilNextPortMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link EstimatedGeneratedUntilNextPortMeasureType }
     *     
     */
    public void setEstimatedGeneratedUntilNextPortMeasure(EstimatedGeneratedUntilNextPortMeasureType value) {
        this.estimatedGeneratedUntilNextPortMeasure = value;
    }

    /**
     * Gets the value of the remainingWasteDeliveryPortLocation property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the remainingWasteDeliveryPortLocation property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getRemainingWasteDeliveryPortLocation().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link LocationType }
     * 
     * 
     */
    public List<LocationType> getRemainingWasteDeliveryPortLocation() {
        if (remainingWasteDeliveryPortLocation == null) {
            remainingWasteDeliveryPortLocation = new ArrayList<LocationType>();
        }
        return this.remainingWasteDeliveryPortLocation;
    }

}
