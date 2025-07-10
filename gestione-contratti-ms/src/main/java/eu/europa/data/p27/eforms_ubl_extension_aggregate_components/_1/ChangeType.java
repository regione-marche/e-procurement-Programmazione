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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ChangeDescriptionType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ProcurementDocumentsChangeDateType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ProcurementDocumentsChangeIndicatorType;


/**
 * &lt;p&gt;Classe Java per ChangeType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ChangeType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}ChangeDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}ProcurementDocumentsChangeDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}ProcurementDocumentsChangeIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}ChangedSection" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChangeType", propOrder = {
    "changeDescription",
    "procurementDocumentsChangeDate",
    "procurementDocumentsChangeIndicator",
    "changedSection"
})
public class ChangeType {

    @XmlElement(name = "ChangeDescription", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected List<ChangeDescriptionType> changeDescription;
    @XmlElement(name = "ProcurementDocumentsChangeDate", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected ProcurementDocumentsChangeDateType procurementDocumentsChangeDate;
    @XmlElement(name = "ProcurementDocumentsChangeIndicator", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected ProcurementDocumentsChangeIndicatorType procurementDocumentsChangeIndicator;
    @XmlElement(name = "ChangedSection")
    protected List<ChangedSectionType> changedSection;

    /**
     * Gets the value of the changeDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the changeDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getChangeDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ChangeDescriptionType }
     * 
     * 
     */
    public List<ChangeDescriptionType> getChangeDescription() {
        if (changeDescription == null) {
            changeDescription = new ArrayList<ChangeDescriptionType>();
        }
        return this.changeDescription;
    }

    /**
     * Recupera il valore della proprietà procurementDocumentsChangeDate.
     * 
     * @return
     *     possible object is
     *     {@link ProcurementDocumentsChangeDateType }
     *     
     */
    public ProcurementDocumentsChangeDateType getProcurementDocumentsChangeDate() {
        return procurementDocumentsChangeDate;
    }

    /**
     * Imposta il valore della proprietà procurementDocumentsChangeDate.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcurementDocumentsChangeDateType }
     *     
     */
    public void setProcurementDocumentsChangeDate(ProcurementDocumentsChangeDateType value) {
        this.procurementDocumentsChangeDate = value;
    }

    /**
     * Recupera il valore della proprietà procurementDocumentsChangeIndicator.
     * 
     * @return
     *     possible object is
     *     {@link ProcurementDocumentsChangeIndicatorType }
     *     
     */
    public ProcurementDocumentsChangeIndicatorType getProcurementDocumentsChangeIndicator() {
        return procurementDocumentsChangeIndicator;
    }

    /**
     * Imposta il valore della proprietà procurementDocumentsChangeIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcurementDocumentsChangeIndicatorType }
     *     
     */
    public void setProcurementDocumentsChangeIndicator(ProcurementDocumentsChangeIndicatorType value) {
        this.procurementDocumentsChangeIndicator = value;
    }

    /**
     * Gets the value of the changedSection property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the changedSection property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getChangedSection().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ChangedSectionType }
     * 
     * 
     */
    public List<ChangedSectionType> getChangedSection() {
        if (changedSection == null) {
            changedSection = new ArrayList<ChangedSectionType>();
        }
        return this.changedSection;
    }

}
