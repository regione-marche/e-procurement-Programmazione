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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AcceptedVariantsDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AdditionalConditionsType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AwardingMethodTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DocumentationFeeAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EconomicOperatorRegistryURIType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EstimatedTimingFurtherPublicationType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FundingProgramCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FundingProgramType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LatestSecurityClearanceDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumAdvertisementAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumVariantQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MultipleTendersCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NoteType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OtherConditionsIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaymentFrequencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PriceEvaluationCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PriceRevisionFormulaDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RecurringProcurementDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RecurringProcurementIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RequiredCurriculaCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RequiredCurriculaIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.VariantConstraintCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.VariantConstraintIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per TenderingTermsType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TenderingTermsType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AwardingMethodTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PriceEvaluationCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MaximumVariantQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}VariantConstraintIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AcceptedVariantsDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}VariantConstraintCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PriceRevisionFormulaDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}FundingProgramCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}FundingProgram" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MaximumAdvertisementAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Note" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PaymentFrequencyCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}EconomicOperatorRegistryURI" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RequiredCurriculaIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RequiredCurriculaCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}OtherConditionsIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RecurringProcurementIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RecurringProcurementDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}EstimatedTimingFurtherPublication" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AdditionalConditions" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LatestSecurityClearanceDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DocumentationFeeAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MultipleTendersCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PenaltyClause" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RequiredFinancialGuarantee" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ProcurementLegislationDocumentReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}FiscalLegislationDocumentReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}EnvironmentalLegislationDocumentReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}EmploymentLegislationDocumentReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ContractualDocumentReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CallForTendersDocumentReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}WarrantyValidityPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PaymentTerms" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TendererQualificationRequest" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AllowedSubcontractTerms" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TenderPreparation" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ContractExecutionRequirement" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AwardingTerms" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AdditionalInformationParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DocumentProviderParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TenderRecipientParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ContractResponsibleParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TenderEvaluationParty" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}QualificationRequestRecipientParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TenderValidityPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ContractAcceptancePeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AppealTerms" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Language" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}BudgetAccountLine" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ReplacedNoticeDocumentReference" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}LotDistribution" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PostAwardProcess" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}EconomicOperatorShortList" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SecurityClearanceTerm" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TenderingTermsType", propOrder = {
    "ublExtensions",
    "awardingMethodTypeCode",
    "priceEvaluationCode",
    "maximumVariantQuantity",
    "variantConstraintIndicator",
    "acceptedVariantsDescription",
    "variantConstraintCode",
    "priceRevisionFormulaDescription",
    "fundingProgramCode",
    "fundingProgram",
    "maximumAdvertisementAmount",
    "note",
    "paymentFrequencyCode",
    "economicOperatorRegistryURI",
    "requiredCurriculaIndicator",
    "requiredCurriculaCode",
    "otherConditionsIndicator",
    "recurringProcurementIndicator",
    "recurringProcurementDescription",
    "estimatedTimingFurtherPublication",
    "additionalConditions",
    "latestSecurityClearanceDate",
    "documentationFeeAmount",
    "multipleTendersCode",
    "penaltyClause",
    "requiredFinancialGuarantee",
    "procurementLegislationDocumentReference",
    "fiscalLegislationDocumentReference",
    "environmentalLegislationDocumentReference",
    "employmentLegislationDocumentReference",
    "contractualDocumentReference",
    "callForTendersDocumentReference",
    "warrantyValidityPeriod",
    "paymentTerms",
    "tendererQualificationRequest",
    "allowedSubcontractTerms",
    "tenderPreparation",
    "contractExecutionRequirement",
    "awardingTerms",
    "additionalInformationParty",
    "documentProviderParty",
    "tenderRecipientParty",
    "contractResponsibleParty",
    "tenderEvaluationParty",
    "qualificationRequestRecipientParty",
    "tenderValidityPeriod",
    "contractAcceptancePeriod",
    "appealTerms",
    "language",
    "budgetAccountLine",
    "replacedNoticeDocumentReference",
    "lotDistribution",
    "postAwardProcess",
    "economicOperatorShortList",
    "securityClearanceTerm"
})
public class TenderingTermsType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "AwardingMethodTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected AwardingMethodTypeCodeType awardingMethodTypeCode;
    @XmlElement(name = "PriceEvaluationCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PriceEvaluationCodeType priceEvaluationCode;
    @XmlElement(name = "MaximumVariantQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MaximumVariantQuantityType maximumVariantQuantity;
    @XmlElement(name = "VariantConstraintIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected VariantConstraintIndicatorType variantConstraintIndicator;
    @XmlElement(name = "AcceptedVariantsDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<AcceptedVariantsDescriptionType> acceptedVariantsDescription;
    @XmlElement(name = "VariantConstraintCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected VariantConstraintCodeType variantConstraintCode;
    @XmlElement(name = "PriceRevisionFormulaDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<PriceRevisionFormulaDescriptionType> priceRevisionFormulaDescription;
    @XmlElement(name = "FundingProgramCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected FundingProgramCodeType fundingProgramCode;
    @XmlElement(name = "FundingProgram", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<FundingProgramType> fundingProgram;
    @XmlElement(name = "MaximumAdvertisementAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MaximumAdvertisementAmountType maximumAdvertisementAmount;
    @XmlElement(name = "Note", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<NoteType> note;
    @XmlElement(name = "PaymentFrequencyCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PaymentFrequencyCodeType paymentFrequencyCode;
    @XmlElement(name = "EconomicOperatorRegistryURI", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected EconomicOperatorRegistryURIType economicOperatorRegistryURI;
    @XmlElement(name = "RequiredCurriculaIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RequiredCurriculaIndicatorType requiredCurriculaIndicator;
    @XmlElement(name = "RequiredCurriculaCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RequiredCurriculaCodeType requiredCurriculaCode;
    @XmlElement(name = "OtherConditionsIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected OtherConditionsIndicatorType otherConditionsIndicator;
    @XmlElement(name = "RecurringProcurementIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RecurringProcurementIndicatorType recurringProcurementIndicator;
    @XmlElement(name = "RecurringProcurementDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<RecurringProcurementDescriptionType> recurringProcurementDescription;
    @XmlElement(name = "EstimatedTimingFurtherPublication", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<EstimatedTimingFurtherPublicationType> estimatedTimingFurtherPublication;
    @XmlElement(name = "AdditionalConditions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<AdditionalConditionsType> additionalConditions;
    @XmlElement(name = "LatestSecurityClearanceDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected LatestSecurityClearanceDateType latestSecurityClearanceDate;
    @XmlElement(name = "DocumentationFeeAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected DocumentationFeeAmountType documentationFeeAmount;
    @XmlElement(name = "MultipleTendersCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MultipleTendersCodeType multipleTendersCode;
    @XmlElement(name = "PenaltyClause")
    protected List<ClauseType> penaltyClause;
    @XmlElement(name = "RequiredFinancialGuarantee")
    protected List<FinancialGuaranteeType> requiredFinancialGuarantee;
    @XmlElement(name = "ProcurementLegislationDocumentReference")
    protected List<DocumentReferenceType> procurementLegislationDocumentReference;
    @XmlElement(name = "FiscalLegislationDocumentReference")
    protected List<DocumentReferenceType> fiscalLegislationDocumentReference;
    @XmlElement(name = "EnvironmentalLegislationDocumentReference")
    protected List<DocumentReferenceType> environmentalLegislationDocumentReference;
    @XmlElement(name = "EmploymentLegislationDocumentReference")
    protected List<DocumentReferenceType> employmentLegislationDocumentReference;
    @XmlElement(name = "ContractualDocumentReference")
    protected List<DocumentReferenceType> contractualDocumentReference;
    @XmlElement(name = "CallForTendersDocumentReference")
    protected List<DocumentReferenceType> callForTendersDocumentReference;
    @XmlElement(name = "WarrantyValidityPeriod")
    protected PeriodType warrantyValidityPeriod;
    @XmlElement(name = "PaymentTerms")
    protected List<PaymentTermsType> paymentTerms;
    @XmlElement(name = "TendererQualificationRequest")
    protected List<TendererQualificationRequestType> tendererQualificationRequest;
    @XmlElement(name = "AllowedSubcontractTerms")
    protected List<SubcontractTermsType> allowedSubcontractTerms;
    @XmlElement(name = "TenderPreparation")
    protected List<TenderPreparationType> tenderPreparation;
    @XmlElement(name = "ContractExecutionRequirement")
    protected List<ContractExecutionRequirementType> contractExecutionRequirement;
    @XmlElement(name = "AwardingTerms")
    protected AwardingTermsType awardingTerms;
    @XmlElement(name = "AdditionalInformationParty")
    protected PartyType additionalInformationParty;
    @XmlElement(name = "DocumentProviderParty")
    protected PartyType documentProviderParty;
    @XmlElement(name = "TenderRecipientParty")
    protected PartyType tenderRecipientParty;
    @XmlElement(name = "ContractResponsibleParty")
    protected PartyType contractResponsibleParty;
    @XmlElement(name = "TenderEvaluationParty")
    protected List<PartyType> tenderEvaluationParty;
    @XmlElement(name = "QualificationRequestRecipientParty")
    protected PartyType qualificationRequestRecipientParty;
    @XmlElement(name = "TenderValidityPeriod")
    protected PeriodType tenderValidityPeriod;
    @XmlElement(name = "ContractAcceptancePeriod")
    protected PeriodType contractAcceptancePeriod;
    @XmlElement(name = "AppealTerms")
    protected AppealTermsType appealTerms;
    @XmlElement(name = "Language")
    protected List<LanguageType> language;
    @XmlElement(name = "BudgetAccountLine")
    protected List<BudgetAccountLineType> budgetAccountLine;
    @XmlElement(name = "ReplacedNoticeDocumentReference")
    protected DocumentReferenceType replacedNoticeDocumentReference;
    @XmlElement(name = "LotDistribution")
    protected LotDistributionType lotDistribution;
    @XmlElement(name = "PostAwardProcess")
    protected PostAwardProcessType postAwardProcess;
    @XmlElement(name = "EconomicOperatorShortList")
    protected EconomicOperatorShortListType economicOperatorShortList;
    @XmlElement(name = "SecurityClearanceTerm")
    protected List<SecurityClearanceTermType> securityClearanceTerm;

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
     * Recupera il valore della proprietà awardingMethodTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link AwardingMethodTypeCodeType }
     *     
     */
    public AwardingMethodTypeCodeType getAwardingMethodTypeCode() {
        return awardingMethodTypeCode;
    }

    /**
     * Imposta il valore della proprietà awardingMethodTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link AwardingMethodTypeCodeType }
     *     
     */
    public void setAwardingMethodTypeCode(AwardingMethodTypeCodeType value) {
        this.awardingMethodTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà priceEvaluationCode.
     * 
     * @return
     *     possible object is
     *     {@link PriceEvaluationCodeType }
     *     
     */
    public PriceEvaluationCodeType getPriceEvaluationCode() {
        return priceEvaluationCode;
    }

    /**
     * Imposta il valore della proprietà priceEvaluationCode.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceEvaluationCodeType }
     *     
     */
    public void setPriceEvaluationCode(PriceEvaluationCodeType value) {
        this.priceEvaluationCode = value;
    }

    /**
     * Recupera il valore della proprietà maximumVariantQuantity.
     * 
     * @return
     *     possible object is
     *     {@link MaximumVariantQuantityType }
     *     
     */
    public MaximumVariantQuantityType getMaximumVariantQuantity() {
        return maximumVariantQuantity;
    }

    /**
     * Imposta il valore della proprietà maximumVariantQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumVariantQuantityType }
     *     
     */
    public void setMaximumVariantQuantity(MaximumVariantQuantityType value) {
        this.maximumVariantQuantity = value;
    }

    /**
     * Recupera il valore della proprietà variantConstraintIndicator.
     * 
     * @return
     *     possible object is
     *     {@link VariantConstraintIndicatorType }
     *     
     */
    public VariantConstraintIndicatorType getVariantConstraintIndicator() {
        return variantConstraintIndicator;
    }

    /**
     * Imposta il valore della proprietà variantConstraintIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link VariantConstraintIndicatorType }
     *     
     */
    public void setVariantConstraintIndicator(VariantConstraintIndicatorType value) {
        this.variantConstraintIndicator = value;
    }

    /**
     * Gets the value of the acceptedVariantsDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the acceptedVariantsDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAcceptedVariantsDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AcceptedVariantsDescriptionType }
     * 
     * 
     */
    public List<AcceptedVariantsDescriptionType> getAcceptedVariantsDescription() {
        if (acceptedVariantsDescription == null) {
            acceptedVariantsDescription = new ArrayList<AcceptedVariantsDescriptionType>();
        }
        return this.acceptedVariantsDescription;
    }

    /**
     * Recupera il valore della proprietà variantConstraintCode.
     * 
     * @return
     *     possible object is
     *     {@link VariantConstraintCodeType }
     *     
     */
    public VariantConstraintCodeType getVariantConstraintCode() {
        return variantConstraintCode;
    }

    /**
     * Imposta il valore della proprietà variantConstraintCode.
     * 
     * @param value
     *     allowed object is
     *     {@link VariantConstraintCodeType }
     *     
     */
    public void setVariantConstraintCode(VariantConstraintCodeType value) {
        this.variantConstraintCode = value;
    }

    /**
     * Gets the value of the priceRevisionFormulaDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the priceRevisionFormulaDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPriceRevisionFormulaDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PriceRevisionFormulaDescriptionType }
     * 
     * 
     */
    public List<PriceRevisionFormulaDescriptionType> getPriceRevisionFormulaDescription() {
        if (priceRevisionFormulaDescription == null) {
            priceRevisionFormulaDescription = new ArrayList<PriceRevisionFormulaDescriptionType>();
        }
        return this.priceRevisionFormulaDescription;
    }

    /**
     * Recupera il valore della proprietà fundingProgramCode.
     * 
     * @return
     *     possible object is
     *     {@link FundingProgramCodeType }
     *     
     */
    public FundingProgramCodeType getFundingProgramCode() {
        return fundingProgramCode;
    }

    /**
     * Imposta il valore della proprietà fundingProgramCode.
     * 
     * @param value
     *     allowed object is
     *     {@link FundingProgramCodeType }
     *     
     */
    public void setFundingProgramCode(FundingProgramCodeType value) {
        this.fundingProgramCode = value;
    }

    /**
     * Gets the value of the fundingProgram property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the fundingProgram property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getFundingProgram().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link FundingProgramType }
     * 
     * 
     */
    public List<FundingProgramType> getFundingProgram() {
        if (fundingProgram == null) {
            fundingProgram = new ArrayList<FundingProgramType>();
        }
        return this.fundingProgram;
    }

    /**
     * Recupera il valore della proprietà maximumAdvertisementAmount.
     * 
     * @return
     *     possible object is
     *     {@link MaximumAdvertisementAmountType }
     *     
     */
    public MaximumAdvertisementAmountType getMaximumAdvertisementAmount() {
        return maximumAdvertisementAmount;
    }

    /**
     * Imposta il valore della proprietà maximumAdvertisementAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumAdvertisementAmountType }
     *     
     */
    public void setMaximumAdvertisementAmount(MaximumAdvertisementAmountType value) {
        this.maximumAdvertisementAmount = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the note property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getNote().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link NoteType }
     * 
     * 
     */
    public List<NoteType> getNote() {
        if (note == null) {
            note = new ArrayList<NoteType>();
        }
        return this.note;
    }

    /**
     * Recupera il valore della proprietà paymentFrequencyCode.
     * 
     * @return
     *     possible object is
     *     {@link PaymentFrequencyCodeType }
     *     
     */
    public PaymentFrequencyCodeType getPaymentFrequencyCode() {
        return paymentFrequencyCode;
    }

    /**
     * Imposta il valore della proprietà paymentFrequencyCode.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentFrequencyCodeType }
     *     
     */
    public void setPaymentFrequencyCode(PaymentFrequencyCodeType value) {
        this.paymentFrequencyCode = value;
    }

    /**
     * Recupera il valore della proprietà economicOperatorRegistryURI.
     * 
     * @return
     *     possible object is
     *     {@link EconomicOperatorRegistryURIType }
     *     
     */
    public EconomicOperatorRegistryURIType getEconomicOperatorRegistryURI() {
        return economicOperatorRegistryURI;
    }

    /**
     * Imposta il valore della proprietà economicOperatorRegistryURI.
     * 
     * @param value
     *     allowed object is
     *     {@link EconomicOperatorRegistryURIType }
     *     
     */
    public void setEconomicOperatorRegistryURI(EconomicOperatorRegistryURIType value) {
        this.economicOperatorRegistryURI = value;
    }

    /**
     * Recupera il valore della proprietà requiredCurriculaIndicator.
     * 
     * @return
     *     possible object is
     *     {@link RequiredCurriculaIndicatorType }
     *     
     */
    public RequiredCurriculaIndicatorType getRequiredCurriculaIndicator() {
        return requiredCurriculaIndicator;
    }

    /**
     * Imposta il valore della proprietà requiredCurriculaIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link RequiredCurriculaIndicatorType }
     *     
     */
    public void setRequiredCurriculaIndicator(RequiredCurriculaIndicatorType value) {
        this.requiredCurriculaIndicator = value;
    }

    /**
     * Recupera il valore della proprietà requiredCurriculaCode.
     * 
     * @return
     *     possible object is
     *     {@link RequiredCurriculaCodeType }
     *     
     */
    public RequiredCurriculaCodeType getRequiredCurriculaCode() {
        return requiredCurriculaCode;
    }

    /**
     * Imposta il valore della proprietà requiredCurriculaCode.
     * 
     * @param value
     *     allowed object is
     *     {@link RequiredCurriculaCodeType }
     *     
     */
    public void setRequiredCurriculaCode(RequiredCurriculaCodeType value) {
        this.requiredCurriculaCode = value;
    }

    /**
     * Recupera il valore della proprietà otherConditionsIndicator.
     * 
     * @return
     *     possible object is
     *     {@link OtherConditionsIndicatorType }
     *     
     */
    public OtherConditionsIndicatorType getOtherConditionsIndicator() {
        return otherConditionsIndicator;
    }

    /**
     * Imposta il valore della proprietà otherConditionsIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link OtherConditionsIndicatorType }
     *     
     */
    public void setOtherConditionsIndicator(OtherConditionsIndicatorType value) {
        this.otherConditionsIndicator = value;
    }

    /**
     * Recupera il valore della proprietà recurringProcurementIndicator.
     * 
     * @return
     *     possible object is
     *     {@link RecurringProcurementIndicatorType }
     *     
     */
    public RecurringProcurementIndicatorType getRecurringProcurementIndicator() {
        return recurringProcurementIndicator;
    }

    /**
     * Imposta il valore della proprietà recurringProcurementIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link RecurringProcurementIndicatorType }
     *     
     */
    public void setRecurringProcurementIndicator(RecurringProcurementIndicatorType value) {
        this.recurringProcurementIndicator = value;
    }

    /**
     * Gets the value of the recurringProcurementDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the recurringProcurementDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getRecurringProcurementDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link RecurringProcurementDescriptionType }
     * 
     * 
     */
    public List<RecurringProcurementDescriptionType> getRecurringProcurementDescription() {
        if (recurringProcurementDescription == null) {
            recurringProcurementDescription = new ArrayList<RecurringProcurementDescriptionType>();
        }
        return this.recurringProcurementDescription;
    }

    /**
     * Gets the value of the estimatedTimingFurtherPublication property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the estimatedTimingFurtherPublication property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getEstimatedTimingFurtherPublication().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link EstimatedTimingFurtherPublicationType }
     * 
     * 
     */
    public List<EstimatedTimingFurtherPublicationType> getEstimatedTimingFurtherPublication() {
        if (estimatedTimingFurtherPublication == null) {
            estimatedTimingFurtherPublication = new ArrayList<EstimatedTimingFurtherPublicationType>();
        }
        return this.estimatedTimingFurtherPublication;
    }

    /**
     * Gets the value of the additionalConditions property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the additionalConditions property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAdditionalConditions().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AdditionalConditionsType }
     * 
     * 
     */
    public List<AdditionalConditionsType> getAdditionalConditions() {
        if (additionalConditions == null) {
            additionalConditions = new ArrayList<AdditionalConditionsType>();
        }
        return this.additionalConditions;
    }

    /**
     * Recupera il valore della proprietà latestSecurityClearanceDate.
     * 
     * @return
     *     possible object is
     *     {@link LatestSecurityClearanceDateType }
     *     
     */
    public LatestSecurityClearanceDateType getLatestSecurityClearanceDate() {
        return latestSecurityClearanceDate;
    }

    /**
     * Imposta il valore della proprietà latestSecurityClearanceDate.
     * 
     * @param value
     *     allowed object is
     *     {@link LatestSecurityClearanceDateType }
     *     
     */
    public void setLatestSecurityClearanceDate(LatestSecurityClearanceDateType value) {
        this.latestSecurityClearanceDate = value;
    }

    /**
     * Recupera il valore della proprietà documentationFeeAmount.
     * 
     * @return
     *     possible object is
     *     {@link DocumentationFeeAmountType }
     *     
     */
    public DocumentationFeeAmountType getDocumentationFeeAmount() {
        return documentationFeeAmount;
    }

    /**
     * Imposta il valore della proprietà documentationFeeAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentationFeeAmountType }
     *     
     */
    public void setDocumentationFeeAmount(DocumentationFeeAmountType value) {
        this.documentationFeeAmount = value;
    }

    /**
     * Recupera il valore della proprietà multipleTendersCode.
     * 
     * @return
     *     possible object is
     *     {@link MultipleTendersCodeType }
     *     
     */
    public MultipleTendersCodeType getMultipleTendersCode() {
        return multipleTendersCode;
    }

    /**
     * Imposta il valore della proprietà multipleTendersCode.
     * 
     * @param value
     *     allowed object is
     *     {@link MultipleTendersCodeType }
     *     
     */
    public void setMultipleTendersCode(MultipleTendersCodeType value) {
        this.multipleTendersCode = value;
    }

    /**
     * Gets the value of the penaltyClause property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the penaltyClause property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPenaltyClause().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ClauseType }
     * 
     * 
     */
    public List<ClauseType> getPenaltyClause() {
        if (penaltyClause == null) {
            penaltyClause = new ArrayList<ClauseType>();
        }
        return this.penaltyClause;
    }

    /**
     * Gets the value of the requiredFinancialGuarantee property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the requiredFinancialGuarantee property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getRequiredFinancialGuarantee().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link FinancialGuaranteeType }
     * 
     * 
     */
    public List<FinancialGuaranteeType> getRequiredFinancialGuarantee() {
        if (requiredFinancialGuarantee == null) {
            requiredFinancialGuarantee = new ArrayList<FinancialGuaranteeType>();
        }
        return this.requiredFinancialGuarantee;
    }

    /**
     * Gets the value of the procurementLegislationDocumentReference property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the procurementLegislationDocumentReference property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getProcurementLegislationDocumentReference().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    public List<DocumentReferenceType> getProcurementLegislationDocumentReference() {
        if (procurementLegislationDocumentReference == null) {
            procurementLegislationDocumentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.procurementLegislationDocumentReference;
    }

    /**
     * Gets the value of the fiscalLegislationDocumentReference property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the fiscalLegislationDocumentReference property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getFiscalLegislationDocumentReference().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    public List<DocumentReferenceType> getFiscalLegislationDocumentReference() {
        if (fiscalLegislationDocumentReference == null) {
            fiscalLegislationDocumentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.fiscalLegislationDocumentReference;
    }

    /**
     * Gets the value of the environmentalLegislationDocumentReference property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the environmentalLegislationDocumentReference property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getEnvironmentalLegislationDocumentReference().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    public List<DocumentReferenceType> getEnvironmentalLegislationDocumentReference() {
        if (environmentalLegislationDocumentReference == null) {
            environmentalLegislationDocumentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.environmentalLegislationDocumentReference;
    }

    /**
     * Gets the value of the employmentLegislationDocumentReference property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the employmentLegislationDocumentReference property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getEmploymentLegislationDocumentReference().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    public List<DocumentReferenceType> getEmploymentLegislationDocumentReference() {
        if (employmentLegislationDocumentReference == null) {
            employmentLegislationDocumentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.employmentLegislationDocumentReference;
    }

    /**
     * Gets the value of the contractualDocumentReference property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the contractualDocumentReference property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getContractualDocumentReference().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    public List<DocumentReferenceType> getContractualDocumentReference() {
        if (contractualDocumentReference == null) {
            contractualDocumentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.contractualDocumentReference;
    }

    /**
     * Gets the value of the callForTendersDocumentReference property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the callForTendersDocumentReference property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCallForTendersDocumentReference().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    public List<DocumentReferenceType> getCallForTendersDocumentReference() {
        if (callForTendersDocumentReference == null) {
            callForTendersDocumentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.callForTendersDocumentReference;
    }

    /**
     * Recupera il valore della proprietà warrantyValidityPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getWarrantyValidityPeriod() {
        return warrantyValidityPeriod;
    }

    /**
     * Imposta il valore della proprietà warrantyValidityPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setWarrantyValidityPeriod(PeriodType value) {
        this.warrantyValidityPeriod = value;
    }

    /**
     * Gets the value of the paymentTerms property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the paymentTerms property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPaymentTerms().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentTermsType }
     * 
     * 
     */
    public List<PaymentTermsType> getPaymentTerms() {
        if (paymentTerms == null) {
            paymentTerms = new ArrayList<PaymentTermsType>();
        }
        return this.paymentTerms;
    }

    /**
     * Gets the value of the tendererQualificationRequest property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the tendererQualificationRequest property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTendererQualificationRequest().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TendererQualificationRequestType }
     * 
     * 
     */
    public List<TendererQualificationRequestType> getTendererQualificationRequest() {
        if (tendererQualificationRequest == null) {
            tendererQualificationRequest = new ArrayList<TendererQualificationRequestType>();
        }
        return this.tendererQualificationRequest;
    }

    /**
     * Gets the value of the allowedSubcontractTerms property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the allowedSubcontractTerms property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAllowedSubcontractTerms().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link SubcontractTermsType }
     * 
     * 
     */
    public List<SubcontractTermsType> getAllowedSubcontractTerms() {
        if (allowedSubcontractTerms == null) {
            allowedSubcontractTerms = new ArrayList<SubcontractTermsType>();
        }
        return this.allowedSubcontractTerms;
    }

    /**
     * Gets the value of the tenderPreparation property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the tenderPreparation property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTenderPreparation().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TenderPreparationType }
     * 
     * 
     */
    public List<TenderPreparationType> getTenderPreparation() {
        if (tenderPreparation == null) {
            tenderPreparation = new ArrayList<TenderPreparationType>();
        }
        return this.tenderPreparation;
    }

    /**
     * Gets the value of the contractExecutionRequirement property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the contractExecutionRequirement property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getContractExecutionRequirement().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ContractExecutionRequirementType }
     * 
     * 
     */
    public List<ContractExecutionRequirementType> getContractExecutionRequirement() {
        if (contractExecutionRequirement == null) {
            contractExecutionRequirement = new ArrayList<ContractExecutionRequirementType>();
        }
        return this.contractExecutionRequirement;
    }

    /**
     * Recupera il valore della proprietà awardingTerms.
     * 
     * @return
     *     possible object is
     *     {@link AwardingTermsType }
     *     
     */
    public AwardingTermsType getAwardingTerms() {
        return awardingTerms;
    }

    /**
     * Imposta il valore della proprietà awardingTerms.
     * 
     * @param value
     *     allowed object is
     *     {@link AwardingTermsType }
     *     
     */
    public void setAwardingTerms(AwardingTermsType value) {
        this.awardingTerms = value;
    }

    /**
     * Recupera il valore della proprietà additionalInformationParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getAdditionalInformationParty() {
        return additionalInformationParty;
    }

    /**
     * Imposta il valore della proprietà additionalInformationParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setAdditionalInformationParty(PartyType value) {
        this.additionalInformationParty = value;
    }

    /**
     * Recupera il valore della proprietà documentProviderParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getDocumentProviderParty() {
        return documentProviderParty;
    }

    /**
     * Imposta il valore della proprietà documentProviderParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setDocumentProviderParty(PartyType value) {
        this.documentProviderParty = value;
    }

    /**
     * Recupera il valore della proprietà tenderRecipientParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getTenderRecipientParty() {
        return tenderRecipientParty;
    }

    /**
     * Imposta il valore della proprietà tenderRecipientParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setTenderRecipientParty(PartyType value) {
        this.tenderRecipientParty = value;
    }

    /**
     * Recupera il valore della proprietà contractResponsibleParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getContractResponsibleParty() {
        return contractResponsibleParty;
    }

    /**
     * Imposta il valore della proprietà contractResponsibleParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setContractResponsibleParty(PartyType value) {
        this.contractResponsibleParty = value;
    }

    /**
     * Gets the value of the tenderEvaluationParty property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the tenderEvaluationParty property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTenderEvaluationParty().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PartyType }
     * 
     * 
     */
    public List<PartyType> getTenderEvaluationParty() {
        if (tenderEvaluationParty == null) {
            tenderEvaluationParty = new ArrayList<PartyType>();
        }
        return this.tenderEvaluationParty;
    }

    /**
     * Recupera il valore della proprietà qualificationRequestRecipientParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getQualificationRequestRecipientParty() {
        return qualificationRequestRecipientParty;
    }

    /**
     * Imposta il valore della proprietà qualificationRequestRecipientParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setQualificationRequestRecipientParty(PartyType value) {
        this.qualificationRequestRecipientParty = value;
    }

    /**
     * Recupera il valore della proprietà tenderValidityPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getTenderValidityPeriod() {
        return tenderValidityPeriod;
    }

    /**
     * Imposta il valore della proprietà tenderValidityPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setTenderValidityPeriod(PeriodType value) {
        this.tenderValidityPeriod = value;
    }

    /**
     * Recupera il valore della proprietà contractAcceptancePeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getContractAcceptancePeriod() {
        return contractAcceptancePeriod;
    }

    /**
     * Imposta il valore della proprietà contractAcceptancePeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setContractAcceptancePeriod(PeriodType value) {
        this.contractAcceptancePeriod = value;
    }

    /**
     * Recupera il valore della proprietà appealTerms.
     * 
     * @return
     *     possible object is
     *     {@link AppealTermsType }
     *     
     */
    public AppealTermsType getAppealTerms() {
        return appealTerms;
    }

    /**
     * Imposta il valore della proprietà appealTerms.
     * 
     * @param value
     *     allowed object is
     *     {@link AppealTermsType }
     *     
     */
    public void setAppealTerms(AppealTermsType value) {
        this.appealTerms = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the language property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getLanguage().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageType }
     * 
     * 
     */
    public List<LanguageType> getLanguage() {
        if (language == null) {
            language = new ArrayList<LanguageType>();
        }
        return this.language;
    }

    /**
     * Gets the value of the budgetAccountLine property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the budgetAccountLine property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getBudgetAccountLine().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link BudgetAccountLineType }
     * 
     * 
     */
    public List<BudgetAccountLineType> getBudgetAccountLine() {
        if (budgetAccountLine == null) {
            budgetAccountLine = new ArrayList<BudgetAccountLineType>();
        }
        return this.budgetAccountLine;
    }

    /**
     * Recupera il valore della proprietà replacedNoticeDocumentReference.
     * 
     * @return
     *     possible object is
     *     {@link DocumentReferenceType }
     *     
     */
    public DocumentReferenceType getReplacedNoticeDocumentReference() {
        return replacedNoticeDocumentReference;
    }

    /**
     * Imposta il valore della proprietà replacedNoticeDocumentReference.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentReferenceType }
     *     
     */
    public void setReplacedNoticeDocumentReference(DocumentReferenceType value) {
        this.replacedNoticeDocumentReference = value;
    }

    /**
     * Recupera il valore della proprietà lotDistribution.
     * 
     * @return
     *     possible object is
     *     {@link LotDistributionType }
     *     
     */
    public LotDistributionType getLotDistribution() {
        return lotDistribution;
    }

    /**
     * Imposta il valore della proprietà lotDistribution.
     * 
     * @param value
     *     allowed object is
     *     {@link LotDistributionType }
     *     
     */
    public void setLotDistribution(LotDistributionType value) {
        this.lotDistribution = value;
    }

    /**
     * Recupera il valore della proprietà postAwardProcess.
     * 
     * @return
     *     possible object is
     *     {@link PostAwardProcessType }
     *     
     */
    public PostAwardProcessType getPostAwardProcess() {
        return postAwardProcess;
    }

    /**
     * Imposta il valore della proprietà postAwardProcess.
     * 
     * @param value
     *     allowed object is
     *     {@link PostAwardProcessType }
     *     
     */
    public void setPostAwardProcess(PostAwardProcessType value) {
        this.postAwardProcess = value;
    }

    /**
     * Recupera il valore della proprietà economicOperatorShortList.
     * 
     * @return
     *     possible object is
     *     {@link EconomicOperatorShortListType }
     *     
     */
    public EconomicOperatorShortListType getEconomicOperatorShortList() {
        return economicOperatorShortList;
    }

    /**
     * Imposta il valore della proprietà economicOperatorShortList.
     * 
     * @param value
     *     allowed object is
     *     {@link EconomicOperatorShortListType }
     *     
     */
    public void setEconomicOperatorShortList(EconomicOperatorShortListType value) {
        this.economicOperatorShortList = value;
    }

    /**
     * Gets the value of the securityClearanceTerm property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the securityClearanceTerm property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSecurityClearanceTerm().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link SecurityClearanceTermType }
     * 
     * 
     */
    public List<SecurityClearanceTermType> getSecurityClearanceTerm() {
        if (securityClearanceTerm == null) {
            securityClearanceTerm = new ArrayList<SecurityClearanceTermType>();
        }
        return this.securityClearanceTerm;
    }

}
