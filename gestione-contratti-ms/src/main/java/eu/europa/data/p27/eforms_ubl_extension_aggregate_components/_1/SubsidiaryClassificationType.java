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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ItemClassificationDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ItemClassificationCodeType;


/**
 * &lt;p&gt;Classe Java per SubsidiaryClassificationType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="SubsidiaryClassificationType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ItemClassificationCode"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}ItemClassificationDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubsidiaryClassificationType", propOrder = {
    "itemClassificationCode",
    "itemClassificationDescription"
})
public class SubsidiaryClassificationType {

    @XmlElement(name = "ItemClassificationCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected ItemClassificationCodeType itemClassificationCode;
    @XmlElement(name = "ItemClassificationDescription", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected List<ItemClassificationDescriptionType> itemClassificationDescription;

    /**
     * Recupera il valore della proprietà itemClassificationCode.
     * 
     * @return
     *     possible object is
     *     {@link ItemClassificationCodeType }
     *     
     */
    public ItemClassificationCodeType getItemClassificationCode() {
        return itemClassificationCode;
    }

    /**
     * Imposta il valore della proprietà itemClassificationCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemClassificationCodeType }
     *     
     */
    public void setItemClassificationCode(ItemClassificationCodeType value) {
        this.itemClassificationCode = value;
    }

    /**
     * Gets the value of the itemClassificationDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the itemClassificationDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getItemClassificationDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ItemClassificationDescriptionType }
     * 
     * 
     */
    public List<ItemClassificationDescriptionType> getItemClassificationDescription() {
        if (itemClassificationDescription == null) {
            itemClassificationDescription = new ArrayList<ItemClassificationDescriptionType>();
        }
        return this.itemClassificationDescription;
    }

}
