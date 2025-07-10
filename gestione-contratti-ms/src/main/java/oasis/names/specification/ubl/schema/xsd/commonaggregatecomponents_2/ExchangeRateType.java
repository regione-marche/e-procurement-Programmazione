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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CalculationRateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExchangeMarketIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MathematicOperatorCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SourceCurrencyBaseRateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SourceCurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TargetCurrencyBaseRateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TargetCurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per ExchangeRateType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ExchangeRateType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SourceCurrencyCode"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SourceCurrencyBaseRate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TargetCurrencyCode"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TargetCurrencyBaseRate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ExchangeMarketID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CalculationRate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MathematicOperatorCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Date" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ForeignExchangeContract" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExchangeRateType", propOrder = {
    "ublExtensions",
    "sourceCurrencyCode",
    "sourceCurrencyBaseRate",
    "targetCurrencyCode",
    "targetCurrencyBaseRate",
    "exchangeMarketID",
    "calculationRate",
    "mathematicOperatorCode",
    "date",
    "foreignExchangeContract"
})
public class ExchangeRateType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "SourceCurrencyCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected SourceCurrencyCodeType sourceCurrencyCode;
    @XmlElement(name = "SourceCurrencyBaseRate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SourceCurrencyBaseRateType sourceCurrencyBaseRate;
    @XmlElement(name = "TargetCurrencyCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected TargetCurrencyCodeType targetCurrencyCode;
    @XmlElement(name = "TargetCurrencyBaseRate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TargetCurrencyBaseRateType targetCurrencyBaseRate;
    @XmlElement(name = "ExchangeMarketID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ExchangeMarketIDType exchangeMarketID;
    @XmlElement(name = "CalculationRate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CalculationRateType calculationRate;
    @XmlElement(name = "MathematicOperatorCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MathematicOperatorCodeType mathematicOperatorCode;
    @XmlElement(name = "Date", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected DateType date;
    @XmlElement(name = "ForeignExchangeContract")
    protected ContractType foreignExchangeContract;

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
     * Recupera il valore della proprietà sourceCurrencyCode.
     * 
     * @return
     *     possible object is
     *     {@link SourceCurrencyCodeType }
     *     
     */
    public SourceCurrencyCodeType getSourceCurrencyCode() {
        return sourceCurrencyCode;
    }

    /**
     * Imposta il valore della proprietà sourceCurrencyCode.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceCurrencyCodeType }
     *     
     */
    public void setSourceCurrencyCode(SourceCurrencyCodeType value) {
        this.sourceCurrencyCode = value;
    }

    /**
     * Recupera il valore della proprietà sourceCurrencyBaseRate.
     * 
     * @return
     *     possible object is
     *     {@link SourceCurrencyBaseRateType }
     *     
     */
    public SourceCurrencyBaseRateType getSourceCurrencyBaseRate() {
        return sourceCurrencyBaseRate;
    }

    /**
     * Imposta il valore della proprietà sourceCurrencyBaseRate.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceCurrencyBaseRateType }
     *     
     */
    public void setSourceCurrencyBaseRate(SourceCurrencyBaseRateType value) {
        this.sourceCurrencyBaseRate = value;
    }

    /**
     * Recupera il valore della proprietà targetCurrencyCode.
     * 
     * @return
     *     possible object is
     *     {@link TargetCurrencyCodeType }
     *     
     */
    public TargetCurrencyCodeType getTargetCurrencyCode() {
        return targetCurrencyCode;
    }

    /**
     * Imposta il valore della proprietà targetCurrencyCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TargetCurrencyCodeType }
     *     
     */
    public void setTargetCurrencyCode(TargetCurrencyCodeType value) {
        this.targetCurrencyCode = value;
    }

    /**
     * Recupera il valore della proprietà targetCurrencyBaseRate.
     * 
     * @return
     *     possible object is
     *     {@link TargetCurrencyBaseRateType }
     *     
     */
    public TargetCurrencyBaseRateType getTargetCurrencyBaseRate() {
        return targetCurrencyBaseRate;
    }

    /**
     * Imposta il valore della proprietà targetCurrencyBaseRate.
     * 
     * @param value
     *     allowed object is
     *     {@link TargetCurrencyBaseRateType }
     *     
     */
    public void setTargetCurrencyBaseRate(TargetCurrencyBaseRateType value) {
        this.targetCurrencyBaseRate = value;
    }

    /**
     * Recupera il valore della proprietà exchangeMarketID.
     * 
     * @return
     *     possible object is
     *     {@link ExchangeMarketIDType }
     *     
     */
    public ExchangeMarketIDType getExchangeMarketID() {
        return exchangeMarketID;
    }

    /**
     * Imposta il valore della proprietà exchangeMarketID.
     * 
     * @param value
     *     allowed object is
     *     {@link ExchangeMarketIDType }
     *     
     */
    public void setExchangeMarketID(ExchangeMarketIDType value) {
        this.exchangeMarketID = value;
    }

    /**
     * Recupera il valore della proprietà calculationRate.
     * 
     * @return
     *     possible object is
     *     {@link CalculationRateType }
     *     
     */
    public CalculationRateType getCalculationRate() {
        return calculationRate;
    }

    /**
     * Imposta il valore della proprietà calculationRate.
     * 
     * @param value
     *     allowed object is
     *     {@link CalculationRateType }
     *     
     */
    public void setCalculationRate(CalculationRateType value) {
        this.calculationRate = value;
    }

    /**
     * Recupera il valore della proprietà mathematicOperatorCode.
     * 
     * @return
     *     possible object is
     *     {@link MathematicOperatorCodeType }
     *     
     */
    public MathematicOperatorCodeType getMathematicOperatorCode() {
        return mathematicOperatorCode;
    }

    /**
     * Imposta il valore della proprietà mathematicOperatorCode.
     * 
     * @param value
     *     allowed object is
     *     {@link MathematicOperatorCodeType }
     *     
     */
    public void setMathematicOperatorCode(MathematicOperatorCodeType value) {
        this.mathematicOperatorCode = value;
    }

    /**
     * Recupera il valore della proprietà date.
     * 
     * @return
     *     possible object is
     *     {@link DateType }
     *     
     */
    public DateType getDate() {
        return date;
    }

    /**
     * Imposta il valore della proprietà date.
     * 
     * @param value
     *     allowed object is
     *     {@link DateType }
     *     
     */
    public void setDate(DateType value) {
        this.date = value;
    }

    /**
     * Recupera il valore della proprietà foreignExchangeContract.
     * 
     * @return
     *     possible object is
     *     {@link ContractType }
     *     
     */
    public ContractType getForeignExchangeContract() {
        return foreignExchangeContract;
    }

    /**
     * Imposta il valore della proprietà foreignExchangeContract.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractType }
     *     
     */
    public void setForeignExchangeContract(ContractType value) {
        this.foreignExchangeContract = value;
    }

}
