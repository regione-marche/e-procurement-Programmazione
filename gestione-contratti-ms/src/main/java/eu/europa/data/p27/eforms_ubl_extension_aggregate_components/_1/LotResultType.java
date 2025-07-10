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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.DPSTerminationIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HigherTenderAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LowerTenderAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TenderResultCodeType;


/**
 * &lt;p&gt;Classe Java per LotResultType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="LotResultType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}FieldsPrivacy" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HigherTenderAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LowerTenderAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TenderResultCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}DPSTerminationIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}FinancingParty" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PayerParty" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}AppealRequestsStatistics" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}DecisionReason" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}LotTender" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}FrameworkAgreementValues" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}ReceivedSubmissionsStatistics" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}SettledContract" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}StrategicProcurement" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}TenderLot"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LotResultType", propOrder = {
    "fieldsPrivacy",
    "id",
    "higherTenderAmount",
    "lowerTenderAmount",
    "tenderResultCode",
    "dpsTerminationIndicator",
    "financingParty",
    "payerParty",
    "appealRequestsStatistics",
    "decisionReason",
    "lotTender",
    "frameworkAgreementValues",
    "receivedSubmissionsStatistics",
    "settledContract",
    "strategicProcurement",
    "tenderLot"
})
public class LotResultType {

    @XmlElement(name = "FieldsPrivacy")
    protected List<FieldsPrivacyType> fieldsPrivacy;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "HigherTenderAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected HigherTenderAmountType higherTenderAmount;
    @XmlElement(name = "LowerTenderAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected LowerTenderAmountType lowerTenderAmount;
    @XmlElement(name = "TenderResultCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TenderResultCodeType tenderResultCode;
    @XmlElement(name = "DPSTerminationIndicator", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected DPSTerminationIndicatorType dpsTerminationIndicator;
    @XmlElement(name = "FinancingParty", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    protected List<PartyType> financingParty;
    @XmlElement(name = "PayerParty", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    protected List<PartyType> payerParty;
    @XmlElement(name = "AppealRequestsStatistics")
    protected List<AppealRequestsStatisticsType> appealRequestsStatistics;
    @XmlElement(name = "DecisionReason")
    protected DecisionReasonType decisionReason;
    @XmlElement(name = "LotTender")
    protected List<LotTenderType> lotTender;
    @XmlElement(name = "FrameworkAgreementValues")
    protected FrameworkAgreementValuesType frameworkAgreementValues;
    @XmlElement(name = "ReceivedSubmissionsStatistics")
    protected List<ReceivedSubmissionsStatisticsType> receivedSubmissionsStatistics;
    @XmlElement(name = "SettledContract")
    protected List<SettledContractType> settledContract;
    @XmlElement(name = "StrategicProcurement")
    protected List<StrategicProcurementType> strategicProcurement;
    @XmlElement(name = "TenderLot", required = true)
    protected TenderLotType tenderLot;

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
     * Recupera il valore della proprietà higherTenderAmount.
     * 
     * @return
     *     possible object is
     *     {@link HigherTenderAmountType }
     *     
     */
    public HigherTenderAmountType getHigherTenderAmount() {
        return higherTenderAmount;
    }

    /**
     * Imposta il valore della proprietà higherTenderAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link HigherTenderAmountType }
     *     
     */
    public void setHigherTenderAmount(HigherTenderAmountType value) {
        this.higherTenderAmount = value;
    }

    /**
     * Recupera il valore della proprietà lowerTenderAmount.
     * 
     * @return
     *     possible object is
     *     {@link LowerTenderAmountType }
     *     
     */
    public LowerTenderAmountType getLowerTenderAmount() {
        return lowerTenderAmount;
    }

    /**
     * Imposta il valore della proprietà lowerTenderAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link LowerTenderAmountType }
     *     
     */
    public void setLowerTenderAmount(LowerTenderAmountType value) {
        this.lowerTenderAmount = value;
    }

    /**
     * Recupera il valore della proprietà tenderResultCode.
     * 
     * @return
     *     possible object is
     *     {@link TenderResultCodeType }
     *     
     */
    public TenderResultCodeType getTenderResultCode() {
        return tenderResultCode;
    }

    /**
     * Imposta il valore della proprietà tenderResultCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TenderResultCodeType }
     *     
     */
    public void setTenderResultCode(TenderResultCodeType value) {
        this.tenderResultCode = value;
    }

    /**
     * Recupera il valore della proprietà dpsTerminationIndicator.
     * 
     * @return
     *     possible object is
     *     {@link DPSTerminationIndicatorType }
     *     
     */
    public DPSTerminationIndicatorType getDPSTerminationIndicator() {
        return dpsTerminationIndicator;
    }

    /**
     * Imposta il valore della proprietà dpsTerminationIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link DPSTerminationIndicatorType }
     *     
     */
    public void setDPSTerminationIndicator(DPSTerminationIndicatorType value) {
        this.dpsTerminationIndicator = value;
    }

    /**
     * Gets the value of the financingParty property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the financingParty property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getFinancingParty().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PartyType }
     * 
     * 
     */
    public List<PartyType> getFinancingParty() {
        if (financingParty == null) {
            financingParty = new ArrayList<PartyType>();
        }
        return this.financingParty;
    }

    /**
     * Gets the value of the payerParty property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the payerParty property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPayerParty().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PartyType }
     * 
     * 
     */
    public List<PartyType> getPayerParty() {
        if (payerParty == null) {
            payerParty = new ArrayList<PartyType>();
        }
        return this.payerParty;
    }

    /**
     * Gets the value of the appealRequestsStatistics property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the appealRequestsStatistics property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAppealRequestsStatistics().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AppealRequestsStatisticsType }
     * 
     * 
     */
    public List<AppealRequestsStatisticsType> getAppealRequestsStatistics() {
        if (appealRequestsStatistics == null) {
            appealRequestsStatistics = new ArrayList<AppealRequestsStatisticsType>();
        }
        return this.appealRequestsStatistics;
    }

    /**
     * Recupera il valore della proprietà decisionReason.
     * 
     * @return
     *     possible object is
     *     {@link DecisionReasonType }
     *     
     */
    public DecisionReasonType getDecisionReason() {
        return decisionReason;
    }

    /**
     * Imposta il valore della proprietà decisionReason.
     * 
     * @param value
     *     allowed object is
     *     {@link DecisionReasonType }
     *     
     */
    public void setDecisionReason(DecisionReasonType value) {
        this.decisionReason = value;
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
     * Recupera il valore della proprietà frameworkAgreementValues.
     * 
     * @return
     *     possible object is
     *     {@link FrameworkAgreementValuesType }
     *     
     */
    public FrameworkAgreementValuesType getFrameworkAgreementValues() {
        return frameworkAgreementValues;
    }

    /**
     * Imposta il valore della proprietà frameworkAgreementValues.
     * 
     * @param value
     *     allowed object is
     *     {@link FrameworkAgreementValuesType }
     *     
     */
    public void setFrameworkAgreementValues(FrameworkAgreementValuesType value) {
        this.frameworkAgreementValues = value;
    }

    /**
     * Gets the value of the receivedSubmissionsStatistics property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the receivedSubmissionsStatistics property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getReceivedSubmissionsStatistics().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ReceivedSubmissionsStatisticsType }
     * 
     * 
     */
    public List<ReceivedSubmissionsStatisticsType> getReceivedSubmissionsStatistics() {
        if (receivedSubmissionsStatistics == null) {
            receivedSubmissionsStatistics = new ArrayList<ReceivedSubmissionsStatisticsType>();
        }
        return this.receivedSubmissionsStatistics;
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
     * Gets the value of the strategicProcurement property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the strategicProcurement property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getStrategicProcurement().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link StrategicProcurementType }
     * 
     * 
     */
    public List<StrategicProcurementType> getStrategicProcurement() {
        if (strategicProcurement == null) {
            strategicProcurement = new ArrayList<StrategicProcurementType>();
        }
        return this.strategicProcurement;
    }

    /**
     * Recupera il valore della proprietà tenderLot.
     * 
     * @return
     *     possible object is
     *     {@link TenderLotType }
     *     
     */
    public TenderLotType getTenderLot() {
        return tenderLot;
    }

    /**
     * Imposta il valore della proprietà tenderLot.
     * 
     * @param value
     *     allowed object is
     *     {@link TenderLotType }
     *     
     */
    public void setTenderLot(TenderLotType value) {
        this.tenderLot = value;
    }

}
