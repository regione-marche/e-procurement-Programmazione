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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ComparisonDataSourceCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DataSourceCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ForecastPurposeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ForecastTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TimeDeltaDaysQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per ForecastExceptionCriterionLineType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ForecastExceptionCriterionLineType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ForecastPurposeCode"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ForecastTypeCode"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ComparisonDataSourceCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DataSourceCode"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TimeDeltaDaysQuantity" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ForecastExceptionCriterionLineType", propOrder = {
    "ublExtensions",
    "forecastPurposeCode",
    "forecastTypeCode",
    "comparisonDataSourceCode",
    "dataSourceCode",
    "timeDeltaDaysQuantity"
})
public class ForecastExceptionCriterionLineType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ForecastPurposeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected ForecastPurposeCodeType forecastPurposeCode;
    @XmlElement(name = "ForecastTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected ForecastTypeCodeType forecastTypeCode;
    @XmlElement(name = "ComparisonDataSourceCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ComparisonDataSourceCodeType comparisonDataSourceCode;
    @XmlElement(name = "DataSourceCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected DataSourceCodeType dataSourceCode;
    @XmlElement(name = "TimeDeltaDaysQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TimeDeltaDaysQuantityType timeDeltaDaysQuantity;

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
     * Recupera il valore della proprietà forecastPurposeCode.
     * 
     * @return
     *     possible object is
     *     {@link ForecastPurposeCodeType }
     *     
     */
    public ForecastPurposeCodeType getForecastPurposeCode() {
        return forecastPurposeCode;
    }

    /**
     * Imposta il valore della proprietà forecastPurposeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ForecastPurposeCodeType }
     *     
     */
    public void setForecastPurposeCode(ForecastPurposeCodeType value) {
        this.forecastPurposeCode = value;
    }

    /**
     * Recupera il valore della proprietà forecastTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link ForecastTypeCodeType }
     *     
     */
    public ForecastTypeCodeType getForecastTypeCode() {
        return forecastTypeCode;
    }

    /**
     * Imposta il valore della proprietà forecastTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ForecastTypeCodeType }
     *     
     */
    public void setForecastTypeCode(ForecastTypeCodeType value) {
        this.forecastTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà comparisonDataSourceCode.
     * 
     * @return
     *     possible object is
     *     {@link ComparisonDataSourceCodeType }
     *     
     */
    public ComparisonDataSourceCodeType getComparisonDataSourceCode() {
        return comparisonDataSourceCode;
    }

    /**
     * Imposta il valore della proprietà comparisonDataSourceCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ComparisonDataSourceCodeType }
     *     
     */
    public void setComparisonDataSourceCode(ComparisonDataSourceCodeType value) {
        this.comparisonDataSourceCode = value;
    }

    /**
     * Recupera il valore della proprietà dataSourceCode.
     * 
     * @return
     *     possible object is
     *     {@link DataSourceCodeType }
     *     
     */
    public DataSourceCodeType getDataSourceCode() {
        return dataSourceCode;
    }

    /**
     * Imposta il valore della proprietà dataSourceCode.
     * 
     * @param value
     *     allowed object is
     *     {@link DataSourceCodeType }
     *     
     */
    public void setDataSourceCode(DataSourceCodeType value) {
        this.dataSourceCode = value;
    }

    /**
     * Recupera il valore della proprietà timeDeltaDaysQuantity.
     * 
     * @return
     *     possible object is
     *     {@link TimeDeltaDaysQuantityType }
     *     
     */
    public TimeDeltaDaysQuantityType getTimeDeltaDaysQuantity() {
        return timeDeltaDaysQuantity;
    }

    /**
     * Imposta il valore della proprietà timeDeltaDaysQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeDeltaDaysQuantityType }
     *     
     */
    public void setTimeDeltaDaysQuantity(TimeDeltaDaysQuantityType value) {
        this.timeDeltaDaysQuantity = value;
    }

}
