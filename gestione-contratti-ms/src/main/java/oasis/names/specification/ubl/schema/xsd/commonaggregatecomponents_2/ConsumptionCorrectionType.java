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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ActualTemperatureReductionQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ConsumptionEnergyQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ConsumptionWaterQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CorrectionAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CorrectionTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CorrectionTypeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CorrectionUnitAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DifferenceTemperatureReductionQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.GasPressureQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MeterNumberType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NormalTemperatureReductionQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per ConsumptionCorrectionType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ConsumptionCorrectionType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CorrectionType" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CorrectionTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MeterNumber" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}GasPressureQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ActualTemperatureReductionQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}NormalTemperatureReductionQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DifferenceTemperatureReductionQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Description" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CorrectionUnitAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ConsumptionEnergyQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ConsumptionWaterQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CorrectionAmount" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsumptionCorrectionType", propOrder = {
    "ublExtensions",
    "correctionType",
    "correctionTypeCode",
    "meterNumber",
    "gasPressureQuantity",
    "actualTemperatureReductionQuantity",
    "normalTemperatureReductionQuantity",
    "differenceTemperatureReductionQuantity",
    "description",
    "correctionUnitAmount",
    "consumptionEnergyQuantity",
    "consumptionWaterQuantity",
    "correctionAmount"
})
public class ConsumptionCorrectionType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "CorrectionType", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CorrectionTypeType correctionType;
    @XmlElement(name = "CorrectionTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CorrectionTypeCodeType correctionTypeCode;
    @XmlElement(name = "MeterNumber", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MeterNumberType meterNumber;
    @XmlElement(name = "GasPressureQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected GasPressureQuantityType gasPressureQuantity;
    @XmlElement(name = "ActualTemperatureReductionQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ActualTemperatureReductionQuantityType actualTemperatureReductionQuantity;
    @XmlElement(name = "NormalTemperatureReductionQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NormalTemperatureReductionQuantityType normalTemperatureReductionQuantity;
    @XmlElement(name = "DifferenceTemperatureReductionQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected DifferenceTemperatureReductionQuantityType differenceTemperatureReductionQuantity;
    @XmlElement(name = "Description", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DescriptionType> description;
    @XmlElement(name = "CorrectionUnitAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CorrectionUnitAmountType correctionUnitAmount;
    @XmlElement(name = "ConsumptionEnergyQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ConsumptionEnergyQuantityType consumptionEnergyQuantity;
    @XmlElement(name = "ConsumptionWaterQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ConsumptionWaterQuantityType consumptionWaterQuantity;
    @XmlElement(name = "CorrectionAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CorrectionAmountType correctionAmount;

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
     * Recupera il valore della proprietà correctionType.
     * 
     * @return
     *     possible object is
     *     {@link CorrectionTypeType }
     *     
     */
    public CorrectionTypeType getCorrectionType() {
        return correctionType;
    }

    /**
     * Imposta il valore della proprietà correctionType.
     * 
     * @param value
     *     allowed object is
     *     {@link CorrectionTypeType }
     *     
     */
    public void setCorrectionType(CorrectionTypeType value) {
        this.correctionType = value;
    }

    /**
     * Recupera il valore della proprietà correctionTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link CorrectionTypeCodeType }
     *     
     */
    public CorrectionTypeCodeType getCorrectionTypeCode() {
        return correctionTypeCode;
    }

    /**
     * Imposta il valore della proprietà correctionTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link CorrectionTypeCodeType }
     *     
     */
    public void setCorrectionTypeCode(CorrectionTypeCodeType value) {
        this.correctionTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà meterNumber.
     * 
     * @return
     *     possible object is
     *     {@link MeterNumberType }
     *     
     */
    public MeterNumberType getMeterNumber() {
        return meterNumber;
    }

    /**
     * Imposta il valore della proprietà meterNumber.
     * 
     * @param value
     *     allowed object is
     *     {@link MeterNumberType }
     *     
     */
    public void setMeterNumber(MeterNumberType value) {
        this.meterNumber = value;
    }

    /**
     * Recupera il valore della proprietà gasPressureQuantity.
     * 
     * @return
     *     possible object is
     *     {@link GasPressureQuantityType }
     *     
     */
    public GasPressureQuantityType getGasPressureQuantity() {
        return gasPressureQuantity;
    }

    /**
     * Imposta il valore della proprietà gasPressureQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link GasPressureQuantityType }
     *     
     */
    public void setGasPressureQuantity(GasPressureQuantityType value) {
        this.gasPressureQuantity = value;
    }

    /**
     * Recupera il valore della proprietà actualTemperatureReductionQuantity.
     * 
     * @return
     *     possible object is
     *     {@link ActualTemperatureReductionQuantityType }
     *     
     */
    public ActualTemperatureReductionQuantityType getActualTemperatureReductionQuantity() {
        return actualTemperatureReductionQuantity;
    }

    /**
     * Imposta il valore della proprietà actualTemperatureReductionQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link ActualTemperatureReductionQuantityType }
     *     
     */
    public void setActualTemperatureReductionQuantity(ActualTemperatureReductionQuantityType value) {
        this.actualTemperatureReductionQuantity = value;
    }

    /**
     * Recupera il valore della proprietà normalTemperatureReductionQuantity.
     * 
     * @return
     *     possible object is
     *     {@link NormalTemperatureReductionQuantityType }
     *     
     */
    public NormalTemperatureReductionQuantityType getNormalTemperatureReductionQuantity() {
        return normalTemperatureReductionQuantity;
    }

    /**
     * Imposta il valore della proprietà normalTemperatureReductionQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link NormalTemperatureReductionQuantityType }
     *     
     */
    public void setNormalTemperatureReductionQuantity(NormalTemperatureReductionQuantityType value) {
        this.normalTemperatureReductionQuantity = value;
    }

    /**
     * Recupera il valore della proprietà differenceTemperatureReductionQuantity.
     * 
     * @return
     *     possible object is
     *     {@link DifferenceTemperatureReductionQuantityType }
     *     
     */
    public DifferenceTemperatureReductionQuantityType getDifferenceTemperatureReductionQuantity() {
        return differenceTemperatureReductionQuantity;
    }

    /**
     * Imposta il valore della proprietà differenceTemperatureReductionQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link DifferenceTemperatureReductionQuantityType }
     *     
     */
    public void setDifferenceTemperatureReductionQuantity(DifferenceTemperatureReductionQuantityType value) {
        this.differenceTemperatureReductionQuantity = value;
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
     * Recupera il valore della proprietà correctionUnitAmount.
     * 
     * @return
     *     possible object is
     *     {@link CorrectionUnitAmountType }
     *     
     */
    public CorrectionUnitAmountType getCorrectionUnitAmount() {
        return correctionUnitAmount;
    }

    /**
     * Imposta il valore della proprietà correctionUnitAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link CorrectionUnitAmountType }
     *     
     */
    public void setCorrectionUnitAmount(CorrectionUnitAmountType value) {
        this.correctionUnitAmount = value;
    }

    /**
     * Recupera il valore della proprietà consumptionEnergyQuantity.
     * 
     * @return
     *     possible object is
     *     {@link ConsumptionEnergyQuantityType }
     *     
     */
    public ConsumptionEnergyQuantityType getConsumptionEnergyQuantity() {
        return consumptionEnergyQuantity;
    }

    /**
     * Imposta il valore della proprietà consumptionEnergyQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsumptionEnergyQuantityType }
     *     
     */
    public void setConsumptionEnergyQuantity(ConsumptionEnergyQuantityType value) {
        this.consumptionEnergyQuantity = value;
    }

    /**
     * Recupera il valore della proprietà consumptionWaterQuantity.
     * 
     * @return
     *     possible object is
     *     {@link ConsumptionWaterQuantityType }
     *     
     */
    public ConsumptionWaterQuantityType getConsumptionWaterQuantity() {
        return consumptionWaterQuantity;
    }

    /**
     * Imposta il valore della proprietà consumptionWaterQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsumptionWaterQuantityType }
     *     
     */
    public void setConsumptionWaterQuantity(ConsumptionWaterQuantityType value) {
        this.consumptionWaterQuantity = value;
    }

    /**
     * Recupera il valore della proprietà correctionAmount.
     * 
     * @return
     *     possible object is
     *     {@link CorrectionAmountType }
     *     
     */
    public CorrectionAmountType getCorrectionAmount() {
        return correctionAmount;
    }

    /**
     * Imposta il valore della proprietà correctionAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link CorrectionAmountType }
     *     
     */
    public void setCorrectionAmount(CorrectionAmountType value) {
        this.correctionAmount = value;
    }

}
