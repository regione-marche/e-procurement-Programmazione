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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ChangedSectionIdentifierType;


/**
 * &lt;p&gt;Classe Java per ChangedSectionType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ChangedSectionType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}ChangedSectionIdentifier" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChangedSectionType", propOrder = {
    "changedSectionIdentifier"
})
public class ChangedSectionType {

    @XmlElement(name = "ChangedSectionIdentifier", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected ChangedSectionIdentifierType changedSectionIdentifier;

    /**
     * Recupera il valore della proprietà changedSectionIdentifier.
     * 
     * @return
     *     possible object is
     *     {@link ChangedSectionIdentifierType }
     *     
     */
    public ChangedSectionIdentifierType getChangedSectionIdentifier() {
        return changedSectionIdentifier;
    }

    /**
     * Imposta il valore della proprietà changedSectionIdentifier.
     * 
     * @param value
     *     allowed object is
     *     {@link ChangedSectionIdentifierType }
     *     
     */
    public void setChangedSectionIdentifier(ChangedSectionIdentifierType value) {
        this.changedSectionIdentifier = value;
    }

}
