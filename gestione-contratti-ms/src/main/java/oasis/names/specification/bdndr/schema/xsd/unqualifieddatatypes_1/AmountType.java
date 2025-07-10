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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.FrameworkMaximumAmountType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.GroupFrameworkMaximumValueAmountType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.GroupFrameworkReestimatedValueAmountType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.OverallApproximateFrameworkContractsAmountType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.OverallMaximumFrameworkContractsAmountType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.PenaltiesAmountType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ReestimatedValueAmountType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.RemedyAmountType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.RevenueBuyerAmountType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.RevenueUserAmountType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.TermAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AdvertisementAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AllowanceTotalAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AnnualAverageAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AverageAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AverageSubsequentContractAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BalanceAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BaseAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CallBaseAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CallExtensionAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ChargeTotalAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CorporateStockAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CorrectionAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CorrectionUnitAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CreditLineAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DebitLineAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DeclaredCarriageValueAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DeclaredCustomsValueAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DeclaredForCarriageValueAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DeclaredStatisticsValueAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DocumentationFeeAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EstimatedAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EstimatedMaximumValueAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EstimatedOverallContractAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EstimatedOverallFrameworkContractsAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExpectedAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FaceValueAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FeeAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FreeOnBoardValueAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HigherTenderAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InsurancePremiumAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InsuranceValueAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InventoryValueAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LiabilityAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LineExtensionAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LowerTenderAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MarketValueAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumAdvertisementAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumPaidAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumValueAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MinimumAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaidAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PartyCapacityAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PayableAlternativeAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PayableAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PayableRoundingAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PenaltyAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PerUnitAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PrepaidAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PriceAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RequiredFeeAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResponseAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RoundingAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SettlementDiscountAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxEnergyAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxEnergyBalanceAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxEnergyOnAccountAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxExclusiveAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxInclusiveAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxInclusiveLineExtensionAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxableAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ThresholdAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalBalanceAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalCreditAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalDebitAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalInvoiceAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalPaymentAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalTaskAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalTaxAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TransactionCurrencyTaxAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValueAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.WithholdingTaxTotalAmountType;


/**
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:UniqueID xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;BDNDRUDT000001&amp;lt;/ccts:UniqueID&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:CategoryCode xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;UDT&amp;lt;/ccts:CategoryCode&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:DictionaryEntryName xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;Amount. Type&amp;lt;/ccts:DictionaryEntryName&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:VersionID xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;1.0&amp;lt;/ccts:VersionID&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:Definition xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;A number of monetary units specified using a given unit of currency.&amp;lt;/ccts:Definition&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:RepresentationTermName xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;Amount&amp;lt;/ccts:RepresentationTermName&amp;gt;
 * &lt;/pre&gt;
 * 
 *       
 * 
 * &lt;p&gt;Classe Java per AmountType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="AmountType"&amp;gt;
 *   &amp;lt;simpleContent&amp;gt;
 *     &amp;lt;restriction base="&amp;lt;urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2&amp;gt;AmountType"&amp;gt;
 *       &amp;lt;attribute name="currencyID" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/simpleContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmountType")
@XmlSeeAlso({
    AdvertisementAmountType.class,
    AllowanceTotalAmountType.class,
    oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AmountType.class,
    AnnualAverageAmountType.class,
    AverageAmountType.class,
    AverageSubsequentContractAmountType.class,
    BalanceAmountType.class,
    BaseAmountType.class,
    CallBaseAmountType.class,
    CallExtensionAmountType.class,
    ChargeTotalAmountType.class,
    CorporateStockAmountType.class,
    CorrectionAmountType.class,
    CorrectionUnitAmountType.class,
    CreditLineAmountType.class,
    DebitLineAmountType.class,
    DeclaredCarriageValueAmountType.class,
    DeclaredCustomsValueAmountType.class,
    DeclaredForCarriageValueAmountType.class,
    DeclaredStatisticsValueAmountType.class,
    DocumentationFeeAmountType.class,
    EstimatedAmountType.class,
    EstimatedMaximumValueAmountType.class,
    EstimatedOverallContractAmountType.class,
    EstimatedOverallFrameworkContractsAmountType.class,
    ExpectedAmountType.class,
    FaceValueAmountType.class,
    FeeAmountType.class,
    FreeOnBoardValueAmountType.class,
    HigherTenderAmountType.class,
    InsurancePremiumAmountType.class,
    InsuranceValueAmountType.class,
    InventoryValueAmountType.class,
    LiabilityAmountType.class,
    LineExtensionAmountType.class,
    LowerTenderAmountType.class,
    MarketValueAmountType.class,
    MaximumAdvertisementAmountType.class,
    MaximumAmountType.class,
    MaximumPaidAmountType.class,
    MaximumValueAmountType.class,
    MinimumAmountType.class,
    PaidAmountType.class,
    PartyCapacityAmountType.class,
    PayableAlternativeAmountType.class,
    PayableAmountType.class,
    PayableRoundingAmountType.class,
    PenaltyAmountType.class,
    PerUnitAmountType.class,
    PrepaidAmountType.class,
    PriceAmountType.class,
    RequiredFeeAmountType.class,
    ResponseAmountType.class,
    RoundingAmountType.class,
    SettlementDiscountAmountType.class,
    TaxAmountType.class,
    TaxEnergyAmountType.class,
    TaxEnergyBalanceAmountType.class,
    TaxEnergyOnAccountAmountType.class,
    TaxExclusiveAmountType.class,
    TaxInclusiveAmountType.class,
    TaxInclusiveLineExtensionAmountType.class,
    TaxableAmountType.class,
    ThresholdAmountType.class,
    TotalAmountType.class,
    TotalBalanceAmountType.class,
    TotalCreditAmountType.class,
    TotalDebitAmountType.class,
    TotalInvoiceAmountType.class,
    TotalPaymentAmountType.class,
    TotalTaskAmountType.class,
    TotalTaxAmountType.class,
    TransactionCurrencyTaxAmountType.class,
    ValueAmountType.class,
    WithholdingTaxTotalAmountType.class,
    FrameworkMaximumAmountType.class,
    GroupFrameworkMaximumValueAmountType.class,
    GroupFrameworkReestimatedValueAmountType.class,
    OverallApproximateFrameworkContractsAmountType.class,
    OverallMaximumFrameworkContractsAmountType.class,
    PenaltiesAmountType.class,
    ReestimatedValueAmountType.class,
    RemedyAmountType.class,
    RevenueBuyerAmountType.class,
    RevenueUserAmountType.class,
    TermAmountType.class
})
public class AmountType
    extends un.unece.uncefact.data.specification.corecomponenttypeschemamodule._2.AmountType
{


}
