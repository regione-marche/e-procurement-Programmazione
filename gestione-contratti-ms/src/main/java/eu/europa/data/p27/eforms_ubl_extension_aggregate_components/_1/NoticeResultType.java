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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.OverallApproximateFrameworkContractsAmountType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.OverallMaximumFrameworkContractsAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalAmountType;


/**
 * &lt;p&gt;Classe Java per NoticeResultType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="NoticeResultType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}FieldsPrivacy" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TotalAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}OverallApproximateFrameworkContractsAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}OverallMaximumFrameworkContractsAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}GroupFramework" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}LotResult" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}LotTender" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}SettledContract" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}TenderingParty" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NoticeResultType", propOrder = {
    "fieldsPrivacy",
    "totalAmount",
    "overallApproximateFrameworkContractsAmount",
    "overallMaximumFrameworkContractsAmount",
    "groupFramework",
    "lotResult",
    "lotTender",
    "settledContract",
    "tenderingParty"
})
public class NoticeResultType {

    @XmlElement(name = "FieldsPrivacy")
    protected List<FieldsPrivacyType> fieldsPrivacy;
    @XmlElement(name = "TotalAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TotalAmountType totalAmount;
    @XmlElement(name = "OverallApproximateFrameworkContractsAmount", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected OverallApproximateFrameworkContractsAmountType overallApproximateFrameworkContractsAmount;
    @XmlElement(name = "OverallMaximumFrameworkContractsAmount", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected OverallMaximumFrameworkContractsAmountType overallMaximumFrameworkContractsAmount;
    @XmlElement(name = "GroupFramework")
    protected List<GroupFrameworkType> groupFramework;
    @XmlElement(name = "LotResult")
    protected List<LotResultType> lotResult;
    @XmlElement(name = "LotTender")
    protected List<LotTenderType> lotTender;
    @XmlElement(name = "SettledContract")
    protected List<SettledContractType> settledContract;
    @XmlElement(name = "TenderingParty")
    protected List<TenderingPartyType> tenderingParty;

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
     * Recupera il valore della proprietà overallApproximateFrameworkContractsAmount.
     * 
     * @return
     *     possible object is
     *     {@link OverallApproximateFrameworkContractsAmountType }
     *     
     */
    public OverallApproximateFrameworkContractsAmountType getOverallApproximateFrameworkContractsAmount() {
        return overallApproximateFrameworkContractsAmount;
    }

    /**
     * Imposta il valore della proprietà overallApproximateFrameworkContractsAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link OverallApproximateFrameworkContractsAmountType }
     *     
     */
    public void setOverallApproximateFrameworkContractsAmount(OverallApproximateFrameworkContractsAmountType value) {
        this.overallApproximateFrameworkContractsAmount = value;
    }

    /**
     * Recupera il valore della proprietà overallMaximumFrameworkContractsAmount.
     * 
     * @return
     *     possible object is
     *     {@link OverallMaximumFrameworkContractsAmountType }
     *     
     */
    public OverallMaximumFrameworkContractsAmountType getOverallMaximumFrameworkContractsAmount() {
        return overallMaximumFrameworkContractsAmount;
    }

    /**
     * Imposta il valore della proprietà overallMaximumFrameworkContractsAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link OverallMaximumFrameworkContractsAmountType }
     *     
     */
    public void setOverallMaximumFrameworkContractsAmount(OverallMaximumFrameworkContractsAmountType value) {
        this.overallMaximumFrameworkContractsAmount = value;
    }

    /**
     * Gets the value of the groupFramework property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the groupFramework property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getGroupFramework().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link GroupFrameworkType }
     * 
     * 
     */
    public List<GroupFrameworkType> getGroupFramework() {
        if (groupFramework == null) {
            groupFramework = new ArrayList<GroupFrameworkType>();
        }
        return this.groupFramework;
    }

    /**
     * Gets the value of the lotResult property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the lotResult property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getLotResult().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link LotResultType }
     * 
     * 
     */
    public List<LotResultType> getLotResult() {
        if (lotResult == null) {
            lotResult = new ArrayList<LotResultType>();
        }
        return this.lotResult;
    }

    /**
     * Gets the value of the lotTender property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the lotTender property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getLotTender().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link LotTenderType }
     * 
     * 
     */
    public List<LotTenderType> getLotTender() {
        if (lotTender == null) {
            lotTender = new ArrayList<LotTenderType>();
        }
        return this.lotTender;
    }

    /**
     * Gets the value of the settledContract property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the settledContract property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSettledContract().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link SettledContractType }
     * 
     * 
     */
    public List<SettledContractType> getSettledContract() {
        if (settledContract == null) {
            settledContract = new ArrayList<SettledContractType>();
        }
        return this.settledContract;
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

}
