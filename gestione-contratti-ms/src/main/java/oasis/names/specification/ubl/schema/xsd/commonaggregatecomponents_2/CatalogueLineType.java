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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ActionCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ContentUnitQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ContractSubdivisionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LifeCycleStatusCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumOrderQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MinimumOrderQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NoteType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OrderQuantityIncrementNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OrderableIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OrderableUnitType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PackLevelCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.WarrantyInformationType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per CatalogueLineType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="CatalogueLineType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ActionCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}LifeCycleStatusCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ContractSubdivision" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Note" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}OrderableIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}OrderableUnit" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ContentUnitQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}OrderQuantityIncrementNumeric" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MinimumOrderQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MaximumOrderQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}WarrantyInformation" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PackLevelCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ContractorCustomerParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SellerSupplierParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}WarrantyParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}WarrantyValidityPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}LineValidityPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ItemComparison" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ComponentRelatedItem" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AccessoryRelatedItem" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RequiredRelatedItem" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ReplacementRelatedItem" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ComplementaryRelatedItem" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ReplacedRelatedItem" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RequiredItemLocationQuantity" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DocumentReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Item"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}KeywordItemProperty" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CallForTendersLineReference" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CallForTendersDocumentReference" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CatalogueLineType", propOrder = {
    "ublExtensions",
    "id",
    "actionCode",
    "lifeCycleStatusCode",
    "contractSubdivision",
    "note",
    "orderableIndicator",
    "orderableUnit",
    "contentUnitQuantity",
    "orderQuantityIncrementNumeric",
    "minimumOrderQuantity",
    "maximumOrderQuantity",
    "warrantyInformation",
    "packLevelCode",
    "contractorCustomerParty",
    "sellerSupplierParty",
    "warrantyParty",
    "warrantyValidityPeriod",
    "lineValidityPeriod",
    "itemComparison",
    "componentRelatedItem",
    "accessoryRelatedItem",
    "requiredRelatedItem",
    "replacementRelatedItem",
    "complementaryRelatedItem",
    "replacedRelatedItem",
    "requiredItemLocationQuantity",
    "documentReference",
    "item",
    "keywordItemProperty",
    "callForTendersLineReference",
    "callForTendersDocumentReference"
})
public class CatalogueLineType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected IDType id;
    @XmlElement(name = "ActionCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ActionCodeType actionCode;
    @XmlElement(name = "LifeCycleStatusCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected LifeCycleStatusCodeType lifeCycleStatusCode;
    @XmlElement(name = "ContractSubdivision", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ContractSubdivisionType contractSubdivision;
    @XmlElement(name = "Note", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<NoteType> note;
    @XmlElement(name = "OrderableIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected OrderableIndicatorType orderableIndicator;
    @XmlElement(name = "OrderableUnit", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected OrderableUnitType orderableUnit;
    @XmlElement(name = "ContentUnitQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ContentUnitQuantityType contentUnitQuantity;
    @XmlElement(name = "OrderQuantityIncrementNumeric", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected OrderQuantityIncrementNumericType orderQuantityIncrementNumeric;
    @XmlElement(name = "MinimumOrderQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MinimumOrderQuantityType minimumOrderQuantity;
    @XmlElement(name = "MaximumOrderQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MaximumOrderQuantityType maximumOrderQuantity;
    @XmlElement(name = "WarrantyInformation", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<WarrantyInformationType> warrantyInformation;
    @XmlElement(name = "PackLevelCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PackLevelCodeType packLevelCode;
    @XmlElement(name = "ContractorCustomerParty")
    protected CustomerPartyType contractorCustomerParty;
    @XmlElement(name = "SellerSupplierParty")
    protected SupplierPartyType sellerSupplierParty;
    @XmlElement(name = "WarrantyParty")
    protected PartyType warrantyParty;
    @XmlElement(name = "WarrantyValidityPeriod")
    protected PeriodType warrantyValidityPeriod;
    @XmlElement(name = "LineValidityPeriod")
    protected PeriodType lineValidityPeriod;
    @XmlElement(name = "ItemComparison")
    protected List<ItemComparisonType> itemComparison;
    @XmlElement(name = "ComponentRelatedItem")
    protected List<RelatedItemType> componentRelatedItem;
    @XmlElement(name = "AccessoryRelatedItem")
    protected List<RelatedItemType> accessoryRelatedItem;
    @XmlElement(name = "RequiredRelatedItem")
    protected List<RelatedItemType> requiredRelatedItem;
    @XmlElement(name = "ReplacementRelatedItem")
    protected List<RelatedItemType> replacementRelatedItem;
    @XmlElement(name = "ComplementaryRelatedItem")
    protected List<RelatedItemType> complementaryRelatedItem;
    @XmlElement(name = "ReplacedRelatedItem")
    protected List<RelatedItemType> replacedRelatedItem;
    @XmlElement(name = "RequiredItemLocationQuantity")
    protected List<ItemLocationQuantityType> requiredItemLocationQuantity;
    @XmlElement(name = "DocumentReference")
    protected List<DocumentReferenceType> documentReference;
    @XmlElement(name = "Item", required = true)
    protected ItemType item;
    @XmlElement(name = "KeywordItemProperty")
    protected List<ItemPropertyType> keywordItemProperty;
    @XmlElement(name = "CallForTendersLineReference")
    protected LineReferenceType callForTendersLineReference;
    @XmlElement(name = "CallForTendersDocumentReference")
    protected DocumentReferenceType callForTendersDocumentReference;

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
     * Recupera il valore della proprietà actionCode.
     * 
     * @return
     *     possible object is
     *     {@link ActionCodeType }
     *     
     */
    public ActionCodeType getActionCode() {
        return actionCode;
    }

    /**
     * Imposta il valore della proprietà actionCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionCodeType }
     *     
     */
    public void setActionCode(ActionCodeType value) {
        this.actionCode = value;
    }

    /**
     * Recupera il valore della proprietà lifeCycleStatusCode.
     * 
     * @return
     *     possible object is
     *     {@link LifeCycleStatusCodeType }
     *     
     */
    public LifeCycleStatusCodeType getLifeCycleStatusCode() {
        return lifeCycleStatusCode;
    }

    /**
     * Imposta il valore della proprietà lifeCycleStatusCode.
     * 
     * @param value
     *     allowed object is
     *     {@link LifeCycleStatusCodeType }
     *     
     */
    public void setLifeCycleStatusCode(LifeCycleStatusCodeType value) {
        this.lifeCycleStatusCode = value;
    }

    /**
     * Recupera il valore della proprietà contractSubdivision.
     * 
     * @return
     *     possible object is
     *     {@link ContractSubdivisionType }
     *     
     */
    public ContractSubdivisionType getContractSubdivision() {
        return contractSubdivision;
    }

    /**
     * Imposta il valore della proprietà contractSubdivision.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractSubdivisionType }
     *     
     */
    public void setContractSubdivision(ContractSubdivisionType value) {
        this.contractSubdivision = value;
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
     * Recupera il valore della proprietà orderableIndicator.
     * 
     * @return
     *     possible object is
     *     {@link OrderableIndicatorType }
     *     
     */
    public OrderableIndicatorType getOrderableIndicator() {
        return orderableIndicator;
    }

    /**
     * Imposta il valore della proprietà orderableIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderableIndicatorType }
     *     
     */
    public void setOrderableIndicator(OrderableIndicatorType value) {
        this.orderableIndicator = value;
    }

    /**
     * Recupera il valore della proprietà orderableUnit.
     * 
     * @return
     *     possible object is
     *     {@link OrderableUnitType }
     *     
     */
    public OrderableUnitType getOrderableUnit() {
        return orderableUnit;
    }

    /**
     * Imposta il valore della proprietà orderableUnit.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderableUnitType }
     *     
     */
    public void setOrderableUnit(OrderableUnitType value) {
        this.orderableUnit = value;
    }

    /**
     * Recupera il valore della proprietà contentUnitQuantity.
     * 
     * @return
     *     possible object is
     *     {@link ContentUnitQuantityType }
     *     
     */
    public ContentUnitQuantityType getContentUnitQuantity() {
        return contentUnitQuantity;
    }

    /**
     * Imposta il valore della proprietà contentUnitQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentUnitQuantityType }
     *     
     */
    public void setContentUnitQuantity(ContentUnitQuantityType value) {
        this.contentUnitQuantity = value;
    }

    /**
     * Recupera il valore della proprietà orderQuantityIncrementNumeric.
     * 
     * @return
     *     possible object is
     *     {@link OrderQuantityIncrementNumericType }
     *     
     */
    public OrderQuantityIncrementNumericType getOrderQuantityIncrementNumeric() {
        return orderQuantityIncrementNumeric;
    }

    /**
     * Imposta il valore della proprietà orderQuantityIncrementNumeric.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderQuantityIncrementNumericType }
     *     
     */
    public void setOrderQuantityIncrementNumeric(OrderQuantityIncrementNumericType value) {
        this.orderQuantityIncrementNumeric = value;
    }

    /**
     * Recupera il valore della proprietà minimumOrderQuantity.
     * 
     * @return
     *     possible object is
     *     {@link MinimumOrderQuantityType }
     *     
     */
    public MinimumOrderQuantityType getMinimumOrderQuantity() {
        return minimumOrderQuantity;
    }

    /**
     * Imposta il valore della proprietà minimumOrderQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link MinimumOrderQuantityType }
     *     
     */
    public void setMinimumOrderQuantity(MinimumOrderQuantityType value) {
        this.minimumOrderQuantity = value;
    }

    /**
     * Recupera il valore della proprietà maximumOrderQuantity.
     * 
     * @return
     *     possible object is
     *     {@link MaximumOrderQuantityType }
     *     
     */
    public MaximumOrderQuantityType getMaximumOrderQuantity() {
        return maximumOrderQuantity;
    }

    /**
     * Imposta il valore della proprietà maximumOrderQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumOrderQuantityType }
     *     
     */
    public void setMaximumOrderQuantity(MaximumOrderQuantityType value) {
        this.maximumOrderQuantity = value;
    }

    /**
     * Gets the value of the warrantyInformation property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the warrantyInformation property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getWarrantyInformation().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link WarrantyInformationType }
     * 
     * 
     */
    public List<WarrantyInformationType> getWarrantyInformation() {
        if (warrantyInformation == null) {
            warrantyInformation = new ArrayList<WarrantyInformationType>();
        }
        return this.warrantyInformation;
    }

    /**
     * Recupera il valore della proprietà packLevelCode.
     * 
     * @return
     *     possible object is
     *     {@link PackLevelCodeType }
     *     
     */
    public PackLevelCodeType getPackLevelCode() {
        return packLevelCode;
    }

    /**
     * Imposta il valore della proprietà packLevelCode.
     * 
     * @param value
     *     allowed object is
     *     {@link PackLevelCodeType }
     *     
     */
    public void setPackLevelCode(PackLevelCodeType value) {
        this.packLevelCode = value;
    }

    /**
     * Recupera il valore della proprietà contractorCustomerParty.
     * 
     * @return
     *     possible object is
     *     {@link CustomerPartyType }
     *     
     */
    public CustomerPartyType getContractorCustomerParty() {
        return contractorCustomerParty;
    }

    /**
     * Imposta il valore della proprietà contractorCustomerParty.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerPartyType }
     *     
     */
    public void setContractorCustomerParty(CustomerPartyType value) {
        this.contractorCustomerParty = value;
    }

    /**
     * Recupera il valore della proprietà sellerSupplierParty.
     * 
     * @return
     *     possible object is
     *     {@link SupplierPartyType }
     *     
     */
    public SupplierPartyType getSellerSupplierParty() {
        return sellerSupplierParty;
    }

    /**
     * Imposta il valore della proprietà sellerSupplierParty.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplierPartyType }
     *     
     */
    public void setSellerSupplierParty(SupplierPartyType value) {
        this.sellerSupplierParty = value;
    }

    /**
     * Recupera il valore della proprietà warrantyParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getWarrantyParty() {
        return warrantyParty;
    }

    /**
     * Imposta il valore della proprietà warrantyParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setWarrantyParty(PartyType value) {
        this.warrantyParty = value;
    }

    /**
     * Recupera il valore della proprietà warrantyValidityPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getWarrantyValidityPeriod() {
        return warrantyValidityPeriod;
    }

    /**
     * Imposta il valore della proprietà warrantyValidityPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setWarrantyValidityPeriod(PeriodType value) {
        this.warrantyValidityPeriod = value;
    }

    /**
     * Recupera il valore della proprietà lineValidityPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getLineValidityPeriod() {
        return lineValidityPeriod;
    }

    /**
     * Imposta il valore della proprietà lineValidityPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setLineValidityPeriod(PeriodType value) {
        this.lineValidityPeriod = value;
    }

    /**
     * Gets the value of the itemComparison property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the itemComparison property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getItemComparison().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ItemComparisonType }
     * 
     * 
     */
    public List<ItemComparisonType> getItemComparison() {
        if (itemComparison == null) {
            itemComparison = new ArrayList<ItemComparisonType>();
        }
        return this.itemComparison;
    }

    /**
     * Gets the value of the componentRelatedItem property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the componentRelatedItem property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getComponentRelatedItem().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedItemType }
     * 
     * 
     */
    public List<RelatedItemType> getComponentRelatedItem() {
        if (componentRelatedItem == null) {
            componentRelatedItem = new ArrayList<RelatedItemType>();
        }
        return this.componentRelatedItem;
    }

    /**
     * Gets the value of the accessoryRelatedItem property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the accessoryRelatedItem property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAccessoryRelatedItem().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedItemType }
     * 
     * 
     */
    public List<RelatedItemType> getAccessoryRelatedItem() {
        if (accessoryRelatedItem == null) {
            accessoryRelatedItem = new ArrayList<RelatedItemType>();
        }
        return this.accessoryRelatedItem;
    }

    /**
     * Gets the value of the requiredRelatedItem property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the requiredRelatedItem property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getRequiredRelatedItem().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedItemType }
     * 
     * 
     */
    public List<RelatedItemType> getRequiredRelatedItem() {
        if (requiredRelatedItem == null) {
            requiredRelatedItem = new ArrayList<RelatedItemType>();
        }
        return this.requiredRelatedItem;
    }

    /**
     * Gets the value of the replacementRelatedItem property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the replacementRelatedItem property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getReplacementRelatedItem().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedItemType }
     * 
     * 
     */
    public List<RelatedItemType> getReplacementRelatedItem() {
        if (replacementRelatedItem == null) {
            replacementRelatedItem = new ArrayList<RelatedItemType>();
        }
        return this.replacementRelatedItem;
    }

    /**
     * Gets the value of the complementaryRelatedItem property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the complementaryRelatedItem property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getComplementaryRelatedItem().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedItemType }
     * 
     * 
     */
    public List<RelatedItemType> getComplementaryRelatedItem() {
        if (complementaryRelatedItem == null) {
            complementaryRelatedItem = new ArrayList<RelatedItemType>();
        }
        return this.complementaryRelatedItem;
    }

    /**
     * Gets the value of the replacedRelatedItem property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the replacedRelatedItem property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getReplacedRelatedItem().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedItemType }
     * 
     * 
     */
    public List<RelatedItemType> getReplacedRelatedItem() {
        if (replacedRelatedItem == null) {
            replacedRelatedItem = new ArrayList<RelatedItemType>();
        }
        return this.replacedRelatedItem;
    }

    /**
     * Gets the value of the requiredItemLocationQuantity property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the requiredItemLocationQuantity property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getRequiredItemLocationQuantity().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ItemLocationQuantityType }
     * 
     * 
     */
    public List<ItemLocationQuantityType> getRequiredItemLocationQuantity() {
        if (requiredItemLocationQuantity == null) {
            requiredItemLocationQuantity = new ArrayList<ItemLocationQuantityType>();
        }
        return this.requiredItemLocationQuantity;
    }

    /**
     * Gets the value of the documentReference property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the documentReference property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDocumentReference().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    public List<DocumentReferenceType> getDocumentReference() {
        if (documentReference == null) {
            documentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.documentReference;
    }

    /**
     * Recupera il valore della proprietà item.
     * 
     * @return
     *     possible object is
     *     {@link ItemType }
     *     
     */
    public ItemType getItem() {
        return item;
    }

    /**
     * Imposta il valore della proprietà item.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemType }
     *     
     */
    public void setItem(ItemType value) {
        this.item = value;
    }

    /**
     * Gets the value of the keywordItemProperty property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the keywordItemProperty property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getKeywordItemProperty().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ItemPropertyType }
     * 
     * 
     */
    public List<ItemPropertyType> getKeywordItemProperty() {
        if (keywordItemProperty == null) {
            keywordItemProperty = new ArrayList<ItemPropertyType>();
        }
        return this.keywordItemProperty;
    }

    /**
     * Recupera il valore della proprietà callForTendersLineReference.
     * 
     * @return
     *     possible object is
     *     {@link LineReferenceType }
     *     
     */
    public LineReferenceType getCallForTendersLineReference() {
        return callForTendersLineReference;
    }

    /**
     * Imposta il valore della proprietà callForTendersLineReference.
     * 
     * @param value
     *     allowed object is
     *     {@link LineReferenceType }
     *     
     */
    public void setCallForTendersLineReference(LineReferenceType value) {
        this.callForTendersLineReference = value;
    }

    /**
     * Recupera il valore della proprietà callForTendersDocumentReference.
     * 
     * @return
     *     possible object is
     *     {@link DocumentReferenceType }
     *     
     */
    public DocumentReferenceType getCallForTendersDocumentReference() {
        return callForTendersDocumentReference;
    }

    /**
     * Imposta il valore della proprietà callForTendersDocumentReference.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentReferenceType }
     *     
     */
    public void setCallForTendersDocumentReference(DocumentReferenceType value) {
        this.callForTendersDocumentReference = value;
    }

}
