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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.AppealPreviousStageIDType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.AppealStageIDType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ChangedNoticeIdentifierType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ChangedSectionIdentifierType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.FinancingIdentifierType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.GazetteIDType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.NoticePublicationIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AccessToolsURIType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AccountIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AdditionalAccountIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AgencyIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AircraftIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AttributeIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AuctionURIType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AwardIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AwardingCriterionIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BarcodeSymbologyIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BrokerAssignedIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BusinessClassificationEvidenceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BusinessIdentityEvidenceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BuyerEventIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BuyerProfileURIType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CV2IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CarrierAssignedIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ChipApplicationIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CompanyIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ConsigneeAssignedIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ConsignorAssignedIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ConsumptionIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ConsumptionReportIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ContractFolderIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ContractedCarrierAssignedIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CustomerAssignedAccountIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CustomizationIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DocumentIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EconomicOperatorRegistryURIType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EndpointIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EndpointURIType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExchangeMarketIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExpectedIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExpectedURIType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExtendedIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FormatIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FreightForwarderAssignedIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.GateIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.GoodsItemPassportCounterfoilIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.GoodsItemPassportIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HazardClassIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IdentificationIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ImmobilizationCertificateIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InformationURIType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InstructionIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IssueNumberIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IssuerIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IssuerScopeIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.JourneyIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LanguageIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LicensePlateIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LineIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LoadingSequenceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LocationIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LogoReferenceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LotNumberIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LotsGroupIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LowerOrangeHazardPlacardIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MMSIRegistrationIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MarkingIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NationalityIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NetworkIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OntologyURIType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OpenTenderIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OriginalContractingSystemIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OriginalJobIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ParentDocumentIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ParentDocumentLineReferenceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ParentDocumentVersionIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ParticipantIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaymentIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaymentMeansIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaymentTermsDetailsURIType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PerformingCarrierAssignedIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PositionInPortIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PrepaidPaymentReferenceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PreviousJobIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PreviousVersionIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PrimaryAccountNumberIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ProductTraceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ProfileExecutionIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ProfileIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ProtocolIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RadioCallSignIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RailCarIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReferenceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReferencedConsignmentIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RegistrationIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RegistrationNationalityIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReleaseIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RequestForQuotationLineIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RequiredCustomsIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResponseIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResponseURIType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RevisedForecastLineIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SalesOrderIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SalesOrderLineIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SchemaURIType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SchemeURIType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SecurityIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SellerEventIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SequenceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SequenceNumberIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SerialIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ShippingOrderIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SignatureIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SpecificationIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SubscriberIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SuccessiveSequenceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SupplierAssignedAccountIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TankIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TenderEnvelopeIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TraceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TrackingIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TrainIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TransportExecutionPlanReferenceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TransportationServiceDetailsURIType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.UBLVersionIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.URIType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.UUIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.UpperOrangeHazardPlacardIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValidatedCriterionPropertyIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValidatorIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.VariantIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.VersionIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.VesselIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.WebsiteURIType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.WeighingDeviceIDType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ExtensionAgencyIDType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ExtensionAgencyURIType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ExtensionURIType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ExtensionVersionIDType;


/**
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:UniqueID xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;BDNDRUDT0000011&amp;lt;/ccts:UniqueID&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:CategoryCode xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;UDT&amp;lt;/ccts:CategoryCode&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:DictionaryEntryName xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;Identifier. Type&amp;lt;/ccts:DictionaryEntryName&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:VersionID xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;1.0&amp;lt;/ccts:VersionID&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:Definition xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;A character string to identify and uniquely distinguish one instance of an object in an identification scheme from all other objects in the same scheme, together with relevant supplementary information.&amp;lt;/ccts:Definition&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:RepresentationTermName xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;Identifier&amp;lt;/ccts:RepresentationTermName&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:PrimitiveType xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;string&amp;lt;/ccts:PrimitiveType&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:UsageRule xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;Other supplementary components in the CCT are captured as part of the token and name for the schema module containing the identifier list and thus, are not declared as attributes. &amp;lt;/ccts:UsageRule&amp;gt;
 * &lt;/pre&gt;
 * 
 *       
 * 
 * &lt;p&gt;Classe Java per IdentifierType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="IdentifierType"&amp;gt;
 *   &amp;lt;simpleContent&amp;gt;
 *     &amp;lt;extension base="&amp;lt;urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2&amp;gt;IdentifierType"&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/simpleContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentifierType")
@XmlSeeAlso({
    AccessToolsURIType.class,
    AccountIDType.class,
    AdditionalAccountIDType.class,
    AgencyIDType.class,
    AircraftIDType.class,
    AttributeIDType.class,
    AuctionURIType.class,
    AwardIDType.class,
    AwardingCriterionIDType.class,
    BarcodeSymbologyIDType.class,
    BrokerAssignedIDType.class,
    BusinessClassificationEvidenceIDType.class,
    BusinessIdentityEvidenceIDType.class,
    BuyerEventIDType.class,
    BuyerProfileURIType.class,
    CV2IDType.class,
    CarrierAssignedIDType.class,
    ChipApplicationIDType.class,
    CompanyIDType.class,
    ConsigneeAssignedIDType.class,
    ConsignorAssignedIDType.class,
    ConsumptionIDType.class,
    ConsumptionReportIDType.class,
    ContractFolderIDType.class,
    ContractedCarrierAssignedIDType.class,
    CustomerAssignedAccountIDType.class,
    CustomizationIDType.class,
    DocumentIDType.class,
    EconomicOperatorRegistryURIType.class,
    EndpointIDType.class,
    EndpointURIType.class,
    ExchangeMarketIDType.class,
    ExpectedIDType.class,
    ExpectedURIType.class,
    ExtendedIDType.class,
    FormatIDType.class,
    FreightForwarderAssignedIDType.class,
    GateIDType.class,
    GoodsItemPassportCounterfoilIDType.class,
    GoodsItemPassportIDType.class,
    HazardClassIDType.class,
    IDType.class,
    IdentificationIDType.class,
    ImmobilizationCertificateIDType.class,
    InformationURIType.class,
    InstructionIDType.class,
    IssueNumberIDType.class,
    IssuerIDType.class,
    IssuerScopeIDType.class,
    JourneyIDType.class,
    LanguageIDType.class,
    LicensePlateIDType.class,
    LineIDType.class,
    LoadingSequenceIDType.class,
    LocationIDType.class,
    LogoReferenceIDType.class,
    LotNumberIDType.class,
    LotsGroupIDType.class,
    LowerOrangeHazardPlacardIDType.class,
    MMSIRegistrationIDType.class,
    MarkingIDType.class,
    NationalityIDType.class,
    NetworkIDType.class,
    OIDType.class,
    OntologyURIType.class,
    OpenTenderIDType.class,
    OriginalContractingSystemIDType.class,
    OriginalJobIDType.class,
    ParentDocumentIDType.class,
    ParentDocumentLineReferenceIDType.class,
    ParentDocumentVersionIDType.class,
    ParticipantIDType.class,
    PaymentIDType.class,
    PaymentMeansIDType.class,
    PaymentTermsDetailsURIType.class,
    PerformingCarrierAssignedIDType.class,
    PositionInPortIDType.class,
    PrepaidPaymentReferenceIDType.class,
    PreviousJobIDType.class,
    PreviousVersionIDType.class,
    PrimaryAccountNumberIDType.class,
    ProductTraceIDType.class,
    ProfileExecutionIDType.class,
    ProfileIDType.class,
    ProtocolIDType.class,
    RadioCallSignIDType.class,
    RailCarIDType.class,
    ReferenceIDType.class,
    ReferencedConsignmentIDType.class,
    RegistrationIDType.class,
    RegistrationNationalityIDType.class,
    ReleaseIDType.class,
    RequestForQuotationLineIDType.class,
    RequiredCustomsIDType.class,
    ResponseIDType.class,
    ResponseURIType.class,
    RevisedForecastLineIDType.class,
    SalesOrderIDType.class,
    SalesOrderLineIDType.class,
    SchemaURIType.class,
    SchemeURIType.class,
    SecurityIDType.class,
    SellerEventIDType.class,
    SequenceIDType.class,
    SequenceNumberIDType.class,
    SerialIDType.class,
    ShippingOrderIDType.class,
    SignatureIDType.class,
    SpecificationIDType.class,
    SubscriberIDType.class,
    SuccessiveSequenceIDType.class,
    SupplierAssignedAccountIDType.class,
    TankIDType.class,
    TenderEnvelopeIDType.class,
    TraceIDType.class,
    TrackingIDType.class,
    TrainIDType.class,
    TransportExecutionPlanReferenceIDType.class,
    TransportationServiceDetailsURIType.class,
    UBLVersionIDType.class,
    URIType.class,
    UUIDType.class,
    UpperOrangeHazardPlacardIDType.class,
    ValidatedCriterionPropertyIDType.class,
    ValidatorIDType.class,
    VariantIDType.class,
    VersionIDType.class,
    VesselIDType.class,
    WebsiteURIType.class,
    WeighingDeviceIDType.class,
    ExtensionAgencyIDType.class,
    ExtensionAgencyURIType.class,
    ExtensionURIType.class,
    ExtensionVersionIDType.class,
    AppealStageIDType.class,
    AppealPreviousStageIDType.class,
    ChangedNoticeIdentifierType.class,
    ChangedSectionIdentifierType.class,
    FinancingIdentifierType.class,
    GazetteIDType.class,
    NoticePublicationIDType.class
})
public class IdentifierType
    extends un.unece.uncefact.data.specification.corecomponenttypeschemamodule._2.IdentifierType
{


}
