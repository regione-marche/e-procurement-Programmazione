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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SequenceNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TransportExecutionPlanReferenceIDType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per TransportationSegmentType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TransportationSegmentType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SequenceNumeric"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TransportExecutionPlanReferenceID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TransportationService"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TransportServiceProviderParty"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ReferencedConsignment" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ShipmentStage" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransportationSegmentType", propOrder = {
    "ublExtensions",
    "sequenceNumeric",
    "transportExecutionPlanReferenceID",
    "transportationService",
    "transportServiceProviderParty",
    "referencedConsignment",
    "shipmentStage"
})
public class TransportationSegmentType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "SequenceNumeric", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected SequenceNumericType sequenceNumeric;
    @XmlElement(name = "TransportExecutionPlanReferenceID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TransportExecutionPlanReferenceIDType transportExecutionPlanReferenceID;
    @XmlElement(name = "TransportationService", required = true)
    protected TransportationServiceType transportationService;
    @XmlElement(name = "TransportServiceProviderParty", required = true)
    protected PartyType transportServiceProviderParty;
    @XmlElement(name = "ReferencedConsignment")
    protected ConsignmentType referencedConsignment;
    @XmlElement(name = "ShipmentStage")
    protected List<ShipmentStageType> shipmentStage;

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
     * Recupera il valore della proprietà sequenceNumeric.
     * 
     * @return
     *     possible object is
     *     {@link SequenceNumericType }
     *     
     */
    public SequenceNumericType getSequenceNumeric() {
        return sequenceNumeric;
    }

    /**
     * Imposta il valore della proprietà sequenceNumeric.
     * 
     * @param value
     *     allowed object is
     *     {@link SequenceNumericType }
     *     
     */
    public void setSequenceNumeric(SequenceNumericType value) {
        this.sequenceNumeric = value;
    }

    /**
     * Recupera il valore della proprietà transportExecutionPlanReferenceID.
     * 
     * @return
     *     possible object is
     *     {@link TransportExecutionPlanReferenceIDType }
     *     
     */
    public TransportExecutionPlanReferenceIDType getTransportExecutionPlanReferenceID() {
        return transportExecutionPlanReferenceID;
    }

    /**
     * Imposta il valore della proprietà transportExecutionPlanReferenceID.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportExecutionPlanReferenceIDType }
     *     
     */
    public void setTransportExecutionPlanReferenceID(TransportExecutionPlanReferenceIDType value) {
        this.transportExecutionPlanReferenceID = value;
    }

    /**
     * Recupera il valore della proprietà transportationService.
     * 
     * @return
     *     possible object is
     *     {@link TransportationServiceType }
     *     
     */
    public TransportationServiceType getTransportationService() {
        return transportationService;
    }

    /**
     * Imposta il valore della proprietà transportationService.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportationServiceType }
     *     
     */
    public void setTransportationService(TransportationServiceType value) {
        this.transportationService = value;
    }

    /**
     * Recupera il valore della proprietà transportServiceProviderParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getTransportServiceProviderParty() {
        return transportServiceProviderParty;
    }

    /**
     * Imposta il valore della proprietà transportServiceProviderParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setTransportServiceProviderParty(PartyType value) {
        this.transportServiceProviderParty = value;
    }

    /**
     * Recupera il valore della proprietà referencedConsignment.
     * 
     * @return
     *     possible object is
     *     {@link ConsignmentType }
     *     
     */
    public ConsignmentType getReferencedConsignment() {
        return referencedConsignment;
    }

    /**
     * Imposta il valore della proprietà referencedConsignment.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsignmentType }
     *     
     */
    public void setReferencedConsignment(ConsignmentType value) {
        this.referencedConsignment = value;
    }

    /**
     * Gets the value of the shipmentStage property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the shipmentStage property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getShipmentStage().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ShipmentStageType }
     * 
     * 
     */
    public List<ShipmentStageType> getShipmentStage() {
        if (shipmentStage == null) {
            shipmentStage = new ArrayList<ShipmentStageType>();
        }
        return this.shipmentStage;
    }

}
