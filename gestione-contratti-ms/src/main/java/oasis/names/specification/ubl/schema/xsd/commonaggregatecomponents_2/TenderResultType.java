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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AdvertisementAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AwardDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AwardIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AwardTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HigherTenderAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LowerTenderAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReceivedElectronicTenderQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReceivedForeignTenderQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReceivedTenderQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.StartDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TenderResultCodeType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per TenderResultType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TenderResultType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AwardID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TenderResultCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Description" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AdvertisementAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AwardDate"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AwardTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ReceivedTenderQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LowerTenderAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HigherTenderAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}StartDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ReceivedElectronicTenderQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ReceivedForeignTenderQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Contract" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AwardedTenderedProject" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ContractFormalizationPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SubcontractTerms" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}WinningParty" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TenderResultType", propOrder = {
    "ublExtensions",
    "awardID",
    "tenderResultCode",
    "description",
    "advertisementAmount",
    "awardDate",
    "awardTime",
    "receivedTenderQuantity",
    "lowerTenderAmount",
    "higherTenderAmount",
    "startDate",
    "receivedElectronicTenderQuantity",
    "receivedForeignTenderQuantity",
    "contract",
    "awardedTenderedProject",
    "contractFormalizationPeriod",
    "subcontractTerms",
    "winningParty"
})
public class TenderResultType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "AwardID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected AwardIDType awardID;
    @XmlElement(name = "TenderResultCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TenderResultCodeType tenderResultCode;
    @XmlElement(name = "Description", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DescriptionType> description;
    @XmlElement(name = "AdvertisementAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected AdvertisementAmountType advertisementAmount;
    @XmlElement(name = "AwardDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected AwardDateType awardDate;
    @XmlElement(name = "AwardTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected AwardTimeType awardTime;
    @XmlElement(name = "ReceivedTenderQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ReceivedTenderQuantityType receivedTenderQuantity;
    @XmlElement(name = "LowerTenderAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected LowerTenderAmountType lowerTenderAmount;
    @XmlElement(name = "HigherTenderAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected HigherTenderAmountType higherTenderAmount;
    @XmlElement(name = "StartDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected StartDateType startDate;
    @XmlElement(name = "ReceivedElectronicTenderQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ReceivedElectronicTenderQuantityType receivedElectronicTenderQuantity;
    @XmlElement(name = "ReceivedForeignTenderQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ReceivedForeignTenderQuantityType receivedForeignTenderQuantity;
    @XmlElement(name = "Contract")
    protected ContractType contract;
    @XmlElement(name = "AwardedTenderedProject")
    protected TenderedProjectType awardedTenderedProject;
    @XmlElement(name = "ContractFormalizationPeriod")
    protected PeriodType contractFormalizationPeriod;
    @XmlElement(name = "SubcontractTerms")
    protected List<SubcontractTermsType> subcontractTerms;
    @XmlElement(name = "WinningParty")
    protected List<WinningPartyType> winningParty;

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
     * Recupera il valore della proprietà awardID.
     * 
     * @return
     *     possible object is
     *     {@link AwardIDType }
     *     
     */
    public AwardIDType getAwardID() {
        return awardID;
    }

    /**
     * Imposta il valore della proprietà awardID.
     * 
     * @param value
     *     allowed object is
     *     {@link AwardIDType }
     *     
     */
    public void setAwardID(AwardIDType value) {
        this.awardID = value;
    }

    /**
     * Recupera il valore della proprietà tenderResultCode.
     * 
     * @return
     *     possible object is
     *     {@link TenderResultCodeType }
     *     
     */
    public TenderResultCodeType getTenderResultCode() {
        return tenderResultCode;
    }

    /**
     * Imposta il valore della proprietà tenderResultCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TenderResultCodeType }
     *     
     */
    public void setTenderResultCode(TenderResultCodeType value) {
        this.tenderResultCode = value;
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
     * Recupera il valore della proprietà advertisementAmount.
     * 
     * @return
     *     possible object is
     *     {@link AdvertisementAmountType }
     *     
     */
    public AdvertisementAmountType getAdvertisementAmount() {
        return advertisementAmount;
    }

    /**
     * Imposta il valore della proprietà advertisementAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link AdvertisementAmountType }
     *     
     */
    public void setAdvertisementAmount(AdvertisementAmountType value) {
        this.advertisementAmount = value;
    }

    /**
     * Recupera il valore della proprietà awardDate.
     * 
     * @return
     *     possible object is
     *     {@link AwardDateType }
     *     
     */
    public AwardDateType getAwardDate() {
        return awardDate;
    }

    /**
     * Imposta il valore della proprietà awardDate.
     * 
     * @param value
     *     allowed object is
     *     {@link AwardDateType }
     *     
     */
    public void setAwardDate(AwardDateType value) {
        this.awardDate = value;
    }

    /**
     * Recupera il valore della proprietà awardTime.
     * 
     * @return
     *     possible object is
     *     {@link AwardTimeType }
     *     
     */
    public AwardTimeType getAwardTime() {
        return awardTime;
    }

    /**
     * Imposta il valore della proprietà awardTime.
     * 
     * @param value
     *     allowed object is
     *     {@link AwardTimeType }
     *     
     */
    public void setAwardTime(AwardTimeType value) {
        this.awardTime = value;
    }

    /**
     * Recupera il valore della proprietà receivedTenderQuantity.
     * 
     * @return
     *     possible object is
     *     {@link ReceivedTenderQuantityType }
     *     
     */
    public ReceivedTenderQuantityType getReceivedTenderQuantity() {
        return receivedTenderQuantity;
    }

    /**
     * Imposta il valore della proprietà receivedTenderQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceivedTenderQuantityType }
     *     
     */
    public void setReceivedTenderQuantity(ReceivedTenderQuantityType value) {
        this.receivedTenderQuantity = value;
    }

    /**
     * Recupera il valore della proprietà lowerTenderAmount.
     * 
     * @return
     *     possible object is
     *     {@link LowerTenderAmountType }
     *     
     */
    public LowerTenderAmountType getLowerTenderAmount() {
        return lowerTenderAmount;
    }

    /**
     * Imposta il valore della proprietà lowerTenderAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link LowerTenderAmountType }
     *     
     */
    public void setLowerTenderAmount(LowerTenderAmountType value) {
        this.lowerTenderAmount = value;
    }

    /**
     * Recupera il valore della proprietà higherTenderAmount.
     * 
     * @return
     *     possible object is
     *     {@link HigherTenderAmountType }
     *     
     */
    public HigherTenderAmountType getHigherTenderAmount() {
        return higherTenderAmount;
    }

    /**
     * Imposta il valore della proprietà higherTenderAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link HigherTenderAmountType }
     *     
     */
    public void setHigherTenderAmount(HigherTenderAmountType value) {
        this.higherTenderAmount = value;
    }

    /**
     * Recupera il valore della proprietà startDate.
     * 
     * @return
     *     possible object is
     *     {@link StartDateType }
     *     
     */
    public StartDateType getStartDate() {
        return startDate;
    }

    /**
     * Imposta il valore della proprietà startDate.
     * 
     * @param value
     *     allowed object is
     *     {@link StartDateType }
     *     
     */
    public void setStartDate(StartDateType value) {
        this.startDate = value;
    }

    /**
     * Recupera il valore della proprietà receivedElectronicTenderQuantity.
     * 
     * @return
     *     possible object is
     *     {@link ReceivedElectronicTenderQuantityType }
     *     
     */
    public ReceivedElectronicTenderQuantityType getReceivedElectronicTenderQuantity() {
        return receivedElectronicTenderQuantity;
    }

    /**
     * Imposta il valore della proprietà receivedElectronicTenderQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceivedElectronicTenderQuantityType }
     *     
     */
    public void setReceivedElectronicTenderQuantity(ReceivedElectronicTenderQuantityType value) {
        this.receivedElectronicTenderQuantity = value;
    }

    /**
     * Recupera il valore della proprietà receivedForeignTenderQuantity.
     * 
     * @return
     *     possible object is
     *     {@link ReceivedForeignTenderQuantityType }
     *     
     */
    public ReceivedForeignTenderQuantityType getReceivedForeignTenderQuantity() {
        return receivedForeignTenderQuantity;
    }

    /**
     * Imposta il valore della proprietà receivedForeignTenderQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceivedForeignTenderQuantityType }
     *     
     */
    public void setReceivedForeignTenderQuantity(ReceivedForeignTenderQuantityType value) {
        this.receivedForeignTenderQuantity = value;
    }

    /**
     * Recupera il valore della proprietà contract.
     * 
     * @return
     *     possible object is
     *     {@link ContractType }
     *     
     */
    public ContractType getContract() {
        return contract;
    }

    /**
     * Imposta il valore della proprietà contract.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractType }
     *     
     */
    public void setContract(ContractType value) {
        this.contract = value;
    }

    /**
     * Recupera il valore della proprietà awardedTenderedProject.
     * 
     * @return
     *     possible object is
     *     {@link TenderedProjectType }
     *     
     */
    public TenderedProjectType getAwardedTenderedProject() {
        return awardedTenderedProject;
    }

    /**
     * Imposta il valore della proprietà awardedTenderedProject.
     * 
     * @param value
     *     allowed object is
     *     {@link TenderedProjectType }
     *     
     */
    public void setAwardedTenderedProject(TenderedProjectType value) {
        this.awardedTenderedProject = value;
    }

    /**
     * Recupera il valore della proprietà contractFormalizationPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getContractFormalizationPeriod() {
        return contractFormalizationPeriod;
    }

    /**
     * Imposta il valore della proprietà contractFormalizationPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setContractFormalizationPeriod(PeriodType value) {
        this.contractFormalizationPeriod = value;
    }

    /**
     * Gets the value of the subcontractTerms property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the subcontractTerms property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSubcontractTerms().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link SubcontractTermsType }
     * 
     * 
     */
    public List<SubcontractTermsType> getSubcontractTerms() {
        if (subcontractTerms == null) {
            subcontractTerms = new ArrayList<SubcontractTermsType>();
        }
        return this.subcontractTerms;
    }

    /**
     * Gets the value of the winningParty property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the winningParty property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getWinningParty().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link WinningPartyType }
     * 
     * 
     */
    public List<WinningPartyType> getWinningParty() {
        if (winningParty == null) {
            winningParty = new ArrayList<WinningPartyType>();
        }
        return this.winningParty;
    }

}
