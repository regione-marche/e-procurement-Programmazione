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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IMOGuidelinesOnBoardIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ManagementPlanImplementedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ManagementPlanOnBoardIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NoControlActionsReasonType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OtherControlActionsType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TanksExchangedQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TanksInBallastQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TanksNotExchangedQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalBallastTanksOnBoardQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalBallastWaterCapacityMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TotalBallastWaterOnBoardMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per BallastWaterSummaryType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="BallastWaterSummaryType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ManagementPlanOnBoardIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ManagementPlanImplementedIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}IMOGuidelinesOnBoardIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TotalBallastTanksOnBoardQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TanksInBallastQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TanksExchangedQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TanksNotExchangedQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TotalBallastWaterOnBoardMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TotalBallastWaterCapacityMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}OtherControlActions" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}NoControlActionsReason" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}UptakeBallastWaterTransaction" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ExchangeBallastWaterTransaction" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DischargeBallastWaterTransaction" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ResponsibleOfficerPerson" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BallastWaterSummaryType", propOrder = {
    "ublExtensions",
    "id",
    "managementPlanOnBoardIndicator",
    "managementPlanImplementedIndicator",
    "imoGuidelinesOnBoardIndicator",
    "totalBallastTanksOnBoardQuantity",
    "tanksInBallastQuantity",
    "tanksExchangedQuantity",
    "tanksNotExchangedQuantity",
    "totalBallastWaterOnBoardMeasure",
    "totalBallastWaterCapacityMeasure",
    "otherControlActions",
    "noControlActionsReason",
    "uptakeBallastWaterTransaction",
    "exchangeBallastWaterTransaction",
    "dischargeBallastWaterTransaction",
    "responsibleOfficerPerson"
})
public class BallastWaterSummaryType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "ManagementPlanOnBoardIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ManagementPlanOnBoardIndicatorType managementPlanOnBoardIndicator;
    @XmlElement(name = "ManagementPlanImplementedIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ManagementPlanImplementedIndicatorType managementPlanImplementedIndicator;
    @XmlElement(name = "IMOGuidelinesOnBoardIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IMOGuidelinesOnBoardIndicatorType imoGuidelinesOnBoardIndicator;
    @XmlElement(name = "TotalBallastTanksOnBoardQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TotalBallastTanksOnBoardQuantityType totalBallastTanksOnBoardQuantity;
    @XmlElement(name = "TanksInBallastQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TanksInBallastQuantityType tanksInBallastQuantity;
    @XmlElement(name = "TanksExchangedQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TanksExchangedQuantityType tanksExchangedQuantity;
    @XmlElement(name = "TanksNotExchangedQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TanksNotExchangedQuantityType tanksNotExchangedQuantity;
    @XmlElement(name = "TotalBallastWaterOnBoardMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TotalBallastWaterOnBoardMeasureType totalBallastWaterOnBoardMeasure;
    @XmlElement(name = "TotalBallastWaterCapacityMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TotalBallastWaterCapacityMeasureType totalBallastWaterCapacityMeasure;
    @XmlElement(name = "OtherControlActions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<OtherControlActionsType> otherControlActions;
    @XmlElement(name = "NoControlActionsReason", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<NoControlActionsReasonType> noControlActionsReason;
    @XmlElement(name = "UptakeBallastWaterTransaction")
    protected List<BallastWaterTransactionType> uptakeBallastWaterTransaction;
    @XmlElement(name = "ExchangeBallastWaterTransaction")
    protected List<BallastWaterTransactionType> exchangeBallastWaterTransaction;
    @XmlElement(name = "DischargeBallastWaterTransaction")
    protected List<BallastWaterTransactionType> dischargeBallastWaterTransaction;
    @XmlElement(name = "ResponsibleOfficerPerson")
    protected PersonType responsibleOfficerPerson;

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
     * Recupera il valore della proprietà managementPlanOnBoardIndicator.
     * 
     * @return
     *     possible object is
     *     {@link ManagementPlanOnBoardIndicatorType }
     *     
     */
    public ManagementPlanOnBoardIndicatorType getManagementPlanOnBoardIndicator() {
        return managementPlanOnBoardIndicator;
    }

    /**
     * Imposta il valore della proprietà managementPlanOnBoardIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagementPlanOnBoardIndicatorType }
     *     
     */
    public void setManagementPlanOnBoardIndicator(ManagementPlanOnBoardIndicatorType value) {
        this.managementPlanOnBoardIndicator = value;
    }

    /**
     * Recupera il valore della proprietà managementPlanImplementedIndicator.
     * 
     * @return
     *     possible object is
     *     {@link ManagementPlanImplementedIndicatorType }
     *     
     */
    public ManagementPlanImplementedIndicatorType getManagementPlanImplementedIndicator() {
        return managementPlanImplementedIndicator;
    }

    /**
     * Imposta il valore della proprietà managementPlanImplementedIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagementPlanImplementedIndicatorType }
     *     
     */
    public void setManagementPlanImplementedIndicator(ManagementPlanImplementedIndicatorType value) {
        this.managementPlanImplementedIndicator = value;
    }

    /**
     * Recupera il valore della proprietà imoGuidelinesOnBoardIndicator.
     * 
     * @return
     *     possible object is
     *     {@link IMOGuidelinesOnBoardIndicatorType }
     *     
     */
    public IMOGuidelinesOnBoardIndicatorType getIMOGuidelinesOnBoardIndicator() {
        return imoGuidelinesOnBoardIndicator;
    }

    /**
     * Imposta il valore della proprietà imoGuidelinesOnBoardIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link IMOGuidelinesOnBoardIndicatorType }
     *     
     */
    public void setIMOGuidelinesOnBoardIndicator(IMOGuidelinesOnBoardIndicatorType value) {
        this.imoGuidelinesOnBoardIndicator = value;
    }

    /**
     * Recupera il valore della proprietà totalBallastTanksOnBoardQuantity.
     * 
     * @return
     *     possible object is
     *     {@link TotalBallastTanksOnBoardQuantityType }
     *     
     */
    public TotalBallastTanksOnBoardQuantityType getTotalBallastTanksOnBoardQuantity() {
        return totalBallastTanksOnBoardQuantity;
    }

    /**
     * Imposta il valore della proprietà totalBallastTanksOnBoardQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalBallastTanksOnBoardQuantityType }
     *     
     */
    public void setTotalBallastTanksOnBoardQuantity(TotalBallastTanksOnBoardQuantityType value) {
        this.totalBallastTanksOnBoardQuantity = value;
    }

    /**
     * Recupera il valore della proprietà tanksInBallastQuantity.
     * 
     * @return
     *     possible object is
     *     {@link TanksInBallastQuantityType }
     *     
     */
    public TanksInBallastQuantityType getTanksInBallastQuantity() {
        return tanksInBallastQuantity;
    }

    /**
     * Imposta il valore della proprietà tanksInBallastQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link TanksInBallastQuantityType }
     *     
     */
    public void setTanksInBallastQuantity(TanksInBallastQuantityType value) {
        this.tanksInBallastQuantity = value;
    }

    /**
     * Recupera il valore della proprietà tanksExchangedQuantity.
     * 
     * @return
     *     possible object is
     *     {@link TanksExchangedQuantityType }
     *     
     */
    public TanksExchangedQuantityType getTanksExchangedQuantity() {
        return tanksExchangedQuantity;
    }

    /**
     * Imposta il valore della proprietà tanksExchangedQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link TanksExchangedQuantityType }
     *     
     */
    public void setTanksExchangedQuantity(TanksExchangedQuantityType value) {
        this.tanksExchangedQuantity = value;
    }

    /**
     * Recupera il valore della proprietà tanksNotExchangedQuantity.
     * 
     * @return
     *     possible object is
     *     {@link TanksNotExchangedQuantityType }
     *     
     */
    public TanksNotExchangedQuantityType getTanksNotExchangedQuantity() {
        return tanksNotExchangedQuantity;
    }

    /**
     * Imposta il valore della proprietà tanksNotExchangedQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link TanksNotExchangedQuantityType }
     *     
     */
    public void setTanksNotExchangedQuantity(TanksNotExchangedQuantityType value) {
        this.tanksNotExchangedQuantity = value;
    }

    /**
     * Recupera il valore della proprietà totalBallastWaterOnBoardMeasure.
     * 
     * @return
     *     possible object is
     *     {@link TotalBallastWaterOnBoardMeasureType }
     *     
     */
    public TotalBallastWaterOnBoardMeasureType getTotalBallastWaterOnBoardMeasure() {
        return totalBallastWaterOnBoardMeasure;
    }

    /**
     * Imposta il valore della proprietà totalBallastWaterOnBoardMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalBallastWaterOnBoardMeasureType }
     *     
     */
    public void setTotalBallastWaterOnBoardMeasure(TotalBallastWaterOnBoardMeasureType value) {
        this.totalBallastWaterOnBoardMeasure = value;
    }

    /**
     * Recupera il valore della proprietà totalBallastWaterCapacityMeasure.
     * 
     * @return
     *     possible object is
     *     {@link TotalBallastWaterCapacityMeasureType }
     *     
     */
    public TotalBallastWaterCapacityMeasureType getTotalBallastWaterCapacityMeasure() {
        return totalBallastWaterCapacityMeasure;
    }

    /**
     * Imposta il valore della proprietà totalBallastWaterCapacityMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalBallastWaterCapacityMeasureType }
     *     
     */
    public void setTotalBallastWaterCapacityMeasure(TotalBallastWaterCapacityMeasureType value) {
        this.totalBallastWaterCapacityMeasure = value;
    }

    /**
     * Gets the value of the otherControlActions property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the otherControlActions property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getOtherControlActions().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link OtherControlActionsType }
     * 
     * 
     */
    public List<OtherControlActionsType> getOtherControlActions() {
        if (otherControlActions == null) {
            otherControlActions = new ArrayList<OtherControlActionsType>();
        }
        return this.otherControlActions;
    }

    /**
     * Gets the value of the noControlActionsReason property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the noControlActionsReason property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getNoControlActionsReason().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link NoControlActionsReasonType }
     * 
     * 
     */
    public List<NoControlActionsReasonType> getNoControlActionsReason() {
        if (noControlActionsReason == null) {
            noControlActionsReason = new ArrayList<NoControlActionsReasonType>();
        }
        return this.noControlActionsReason;
    }

    /**
     * Gets the value of the uptakeBallastWaterTransaction property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the uptakeBallastWaterTransaction property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getUptakeBallastWaterTransaction().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link BallastWaterTransactionType }
     * 
     * 
     */
    public List<BallastWaterTransactionType> getUptakeBallastWaterTransaction() {
        if (uptakeBallastWaterTransaction == null) {
            uptakeBallastWaterTransaction = new ArrayList<BallastWaterTransactionType>();
        }
        return this.uptakeBallastWaterTransaction;
    }

    /**
     * Gets the value of the exchangeBallastWaterTransaction property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the exchangeBallastWaterTransaction property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getExchangeBallastWaterTransaction().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link BallastWaterTransactionType }
     * 
     * 
     */
    public List<BallastWaterTransactionType> getExchangeBallastWaterTransaction() {
        if (exchangeBallastWaterTransaction == null) {
            exchangeBallastWaterTransaction = new ArrayList<BallastWaterTransactionType>();
        }
        return this.exchangeBallastWaterTransaction;
    }

    /**
     * Gets the value of the dischargeBallastWaterTransaction property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the dischargeBallastWaterTransaction property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDischargeBallastWaterTransaction().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link BallastWaterTransactionType }
     * 
     * 
     */
    public List<BallastWaterTransactionType> getDischargeBallastWaterTransaction() {
        if (dischargeBallastWaterTransaction == null) {
            dischargeBallastWaterTransaction = new ArrayList<BallastWaterTransactionType>();
        }
        return this.dischargeBallastWaterTransaction;
    }

    /**
     * Recupera il valore della proprietà responsibleOfficerPerson.
     * 
     * @return
     *     possible object is
     *     {@link PersonType }
     *     
     */
    public PersonType getResponsibleOfficerPerson() {
        return responsibleOfficerPerson;
    }

    /**
     * Imposta il valore della proprietà responsibleOfficerPerson.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonType }
     *     
     */
    public void setResponsibleOfficerPerson(PersonType value) {
        this.responsibleOfficerPerson = value;
    }

}
