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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AverageSubsequentContractAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EstimatedOverallContractAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EstimatedOverallFrameworkContractsAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MinimumAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MonetaryScopeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxIncludedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalAmountType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per RequestedTenderTotalType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="RequestedTenderTotalType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}EstimatedOverallContractAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}EstimatedOverallFrameworkContractsAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TotalAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TaxIncludedIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MinimumAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MaximumAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MonetaryScope" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AverageSubsequentContractAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ApplicableTaxCategory" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestedTenderTotalType", propOrder = {
    "ublExtensions",
    "estimatedOverallContractAmount",
    "estimatedOverallFrameworkContractsAmount",
    "totalAmount",
    "taxIncludedIndicator",
    "minimumAmount",
    "maximumAmount",
    "monetaryScope",
    "averageSubsequentContractAmount",
    "applicableTaxCategory"
})
public class RequestedTenderTotalType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "EstimatedOverallContractAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected EstimatedOverallContractAmountType estimatedOverallContractAmount;
    @XmlElement(name = "EstimatedOverallFrameworkContractsAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected EstimatedOverallFrameworkContractsAmountType estimatedOverallFrameworkContractsAmount;
    @XmlElement(name = "TotalAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TotalAmountType totalAmount;
    @XmlElement(name = "TaxIncludedIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TaxIncludedIndicatorType taxIncludedIndicator;
    @XmlElement(name = "MinimumAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MinimumAmountType minimumAmount;
    @XmlElement(name = "MaximumAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MaximumAmountType maximumAmount;
    @XmlElement(name = "MonetaryScope", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<MonetaryScopeType> monetaryScope;
    @XmlElement(name = "AverageSubsequentContractAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected AverageSubsequentContractAmountType averageSubsequentContractAmount;
    @XmlElement(name = "ApplicableTaxCategory")
    protected List<TaxCategoryType> applicableTaxCategory;

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
     * Recupera il valore della proprietà estimatedOverallContractAmount.
     * 
     * @return
     *     possible object is
     *     {@link EstimatedOverallContractAmountType }
     *     
     */
    public EstimatedOverallContractAmountType getEstimatedOverallContractAmount() {
        return estimatedOverallContractAmount;
    }

    /**
     * Imposta il valore della proprietà estimatedOverallContractAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link EstimatedOverallContractAmountType }
     *     
     */
    public void setEstimatedOverallContractAmount(EstimatedOverallContractAmountType value) {
        this.estimatedOverallContractAmount = value;
    }

    /**
     * Recupera il valore della proprietà estimatedOverallFrameworkContractsAmount.
     * 
     * @return
     *     possible object is
     *     {@link EstimatedOverallFrameworkContractsAmountType }
     *     
     */
    public EstimatedOverallFrameworkContractsAmountType getEstimatedOverallFrameworkContractsAmount() {
        return estimatedOverallFrameworkContractsAmount;
    }

    /**
     * Imposta il valore della proprietà estimatedOverallFrameworkContractsAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link EstimatedOverallFrameworkContractsAmountType }
     *     
     */
    public void setEstimatedOverallFrameworkContractsAmount(EstimatedOverallFrameworkContractsAmountType value) {
        this.estimatedOverallFrameworkContractsAmount = value;
    }

    /**
     * Recupera il valore della proprietà totalAmount.
     * 
     * @return
     *     possible object is
     *     {@link TotalAmountType }
     *     
     */
    public TotalAmountType getTotalAmount() {
        return totalAmount;
    }

    /**
     * Imposta il valore della proprietà totalAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalAmountType }
     *     
     */
    public void setTotalAmount(TotalAmountType value) {
        this.totalAmount = value;
    }

    /**
     * Recupera il valore della proprietà taxIncludedIndicator.
     * 
     * @return
     *     possible object is
     *     {@link TaxIncludedIndicatorType }
     *     
     */
    public TaxIncludedIndicatorType getTaxIncludedIndicator() {
        return taxIncludedIndicator;
    }

    /**
     * Imposta il valore della proprietà taxIncludedIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxIncludedIndicatorType }
     *     
     */
    public void setTaxIncludedIndicator(TaxIncludedIndicatorType value) {
        this.taxIncludedIndicator = value;
    }

    /**
     * Recupera il valore della proprietà minimumAmount.
     * 
     * @return
     *     possible object is
     *     {@link MinimumAmountType }
     *     
     */
    public MinimumAmountType getMinimumAmount() {
        return minimumAmount;
    }

    /**
     * Imposta il valore della proprietà minimumAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link MinimumAmountType }
     *     
     */
    public void setMinimumAmount(MinimumAmountType value) {
        this.minimumAmount = value;
    }

    /**
     * Recupera il valore della proprietà maximumAmount.
     * 
     * @return
     *     possible object is
     *     {@link MaximumAmountType }
     *     
     */
    public MaximumAmountType getMaximumAmount() {
        return maximumAmount;
    }

    /**
     * Imposta il valore della proprietà maximumAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumAmountType }
     *     
     */
    public void setMaximumAmount(MaximumAmountType value) {
        this.maximumAmount = value;
    }

    /**
     * Gets the value of the monetaryScope property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the monetaryScope property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getMonetaryScope().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link MonetaryScopeType }
     * 
     * 
     */
    public List<MonetaryScopeType> getMonetaryScope() {
        if (monetaryScope == null) {
            monetaryScope = new ArrayList<MonetaryScopeType>();
        }
        return this.monetaryScope;
    }

    /**
     * Recupera il valore della proprietà averageSubsequentContractAmount.
     * 
     * @return
     *     possible object is
     *     {@link AverageSubsequentContractAmountType }
     *     
     */
    public AverageSubsequentContractAmountType getAverageSubsequentContractAmount() {
        return averageSubsequentContractAmount;
    }

    /**
     * Imposta il valore della proprietà averageSubsequentContractAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link AverageSubsequentContractAmountType }
     *     
     */
    public void setAverageSubsequentContractAmount(AverageSubsequentContractAmountType value) {
        this.averageSubsequentContractAmount = value;
    }

    /**
     * Gets the value of the applicableTaxCategory property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the applicableTaxCategory property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getApplicableTaxCategory().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TaxCategoryType }
     * 
     * 
     */
    public List<TaxCategoryType> getApplicableTaxCategory() {
        if (applicableTaxCategory == null) {
            applicableTaxCategory = new ArrayList<TaxCategoryType>();
        }
        return this.applicableTaxCategory;
    }

}
