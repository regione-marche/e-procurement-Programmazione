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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.PublicTransportationCumulatedDistanceType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.TenderRankedIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.TenderVariantIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.MonetaryTotalType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RankCodeType;


/**
 * &lt;p&gt;Classe Java per LotTenderType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="LotTenderType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}FieldsPrivacy" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RankCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}PublicTransportationCumulatedDistance" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}TenderRankedIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}TenderVariantIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}LegalMonetaryTotal" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}AggregatedAmounts" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}ConcessionRevenue" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}ContractTerm" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}Origin" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}SubcontractingTerm" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}TenderingParty" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}TenderLot" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}TenderReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LotTenderType", propOrder = {
    "fieldsPrivacy",
    "id",
    "rankCode",
    "publicTransportationCumulatedDistance",
    "tenderRankedIndicator",
    "tenderVariantIndicator",
    "legalMonetaryTotal",
    "aggregatedAmounts",
    "concessionRevenue",
    "contractTerm",
    "origin",
    "subcontractingTerm",
    "tenderingParty",
    "tenderLot",
    "tenderReference"
})
public class LotTenderType {

    @XmlElement(name = "FieldsPrivacy")
    protected List<FieldsPrivacyType> fieldsPrivacy;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected IDType id;
    @XmlElement(name = "RankCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RankCodeType rankCode;
    @XmlElement(name = "PublicTransportationCumulatedDistance", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected PublicTransportationCumulatedDistanceType publicTransportationCumulatedDistance;
    @XmlElement(name = "TenderRankedIndicator", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected TenderRankedIndicatorType tenderRankedIndicator;
    @XmlElement(name = "TenderVariantIndicator", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected TenderVariantIndicatorType tenderVariantIndicator;
    @XmlElement(name = "LegalMonetaryTotal", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    protected MonetaryTotalType legalMonetaryTotal;
    @XmlElement(name = "AggregatedAmounts")
    protected AggregatedAmountsType aggregatedAmounts;
    @XmlElement(name = "ConcessionRevenue")
    protected ConcessionRevenueType concessionRevenue;
    @XmlElement(name = "ContractTerm")
    protected List<ContractTermType> contractTerm;
    @XmlElement(name = "Origin")
    protected List<OriginType> origin;
    @XmlElement(name = "SubcontractingTerm")
    protected List<SubcontractingTermType> subcontractingTerm;
    @XmlElement(name = "TenderingParty")
    protected List<TenderingPartyType> tenderingParty;
    @XmlElement(name = "TenderLot")
    protected List<TenderLotType> tenderLot;
    @XmlElement(name = "TenderReference")
    protected List<TenderReferenceType> tenderReference;

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
     * Recupera il valore della proprietà id.
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getID() {
        return id;
    }

    /**
     * Imposta il valore della proprietà id.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setID(IDType value) {
        this.id = value;
    }

    /**
     * Recupera il valore della proprietà rankCode.
     * 
     * @return
     *     possible object is
     *     {@link RankCodeType }
     *     
     */
    public RankCodeType getRankCode() {
        return rankCode;
    }

    /**
     * Imposta il valore della proprietà rankCode.
     * 
     * @param value
     *     allowed object is
     *     {@link RankCodeType }
     *     
     */
    public void setRankCode(RankCodeType value) {
        this.rankCode = value;
    }

    /**
     * Recupera il valore della proprietà publicTransportationCumulatedDistance.
     * 
     * @return
     *     possible object is
     *     {@link PublicTransportationCumulatedDistanceType }
     *     
     */
    public PublicTransportationCumulatedDistanceType getPublicTransportationCumulatedDistance() {
        return publicTransportationCumulatedDistance;
    }

    /**
     * Imposta il valore della proprietà publicTransportationCumulatedDistance.
     * 
     * @param value
     *     allowed object is
     *     {@link PublicTransportationCumulatedDistanceType }
     *     
     */
    public void setPublicTransportationCumulatedDistance(PublicTransportationCumulatedDistanceType value) {
        this.publicTransportationCumulatedDistance = value;
    }

    /**
     * Recupera il valore della proprietà tenderRankedIndicator.
     * 
     * @return
     *     possible object is
     *     {@link TenderRankedIndicatorType }
     *     
     */
    public TenderRankedIndicatorType getTenderRankedIndicator() {
        return tenderRankedIndicator;
    }

    /**
     * Imposta il valore della proprietà tenderRankedIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link TenderRankedIndicatorType }
     *     
     */
    public void setTenderRankedIndicator(TenderRankedIndicatorType value) {
        this.tenderRankedIndicator = value;
    }

    /**
     * Recupera il valore della proprietà tenderVariantIndicator.
     * 
     * @return
     *     possible object is
     *     {@link TenderVariantIndicatorType }
     *     
     */
    public TenderVariantIndicatorType getTenderVariantIndicator() {
        return tenderVariantIndicator;
    }

    /**
     * Imposta il valore della proprietà tenderVariantIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link TenderVariantIndicatorType }
     *     
     */
    public void setTenderVariantIndicator(TenderVariantIndicatorType value) {
        this.tenderVariantIndicator = value;
    }

    /**
     * Recupera il valore della proprietà legalMonetaryTotal.
     * 
     * @return
     *     possible object is
     *     {@link MonetaryTotalType }
     *     
     */
    public MonetaryTotalType getLegalMonetaryTotal() {
        return legalMonetaryTotal;
    }

    /**
     * Imposta il valore della proprietà legalMonetaryTotal.
     * 
     * @param value
     *     allowed object is
     *     {@link MonetaryTotalType }
     *     
     */
    public void setLegalMonetaryTotal(MonetaryTotalType value) {
        this.legalMonetaryTotal = value;
    }

    /**
     * Recupera il valore della proprietà aggregatedAmounts.
     * 
     * @return
     *     possible object is
     *     {@link AggregatedAmountsType }
     *     
     */
    public AggregatedAmountsType getAggregatedAmounts() {
        return aggregatedAmounts;
    }

    /**
     * Imposta il valore della proprietà aggregatedAmounts.
     * 
     * @param value
     *     allowed object is
     *     {@link AggregatedAmountsType }
     *     
     */
    public void setAggregatedAmounts(AggregatedAmountsType value) {
        this.aggregatedAmounts = value;
    }

    /**
     * Recupera il valore della proprietà concessionRevenue.
     * 
     * @return
     *     possible object is
     *     {@link ConcessionRevenueType }
     *     
     */
    public ConcessionRevenueType getConcessionRevenue() {
        return concessionRevenue;
    }

    /**
     * Imposta il valore della proprietà concessionRevenue.
     * 
     * @param value
     *     allowed object is
     *     {@link ConcessionRevenueType }
     *     
     */
    public void setConcessionRevenue(ConcessionRevenueType value) {
        this.concessionRevenue = value;
    }

    /**
     * Gets the value of the contractTerm property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the contractTerm property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getContractTerm().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ContractTermType }
     * 
     * 
     */
    public List<ContractTermType> getContractTerm() {
        if (contractTerm == null) {
            contractTerm = new ArrayList<ContractTermType>();
        }
        return this.contractTerm;
    }

    /**
     * Gets the value of the origin property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the origin property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getOrigin().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link OriginType }
     * 
     * 
     */
    public List<OriginType> getOrigin() {
        if (origin == null) {
            origin = new ArrayList<OriginType>();
        }
        return this.origin;
    }

    /**
     * Gets the value of the subcontractingTerm property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the subcontractingTerm property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSubcontractingTerm().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link SubcontractingTermType }
     * 
     * 
     */
    public List<SubcontractingTermType> getSubcontractingTerm() {
        if (subcontractingTerm == null) {
            subcontractingTerm = new ArrayList<SubcontractingTermType>();
        }
        return this.subcontractingTerm;
    }

    /**
     * Gets the value of the tenderingParty property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the tenderingParty property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTenderingParty().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TenderingPartyType }
     * 
     * 
     */
    public List<TenderingPartyType> getTenderingParty() {
        if (tenderingParty == null) {
            tenderingParty = new ArrayList<TenderingPartyType>();
        }
        return this.tenderingParty;
    }

    /**
     * Gets the value of the tenderLot property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the tenderLot property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTenderLot().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TenderLotType }
     * 
     * 
     */
    public List<TenderLotType> getTenderLot() {
        if (tenderLot == null) {
            tenderLot = new ArrayList<TenderLotType>();
        }
        return this.tenderLot;
    }

    /**
     * Gets the value of the tenderReference property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the tenderReference property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTenderReference().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TenderReferenceType }
     * 
     * 
     */
    public List<TenderReferenceType> getTenderReference() {
        if (tenderReference == null) {
            tenderReference = new ArrayList<TenderReferenceType>();
        }
        return this.tenderReference;
    }

}
