//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package eu.europa.data.p27.eforms_ubl_extensions._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1.AppealRequestsStatisticsType;
import eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1.AppealsInformationType;
import eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1.AwardCriterionParameterType;
import eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1.BuyingPartyReferenceType;
import eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1.ChangesType;
import eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1.CriterionType;
import eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1.FieldsPrivacyType;
import eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1.FundingType;
import eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1.NonOfficialLanguagesType;
import eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1.NoticeResultType;
import eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1.NoticeSubTypeType;
import eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1.OfficialLanguagesType;
import eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1.OrganizationsType;
import eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1.PublicationType;
import eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1.ReferencedDocumentPartType;
import eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1.StrategicProcurementType;
import eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1.SubsidiaryClassificationType;
import eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1.TenderSubcontractingRequirementsType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.AccessToolNameType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.FrameworkMaximumAmountType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ProcedureRelaunchIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.TransmissionDateType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.TransmissionTimeType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PeriodType;


/**
 * &lt;p&gt;Classe Java per anonymous complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}AccessToolName" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}FrameworkMaximumAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}ProcedureRelaunchIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}TransmissionDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}TransmissionTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}AnswerReceptionPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}AppealRequestsStatistics" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}AppealsInformation" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}AwardCriterionParameter" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}BuyingPartyReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}Changes" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}ContractModification" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}FieldsPrivacy" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}Funding" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}InterestExpressionReceptionPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}NonOfficialLanguages" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}NoticeResult" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}NoticeSubType" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}OfficialLanguages" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}Organizations" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}Publication" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}SelectionCriteria" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}StrategicProcurement" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}SubsidiaryClassification" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}ReferencedDocumentPart" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}TenderSubcontractingRequirements" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "accessToolName",
    "frameworkMaximumAmount",
    "procedureRelaunchIndicator",
    "transmissionDate",
    "transmissionTime",
    "answerReceptionPeriod",
    "appealRequestsStatistics",
    "appealsInformation",
    "awardCriterionParameter",
    "buyingPartyReference",
    "changes",
    "contractModification",
    "fieldsPrivacy",
    "funding",
    "interestExpressionReceptionPeriod",
    "nonOfficialLanguages",
    "noticeResult",
    "noticeSubType",
    "officialLanguages",
    "organizations",
    "publication",
    "selectionCriteria",
    "strategicProcurement",
    "subsidiaryClassification",
    "referencedDocumentPart",
    "tenderSubcontractingRequirements"
})
@XmlRootElement(name = "EformsExtension")
public class EformsExtension {

    @XmlElement(name = "AccessToolName", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected List<AccessToolNameType> accessToolName;
    @XmlElement(name = "FrameworkMaximumAmount", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected FrameworkMaximumAmountType frameworkMaximumAmount;
    @XmlElement(name = "ProcedureRelaunchIndicator", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected ProcedureRelaunchIndicatorType procedureRelaunchIndicator;
    @XmlElement(name = "TransmissionDate", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected TransmissionDateType transmissionDate;
    @XmlElement(name = "TransmissionTime", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected TransmissionTimeType transmissionTime;
    @XmlElement(name = "AnswerReceptionPeriod", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected PeriodType answerReceptionPeriod;
    @XmlElement(name = "AppealRequestsStatistics", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected List<AppealRequestsStatisticsType> appealRequestsStatistics;
    @XmlElement(name = "AppealsInformation", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected List<AppealsInformationType> appealsInformation;
    @XmlElement(name = "AwardCriterionParameter", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected List<AwardCriterionParameterType> awardCriterionParameter;
    @XmlElement(name = "BuyingPartyReference", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected List<BuyingPartyReferenceType> buyingPartyReference;
    @XmlElement(name = "Changes", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected ChangesType changes;
    @XmlElement(name = "ContractModification", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected List<ChangesType> contractModification;
    @XmlElement(name = "FieldsPrivacy", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected List<FieldsPrivacyType> fieldsPrivacy;
    @XmlElement(name = "Funding", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected List<FundingType> funding;
    @XmlElement(name = "InterestExpressionReceptionPeriod", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected PeriodType interestExpressionReceptionPeriod;
    @XmlElement(name = "NonOfficialLanguages", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected NonOfficialLanguagesType nonOfficialLanguages;
    @XmlElement(name = "NoticeResult", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected NoticeResultType noticeResult;
    @XmlElement(name = "NoticeSubType", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected NoticeSubTypeType noticeSubType;
    @XmlElement(name = "OfficialLanguages", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected OfficialLanguagesType officialLanguages;
    @XmlElement(name = "Organizations", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected OrganizationsType organizations;
    @XmlElement(name = "Publication", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected PublicationType publication;
    @XmlElement(name = "SelectionCriteria", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected List<CriterionType> selectionCriteria;
    @XmlElement(name = "StrategicProcurement", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected List<StrategicProcurementType> strategicProcurement;
    @XmlElement(name = "SubsidiaryClassification", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected List<SubsidiaryClassificationType> subsidiaryClassification;
    @XmlElement(name = "ReferencedDocumentPart", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected List<ReferencedDocumentPartType> referencedDocumentPart;
    @XmlElement(name = "TenderSubcontractingRequirements", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1")
    protected List<TenderSubcontractingRequirementsType> tenderSubcontractingRequirements;

    /**
     * Gets the value of the accessToolName property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the accessToolName property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAccessToolName().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AccessToolNameType }
     * 
     * 
     */
    public List<AccessToolNameType> getAccessToolName() {
        if (accessToolName == null) {
            accessToolName = new ArrayList<AccessToolNameType>();
        }
        return this.accessToolName;
    }

    /**
     * Recupera il valore della proprietà frameworkMaximumAmount.
     * 
     * @return
     *     possible object is
     *     {@link FrameworkMaximumAmountType }
     *     
     */
    public FrameworkMaximumAmountType getFrameworkMaximumAmount() {
        return frameworkMaximumAmount;
    }

    /**
     * Imposta il valore della proprietà frameworkMaximumAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link FrameworkMaximumAmountType }
     *     
     */
    public void setFrameworkMaximumAmount(FrameworkMaximumAmountType value) {
        this.frameworkMaximumAmount = value;
    }

    /**
     * Recupera il valore della proprietà procedureRelaunchIndicator.
     * 
     * @return
     *     possible object is
     *     {@link ProcedureRelaunchIndicatorType }
     *     
     */
    public ProcedureRelaunchIndicatorType getProcedureRelaunchIndicator() {
        return procedureRelaunchIndicator;
    }

    /**
     * Imposta il valore della proprietà procedureRelaunchIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcedureRelaunchIndicatorType }
     *     
     */
    public void setProcedureRelaunchIndicator(ProcedureRelaunchIndicatorType value) {
        this.procedureRelaunchIndicator = value;
    }

    /**
     * Recupera il valore della proprietà transmissionDate.
     * 
     * @return
     *     possible object is
     *     {@link TransmissionDateType }
     *     
     */
    public TransmissionDateType getTransmissionDate() {
        return transmissionDate;
    }

    /**
     * Imposta il valore della proprietà transmissionDate.
     * 
     * @param value
     *     allowed object is
     *     {@link TransmissionDateType }
     *     
     */
    public void setTransmissionDate(TransmissionDateType value) {
        this.transmissionDate = value;
    }

    /**
     * Recupera il valore della proprietà transmissionTime.
     * 
     * @return
     *     possible object is
     *     {@link TransmissionTimeType }
     *     
     */
    public TransmissionTimeType getTransmissionTime() {
        return transmissionTime;
    }

    /**
     * Imposta il valore della proprietà transmissionTime.
     * 
     * @param value
     *     allowed object is
     *     {@link TransmissionTimeType }
     *     
     */
    public void setTransmissionTime(TransmissionTimeType value) {
        this.transmissionTime = value;
    }

    /**
     * Recupera il valore della proprietà answerReceptionPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getAnswerReceptionPeriod() {
        return answerReceptionPeriod;
    }

    /**
     * Imposta il valore della proprietà answerReceptionPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setAnswerReceptionPeriod(PeriodType value) {
        this.answerReceptionPeriod = value;
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
     * Gets the value of the appealsInformation property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the appealsInformation property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAppealsInformation().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AppealsInformationType }
     * 
     * 
     */
    public List<AppealsInformationType> getAppealsInformation() {
        if (appealsInformation == null) {
            appealsInformation = new ArrayList<AppealsInformationType>();
        }
        return this.appealsInformation;
    }

    /**
     * Gets the value of the awardCriterionParameter property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the awardCriterionParameter property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAwardCriterionParameter().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AwardCriterionParameterType }
     * 
     * 
     */
    public List<AwardCriterionParameterType> getAwardCriterionParameter() {
        if (awardCriterionParameter == null) {
            awardCriterionParameter = new ArrayList<AwardCriterionParameterType>();
        }
        return this.awardCriterionParameter;
    }

    /**
     * Gets the value of the buyingPartyReference property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the buyingPartyReference property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getBuyingPartyReference().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link BuyingPartyReferenceType }
     * 
     * 
     */
    public List<BuyingPartyReferenceType> getBuyingPartyReference() {
        if (buyingPartyReference == null) {
            buyingPartyReference = new ArrayList<BuyingPartyReferenceType>();
        }
        return this.buyingPartyReference;
    }

    /**
     * Recupera il valore della proprietà changes.
     * 
     * @return
     *     possible object is
     *     {@link ChangesType }
     *     
     */
    public ChangesType getChanges() {
        return changes;
    }

    /**
     * Imposta il valore della proprietà changes.
     * 
     * @param value
     *     allowed object is
     *     {@link ChangesType }
     *     
     */
    public void setChanges(ChangesType value) {
        this.changes = value;
    }

    /**
     * Gets the value of the contractModification property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the contractModification property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getContractModification().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ChangesType }
     * 
     * 
     */
    public List<ChangesType> getContractModification() {
        if (contractModification == null) {
            contractModification = new ArrayList<ChangesType>();
        }
        return this.contractModification;
    }

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
     * Gets the value of the funding property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the funding property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getFunding().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link FundingType }
     * 
     * 
     */
    public List<FundingType> getFunding() {
        if (funding == null) {
            funding = new ArrayList<FundingType>();
        }
        return this.funding;
    }

    /**
     * Recupera il valore della proprietà interestExpressionReceptionPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getInterestExpressionReceptionPeriod() {
        return interestExpressionReceptionPeriod;
    }

    /**
     * Imposta il valore della proprietà interestExpressionReceptionPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setInterestExpressionReceptionPeriod(PeriodType value) {
        this.interestExpressionReceptionPeriod = value;
    }

    /**
     * Recupera il valore della proprietà nonOfficialLanguages.
     * 
     * @return
     *     possible object is
     *     {@link NonOfficialLanguagesType }
     *     
     */
    public NonOfficialLanguagesType getNonOfficialLanguages() {
        return nonOfficialLanguages;
    }

    /**
     * Imposta il valore della proprietà nonOfficialLanguages.
     * 
     * @param value
     *     allowed object is
     *     {@link NonOfficialLanguagesType }
     *     
     */
    public void setNonOfficialLanguages(NonOfficialLanguagesType value) {
        this.nonOfficialLanguages = value;
    }

    /**
     * Recupera il valore della proprietà noticeResult.
     * 
     * @return
     *     possible object is
     *     {@link NoticeResultType }
     *     
     */
    public NoticeResultType getNoticeResult() {
        return noticeResult;
    }

    /**
     * Imposta il valore della proprietà noticeResult.
     * 
     * @param value
     *     allowed object is
     *     {@link NoticeResultType }
     *     
     */
    public void setNoticeResult(NoticeResultType value) {
        this.noticeResult = value;
    }

    /**
     * Recupera il valore della proprietà noticeSubType.
     * 
     * @return
     *     possible object is
     *     {@link NoticeSubTypeType }
     *     
     */
    public NoticeSubTypeType getNoticeSubType() {
        return noticeSubType;
    }

    /**
     * Imposta il valore della proprietà noticeSubType.
     * 
     * @param value
     *     allowed object is
     *     {@link NoticeSubTypeType }
     *     
     */
    public void setNoticeSubType(NoticeSubTypeType value) {
        this.noticeSubType = value;
    }

    /**
     * Recupera il valore della proprietà officialLanguages.
     * 
     * @return
     *     possible object is
     *     {@link OfficialLanguagesType }
     *     
     */
    public OfficialLanguagesType getOfficialLanguages() {
        return officialLanguages;
    }

    /**
     * Imposta il valore della proprietà officialLanguages.
     * 
     * @param value
     *     allowed object is
     *     {@link OfficialLanguagesType }
     *     
     */
    public void setOfficialLanguages(OfficialLanguagesType value) {
        this.officialLanguages = value;
    }

    /**
     * Recupera il valore della proprietà organizations.
     * 
     * @return
     *     possible object is
     *     {@link OrganizationsType }
     *     
     */
    public OrganizationsType getOrganizations() {
        return organizations;
    }

    /**
     * Imposta il valore della proprietà organizations.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganizationsType }
     *     
     */
    public void setOrganizations(OrganizationsType value) {
        this.organizations = value;
    }

    /**
     * Recupera il valore della proprietà publication.
     * 
     * @return
     *     possible object is
     *     {@link PublicationType }
     *     
     */
    public PublicationType getPublication() {
        return publication;
    }

    /**
     * Imposta il valore della proprietà publication.
     * 
     * @param value
     *     allowed object is
     *     {@link PublicationType }
     *     
     */
    public void setPublication(PublicationType value) {
        this.publication = value;
    }

    /**
     * Gets the value of the selectionCriteria property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the selectionCriteria property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSelectionCriteria().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CriterionType }
     * 
     * 
     */
    public List<CriterionType> getSelectionCriteria() {
        if (selectionCriteria == null) {
            selectionCriteria = new ArrayList<CriterionType>();
        }
        return this.selectionCriteria;
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
     * Gets the value of the subsidiaryClassification property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the subsidiaryClassification property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSubsidiaryClassification().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link SubsidiaryClassificationType }
     * 
     * 
     */
    public List<SubsidiaryClassificationType> getSubsidiaryClassification() {
        if (subsidiaryClassification == null) {
            subsidiaryClassification = new ArrayList<SubsidiaryClassificationType>();
        }
        return this.subsidiaryClassification;
    }

    /**
     * Gets the value of the referencedDocumentPart property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the referencedDocumentPart property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getReferencedDocumentPart().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ReferencedDocumentPartType }
     * 
     * 
     */
    public List<ReferencedDocumentPartType> getReferencedDocumentPart() {
        if (referencedDocumentPart == null) {
            referencedDocumentPart = new ArrayList<ReferencedDocumentPartType>();
        }
        return this.referencedDocumentPart;
    }

    /**
     * Gets the value of the tenderSubcontractingRequirements property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the tenderSubcontractingRequirements property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTenderSubcontractingRequirements().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TenderSubcontractingRequirementsType }
     * 
     * 
     */
    public List<TenderSubcontractingRequirementsType> getTenderSubcontractingRequirements() {
        if (tenderSubcontractingRequirements == null) {
            tenderSubcontractingRequirements = new ArrayList<TenderSubcontractingRequirementsType>();
        }
        return this.tenderSubcontractingRequirements;
    }

}
