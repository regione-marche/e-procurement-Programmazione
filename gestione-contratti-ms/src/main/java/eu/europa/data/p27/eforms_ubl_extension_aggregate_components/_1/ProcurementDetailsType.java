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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.AssetCategoryCodeType;


/**
 * &lt;p&gt;Classe Java per ProcurementDetailsType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ProcurementDetailsType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}AssetCategoryCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}StrategicProcurementStatistics" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcurementDetailsType", propOrder = {
    "assetCategoryCode",
    "strategicProcurementStatistics"
})
public class ProcurementDetailsType {

    @XmlElement(name = "AssetCategoryCode", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected AssetCategoryCodeType assetCategoryCode;
    @XmlElement(name = "StrategicProcurementStatistics")
    protected List<StatisticsType> strategicProcurementStatistics;

    /**
     * Recupera il valore della proprietà assetCategoryCode.
     * 
     * @return
     *     possible object is
     *     {@link AssetCategoryCodeType }
     *     
     */
    public AssetCategoryCodeType getAssetCategoryCode() {
        return assetCategoryCode;
    }

    /**
     * Imposta il valore della proprietà assetCategoryCode.
     * 
     * @param value
     *     allowed object is
     *     {@link AssetCategoryCodeType }
     *     
     */
    public void setAssetCategoryCode(AssetCategoryCodeType value) {
        this.assetCategoryCode = value;
    }

    /**
     * Gets the value of the strategicProcurementStatistics property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the strategicProcurementStatistics property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getStrategicProcurementStatistics().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link StatisticsType }
     * 
     * 
     */
    public List<StatisticsType> getStrategicProcurementStatistics() {
        if (strategicProcurementStatistics == null) {
            strategicProcurementStatistics = new ArrayList<StatisticsType>();
        }
        return this.strategicProcurementStatistics;
    }

}
