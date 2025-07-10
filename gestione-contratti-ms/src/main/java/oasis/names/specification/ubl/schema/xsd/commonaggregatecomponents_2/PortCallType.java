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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CargoAndBallastTankConditionDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExpectedAnchorageIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PlannedInspectionsDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PlannedOperationsDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PlannedWorksDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PositionInPortIDType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per PortCallType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="PortCallType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PlannedOperationsDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PlannedWorksDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PlannedInspectionsDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ExpectedAnchorageIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PositionInPortID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CargoAndBallastTankConditionDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ShipRequirement" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PrimaryPortCallPurpose" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AdditionalPortCallPurpose" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RequestedArrivalEvent" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PortCallType", propOrder = {
    "ublExtensions",
    "id",
    "plannedOperationsDescription",
    "plannedWorksDescription",
    "plannedInspectionsDescription",
    "expectedAnchorageIndicator",
    "positionInPortID",
    "cargoAndBallastTankConditionDescription",
    "shipRequirement",
    "primaryPortCallPurpose",
    "additionalPortCallPurpose",
    "requestedArrivalEvent"
})
public class PortCallType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "PlannedOperationsDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<PlannedOperationsDescriptionType> plannedOperationsDescription;
    @XmlElement(name = "PlannedWorksDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<PlannedWorksDescriptionType> plannedWorksDescription;
    @XmlElement(name = "PlannedInspectionsDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<PlannedInspectionsDescriptionType> plannedInspectionsDescription;
    @XmlElement(name = "ExpectedAnchorageIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ExpectedAnchorageIndicatorType expectedAnchorageIndicator;
    @XmlElement(name = "PositionInPortID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PositionInPortIDType positionInPortID;
    @XmlElement(name = "CargoAndBallastTankConditionDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<CargoAndBallastTankConditionDescriptionType> cargoAndBallastTankConditionDescription;
    @XmlElement(name = "ShipRequirement")
    protected List<ShipRequirementType> shipRequirement;
    @XmlElement(name = "PrimaryPortCallPurpose")
    protected PortCallPurposeType primaryPortCallPurpose;
    @XmlElement(name = "AdditionalPortCallPurpose")
    protected List<PortCallPurposeType> additionalPortCallPurpose;
    @XmlElement(name = "RequestedArrivalEvent")
    protected EventType requestedArrivalEvent;

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
     * Gets the value of the plannedOperationsDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the plannedOperationsDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPlannedOperationsDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PlannedOperationsDescriptionType }
     * 
     * 
     */
    public List<PlannedOperationsDescriptionType> getPlannedOperationsDescription() {
        if (plannedOperationsDescription == null) {
            plannedOperationsDescription = new ArrayList<PlannedOperationsDescriptionType>();
        }
        return this.plannedOperationsDescription;
    }

    /**
     * Gets the value of the plannedWorksDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the plannedWorksDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPlannedWorksDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PlannedWorksDescriptionType }
     * 
     * 
     */
    public List<PlannedWorksDescriptionType> getPlannedWorksDescription() {
        if (plannedWorksDescription == null) {
            plannedWorksDescription = new ArrayList<PlannedWorksDescriptionType>();
        }
        return this.plannedWorksDescription;
    }

    /**
     * Gets the value of the plannedInspectionsDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the plannedInspectionsDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPlannedInspectionsDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PlannedInspectionsDescriptionType }
     * 
     * 
     */
    public List<PlannedInspectionsDescriptionType> getPlannedInspectionsDescription() {
        if (plannedInspectionsDescription == null) {
            plannedInspectionsDescription = new ArrayList<PlannedInspectionsDescriptionType>();
        }
        return this.plannedInspectionsDescription;
    }

    /**
     * Recupera il valore della proprietà expectedAnchorageIndicator.
     * 
     * @return
     *     possible object is
     *     {@link ExpectedAnchorageIndicatorType }
     *     
     */
    public ExpectedAnchorageIndicatorType getExpectedAnchorageIndicator() {
        return expectedAnchorageIndicator;
    }

    /**
     * Imposta il valore della proprietà expectedAnchorageIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpectedAnchorageIndicatorType }
     *     
     */
    public void setExpectedAnchorageIndicator(ExpectedAnchorageIndicatorType value) {
        this.expectedAnchorageIndicator = value;
    }

    /**
     * Recupera il valore della proprietà positionInPortID.
     * 
     * @return
     *     possible object is
     *     {@link PositionInPortIDType }
     *     
     */
    public PositionInPortIDType getPositionInPortID() {
        return positionInPortID;
    }

    /**
     * Imposta il valore della proprietà positionInPortID.
     * 
     * @param value
     *     allowed object is
     *     {@link PositionInPortIDType }
     *     
     */
    public void setPositionInPortID(PositionInPortIDType value) {
        this.positionInPortID = value;
    }

    /**
     * Gets the value of the cargoAndBallastTankConditionDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the cargoAndBallastTankConditionDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCargoAndBallastTankConditionDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CargoAndBallastTankConditionDescriptionType }
     * 
     * 
     */
    public List<CargoAndBallastTankConditionDescriptionType> getCargoAndBallastTankConditionDescription() {
        if (cargoAndBallastTankConditionDescription == null) {
            cargoAndBallastTankConditionDescription = new ArrayList<CargoAndBallastTankConditionDescriptionType>();
        }
        return this.cargoAndBallastTankConditionDescription;
    }

    /**
     * Gets the value of the shipRequirement property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the shipRequirement property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getShipRequirement().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ShipRequirementType }
     * 
     * 
     */
    public List<ShipRequirementType> getShipRequirement() {
        if (shipRequirement == null) {
            shipRequirement = new ArrayList<ShipRequirementType>();
        }
        return this.shipRequirement;
    }

    /**
     * Recupera il valore della proprietà primaryPortCallPurpose.
     * 
     * @return
     *     possible object is
     *     {@link PortCallPurposeType }
     *     
     */
    public PortCallPurposeType getPrimaryPortCallPurpose() {
        return primaryPortCallPurpose;
    }

    /**
     * Imposta il valore della proprietà primaryPortCallPurpose.
     * 
     * @param value
     *     allowed object is
     *     {@link PortCallPurposeType }
     *     
     */
    public void setPrimaryPortCallPurpose(PortCallPurposeType value) {
        this.primaryPortCallPurpose = value;
    }

    /**
     * Gets the value of the additionalPortCallPurpose property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the additionalPortCallPurpose property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAdditionalPortCallPurpose().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PortCallPurposeType }
     * 
     * 
     */
    public List<PortCallPurposeType> getAdditionalPortCallPurpose() {
        if (additionalPortCallPurpose == null) {
            additionalPortCallPurpose = new ArrayList<PortCallPurposeType>();
        }
        return this.additionalPortCallPurpose;
    }

    /**
     * Recupera il valore della proprietà requestedArrivalEvent.
     * 
     * @return
     *     possible object is
     *     {@link EventType }
     *     
     */
    public EventType getRequestedArrivalEvent() {
        return requestedArrivalEvent;
    }

    /**
     * Imposta il valore della proprietà requestedArrivalEvent.
     * 
     * @param value
     *     allowed object is
     *     {@link EventType }
     *     
     */
    public void setRequestedArrivalEvent(EventType value) {
        this.requestedArrivalEvent = value;
    }

}
