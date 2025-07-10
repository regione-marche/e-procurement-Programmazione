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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ApplicationDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SanitaryMeasureTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per SanitaryMeasureType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="SanitaryMeasureType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SanitaryMeasureTypeCode"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ApplicationDate" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SanitaryMeasureType", propOrder = {
    "ublExtensions",
    "sanitaryMeasureTypeCode",
    "applicationDate"
})
public class SanitaryMeasureType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "SanitaryMeasureTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected SanitaryMeasureTypeCodeType sanitaryMeasureTypeCode;
    @XmlElement(name = "ApplicationDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ApplicationDateType applicationDate;

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
     * Recupera il valore della proprietà sanitaryMeasureTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link SanitaryMeasureTypeCodeType }
     *     
     */
    public SanitaryMeasureTypeCodeType getSanitaryMeasureTypeCode() {
        return sanitaryMeasureTypeCode;
    }

    /**
     * Imposta il valore della proprietà sanitaryMeasureTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link SanitaryMeasureTypeCodeType }
     *     
     */
    public void setSanitaryMeasureTypeCode(SanitaryMeasureTypeCodeType value) {
        this.sanitaryMeasureTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà applicationDate.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationDateType }
     *     
     */
    public ApplicationDateType getApplicationDate() {
        return applicationDate;
    }

    /**
     * Imposta il valore della proprietà applicationDate.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationDateType }
     *     
     */
    public void setApplicationDate(ApplicationDateType value) {
        this.applicationDate = value;
    }

}
