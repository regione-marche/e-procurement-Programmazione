//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.GroupingLotsType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumLotsAwardedNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumLotsSubmittedNumericType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per LotDistributionType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="LotDistributionType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MaximumLotsAwardedNumeric" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MaximumLotsSubmittedNumeric" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}GroupingLots" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}LotsGroup" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LotDistributionType", propOrder = {
    "ublExtensions",
    "maximumLotsAwardedNumeric",
    "maximumLotsSubmittedNumeric",
    "groupingLots",
    "lotsGroup"
})
public class LotDistributionType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "MaximumLotsAwardedNumeric", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MaximumLotsAwardedNumericType maximumLotsAwardedNumeric;
    @XmlElement(name = "MaximumLotsSubmittedNumeric", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MaximumLotsSubmittedNumericType maximumLotsSubmittedNumeric;
    @XmlElement(name = "GroupingLots", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<GroupingLotsType> groupingLots;
    @XmlElement(name = "LotsGroup")
    protected List<LotsGroupType> lotsGroup;

    /**
     * Recupera il valore della proprietà ublExtensions.
     * 
     * @return
     *     possible object is
     *     {@link UBLExtensionsType }
     *     
     */
    public UBLExtensionsType getUBLExtensions() {
        return ublExtensions;
    }

    /**
     * Imposta il valore della proprietà ublExtensions.
     * 
     * @param value
     *     allowed object is
     *     {@link UBLExtensionsType }
     *     
     */
    public void setUBLExtensions(UBLExtensionsType value) {
        this.ublExtensions = value;
    }

    /**
     * Recupera il valore della proprietà maximumLotsAwardedNumeric.
     * 
     * @return
     *     possible object is
     *     {@link MaximumLotsAwardedNumericType }
     *     
     */
    public MaximumLotsAwardedNumericType getMaximumLotsAwardedNumeric() {
        return maximumLotsAwardedNumeric;
    }

    /**
     * Imposta il valore della proprietà maximumLotsAwardedNumeric.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumLotsAwardedNumericType }
     *     
     */
    public void setMaximumLotsAwardedNumeric(MaximumLotsAwardedNumericType value) {
        this.maximumLotsAwardedNumeric = value;
    }

    /**
     * Recupera il valore della proprietà maximumLotsSubmittedNumeric.
     * 
     * @return
     *     possible object is
     *     {@link MaximumLotsSubmittedNumericType }
     *     
     */
    public MaximumLotsSubmittedNumericType getMaximumLotsSubmittedNumeric() {
        return maximumLotsSubmittedNumeric;
    }

    /**
     * Imposta il valore della proprietà maximumLotsSubmittedNumeric.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumLotsSubmittedNumericType }
     *     
     */
    public void setMaximumLotsSubmittedNumeric(MaximumLotsSubmittedNumericType value) {
        this.maximumLotsSubmittedNumeric = value;
    }

    /**
     * Gets the value of the groupingLots property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the groupingLots property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getGroupingLots().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link GroupingLotsType }
     * 
     * 
     */
    public List<GroupingLotsType> getGroupingLots() {
        if (groupingLots == null) {
            groupingLots = new ArrayList<GroupingLotsType>();
        }
        return this.groupingLots;
    }

    /**
     * Gets the value of the lotsGroup property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the lotsGroup property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getLotsGroup().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link LotsGroupType }
     * 
     * 
     */
    public List<LotsGroupType> getLotsGroup() {
        if (lotsGroup == null) {
            lotsGroup = new ArrayList<LotsGroupType>();
        }
        return this.lotsGroup;
    }

}
