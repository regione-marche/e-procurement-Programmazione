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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AirFlowPercentType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AnimalFoodApprovedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DangerousGoodsApprovedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.GateIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HumanFoodApprovedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HumidityPercentType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PowerIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RefrigeratedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per StorageType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="StorageType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Name" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}GateID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AirFlowPercent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HumidityPercent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AnimalFoodApprovedIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HumanFoodApprovedIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DangerousGoodsApprovedIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RefrigeratedIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PowerIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}MinimumTemperature" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}MaximumTemperature" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Certificate" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StorageType", propOrder = {
    "ublExtensions",
    "id",
    "name",
    "gateID",
    "airFlowPercent",
    "humidityPercent",
    "animalFoodApprovedIndicator",
    "humanFoodApprovedIndicator",
    "dangerousGoodsApprovedIndicator",
    "refrigeratedIndicator",
    "powerIndicator",
    "minimumTemperature",
    "maximumTemperature",
    "certificate"
})
public class StorageType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected IDType id;
    @XmlElement(name = "Name", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NameType name;
    @XmlElement(name = "GateID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected GateIDType gateID;
    @XmlElement(name = "AirFlowPercent", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected AirFlowPercentType airFlowPercent;
    @XmlElement(name = "HumidityPercent", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected HumidityPercentType humidityPercent;
    @XmlElement(name = "AnimalFoodApprovedIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected AnimalFoodApprovedIndicatorType animalFoodApprovedIndicator;
    @XmlElement(name = "HumanFoodApprovedIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected HumanFoodApprovedIndicatorType humanFoodApprovedIndicator;
    @XmlElement(name = "DangerousGoodsApprovedIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected DangerousGoodsApprovedIndicatorType dangerousGoodsApprovedIndicator;
    @XmlElement(name = "RefrigeratedIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RefrigeratedIndicatorType refrigeratedIndicator;
    @XmlElement(name = "PowerIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PowerIndicatorType powerIndicator;
    @XmlElement(name = "MinimumTemperature")
    protected TemperatureType minimumTemperature;
    @XmlElement(name = "MaximumTemperature")
    protected TemperatureType maximumTemperature;
    @XmlElement(name = "Certificate")
    protected List<CertificateType> certificate;

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
     * Recupera il valore della proprietà name.
     * 
     * @return
     *     possible object is
     *     {@link NameType }
     *     
     */
    public NameType getName() {
        return name;
    }

    /**
     * Imposta il valore della proprietà name.
     * 
     * @param value
     *     allowed object is
     *     {@link NameType }
     *     
     */
    public void setName(NameType value) {
        this.name = value;
    }

    /**
     * Recupera il valore della proprietà gateID.
     * 
     * @return
     *     possible object is
     *     {@link GateIDType }
     *     
     */
    public GateIDType getGateID() {
        return gateID;
    }

    /**
     * Imposta il valore della proprietà gateID.
     * 
     * @param value
     *     allowed object is
     *     {@link GateIDType }
     *     
     */
    public void setGateID(GateIDType value) {
        this.gateID = value;
    }

    /**
     * Recupera il valore della proprietà airFlowPercent.
     * 
     * @return
     *     possible object is
     *     {@link AirFlowPercentType }
     *     
     */
    public AirFlowPercentType getAirFlowPercent() {
        return airFlowPercent;
    }

    /**
     * Imposta il valore della proprietà airFlowPercent.
     * 
     * @param value
     *     allowed object is
     *     {@link AirFlowPercentType }
     *     
     */
    public void setAirFlowPercent(AirFlowPercentType value) {
        this.airFlowPercent = value;
    }

    /**
     * Recupera il valore della proprietà humidityPercent.
     * 
     * @return
     *     possible object is
     *     {@link HumidityPercentType }
     *     
     */
    public HumidityPercentType getHumidityPercent() {
        return humidityPercent;
    }

    /**
     * Imposta il valore della proprietà humidityPercent.
     * 
     * @param value
     *     allowed object is
     *     {@link HumidityPercentType }
     *     
     */
    public void setHumidityPercent(HumidityPercentType value) {
        this.humidityPercent = value;
    }

    /**
     * Recupera il valore della proprietà animalFoodApprovedIndicator.
     * 
     * @return
     *     possible object is
     *     {@link AnimalFoodApprovedIndicatorType }
     *     
     */
    public AnimalFoodApprovedIndicatorType getAnimalFoodApprovedIndicator() {
        return animalFoodApprovedIndicator;
    }

    /**
     * Imposta il valore della proprietà animalFoodApprovedIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link AnimalFoodApprovedIndicatorType }
     *     
     */
    public void setAnimalFoodApprovedIndicator(AnimalFoodApprovedIndicatorType value) {
        this.animalFoodApprovedIndicator = value;
    }

    /**
     * Recupera il valore della proprietà humanFoodApprovedIndicator.
     * 
     * @return
     *     possible object is
     *     {@link HumanFoodApprovedIndicatorType }
     *     
     */
    public HumanFoodApprovedIndicatorType getHumanFoodApprovedIndicator() {
        return humanFoodApprovedIndicator;
    }

    /**
     * Imposta il valore della proprietà humanFoodApprovedIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link HumanFoodApprovedIndicatorType }
     *     
     */
    public void setHumanFoodApprovedIndicator(HumanFoodApprovedIndicatorType value) {
        this.humanFoodApprovedIndicator = value;
    }

    /**
     * Recupera il valore della proprietà dangerousGoodsApprovedIndicator.
     * 
     * @return
     *     possible object is
     *     {@link DangerousGoodsApprovedIndicatorType }
     *     
     */
    public DangerousGoodsApprovedIndicatorType getDangerousGoodsApprovedIndicator() {
        return dangerousGoodsApprovedIndicator;
    }

    /**
     * Imposta il valore della proprietà dangerousGoodsApprovedIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link DangerousGoodsApprovedIndicatorType }
     *     
     */
    public void setDangerousGoodsApprovedIndicator(DangerousGoodsApprovedIndicatorType value) {
        this.dangerousGoodsApprovedIndicator = value;
    }

    /**
     * Recupera il valore della proprietà refrigeratedIndicator.
     * 
     * @return
     *     possible object is
     *     {@link RefrigeratedIndicatorType }
     *     
     */
    public RefrigeratedIndicatorType getRefrigeratedIndicator() {
        return refrigeratedIndicator;
    }

    /**
     * Imposta il valore della proprietà refrigeratedIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link RefrigeratedIndicatorType }
     *     
     */
    public void setRefrigeratedIndicator(RefrigeratedIndicatorType value) {
        this.refrigeratedIndicator = value;
    }

    /**
     * Recupera il valore della proprietà powerIndicator.
     * 
     * @return
     *     possible object is
     *     {@link PowerIndicatorType }
     *     
     */
    public PowerIndicatorType getPowerIndicator() {
        return powerIndicator;
    }

    /**
     * Imposta il valore della proprietà powerIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link PowerIndicatorType }
     *     
     */
    public void setPowerIndicator(PowerIndicatorType value) {
        this.powerIndicator = value;
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
     * Gets the value of the certificate property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the certificate property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCertificate().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CertificateType }
     * 
     * 
     */
    public List<CertificateType> getCertificate() {
        if (certificate == null) {
            certificate = new ArrayList<CertificateType>();
        }
        return this.certificate;
    }

}
