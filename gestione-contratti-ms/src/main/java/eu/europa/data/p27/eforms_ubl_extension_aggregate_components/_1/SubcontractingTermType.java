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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.PercentageKnownIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.TermAmountType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.TermCodeType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.TermDescriptionType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.TermPercentType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ValueKnownIndicatorType;


/**
 * &lt;p&gt;Classe Java per SubcontractingTermType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="SubcontractingTermType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}FieldsPrivacy" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}TermAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}TermDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}TermPercent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}TermCode"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}PercentageKnownIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}ValueKnownIndicator" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubcontractingTermType", propOrder = {
    "fieldsPrivacy",
    "termAmount",
    "termDescription",
    "termPercent",
    "termCode",
    "percentageKnownIndicator",
    "valueKnownIndicator"
})
public class SubcontractingTermType {

    @XmlElement(name = "FieldsPrivacy")
    protected List<FieldsPrivacyType> fieldsPrivacy;
    @XmlElement(name = "TermAmount", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected TermAmountType termAmount;
    @XmlElement(name = "TermDescription", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected List<TermDescriptionType> termDescription;
    @XmlElement(name = "TermPercent", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected TermPercentType termPercent;
    @XmlElement(name = "TermCode", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1", required = true)
    protected TermCodeType termCode;
    @XmlElement(name = "PercentageKnownIndicator", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected PercentageKnownIndicatorType percentageKnownIndicator;
    @XmlElement(name = "ValueKnownIndicator", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected ValueKnownIndicatorType valueKnownIndicator;

    /**
     * Gets the value of the fieldsPrivacy property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the fieldsPrivacy property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getFieldsPrivacy().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link FieldsPrivacyType }
     * 
     * 
     */
    public List<FieldsPrivacyType> getFieldsPrivacy() {
        if (fieldsPrivacy == null) {
            fieldsPrivacy = new ArrayList<FieldsPrivacyType>();
        }
        return this.fieldsPrivacy;
    }

    /**
     * Recupera il valore della proprietà termAmount.
     * 
     * @return
     *     possible object is
     *     {@link TermAmountType }
     *     
     */
    public TermAmountType getTermAmount() {
        return termAmount;
    }

    /**
     * Imposta il valore della proprietà termAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link TermAmountType }
     *     
     */
    public void setTermAmount(TermAmountType value) {
        this.termAmount = value;
    }

    /**
     * Gets the value of the termDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the termDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTermDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TermDescriptionType }
     * 
     * 
     */
    public List<TermDescriptionType> getTermDescription() {
        if (termDescription == null) {
            termDescription = new ArrayList<TermDescriptionType>();
        }
        return this.termDescription;
    }

    /**
     * Recupera il valore della proprietà termPercent.
     * 
     * @return
     *     possible object is
     *     {@link TermPercentType }
     *     
     */
    public TermPercentType getTermPercent() {
        return termPercent;
    }

    /**
     * Imposta il valore della proprietà termPercent.
     * 
     * @param value
     *     allowed object is
     *     {@link TermPercentType }
     *     
     */
    public void setTermPercent(TermPercentType value) {
        this.termPercent = value;
    }

    /**
     * Recupera il valore della proprietà termCode.
     * 
     * @return
     *     possible object is
     *     {@link TermCodeType }
     *     
     */
    public TermCodeType getTermCode() {
        return termCode;
    }

    /**
     * Imposta il valore della proprietà termCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TermCodeType }
     *     
     */
    public void setTermCode(TermCodeType value) {
        this.termCode = value;
    }

    /**
     * Recupera il valore della proprietà percentageKnownIndicator.
     * 
     * @return
     *     possible object is
     *     {@link PercentageKnownIndicatorType }
     *     
     */
    public PercentageKnownIndicatorType getPercentageKnownIndicator() {
        return percentageKnownIndicator;
    }

    /**
     * Imposta il valore della proprietà percentageKnownIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link PercentageKnownIndicatorType }
     *     
     */
    public void setPercentageKnownIndicator(PercentageKnownIndicatorType value) {
        this.percentageKnownIndicator = value;
    }

    /**
     * Recupera il valore della proprietà valueKnownIndicator.
     * 
     * @return
     *     possible object is
     *     {@link ValueKnownIndicatorType }
     *     
     */
    public ValueKnownIndicatorType getValueKnownIndicator() {
        return valueKnownIndicator;
    }

    /**
     * Imposta il valore della proprietà valueKnownIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link ValueKnownIndicatorType }
     *     
     */
    public void setValueKnownIndicator(ValueKnownIndicatorType value) {
        this.valueKnownIndicator = value;
    }

}
