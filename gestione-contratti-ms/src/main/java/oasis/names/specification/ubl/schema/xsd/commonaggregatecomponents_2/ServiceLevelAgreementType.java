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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AvailabilityTimePercentType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FridayAvailabilityIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumDataLossDurationMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumIncidentNotificationDurationMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MeanTimeToRecoverDurationMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MinimumDownTimeScheduleDurationMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MinimumResponseTimeDurationMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MondayAvailabilityIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SaturdayAvailabilityIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ServiceTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ServiceTypeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SundayAvailabilityIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ThursdayAvailabilityIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TuesdayAvailabilityIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.WednesdayAvailabilityIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per ServiceLevelAgreementType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ServiceLevelAgreementType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ServiceTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ServiceType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AvailabilityTimePercent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MondayAvailabilityIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TuesdayAvailabilityIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}WednesdayAvailabilityIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ThursdayAvailabilityIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}FridayAvailabilityIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SaturdayAvailabilityIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SundayAvailabilityIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MinimumResponseTimeDurationMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MinimumDownTimeScheduleDurationMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MaximumIncidentNotificationDurationMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MaximumDataLossDurationMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MeanTimeToRecoverDurationMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ServiceAvailabilityPeriod" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ServiceMaintenancePeriod" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceLevelAgreementType", propOrder = {
    "ublExtensions",
    "id",
    "serviceTypeCode",
    "serviceType",
    "availabilityTimePercent",
    "mondayAvailabilityIndicator",
    "tuesdayAvailabilityIndicator",
    "wednesdayAvailabilityIndicator",
    "thursdayAvailabilityIndicator",
    "fridayAvailabilityIndicator",
    "saturdayAvailabilityIndicator",
    "sundayAvailabilityIndicator",
    "minimumResponseTimeDurationMeasure",
    "minimumDownTimeScheduleDurationMeasure",
    "maximumIncidentNotificationDurationMeasure",
    "maximumDataLossDurationMeasure",
    "meanTimeToRecoverDurationMeasure",
    "serviceAvailabilityPeriod",
    "serviceMaintenancePeriod"
})
public class ServiceLevelAgreementType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "ServiceTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ServiceTypeCodeType serviceTypeCode;
    @XmlElement(name = "ServiceType", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<ServiceTypeType> serviceType;
    @XmlElement(name = "AvailabilityTimePercent", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected AvailabilityTimePercentType availabilityTimePercent;
    @XmlElement(name = "MondayAvailabilityIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MondayAvailabilityIndicatorType mondayAvailabilityIndicator;
    @XmlElement(name = "TuesdayAvailabilityIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TuesdayAvailabilityIndicatorType tuesdayAvailabilityIndicator;
    @XmlElement(name = "WednesdayAvailabilityIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected WednesdayAvailabilityIndicatorType wednesdayAvailabilityIndicator;
    @XmlElement(name = "ThursdayAvailabilityIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ThursdayAvailabilityIndicatorType thursdayAvailabilityIndicator;
    @XmlElement(name = "FridayAvailabilityIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected FridayAvailabilityIndicatorType fridayAvailabilityIndicator;
    @XmlElement(name = "SaturdayAvailabilityIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SaturdayAvailabilityIndicatorType saturdayAvailabilityIndicator;
    @XmlElement(name = "SundayAvailabilityIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SundayAvailabilityIndicatorType sundayAvailabilityIndicator;
    @XmlElement(name = "MinimumResponseTimeDurationMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MinimumResponseTimeDurationMeasureType minimumResponseTimeDurationMeasure;
    @XmlElement(name = "MinimumDownTimeScheduleDurationMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MinimumDownTimeScheduleDurationMeasureType minimumDownTimeScheduleDurationMeasure;
    @XmlElement(name = "MaximumIncidentNotificationDurationMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MaximumIncidentNotificationDurationMeasureType maximumIncidentNotificationDurationMeasure;
    @XmlElement(name = "MaximumDataLossDurationMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MaximumDataLossDurationMeasureType maximumDataLossDurationMeasure;
    @XmlElement(name = "MeanTimeToRecoverDurationMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MeanTimeToRecoverDurationMeasureType meanTimeToRecoverDurationMeasure;
    @XmlElement(name = "ServiceAvailabilityPeriod")
    protected List<PeriodType> serviceAvailabilityPeriod;
    @XmlElement(name = "ServiceMaintenancePeriod")
    protected List<PeriodType> serviceMaintenancePeriod;

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
     * Recupera il valore della proprietà serviceTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link ServiceTypeCodeType }
     *     
     */
    public ServiceTypeCodeType getServiceTypeCode() {
        return serviceTypeCode;
    }

    /**
     * Imposta il valore della proprietà serviceTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceTypeCodeType }
     *     
     */
    public void setServiceTypeCode(ServiceTypeCodeType value) {
        this.serviceTypeCode = value;
    }

    /**
     * Gets the value of the serviceType property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the serviceType property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getServiceType().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceTypeType }
     * 
     * 
     */
    public List<ServiceTypeType> getServiceType() {
        if (serviceType == null) {
            serviceType = new ArrayList<ServiceTypeType>();
        }
        return this.serviceType;
    }

    /**
     * Recupera il valore della proprietà availabilityTimePercent.
     * 
     * @return
     *     possible object is
     *     {@link AvailabilityTimePercentType }
     *     
     */
    public AvailabilityTimePercentType getAvailabilityTimePercent() {
        return availabilityTimePercent;
    }

    /**
     * Imposta il valore della proprietà availabilityTimePercent.
     * 
     * @param value
     *     allowed object is
     *     {@link AvailabilityTimePercentType }
     *     
     */
    public void setAvailabilityTimePercent(AvailabilityTimePercentType value) {
        this.availabilityTimePercent = value;
    }

    /**
     * Recupera il valore della proprietà mondayAvailabilityIndicator.
     * 
     * @return
     *     possible object is
     *     {@link MondayAvailabilityIndicatorType }
     *     
     */
    public MondayAvailabilityIndicatorType getMondayAvailabilityIndicator() {
        return mondayAvailabilityIndicator;
    }

    /**
     * Imposta il valore della proprietà mondayAvailabilityIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link MondayAvailabilityIndicatorType }
     *     
     */
    public void setMondayAvailabilityIndicator(MondayAvailabilityIndicatorType value) {
        this.mondayAvailabilityIndicator = value;
    }

    /**
     * Recupera il valore della proprietà tuesdayAvailabilityIndicator.
     * 
     * @return
     *     possible object is
     *     {@link TuesdayAvailabilityIndicatorType }
     *     
     */
    public TuesdayAvailabilityIndicatorType getTuesdayAvailabilityIndicator() {
        return tuesdayAvailabilityIndicator;
    }

    /**
     * Imposta il valore della proprietà tuesdayAvailabilityIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link TuesdayAvailabilityIndicatorType }
     *     
     */
    public void setTuesdayAvailabilityIndicator(TuesdayAvailabilityIndicatorType value) {
        this.tuesdayAvailabilityIndicator = value;
    }

    /**
     * Recupera il valore della proprietà wednesdayAvailabilityIndicator.
     * 
     * @return
     *     possible object is
     *     {@link WednesdayAvailabilityIndicatorType }
     *     
     */
    public WednesdayAvailabilityIndicatorType getWednesdayAvailabilityIndicator() {
        return wednesdayAvailabilityIndicator;
    }

    /**
     * Imposta il valore della proprietà wednesdayAvailabilityIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link WednesdayAvailabilityIndicatorType }
     *     
     */
    public void setWednesdayAvailabilityIndicator(WednesdayAvailabilityIndicatorType value) {
        this.wednesdayAvailabilityIndicator = value;
    }

    /**
     * Recupera il valore della proprietà thursdayAvailabilityIndicator.
     * 
     * @return
     *     possible object is
     *     {@link ThursdayAvailabilityIndicatorType }
     *     
     */
    public ThursdayAvailabilityIndicatorType getThursdayAvailabilityIndicator() {
        return thursdayAvailabilityIndicator;
    }

    /**
     * Imposta il valore della proprietà thursdayAvailabilityIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link ThursdayAvailabilityIndicatorType }
     *     
     */
    public void setThursdayAvailabilityIndicator(ThursdayAvailabilityIndicatorType value) {
        this.thursdayAvailabilityIndicator = value;
    }

    /**
     * Recupera il valore della proprietà fridayAvailabilityIndicator.
     * 
     * @return
     *     possible object is
     *     {@link FridayAvailabilityIndicatorType }
     *     
     */
    public FridayAvailabilityIndicatorType getFridayAvailabilityIndicator() {
        return fridayAvailabilityIndicator;
    }

    /**
     * Imposta il valore della proprietà fridayAvailabilityIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link FridayAvailabilityIndicatorType }
     *     
     */
    public void setFridayAvailabilityIndicator(FridayAvailabilityIndicatorType value) {
        this.fridayAvailabilityIndicator = value;
    }

    /**
     * Recupera il valore della proprietà saturdayAvailabilityIndicator.
     * 
     * @return
     *     possible object is
     *     {@link SaturdayAvailabilityIndicatorType }
     *     
     */
    public SaturdayAvailabilityIndicatorType getSaturdayAvailabilityIndicator() {
        return saturdayAvailabilityIndicator;
    }

    /**
     * Imposta il valore della proprietà saturdayAvailabilityIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link SaturdayAvailabilityIndicatorType }
     *     
     */
    public void setSaturdayAvailabilityIndicator(SaturdayAvailabilityIndicatorType value) {
        this.saturdayAvailabilityIndicator = value;
    }

    /**
     * Recupera il valore della proprietà sundayAvailabilityIndicator.
     * 
     * @return
     *     possible object is
     *     {@link SundayAvailabilityIndicatorType }
     *     
     */
    public SundayAvailabilityIndicatorType getSundayAvailabilityIndicator() {
        return sundayAvailabilityIndicator;
    }

    /**
     * Imposta il valore della proprietà sundayAvailabilityIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link SundayAvailabilityIndicatorType }
     *     
     */
    public void setSundayAvailabilityIndicator(SundayAvailabilityIndicatorType value) {
        this.sundayAvailabilityIndicator = value;
    }

    /**
     * Recupera il valore della proprietà minimumResponseTimeDurationMeasure.
     * 
     * @return
     *     possible object is
     *     {@link MinimumResponseTimeDurationMeasureType }
     *     
     */
    public MinimumResponseTimeDurationMeasureType getMinimumResponseTimeDurationMeasure() {
        return minimumResponseTimeDurationMeasure;
    }

    /**
     * Imposta il valore della proprietà minimumResponseTimeDurationMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link MinimumResponseTimeDurationMeasureType }
     *     
     */
    public void setMinimumResponseTimeDurationMeasure(MinimumResponseTimeDurationMeasureType value) {
        this.minimumResponseTimeDurationMeasure = value;
    }

    /**
     * Recupera il valore della proprietà minimumDownTimeScheduleDurationMeasure.
     * 
     * @return
     *     possible object is
     *     {@link MinimumDownTimeScheduleDurationMeasureType }
     *     
     */
    public MinimumDownTimeScheduleDurationMeasureType getMinimumDownTimeScheduleDurationMeasure() {
        return minimumDownTimeScheduleDurationMeasure;
    }

    /**
     * Imposta il valore della proprietà minimumDownTimeScheduleDurationMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link MinimumDownTimeScheduleDurationMeasureType }
     *     
     */
    public void setMinimumDownTimeScheduleDurationMeasure(MinimumDownTimeScheduleDurationMeasureType value) {
        this.minimumDownTimeScheduleDurationMeasure = value;
    }

    /**
     * Recupera il valore della proprietà maximumIncidentNotificationDurationMeasure.
     * 
     * @return
     *     possible object is
     *     {@link MaximumIncidentNotificationDurationMeasureType }
     *     
     */
    public MaximumIncidentNotificationDurationMeasureType getMaximumIncidentNotificationDurationMeasure() {
        return maximumIncidentNotificationDurationMeasure;
    }

    /**
     * Imposta il valore della proprietà maximumIncidentNotificationDurationMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumIncidentNotificationDurationMeasureType }
     *     
     */
    public void setMaximumIncidentNotificationDurationMeasure(MaximumIncidentNotificationDurationMeasureType value) {
        this.maximumIncidentNotificationDurationMeasure = value;
    }

    /**
     * Recupera il valore della proprietà maximumDataLossDurationMeasure.
     * 
     * @return
     *     possible object is
     *     {@link MaximumDataLossDurationMeasureType }
     *     
     */
    public MaximumDataLossDurationMeasureType getMaximumDataLossDurationMeasure() {
        return maximumDataLossDurationMeasure;
    }

    /**
     * Imposta il valore della proprietà maximumDataLossDurationMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumDataLossDurationMeasureType }
     *     
     */
    public void setMaximumDataLossDurationMeasure(MaximumDataLossDurationMeasureType value) {
        this.maximumDataLossDurationMeasure = value;
    }

    /**
     * Recupera il valore della proprietà meanTimeToRecoverDurationMeasure.
     * 
     * @return
     *     possible object is
     *     {@link MeanTimeToRecoverDurationMeasureType }
     *     
     */
    public MeanTimeToRecoverDurationMeasureType getMeanTimeToRecoverDurationMeasure() {
        return meanTimeToRecoverDurationMeasure;
    }

    /**
     * Imposta il valore della proprietà meanTimeToRecoverDurationMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link MeanTimeToRecoverDurationMeasureType }
     *     
     */
    public void setMeanTimeToRecoverDurationMeasure(MeanTimeToRecoverDurationMeasureType value) {
        this.meanTimeToRecoverDurationMeasure = value;
    }

    /**
     * Gets the value of the serviceAvailabilityPeriod property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the serviceAvailabilityPeriod property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getServiceAvailabilityPeriod().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PeriodType }
     * 
     * 
     */
    public List<PeriodType> getServiceAvailabilityPeriod() {
        if (serviceAvailabilityPeriod == null) {
            serviceAvailabilityPeriod = new ArrayList<PeriodType>();
        }
        return this.serviceAvailabilityPeriod;
    }

    /**
     * Gets the value of the serviceMaintenancePeriod property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the serviceMaintenancePeriod property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getServiceMaintenancePeriod().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PeriodType }
     * 
     * 
     */
    public List<PeriodType> getServiceMaintenancePeriod() {
        if (serviceMaintenancePeriod == null) {
            serviceMaintenancePeriod = new ArrayList<PeriodType>();
        }
        return this.serviceMaintenancePeriod;
    }

}
