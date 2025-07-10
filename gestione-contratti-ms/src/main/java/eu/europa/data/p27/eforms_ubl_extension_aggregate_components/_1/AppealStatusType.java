//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.AppealPreviousStageIDType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.AppealStageCodeType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.AppealStageIDType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.RemedyAmountType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.WithdrawnAppealDateType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.WithdrawnAppealIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.WithdrawnAppealReasonsType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FeeAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TitleType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.URIType;


/**
 * &lt;p&gt;Classe Java per AppealStatusType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="AppealStatusType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Date"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Title" maxOccurs="unbounded"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Description" maxOccurs="unbounded"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}URI" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}FeeAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}AppealStageCode"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}AppealStageID"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}AppealPreviousStageID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}RemedyAmount"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}WithdrawnAppealIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}WithdrawnAppealDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}WithdrawnAppealReasons" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}AppealDecision" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}AppealIrregularity" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}AppealProcessingParty"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}AppealRemedy" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}AppealedItem" maxOccurs="unbounded"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}AppealingParty" maxOccurs="unbounded"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppealStatusType", propOrder = {
    "date",
    "title",
    "description",
    "uri",
    "feeAmount",
    "appealStageCode",
    "appealStageID",
    "appealPreviousStageID",
    "remedyAmount",
    "withdrawnAppealIndicator",
    "withdrawnAppealDate",
    "withdrawnAppealReasons",
    "appealDecision",
    "appealIrregularity",
    "appealProcessingParty",
    "appealRemedy",
    "appealedItem",
    "appealingParty"
})
public class AppealStatusType {

    @XmlElement(name = "Date", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected DateType date;
    @XmlElement(name = "Title", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected List<TitleType> title;
    @XmlElement(name = "Description", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected List<DescriptionType> description;
    @XmlElement(name = "URI", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected URIType uri;
    @XmlElement(name = "FeeAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected FeeAmountType feeAmount;
    @XmlElement(name = "AppealStageCode", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1", required = true)
    protected AppealStageCodeType appealStageCode;
    @XmlElement(name = "AppealStageID", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1", required = true)
    protected AppealStageIDType appealStageID;
    @XmlElement(name = "AppealPreviousStageID", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected AppealPreviousStageIDType appealPreviousStageID;
    @XmlElement(name = "RemedyAmount", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1", required = true)
    protected RemedyAmountType remedyAmount;
    @XmlElement(name = "WithdrawnAppealIndicator", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected WithdrawnAppealIndicatorType withdrawnAppealIndicator;
    @XmlElement(name = "WithdrawnAppealDate", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected WithdrawnAppealDateType withdrawnAppealDate;
    @XmlElement(name = "WithdrawnAppealReasons", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected List<WithdrawnAppealReasonsType> withdrawnAppealReasons;
    @XmlElement(name = "AppealDecision")
    protected List<AppealDecisionType> appealDecision;
    @XmlElement(name = "AppealIrregularity")
    protected List<AppealIrregularityType> appealIrregularity;
    @XmlElement(name = "AppealProcessingParty", required = true)
    protected AppealProcessingPartyType appealProcessingParty;
    @XmlElement(name = "AppealRemedy")
    protected List<AppealRemedyType> appealRemedy;
    @XmlElement(name = "AppealedItem", required = true)
    protected List<AppealedItemType> appealedItem;
    @XmlElement(name = "AppealingParty", required = true)
    protected List<AppealingPartyType> appealingParty;

    /**
     * Recupera il valore della proprietà date.
     * 
     * @return
     *     possible object is
     *     {@link DateType }
     *     
     */
    public DateType getDate() {
        return date;
    }

    /**
     * Imposta il valore della proprietà date.
     * 
     * @param value
     *     allowed object is
     *     {@link DateType }
     *     
     */
    public void setDate(DateType value) {
        this.date = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the title property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTitle().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TitleType }
     * 
     * 
     */
    public List<TitleType> getTitle() {
        if (title == null) {
            title = new ArrayList<TitleType>();
        }
        return this.title;
    }

    /**
     * Gets the value of the description property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the description property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DescriptionType }
     * 
     * 
     */
    public List<DescriptionType> getDescription() {
        if (description == null) {
            description = new ArrayList<DescriptionType>();
        }
        return this.description;
    }

    /**
     * Recupera il valore della proprietà uri.
     * 
     * @return
     *     possible object is
     *     {@link URIType }
     *     
     */
    public URIType getURI() {
        return uri;
    }

    /**
     * Imposta il valore della proprietà uri.
     * 
     * @param value
     *     allowed object is
     *     {@link URIType }
     *     
     */
    public void setURI(URIType value) {
        this.uri = value;
    }

    /**
     * Recupera il valore della proprietà feeAmount.
     * 
     * @return
     *     possible object is
     *     {@link FeeAmountType }
     *     
     */
    public FeeAmountType getFeeAmount() {
        return feeAmount;
    }

    /**
     * Imposta il valore della proprietà feeAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link FeeAmountType }
     *     
     */
    public void setFeeAmount(FeeAmountType value) {
        this.feeAmount = value;
    }

    /**
     * Recupera il valore della proprietà appealStageCode.
     * 
     * @return
     *     possible object is
     *     {@link AppealStageCodeType }
     *     
     */
    public AppealStageCodeType getAppealStageCode() {
        return appealStageCode;
    }

    /**
     * Imposta il valore della proprietà appealStageCode.
     * 
     * @param value
     *     allowed object is
     *     {@link AppealStageCodeType }
     *     
     */
    public void setAppealStageCode(AppealStageCodeType value) {
        this.appealStageCode = value;
    }

    /**
     * Recupera il valore della proprietà appealStageID.
     * 
     * @return
     *     possible object is
     *     {@link AppealStageIDType }
     *     
     */
    public AppealStageIDType getAppealStageID() {
        return appealStageID;
    }

    /**
     * Imposta il valore della proprietà appealStageID.
     * 
     * @param value
     *     allowed object is
     *     {@link AppealStageIDType }
     *     
     */
    public void setAppealStageID(AppealStageIDType value) {
        this.appealStageID = value;
    }

    /**
     * Recupera il valore della proprietà appealPreviousStageID.
     * 
     * @return
     *     possible object is
     *     {@link AppealPreviousStageIDType }
     *     
     */
    public AppealPreviousStageIDType getAppealPreviousStageID() {
        return appealPreviousStageID;
    }

    /**
     * Imposta il valore della proprietà appealPreviousStageID.
     * 
     * @param value
     *     allowed object is
     *     {@link AppealPreviousStageIDType }
     *     
     */
    public void setAppealPreviousStageID(AppealPreviousStageIDType value) {
        this.appealPreviousStageID = value;
    }

    /**
     * Recupera il valore della proprietà remedyAmount.
     * 
     * @return
     *     possible object is
     *     {@link RemedyAmountType }
     *     
     */
    public RemedyAmountType getRemedyAmount() {
        return remedyAmount;
    }

    /**
     * Imposta il valore della proprietà remedyAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link RemedyAmountType }
     *     
     */
    public void setRemedyAmount(RemedyAmountType value) {
        this.remedyAmount = value;
    }

    /**
     * Recupera il valore della proprietà withdrawnAppealIndicator.
     * 
     * @return
     *     possible object is
     *     {@link WithdrawnAppealIndicatorType }
     *     
     */
    public WithdrawnAppealIndicatorType getWithdrawnAppealIndicator() {
        return withdrawnAppealIndicator;
    }

    /**
     * Imposta il valore della proprietà withdrawnAppealIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link WithdrawnAppealIndicatorType }
     *     
     */
    public void setWithdrawnAppealIndicator(WithdrawnAppealIndicatorType value) {
        this.withdrawnAppealIndicator = value;
    }

    /**
     * Recupera il valore della proprietà withdrawnAppealDate.
     * 
     * @return
     *     possible object is
     *     {@link WithdrawnAppealDateType }
     *     
     */
    public WithdrawnAppealDateType getWithdrawnAppealDate() {
        return withdrawnAppealDate;
    }

    /**
     * Imposta il valore della proprietà withdrawnAppealDate.
     * 
     * @param value
     *     allowed object is
     *     {@link WithdrawnAppealDateType }
     *     
     */
    public void setWithdrawnAppealDate(WithdrawnAppealDateType value) {
        this.withdrawnAppealDate = value;
    }

    /**
     * Gets the value of the withdrawnAppealReasons property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the withdrawnAppealReasons property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getWithdrawnAppealReasons().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link WithdrawnAppealReasonsType }
     * 
     * 
     */
    public List<WithdrawnAppealReasonsType> getWithdrawnAppealReasons() {
        if (withdrawnAppealReasons == null) {
            withdrawnAppealReasons = new ArrayList<WithdrawnAppealReasonsType>();
        }
        return this.withdrawnAppealReasons;
    }

    /**
     * Gets the value of the appealDecision property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the appealDecision property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAppealDecision().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AppealDecisionType }
     * 
     * 
     */
    public List<AppealDecisionType> getAppealDecision() {
        if (appealDecision == null) {
            appealDecision = new ArrayList<AppealDecisionType>();
        }
        return this.appealDecision;
    }

    /**
     * Gets the value of the appealIrregularity property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the appealIrregularity property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAppealIrregularity().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AppealIrregularityType }
     * 
     * 
     */
    public List<AppealIrregularityType> getAppealIrregularity() {
        if (appealIrregularity == null) {
            appealIrregularity = new ArrayList<AppealIrregularityType>();
        }
        return this.appealIrregularity;
    }

    /**
     * Recupera il valore della proprietà appealProcessingParty.
     * 
     * @return
     *     possible object is
     *     {@link AppealProcessingPartyType }
     *     
     */
    public AppealProcessingPartyType getAppealProcessingParty() {
        return appealProcessingParty;
    }

    /**
     * Imposta il valore della proprietà appealProcessingParty.
     * 
     * @param value
     *     allowed object is
     *     {@link AppealProcessingPartyType }
     *     
     */
    public void setAppealProcessingParty(AppealProcessingPartyType value) {
        this.appealProcessingParty = value;
    }

    /**
     * Gets the value of the appealRemedy property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the appealRemedy property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAppealRemedy().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AppealRemedyType }
     * 
     * 
     */
    public List<AppealRemedyType> getAppealRemedy() {
        if (appealRemedy == null) {
            appealRemedy = new ArrayList<AppealRemedyType>();
        }
        return this.appealRemedy;
    }

    /**
     * Gets the value of the appealedItem property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the appealedItem property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAppealedItem().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AppealedItemType }
     * 
     * 
     */
    public List<AppealedItemType> getAppealedItem() {
        if (appealedItem == null) {
            appealedItem = new ArrayList<AppealedItemType>();
        }
        return this.appealedItem;
    }

    /**
     * Gets the value of the appealingParty property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the appealingParty property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAppealingParty().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AppealingPartyType }
     * 
     * 
     */
    public List<AppealingPartyType> getAppealingParty() {
        if (appealingParty == null) {
            appealingParty = new ArrayList<AppealingPartyType>();
        }
        return this.appealingParty;
    }

}
