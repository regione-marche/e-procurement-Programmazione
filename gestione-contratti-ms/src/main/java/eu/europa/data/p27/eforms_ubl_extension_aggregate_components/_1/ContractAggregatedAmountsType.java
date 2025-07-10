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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.PaidAmountDescriptionType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.PenaltiesAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaidAmountType;


/**
 * &lt;p&gt;Classe Java per ContractAggregatedAmountsType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ContractAggregatedAmountsType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PaidAmount"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}PaidAmountDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}PenaltiesAmount"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractAggregatedAmountsType", propOrder = {
    "paidAmount",
    "paidAmountDescription",
    "penaltiesAmount"
})
public class ContractAggregatedAmountsType {

    @XmlElement(name = "PaidAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected PaidAmountType paidAmount;
    @XmlElement(name = "PaidAmountDescription", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected List<PaidAmountDescriptionType> paidAmountDescription;
    @XmlElement(name = "PenaltiesAmount", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1", required = true)
    protected PenaltiesAmountType penaltiesAmount;

    /**
     * Recupera il valore della proprietà paidAmount.
     * 
     * @return
     *     possible object is
     *     {@link PaidAmountType }
     *     
     */
    public PaidAmountType getPaidAmount() {
        return paidAmount;
    }

    /**
     * Imposta il valore della proprietà paidAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link PaidAmountType }
     *     
     */
    public void setPaidAmount(PaidAmountType value) {
        this.paidAmount = value;
    }

    /**
     * Gets the value of the paidAmountDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the paidAmountDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPaidAmountDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PaidAmountDescriptionType }
     * 
     * 
     */
    public List<PaidAmountDescriptionType> getPaidAmountDescription() {
        if (paidAmountDescription == null) {
            paidAmountDescription = new ArrayList<PaidAmountDescriptionType>();
        }
        return this.paidAmountDescription;
    }

    /**
     * Recupera il valore della proprietà penaltiesAmount.
     * 
     * @return
     *     possible object is
     *     {@link PenaltiesAmountType }
     *     
     */
    public PenaltiesAmountType getPenaltiesAmount() {
        return penaltiesAmount;
    }

    /**
     * Imposta il valore della proprietà penaltiesAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link PenaltiesAmountType }
     *     
     */
    public void setPenaltiesAmount(PenaltiesAmountType value) {
        this.penaltiesAmount = value;
    }

}
