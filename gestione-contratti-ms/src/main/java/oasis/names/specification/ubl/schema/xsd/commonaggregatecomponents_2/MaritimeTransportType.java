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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AntennaLocusType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.GrossTonnageMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.INFShipClassCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MMSIRegistrationIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NetTonnageMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RadioCallSignIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SegregatedBallastMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ShipConfigurationCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ShipsRequirementsType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.VesselIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.VesselNameType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per MaritimeTransportType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="MaritimeTransportType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}VesselID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}VesselName" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RadioCallSignID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MMSIRegistrationID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ShipsRequirements" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}GrossTonnageMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}NetTonnageMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SegregatedBallastMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ShipConfigurationCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}INFShipClassCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AntennaLocus" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RegistryCertificateDocumentReference" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RegistryPortLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}VesselDynamics" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaritimeTransportType", propOrder = {
    "ublExtensions",
    "vesselID",
    "vesselName",
    "radioCallSignID",
    "mmsiRegistrationID",
    "shipsRequirements",
    "grossTonnageMeasure",
    "netTonnageMeasure",
    "segregatedBallastMeasure",
    "shipConfigurationCode",
    "infShipClassCode",
    "antennaLocus",
    "registryCertificateDocumentReference",
    "registryPortLocation",
    "vesselDynamics"
})
public class MaritimeTransportType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "VesselID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected VesselIDType vesselID;
    @XmlElement(name = "VesselName", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected VesselNameType vesselName;
    @XmlElement(name = "RadioCallSignID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RadioCallSignIDType radioCallSignID;
    @XmlElement(name = "MMSIRegistrationID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MMSIRegistrationIDType mmsiRegistrationID;
    @XmlElement(name = "ShipsRequirements", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<ShipsRequirementsType> shipsRequirements;
    @XmlElement(name = "GrossTonnageMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected GrossTonnageMeasureType grossTonnageMeasure;
    @XmlElement(name = "NetTonnageMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NetTonnageMeasureType netTonnageMeasure;
    @XmlElement(name = "SegregatedBallastMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SegregatedBallastMeasureType segregatedBallastMeasure;
    @XmlElement(name = "ShipConfigurationCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ShipConfigurationCodeType shipConfigurationCode;
    @XmlElement(name = "INFShipClassCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected INFShipClassCodeType infShipClassCode;
    @XmlElement(name = "AntennaLocus", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected AntennaLocusType antennaLocus;
    @XmlElement(name = "RegistryCertificateDocumentReference")
    protected DocumentReferenceType registryCertificateDocumentReference;
    @XmlElement(name = "RegistryPortLocation")
    protected LocationType registryPortLocation;
    @XmlElement(name = "VesselDynamics")
    protected VesselDynamicsType vesselDynamics;

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
     * Recupera il valore della proprietà vesselID.
     * 
     * @return
     *     possible object is
     *     {@link VesselIDType }
     *     
     */
    public VesselIDType getVesselID() {
        return vesselID;
    }

    /**
     * Imposta il valore della proprietà vesselID.
     * 
     * @param value
     *     allowed object is
     *     {@link VesselIDType }
     *     
     */
    public void setVesselID(VesselIDType value) {
        this.vesselID = value;
    }

    /**
     * Recupera il valore della proprietà vesselName.
     * 
     * @return
     *     possible object is
     *     {@link VesselNameType }
     *     
     */
    public VesselNameType getVesselName() {
        return vesselName;
    }

    /**
     * Imposta il valore della proprietà vesselName.
     * 
     * @param value
     *     allowed object is
     *     {@link VesselNameType }
     *     
     */
    public void setVesselName(VesselNameType value) {
        this.vesselName = value;
    }

    /**
     * Recupera il valore della proprietà radioCallSignID.
     * 
     * @return
     *     possible object is
     *     {@link RadioCallSignIDType }
     *     
     */
    public RadioCallSignIDType getRadioCallSignID() {
        return radioCallSignID;
    }

    /**
     * Imposta il valore della proprietà radioCallSignID.
     * 
     * @param value
     *     allowed object is
     *     {@link RadioCallSignIDType }
     *     
     */
    public void setRadioCallSignID(RadioCallSignIDType value) {
        this.radioCallSignID = value;
    }

    /**
     * Recupera il valore della proprietà mmsiRegistrationID.
     * 
     * @return
     *     possible object is
     *     {@link MMSIRegistrationIDType }
     *     
     */
    public MMSIRegistrationIDType getMMSIRegistrationID() {
        return mmsiRegistrationID;
    }

    /**
     * Imposta il valore della proprietà mmsiRegistrationID.
     * 
     * @param value
     *     allowed object is
     *     {@link MMSIRegistrationIDType }
     *     
     */
    public void setMMSIRegistrationID(MMSIRegistrationIDType value) {
        this.mmsiRegistrationID = value;
    }

    /**
     * Gets the value of the shipsRequirements property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the shipsRequirements property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getShipsRequirements().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ShipsRequirementsType }
     * 
     * 
     */
    public List<ShipsRequirementsType> getShipsRequirements() {
        if (shipsRequirements == null) {
            shipsRequirements = new ArrayList<ShipsRequirementsType>();
        }
        return this.shipsRequirements;
    }

    /**
     * Recupera il valore della proprietà grossTonnageMeasure.
     * 
     * @return
     *     possible object is
     *     {@link GrossTonnageMeasureType }
     *     
     */
    public GrossTonnageMeasureType getGrossTonnageMeasure() {
        return grossTonnageMeasure;
    }

    /**
     * Imposta il valore della proprietà grossTonnageMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link GrossTonnageMeasureType }
     *     
     */
    public void setGrossTonnageMeasure(GrossTonnageMeasureType value) {
        this.grossTonnageMeasure = value;
    }

    /**
     * Recupera il valore della proprietà netTonnageMeasure.
     * 
     * @return
     *     possible object is
     *     {@link NetTonnageMeasureType }
     *     
     */
    public NetTonnageMeasureType getNetTonnageMeasure() {
        return netTonnageMeasure;
    }

    /**
     * Imposta il valore della proprietà netTonnageMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link NetTonnageMeasureType }
     *     
     */
    public void setNetTonnageMeasure(NetTonnageMeasureType value) {
        this.netTonnageMeasure = value;
    }

    /**
     * Recupera il valore della proprietà segregatedBallastMeasure.
     * 
     * @return
     *     possible object is
     *     {@link SegregatedBallastMeasureType }
     *     
     */
    public SegregatedBallastMeasureType getSegregatedBallastMeasure() {
        return segregatedBallastMeasure;
    }

    /**
     * Imposta il valore della proprietà segregatedBallastMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link SegregatedBallastMeasureType }
     *     
     */
    public void setSegregatedBallastMeasure(SegregatedBallastMeasureType value) {
        this.segregatedBallastMeasure = value;
    }

    /**
     * Recupera il valore della proprietà shipConfigurationCode.
     * 
     * @return
     *     possible object is
     *     {@link ShipConfigurationCodeType }
     *     
     */
    public ShipConfigurationCodeType getShipConfigurationCode() {
        return shipConfigurationCode;
    }

    /**
     * Imposta il valore della proprietà shipConfigurationCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipConfigurationCodeType }
     *     
     */
    public void setShipConfigurationCode(ShipConfigurationCodeType value) {
        this.shipConfigurationCode = value;
    }

    /**
     * Recupera il valore della proprietà infShipClassCode.
     * 
     * @return
     *     possible object is
     *     {@link INFShipClassCodeType }
     *     
     */
    public INFShipClassCodeType getINFShipClassCode() {
        return infShipClassCode;
    }

    /**
     * Imposta il valore della proprietà infShipClassCode.
     * 
     * @param value
     *     allowed object is
     *     {@link INFShipClassCodeType }
     *     
     */
    public void setINFShipClassCode(INFShipClassCodeType value) {
        this.infShipClassCode = value;
    }

    /**
     * Recupera il valore della proprietà antennaLocus.
     * 
     * @return
     *     possible object is
     *     {@link AntennaLocusType }
     *     
     */
    public AntennaLocusType getAntennaLocus() {
        return antennaLocus;
    }

    /**
     * Imposta il valore della proprietà antennaLocus.
     * 
     * @param value
     *     allowed object is
     *     {@link AntennaLocusType }
     *     
     */
    public void setAntennaLocus(AntennaLocusType value) {
        this.antennaLocus = value;
    }

    /**
     * Recupera il valore della proprietà registryCertificateDocumentReference.
     * 
     * @return
     *     possible object is
     *     {@link DocumentReferenceType }
     *     
     */
    public DocumentReferenceType getRegistryCertificateDocumentReference() {
        return registryCertificateDocumentReference;
    }

    /**
     * Imposta il valore della proprietà registryCertificateDocumentReference.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentReferenceType }
     *     
     */
    public void setRegistryCertificateDocumentReference(DocumentReferenceType value) {
        this.registryCertificateDocumentReference = value;
    }

    /**
     * Recupera il valore della proprietà registryPortLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getRegistryPortLocation() {
        return registryPortLocation;
    }

    /**
     * Imposta il valore della proprietà registryPortLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setRegistryPortLocation(LocationType value) {
        this.registryPortLocation = value;
    }

    /**
     * Recupera il valore della proprietà vesselDynamics.
     * 
     * @return
     *     possible object is
     *     {@link VesselDynamicsType }
     *     
     */
    public VesselDynamicsType getVesselDynamics() {
        return vesselDynamics;
    }

    /**
     * Imposta il valore della proprietà vesselDynamics.
     * 
     * @param value
     *     allowed object is
     *     {@link VesselDynamicsType }
     *     
     */
    public void setVesselDynamics(VesselDynamicsType value) {
        this.vesselDynamics = value;
    }

}
