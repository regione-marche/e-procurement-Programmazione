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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AdditionalMattersDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CurrentOperatingSecurityLevelCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ISSCAbsenceReasonType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ISSCExpiryDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SSPOnBoardIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SSPSecurityMeasuresAppliedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValidISSCIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per ISPSRequirementsType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ISPSRequirementsType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ValidISSCIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ISSCAbsenceReason" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ISSCExpiryDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SSPOnBoardIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SSPSecurityMeasuresAppliedIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CurrentOperatingSecurityLevelCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AdditionalMattersDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AdditionalSecurityMeasure" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PortCallRecord" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ShipToShipActivityRecord" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ReportLocation" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ISSCIssuerParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SecurityOfficerPerson" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ISPSRequirementsType", propOrder = {
    "ublExtensions",
    "id",
    "validISSCIndicator",
    "isscAbsenceReason",
    "isscExpiryDate",
    "sspOnBoardIndicator",
    "sspSecurityMeasuresAppliedIndicator",
    "currentOperatingSecurityLevelCode",
    "additionalMattersDescription",
    "additionalSecurityMeasure",
    "portCallRecord",
    "shipToShipActivityRecord",
    "reportLocation",
    "isscIssuerParty",
    "securityOfficerPerson"
})
public class ISPSRequirementsType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected IDType id;
    @XmlElement(name = "ValidISSCIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ValidISSCIndicatorType validISSCIndicator;
    @XmlElement(name = "ISSCAbsenceReason", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<ISSCAbsenceReasonType> isscAbsenceReason;
    @XmlElement(name = "ISSCExpiryDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ISSCExpiryDateType isscExpiryDate;
    @XmlElement(name = "SSPOnBoardIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SSPOnBoardIndicatorType sspOnBoardIndicator;
    @XmlElement(name = "SSPSecurityMeasuresAppliedIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SSPSecurityMeasuresAppliedIndicatorType sspSecurityMeasuresAppliedIndicator;
    @XmlElement(name = "CurrentOperatingSecurityLevelCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CurrentOperatingSecurityLevelCodeType currentOperatingSecurityLevelCode;
    @XmlElement(name = "AdditionalMattersDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<AdditionalMattersDescriptionType> additionalMattersDescription;
    @XmlElement(name = "AdditionalSecurityMeasure")
    protected List<SecurityMeasureType> additionalSecurityMeasure;
    @XmlElement(name = "PortCallRecord")
    protected List<PortCallRecordType> portCallRecord;
    @XmlElement(name = "ShipToShipActivityRecord")
    protected List<ShipToShipActivityRecordType> shipToShipActivityRecord;
    @XmlElement(name = "ReportLocation")
    protected LocationType reportLocation;
    @XmlElement(name = "ISSCIssuerParty")
    protected PartyType isscIssuerParty;
    @XmlElement(name = "SecurityOfficerPerson")
    protected PersonType securityOfficerPerson;

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
     * Recupera il valore della proprietà validISSCIndicator.
     * 
     * @return
     *     possible object is
     *     {@link ValidISSCIndicatorType }
     *     
     */
    public ValidISSCIndicatorType getValidISSCIndicator() {
        return validISSCIndicator;
    }

    /**
     * Imposta il valore della proprietà validISSCIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidISSCIndicatorType }
     *     
     */
    public void setValidISSCIndicator(ValidISSCIndicatorType value) {
        this.validISSCIndicator = value;
    }

    /**
     * Gets the value of the isscAbsenceReason property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the isscAbsenceReason property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getISSCAbsenceReason().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ISSCAbsenceReasonType }
     * 
     * 
     */
    public List<ISSCAbsenceReasonType> getISSCAbsenceReason() {
        if (isscAbsenceReason == null) {
            isscAbsenceReason = new ArrayList<ISSCAbsenceReasonType>();
        }
        return this.isscAbsenceReason;
    }

    /**
     * Recupera il valore della proprietà isscExpiryDate.
     * 
     * @return
     *     possible object is
     *     {@link ISSCExpiryDateType }
     *     
     */
    public ISSCExpiryDateType getISSCExpiryDate() {
        return isscExpiryDate;
    }

    /**
     * Imposta il valore della proprietà isscExpiryDate.
     * 
     * @param value
     *     allowed object is
     *     {@link ISSCExpiryDateType }
     *     
     */
    public void setISSCExpiryDate(ISSCExpiryDateType value) {
        this.isscExpiryDate = value;
    }

    /**
     * Recupera il valore della proprietà sspOnBoardIndicator.
     * 
     * @return
     *     possible object is
     *     {@link SSPOnBoardIndicatorType }
     *     
     */
    public SSPOnBoardIndicatorType getSSPOnBoardIndicator() {
        return sspOnBoardIndicator;
    }

    /**
     * Imposta il valore della proprietà sspOnBoardIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link SSPOnBoardIndicatorType }
     *     
     */
    public void setSSPOnBoardIndicator(SSPOnBoardIndicatorType value) {
        this.sspOnBoardIndicator = value;
    }

    /**
     * Recupera il valore della proprietà sspSecurityMeasuresAppliedIndicator.
     * 
     * @return
     *     possible object is
     *     {@link SSPSecurityMeasuresAppliedIndicatorType }
     *     
     */
    public SSPSecurityMeasuresAppliedIndicatorType getSSPSecurityMeasuresAppliedIndicator() {
        return sspSecurityMeasuresAppliedIndicator;
    }

    /**
     * Imposta il valore della proprietà sspSecurityMeasuresAppliedIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link SSPSecurityMeasuresAppliedIndicatorType }
     *     
     */
    public void setSSPSecurityMeasuresAppliedIndicator(SSPSecurityMeasuresAppliedIndicatorType value) {
        this.sspSecurityMeasuresAppliedIndicator = value;
    }

    /**
     * Recupera il valore della proprietà currentOperatingSecurityLevelCode.
     * 
     * @return
     *     possible object is
     *     {@link CurrentOperatingSecurityLevelCodeType }
     *     
     */
    public CurrentOperatingSecurityLevelCodeType getCurrentOperatingSecurityLevelCode() {
        return currentOperatingSecurityLevelCode;
    }

    /**
     * Imposta il valore della proprietà currentOperatingSecurityLevelCode.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrentOperatingSecurityLevelCodeType }
     *     
     */
    public void setCurrentOperatingSecurityLevelCode(CurrentOperatingSecurityLevelCodeType value) {
        this.currentOperatingSecurityLevelCode = value;
    }

    /**
     * Gets the value of the additionalMattersDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the additionalMattersDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAdditionalMattersDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AdditionalMattersDescriptionType }
     * 
     * 
     */
    public List<AdditionalMattersDescriptionType> getAdditionalMattersDescription() {
        if (additionalMattersDescription == null) {
            additionalMattersDescription = new ArrayList<AdditionalMattersDescriptionType>();
        }
        return this.additionalMattersDescription;
    }

    /**
     * Gets the value of the additionalSecurityMeasure property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the additionalSecurityMeasure property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAdditionalSecurityMeasure().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link SecurityMeasureType }
     * 
     * 
     */
    public List<SecurityMeasureType> getAdditionalSecurityMeasure() {
        if (additionalSecurityMeasure == null) {
            additionalSecurityMeasure = new ArrayList<SecurityMeasureType>();
        }
        return this.additionalSecurityMeasure;
    }

    /**
     * Gets the value of the portCallRecord property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the portCallRecord property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPortCallRecord().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PortCallRecordType }
     * 
     * 
     */
    public List<PortCallRecordType> getPortCallRecord() {
        if (portCallRecord == null) {
            portCallRecord = new ArrayList<PortCallRecordType>();
        }
        return this.portCallRecord;
    }

    /**
     * Gets the value of the shipToShipActivityRecord property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the shipToShipActivityRecord property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getShipToShipActivityRecord().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ShipToShipActivityRecordType }
     * 
     * 
     */
    public List<ShipToShipActivityRecordType> getShipToShipActivityRecord() {
        if (shipToShipActivityRecord == null) {
            shipToShipActivityRecord = new ArrayList<ShipToShipActivityRecordType>();
        }
        return this.shipToShipActivityRecord;
    }

    /**
     * Recupera il valore della proprietà reportLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    public LocationType getReportLocation() {
        return reportLocation;
    }

    /**
     * Imposta il valore della proprietà reportLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setReportLocation(LocationType value) {
        this.reportLocation = value;
    }

    /**
     * Recupera il valore della proprietà isscIssuerParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getISSCIssuerParty() {
        return isscIssuerParty;
    }

    /**
     * Imposta il valore della proprietà isscIssuerParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setISSCIssuerParty(PartyType value) {
        this.isscIssuerParty = value;
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

}
