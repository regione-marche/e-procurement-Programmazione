//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package oasis.names.specification.bdndr.schema.xsd.unqualifieddatatypes_1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.AcquiringCPBIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.AwardingCPBIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ContractFrameworkIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.DPSTerminationIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ExtendedDurationIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.GroupLeadIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ListedOnRegulatedMarketIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.NaturalPersonIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.PercentageKnownIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ProcedureRelaunchIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ProcurementDocumentsChangeIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.SecondStageIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.TenderRankedIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.TenderVariantIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.TermIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ValueKnownIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.WithdrawnAppealIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AcceptanceIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AcceptedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AdValoremIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AnimalFoodApprovedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AnimalFoodIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AtAnchorageIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AuctionConstraintIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BackOrderAllowedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BalanceBroughtForwardIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BasedOnConsensusIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BindingOnBuyerIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BulkCargoIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BuriedAtSeaIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CabotageIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CandidateReductionConstraintIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CatalogueIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ChargeIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CompletionIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ConsolidatableIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ContainerizedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CopyIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CustomsImportClassifiedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DangerousGoodsApprovedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DiedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ElectronicCatalogueUsageIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ElectronicInvoiceAcceptedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ElectronicOrderUsageIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ElectronicPaymentUsageIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EvacuatedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExpectedAnchorageIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExpectedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FollowupContractIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FreeOfChargeIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FridayAvailabilityIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FrozenDocumentIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FulfilmentIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FullyPaidSharesIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FumigatedCargoTransportIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.GeneralCargoIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.GovernmentAgreementConstraintIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HazardousRiskIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HumanFoodApprovedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HumanFoodIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IMOGuidelinesOnBoardIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IndicationIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InfectiousDiseaseCaseOnBoardIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InitiatingPartyIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ItemUpdateRequestIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LegalStatusIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LivestockIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ManagementPlanImplementedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ManagementPlanOnBoardIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MarkAttentionIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MarkCareIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MedicalPractitionerConsultedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MondayAvailabilityIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MoreIllThanExpectedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NoFurtherNegotiationIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OnCarriageIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OptionalLineItemIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OrderableIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OtherConditionsIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PartialDeliveryIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PowerIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PreCarriageIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PrepaidIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PricingUpdateRequestIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PrivatePartyIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PrizeIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PublicPartyIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PublishAwardIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RecurringProcurementIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RefrigeratedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RefrigerationOnIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReinspectionRequiredIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RenewalsIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReportedToMedicalOfficerIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RequiredCurriculaIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResponseIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReturnabilityIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReturnableMaterialIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SMESuitableIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SSPOnBoardIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SSPSecurityMeasuresAppliedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SanitaryMeasuresAppliedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SaturdayAvailabilityIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ServiceProviderPartyIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SickAnimalOnBoardIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SoleProprietorshipIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SpecialSecurityIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SplitConsignmentIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.StatusAvailableIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.StillIllIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.StillOnBoardIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.StowawaysFoundOnBoardIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SundayAvailabilityIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxEvidenceIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxIncludedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TerminatedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TestIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ThirdPartyPayerIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ThursdayAvailabilityIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ToOrderIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TuesdayAvailabilityIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.UnknownPriceIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValidISSCIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValidSanitationCertificateOnBoardIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.VariantConstraintIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.WednesdayAvailabilityIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.WithdrawOfferIndicatorType;


/**
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:UniqueID xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;BDNDRUDT0000012&amp;lt;/ccts:UniqueID&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:CategoryCode xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;UDT&amp;lt;/ccts:CategoryCode&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:DictionaryEntryName xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;Indicator. Type&amp;lt;/ccts:DictionaryEntryName&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:VersionID xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;1.0&amp;lt;/ccts:VersionID&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:Definition xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;A list of two mutually exclusive Boolean values that express the only possible states of a property.&amp;lt;/ccts:Definition&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:RepresentationTermName xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;Indicator&amp;lt;/ccts:RepresentationTermName&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:PrimitiveType xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;string&amp;lt;/ccts:PrimitiveType&amp;gt;
 * &lt;/pre&gt;
 * 
 *       
 * 
 * &lt;p&gt;Classe Java per IndicatorType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="IndicatorType"&amp;gt;
 *   &amp;lt;simpleContent&amp;gt;
 *     &amp;lt;extension base="&amp;lt;http://www.w3.org/2001/XMLSchema&amp;gt;boolean"&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/simpleContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndicatorType", propOrder = {
    "value"
})
@XmlSeeAlso({
    AcceptanceIndicatorType.class,
    AcceptedIndicatorType.class,
    AdValoremIndicatorType.class,
    AnimalFoodApprovedIndicatorType.class,
    AnimalFoodIndicatorType.class,
    AtAnchorageIndicatorType.class,
    AuctionConstraintIndicatorType.class,
    BackOrderAllowedIndicatorType.class,
    BalanceBroughtForwardIndicatorType.class,
    BasedOnConsensusIndicatorType.class,
    BindingOnBuyerIndicatorType.class,
    BulkCargoIndicatorType.class,
    BuriedAtSeaIndicatorType.class,
    CabotageIndicatorType.class,
    CandidateReductionConstraintIndicatorType.class,
    CatalogueIndicatorType.class,
    ChargeIndicatorType.class,
    CompletionIndicatorType.class,
    ConsolidatableIndicatorType.class,
    ContainerizedIndicatorType.class,
    CopyIndicatorType.class,
    CustomsImportClassifiedIndicatorType.class,
    DangerousGoodsApprovedIndicatorType.class,
    DiedIndicatorType.class,
    ElectronicCatalogueUsageIndicatorType.class,
    ElectronicInvoiceAcceptedIndicatorType.class,
    ElectronicOrderUsageIndicatorType.class,
    ElectronicPaymentUsageIndicatorType.class,
    EvacuatedIndicatorType.class,
    ExpectedAnchorageIndicatorType.class,
    ExpectedIndicatorType.class,
    FollowupContractIndicatorType.class,
    FreeOfChargeIndicatorType.class,
    FridayAvailabilityIndicatorType.class,
    FrozenDocumentIndicatorType.class,
    FulfilmentIndicatorType.class,
    FullyPaidSharesIndicatorType.class,
    FumigatedCargoTransportIndicatorType.class,
    GeneralCargoIndicatorType.class,
    GovernmentAgreementConstraintIndicatorType.class,
    HazardousRiskIndicatorType.class,
    HumanFoodApprovedIndicatorType.class,
    HumanFoodIndicatorType.class,
    IMOGuidelinesOnBoardIndicatorType.class,
    IndicationIndicatorType.class,
    InfectiousDiseaseCaseOnBoardIndicatorType.class,
    InitiatingPartyIndicatorType.class,
    ItemUpdateRequestIndicatorType.class,
    LegalStatusIndicatorType.class,
    LivestockIndicatorType.class,
    ManagementPlanImplementedIndicatorType.class,
    ManagementPlanOnBoardIndicatorType.class,
    MarkAttentionIndicatorType.class,
    MarkCareIndicatorType.class,
    MedicalPractitionerConsultedIndicatorType.class,
    MondayAvailabilityIndicatorType.class,
    MoreIllThanExpectedIndicatorType.class,
    NoFurtherNegotiationIndicatorType.class,
    OnCarriageIndicatorType.class,
    OptionalLineItemIndicatorType.class,
    OrderableIndicatorType.class,
    OtherConditionsIndicatorType.class,
    PartialDeliveryIndicatorType.class,
    PowerIndicatorType.class,
    PreCarriageIndicatorType.class,
    PrepaidIndicatorType.class,
    PricingUpdateRequestIndicatorType.class,
    PrivatePartyIndicatorType.class,
    PrizeIndicatorType.class,
    PublicPartyIndicatorType.class,
    PublishAwardIndicatorType.class,
    RecurringProcurementIndicatorType.class,
    RefrigeratedIndicatorType.class,
    RefrigerationOnIndicatorType.class,
    ReinspectionRequiredIndicatorType.class,
    RenewalsIndicatorType.class,
    ReportedToMedicalOfficerIndicatorType.class,
    RequiredCurriculaIndicatorType.class,
    ResponseIndicatorType.class,
    ReturnabilityIndicatorType.class,
    ReturnableMaterialIndicatorType.class,
    SMESuitableIndicatorType.class,
    SSPOnBoardIndicatorType.class,
    SSPSecurityMeasuresAppliedIndicatorType.class,
    SanitaryMeasuresAppliedIndicatorType.class,
    SaturdayAvailabilityIndicatorType.class,
    ServiceProviderPartyIndicatorType.class,
    SickAnimalOnBoardIndicatorType.class,
    SoleProprietorshipIndicatorType.class,
    SpecialSecurityIndicatorType.class,
    SplitConsignmentIndicatorType.class,
    StatusAvailableIndicatorType.class,
    StillIllIndicatorType.class,
    StillOnBoardIndicatorType.class,
    StowawaysFoundOnBoardIndicatorType.class,
    SundayAvailabilityIndicatorType.class,
    TaxEvidenceIndicatorType.class,
    TaxIncludedIndicatorType.class,
    TerminatedIndicatorType.class,
    TestIndicatorType.class,
    ThirdPartyPayerIndicatorType.class,
    ThursdayAvailabilityIndicatorType.class,
    ToOrderIndicatorType.class,
    TuesdayAvailabilityIndicatorType.class,
    UnknownPriceIndicatorType.class,
    ValidISSCIndicatorType.class,
    ValidSanitationCertificateOnBoardIndicatorType.class,
    VariantConstraintIndicatorType.class,
    WednesdayAvailabilityIndicatorType.class,
    WithdrawOfferIndicatorType.class,
    ProcedureRelaunchIndicatorType.class,
    AcquiringCPBIndicatorType.class,
    AwardingCPBIndicatorType.class,
    ContractFrameworkIndicatorType.class,
    DPSTerminationIndicatorType.class,
    ExtendedDurationIndicatorType.class,
    GroupLeadIndicatorType.class,
    ListedOnRegulatedMarketIndicatorType.class,
    NaturalPersonIndicatorType.class,
    PercentageKnownIndicatorType.class,
    ProcurementDocumentsChangeIndicatorType.class,
    SecondStageIndicatorType.class,
    TenderRankedIndicatorType.class,
    TenderVariantIndicatorType.class,
    TermIndicatorType.class,
    ValueKnownIndicatorType.class,
    WithdrawnAppealIndicatorType.class
})
public class IndicatorType {

    @XmlValue
    protected boolean value;

    /**
     * Recupera il valore della proprietà value.
     * 
     */
    public boolean isValue() {
        return value;
    }

    /**
     * Imposta il valore della proprietà value.
     * 
     */
    public void setValue(boolean value) {
        this.value = value;
    }

}
