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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumPercentType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MinimumPercentType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SubcontractingConditionsCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.UnknownPriceIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per SubcontractTermsType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="SubcontractTermsType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Rate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}UnknownPriceIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Description" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Amount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SubcontractingConditionsCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MaximumPercent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MinimumPercent" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubcontractTermsType", propOrder = {
    "ublExtensions",
    "rate",
    "unknownPriceIndicator",
    "description",
    "amount",
    "subcontractingConditionsCode",
    "maximumPercent",
    "minimumPercent"
})
public class SubcontractTermsType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "Rate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RateType rate;
    @XmlElement(name = "UnknownPriceIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected UnknownPriceIndicatorType unknownPriceIndicator;
    @XmlElement(name = "Description", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DescriptionType> description;
    @XmlElement(name = "Amount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected AmountType amount;
    @XmlElement(name = "SubcontractingConditionsCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SubcontractingConditionsCodeType subcontractingConditionsCode;
    @XmlElement(name = "MaximumPercent", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MaximumPercentType maximumPercent;
    @XmlElement(name = "MinimumPercent", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MinimumPercentType minimumPercent;

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
     * Recupera il valore della proprietà rate.
     * 
     * @return
     *     possible object is
     *     {@link RateType }
     *     
     */
    public RateType getRate() {
        return rate;
    }

    /**
     * Imposta il valore della proprietà rate.
     * 
     * @param value
     *     allowed object is
     *     {@link RateType }
     *     
     */
    public void setRate(RateType value) {
        this.rate = value;
    }

    /**
     * Recupera il valore della proprietà unknownPriceIndicator.
     * 
     * @return
     *     possible object is
     *     {@link UnknownPriceIndicatorType }
     *     
     */
    public UnknownPriceIndicatorType getUnknownPriceIndicator() {
        return unknownPriceIndicator;
    }

    /**
     * Imposta il valore della proprietà unknownPriceIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link UnknownPriceIndicatorType }
     *     
     */
    public void setUnknownPriceIndicator(UnknownPriceIndicatorType value) {
        this.unknownPriceIndicator = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the description property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DescriptionType }
     * 
     * 
     */
    public List<DescriptionType> getDescription() {
        if (description == null) {
            description = new ArrayList<DescriptionType>();
        }
        return this.description;
    }

    /**
     * Recupera il valore della proprietà amount.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getAmount() {
        return amount;
    }

    /**
     * Imposta il valore della proprietà amount.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setAmount(AmountType value) {
        this.amount = value;
    }

    /**
     * Recupera il valore della proprietà subcontractingConditionsCode.
     * 
     * @return
     *     possible object is
     *     {@link SubcontractingConditionsCodeType }
     *     
     */
    public SubcontractingConditionsCodeType getSubcontractingConditionsCode() {
        return subcontractingConditionsCode;
    }

    /**
     * Imposta il valore della proprietà subcontractingConditionsCode.
     * 
     * @param value
     *     allowed object is
     *     {@link SubcontractingConditionsCodeType }
     *     
     */
    public void setSubcontractingConditionsCode(SubcontractingConditionsCodeType value) {
        this.subcontractingConditionsCode = value;
    }

    /**
     * Recupera il valore della proprietà maximumPercent.
     * 
     * @return
     *     possible object is
     *     {@link MaximumPercentType }
     *     
     */
    public MaximumPercentType getMaximumPercent() {
        return maximumPercent;
    }

    /**
     * Imposta il valore della proprietà maximumPercent.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumPercentType }
     *     
     */
    public void setMaximumPercent(MaximumPercentType value) {
        this.maximumPercent = value;
    }

    /**
     * Recupera il valore della proprietà minimumPercent.
     * 
     * @return
     *     possible object is
     *     {@link MinimumPercentType }
     *     
     */
    public MinimumPercentType getMinimumPercent() {
        return minimumPercent;
    }

    /**
     * Imposta il valore della proprietà minimumPercent.
     * 
     * @param value
     *     allowed object is
     *     {@link MinimumPercentType }
     *     
     */
    public void setMinimumPercent(MinimumPercentType value) {
        this.minimumPercent = value;
    }

}
