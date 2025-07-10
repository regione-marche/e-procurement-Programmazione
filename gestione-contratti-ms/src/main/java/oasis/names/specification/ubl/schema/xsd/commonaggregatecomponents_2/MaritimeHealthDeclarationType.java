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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FumigatedCargoTransportIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InfectiousDiseaseCaseOnBoardIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LastDrinkingWaterAnalysisDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MedicalPractitionerConsultedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MoreIllThanExpectedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReinspectionRequiredIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SanitaryMeasuresAppliedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SickAnimalDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SickAnimalOnBoardIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.StowawayDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.StowawaysFoundOnBoardIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalDeadPersonQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalIllPersonQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValidSanitationCertificateOnBoardIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per MaritimeHealthDeclarationType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="MaritimeHealthDeclarationType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}InfectiousDiseaseCaseOnBoardIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MoreIllThanExpectedIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MedicalPractitionerConsultedIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}StowawaysFoundOnBoardIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SickAnimalOnBoardIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}FumigatedCargoTransportIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SanitaryMeasuresAppliedIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ValidSanitationCertificateOnBoardIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ReinspectionRequiredIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TotalDeadPersonQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TotalIllPersonQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SickAnimalDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}StowawayDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LastDrinkingWaterAnalysisDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}WHOAffectedAreaVisit" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PersonnelHealthIncident" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SanitaryMeasure" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PlaceOfReportLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}MedicalCertificate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ShipSanitationControlCertificate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ShipSanitationControlExemptionDocumentReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaritimeHealthDeclarationType", propOrder = {
    "ublExtensions",
    "id",
    "infectiousDiseaseCaseOnBoardIndicator",
    "moreIllThanExpectedIndicator",
    "medicalPractitionerConsultedIndicator",
    "stowawaysFoundOnBoardIndicator",
    "sickAnimalOnBoardIndicator",
    "fumigatedCargoTransportIndicator",
    "sanitaryMeasuresAppliedIndicator",
    "validSanitationCertificateOnBoardIndicator",
    "reinspectionRequiredIndicator",
    "totalDeadPersonQuantity",
    "totalIllPersonQuantity",
    "sickAnimalDescription",
    "stowawayDescription",
    "lastDrinkingWaterAnalysisDate",
    "whoAffectedAreaVisit",
    "personnelHealthIncident",
    "sanitaryMeasure",
    "placeOfReportLocation",
    "medicalCertificate",
    "shipSanitationControlCertificate",
    "shipSanitationControlExemptionDocumentReference"
})
public class MaritimeHealthDeclarationType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "InfectiousDiseaseCaseOnBoardIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected InfectiousDiseaseCaseOnBoardIndicatorType infectiousDiseaseCaseOnBoardIndicator;
    @XmlElement(name = "MoreIllThanExpectedIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MoreIllThanExpectedIndicatorType moreIllThanExpectedIndicator;
    @XmlElement(name = "MedicalPractitionerConsultedIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MedicalPractitionerConsultedIndicatorType medicalPractitionerConsultedIndicator;
    @XmlElement(name = "StowawaysFoundOnBoardIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected StowawaysFoundOnBoardIndicatorType stowawaysFoundOnBoardIndicator;
    @XmlElement(name = "SickAnimalOnBoardIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SickAnimalOnBoardIndicatorType sickAnimalOnBoardIndicator;
    @XmlElement(name = "FumigatedCargoTransportIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected FumigatedCargoTransportIndicatorType fumigatedCargoTransportIndicator;
    @XmlElement(name = "SanitaryMeasuresAppliedIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SanitaryMeasuresAppliedIndicatorType sanitaryMeasuresAppliedIndicator;
    @XmlElement(name = "ValidSanitationCertificateOnBoardIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ValidSanitationCertificateOnBoardIndicatorType validSanitationCertificateOnBoardIndicator;
    @XmlElement(name = "ReinspectionRequiredIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ReinspectionRequiredIndicatorType reinspectionRequiredIndicator;
    @XmlElement(name = "TotalDeadPersonQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TotalDeadPersonQuantityType totalDeadPersonQuantity;
    @XmlElement(name = "TotalIllPersonQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TotalIllPersonQuantityType totalIllPersonQuantity;
    @XmlElement(name = "SickAnimalDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<SickAnimalDescriptionType> sickAnimalDescription;
    @XmlElement(name = "StowawayDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<StowawayDescriptionType> stowawayDescription;
    @XmlElement(name = "LastDrinkingWaterAnalysisDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected LastDrinkingWaterAnalysisDateType lastDrinkingWaterAnalysisDate;
    @XmlElement(name = "WHOAffectedAreaVisit")
    protected List<WHOAffectedAreaVisitType> whoAffectedAreaVisit;
    @XmlElement(name = "PersonnelHealthIncident")
    protected List<PersonnelHealthIncidentType> personnelHealthIncident;
    @XmlElement(name = "SanitaryMeasure")
    protected List<SanitaryMeasureType> sanitaryMeasure;
    @XmlElement(name = "PlaceOfReportLocation")
    protected LocationType placeOfReportLocation;
    @XmlElement(name = "MedicalCertificate")
    protected CertificateType medicalCertificate;
    @XmlElement(name = "ShipSanitationControlCertificate")
    protected CertificateType shipSanitationControlCertificate;
    @XmlElement(name = "ShipSanitationControlExemptionDocumentReference")
    protected List<DocumentReferenceType> shipSanitationControlExemptionDocumentReference;

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
     * Recupera il valore della proprietà infectiousDiseaseCaseOnBoardIndicator.
     * 
     * @return
     *     possible object is
     *     {@link InfectiousDiseaseCaseOnBoardIndicatorType }
     *     
     */
    public InfectiousDiseaseCaseOnBoardIndicatorType getInfectiousDiseaseCaseOnBoardIndicator() {
        return infectiousDiseaseCaseOnBoardIndicator;
    }

    /**
     * Imposta il valore della proprietà infectiousDiseaseCaseOnBoardIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link InfectiousDiseaseCaseOnBoardIndicatorType }
     *     
     */
    public void setInfectiousDiseaseCaseOnBoardIndicator(InfectiousDiseaseCaseOnBoardIndicatorType value) {
        this.infectiousDiseaseCaseOnBoardIndicator = value;
    }

    /**
     * Recupera il valore della proprietà moreIllThanExpectedIndicator.
     * 
     * @return
     *     possible object is
     *     {@link MoreIllThanExpectedIndicatorType }
     *     
     */
    public MoreIllThanExpectedIndicatorType getMoreIllThanExpectedIndicator() {
        return moreIllThanExpectedIndicator;
    }

    /**
     * Imposta il valore della proprietà moreIllThanExpectedIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link MoreIllThanExpectedIndicatorType }
     *     
     */
    public void setMoreIllThanExpectedIndicator(MoreIllThanExpectedIndicatorType value) {
        this.moreIllThanExpectedIndicator = value;
    }

    /**
     * Recupera il valore della proprietà medicalPractitionerConsultedIndicator.
     * 
     * @return
     *     possible object is
     *     {@link MedicalPractitionerConsultedIndicatorType }
     *     
     */
    public MedicalPractitionerConsultedIndicatorType getMedicalPractitionerConsultedIndicator() {
        return medicalPractitionerConsultedIndicator;
    }

    /**
     * Imposta il valore della proprietà medicalPractitionerConsultedIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link MedicalPractitionerConsultedIndicatorType }
     *     
     */
    public void setMedicalPractitionerConsultedIndicator(MedicalPractitionerConsultedIndicatorType value) {
        this.medicalPractitionerConsultedIndicator = value;
    }

    /**
     * Recupera il valore della proprietà stowawaysFoundOnBoardIndicator.
     * 
     * @return
     *     possible object is
     *     {@link StowawaysFoundOnBoardIndicatorType }
     *     
     */
    public StowawaysFoundOnBoardIndicatorType getStowawaysFoundOnBoardIndicator() {
        return stowawaysFoundOnBoardIndicator;
    }

    /**
     * Imposta il valore della proprietà stowawaysFoundOnBoardIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link StowawaysFoundOnBoardIndicatorType }
     *     
     */
    public void setStowawaysFoundOnBoardIndicator(StowawaysFoundOnBoardIndicatorType value) {
        this.stowawaysFoundOnBoardIndicator = value;
    }

    /**
     * Recupera il valore della proprietà sickAnimalOnBoardIndicator.
     * 
     * @return
     *     possible object is
     *     {@link SickAnimalOnBoardIndicatorType }
     *     
     */
    public SickAnimalOnBoardIndicatorType getSickAnimalOnBoardIndicator() {
        return sickAnimalOnBoardIndicator;
    }

    /**
     * Imposta il valore della proprietà sickAnimalOnBoardIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link SickAnimalOnBoardIndicatorType }
     *     
     */
    public void setSickAnimalOnBoardIndicator(SickAnimalOnBoardIndicatorType value) {
        this.sickAnimalOnBoardIndicator = value;
    }

    /**
     * Recupera il valore della proprietà fumigatedCargoTransportIndicator.
     * 
     * @return
     *     possible object is
     *     {@link FumigatedCargoTransportIndicatorType }
     *     
     */
    public FumigatedCargoTransportIndicatorType getFumigatedCargoTransportIndicator() {
        return fumigatedCargoTransportIndicator;
    }

    /**
     * Imposta il valore della proprietà fumigatedCargoTransportIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link FumigatedCargoTransportIndicatorType }
     *     
     */
    public void setFumigatedCargoTransportIndicator(FumigatedCargoTransportIndicatorType value) {
        this.fumigatedCargoTransportIndicator = value;
    }

    /**
     * Recupera il valore della proprietà sanitaryMeasuresAppliedIndicator.
     * 
     * @return
     *     possible object is
     *     {@link SanitaryMeasuresAppliedIndicatorType }
     *     
     */
    public SanitaryMeasuresAppliedIndicatorType getSanitaryMeasuresAppliedIndicator() {
        return sanitaryMeasuresAppliedIndicator;
    }

    /**
     * Imposta il valore della proprietà sanitaryMeasuresAppliedIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link SanitaryMeasuresAppliedIndicatorType }
     *     
     */
    public void setSanitaryMeasuresAppliedIndicator(SanitaryMeasuresAppliedIndicatorType value) {
        this.sanitaryMeasuresAppliedIndicator = value;
    }

    /**
     * Recupera il valore della proprietà validSanitationCertificateOnBoardIndicator.
     * 
     * @return
     *     possible object is
     *     {@link ValidSanitationCertificateOnBoardIndicatorType }
     *     
     */
    public ValidSanitationCertificateOnBoardIndicatorType getValidSanitationCertificateOnBoardIndicator() {
        return validSanitationCertificateOnBoardIndicator;
    }

    /**
     * Imposta il valore della proprietà validSanitationCertificateOnBoardIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidSanitationCertificateOnBoardIndicatorType }
     *     
     */
    public void setValidSanitationCertificateOnBoardIndicator(ValidSanitationCertificateOnBoardIndicatorType value) {
        this.validSanitationCertificateOnBoardIndicator = value;
    }

    /**
     * Recupera il valore della proprietà reinspectionRequiredIndicator.
     * 
     * @return
     *     possible object is
     *     {@link ReinspectionRequiredIndicatorType }
     *     
     */
    public ReinspectionRequiredIndicatorType getReinspectionRequiredIndicator() {
        return reinspectionRequiredIndicator;
    }

    /**
     * Imposta il valore della proprietà reinspectionRequiredIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link ReinspectionRequiredIndicatorType }
     *     
     */
    public void setReinspectionRequiredIndicator(ReinspectionRequiredIndicatorType value) {
        this.reinspectionRequiredIndicator = value;
    }

    /**
     * Recupera il valore della proprietà totalDeadPersonQuantity.
     * 
     * @return
     *     possible object is
     *     {@link TotalDeadPersonQuantityType }
     *     
     */
    public TotalDeadPersonQuantityType getTotalDeadPersonQuantity() {
        return totalDeadPersonQuantity;
    }

    /**
     * Imposta il valore della proprietà totalDeadPersonQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalDeadPersonQuantityType }
     *     
     */
    public void setTotalDeadPersonQuantity(TotalDeadPersonQuantityType value) {
        this.totalDeadPersonQuantity = value;
    }

    /**
     * Recupera il valore della proprietà totalIllPersonQuantity.
     * 
     * @return
     *     possible object is
     *     {@link TotalIllPersonQuantityType }
     *     
     */
    public TotalIllPersonQuantityType getTotalIllPersonQuantity() {
        return totalIllPersonQuantity;
    }

    /**
     * Imposta il valore della proprietà totalIllPersonQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalIllPersonQuantityType }
     *     
     */
    public void setTotalIllPersonQuantity(TotalIllPersonQuantityType value) {
        this.totalIllPersonQuantity = value;
    }

    /**
     * Gets the value of the sickAnimalDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the sickAnimalDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSickAnimalDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link SickAnimalDescriptionType }
     * 
     * 
     */
    public List<SickAnimalDescriptionType> getSickAnimalDescription() {
        if (sickAnimalDescription == null) {
            sickAnimalDescription = new ArrayList<SickAnimalDescriptionType>();
        }
        return this.sickAnimalDescription;
    }

    /**
     * Gets the value of the stowawayDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the stowawayDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getStowawayDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link StowawayDescriptionType }
     * 
     * 
     */
    public List<StowawayDescriptionType> getStowawayDescription() {
        if (stowawayDescription == null) {
            stowawayDescription = new ArrayList<StowawayDescriptionType>();
        }
        return this.stowawayDescription;
    }

    /**
     * Recupera il valore della proprietà lastDrinkingWaterAnalysisDate.
     * 
     * @return
     *     possible object is
     *     {@link LastDrinkingWaterAnalysisDateType }
     *     
     */
    public LastDrinkingWaterAnalysisDateType getLastDrinkingWaterAnalysisDate() {
        return lastDrinkingWaterAnalysisDate;
    }

    /**
     * Imposta il valore della proprietà lastDrinkingWaterAnalysisDate.
     * 
     * @param value
     *     allowed object is
     *     {@link LastDrinkingWaterAnalysisDateType }
     *     
     */
    public void setLastDrinkingWaterAnalysisDate(LastDrinkingWaterAnalysisDateType value) {
        this.lastDrinkingWaterAnalysisDate = value;
    }

    /**
     * Gets the value of the whoAffectedAreaVisit property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the whoAffectedAreaVisit property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getWHOAffectedAreaVisit().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link WHOAffectedAreaVisitType }
     * 
     * 
     */
    public List<WHOAffectedAreaVisitType> getWHOAffectedAreaVisit() {
        if (whoAffectedAreaVisit == null) {
            whoAffectedAreaVisit = new ArrayList<WHOAffectedAreaVisitType>();
        }
        return this.whoAffectedAreaVisit;
    }

    /**
     * Gets the value of the personnelHealthIncident property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the personnelHealthIncident property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPersonnelHealthIncident().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PersonnelHealthIncidentType }
     * 
     * 
     */
    public List<PersonnelHealthIncidentType> getPersonnelHealthIncident() {
        if (personnelHealthIncident == null) {
            personnelHealthIncident = new ArrayList<PersonnelHealthIncidentType>();
        }
        return this.personnelHealthIncident;
    }

    /**
     * Gets the value of the sanitaryMeasure property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the sanitaryMeasure property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSanitaryMeasure().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link SanitaryMeasureType }
     * 
     * 
     */
    public List<SanitaryMeasureType> getSanitaryMeasure() {
        if (sanitaryMeasure == null) {
            sanitaryMeasure = new ArrayList<SanitaryMeasureType>();
        }
        return this.sanitaryMeasure;
    }

    /**
     * Recupera il valore della proprietà placeOfReportLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getPlaceOfReportLocation() {
        return placeOfReportLocation;
    }

    /**
     * Imposta il valore della proprietà placeOfReportLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setPlaceOfReportLocation(LocationType value) {
        this.placeOfReportLocation = value;
    }

    /**
     * Recupera il valore della proprietà medicalCertificate.
     * 
     * @return
     *     possible object is
     *     {@link CertificateType }
     *     
     */
    public CertificateType getMedicalCertificate() {
        return medicalCertificate;
    }

    /**
     * Imposta il valore della proprietà medicalCertificate.
     * 
     * @param value
     *     allowed object is
     *     {@link CertificateType }
     *     
     */
    public void setMedicalCertificate(CertificateType value) {
        this.medicalCertificate = value;
    }

    /**
     * Recupera il valore della proprietà shipSanitationControlCertificate.
     * 
     * @return
     *     possible object is
     *     {@link CertificateType }
     *     
     */
    public CertificateType getShipSanitationControlCertificate() {
        return shipSanitationControlCertificate;
    }

    /**
     * Imposta il valore della proprietà shipSanitationControlCertificate.
     * 
     * @param value
     *     allowed object is
     *     {@link CertificateType }
     *     
     */
    public void setShipSanitationControlCertificate(CertificateType value) {
        this.shipSanitationControlCertificate = value;
    }

    /**
     * Gets the value of the shipSanitationControlExemptionDocumentReference property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the shipSanitationControlExemptionDocumentReference property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getShipSanitationControlExemptionDocumentReference().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    public List<DocumentReferenceType> getShipSanitationControlExemptionDocumentReference() {
        if (shipSanitationControlExemptionDocumentReference == null) {
            shipSanitationControlExemptionDocumentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.shipSanitationControlExemptionDocumentReference;
    }

}
