//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.TenderSubcontractingRequirementsCodeType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.TenderSubcontractingRequirementsDescriptionType;


/**
 * &lt;p&gt;Classe Java per TenderSubcontractingRequirementsType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TenderSubcontractingRequirementsType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}TenderSubcontractingRequirementsCode"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}TenderSubcontractingRequirementsDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TenderSubcontractingRequirementsType", propOrder = {
    "tenderSubcontractingRequirementsCode",
    "tenderSubcontractingRequirementsDescription"
})
public class TenderSubcontractingRequirementsType {

    @XmlElement(name = "TenderSubcontractingRequirementsCode", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1", required = true)
    protected TenderSubcontractingRequirementsCodeType tenderSubcontractingRequirementsCode;
    @XmlElement(name = "TenderSubcontractingRequirementsDescription", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected List<TenderSubcontractingRequirementsDescriptionType> tenderSubcontractingRequirementsDescription;

    /**
     * Recupera il valore della proprietà tenderSubcontractingRequirementsCode.
     * 
     * @return
     *     possible object is
     *     {@link TenderSubcontractingRequirementsCodeType }
     *     
     */
    public TenderSubcontractingRequirementsCodeType getTenderSubcontractingRequirementsCode() {
        return tenderSubcontractingRequirementsCode;
    }

    /**
     * Imposta il valore della proprietà tenderSubcontractingRequirementsCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TenderSubcontractingRequirementsCodeType }
     *     
     */
    public void setTenderSubcontractingRequirementsCode(TenderSubcontractingRequirementsCodeType value) {
        this.tenderSubcontractingRequirementsCode = value;
    }

    /**
     * Gets the value of the tenderSubcontractingRequirementsDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the tenderSubcontractingRequirementsDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTenderSubcontractingRequirementsDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TenderSubcontractingRequirementsDescriptionType }
     * 
     * 
     */
    public List<TenderSubcontractingRequirementsDescriptionType> getTenderSubcontractingRequirementsDescription() {
        if (tenderSubcontractingRequirementsDescription == null) {
            tenderSubcontractingRequirementsDescription = new ArrayList<TenderSubcontractingRequirementsDescriptionType>();
        }
        return this.tenderSubcontractingRequirementsDescription;
    }

}
