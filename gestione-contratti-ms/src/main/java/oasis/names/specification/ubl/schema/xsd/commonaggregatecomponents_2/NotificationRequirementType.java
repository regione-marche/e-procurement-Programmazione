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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NotificationTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PostEventNotificationDurationMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PreEventNotificationDurationMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per NotificationRequirementType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="NotificationRequirementType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}NotificationTypeCode"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PostEventNotificationDurationMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PreEventNotificationDurationMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}NotifyParty" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}NotificationPeriod" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}NotificationLocation" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NotificationRequirementType", propOrder = {
    "ublExtensions",
    "notificationTypeCode",
    "postEventNotificationDurationMeasure",
    "preEventNotificationDurationMeasure",
    "notifyParty",
    "notificationPeriod",
    "notificationLocation"
})
public class NotificationRequirementType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "NotificationTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected NotificationTypeCodeType notificationTypeCode;
    @XmlElement(name = "PostEventNotificationDurationMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PostEventNotificationDurationMeasureType postEventNotificationDurationMeasure;
    @XmlElement(name = "PreEventNotificationDurationMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PreEventNotificationDurationMeasureType preEventNotificationDurationMeasure;
    @XmlElement(name = "NotifyParty")
    protected List<PartyType> notifyParty;
    @XmlElement(name = "NotificationPeriod")
    protected List<PeriodType> notificationPeriod;
    @XmlElement(name = "NotificationLocation")
    protected List<LocationType> notificationLocation;

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
     * Recupera il valore della proprietà notificationTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link NotificationTypeCodeType }
     *     
     */
    public NotificationTypeCodeType getNotificationTypeCode() {
        return notificationTypeCode;
    }

    /**
     * Imposta il valore della proprietà notificationTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationTypeCodeType }
     *     
     */
    public void setNotificationTypeCode(NotificationTypeCodeType value) {
        this.notificationTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà postEventNotificationDurationMeasure.
     * 
     * @return
     *     possible object is
     *     {@link PostEventNotificationDurationMeasureType }
     *     
     */
    public PostEventNotificationDurationMeasureType getPostEventNotificationDurationMeasure() {
        return postEventNotificationDurationMeasure;
    }

    /**
     * Imposta il valore della proprietà postEventNotificationDurationMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link PostEventNotificationDurationMeasureType }
     *     
     */
    public void setPostEventNotificationDurationMeasure(PostEventNotificationDurationMeasureType value) {
        this.postEventNotificationDurationMeasure = value;
    }

    /**
     * Recupera il valore della proprietà preEventNotificationDurationMeasure.
     * 
     * @return
     *     possible object is
     *     {@link PreEventNotificationDurationMeasureType }
     *     
     */
    public PreEventNotificationDurationMeasureType getPreEventNotificationDurationMeasure() {
        return preEventNotificationDurationMeasure;
    }

    /**
     * Imposta il valore della proprietà preEventNotificationDurationMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link PreEventNotificationDurationMeasureType }
     *     
     */
    public void setPreEventNotificationDurationMeasure(PreEventNotificationDurationMeasureType value) {
        this.preEventNotificationDurationMeasure = value;
    }

    /**
     * Gets the value of the notifyParty property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the notifyParty property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getNotifyParty().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PartyType }
     * 
     * 
     */
    public List<PartyType> getNotifyParty() {
        if (notifyParty == null) {
            notifyParty = new ArrayList<PartyType>();
        }
        return this.notifyParty;
    }

    /**
     * Gets the value of the notificationPeriod property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the notificationPeriod property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getNotificationPeriod().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PeriodType }
     * 
     * 
     */
    public List<PeriodType> getNotificationPeriod() {
        if (notificationPeriod == null) {
            notificationPeriod = new ArrayList<PeriodType>();
        }
        return this.notificationPeriod;
    }

    /**
     * Gets the value of the notificationLocation property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the notificationLocation property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getNotificationLocation().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link LocationType }
     * 
     * 
     */
    public List<LocationType> getNotificationLocation() {
        if (notificationLocation == null) {
            notificationLocation = new ArrayList<LocationType>();
        }
        return this.notificationLocation;
    }

}
