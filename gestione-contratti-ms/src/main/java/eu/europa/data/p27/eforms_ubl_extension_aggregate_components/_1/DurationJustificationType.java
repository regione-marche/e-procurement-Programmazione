//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ExtendedDurationIndicatorType;


/**
 * &lt;p&gt;Classe Java per DurationJustificationType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="DurationJustificationType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}ExtendedDurationIndicator"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}AssetsList" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DurationJustificationType", propOrder = {
    "extendedDurationIndicator",
    "assetsList"
})
public class DurationJustificationType {

    @XmlElement(name = "ExtendedDurationIndicator", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1", required = true)
    protected ExtendedDurationIndicatorType extendedDurationIndicator;
    @XmlElement(name = "AssetsList")
    protected AssetsListType assetsList;

    /**
     * Recupera il valore della proprietà extendedDurationIndicator.
     * 
     * @return
     *     possible object is
     *     {@link ExtendedDurationIndicatorType }
     *     
     */
    public ExtendedDurationIndicatorType getExtendedDurationIndicator() {
        return extendedDurationIndicator;
    }

    /**
     * Imposta il valore della proprietà extendedDurationIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtendedDurationIndicatorType }
     *     
     */
    public void setExtendedDurationIndicator(ExtendedDurationIndicatorType value) {
        this.extendedDurationIndicator = value;
    }

    /**
     * Recupera il valore della proprietà assetsList.
     * 
     * @return
     *     possible object is
     *     {@link AssetsListType }
     *     
     */
    public AssetsListType getAssetsList() {
        return assetsList;
    }

    /**
     * Imposta il valore della proprietà assetsList.
     * 
     * @param value
     *     allowed object is
     *     {@link AssetsListType }
     *     
     */
    public void setAssetsList(AssetsListType value) {
        this.assetsList = value;
    }

}
