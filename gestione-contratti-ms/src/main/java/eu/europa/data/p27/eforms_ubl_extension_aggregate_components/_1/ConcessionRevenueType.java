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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.RevenueBuyerAmountType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.RevenueUserAmountType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ValueDescriptionType;


/**
 * &lt;p&gt;Classe Java per ConcessionRevenueType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ConcessionRevenueType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}FieldsPrivacy" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}RevenueBuyerAmount" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}RevenueUserAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}ValueDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConcessionRevenueType", propOrder = {
    "fieldsPrivacy",
    "revenueBuyerAmount",
    "revenueUserAmount",
    "valueDescription"
})
public class ConcessionRevenueType {

    @XmlElement(name = "FieldsPrivacy")
    protected List<FieldsPrivacyType> fieldsPrivacy;
    @XmlElement(name = "RevenueBuyerAmount", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected List<RevenueBuyerAmountType> revenueBuyerAmount;
    @XmlElement(name = "RevenueUserAmount", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected RevenueUserAmountType revenueUserAmount;
    @XmlElement(name = "ValueDescription", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected List<ValueDescriptionType> valueDescription;

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
     * Gets the value of the revenueBuyerAmount property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the revenueBuyerAmount property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getRevenueBuyerAmount().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link RevenueBuyerAmountType }
     * 
     * 
     */
    public List<RevenueBuyerAmountType> getRevenueBuyerAmount() {
        if (revenueBuyerAmount == null) {
            revenueBuyerAmount = new ArrayList<RevenueBuyerAmountType>();
        }
        return this.revenueBuyerAmount;
    }

    /**
     * Recupera il valore della proprietà revenueUserAmount.
     * 
     * @return
     *     possible object is
     *     {@link RevenueUserAmountType }
     *     
     */
    public RevenueUserAmountType getRevenueUserAmount() {
        return revenueUserAmount;
    }

    /**
     * Imposta il valore della proprietà revenueUserAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link RevenueUserAmountType }
     *     
     */
    public void setRevenueUserAmount(RevenueUserAmountType value) {
        this.revenueUserAmount = value;
    }

    /**
     * Gets the value of the valueDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the valueDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getValueDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ValueDescriptionType }
     * 
     * 
     */
    public List<ValueDescriptionType> getValueDescription() {
        if (valueDescription == null) {
            valueDescription = new ArrayList<ValueDescriptionType>();
        }
        return this.valueDescription;
    }

}
