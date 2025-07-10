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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BuriedAtSeaIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DiedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EvacuatedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.GivenTreatmentDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.JoinedShipDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NatureOfIllnessDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NoteType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OnsetDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReportedToMedicalOfficerIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.StillIllIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.StillOnBoardIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per PersonnelHealthIncidentType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="PersonnelHealthIncidentType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}JoinedShipDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}NatureOfIllnessDescription" maxOccurs="unbounded"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}OnsetDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ReportedToMedicalOfficerIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}GivenTreatmentDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}StillIllIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DiedIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}StillOnBoardIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}EvacuatedIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}BuriedAtSeaIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Note" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Person" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonnelHealthIncidentType", propOrder = {
    "ublExtensions",
    "id",
    "joinedShipDate",
    "natureOfIllnessDescription",
    "onsetDate",
    "reportedToMedicalOfficerIndicator",
    "givenTreatmentDescription",
    "stillIllIndicator",
    "diedIndicator",
    "stillOnBoardIndicator",
    "evacuatedIndicator",
    "buriedAtSeaIndicator",
    "note",
    "person"
})
public class PersonnelHealthIncidentType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected IDType id;
    @XmlElement(name = "JoinedShipDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected JoinedShipDateType joinedShipDate;
    @XmlElement(name = "NatureOfIllnessDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected List<NatureOfIllnessDescriptionType> natureOfIllnessDescription;
    @XmlElement(name = "OnsetDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected OnsetDateType onsetDate;
    @XmlElement(name = "ReportedToMedicalOfficerIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ReportedToMedicalOfficerIndicatorType reportedToMedicalOfficerIndicator;
    @XmlElement(name = "GivenTreatmentDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<GivenTreatmentDescriptionType> givenTreatmentDescription;
    @XmlElement(name = "StillIllIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected StillIllIndicatorType stillIllIndicator;
    @XmlElement(name = "DiedIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected DiedIndicatorType diedIndicator;
    @XmlElement(name = "StillOnBoardIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected StillOnBoardIndicatorType stillOnBoardIndicator;
    @XmlElement(name = "EvacuatedIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected EvacuatedIndicatorType evacuatedIndicator;
    @XmlElement(name = "BuriedAtSeaIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected BuriedAtSeaIndicatorType buriedAtSeaIndicator;
    @XmlElement(name = "Note", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<NoteType> note;
    @XmlElement(name = "Person")
    protected PersonType person;

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
     * Recupera il valore della proprietà joinedShipDate.
     * 
     * @return
     *     possible object is
     *     {@link JoinedShipDateType }
     *     
     */
    public JoinedShipDateType getJoinedShipDate() {
        return joinedShipDate;
    }

    /**
     * Imposta il valore della proprietà joinedShipDate.
     * 
     * @param value
     *     allowed object is
     *     {@link JoinedShipDateType }
     *     
     */
    public void setJoinedShipDate(JoinedShipDateType value) {
        this.joinedShipDate = value;
    }

    /**
     * Gets the value of the natureOfIllnessDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the natureOfIllnessDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getNatureOfIllnessDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link NatureOfIllnessDescriptionType }
     * 
     * 
     */
    public List<NatureOfIllnessDescriptionType> getNatureOfIllnessDescription() {
        if (natureOfIllnessDescription == null) {
            natureOfIllnessDescription = new ArrayList<NatureOfIllnessDescriptionType>();
        }
        return this.natureOfIllnessDescription;
    }

    /**
     * Recupera il valore della proprietà onsetDate.
     * 
     * @return
     *     possible object is
     *     {@link OnsetDateType }
     *     
     */
    public OnsetDateType getOnsetDate() {
        return onsetDate;
    }

    /**
     * Imposta il valore della proprietà onsetDate.
     * 
     * @param value
     *     allowed object is
     *     {@link OnsetDateType }
     *     
     */
    public void setOnsetDate(OnsetDateType value) {
        this.onsetDate = value;
    }

    /**
     * Recupera il valore della proprietà reportedToMedicalOfficerIndicator.
     * 
     * @return
     *     possible object is
     *     {@link ReportedToMedicalOfficerIndicatorType }
     *     
     */
    public ReportedToMedicalOfficerIndicatorType getReportedToMedicalOfficerIndicator() {
        return reportedToMedicalOfficerIndicator;
    }

    /**
     * Imposta il valore della proprietà reportedToMedicalOfficerIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportedToMedicalOfficerIndicatorType }
     *     
     */
    public void setReportedToMedicalOfficerIndicator(ReportedToMedicalOfficerIndicatorType value) {
        this.reportedToMedicalOfficerIndicator = value;
    }

    /**
     * Gets the value of the givenTreatmentDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the givenTreatmentDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getGivenTreatmentDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link GivenTreatmentDescriptionType }
     * 
     * 
     */
    public List<GivenTreatmentDescriptionType> getGivenTreatmentDescription() {
        if (givenTreatmentDescription == null) {
            givenTreatmentDescription = new ArrayList<GivenTreatmentDescriptionType>();
        }
        return this.givenTreatmentDescription;
    }

    /**
     * Recupera il valore della proprietà stillIllIndicator.
     * 
     * @return
     *     possible object is
     *     {@link StillIllIndicatorType }
     *     
     */
    public StillIllIndicatorType getStillIllIndicator() {
        return stillIllIndicator;
    }

    /**
     * Imposta il valore della proprietà stillIllIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link StillIllIndicatorType }
     *     
     */
    public void setStillIllIndicator(StillIllIndicatorType value) {
        this.stillIllIndicator = value;
    }

    /**
     * Recupera il valore della proprietà diedIndicator.
     * 
     * @return
     *     possible object is
     *     {@link DiedIndicatorType }
     *     
     */
    public DiedIndicatorType getDiedIndicator() {
        return diedIndicator;
    }

    /**
     * Imposta il valore della proprietà diedIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link DiedIndicatorType }
     *     
     */
    public void setDiedIndicator(DiedIndicatorType value) {
        this.diedIndicator = value;
    }

    /**
     * Recupera il valore della proprietà stillOnBoardIndicator.
     * 
     * @return
     *     possible object is
     *     {@link StillOnBoardIndicatorType }
     *     
     */
    public StillOnBoardIndicatorType getStillOnBoardIndicator() {
        return stillOnBoardIndicator;
    }

    /**
     * Imposta il valore della proprietà stillOnBoardIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link StillOnBoardIndicatorType }
     *     
     */
    public void setStillOnBoardIndicator(StillOnBoardIndicatorType value) {
        this.stillOnBoardIndicator = value;
    }

    /**
     * Recupera il valore della proprietà evacuatedIndicator.
     * 
     * @return
     *     possible object is
     *     {@link EvacuatedIndicatorType }
     *     
     */
    public EvacuatedIndicatorType getEvacuatedIndicator() {
        return evacuatedIndicator;
    }

    /**
     * Imposta il valore della proprietà evacuatedIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link EvacuatedIndicatorType }
     *     
     */
    public void setEvacuatedIndicator(EvacuatedIndicatorType value) {
        this.evacuatedIndicator = value;
    }

    /**
     * Recupera il valore della proprietà buriedAtSeaIndicator.
     * 
     * @return
     *     possible object is
     *     {@link BuriedAtSeaIndicatorType }
     *     
     */
    public BuriedAtSeaIndicatorType getBuriedAtSeaIndicator() {
        return buriedAtSeaIndicator;
    }

    /**
     * Imposta il valore della proprietà buriedAtSeaIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link BuriedAtSeaIndicatorType }
     *     
     */
    public void setBuriedAtSeaIndicator(BuriedAtSeaIndicatorType value) {
        this.buriedAtSeaIndicator = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the note property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getNote().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link NoteType }
     * 
     * 
     */
    public List<NoteType> getNote() {
        if (note == null) {
            note = new ArrayList<NoteType>();
        }
        return this.note;
    }

    /**
     * Recupera il valore della proprietà person.
     * 
     * @return
     *     possible object is
     *     {@link PersonType }
     *     
     */
    public PersonType getPerson() {
        return person;
    }

    /**
     * Imposta il valore della proprietà person.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonType }
     *     
     */
    public void setPerson(PersonType value) {
        this.person = value;
    }

}
