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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BindingOnBuyerIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FollowupContractIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LowTendersDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NoFurtherNegotiationIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaymentDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PrizeDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PrizeIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TechnicalCommitteeDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.WeightingAlgorithmCodeType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per AwardingTermsType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="AwardingTermsType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}WeightingAlgorithmCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Description" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TechnicalCommitteeDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LowTendersDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PrizeIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PrizeDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PaymentDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}FollowupContractIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}BindingOnBuyerIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}NoFurtherNegotiationIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AwardingCriterion" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TechnicalCommitteePerson" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Prize" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AwardingTermsType", propOrder = {
    "ublExtensions",
    "weightingAlgorithmCode",
    "description",
    "technicalCommitteeDescription",
    "lowTendersDescription",
    "prizeIndicator",
    "prizeDescription",
    "paymentDescription",
    "followupContractIndicator",
    "bindingOnBuyerIndicator",
    "noFurtherNegotiationIndicator",
    "awardingCriterion",
    "technicalCommitteePerson",
    "prize"
})
public class AwardingTermsType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "WeightingAlgorithmCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected WeightingAlgorithmCodeType weightingAlgorithmCode;
    @XmlElement(name = "Description", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DescriptionType> description;
    @XmlElement(name = "TechnicalCommitteeDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<TechnicalCommitteeDescriptionType> technicalCommitteeDescription;
    @XmlElement(name = "LowTendersDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<LowTendersDescriptionType> lowTendersDescription;
    @XmlElement(name = "PrizeIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PrizeIndicatorType prizeIndicator;
    @XmlElement(name = "PrizeDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<PrizeDescriptionType> prizeDescription;
    @XmlElement(name = "PaymentDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<PaymentDescriptionType> paymentDescription;
    @XmlElement(name = "FollowupContractIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected FollowupContractIndicatorType followupContractIndicator;
    @XmlElement(name = "BindingOnBuyerIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected BindingOnBuyerIndicatorType bindingOnBuyerIndicator;
    @XmlElement(name = "NoFurtherNegotiationIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NoFurtherNegotiationIndicatorType noFurtherNegotiationIndicator;
    @XmlElement(name = "AwardingCriterion")
    protected List<AwardingCriterionType> awardingCriterion;
    @XmlElement(name = "TechnicalCommitteePerson")
    protected List<PersonType> technicalCommitteePerson;
    @XmlElement(name = "Prize")
    protected List<PrizeType> prize;

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
     * Recupera il valore della proprietà weightingAlgorithmCode.
     * 
     * @return
     *     possible object is
     *     {@link WeightingAlgorithmCodeType }
     *     
     */
    public WeightingAlgorithmCodeType getWeightingAlgorithmCode() {
        return weightingAlgorithmCode;
    }

    /**
     * Imposta il valore della proprietà weightingAlgorithmCode.
     * 
     * @param value
     *     allowed object is
     *     {@link WeightingAlgorithmCodeType }
     *     
     */
    public void setWeightingAlgorithmCode(WeightingAlgorithmCodeType value) {
        this.weightingAlgorithmCode = value;
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
     * Gets the value of the technicalCommitteeDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the technicalCommitteeDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTechnicalCommitteeDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TechnicalCommitteeDescriptionType }
     * 
     * 
     */
    public List<TechnicalCommitteeDescriptionType> getTechnicalCommitteeDescription() {
        if (technicalCommitteeDescription == null) {
            technicalCommitteeDescription = new ArrayList<TechnicalCommitteeDescriptionType>();
        }
        return this.technicalCommitteeDescription;
    }

    /**
     * Gets the value of the lowTendersDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the lowTendersDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getLowTendersDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link LowTendersDescriptionType }
     * 
     * 
     */
    public List<LowTendersDescriptionType> getLowTendersDescription() {
        if (lowTendersDescription == null) {
            lowTendersDescription = new ArrayList<LowTendersDescriptionType>();
        }
        return this.lowTendersDescription;
    }

    /**
     * Recupera il valore della proprietà prizeIndicator.
     * 
     * @return
     *     possible object is
     *     {@link PrizeIndicatorType }
     *     
     */
    public PrizeIndicatorType getPrizeIndicator() {
        return prizeIndicator;
    }

    /**
     * Imposta il valore della proprietà prizeIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link PrizeIndicatorType }
     *     
     */
    public void setPrizeIndicator(PrizeIndicatorType value) {
        this.prizeIndicator = value;
    }

    /**
     * Gets the value of the prizeDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the prizeDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPrizeDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PrizeDescriptionType }
     * 
     * 
     */
    public List<PrizeDescriptionType> getPrizeDescription() {
        if (prizeDescription == null) {
            prizeDescription = new ArrayList<PrizeDescriptionType>();
        }
        return this.prizeDescription;
    }

    /**
     * Gets the value of the paymentDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the paymentDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPaymentDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentDescriptionType }
     * 
     * 
     */
    public List<PaymentDescriptionType> getPaymentDescription() {
        if (paymentDescription == null) {
            paymentDescription = new ArrayList<PaymentDescriptionType>();
        }
        return this.paymentDescription;
    }

    /**
     * Recupera il valore della proprietà followupContractIndicator.
     * 
     * @return
     *     possible object is
     *     {@link FollowupContractIndicatorType }
     *     
     */
    public FollowupContractIndicatorType getFollowupContractIndicator() {
        return followupContractIndicator;
    }

    /**
     * Imposta il valore della proprietà followupContractIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link FollowupContractIndicatorType }
     *     
     */
    public void setFollowupContractIndicator(FollowupContractIndicatorType value) {
        this.followupContractIndicator = value;
    }

    /**
     * Recupera il valore della proprietà bindingOnBuyerIndicator.
     * 
     * @return
     *     possible object is
     *     {@link BindingOnBuyerIndicatorType }
     *     
     */
    public BindingOnBuyerIndicatorType getBindingOnBuyerIndicator() {
        return bindingOnBuyerIndicator;
    }

    /**
     * Imposta il valore della proprietà bindingOnBuyerIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link BindingOnBuyerIndicatorType }
     *     
     */
    public void setBindingOnBuyerIndicator(BindingOnBuyerIndicatorType value) {
        this.bindingOnBuyerIndicator = value;
    }

    /**
     * Recupera il valore della proprietà noFurtherNegotiationIndicator.
     * 
     * @return
     *     possible object is
     *     {@link NoFurtherNegotiationIndicatorType }
     *     
     */
    public NoFurtherNegotiationIndicatorType getNoFurtherNegotiationIndicator() {
        return noFurtherNegotiationIndicator;
    }

    /**
     * Imposta il valore della proprietà noFurtherNegotiationIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link NoFurtherNegotiationIndicatorType }
     *     
     */
    public void setNoFurtherNegotiationIndicator(NoFurtherNegotiationIndicatorType value) {
        this.noFurtherNegotiationIndicator = value;
    }

    /**
     * Gets the value of the awardingCriterion property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the awardingCriterion property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAwardingCriterion().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AwardingCriterionType }
     * 
     * 
     */
    public List<AwardingCriterionType> getAwardingCriterion() {
        if (awardingCriterion == null) {
            awardingCriterion = new ArrayList<AwardingCriterionType>();
        }
        return this.awardingCriterion;
    }

    /**
     * Gets the value of the technicalCommitteePerson property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the technicalCommitteePerson property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTechnicalCommitteePerson().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PersonType }
     * 
     * 
     */
    public List<PersonType> getTechnicalCommitteePerson() {
        if (technicalCommitteePerson == null) {
            technicalCommitteePerson = new ArrayList<PersonType>();
        }
        return this.technicalCommitteePerson;
    }

    /**
     * Gets the value of the prize property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the prize property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPrize().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PrizeType }
     * 
     * 
     */
    public List<PrizeType> getPrize() {
        if (prize == null) {
            prize = new ArrayList<PrizeType>();
        }
        return this.prize;
    }

}
