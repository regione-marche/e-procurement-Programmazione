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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CabotageIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CrewQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DemurrageInstructionsType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EstimatedDeliveryDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EstimatedDeliveryTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HazardousRiskIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InstructionsType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LoadingSequenceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OnCarriageIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PassengerQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PreCarriageIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RequiredDeliveryDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RequiredDeliveryTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ShipmentStageTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ShipmentStageTypeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SuccessiveSequenceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TransitDirectionCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TransportMeansTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TransportModeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per ShipmentStageType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ShipmentStageType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ShipmentStageTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ShipmentStageType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TransportModeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TransportMeansTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TransitDirectionCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PreCarriageIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}OnCarriageIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CabotageIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HazardousRiskIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}EstimatedDeliveryDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}EstimatedDeliveryTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RequiredDeliveryDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RequiredDeliveryTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LoadingSequenceID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SuccessiveSequenceID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Instructions" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DemurrageInstructions" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CrewQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PassengerQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TransitPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CarrierParty" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TransportMeans" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}LoadingPortLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}UnloadingPortLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TransshipPortLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}LoadingTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ExaminationTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AvailabilityTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ExportationTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DischargeTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}WarehousingTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TakeoverTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OptionalTakeoverTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DropoffTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ActualPickupTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DeliveryTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ReceiptTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}StorageTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AcceptanceTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TerminalOperatorParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CustomsAgentParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}EstimatedTransitPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}FreightAllowanceCharge" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}FreightChargeLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DetentionTransportEvent" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RequestedDepartureTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RequestedArrivalTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RequestedWaypointTransportEvent" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PlannedDepartureTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PlannedArrivalTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PlannedWaypointTransportEvent" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ActualDepartureTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ActualWaypointTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ActualArrivalTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TransportEvent" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}EstimatedDepartureTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}EstimatedArrivalTransportEvent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PassengerPerson" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DriverPerson" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ReportingPerson" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CrewMemberPerson" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SecurityOfficerPerson" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}MasterPerson" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ShipsSurgeonPerson" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DestinationPortCall" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ShipStoreArticle" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CrewPersonEffect" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}MaritimeWaste" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}BallastWaterSummary" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ISPSRequirements" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}MaritimeHealthDeclaration" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipmentStageType", propOrder = {
    "ublExtensions",
    "id",
    "shipmentStageTypeCode",
    "shipmentStageType",
    "transportModeCode",
    "transportMeansTypeCode",
    "transitDirectionCode",
    "preCarriageIndicator",
    "onCarriageIndicator",
    "cabotageIndicator",
    "hazardousRiskIndicator",
    "estimatedDeliveryDate",
    "estimatedDeliveryTime",
    "requiredDeliveryDate",
    "requiredDeliveryTime",
    "loadingSequenceID",
    "successiveSequenceID",
    "instructions",
    "demurrageInstructions",
    "crewQuantity",
    "passengerQuantity",
    "transitPeriod",
    "carrierParty",
    "transportMeans",
    "loadingPortLocation",
    "unloadingPortLocation",
    "transshipPortLocation",
    "loadingTransportEvent",
    "examinationTransportEvent",
    "availabilityTransportEvent",
    "exportationTransportEvent",
    "dischargeTransportEvent",
    "warehousingTransportEvent",
    "takeoverTransportEvent",
    "optionalTakeoverTransportEvent",
    "dropoffTransportEvent",
    "actualPickupTransportEvent",
    "deliveryTransportEvent",
    "receiptTransportEvent",
    "storageTransportEvent",
    "acceptanceTransportEvent",
    "terminalOperatorParty",
    "customsAgentParty",
    "estimatedTransitPeriod",
    "freightAllowanceCharge",
    "freightChargeLocation",
    "detentionTransportEvent",
    "requestedDepartureTransportEvent",
    "requestedArrivalTransportEvent",
    "requestedWaypointTransportEvent",
    "plannedDepartureTransportEvent",
    "plannedArrivalTransportEvent",
    "plannedWaypointTransportEvent",
    "actualDepartureTransportEvent",
    "actualWaypointTransportEvent",
    "actualArrivalTransportEvent",
    "transportEvent",
    "estimatedDepartureTransportEvent",
    "estimatedArrivalTransportEvent",
    "passengerPerson",
    "driverPerson",
    "reportingPerson",
    "crewMemberPerson",
    "securityOfficerPerson",
    "masterPerson",
    "shipsSurgeonPerson",
    "destinationPortCall",
    "shipStoreArticle",
    "crewPersonEffect",
    "maritimeWaste",
    "ballastWaterSummary",
    "ispsRequirements",
    "maritimeHealthDeclaration"
})
public class ShipmentStageType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "ShipmentStageTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ShipmentStageTypeCodeType shipmentStageTypeCode;
    @XmlElement(name = "ShipmentStageType", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<ShipmentStageTypeType> shipmentStageType;
    @XmlElement(name = "TransportModeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TransportModeCodeType transportModeCode;
    @XmlElement(name = "TransportMeansTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TransportMeansTypeCodeType transportMeansTypeCode;
    @XmlElement(name = "TransitDirectionCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TransitDirectionCodeType transitDirectionCode;
    @XmlElement(name = "PreCarriageIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PreCarriageIndicatorType preCarriageIndicator;
    @XmlElement(name = "OnCarriageIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected OnCarriageIndicatorType onCarriageIndicator;
    @XmlElement(name = "CabotageIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CabotageIndicatorType cabotageIndicator;
    @XmlElement(name = "HazardousRiskIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected HazardousRiskIndicatorType hazardousRiskIndicator;
    @XmlElement(name = "EstimatedDeliveryDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected EstimatedDeliveryDateType estimatedDeliveryDate;
    @XmlElement(name = "EstimatedDeliveryTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected EstimatedDeliveryTimeType estimatedDeliveryTime;
    @XmlElement(name = "RequiredDeliveryDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RequiredDeliveryDateType requiredDeliveryDate;
    @XmlElement(name = "RequiredDeliveryTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RequiredDeliveryTimeType requiredDeliveryTime;
    @XmlElement(name = "LoadingSequenceID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected LoadingSequenceIDType loadingSequenceID;
    @XmlElement(name = "SuccessiveSequenceID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SuccessiveSequenceIDType successiveSequenceID;
    @XmlElement(name = "Instructions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<InstructionsType> instructions;
    @XmlElement(name = "DemurrageInstructions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DemurrageInstructionsType> demurrageInstructions;
    @XmlElement(name = "CrewQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CrewQuantityType crewQuantity;
    @XmlElement(name = "PassengerQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PassengerQuantityType passengerQuantity;
    @XmlElement(name = "TransitPeriod")
    protected PeriodType transitPeriod;
    @XmlElement(name = "CarrierParty")
    protected List<PartyType> carrierParty;
    @XmlElement(name = "TransportMeans")
    protected TransportMeansType transportMeans;
    @XmlElement(name = "LoadingPortLocation")
    protected LocationType loadingPortLocation;
    @XmlElement(name = "UnloadingPortLocation")
    protected LocationType unloadingPortLocation;
    @XmlElement(name = "TransshipPortLocation")
    protected LocationType transshipPortLocation;
    @XmlElement(name = "LoadingTransportEvent")
    protected TransportEventType loadingTransportEvent;
    @XmlElement(name = "ExaminationTransportEvent")
    protected TransportEventType examinationTransportEvent;
    @XmlElement(name = "AvailabilityTransportEvent")
    protected TransportEventType availabilityTransportEvent;
    @XmlElement(name = "ExportationTransportEvent")
    protected TransportEventType exportationTransportEvent;
    @XmlElement(name = "DischargeTransportEvent")
    protected TransportEventType dischargeTransportEvent;
    @XmlElement(name = "WarehousingTransportEvent")
    protected TransportEventType warehousingTransportEvent;
    @XmlElement(name = "TakeoverTransportEvent")
    protected TransportEventType takeoverTransportEvent;
    @XmlElement(name = "OptionalTakeoverTransportEvent")
    protected TransportEventType optionalTakeoverTransportEvent;
    @XmlElement(name = "DropoffTransportEvent")
    protected TransportEventType dropoffTransportEvent;
    @XmlElement(name = "ActualPickupTransportEvent")
    protected TransportEventType actualPickupTransportEvent;
    @XmlElement(name = "DeliveryTransportEvent")
    protected TransportEventType deliveryTransportEvent;
    @XmlElement(name = "ReceiptTransportEvent")
    protected TransportEventType receiptTransportEvent;
    @XmlElement(name = "StorageTransportEvent")
    protected TransportEventType storageTransportEvent;
    @XmlElement(name = "AcceptanceTransportEvent")
    protected TransportEventType acceptanceTransportEvent;
    @XmlElement(name = "TerminalOperatorParty")
    protected PartyType terminalOperatorParty;
    @XmlElement(name = "CustomsAgentParty")
    protected PartyType customsAgentParty;
    @XmlElement(name = "EstimatedTransitPeriod")
    protected PeriodType estimatedTransitPeriod;
    @XmlElement(name = "FreightAllowanceCharge")
    protected List<AllowanceChargeType> freightAllowanceCharge;
    @XmlElement(name = "FreightChargeLocation")
    protected LocationType freightChargeLocation;
    @XmlElement(name = "DetentionTransportEvent")
    protected List<TransportEventType> detentionTransportEvent;
    @XmlElement(name = "RequestedDepartureTransportEvent")
    protected TransportEventType requestedDepartureTransportEvent;
    @XmlElement(name = "RequestedArrivalTransportEvent")
    protected TransportEventType requestedArrivalTransportEvent;
    @XmlElement(name = "RequestedWaypointTransportEvent")
    protected List<TransportEventType> requestedWaypointTransportEvent;
    @XmlElement(name = "PlannedDepartureTransportEvent")
    protected TransportEventType plannedDepartureTransportEvent;
    @XmlElement(name = "PlannedArrivalTransportEvent")
    protected TransportEventType plannedArrivalTransportEvent;
    @XmlElement(name = "PlannedWaypointTransportEvent")
    protected List<TransportEventType> plannedWaypointTransportEvent;
    @XmlElement(name = "ActualDepartureTransportEvent")
    protected TransportEventType actualDepartureTransportEvent;
    @XmlElement(name = "ActualWaypointTransportEvent")
    protected TransportEventType actualWaypointTransportEvent;
    @XmlElement(name = "ActualArrivalTransportEvent")
    protected TransportEventType actualArrivalTransportEvent;
    @XmlElement(name = "TransportEvent")
    protected List<TransportEventType> transportEvent;
    @XmlElement(name = "EstimatedDepartureTransportEvent")
    protected TransportEventType estimatedDepartureTransportEvent;
    @XmlElement(name = "EstimatedArrivalTransportEvent")
    protected TransportEventType estimatedArrivalTransportEvent;
    @XmlElement(name = "PassengerPerson")
    protected List<PersonType> passengerPerson;
    @XmlElement(name = "DriverPerson")
    protected List<PersonType> driverPerson;
    @XmlElement(name = "ReportingPerson")
    protected PersonType reportingPerson;
    @XmlElement(name = "CrewMemberPerson")
    protected List<PersonType> crewMemberPerson;
    @XmlElement(name = "SecurityOfficerPerson")
    protected PersonType securityOfficerPerson;
    @XmlElement(name = "MasterPerson")
    protected PersonType masterPerson;
    @XmlElement(name = "ShipsSurgeonPerson")
    protected PersonType shipsSurgeonPerson;
    @XmlElement(name = "DestinationPortCall")
    protected PortCallType destinationPortCall;
    @XmlElement(name = "ShipStoreArticle")
    protected List<ShipStoreArticleType> shipStoreArticle;
    @XmlElement(name = "CrewPersonEffect")
    protected List<CrewPersonEffectType> crewPersonEffect;
    @XmlElement(name = "MaritimeWaste")
    protected List<MaritimeWasteType> maritimeWaste;
    @XmlElement(name = "BallastWaterSummary")
    protected BallastWaterSummaryType ballastWaterSummary;
    @XmlElement(name = "ISPSRequirements")
    protected ISPSRequirementsType ispsRequirements;
    @XmlElement(name = "MaritimeHealthDeclaration")
    protected MaritimeHealthDeclarationType maritimeHealthDeclaration;

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
     * Recupera il valore della proprietà shipmentStageTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link ShipmentStageTypeCodeType }
     *     
     */
    public ShipmentStageTypeCodeType getShipmentStageTypeCode() {
        return shipmentStageTypeCode;
    }

    /**
     * Imposta il valore della proprietà shipmentStageTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipmentStageTypeCodeType }
     *     
     */
    public void setShipmentStageTypeCode(ShipmentStageTypeCodeType value) {
        this.shipmentStageTypeCode = value;
    }

    /**
     * Gets the value of the shipmentStageType property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the shipmentStageType property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getShipmentStageType().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ShipmentStageTypeType }
     * 
     * 
     */
    public List<ShipmentStageTypeType> getShipmentStageType() {
        if (shipmentStageType == null) {
            shipmentStageType = new ArrayList<ShipmentStageTypeType>();
        }
        return this.shipmentStageType;
    }

    /**
     * Recupera il valore della proprietà transportModeCode.
     * 
     * @return
     *     possible object is
     *     {@link TransportModeCodeType }
     *     
     */
    public TransportModeCodeType getTransportModeCode() {
        return transportModeCode;
    }

    /**
     * Imposta il valore della proprietà transportModeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportModeCodeType }
     *     
     */
    public void setTransportModeCode(TransportModeCodeType value) {
        this.transportModeCode = value;
    }

    /**
     * Recupera il valore della proprietà transportMeansTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link TransportMeansTypeCodeType }
     *     
     */
    public TransportMeansTypeCodeType getTransportMeansTypeCode() {
        return transportMeansTypeCode;
    }

    /**
     * Imposta il valore della proprietà transportMeansTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportMeansTypeCodeType }
     *     
     */
    public void setTransportMeansTypeCode(TransportMeansTypeCodeType value) {
        this.transportMeansTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà transitDirectionCode.
     * 
     * @return
     *     possible object is
     *     {@link TransitDirectionCodeType }
     *     
     */
    public TransitDirectionCodeType getTransitDirectionCode() {
        return transitDirectionCode;
    }

    /**
     * Imposta il valore della proprietà transitDirectionCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TransitDirectionCodeType }
     *     
     */
    public void setTransitDirectionCode(TransitDirectionCodeType value) {
        this.transitDirectionCode = value;
    }

    /**
     * Recupera il valore della proprietà preCarriageIndicator.
     * 
     * @return
     *     possible object is
     *     {@link PreCarriageIndicatorType }
     *     
     */
    public PreCarriageIndicatorType getPreCarriageIndicator() {
        return preCarriageIndicator;
    }

    /**
     * Imposta il valore della proprietà preCarriageIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link PreCarriageIndicatorType }
     *     
     */
    public void setPreCarriageIndicator(PreCarriageIndicatorType value) {
        this.preCarriageIndicator = value;
    }

    /**
     * Recupera il valore della proprietà onCarriageIndicator.
     * 
     * @return
     *     possible object is
     *     {@link OnCarriageIndicatorType }
     *     
     */
    public OnCarriageIndicatorType getOnCarriageIndicator() {
        return onCarriageIndicator;
    }

    /**
     * Imposta il valore della proprietà onCarriageIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link OnCarriageIndicatorType }
     *     
     */
    public void setOnCarriageIndicator(OnCarriageIndicatorType value) {
        this.onCarriageIndicator = value;
    }

    /**
     * Recupera il valore della proprietà cabotageIndicator.
     * 
     * @return
     *     possible object is
     *     {@link CabotageIndicatorType }
     *     
     */
    public CabotageIndicatorType getCabotageIndicator() {
        return cabotageIndicator;
    }

    /**
     * Imposta il valore della proprietà cabotageIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link CabotageIndicatorType }
     *     
     */
    public void setCabotageIndicator(CabotageIndicatorType value) {
        this.cabotageIndicator = value;
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
     * Recupera il valore della proprietà estimatedDeliveryDate.
     * 
     * @return
     *     possible object is
     *     {@link EstimatedDeliveryDateType }
     *     
     */
    public EstimatedDeliveryDateType getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    /**
     * Imposta il valore della proprietà estimatedDeliveryDate.
     * 
     * @param value
     *     allowed object is
     *     {@link EstimatedDeliveryDateType }
     *     
     */
    public void setEstimatedDeliveryDate(EstimatedDeliveryDateType value) {
        this.estimatedDeliveryDate = value;
    }

    /**
     * Recupera il valore della proprietà estimatedDeliveryTime.
     * 
     * @return
     *     possible object is
     *     {@link EstimatedDeliveryTimeType }
     *     
     */
    public EstimatedDeliveryTimeType getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    /**
     * Imposta il valore della proprietà estimatedDeliveryTime.
     * 
     * @param value
     *     allowed object is
     *     {@link EstimatedDeliveryTimeType }
     *     
     */
    public void setEstimatedDeliveryTime(EstimatedDeliveryTimeType value) {
        this.estimatedDeliveryTime = value;
    }

    /**
     * Recupera il valore della proprietà requiredDeliveryDate.
     * 
     * @return
     *     possible object is
     *     {@link RequiredDeliveryDateType }
     *     
     */
    public RequiredDeliveryDateType getRequiredDeliveryDate() {
        return requiredDeliveryDate;
    }

    /**
     * Imposta il valore della proprietà requiredDeliveryDate.
     * 
     * @param value
     *     allowed object is
     *     {@link RequiredDeliveryDateType }
     *     
     */
    public void setRequiredDeliveryDate(RequiredDeliveryDateType value) {
        this.requiredDeliveryDate = value;
    }

    /**
     * Recupera il valore della proprietà requiredDeliveryTime.
     * 
     * @return
     *     possible object is
     *     {@link RequiredDeliveryTimeType }
     *     
     */
    public RequiredDeliveryTimeType getRequiredDeliveryTime() {
        return requiredDeliveryTime;
    }

    /**
     * Imposta il valore della proprietà requiredDeliveryTime.
     * 
     * @param value
     *     allowed object is
     *     {@link RequiredDeliveryTimeType }
     *     
     */
    public void setRequiredDeliveryTime(RequiredDeliveryTimeType value) {
        this.requiredDeliveryTime = value;
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
     * Recupera il valore della proprietà successiveSequenceID.
     * 
     * @return
     *     possible object is
     *     {@link SuccessiveSequenceIDType }
     *     
     */
    public SuccessiveSequenceIDType getSuccessiveSequenceID() {
        return successiveSequenceID;
    }

    /**
     * Imposta il valore della proprietà successiveSequenceID.
     * 
     * @param value
     *     allowed object is
     *     {@link SuccessiveSequenceIDType }
     *     
     */
    public void setSuccessiveSequenceID(SuccessiveSequenceIDType value) {
        this.successiveSequenceID = value;
    }

    /**
     * Gets the value of the instructions property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the instructions property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getInstructions().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link InstructionsType }
     * 
     * 
     */
    public List<InstructionsType> getInstructions() {
        if (instructions == null) {
            instructions = new ArrayList<InstructionsType>();
        }
        return this.instructions;
    }

    /**
     * Gets the value of the demurrageInstructions property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the demurrageInstructions property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDemurrageInstructions().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DemurrageInstructionsType }
     * 
     * 
     */
    public List<DemurrageInstructionsType> getDemurrageInstructions() {
        if (demurrageInstructions == null) {
            demurrageInstructions = new ArrayList<DemurrageInstructionsType>();
        }
        return this.demurrageInstructions;
    }

    /**
     * Recupera il valore della proprietà crewQuantity.
     * 
     * @return
     *     possible object is
     *     {@link CrewQuantityType }
     *     
     */
    public CrewQuantityType getCrewQuantity() {
        return crewQuantity;
    }

    /**
     * Imposta il valore della proprietà crewQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link CrewQuantityType }
     *     
     */
    public void setCrewQuantity(CrewQuantityType value) {
        this.crewQuantity = value;
    }

    /**
     * Recupera il valore della proprietà passengerQuantity.
     * 
     * @return
     *     possible object is
     *     {@link PassengerQuantityType }
     *     
     */
    public PassengerQuantityType getPassengerQuantity() {
        return passengerQuantity;
    }

    /**
     * Imposta il valore della proprietà passengerQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link PassengerQuantityType }
     *     
     */
    public void setPassengerQuantity(PassengerQuantityType value) {
        this.passengerQuantity = value;
    }

    /**
     * Recupera il valore della proprietà transitPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getTransitPeriod() {
        return transitPeriod;
    }

    /**
     * Imposta il valore della proprietà transitPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setTransitPeriod(PeriodType value) {
        this.transitPeriod = value;
    }

    /**
     * Gets the value of the carrierParty property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the carrierParty property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCarrierParty().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PartyType }
     * 
     * 
     */
    public List<PartyType> getCarrierParty() {
        if (carrierParty == null) {
            carrierParty = new ArrayList<PartyType>();
        }
        return this.carrierParty;
    }

    /**
     * Recupera il valore della proprietà transportMeans.
     * 
     * @return
     *     possible object is
     *     {@link TransportMeansType }
     *     
     */
    public TransportMeansType getTransportMeans() {
        return transportMeans;
    }

    /**
     * Imposta il valore della proprietà transportMeans.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportMeansType }
     *     
     */
    public void setTransportMeans(TransportMeansType value) {
        this.transportMeans = value;
    }

    /**
     * Recupera il valore della proprietà loadingPortLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getLoadingPortLocation() {
        return loadingPortLocation;
    }

    /**
     * Imposta il valore della proprietà loadingPortLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setLoadingPortLocation(LocationType value) {
        this.loadingPortLocation = value;
    }

    /**
     * Recupera il valore della proprietà unloadingPortLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getUnloadingPortLocation() {
        return unloadingPortLocation;
    }

    /**
     * Imposta il valore della proprietà unloadingPortLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setUnloadingPortLocation(LocationType value) {
        this.unloadingPortLocation = value;
    }

    /**
     * Recupera il valore della proprietà transshipPortLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getTransshipPortLocation() {
        return transshipPortLocation;
    }

    /**
     * Imposta il valore della proprietà transshipPortLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setTransshipPortLocation(LocationType value) {
        this.transshipPortLocation = value;
    }

    /**
     * Recupera il valore della proprietà loadingTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getLoadingTransportEvent() {
        return loadingTransportEvent;
    }

    /**
     * Imposta il valore della proprietà loadingTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setLoadingTransportEvent(TransportEventType value) {
        this.loadingTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà examinationTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getExaminationTransportEvent() {
        return examinationTransportEvent;
    }

    /**
     * Imposta il valore della proprietà examinationTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setExaminationTransportEvent(TransportEventType value) {
        this.examinationTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà availabilityTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getAvailabilityTransportEvent() {
        return availabilityTransportEvent;
    }

    /**
     * Imposta il valore della proprietà availabilityTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setAvailabilityTransportEvent(TransportEventType value) {
        this.availabilityTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà exportationTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getExportationTransportEvent() {
        return exportationTransportEvent;
    }

    /**
     * Imposta il valore della proprietà exportationTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setExportationTransportEvent(TransportEventType value) {
        this.exportationTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà dischargeTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getDischargeTransportEvent() {
        return dischargeTransportEvent;
    }

    /**
     * Imposta il valore della proprietà dischargeTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setDischargeTransportEvent(TransportEventType value) {
        this.dischargeTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà warehousingTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getWarehousingTransportEvent() {
        return warehousingTransportEvent;
    }

    /**
     * Imposta il valore della proprietà warehousingTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setWarehousingTransportEvent(TransportEventType value) {
        this.warehousingTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà takeoverTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getTakeoverTransportEvent() {
        return takeoverTransportEvent;
    }

    /**
     * Imposta il valore della proprietà takeoverTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setTakeoverTransportEvent(TransportEventType value) {
        this.takeoverTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà optionalTakeoverTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getOptionalTakeoverTransportEvent() {
        return optionalTakeoverTransportEvent;
    }

    /**
     * Imposta il valore della proprietà optionalTakeoverTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setOptionalTakeoverTransportEvent(TransportEventType value) {
        this.optionalTakeoverTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà dropoffTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getDropoffTransportEvent() {
        return dropoffTransportEvent;
    }

    /**
     * Imposta il valore della proprietà dropoffTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setDropoffTransportEvent(TransportEventType value) {
        this.dropoffTransportEvent = value;
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
     * Recupera il valore della proprietà deliveryTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getDeliveryTransportEvent() {
        return deliveryTransportEvent;
    }

    /**
     * Imposta il valore della proprietà deliveryTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setDeliveryTransportEvent(TransportEventType value) {
        this.deliveryTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà receiptTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getReceiptTransportEvent() {
        return receiptTransportEvent;
    }

    /**
     * Imposta il valore della proprietà receiptTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setReceiptTransportEvent(TransportEventType value) {
        this.receiptTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà storageTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getStorageTransportEvent() {
        return storageTransportEvent;
    }

    /**
     * Imposta il valore della proprietà storageTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setStorageTransportEvent(TransportEventType value) {
        this.storageTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà acceptanceTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getAcceptanceTransportEvent() {
        return acceptanceTransportEvent;
    }

    /**
     * Imposta il valore della proprietà acceptanceTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setAcceptanceTransportEvent(TransportEventType value) {
        this.acceptanceTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà terminalOperatorParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getTerminalOperatorParty() {
        return terminalOperatorParty;
    }

    /**
     * Imposta il valore della proprietà terminalOperatorParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setTerminalOperatorParty(PartyType value) {
        this.terminalOperatorParty = value;
    }

    /**
     * Recupera il valore della proprietà customsAgentParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getCustomsAgentParty() {
        return customsAgentParty;
    }

    /**
     * Imposta il valore della proprietà customsAgentParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setCustomsAgentParty(PartyType value) {
        this.customsAgentParty = value;
    }

    /**
     * Recupera il valore della proprietà estimatedTransitPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getEstimatedTransitPeriod() {
        return estimatedTransitPeriod;
    }

    /**
     * Imposta il valore della proprietà estimatedTransitPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setEstimatedTransitPeriod(PeriodType value) {
        this.estimatedTransitPeriod = value;
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
     * Recupera il valore della proprietà freightChargeLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getFreightChargeLocation() {
        return freightChargeLocation;
    }

    /**
     * Imposta il valore della proprietà freightChargeLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setFreightChargeLocation(LocationType value) {
        this.freightChargeLocation = value;
    }

    /**
     * Gets the value of the detentionTransportEvent property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the detentionTransportEvent property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDetentionTransportEvent().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TransportEventType }
     * 
     * 
     */
    public List<TransportEventType> getDetentionTransportEvent() {
        if (detentionTransportEvent == null) {
            detentionTransportEvent = new ArrayList<TransportEventType>();
        }
        return this.detentionTransportEvent;
    }

    /**
     * Recupera il valore della proprietà requestedDepartureTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getRequestedDepartureTransportEvent() {
        return requestedDepartureTransportEvent;
    }

    /**
     * Imposta il valore della proprietà requestedDepartureTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setRequestedDepartureTransportEvent(TransportEventType value) {
        this.requestedDepartureTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà requestedArrivalTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getRequestedArrivalTransportEvent() {
        return requestedArrivalTransportEvent;
    }

    /**
     * Imposta il valore della proprietà requestedArrivalTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setRequestedArrivalTransportEvent(TransportEventType value) {
        this.requestedArrivalTransportEvent = value;
    }

    /**
     * Gets the value of the requestedWaypointTransportEvent property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the requestedWaypointTransportEvent property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getRequestedWaypointTransportEvent().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TransportEventType }
     * 
     * 
     */
    public List<TransportEventType> getRequestedWaypointTransportEvent() {
        if (requestedWaypointTransportEvent == null) {
            requestedWaypointTransportEvent = new ArrayList<TransportEventType>();
        }
        return this.requestedWaypointTransportEvent;
    }

    /**
     * Recupera il valore della proprietà plannedDepartureTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getPlannedDepartureTransportEvent() {
        return plannedDepartureTransportEvent;
    }

    /**
     * Imposta il valore della proprietà plannedDepartureTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setPlannedDepartureTransportEvent(TransportEventType value) {
        this.plannedDepartureTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà plannedArrivalTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getPlannedArrivalTransportEvent() {
        return plannedArrivalTransportEvent;
    }

    /**
     * Imposta il valore della proprietà plannedArrivalTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setPlannedArrivalTransportEvent(TransportEventType value) {
        this.plannedArrivalTransportEvent = value;
    }

    /**
     * Gets the value of the plannedWaypointTransportEvent property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the plannedWaypointTransportEvent property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPlannedWaypointTransportEvent().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TransportEventType }
     * 
     * 
     */
    public List<TransportEventType> getPlannedWaypointTransportEvent() {
        if (plannedWaypointTransportEvent == null) {
            plannedWaypointTransportEvent = new ArrayList<TransportEventType>();
        }
        return this.plannedWaypointTransportEvent;
    }

    /**
     * Recupera il valore della proprietà actualDepartureTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getActualDepartureTransportEvent() {
        return actualDepartureTransportEvent;
    }

    /**
     * Imposta il valore della proprietà actualDepartureTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setActualDepartureTransportEvent(TransportEventType value) {
        this.actualDepartureTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà actualWaypointTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getActualWaypointTransportEvent() {
        return actualWaypointTransportEvent;
    }

    /**
     * Imposta il valore della proprietà actualWaypointTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setActualWaypointTransportEvent(TransportEventType value) {
        this.actualWaypointTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà actualArrivalTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getActualArrivalTransportEvent() {
        return actualArrivalTransportEvent;
    }

    /**
     * Imposta il valore della proprietà actualArrivalTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setActualArrivalTransportEvent(TransportEventType value) {
        this.actualArrivalTransportEvent = value;
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
     * Recupera il valore della proprietà estimatedDepartureTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getEstimatedDepartureTransportEvent() {
        return estimatedDepartureTransportEvent;
    }

    /**
     * Imposta il valore della proprietà estimatedDepartureTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setEstimatedDepartureTransportEvent(TransportEventType value) {
        this.estimatedDepartureTransportEvent = value;
    }

    /**
     * Recupera il valore della proprietà estimatedArrivalTransportEvent.
     * 
     * @return
     *     possible object is
     *     {@link TransportEventType }
     *     
     */
    public TransportEventType getEstimatedArrivalTransportEvent() {
        return estimatedArrivalTransportEvent;
    }

    /**
     * Imposta il valore della proprietà estimatedArrivalTransportEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportEventType }
     *     
     */
    public void setEstimatedArrivalTransportEvent(TransportEventType value) {
        this.estimatedArrivalTransportEvent = value;
    }

    /**
     * Gets the value of the passengerPerson property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the passengerPerson property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPassengerPerson().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PersonType }
     * 
     * 
     */
    public List<PersonType> getPassengerPerson() {
        if (passengerPerson == null) {
            passengerPerson = new ArrayList<PersonType>();
        }
        return this.passengerPerson;
    }

    /**
     * Gets the value of the driverPerson property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the driverPerson property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDriverPerson().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PersonType }
     * 
     * 
     */
    public List<PersonType> getDriverPerson() {
        if (driverPerson == null) {
            driverPerson = new ArrayList<PersonType>();
        }
        return this.driverPerson;
    }

    /**
     * Recupera il valore della proprietà reportingPerson.
     * 
     * @return
     *     possible object is
     *     {@link PersonType }
     *     
     */
    public PersonType getReportingPerson() {
        return reportingPerson;
    }

    /**
     * Imposta il valore della proprietà reportingPerson.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonType }
     *     
     */
    public void setReportingPerson(PersonType value) {
        this.reportingPerson = value;
    }

    /**
     * Gets the value of the crewMemberPerson property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the crewMemberPerson property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCrewMemberPerson().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PersonType }
     * 
     * 
     */
    public List<PersonType> getCrewMemberPerson() {
        if (crewMemberPerson == null) {
            crewMemberPerson = new ArrayList<PersonType>();
        }
        return this.crewMemberPerson;
    }

    /**
     * Recupera il valore della proprietà securityOfficerPerson.
     * 
     * @return
     *     possible object is
     *     {@link PersonType }
     *     
     */
    public PersonType getSecurityOfficerPerson() {
        return securityOfficerPerson;
    }

    /**
     * Imposta il valore della proprietà securityOfficerPerson.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonType }
     *     
     */
    public void setSecurityOfficerPerson(PersonType value) {
        this.securityOfficerPerson = value;
    }

    /**
     * Recupera il valore della proprietà masterPerson.
     * 
     * @return
     *     possible object is
     *     {@link PersonType }
     *     
     */
    public PersonType getMasterPerson() {
        return masterPerson;
    }

    /**
     * Imposta il valore della proprietà masterPerson.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonType }
     *     
     */
    public void setMasterPerson(PersonType value) {
        this.masterPerson = value;
    }

    /**
     * Recupera il valore della proprietà shipsSurgeonPerson.
     * 
     * @return
     *     possible object is
     *     {@link PersonType }
     *     
     */
    public PersonType getShipsSurgeonPerson() {
        return shipsSurgeonPerson;
    }

    /**
     * Imposta il valore della proprietà shipsSurgeonPerson.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonType }
     *     
     */
    public void setShipsSurgeonPerson(PersonType value) {
        this.shipsSurgeonPerson = value;
    }

    /**
     * Recupera il valore della proprietà destinationPortCall.
     * 
     * @return
     *     possible object is
     *     {@link PortCallType }
     *     
     */
    public PortCallType getDestinationPortCall() {
        return destinationPortCall;
    }

    /**
     * Imposta il valore della proprietà destinationPortCall.
     * 
     * @param value
     *     allowed object is
     *     {@link PortCallType }
     *     
     */
    public void setDestinationPortCall(PortCallType value) {
        this.destinationPortCall = value;
    }

    /**
     * Gets the value of the shipStoreArticle property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the shipStoreArticle property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getShipStoreArticle().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ShipStoreArticleType }
     * 
     * 
     */
    public List<ShipStoreArticleType> getShipStoreArticle() {
        if (shipStoreArticle == null) {
            shipStoreArticle = new ArrayList<ShipStoreArticleType>();
        }
        return this.shipStoreArticle;
    }

    /**
     * Gets the value of the crewPersonEffect property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the crewPersonEffect property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCrewPersonEffect().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CrewPersonEffectType }
     * 
     * 
     */
    public List<CrewPersonEffectType> getCrewPersonEffect() {
        if (crewPersonEffect == null) {
            crewPersonEffect = new ArrayList<CrewPersonEffectType>();
        }
        return this.crewPersonEffect;
    }

    /**
     * Gets the value of the maritimeWaste property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the maritimeWaste property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getMaritimeWaste().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link MaritimeWasteType }
     * 
     * 
     */
    public List<MaritimeWasteType> getMaritimeWaste() {
        if (maritimeWaste == null) {
            maritimeWaste = new ArrayList<MaritimeWasteType>();
        }
        return this.maritimeWaste;
    }

    /**
     * Recupera il valore della proprietà ballastWaterSummary.
     * 
     * @return
     *     possible object is
     *     {@link BallastWaterSummaryType }
     *     
     */
    public BallastWaterSummaryType getBallastWaterSummary() {
        return ballastWaterSummary;
    }

    /**
     * Imposta il valore della proprietà ballastWaterSummary.
     * 
     * @param value
     *     allowed object is
     *     {@link BallastWaterSummaryType }
     *     
     */
    public void setBallastWaterSummary(BallastWaterSummaryType value) {
        this.ballastWaterSummary = value;
    }

    /**
     * Recupera il valore della proprietà ispsRequirements.
     * 
     * @return
     *     possible object is
     *     {@link ISPSRequirementsType }
     *     
     */
    public ISPSRequirementsType getISPSRequirements() {
        return ispsRequirements;
    }

    /**
     * Imposta il valore della proprietà ispsRequirements.
     * 
     * @param value
     *     allowed object is
     *     {@link ISPSRequirementsType }
     *     
     */
    public void setISPSRequirements(ISPSRequirementsType value) {
        this.ispsRequirements = value;
    }

    /**
     * Recupera il valore della proprietà maritimeHealthDeclaration.
     * 
     * @return
     *     possible object is
     *     {@link MaritimeHealthDeclarationType }
     *     
     */
    public MaritimeHealthDeclarationType getMaritimeHealthDeclaration() {
        return maritimeHealthDeclaration;
    }

    /**
     * Imposta il valore della proprietà maritimeHealthDeclaration.
     * 
     * @param value
     *     allowed object is
     *     {@link MaritimeHealthDeclarationType }
     *     
     */
    public void setMaritimeHealthDeclaration(MaritimeHealthDeclarationType value) {
        this.maritimeHealthDeclaration = value;
    }

}
