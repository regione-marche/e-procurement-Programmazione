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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ApplicableLegalBasisType;


/**
 * &lt;p&gt;Classe Java per StrategicProcurementType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="StrategicProcurementType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}ApplicableLegalBasis" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}StrategicProcurementInformation" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StrategicProcurementType", propOrder = {
    "applicableLegalBasis",
    "strategicProcurementInformation"
})
public class StrategicProcurementType {

    @XmlElement(name = "ApplicableLegalBasis", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected ApplicableLegalBasisType applicableLegalBasis;
    @XmlElement(name = "StrategicProcurementInformation")
    protected List<StrategicProcurementInformationType> strategicProcurementInformation;

    /**
     * Recupera il valore della proprietà applicableLegalBasis.
     * 
     * @return
     *     possible object is
     *     {@link ApplicableLegalBasisType }
     *     
     */
    public ApplicableLegalBasisType getApplicableLegalBasis() {
        return applicableLegalBasis;
    }

    /**
     * Imposta il valore della proprietà applicableLegalBasis.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicableLegalBasisType }
     *     
     */
    public void setApplicableLegalBasis(ApplicableLegalBasisType value) {
        this.applicableLegalBasis = value;
    }

    /**
     * Gets the value of the strategicProcurementInformation property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the strategicProcurementInformation property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getStrategicProcurementInformation().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link StrategicProcurementInformationType }
     * 
     * 
     */
    public List<StrategicProcurementInformationType> getStrategicProcurementInformation() {
        if (strategicProcurementInformation == null) {
            strategicProcurementInformation = new ArrayList<StrategicProcurementInformationType>();
        }
        return this.strategicProcurementInformation;
    }

}
