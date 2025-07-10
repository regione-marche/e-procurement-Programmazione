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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AdditionalInformationType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CategoryNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EmergencyProceduresCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HazardClassIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HazardousCategoryCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LowerOrangeHazardPlacardIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaritimePollutantCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MarkingIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MedicalFirstAidGuideCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NetVolumeMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NetWeightMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PlacardEndorsementType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PlacardNotationType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.QuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TechnicalNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TunnelRestrictionCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.UNDGCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.UNPackingGroupCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.UNPackingGroupType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.UpperOrangeHazardPlacardIDType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per HazardousItemType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="HazardousItemType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PlacardNotation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PlacardEndorsement" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AdditionalInformation" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}UNDGCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}UNPackingGroupCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}UNPackingGroup" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}EmergencyProceduresCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MedicalFirstAidGuideCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TunnelRestrictionCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MaritimePollutantCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TechnicalName" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CategoryName" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HazardousCategoryCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}UpperOrangeHazardPlacardID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LowerOrangeHazardPlacardID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MarkingID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HazardClassID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}NetWeightMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}NetVolumeMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Quantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ContactParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SecondaryHazard" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}HazardousGoodsTransit" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}EmergencyTemperature" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}FlashpointTemperature" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AdditionalTemperature" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PositionOnBoardStowage" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HazardousItemType", propOrder = {
    "ublExtensions",
    "id",
    "placardNotation",
    "placardEndorsement",
    "additionalInformation",
    "undgCode",
    "unPackingGroupCode",
    "unPackingGroup",
    "emergencyProceduresCode",
    "medicalFirstAidGuideCode",
    "tunnelRestrictionCode",
    "maritimePollutantCode",
    "technicalName",
    "categoryName",
    "hazardousCategoryCode",
    "upperOrangeHazardPlacardID",
    "lowerOrangeHazardPlacardID",
    "markingID",
    "hazardClassID",
    "netWeightMeasure",
    "netVolumeMeasure",
    "quantity",
    "contactParty",
    "secondaryHazard",
    "hazardousGoodsTransit",
    "emergencyTemperature",
    "flashpointTemperature",
    "additionalTemperature",
    "positionOnBoardStowage"
})
public class HazardousItemType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "PlacardNotation", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PlacardNotationType placardNotation;
    @XmlElement(name = "PlacardEndorsement", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PlacardEndorsementType placardEndorsement;
    @XmlElement(name = "AdditionalInformation", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<AdditionalInformationType> additionalInformation;
    @XmlElement(name = "UNDGCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected UNDGCodeType undgCode;
    @XmlElement(name = "UNPackingGroupCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected UNPackingGroupCodeType unPackingGroupCode;
    @XmlElement(name = "UNPackingGroup", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<UNPackingGroupType> unPackingGroup;
    @XmlElement(name = "EmergencyProceduresCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected EmergencyProceduresCodeType emergencyProceduresCode;
    @XmlElement(name = "MedicalFirstAidGuideCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MedicalFirstAidGuideCodeType medicalFirstAidGuideCode;
    @XmlElement(name = "TunnelRestrictionCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TunnelRestrictionCodeType tunnelRestrictionCode;
    @XmlElement(name = "MaritimePollutantCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MaritimePollutantCodeType maritimePollutantCode;
    @XmlElement(name = "TechnicalName", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TechnicalNameType technicalName;
    @XmlElement(name = "CategoryName", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CategoryNameType categoryName;
    @XmlElement(name = "HazardousCategoryCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected HazardousCategoryCodeType hazardousCategoryCode;
    @XmlElement(name = "UpperOrangeHazardPlacardID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected UpperOrangeHazardPlacardIDType upperOrangeHazardPlacardID;
    @XmlElement(name = "LowerOrangeHazardPlacardID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected LowerOrangeHazardPlacardIDType lowerOrangeHazardPlacardID;
    @XmlElement(name = "MarkingID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MarkingIDType markingID;
    @XmlElement(name = "HazardClassID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected HazardClassIDType hazardClassID;
    @XmlElement(name = "NetWeightMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NetWeightMeasureType netWeightMeasure;
    @XmlElement(name = "NetVolumeMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NetVolumeMeasureType netVolumeMeasure;
    @XmlElement(name = "Quantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected QuantityType quantity;
    @XmlElement(name = "ContactParty")
    protected PartyType contactParty;
    @XmlElement(name = "SecondaryHazard")
    protected List<SecondaryHazardType> secondaryHazard;
    @XmlElement(name = "HazardousGoodsTransit")
    protected List<HazardousGoodsTransitType> hazardousGoodsTransit;
    @XmlElement(name = "EmergencyTemperature")
    protected TemperatureType emergencyTemperature;
    @XmlElement(name = "FlashpointTemperature")
    protected TemperatureType flashpointTemperature;
    @XmlElement(name = "AdditionalTemperature")
    protected List<TemperatureType> additionalTemperature;
    @XmlElement(name = "PositionOnBoardStowage")
    protected StowageType positionOnBoardStowage;

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
     * Recupera il valore della proprietà placardNotation.
     * 
     * @return
     *     possible object is
     *     {@link PlacardNotationType }
     *     
     */
    public PlacardNotationType getPlacardNotation() {
        return placardNotation;
    }

    /**
     * Imposta il valore della proprietà placardNotation.
     * 
     * @param value
     *     allowed object is
     *     {@link PlacardNotationType }
     *     
     */
    public void setPlacardNotation(PlacardNotationType value) {
        this.placardNotation = value;
    }

    /**
     * Recupera il valore della proprietà placardEndorsement.
     * 
     * @return
     *     possible object is
     *     {@link PlacardEndorsementType }
     *     
     */
    public PlacardEndorsementType getPlacardEndorsement() {
        return placardEndorsement;
    }

    /**
     * Imposta il valore della proprietà placardEndorsement.
     * 
     * @param value
     *     allowed object is
     *     {@link PlacardEndorsementType }
     *     
     */
    public void setPlacardEndorsement(PlacardEndorsementType value) {
        this.placardEndorsement = value;
    }

    /**
     * Gets the value of the additionalInformation property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the additionalInformation property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAdditionalInformation().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AdditionalInformationType }
     * 
     * 
     */
    public List<AdditionalInformationType> getAdditionalInformation() {
        if (additionalInformation == null) {
            additionalInformation = new ArrayList<AdditionalInformationType>();
        }
        return this.additionalInformation;
    }

    /**
     * Recupera il valore della proprietà undgCode.
     * 
     * @return
     *     possible object is
     *     {@link UNDGCodeType }
     *     
     */
    public UNDGCodeType getUNDGCode() {
        return undgCode;
    }

    /**
     * Imposta il valore della proprietà undgCode.
     * 
     * @param value
     *     allowed object is
     *     {@link UNDGCodeType }
     *     
     */
    public void setUNDGCode(UNDGCodeType value) {
        this.undgCode = value;
    }

    /**
     * Recupera il valore della proprietà unPackingGroupCode.
     * 
     * @return
     *     possible object is
     *     {@link UNPackingGroupCodeType }
     *     
     */
    public UNPackingGroupCodeType getUNPackingGroupCode() {
        return unPackingGroupCode;
    }

    /**
     * Imposta il valore della proprietà unPackingGroupCode.
     * 
     * @param value
     *     allowed object is
     *     {@link UNPackingGroupCodeType }
     *     
     */
    public void setUNPackingGroupCode(UNPackingGroupCodeType value) {
        this.unPackingGroupCode = value;
    }

    /**
     * Gets the value of the unPackingGroup property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the unPackingGroup property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getUNPackingGroup().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link UNPackingGroupType }
     * 
     * 
     */
    public List<UNPackingGroupType> getUNPackingGroup() {
        if (unPackingGroup == null) {
            unPackingGroup = new ArrayList<UNPackingGroupType>();
        }
        return this.unPackingGroup;
    }

    /**
     * Recupera il valore della proprietà emergencyProceduresCode.
     * 
     * @return
     *     possible object is
     *     {@link EmergencyProceduresCodeType }
     *     
     */
    public EmergencyProceduresCodeType getEmergencyProceduresCode() {
        return emergencyProceduresCode;
    }

    /**
     * Imposta il valore della proprietà emergencyProceduresCode.
     * 
     * @param value
     *     allowed object is
     *     {@link EmergencyProceduresCodeType }
     *     
     */
    public void setEmergencyProceduresCode(EmergencyProceduresCodeType value) {
        this.emergencyProceduresCode = value;
    }

    /**
     * Recupera il valore della proprietà medicalFirstAidGuideCode.
     * 
     * @return
     *     possible object is
     *     {@link MedicalFirstAidGuideCodeType }
     *     
     */
    public MedicalFirstAidGuideCodeType getMedicalFirstAidGuideCode() {
        return medicalFirstAidGuideCode;
    }

    /**
     * Imposta il valore della proprietà medicalFirstAidGuideCode.
     * 
     * @param value
     *     allowed object is
     *     {@link MedicalFirstAidGuideCodeType }
     *     
     */
    public void setMedicalFirstAidGuideCode(MedicalFirstAidGuideCodeType value) {
        this.medicalFirstAidGuideCode = value;
    }

    /**
     * Recupera il valore della proprietà tunnelRestrictionCode.
     * 
     * @return
     *     possible object is
     *     {@link TunnelRestrictionCodeType }
     *     
     */
    public TunnelRestrictionCodeType getTunnelRestrictionCode() {
        return tunnelRestrictionCode;
    }

    /**
     * Imposta il valore della proprietà tunnelRestrictionCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TunnelRestrictionCodeType }
     *     
     */
    public void setTunnelRestrictionCode(TunnelRestrictionCodeType value) {
        this.tunnelRestrictionCode = value;
    }

    /**
     * Recupera il valore della proprietà maritimePollutantCode.
     * 
     * @return
     *     possible object is
     *     {@link MaritimePollutantCodeType }
     *     
     */
    public MaritimePollutantCodeType getMaritimePollutantCode() {
        return maritimePollutantCode;
    }

    /**
     * Imposta il valore della proprietà maritimePollutantCode.
     * 
     * @param value
     *     allowed object is
     *     {@link MaritimePollutantCodeType }
     *     
     */
    public void setMaritimePollutantCode(MaritimePollutantCodeType value) {
        this.maritimePollutantCode = value;
    }

    /**
     * Recupera il valore della proprietà technicalName.
     * 
     * @return
     *     possible object is
     *     {@link TechnicalNameType }
     *     
     */
    public TechnicalNameType getTechnicalName() {
        return technicalName;
    }

    /**
     * Imposta il valore della proprietà technicalName.
     * 
     * @param value
     *     allowed object is
     *     {@link TechnicalNameType }
     *     
     */
    public void setTechnicalName(TechnicalNameType value) {
        this.technicalName = value;
    }

    /**
     * Recupera il valore della proprietà categoryName.
     * 
     * @return
     *     possible object is
     *     {@link CategoryNameType }
     *     
     */
    public CategoryNameType getCategoryName() {
        return categoryName;
    }

    /**
     * Imposta il valore della proprietà categoryName.
     * 
     * @param value
     *     allowed object is
     *     {@link CategoryNameType }
     *     
     */
    public void setCategoryName(CategoryNameType value) {
        this.categoryName = value;
    }

    /**
     * Recupera il valore della proprietà hazardousCategoryCode.
     * 
     * @return
     *     possible object is
     *     {@link HazardousCategoryCodeType }
     *     
     */
    public HazardousCategoryCodeType getHazardousCategoryCode() {
        return hazardousCategoryCode;
    }

    /**
     * Imposta il valore della proprietà hazardousCategoryCode.
     * 
     * @param value
     *     allowed object is
     *     {@link HazardousCategoryCodeType }
     *     
     */
    public void setHazardousCategoryCode(HazardousCategoryCodeType value) {
        this.hazardousCategoryCode = value;
    }

    /**
     * Recupera il valore della proprietà upperOrangeHazardPlacardID.
     * 
     * @return
     *     possible object is
     *     {@link UpperOrangeHazardPlacardIDType }
     *     
     */
    public UpperOrangeHazardPlacardIDType getUpperOrangeHazardPlacardID() {
        return upperOrangeHazardPlacardID;
    }

    /**
     * Imposta il valore della proprietà upperOrangeHazardPlacardID.
     * 
     * @param value
     *     allowed object is
     *     {@link UpperOrangeHazardPlacardIDType }
     *     
     */
    public void setUpperOrangeHazardPlacardID(UpperOrangeHazardPlacardIDType value) {
        this.upperOrangeHazardPlacardID = value;
    }

    /**
     * Recupera il valore della proprietà lowerOrangeHazardPlacardID.
     * 
     * @return
     *     possible object is
     *     {@link LowerOrangeHazardPlacardIDType }
     *     
     */
    public LowerOrangeHazardPlacardIDType getLowerOrangeHazardPlacardID() {
        return lowerOrangeHazardPlacardID;
    }

    /**
     * Imposta il valore della proprietà lowerOrangeHazardPlacardID.
     * 
     * @param value
     *     allowed object is
     *     {@link LowerOrangeHazardPlacardIDType }
     *     
     */
    public void setLowerOrangeHazardPlacardID(LowerOrangeHazardPlacardIDType value) {
        this.lowerOrangeHazardPlacardID = value;
    }

    /**
     * Recupera il valore della proprietà markingID.
     * 
     * @return
     *     possible object is
     *     {@link MarkingIDType }
     *     
     */
    public MarkingIDType getMarkingID() {
        return markingID;
    }

    /**
     * Imposta il valore della proprietà markingID.
     * 
     * @param value
     *     allowed object is
     *     {@link MarkingIDType }
     *     
     */
    public void setMarkingID(MarkingIDType value) {
        this.markingID = value;
    }

    /**
     * Recupera il valore della proprietà hazardClassID.
     * 
     * @return
     *     possible object is
     *     {@link HazardClassIDType }
     *     
     */
    public HazardClassIDType getHazardClassID() {
        return hazardClassID;
    }

    /**
     * Imposta il valore della proprietà hazardClassID.
     * 
     * @param value
     *     allowed object is
     *     {@link HazardClassIDType }
     *     
     */
    public void setHazardClassID(HazardClassIDType value) {
        this.hazardClassID = value;
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
     * Recupera il valore della proprietà quantity.
     * 
     * @return
     *     possible object is
     *     {@link QuantityType }
     *     
     */
    public QuantityType getQuantity() {
        return quantity;
    }

    /**
     * Imposta il valore della proprietà quantity.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *     
     */
    public void setQuantity(QuantityType value) {
        this.quantity = value;
    }

    /**
     * Recupera il valore della proprietà contactParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getContactParty() {
        return contactParty;
    }

    /**
     * Imposta il valore della proprietà contactParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setContactParty(PartyType value) {
        this.contactParty = value;
    }

    /**
     * Gets the value of the secondaryHazard property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the secondaryHazard property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSecondaryHazard().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link SecondaryHazardType }
     * 
     * 
     */
    public List<SecondaryHazardType> getSecondaryHazard() {
        if (secondaryHazard == null) {
            secondaryHazard = new ArrayList<SecondaryHazardType>();
        }
        return this.secondaryHazard;
    }

    /**
     * Gets the value of the hazardousGoodsTransit property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the hazardousGoodsTransit property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getHazardousGoodsTransit().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link HazardousGoodsTransitType }
     * 
     * 
     */
    public List<HazardousGoodsTransitType> getHazardousGoodsTransit() {
        if (hazardousGoodsTransit == null) {
            hazardousGoodsTransit = new ArrayList<HazardousGoodsTransitType>();
        }
        return this.hazardousGoodsTransit;
    }

    /**
     * Recupera il valore della proprietà emergencyTemperature.
     * 
     * @return
     *     possible object is
     *     {@link TemperatureType }
     *     
     */
    public TemperatureType getEmergencyTemperature() {
        return emergencyTemperature;
    }

    /**
     * Imposta il valore della proprietà emergencyTemperature.
     * 
     * @param value
     *     allowed object is
     *     {@link TemperatureType }
     *     
     */
    public void setEmergencyTemperature(TemperatureType value) {
        this.emergencyTemperature = value;
    }

    /**
     * Recupera il valore della proprietà flashpointTemperature.
     * 
     * @return
     *     possible object is
     *     {@link TemperatureType }
     *     
     */
    public TemperatureType getFlashpointTemperature() {
        return flashpointTemperature;
    }

    /**
     * Imposta il valore della proprietà flashpointTemperature.
     * 
     * @param value
     *     allowed object is
     *     {@link TemperatureType }
     *     
     */
    public void setFlashpointTemperature(TemperatureType value) {
        this.flashpointTemperature = value;
    }

    /**
     * Gets the value of the additionalTemperature property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the additionalTemperature property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAdditionalTemperature().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TemperatureType }
     * 
     * 
     */
    public List<TemperatureType> getAdditionalTemperature() {
        if (additionalTemperature == null) {
            additionalTemperature = new ArrayList<TemperatureType>();
        }
        return this.additionalTemperature;
    }

    /**
     * Recupera il valore della proprietà positionOnBoardStowage.
     * 
     * @return
     *     possible object is
     *     {@link StowageType }
     *     
     */
    public StowageType getPositionOnBoardStowage() {
        return positionOnBoardStowage;
    }

    /**
     * Imposta il valore della proprietà positionOnBoardStowage.
     * 
     * @param value
     *     allowed object is
     *     {@link StowageType }
     *     
     */
    public void setPositionOnBoardStowage(StowageType value) {
        this.positionOnBoardStowage = value;
    }

}
