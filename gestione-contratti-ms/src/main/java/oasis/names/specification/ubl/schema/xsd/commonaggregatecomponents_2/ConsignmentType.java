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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AnimalFoodIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BrokerAssignedIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BulkCargoIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CarrierAssignedIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CarrierServiceInstructionsType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ChargeableWeightMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ChildConsignmentQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ConsigneeAssignedIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ConsignmentQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ConsignorAssignedIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ConsolidatableIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ContainerizedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ContractedCarrierAssignedIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CustomsClearanceServiceInstructionsType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DeclaredCustomsValueAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DeclaredForCarriageValueAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DeclaredStatisticsValueAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DeliveryInstructionsType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ForwarderServiceInstructionsType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FreeOnBoardValueAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FreightForwarderAssignedIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.GeneralCargoIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.GrossVolumeMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.GrossWeightMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HandlingCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HandlingInstructionsType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HaulageInstructionsType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HazardousRiskIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HumanFoodIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InformationType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InsurancePremiumAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InsuranceValueAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LivestockIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LoadingLengthMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LoadingSequenceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NetNetWeightMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NetVolumeMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NetWeightMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PerformingCarrierAssignedIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RemarksType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SequenceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ShippingPriorityLevelCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SpecialInstructionsType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SpecialSecurityIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SpecialServiceInstructionsType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SplitConsignmentIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SummaryDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TariffCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TariffDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ThirdPartyPayerIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalGoodsItemQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalInvoiceAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalPackagesQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalTransportHandlingUnitQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per ConsignmentType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ConsignmentType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CarrierAssignedID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ConsigneeAssignedID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ConsignorAssignedID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}FreightForwarderAssignedID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}BrokerAssignedID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ContractedCarrierAssignedID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PerformingCarrierAssignedID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SummaryDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TotalInvoiceAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DeclaredCustomsValueAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TariffDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TariffCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}InsurancePremiumAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}GrossWeightMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}NetWeightMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}NetNetWeightMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ChargeableWeightMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}GrossVolumeMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}NetVolumeMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LoadingLengthMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Remarks" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HazardousRiskIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AnimalFoodIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HumanFoodIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LivestockIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}BulkCargoIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ContainerizedIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}GeneralCargoIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SpecialSecurityIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ThirdPartyPayerIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CarrierServiceInstructions" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CustomsClearanceServiceInstructions" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ForwarderServiceInstructions" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SpecialServiceInstructions" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SequenceID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ShippingPriorityLevelCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HandlingCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HandlingInstructions" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Information" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TotalGoodsItemQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TotalTransportHandlingUnitQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}InsuranceValueAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DeclaredForCarriageValueAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DeclaredStatisticsValueAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}FreeOnBoardValueAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SpecialInstructions" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SplitConsignmentIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DeliveryInstructions" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ConsignmentQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ConsolidatableIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HaulageInstructions" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LoadingSequenceID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ChildConsignmentQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TotalPackagesQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ConsolidatedShipment" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CustomsDeclaration" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RequestedPickupTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RequestedDeliveryTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PlannedPickupTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PlannedDeliveryTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ActualPickupTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ActualDeliveryTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Status" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ChildConsignment" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ConsigneeParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ExporterParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ConsignorParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ImporterParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CarrierParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}FreightForwarderParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}NotifyParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OriginalDespatchParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}FinalDeliveryParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PerformingCarrierParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SubstituteCarrierParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}LogisticsOperatorParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TransportAdvisorParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}HazardousItemNotificationParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}InsuranceParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}MortgageHolderParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}BillOfLadingHolderParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OriginalDepartureCountry" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}FinalDestinationCountry" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TransitCountry" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TransportContract" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TransportEvent" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OriginalDespatchTransportationService" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}FinalDeliveryTransportationService" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DeliveryTerms" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PaymentTerms" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CollectPaymentTerms" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DisbursementPaymentTerms" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PrepaidPaymentTerms" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}FreightAllowanceCharge" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ExtraAllowanceCharge" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}MainCarriageShipmentStage" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PreCarriageShipmentStage" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OnCarriageShipmentStage" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TransportHandlingUnit" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}FirstArrivalPortLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}LastExitPortLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OfficeOfEntryLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OfficeOfSubSequentiallyEntryLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OfficeOfExitLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OfficeOfDepartureLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OfficeOfDestinationLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OfficeOfImportLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OfficeOfExportLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DocumentReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsignmentType", propOrder = {
    "ublExtensions",
    "id",
    "carrierAssignedID",
    "consigneeAssignedID",
    "consignorAssignedID",
    "freightForwarderAssignedID",
    "brokerAssignedID",
    "contractedCarrierAssignedID",
    "performingCarrierAssignedID",
    "summaryDescription",
    "totalInvoiceAmount",
    "declaredCustomsValueAmount",
    "tariffDescription",
    "tariffCode",
    "insurancePremiumAmount",
    "grossWeightMeasure",
    "netWeightMeasure",
    "netNetWeightMeasure",
    "chargeableWeightMeasure",
    "grossVolumeMeasure",
    "netVolumeMeasure",
    "loadingLengthMeasure",
    "remarks",
    "hazardousRiskIndicator",
    "animalFoodIndicator",
    "humanFoodIndicator",
    "livestockIndicator",
    "bulkCargoIndicator",
    "containerizedIndicator",
    "generalCargoIndicator",
    "specialSecurityIndicator",
    "thirdPartyPayerIndicator",
    "carrierServiceInstructions",
    "customsClearanceServiceInstructions",
    "forwarderServiceInstructions",
    "specialServiceInstructions",
    "sequenceID",
    "shippingPriorityLevelCode",
    "handlingCode",
    "handlingInstructions",
    "information",
    "totalGoodsItemQuantity",
    "totalTransportHandlingUnitQuantity",
    "insuranceValueAmount",
    "declaredForCarriageValueAmount",
    "declaredStatisticsValueAmount",
    "freeOnBoardValueAmount",
    "specialInstructions",
    "splitConsignmentIndicator",
    "deliveryInstructions",
    "consignmentQuantity",
    "consolidatableIndicator",
    "haulageInstructions",
    "loadingSequenceID",
    "childConsignmentQuantity",
    "totalPackagesQuantity",
    "consolidatedShipment",
    "customsDeclaration",
    "requestedPickupTransportEvent",
    "requestedDeliveryTransportEvent",
    "plannedPickupTransportEvent",
    "plannedDeliveryTransportEvent",
    "actualPickupTransportEvent",
    "actualDeliveryTransportEvent",
    "status",
    "childConsignment",
    "consigneeParty",
    "exporterParty",
    "consignorParty",
    "importerParty",
    "carrierParty",
    "freightForwarderParty",
    "notifyParty",
    "originalDespatchParty",
    "finalDeliveryParty",
    "performingCarrierParty",
    "substituteCarrierParty",
    "logisticsOperatorParty",
    "transportAdvisorParty",
    "hazardousItemNotificationParty",
    "insuranceParty",
    "mortgageHolderParty",
    "billOfLadingHolderParty",
    "originalDepartureCountry",
    "finalDestinationCountry",
    "transitCountry",
    "transportContract",
    "transportEvent",
    "originalDespatchTransportationService",
    "finalDeliveryTransportationService",
    "deliveryTerms",
    "paymentTerms",
    "collectPaymentTerms",
    "disbursementPaymentTerms",
    "prepaidPaymentTerms",
    "freightAllowanceCharge",
    "extraAllowanceCharge",
    "mainCarriageShipmentStage",
    "preCarriageShipmentStage",
    "onCarriageShipmentStage",
    "transportHandlingUnit",
    "firstArrivalPortLocation",
    "lastExitPortLocation",
    "officeOfEntryLocation",
    "officeOfSubSequentiallyEntryLocation",
    "officeOfExitLocation",
    "officeOfDepartureLocation",
    "officeOfDestinationLocation",
    "officeOfImportLocation",
    "officeOfExportLocation",
    "documentReference"
})
public class ConsignmentType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected IDType id;
    @XmlElement(name = "CarrierAssignedID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CarrierAssignedIDType carrierAssignedID;
    @XmlElement(name = "ConsigneeAssignedID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ConsigneeAssignedIDType consigneeAssignedID;
    @XmlElement(name = "ConsignorAssignedID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ConsignorAssignedIDType consignorAssignedID;
    @XmlElement(name = "FreightForwarderAssignedID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected FreightForwarderAssignedIDType freightForwarderAssignedID;
    @XmlElement(name = "BrokerAssignedID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected BrokerAssignedIDType brokerAssignedID;
    @XmlElement(name = "ContractedCarrierAssignedID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ContractedCarrierAssignedIDType contractedCarrierAssignedID;
    @XmlElement(name = "PerformingCarrierAssignedID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PerformingCarrierAssignedIDType performingCarrierAssignedID;
    @XmlElement(name = "SummaryDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<SummaryDescriptionType> summaryDescription;
    @XmlElement(name = "TotalInvoiceAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TotalInvoiceAmountType totalInvoiceAmount;
    @XmlElement(name = "DeclaredCustomsValueAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected DeclaredCustomsValueAmountType declaredCustomsValueAmount;
    @XmlElement(name = "TariffDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<TariffDescriptionType> tariffDescription;
    @XmlElement(name = "TariffCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TariffCodeType tariffCode;
    @XmlElement(name = "InsurancePremiumAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected InsurancePremiumAmountType insurancePremiumAmount;
    @XmlElement(name = "GrossWeightMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected GrossWeightMeasureType grossWeightMeasure;
    @XmlElement(name = "NetWeightMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NetWeightMeasureType netWeightMeasure;
    @XmlElement(name = "NetNetWeightMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NetNetWeightMeasureType netNetWeightMeasure;
    @XmlElement(name = "ChargeableWeightMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ChargeableWeightMeasureType chargeableWeightMeasure;
    @XmlElement(name = "GrossVolumeMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected GrossVolumeMeasureType grossVolumeMeasure;
    @XmlElement(name = "NetVolumeMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NetVolumeMeasureType netVolumeMeasure;
    @XmlElement(name = "LoadingLengthMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected LoadingLengthMeasureType loadingLengthMeasure;
    @XmlElement(name = "Remarks", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<RemarksType> remarks;
    @XmlElement(name = "HazardousRiskIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected HazardousRiskIndicatorType hazardousRiskIndicator;
    @XmlElement(name = "AnimalFoodIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected AnimalFoodIndicatorType animalFoodIndicator;
    @XmlElement(name = "HumanFoodIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected HumanFoodIndicatorType humanFoodIndicator;
    @XmlElement(name = "LivestockIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected LivestockIndicatorType livestockIndicator;
    @XmlElement(name = "BulkCargoIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected BulkCargoIndicatorType bulkCargoIndicator;
    @XmlElement(name = "ContainerizedIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ContainerizedIndicatorType containerizedIndicator;
    @XmlElement(name = "GeneralCargoIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected GeneralCargoIndicatorType generalCargoIndicator;
    @XmlElement(name = "SpecialSecurityIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SpecialSecurityIndicatorType specialSecurityIndicator;
    @XmlElement(name = "ThirdPartyPayerIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ThirdPartyPayerIndicatorType thirdPartyPayerIndicator;
    @XmlElement(name = "CarrierServiceInstructions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<CarrierServiceInstructionsType> carrierServiceInstructions;
    @XmlElement(name = "CustomsClearanceServiceInstructions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<CustomsClearanceServiceInstructionsType> customsClearanceServiceInstructions;
    @XmlElement(name = "ForwarderServiceInstructions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<ForwarderServiceInstructionsType> forwarderServiceInstructions;
    @XmlElement(name = "SpecialServiceInstructions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<SpecialServiceInstructionsType> specialServiceInstructions;
    @XmlElement(name = "SequenceID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SequenceIDType sequenceID;
    @XmlElement(name = "ShippingPriorityLevelCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ShippingPriorityLevelCodeType shippingPriorityLevelCode;
    @XmlElement(name = "HandlingCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected HandlingCodeType handlingCode;
    @XmlElement(name = "HandlingInstructions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<HandlingInstructionsType> handlingInstructions;
    @XmlElement(name = "Information", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<InformationType> information;
    @XmlElement(name = "TotalGoodsItemQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TotalGoodsItemQuantityType totalGoodsItemQuantity;
    @XmlElement(name = "TotalTransportHandlingUnitQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TotalTransportHandlingUnitQuantityType totalTransportHandlingUnitQuantity;
    @XmlElement(name = "InsuranceValueAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected InsuranceValueAmountType insuranceValueAmount;
    @XmlElement(name = "DeclaredForCarriageValueAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected DeclaredForCarriageValueAmountType declaredForCarriageValueAmount;
    @XmlElement(name = "DeclaredStatisticsValueAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected DeclaredStatisticsValueAmountType declaredStatisticsValueAmount;
    @XmlElement(name = "FreeOnBoardValueAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected FreeOnBoardValueAmountType freeOnBoardValueAmount;
    @XmlElement(name = "SpecialInstructions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<SpecialInstructionsType> specialInstructions;
    @XmlElement(name = "SplitConsignmentIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SplitConsignmentIndicatorType splitConsignmentIndicator;
    @XmlElement(name = "DeliveryInstructions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DeliveryInstructionsType> deliveryInstructions;
    @XmlElement(name = "ConsignmentQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ConsignmentQuantityType consignmentQuantity;
    @XmlElement(name = "ConsolidatableIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ConsolidatableIndicatorType consolidatableIndicator;
    @XmlElement(name = "HaulageInstructions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<HaulageInstructionsType> haulageInstructions;
    @XmlElement(name = "LoadingSequenceID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected LoadingSequenceIDType loadingSequenceID;
    @XmlElement(name = "ChildConsignmentQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ChildConsignmentQuantityType childConsignmentQuantity;
    @XmlElement(name = "TotalPackagesQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TotalPackagesQuantityType totalPackagesQuantity;
    @XmlElement(name = "ConsolidatedShipment")
    protected List<ShipmentType> consolidatedShipment;
    @XmlElement(name = "CustomsDeclaration")
    protected List<CustomsDeclarationType> customsDeclaration;
    @XmlElement(name = "RequestedPickupTransportEvent")
    protected TransportEventType requestedPickupTransportEvent;
    @XmlElement(name = "RequestedDeliveryTransportEvent")
    protected TransportEventType requestedDeliveryTransportEvent;
    @XmlElement(name = "PlannedPickupTransportEvent")
    protected TransportEventType plannedPickupTransportEvent;
    @XmlElement(name = "PlannedDeliveryTransportEvent")
    protected TransportEventType plannedDeliveryTransportEvent;
    @XmlElement(name = "ActualPickupTransportEvent")
    protected TransportEventType actualPickupTransportEvent;
    @XmlElement(name = "ActualDeliveryTransportEvent")
    protected TransportEventType actualDeliveryTransportEvent;
    @XmlElement(name = "Status")
    protected List<StatusType> status;
    @XmlElement(name = "ChildConsignment")
    protected List<ConsignmentType> childConsignment;
    @XmlElement(name = "ConsigneeParty")
    protected PartyType consigneeParty;
    @XmlElement(name = "ExporterParty")
    protected PartyType exporterParty;
    @XmlElement(name = "ConsignorParty")
    protected PartyType consignorParty;
    @XmlElement(name = "ImporterParty")
    protected PartyType importerParty;
    @XmlElement(name = "CarrierParty")
    protected PartyType carrierParty;
    @XmlElement(name = "FreightForwarderParty")
    protected PartyType freightForwarderParty;
    @XmlElement(name = "NotifyParty")
    protected PartyType notifyParty;
    @XmlElement(name = "OriginalDespatchParty")
    protected PartyType originalDespatchParty;
    @XmlElement(name = "FinalDeliveryParty")
    protected PartyType finalDeliveryParty;
    @XmlElement(name = "PerformingCarrierParty")
    protected PartyType performingCarrierParty;
    @XmlElement(name = "SubstituteCarrierParty")
    protected PartyType substituteCarrierParty;
    @XmlElement(name = "LogisticsOperatorParty")
    protected PartyType logisticsOperatorParty;
    @XmlElement(name = "TransportAdvisorParty")
    protected PartyType transportAdvisorParty;
    @XmlElement(name = "HazardousItemNotificationParty")
    protected PartyType hazardousItemNotificationParty;
    @XmlElement(name = "InsuranceParty")
    protected PartyType insuranceParty;
    @XmlElement(name = "MortgageHolderParty")
    protected PartyType mortgageHolderParty;
    @XmlElement(name = "BillOfLadingHolderParty")
    protected PartyType billOfLadingHolderParty;
    @XmlElement(name = "OriginalDepartureCountry")
    protected CountryType originalDepartureCountry;
    @XmlElement(name = "FinalDestinationCountry")
    protected CountryType finalDestinationCountry;
    @XmlElement(name = "TransitCountry")
    protected List<CountryType> transitCountry;
    @XmlElement(name = "TransportContract")
    protected ContractType transportContract;
    @XmlElement(name = "TransportEvent")
    protected List<TransportEventType> transportEvent;
    @XmlElement(name = "OriginalDespatchTransportationService")
    protected TransportationServiceType originalDespatchTransportationService;
    @XmlElement(name = "FinalDeliveryTransportationService")
    protected TransportationServiceType finalDeliveryTransportationService;
    @XmlElement(name = "DeliveryTerms")
    protected DeliveryTermsType deliveryTerms;
    @XmlElement(name = "PaymentTerms")
    protected PaymentTermsType paymentTerms;
    @XmlElement(name = "CollectPaymentTerms")
    protected PaymentTermsType collectPaymentTerms;
    @XmlElement(name = "DisbursementPaymentTerms")
    protected PaymentTermsType disbursementPaymentTerms;
    @XmlElement(name = "PrepaidPaymentTerms")
    protected PaymentTermsType prepaidPaymentTerms;
    @XmlElement(name = "FreightAllowanceCharge")
    protected List<AllowanceChargeType> freightAllowanceCharge;
    @XmlElement(name = "ExtraAllowanceCharge")
    protected List<AllowanceChargeType> extraAllowanceCharge;
    @XmlElement(name = "MainCarriageShipmentStage")
    protected List<ShipmentStageType> mainCarriageShipmentStage;
    @XmlElement(name = "PreCarriageShipmentStage")
    protected List<ShipmentStageType> preCarriageShipmentStage;
    @XmlElement(name = "OnCarriageShipmentStage")
    protected List<ShipmentStageType> onCarriageShipmentStage;
    @XmlElement(name = "TransportHandlingUnit")
    protected List<TransportHandlingUnitType> transportHandlingUnit;
    @XmlElement(name = "FirstArrivalPortLocation")
    protected LocationType firstArrivalPortLocation;
    @XmlElement(name = "LastExitPortLocation")
    protected LocationType lastExitPortLocation;
    @XmlElement(name = "OfficeOfEntryLocation")
    protected LocationType officeOfEntryLocation;
    @XmlElement(name = "OfficeOfSubSequentiallyEntryLocation")
    protected LocationType officeOfSubSequentiallyEntryLocation;
    @XmlElement(name = "OfficeOfExitLocation")
    protected LocationType officeOfExitLocation;
    @XmlElement(name = "OfficeOfDepartureLocation")
    protected LocationType officeOfDepartureLocation;
    @XmlElement(name = "OfficeOfDestinationLocation")
    protected LocationType officeOfDestinationLocation;
    @XmlElement(name = "OfficeOfImportLocation")
    protected LocationType officeOfImportLocation;
    @XmlElement(name = "OfficeOfExportLocation")
    protected LocationType officeOfExportLocation;
    @XmlElement(name = "DocumentReference")
    protected List<DocumentReferenceType> documentReference;

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
     * Recupera il valore della proprietà carrierAssignedID.
     * 
     * @return
     *     possible object is
     *     {@link CarrierAssignedIDType }
     *     
     */
    public CarrierAssignedIDType getCarrierAssignedID() {
        return carrierAssignedID;
    }

    /**
     * Imposta il valore della proprietà carrierAssignedID.
     * 
     * @param value
     *     allowed object is
     *     {@link CarrierAssignedIDType }
     *     
     */
    public void setCarrierAssignedID(CarrierAssignedIDType value) {
        this.carrierAssignedID = value;
    }

    /**
     * Recupera il valore della proprietà consigneeAssignedID.
     * 
     * @return
     *     possible object is
     *     {@link ConsigneeAssignedIDType }
     *     
     */
    public ConsigneeAssignedIDType getConsigneeAssignedID() {
        return consigneeAssignedID;
    }

    /**
     * Imposta il valore della proprietà consigneeAssignedID.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsigneeAssignedIDType }
     *     
     */
    public void setConsigneeAssignedID(ConsigneeAssignedIDType value) {
        this.consigneeAssignedID = value;
    }

    /**
     * Recupera il valore della proprietà consignorAssignedID.
     * 
     * @return
     *     possible object is
     *     {@link ConsignorAssignedIDType }
     *     
     */
    public ConsignorAssignedIDType getConsignorAssignedID() {
        return consignorAssignedID;
    }

    /**
     * Imposta il valore della proprietà consignorAssignedID.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsignorAssignedIDType }
     *     
     */
    public void setConsignorAssignedID(ConsignorAssignedIDType value) {
        this.consignorAssignedID = value;
    }

    /**
     * Recupera il valore della proprietà freightForwarderAssignedID.
     * 
     * @return
     *     possible object is
     *     {@link FreightForwarderAssignedIDType }
     *     
     */
    public FreightForwarderAssignedIDType getFreightForwarderAssignedID() {
        return freightForwarderAssignedID;
    }

    /**
     * Imposta il valore della proprietà freightForwarderAssignedID.
     * 
     * @param value
     *     allowed object is
     *     {@link FreightForwarderAssignedIDType }
     *     
     */
    public void setFreightForwarderAssignedID(FreightForwarderAssignedIDType value) {
        this.freightForwarderAssignedID = value;
    }

    /**
     * Recupera il valore della proprietà brokerAssignedID.
     * 
     * @return
     *     possible object is
     *     {@link BrokerAssignedIDType }
     *     
     */
    public BrokerAssignedIDType getBrokerAssignedID() {
        return brokerAssignedID;
    }

    /**
     * Imposta il valore della proprietà brokerAssignedID.
     * 
     * @param value
     *     allowed object is
     *     {@link BrokerAssignedIDType }
     *     
     */
    public void setBrokerAssignedID(BrokerAssignedIDType value) {
        this.brokerAssignedID = value;
    }

    /**
     * Recupera il valore della proprietà contractedCarrierAssignedID.
     * 
     * @return
     *     possible object is
     *     {@link ContractedCarrierAssignedIDType }
     *     
     */
    public ContractedCarrierAssignedIDType getContractedCarrierAssignedID() {
        return contractedCarrierAssignedID;
    }

    /**
     * Imposta il valore della proprietà contractedCarrierAssignedID.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractedCarrierAssignedIDType }
     *     
     */
    public void setContractedCarrierAssignedID(ContractedCarrierAssignedIDType value) {
        this.contractedCarrierAssignedID = value;
    }

    /**
     * Recupera il valore della proprietà performingCarrierAssignedID.
     * 
     * @return
     *     possible object is
     *     {@link PerformingCarrierAssignedIDType }
     *     
     */
    public PerformingCarrierAssignedIDType getPerformingCarrierAssignedID() {
        return performingCarrierAssignedID;
    }

    /**
     * Imposta il valore della proprietà performingCarrierAssignedID.
     * 
     * @param value
     *     allowed object is
     *     {@link PerformingCarrierAssignedIDType }
     *     
     */
    public void setPerformingCarrierAssignedID(PerformingCarrierAssignedIDType value) {
        this.performingCarrierAssignedID = value;
    }

    /**
     * Gets the value of the summaryDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the summaryDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSummaryDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link SummaryDescriptionType }
     * 
     * 
     */
    public List<SummaryDescriptionType> getSummaryDescription() {
        if (summaryDescription == null) {
            summaryDescription = new ArrayList<SummaryDescriptionType>();
        }
        return this.summaryDescription;
    }

    /**
     * Recupera il valore della proprietà totalInvoiceAmount.
     * 
     * @return
     *     possible object is
     *     {@link TotalInvoiceAmountType }
     *     
     */
    public TotalInvoiceAmountType getTotalInvoiceAmount() {
        return totalInvoiceAmount;
    }

    /**
     * Imposta il valore della proprietà totalInvoiceAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalInvoiceAmountType }
     *     
     */
    public void setTotalInvoiceAmount(TotalInvoiceAmountType value) {
        this.totalInvoiceAmount = value;
    }

    /**
     * Recupera il valore della proprietà declaredCustomsValueAmount.
     * 
     * @return
     *     possible object is
     *     {@link DeclaredCustomsValueAmountType }
     *     
     */
    public DeclaredCustomsValueAmountType getDeclaredCustomsValueAmount() {
        return declaredCustomsValueAmount;
    }

    /**
     * Imposta il valore della proprietà declaredCustomsValueAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link DeclaredCustomsValueAmountType }
     *     
     */
    public void setDeclaredCustomsValueAmount(DeclaredCustomsValueAmountType value) {
        this.declaredCustomsValueAmount = value;
    }

    /**
     * Gets the value of the tariffDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the tariffDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTariffDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TariffDescriptionType }
     * 
     * 
     */
    public List<TariffDescriptionType> getTariffDescription() {
        if (tariffDescription == null) {
            tariffDescription = new ArrayList<TariffDescriptionType>();
        }
        return this.tariffDescription;
    }

    /**
     * Recupera il valore della proprietà tariffCode.
     * 
     * @return
     *     possible object is
     *     {@link TariffCodeType }
     *     
     */
    public TariffCodeType getTariffCode() {
        return tariffCode;
    }

    /**
     * Imposta il valore della proprietà tariffCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TariffCodeType }
     *     
     */
    public void setTariffCode(TariffCodeType value) {
        this.tariffCode = value;
    }

    /**
     * Recupera il valore della proprietà insurancePremiumAmount.
     * 
     * @return
     *     possible object is
     *     {@link InsurancePremiumAmountType }
     *     
     */
    public InsurancePremiumAmountType getInsurancePremiumAmount() {
        return insurancePremiumAmount;
    }

    /**
     * Imposta il valore della proprietà insurancePremiumAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link InsurancePremiumAmountType }
     *     
     */
    public void setInsurancePremiumAmount(InsurancePremiumAmountType value) {
        this.insurancePremiumAmount = value;
    }

    /**
     * Recupera il valore della proprietà grossWeightMeasure.
     * 
     * @return
     *     possible object is
     *     {@link GrossWeightMeasureType }
     *     
     */
    public GrossWeightMeasureType getGrossWeightMeasure() {
        return grossWeightMeasure;
    }

    /**
     * Imposta il valore della proprietà grossWeightMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link GrossWeightMeasureType }
     *     
     */
    public void setGrossWeightMeasure(GrossWeightMeasureType value) {
        this.grossWeightMeasure = value;
    }

    /**
     * Recupera il valore della proprietà netWeightMeasure.
     * 
     * @return
     *     possible object is
     *     {@link NetWeightMeasureType }
     *     
     */
    public NetWeightMeasureType getNetWeightMeasure() {
        return netWeightMeasure;
    }

    /**
     * Imposta il valore della proprietà netWeightMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link NetWeightMeasureType }
     *     
     */
    public void setNetWeightMeasure(NetWeightMeasureType value) {
        this.netWeightMeasure = value;
    }

    /**
     * Recupera il valore della proprietà netNetWeightMeasure.
     * 
     * @return
     *     possible object is
     *     {@link NetNetWeightMeasureType }
     *     
     */
    public NetNetWeightMeasureType getNetNetWeightMeasure() {
        return netNetWeightMeasure;
    }

    /**
     * Imposta il valore della proprietà netNetWeightMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link NetNetWeightMeasureType }
     *     
     */
    public void setNetNetWeightMeasure(NetNetWeightMeasureType value) {
        this.netNetWeightMeasure = value;
    }

    /**
     * Recupera il valore della proprietà chargeableWeightMeasure.
     * 
     * @return
     *     possible object is
     *     {@link ChargeableWeightMeasureType }
     *     
     */
    public ChargeableWeightMeasureType getChargeableWeightMeasure() {
        return chargeableWeightMeasure;
    }

    /**
     * Imposta il valore della proprietà chargeableWeightMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link ChargeableWeightMeasureType }
     *     
     */
    public void setChargeableWeightMeasure(ChargeableWeightMeasureType value) {
        this.chargeableWeightMeasure = value;
    }

    /**
     * Recupera il valore della proprietà grossVolumeMeasure.
     * 
     * @return
     *     possible object is
     *     {@link GrossVolumeMeasureType }
     *     
     */
    public GrossVolumeMeasureType getGrossVolumeMeasure() {
        return grossVolumeMeasure;
    }

    /**
     * Imposta il valore della proprietà grossVolumeMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link GrossVolumeMeasureType }
     *     
     */
    public void setGrossVolumeMeasure(GrossVolumeMeasureType value) {
        this.grossVolumeMeasure = value;
    }

    /**
     * Recupera il valore della proprietà netVolumeMeasure.
     * 
     * @return
     *     possible object is
     *     {@link NetVolumeMeasureType }
     *     
     */
    public NetVolumeMeasureType getNetVolumeMeasure() {
        return netVolumeMeasure;
    }

    /**
     * Imposta il valore della proprietà netVolumeMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link NetVolumeMeasureType }
     *     
     */
    public void setNetVolumeMeasure(NetVolumeMeasureType value) {
        this.netVolumeMeasure = value;
    }

    /**
     * Recupera il valore della proprietà loadingLengthMeasure.
     * 
     * @return
     *     possible object is
     *     {@link LoadingLengthMeasureType }
     *     
     */
    public LoadingLengthMeasureType getLoadingLengthMeasure() {
        return loadingLengthMeasure;
    }

    /**
     * Imposta il valore della proprietà loadingLengthMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link LoadingLengthMeasureType }
     *     
     */
    public void setLoadingLengthMeasure(LoadingLengthMeasureType value) {
        this.loadingLengthMeasure = value;
    }

    /**
     * Gets the value of the remarks property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the remarks property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getRemarks().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link RemarksType }
     * 
     * 
     */
    public List<RemarksType> getRemarks() {
        if (remarks == null) {
            remarks = new ArrayList<RemarksType>();
        }
        return this.remarks;
    }

    /**
     * Recupera il valore della proprietà hazardousRiskIndicator.
     * 
     * @return
     *     possible object is
     *     {@link HazardousRiskIndicatorType }
     *     
     */
    public HazardousRiskIndicatorType getHazardousRiskIndicator() {
        return hazardousRiskIndicator;
    }

    /**
     * Imposta il valore della proprietà hazardousRiskIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link HazardousRiskIndicatorType }
     *     
     */
    public void setHazardousRiskIndicator(HazardousRiskIndicatorType value) {
        this.hazardousRiskIndicator = value;
    }

    /**
     * Recupera il valore della proprietà animalFoodIndicator.
     * 
     * @return
     *     possible object is
     *     {@link AnimalFoodIndicatorType }
     *     
     */
    public AnimalFoodIndicatorType getAnimalFoodIndicator() {
        return animalFoodIndicator;
    }

    /**
     * Imposta il valore della proprietà animalFoodIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link AnimalFoodIndicatorType }
     *     
     */
    public void setAnimalFoodIndicator(AnimalFoodIndicatorType value) {
        this.animalFoodIndicator = value;
    }

    /**
     * Recupera il valore della proprietà humanFoodIndicator.
     * 
     * @return
     *     possible object is
     *     {@link HumanFoodIndicatorType }
     *     
     */
    public HumanFoodIndicatorType getHumanFoodIndicator() {
        return humanFoodIndicator;
    }

    /**
     * Imposta il valore della proprietà humanFoodIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link HumanFoodIndicatorType }
     *     
     */
    public void setHumanFoodIndicator(HumanFoodIndicatorType value) {
        this.humanFoodIndicator = value;
    }

    /**
     * Recupera il valore della proprietà livestockIndicator.
     * 
     * @return
     *     possible object is
     *     {@link LivestockIndicatorType }
     *     
     */
    public LivestockIndicatorType getLivestockIndicator() {
        return livestockIndicator;
    }

    /**
     * Imposta il valore della proprietà livestockIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link LivestockIndicatorType }
     *     
     */
    public void setLivestockIndicator(LivestockIndicatorType value) {
        this.livestockIndicator = value;
    }

    /**
     * Recupera il valore della proprietà bulkCargoIndicator.
     * 
     * @return
     *     possible object is
     *     {@link BulkCargoIndicatorType }
     *     
     */
    public BulkCargoIndicatorType getBulkCargoIndicator() {
        return bulkCargoIndicator;
    }

    /**
     * Imposta il valore della proprietà bulkCargoIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link BulkCargoIndicatorType }
     *     
     */
    public void setBulkCargoIndicator(BulkCargoIndicatorType value) {
        this.bulkCargoIndicator = value;
    }

    /**
     * Recupera il valore della proprietà containerizedIndicator.
     * 
     * @return
     *     possible object is
     *     {@link ContainerizedIndicatorType }
     *     
     */
    public ContainerizedIndicatorType getContainerizedIndicator() {
        return containerizedIndicator;
    }

    /**
     * Imposta il valore della proprietà containerizedIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link ContainerizedIndicatorType }
     *     
     */
    public void setContainerizedIndicator(ContainerizedIndicatorType value) {
        this.containerizedIndicator = value;
    }

    /**
     * Recupera il valore della proprietà generalCargoIndicator.
     * 
     * @return
     *     possible object is
     *     {@link GeneralCargoIndicatorType }
     *     
     */
    public GeneralCargoIndicatorType getGeneralCargoIndicator() {
        return generalCargoIndicator;
    }

    /**
     * Imposta il valore della proprietà generalCargoIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link GeneralCargoIndicatorType }
     *     
     */
    public void setGeneralCargoIndicator(GeneralCargoIndicatorType value) {
        this.generalCargoIndicator = value;
    }

    /**
     * Recupera il valore della proprietà specialSecurityIndicator.
     * 
     * @return
     *     possible object is
     *     {@link SpecialSecurityIndicatorType }
     *     
     */
    public SpecialSecurityIndicatorType getSpecialSecurityIndicator() {
        return specialSecurityIndicator;
    }

    /**
     * Imposta il valore della proprietà specialSecurityIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link SpecialSecurityIndicatorType }
     *     
     */
    public void setSpecialSecurityIndicator(SpecialSecurityIndicatorType value) {
        this.specialSecurityIndicator = value;
    }

    /**
     * Recupera il valore della proprietà thirdPartyPayerIndicator.
     * 
     * @return
     *     possible object is
     *     {@link ThirdPartyPayerIndicatorType }
     *     
     */
    public ThirdPartyPayerIndicatorType getThirdPartyPayerIndicator() {
        return thirdPartyPayerIndicator;
    }

    /**
     * Imposta il valore della proprietà thirdPartyPayerIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link ThirdPartyPayerIndicatorType }
     *     
     */
    public void setThirdPartyPayerIndicator(ThirdPartyPayerIndicatorType value) {
        this.thirdPartyPayerIndicator = value;
    }

    /**
     * Gets the value of the carrierServiceInstructions property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the carrierServiceInstructions property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCarrierServiceInstructions().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CarrierServiceInstructionsType }
     * 
     * 
     */
    public List<CarrierServiceInstructionsType> getCarrierServiceInstructions() {
        if (carrierServiceInstructions == null) {
            carrierServiceInstructions = new ArrayList<CarrierServiceInstructionsType>();
        }
        return this.carrierServiceInstructions;
    }

    /**
     * Gets the value of the customsClearanceServiceInstructions property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the customsClearanceServiceInstructions property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCustomsClearanceServiceInstructions().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CustomsClearanceServiceInstructionsType }
     * 
     * 
     */
    public List<CustomsClearanceServiceInstructionsType> getCustomsClearanceServiceInstructions() {
        if (customsClearanceServiceInstructions == null) {
            customsClearanceServiceInstructions = new ArrayList<CustomsClearanceServiceInstructionsType>();
        }
        return this.customsClearanceServiceInstructions;
    }

    /**
     * Gets the value of the forwarderServiceInstructions property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the forwarderServiceInstructions property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getForwarderServiceInstructions().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ForwarderServiceInstructionsType }
     * 
     * 
     */
    public List<ForwarderServiceInstructionsType> getForwarderServiceInstructions() {
        if (forwarderServiceInstructions == null) {
            forwarderServiceInstructions = new ArrayList<ForwarderServiceInstructionsType>();
        }
        return this.forwarderServiceInstructions;
    }

    /**
     * Gets the value of the specialServiceInstructions property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the specialServiceInstructions property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSpecialServiceInstructions().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link SpecialServiceInstructionsType }
     * 
     * 
     */
    public List<SpecialServiceInstructionsType> getSpecialServiceInstructions() {
        if (specialServiceInstructions == null) {
            specialServiceInstructions = new ArrayList<SpecialServiceInstructionsType>();
        }
        return this.specialServiceInstructions;
    }

    /**
     * Recupera il valore della proprietà sequenceID.
     * 
     * @return
     *     possible object is
     *     {@link SequenceIDType }
     *     
     */
    public SequenceIDType getSequenceID() {
        return sequenceID;
    }

    /**
     * Imposta il valore della proprietà sequenceID.
     * 
     * @param value
     *     allowed object is
     *     {@link SequenceIDType }
     *     
     */
    public void setSequenceID(SequenceIDType value) {
        this.sequenceID = value;
    }

    /**
     * Recupera il valore della proprietà shippingPriorityLevelCode.
     * 
     * @return
     *     possible object is
     *     {@link ShippingPriorityLevelCodeType }
     *     
     */
    public ShippingPriorityLevelCodeType getShippingPriorityLevelCode() {
        return shippingPriorityLevelCode;
    }

    /**
     * Imposta il valore della proprietà shippingPriorityLevelCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ShippingPriorityLevelCodeType }
     *     
     */
    public void setShippingPriorityLevelCode(ShippingPriorityLevelCodeType value) {
        this.shippingPriorityLevelCode = value;
    }

    /**
     * Recupera il valore della proprietà handlingCode.
     * 
     * @return
     *     possible object is
     *     {@link HandlingCodeType }
     *     
     */
    public HandlingCodeType getHandlingCode() {
        return handlingCode;
    }

    /**
     * Imposta il valore della proprietà handlingCode.
     * 
     * @param value
     *     allowed object is
     *     {@link HandlingCodeType }
     *     
     */
    public void setHandlingCode(HandlingCodeType value) {
        this.handlingCode = value;
    }

    /**
     * Gets the value of the handlingInstructions property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the handlingInstructions property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getHandlingInstructions().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link HandlingInstructionsType }
     * 
     * 
     */
    public List<HandlingInstructionsType> getHandlingInstructions() {
        if (handlingInstructions == null) {
            handlingInstructions = new ArrayList<HandlingInstructionsType>();
        }
        return this.handlingInstructions;
    }

    /**
     * Gets the value of the information property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the information property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getInformation().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link InformationType }
     * 
     * 
     */
    public List<InformationType> getInformation() {
        if (information == null) {
            information = new ArrayList<InformationType>();
        }
        return this.information;
    }

    /**
     * Recupera il valore della proprietà totalGoodsItemQuantity.
     * 
     * @return
     *     possible object is
     *     {@link TotalGoodsItemQuantityType }
     *     
     */
    public TotalGoodsItemQuantityType getTotalGoodsItemQuantity() {
        return totalGoodsItemQuantity;
    }

    /**
     * Imposta il valore della proprietà totalGoodsItemQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalGoodsItemQuantityType }
     *     
     */
    public void setTotalGoodsItemQuantity(TotalGoodsItemQuantityType value) {
        this.totalGoodsItemQuantity = value;
    }

    /**
     * Recupera il valore della proprietà totalTransportHandlingUnitQuantity.
     * 
     * @return
     *     possible object is
     *     {@link TotalTransportHandlingUnitQuantityType }
     *     
     */
    public TotalTransportHandlingUnitQuantityType getTotalTransportHandlingUnitQuantity() {
        return totalTransportHandlingUnitQuantity;
    }

    /**
     * Imposta il valore della proprietà totalTransportHandlingUnitQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalTransportHandlingUnitQuantityType }
     *     
     */
    public void setTotalTransportHandlingUnitQuantity(TotalTransportHandlingUnitQuantityType value) {
        this.totalTransportHandlingUnitQuantity = value;
    }

    /**
     * Recupera il valore della proprietà insuranceValueAmount.
     * 
     * @return
     *     possible object is
     *     {@link InsuranceValueAmountType }
     *     
     */
    public InsuranceValueAmountType getInsuranceValueAmount() {
        return insuranceValueAmount;
    }

    /**
     * Imposta il valore della proprietà insuranceValueAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link InsuranceValueAmountType }
     *     
     */
    public void setInsuranceValueAmount(InsuranceValueAmountType value) {
        this.insuranceValueAmount = value;
    }

    /**
     * Recupera il valore della proprietà declaredForCarriageValueAmount.
     * 
     * @return
     *     possible object is
     *     {@link DeclaredForCarriageValueAmountType }
     *     
     */
    public DeclaredForCarriageValueAmountType getDeclaredForCarriageValueAmount() {
        return declaredForCarriageValueAmount;
    }

    /**
     * Imposta il valore della proprietà declaredForCarriageValueAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link DeclaredForCarriageValueAmountType }
     *     
     */
    public void setDeclaredForCarriageValueAmount(DeclaredForCarriageValueAmountType value) {
        this.declaredForCarriageValueAmount = value;
    }

    /**
     * Recupera il valore della proprietà declaredStatisticsValueAmount.
     * 
     * @return
     *     possible object is
     *     {@link DeclaredStatisticsValueAmountType }
     *     
     */
    public DeclaredStatisticsValueAmountType getDeclaredStatisticsValueAmount() {
        return declaredStatisticsValueAmount;
    }

    /**
     * Imposta il valore della proprietà declaredStatisticsValueAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link DeclaredStatisticsValueAmountType }
     *     
     */
    public void setDeclaredStatisticsValueAmount(DeclaredStatisticsValueAmountType value) {
        this.declaredStatisticsValueAmount = value;
    }

    /**
     * Recupera il valore della proprietà freeOnBoardValueAmount.
     * 
     * @return
     *     possible object is
     *     {@link FreeOnBoardValueAmountType }
     *     
     */
    public FreeOnBoardValueAmountType getFreeOnBoardValueAmount() {
        return freeOnBoardValueAmount;
    }

    /**
     * Imposta il valore della proprietà freeOnBoardValueAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link FreeOnBoardValueAmountType }
     *     
     */
    public void setFreeOnBoardValueAmount(FreeOnBoardValueAmountType value) {
        this.freeOnBoardValueAmount = value;
    }

    /**
     * Gets the value of the specialInstructions property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the specialInstructions property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSpecialInstructions().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link SpecialInstructionsType }
     * 
     * 
     */
    public List<SpecialInstructionsType> getSpecialInstructions() {
        if (specialInstructions == null) {
            specialInstructions = new ArrayList<SpecialInstructionsType>();
        }
        return this.specialInstructions;
    }

    /**
     * Recupera il valore della proprietà splitConsignmentIndicator.
     * 
     * @return
     *     possible object is
     *     {@link SplitConsignmentIndicatorType }
     *     
     */
    public SplitConsignmentIndicatorType getSplitConsignmentIndicator() {
        return splitConsignmentIndicator;
    }

    /**
     * Imposta il valore della proprietà splitConsignmentIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link SplitConsignmentIndicatorType }
     *     
     */
    public void setSplitConsignmentIndicator(SplitConsignmentIndicatorType value) {
        this.splitConsignmentIndicator = value;
    }

    /**
     * Gets the value of the deliveryInstructions property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the deliveryInstructions property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDeliveryInstructions().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DeliveryInstructionsType }
     * 
     * 
     */
    public List<DeliveryInstructionsType> getDeliveryInstructions() {
        if (deliveryInstructions == null) {
            deliveryInstructions = new ArrayList<DeliveryInstructionsType>();
        }
        return this.deliveryInstructions;
    }

    /**
     * Recupera il valore della proprietà consignmentQuantity.
     * 
     * @return
     *     possible object is
     *     {@link ConsignmentQuantityType }
     *     
     */
    public ConsignmentQuantityType getConsignmentQuantity() {
        return consignmentQuantity;
    }

    /**
     * Imposta il valore della proprietà consignmentQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsignmentQuantityType }
     *     
     */
    public void setConsignmentQuantity(ConsignmentQuantityType value) {
        this.consignmentQuantity = value;
    }

    /**
     * Recupera il valore della proprietà consolidatableIndicator.
     * 
     * @return
     *     possible object is
     *     {@link ConsolidatableIndicatorType }
     *     
     */
    public ConsolidatableIndicatorType getConsolidatableIndicator() {
        return consolidatableIndicator;
    }

    /**
     * Imposta il valore della proprietà consolidatableIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsolidatableIndicatorType }
     *     
     */
    public void setConsolidatableIndicator(ConsolidatableIndicatorType value) {
        this.consolidatableIndicator = value;
    }

    /**
     * Gets the value of the haulageInstructions property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the haulageInstructions property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getHaulageInstructions().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link HaulageInstructionsType }
     * 
     * 
     */
    public List<HaulageInstructionsType> getHaulageInstructions() {
        if (haulageInstructions == null) {
            haulageInstructions = new ArrayList<HaulageInstructionsType>();
        }
        return this.haulageInstructions;
    }

    /**
     * Recupera il valore della proprietà loadingSequenceID.
     * 
     * @return
     *     possible object is
     *     {@link LoadingSequenceIDType }
     *     
     */
    public LoadingSequenceIDType getLoadingSequenceID() {
        return loadingSequenceID;
    }

    /**
     * Imposta il valore della proprietà loadingSequenceID.
     * 
     * @param value
     *     allowed object is
     *     {@link LoadingSequenceIDType }
     *     
     */
    public void setLoadingSequenceID(LoadingSequenceIDType value) {
        this.loadingSequenceID = value;
    }

    /**
     * Recupera il valore della proprietà childConsignmentQuantity.
     * 
     * @return
     *     possible object is
     *     {@link ChildConsignmentQuantityType }
     *     
     */
    public ChildConsignmentQuantityType getChildConsignmentQuantity() {
        return childConsignmentQuantity;
    }

    /**
     * Imposta il valore della proprietà childConsignmentQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link ChildConsignmentQuantityType }
     *     
     */
    public void setChildConsignmentQuantity(ChildConsignmentQuantityType value) {
        this.childConsignmentQuantity = value;
    }

    /**
     * Recupera il valore della proprietà totalPackagesQuantity.
     * 
     * @return
     *     possible object is
     *     {@link TotalPackagesQuantityType }
     *     
     */
    public TotalPackagesQuantityType getTotalPackagesQuantity() {
        return totalPackagesQuantity;
    }

    /**
     * Imposta il valore della proprietà totalPackagesQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalPackagesQuantityType }
     *     
     */
    public void setTotalPackagesQuantity(TotalPackagesQuantityType value) {
        this.totalPackagesQuantity = value;
    }

    /**
     * Gets the value of the consolidatedShipment property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the consolidatedShipment property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getConsolidatedShipment().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ShipmentType }
     * 
     * 
     */
    public List<ShipmentType> getConsolidatedShipment() {
        if (consolidatedShipment == null) {
            consolidatedShipment = new ArrayList<ShipmentType>();
        }
        return this.consolidatedShipment;
    }

    /**
     * Gets the value of the customsDeclaration property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the customsDeclaration property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCustomsDeclaration().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CustomsDeclarationType }
     * 
     * 
     */
    public List<CustomsDeclarationType> getCustomsDeclaration() {
        if (customsDeclaration == null) {
            customsDeclaration = new ArrayList<CustomsDeclarationType>();
        }
        return this.customsDeclaration;
    }

    /**
     * Recupera il valore della proprietà requestedPickupTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getRequestedPickupTransportEvent() {
        return requestedPickupTransportEvent;
    }

    /**
     * Imposta il valore della proprietà requestedPickupTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setRequestedPickupTransportEvent(TransportEventType value) {
        this.requestedPickupTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà requestedDeliveryTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getRequestedDeliveryTransportEvent() {
        return requestedDeliveryTransportEvent;
    }

    /**
     * Imposta il valore della proprietà requestedDeliveryTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setRequestedDeliveryTransportEvent(TransportEventType value) {
        this.requestedDeliveryTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà plannedPickupTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getPlannedPickupTransportEvent() {
        return plannedPickupTransportEvent;
    }

    /**
     * Imposta il valore della proprietà plannedPickupTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setPlannedPickupTransportEvent(TransportEventType value) {
        this.plannedPickupTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà plannedDeliveryTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getPlannedDeliveryTransportEvent() {
        return plannedDeliveryTransportEvent;
    }

    /**
     * Imposta il valore della proprietà plannedDeliveryTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setPlannedDeliveryTransportEvent(TransportEventType value) {
        this.plannedDeliveryTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà actualPickupTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getActualPickupTransportEvent() {
        return actualPickupTransportEvent;
    }

    /**
     * Imposta il valore della proprietà actualPickupTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setActualPickupTransportEvent(TransportEventType value) {
        this.actualPickupTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà actualDeliveryTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getActualDeliveryTransportEvent() {
        return actualDeliveryTransportEvent;
    }

    /**
     * Imposta il valore della proprietà actualDeliveryTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setActualDeliveryTransportEvent(TransportEventType value) {
        this.actualDeliveryTransportEvent = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the status property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getStatus().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link StatusType }
     * 
     * 
     */
    public List<StatusType> getStatus() {
        if (status == null) {
            status = new ArrayList<StatusType>();
        }
        return this.status;
    }

    /**
     * Gets the value of the childConsignment property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the childConsignment property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getChildConsignment().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ConsignmentType }
     * 
     * 
     */
    public List<ConsignmentType> getChildConsignment() {
        if (childConsignment == null) {
            childConsignment = new ArrayList<ConsignmentType>();
        }
        return this.childConsignment;
    }

    /**
     * Recupera il valore della proprietà consigneeParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getConsigneeParty() {
        return consigneeParty;
    }

    /**
     * Imposta il valore della proprietà consigneeParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setConsigneeParty(PartyType value) {
        this.consigneeParty = value;
    }

    /**
     * Recupera il valore della proprietà exporterParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getExporterParty() {
        return exporterParty;
    }

    /**
     * Imposta il valore della proprietà exporterParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setExporterParty(PartyType value) {
        this.exporterParty = value;
    }

    /**
     * Recupera il valore della proprietà consignorParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getConsignorParty() {
        return consignorParty;
    }

    /**
     * Imposta il valore della proprietà consignorParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setConsignorParty(PartyType value) {
        this.consignorParty = value;
    }

    /**
     * Recupera il valore della proprietà importerParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getImporterParty() {
        return importerParty;
    }

    /**
     * Imposta il valore della proprietà importerParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setImporterParty(PartyType value) {
        this.importerParty = value;
    }

    /**
     * Recupera il valore della proprietà carrierParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getCarrierParty() {
        return carrierParty;
    }

    /**
     * Imposta il valore della proprietà carrierParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setCarrierParty(PartyType value) {
        this.carrierParty = value;
    }

    /**
     * Recupera il valore della proprietà freightForwarderParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getFreightForwarderParty() {
        return freightForwarderParty;
    }

    /**
     * Imposta il valore della proprietà freightForwarderParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setFreightForwarderParty(PartyType value) {
        this.freightForwarderParty = value;
    }

    /**
     * Recupera il valore della proprietà notifyParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getNotifyParty() {
        return notifyParty;
    }

    /**
     * Imposta il valore della proprietà notifyParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setNotifyParty(PartyType value) {
        this.notifyParty = value;
    }

    /**
     * Recupera il valore della proprietà originalDespatchParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getOriginalDespatchParty() {
        return originalDespatchParty;
    }

    /**
     * Imposta il valore della proprietà originalDespatchParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setOriginalDespatchParty(PartyType value) {
        this.originalDespatchParty = value;
    }

    /**
     * Recupera il valore della proprietà finalDeliveryParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getFinalDeliveryParty() {
        return finalDeliveryParty;
    }

    /**
     * Imposta il valore della proprietà finalDeliveryParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setFinalDeliveryParty(PartyType value) {
        this.finalDeliveryParty = value;
    }

    /**
     * Recupera il valore della proprietà performingCarrierParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getPerformingCarrierParty() {
        return performingCarrierParty;
    }

    /**
     * Imposta il valore della proprietà performingCarrierParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setPerformingCarrierParty(PartyType value) {
        this.performingCarrierParty = value;
    }

    /**
     * Recupera il valore della proprietà substituteCarrierParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getSubstituteCarrierParty() {
        return substituteCarrierParty;
    }

    /**
     * Imposta il valore della proprietà substituteCarrierParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setSubstituteCarrierParty(PartyType value) {
        this.substituteCarrierParty = value;
    }

    /**
     * Recupera il valore della proprietà logisticsOperatorParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getLogisticsOperatorParty() {
        return logisticsOperatorParty;
    }

    /**
     * Imposta il valore della proprietà logisticsOperatorParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setLogisticsOperatorParty(PartyType value) {
        this.logisticsOperatorParty = value;
    }

    /**
     * Recupera il valore della proprietà transportAdvisorParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getTransportAdvisorParty() {
        return transportAdvisorParty;
    }

    /**
     * Imposta il valore della proprietà transportAdvisorParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setTransportAdvisorParty(PartyType value) {
        this.transportAdvisorParty = value;
    }

    /**
     * Recupera il valore della proprietà hazardousItemNotificationParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getHazardousItemNotificationParty() {
        return hazardousItemNotificationParty;
    }

    /**
     * Imposta il valore della proprietà hazardousItemNotificationParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setHazardousItemNotificationParty(PartyType value) {
        this.hazardousItemNotificationParty = value;
    }

    /**
     * Recupera il valore della proprietà insuranceParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getInsuranceParty() {
        return insuranceParty;
    }

    /**
     * Imposta il valore della proprietà insuranceParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setInsuranceParty(PartyType value) {
        this.insuranceParty = value;
    }

    /**
     * Recupera il valore della proprietà mortgageHolderParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getMortgageHolderParty() {
        return mortgageHolderParty;
    }

    /**
     * Imposta il valore della proprietà mortgageHolderParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setMortgageHolderParty(PartyType value) {
        this.mortgageHolderParty = value;
    }

    /**
     * Recupera il valore della proprietà billOfLadingHolderParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getBillOfLadingHolderParty() {
        return billOfLadingHolderParty;
    }

    /**
     * Imposta il valore della proprietà billOfLadingHolderParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setBillOfLadingHolderParty(PartyType value) {
        this.billOfLadingHolderParty = value;
    }

    /**
     * Recupera il valore della proprietà originalDepartureCountry.
     * 
     * @return
     *     possible object is
     *     {@link CountryType }
     *     
     */
    public CountryType getOriginalDepartureCountry() {
        return originalDepartureCountry;
    }

    /**
     * Imposta il valore della proprietà originalDepartureCountry.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryType }
     *     
     */
    public void setOriginalDepartureCountry(CountryType value) {
        this.originalDepartureCountry = value;
    }

    /**
     * Recupera il valore della proprietà finalDestinationCountry.
     * 
     * @return
     *     possible object is
     *     {@link CountryType }
     *     
     */
    public CountryType getFinalDestinationCountry() {
        return finalDestinationCountry;
    }

    /**
     * Imposta il valore della proprietà finalDestinationCountry.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryType }
     *     
     */
    public void setFinalDestinationCountry(CountryType value) {
        this.finalDestinationCountry = value;
    }

    /**
     * Gets the value of the transitCountry property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the transitCountry property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTransitCountry().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CountryType }
     * 
     * 
     */
    public List<CountryType> getTransitCountry() {
        if (transitCountry == null) {
            transitCountry = new ArrayList<CountryType>();
        }
        return this.transitCountry;
    }

    /**
     * Recupera il valore della proprietà transportContract.
     * 
     * @return
     *     possible object is
     *     {@link ContractType }
     *     
     */
    public ContractType getTransportContract() {
        return transportContract;
    }

    /**
     * Imposta il valore della proprietà transportContract.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractType }
     *     
     */
    public void setTransportContract(ContractType value) {
        this.transportContract = value;
    }

    /**
     * Gets the value of the transportEvent property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the transportEvent property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTransportEvent().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TransportEventType }
     * 
     * 
     */
    public List<TransportEventType> getTransportEvent() {
        if (transportEvent == null) {
            transportEvent = new ArrayList<TransportEventType>();
        }
        return this.transportEvent;
    }

    /**
     * Recupera il valore della proprietà originalDespatchTransportationService.
     * 
     * @return
     *     possible object is
     *     {@link TransportationServiceType }
     *     
     */
    public TransportationServiceType getOriginalDespatchTransportationService() {
        return originalDespatchTransportationService;
    }

    /**
     * Imposta il valore della proprietà originalDespatchTransportationService.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportationServiceType }
     *     
     */
    public void setOriginalDespatchTransportationService(TransportationServiceType value) {
        this.originalDespatchTransportationService = value;
    }

    /**
     * Recupera il valore della proprietà finalDeliveryTransportationService.
     * 
     * @return
     *     possible object is
     *     {@link TransportationServiceType }
     *     
     */
    public TransportationServiceType getFinalDeliveryTransportationService() {
        return finalDeliveryTransportationService;
    }

    /**
     * Imposta il valore della proprietà finalDeliveryTransportationService.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportationServiceType }
     *     
     */
    public void setFinalDeliveryTransportationService(TransportationServiceType value) {
        this.finalDeliveryTransportationService = value;
    }

    /**
     * Recupera il valore della proprietà deliveryTerms.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryTermsType }
     *     
     */
    public DeliveryTermsType getDeliveryTerms() {
        return deliveryTerms;
    }

    /**
     * Imposta il valore della proprietà deliveryTerms.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryTermsType }
     *     
     */
    public void setDeliveryTerms(DeliveryTermsType value) {
        this.deliveryTerms = value;
    }

    /**
     * Recupera il valore della proprietà paymentTerms.
     * 
     * @return
     *     possible object is
     *     {@link PaymentTermsType }
     *     
     */
    public PaymentTermsType getPaymentTerms() {
        return paymentTerms;
    }

    /**
     * Imposta il valore della proprietà paymentTerms.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentTermsType }
     *     
     */
    public void setPaymentTerms(PaymentTermsType value) {
        this.paymentTerms = value;
    }

    /**
     * Recupera il valore della proprietà collectPaymentTerms.
     * 
     * @return
     *     possible object is
     *     {@link PaymentTermsType }
     *     
     */
    public PaymentTermsType getCollectPaymentTerms() {
        return collectPaymentTerms;
    }

    /**
     * Imposta il valore della proprietà collectPaymentTerms.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentTermsType }
     *     
     */
    public void setCollectPaymentTerms(PaymentTermsType value) {
        this.collectPaymentTerms = value;
    }

    /**
     * Recupera il valore della proprietà disbursementPaymentTerms.
     * 
     * @return
     *     possible object is
     *     {@link PaymentTermsType }
     *     
     */
    public PaymentTermsType getDisbursementPaymentTerms() {
        return disbursementPaymentTerms;
    }

    /**
     * Imposta il valore della proprietà disbursementPaymentTerms.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentTermsType }
     *     
     */
    public void setDisbursementPaymentTerms(PaymentTermsType value) {
        this.disbursementPaymentTerms = value;
    }

    /**
     * Recupera il valore della proprietà prepaidPaymentTerms.
     * 
     * @return
     *     possible object is
     *     {@link PaymentTermsType }
     *     
     */
    public PaymentTermsType getPrepaidPaymentTerms() {
        return prepaidPaymentTerms;
    }

    /**
     * Imposta il valore della proprietà prepaidPaymentTerms.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentTermsType }
     *     
     */
    public void setPrepaidPaymentTerms(PaymentTermsType value) {
        this.prepaidPaymentTerms = value;
    }

    /**
     * Gets the value of the freightAllowanceCharge property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the freightAllowanceCharge property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getFreightAllowanceCharge().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AllowanceChargeType }
     * 
     * 
     */
    public List<AllowanceChargeType> getFreightAllowanceCharge() {
        if (freightAllowanceCharge == null) {
            freightAllowanceCharge = new ArrayList<AllowanceChargeType>();
        }
        return this.freightAllowanceCharge;
    }

    /**
     * Gets the value of the extraAllowanceCharge property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the extraAllowanceCharge property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getExtraAllowanceCharge().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AllowanceChargeType }
     * 
     * 
     */
    public List<AllowanceChargeType> getExtraAllowanceCharge() {
        if (extraAllowanceCharge == null) {
            extraAllowanceCharge = new ArrayList<AllowanceChargeType>();
        }
        return this.extraAllowanceCharge;
    }

    /**
     * Gets the value of the mainCarriageShipmentStage property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the mainCarriageShipmentStage property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getMainCarriageShipmentStage().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ShipmentStageType }
     * 
     * 
     */
    public List<ShipmentStageType> getMainCarriageShipmentStage() {
        if (mainCarriageShipmentStage == null) {
            mainCarriageShipmentStage = new ArrayList<ShipmentStageType>();
        }
        return this.mainCarriageShipmentStage;
    }

    /**
     * Gets the value of the preCarriageShipmentStage property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the preCarriageShipmentStage property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPreCarriageShipmentStage().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ShipmentStageType }
     * 
     * 
     */
    public List<ShipmentStageType> getPreCarriageShipmentStage() {
        if (preCarriageShipmentStage == null) {
            preCarriageShipmentStage = new ArrayList<ShipmentStageType>();
        }
        return this.preCarriageShipmentStage;
    }

    /**
     * Gets the value of the onCarriageShipmentStage property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the onCarriageShipmentStage property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getOnCarriageShipmentStage().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ShipmentStageType }
     * 
     * 
     */
    public List<ShipmentStageType> getOnCarriageShipmentStage() {
        if (onCarriageShipmentStage == null) {
            onCarriageShipmentStage = new ArrayList<ShipmentStageType>();
        }
        return this.onCarriageShipmentStage;
    }

    /**
     * Gets the value of the transportHandlingUnit property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the transportHandlingUnit property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTransportHandlingUnit().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TransportHandlingUnitType }
     * 
     * 
     */
    public List<TransportHandlingUnitType> getTransportHandlingUnit() {
        if (transportHandlingUnit == null) {
            transportHandlingUnit = new ArrayList<TransportHandlingUnitType>();
        }
        return this.transportHandlingUnit;
    }

    /**
     * Recupera il valore della proprietà firstArrivalPortLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getFirstArrivalPortLocation() {
        return firstArrivalPortLocation;
    }

    /**
     * Imposta il valore della proprietà firstArrivalPortLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setFirstArrivalPortLocation(LocationType value) {
        this.firstArrivalPortLocation = value;
    }

    /**
     * Recupera il valore della proprietà lastExitPortLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getLastExitPortLocation() {
        return lastExitPortLocation;
    }

    /**
     * Imposta il valore della proprietà lastExitPortLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setLastExitPortLocation(LocationType value) {
        this.lastExitPortLocation = value;
    }

    /**
     * Recupera il valore della proprietà officeOfEntryLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getOfficeOfEntryLocation() {
        return officeOfEntryLocation;
    }

    /**
     * Imposta il valore della proprietà officeOfEntryLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setOfficeOfEntryLocation(LocationType value) {
        this.officeOfEntryLocation = value;
    }

    /**
     * Recupera il valore della proprietà officeOfSubSequentiallyEntryLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getOfficeOfSubSequentiallyEntryLocation() {
        return officeOfSubSequentiallyEntryLocation;
    }

    /**
     * Imposta il valore della proprietà officeOfSubSequentiallyEntryLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setOfficeOfSubSequentiallyEntryLocation(LocationType value) {
        this.officeOfSubSequentiallyEntryLocation = value;
    }

    /**
     * Recupera il valore della proprietà officeOfExitLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getOfficeOfExitLocation() {
        return officeOfExitLocation;
    }

    /**
     * Imposta il valore della proprietà officeOfExitLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setOfficeOfExitLocation(LocationType value) {
        this.officeOfExitLocation = value;
    }

    /**
     * Recupera il valore della proprietà officeOfDepartureLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getOfficeOfDepartureLocation() {
        return officeOfDepartureLocation;
    }

    /**
     * Imposta il valore della proprietà officeOfDepartureLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setOfficeOfDepartureLocation(LocationType value) {
        this.officeOfDepartureLocation = value;
    }

    /**
     * Recupera il valore della proprietà officeOfDestinationLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getOfficeOfDestinationLocation() {
        return officeOfDestinationLocation;
    }

    /**
     * Imposta il valore della proprietà officeOfDestinationLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setOfficeOfDestinationLocation(LocationType value) {
        this.officeOfDestinationLocation = value;
    }

    /**
     * Recupera il valore della proprietà officeOfImportLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getOfficeOfImportLocation() {
        return officeOfImportLocation;
    }

    /**
     * Imposta il valore della proprietà officeOfImportLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setOfficeOfImportLocation(LocationType value) {
        this.officeOfImportLocation = value;
    }

    /**
     * Recupera il valore della proprietà officeOfExportLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getOfficeOfExportLocation() {
        return officeOfExportLocation;
    }

    /**
     * Imposta il valore della proprietà officeOfExportLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setOfficeOfExportLocation(LocationType value) {
        this.officeOfExportLocation = value;
    }

    /**
     * Gets the value of the documentReference property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the documentReference property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDocumentReference().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    public List<DocumentReferenceType> getDocumentReference() {
        if (documentReference == null) {
            documentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.documentReference;
    }

}
