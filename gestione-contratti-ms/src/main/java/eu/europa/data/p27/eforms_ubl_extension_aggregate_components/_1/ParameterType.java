//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ParameterCodeType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ParameterNumericType;


/**
 * &lt;p&gt;Classe Java per ParameterType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ParameterType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}ParameterCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}ParameterNumeric"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParameterType", propOrder = {
    "parameterCode",
    "parameterNumeric"
})
public class ParameterType {

    @XmlElement(name = "ParameterCode", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected ParameterCodeType parameterCode;
    @XmlElement(name = "ParameterNumeric", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1", required = true)
    protected ParameterNumericType parameterNumeric;

    /**
     * Recupera il valore della proprietà parameterCode.
     * 
     * @return
     *     possible object is
     *     {@link ParameterCodeType }
     *     
     */
    public ParameterCodeType getParameterCode() {
        return parameterCode;
    }

    /**
     * Imposta il valore della proprietà parameterCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ParameterCodeType }
     *     
     */
    public void setParameterCode(ParameterCodeType value) {
        this.parameterCode = value;
    }

    /**
     * Recupera il valore della proprietà parameterNumeric.
     * 
     * @return
     *     possible object is
     *     {@link ParameterNumericType }
     *     
     */
    public ParameterNumericType getParameterNumeric() {
        return parameterNumeric;
    }

    /**
     * Imposta il valore della proprietà parameterNumeric.
     * 
     * @param value
     *     allowed object is
     *     {@link ParameterNumericType }
     *     
     */
    public void setParameterNumeric(ParameterNumericType value) {
        this.parameterNumeric = value;
    }

}
