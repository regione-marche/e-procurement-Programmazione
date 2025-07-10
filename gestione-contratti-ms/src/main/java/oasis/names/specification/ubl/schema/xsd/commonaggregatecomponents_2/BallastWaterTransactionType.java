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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExchangeMethodCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExchangedPercentType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SalinityMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SeaHeightMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TankIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TankTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TransactionDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.VolumeMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per BallastWaterTransactionType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="BallastWaterTransactionType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TankID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TankTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ExchangeMethodCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ExchangedPercent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}VolumeMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SeaHeightMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SalinityMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TransactionDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Location" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}BallastWaterTemperature" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BallastWaterTransactionType", propOrder = {
    "ublExtensions",
    "tankID",
    "tankTypeCode",
    "exchangeMethodCode",
    "exchangedPercent",
    "volumeMeasure",
    "seaHeightMeasure",
    "salinityMeasure",
    "transactionDate",
    "location",
    "ballastWaterTemperature"
})
public class BallastWaterTransactionType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "TankID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TankIDType tankID;
    @XmlElement(name = "TankTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TankTypeCodeType tankTypeCode;
    @XmlElement(name = "ExchangeMethodCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ExchangeMethodCodeType exchangeMethodCode;
    @XmlElement(name = "ExchangedPercent", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ExchangedPercentType exchangedPercent;
    @XmlElement(name = "VolumeMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected VolumeMeasureType volumeMeasure;
    @XmlElement(name = "SeaHeightMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SeaHeightMeasureType seaHeightMeasure;
    @XmlElement(name = "SalinityMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SalinityMeasureType salinityMeasure;
    @XmlElement(name = "TransactionDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TransactionDateType transactionDate;
    @XmlElement(name = "Location")
    protected LocationType location;
    @XmlElement(name = "BallastWaterTemperature")
    protected TemperatureType ballastWaterTemperature;

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
     * Recupera il valore della proprietà tankID.
     * 
     * @return
     *     possible object is
     *     {@link TankIDType }
     *     
     */
    public TankIDType getTankID() {
        return tankID;
    }

    /**
     * Imposta il valore della proprietà tankID.
     * 
     * @param value
     *     allowed object is
     *     {@link TankIDType }
     *     
     */
    public void setTankID(TankIDType value) {
        this.tankID = value;
    }

    /**
     * Recupera il valore della proprietà tankTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link TankTypeCodeType }
     *     
     */
    public TankTypeCodeType getTankTypeCode() {
        return tankTypeCode;
    }

    /**
     * Imposta il valore della proprietà tankTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TankTypeCodeType }
     *     
     */
    public void setTankTypeCode(TankTypeCodeType value) {
        this.tankTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà exchangeMethodCode.
     * 
     * @return
     *     possible object is
     *     {@link ExchangeMethodCodeType }
     *     
     */
    public ExchangeMethodCodeType getExchangeMethodCode() {
        return exchangeMethodCode;
    }

    /**
     * Imposta il valore della proprietà exchangeMethodCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ExchangeMethodCodeType }
     *     
     */
    public void setExchangeMethodCode(ExchangeMethodCodeType value) {
        this.exchangeMethodCode = value;
    }

    /**
     * Recupera il valore della proprietà exchangedPercent.
     * 
     * @return
     *     possible object is
     *     {@link ExchangedPercentType }
     *     
     */
    public ExchangedPercentType getExchangedPercent() {
        return exchangedPercent;
    }

    /**
     * Imposta il valore della proprietà exchangedPercent.
     * 
     * @param value
     *     allowed object is
     *     {@link ExchangedPercentType }
     *     
     */
    public void setExchangedPercent(ExchangedPercentType value) {
        this.exchangedPercent = value;
    }

    /**
     * Recupera il valore della proprietà volumeMeasure.
     * 
     * @return
     *     possible object is
     *     {@link VolumeMeasureType }
     *     
     */
    public VolumeMeasureType getVolumeMeasure() {
        return volumeMeasure;
    }

    /**
     * Imposta il valore della proprietà volumeMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link VolumeMeasureType }
     *     
     */
    public void setVolumeMeasure(VolumeMeasureType value) {
        this.volumeMeasure = value;
    }

    /**
     * Recupera il valore della proprietà seaHeightMeasure.
     * 
     * @return
     *     possible object is
     *     {@link SeaHeightMeasureType }
     *     
     */
    public SeaHeightMeasureType getSeaHeightMeasure() {
        return seaHeightMeasure;
    }

    /**
     * Imposta il valore della proprietà seaHeightMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link SeaHeightMeasureType }
     *     
     */
    public void setSeaHeightMeasure(SeaHeightMeasureType value) {
        this.seaHeightMeasure = value;
    }

    /**
     * Recupera il valore della proprietà salinityMeasure.
     * 
     * @return
     *     possible object is
     *     {@link SalinityMeasureType }
     *     
     */
    public SalinityMeasureType getSalinityMeasure() {
        return salinityMeasure;
    }

    /**
     * Imposta il valore della proprietà salinityMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link SalinityMeasureType }
     *     
     */
    public void setSalinityMeasure(SalinityMeasureType value) {
        this.salinityMeasure = value;
    }

    /**
     * Recupera il valore della proprietà transactionDate.
     * 
     * @return
     *     possible object is
     *     {@link TransactionDateType }
     *     
     */
    public TransactionDateType getTransactionDate() {
        return transactionDate;
    }

    /**
     * Imposta il valore della proprietà transactionDate.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionDateType }
     *     
     */
    public void setTransactionDate(TransactionDateType value) {
        this.transactionDate = value;
    }

    /**
     * Recupera il valore della proprietà location.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getLocation() {
        return location;
    }

    /**
     * Imposta il valore della proprietà location.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setLocation(LocationType value) {
        this.location = value;
    }

    /**
     * Recupera il valore della proprietà ballastWaterTemperature.
     * 
     * @return
     *     possible object is
     *     {@link TemperatureType }
     *     
     */
    public TemperatureType getBallastWaterTemperature() {
        return ballastWaterTemperature;
    }

    /**
     * Imposta il valore della proprietà ballastWaterTemperature.
     * 
     * @param value
     *     allowed object is
     *     {@link TemperatureType }
     *     
     */
    public void setBallastWaterTemperature(TemperatureType value) {
        this.ballastWaterTemperature = value;
    }

}
