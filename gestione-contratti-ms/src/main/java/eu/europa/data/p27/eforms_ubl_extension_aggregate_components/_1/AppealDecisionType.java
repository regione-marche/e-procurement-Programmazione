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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.DecisionTypeCodeType;


/**
 * &lt;p&gt;Classe Java per AppealDecisionType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="AppealDecisionType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}DecisionTypeCode"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppealDecisionType", propOrder = {
    "decisionTypeCode"
})
public class AppealDecisionType {

    @XmlElement(name = "DecisionTypeCode", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1", required = true)
    protected DecisionTypeCodeType decisionTypeCode;

    /**
     * Recupera il valore della proprietà decisionTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link DecisionTypeCodeType }
     *     
     */
    public DecisionTypeCodeType getDecisionTypeCode() {
        return decisionTypeCode;
    }

    /**
     * Imposta il valore della proprietà decisionTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link DecisionTypeCodeType }
     *     
     */
    public void setDecisionTypeCode(DecisionTypeCodeType value) {
        this.decisionTypeCode = value;
    }

}
