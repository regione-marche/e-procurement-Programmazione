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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.AssetDescriptionType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.AssetPredominanceType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.AssetSignificanceType;


/**
 * &lt;p&gt;Classe Java per AssetType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="AssetType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}AssetDescription" maxOccurs="unbounded"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}AssetSignificance"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}AssetPredominance"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AssetType", propOrder = {
    "assetDescription",
    "assetSignificance",
    "assetPredominance"
})
public class AssetType {

    @XmlElement(name = "AssetDescription", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1", required = true)
    protected List<AssetDescriptionType> assetDescription;
    @XmlElement(name = "AssetSignificance", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1", required = true)
    protected AssetSignificanceType assetSignificance;
    @XmlElement(name = "AssetPredominance", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1", required = true)
    protected AssetPredominanceType assetPredominance;

    /**
     * Gets the value of the assetDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the assetDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAssetDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AssetDescriptionType }
     * 
     * 
     */
    public List<AssetDescriptionType> getAssetDescription() {
        if (assetDescription == null) {
            assetDescription = new ArrayList<AssetDescriptionType>();
        }
        return this.assetDescription;
    }

    /**
     * Recupera il valore della proprietà assetSignificance.
     * 
     * @return
     *     possible object is
     *     {@link AssetSignificanceType }
     *     
     */
    public AssetSignificanceType getAssetSignificance() {
        return assetSignificance;
    }

    /**
     * Imposta il valore della proprietà assetSignificance.
     * 
     * @param value
     *     allowed object is
     *     {@link AssetSignificanceType }
     *     
     */
    public void setAssetSignificance(AssetSignificanceType value) {
        this.assetSignificance = value;
    }

    /**
     * Recupera il valore della proprietà assetPredominance.
     * 
     * @return
     *     possible object is
     *     {@link AssetPredominanceType }
     *     
     */
    public AssetPredominanceType getAssetPredominance() {
        return assetPredominance;
    }

    /**
     * Imposta il valore della proprietà assetPredominance.
     * 
     * @param value
     *     allowed object is
     *     {@link AssetPredominanceType }
     *     
     */
    public void setAssetPredominance(AssetPredominanceType value) {
        this.assetPredominance = value;
    }

}
