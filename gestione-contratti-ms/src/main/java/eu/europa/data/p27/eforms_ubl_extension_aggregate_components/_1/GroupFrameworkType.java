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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.GroupFrameworkMaximumValueAmountType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.GroupFrameworkReestimatedValueAmountType;


/**
 * &lt;p&gt;Classe Java per GroupFrameworkType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="GroupFrameworkType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}FieldsPrivacy" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}GroupFrameworkMaximumValueAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}GroupFrameworkReestimatedValueAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}TenderLot"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroupFrameworkType", propOrder = {
    "fieldsPrivacy",
    "groupFrameworkMaximumValueAmount",
    "groupFrameworkReestimatedValueAmount",
    "tenderLot"
})
public class GroupFrameworkType {

    @XmlElement(name = "FieldsPrivacy")
    protected List<FieldsPrivacyType> fieldsPrivacy;
    @XmlElement(name = "GroupFrameworkMaximumValueAmount", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected GroupFrameworkMaximumValueAmountType groupFrameworkMaximumValueAmount;
    @XmlElement(name = "GroupFrameworkReestimatedValueAmount", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected GroupFrameworkReestimatedValueAmountType groupFrameworkReestimatedValueAmount;
    @XmlElement(name = "TenderLot", required = true)
    protected TenderLotType tenderLot;

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
     * Recupera il valore della proprietà groupFrameworkMaximumValueAmount.
     * 
     * @return
     *     possible object is
     *     {@link GroupFrameworkMaximumValueAmountType }
     *     
     */
    public GroupFrameworkMaximumValueAmountType getGroupFrameworkMaximumValueAmount() {
        return groupFrameworkMaximumValueAmount;
    }

    /**
     * Imposta il valore della proprietà groupFrameworkMaximumValueAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupFrameworkMaximumValueAmountType }
     *     
     */
    public void setGroupFrameworkMaximumValueAmount(GroupFrameworkMaximumValueAmountType value) {
        this.groupFrameworkMaximumValueAmount = value;
    }

    /**
     * Recupera il valore della proprietà groupFrameworkReestimatedValueAmount.
     * 
     * @return
     *     possible object is
     *     {@link GroupFrameworkReestimatedValueAmountType }
     *     
     */
    public GroupFrameworkReestimatedValueAmountType getGroupFrameworkReestimatedValueAmount() {
        return groupFrameworkReestimatedValueAmount;
    }

    /**
     * Imposta il valore della proprietà groupFrameworkReestimatedValueAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupFrameworkReestimatedValueAmountType }
     *     
     */
    public void setGroupFrameworkReestimatedValueAmount(GroupFrameworkReestimatedValueAmountType value) {
        this.groupFrameworkReestimatedValueAmount = value;
    }

    /**
     * Recupera il valore della proprietà tenderLot.
     * 
     * @return
     *     possible object is
     *     {@link TenderLotType }
     *     
     */
    public TenderLotType getTenderLot() {
        return tenderLot;
    }

    /**
     * Imposta il valore della proprietà tenderLot.
     * 
     * @param value
     *     allowed object is
     *     {@link TenderLotType }
     *     
     */
    public void setTenderLot(TenderLotType value) {
        this.tenderLot = value;
    }

}
