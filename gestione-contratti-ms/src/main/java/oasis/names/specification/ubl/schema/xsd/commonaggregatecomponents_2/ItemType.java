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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BrandNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CatalogueIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HazardousRiskIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.KeywordType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ModelNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PackQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PackSizeNumericType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per ItemType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ItemType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Description" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PackQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PackSizeNumeric" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CatalogueIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Name" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HazardousRiskIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AdditionalInformation" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Keyword" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}BrandName" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ModelName" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}BuyersItemIdentification" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SellersItemIdentification" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ManufacturersItemIdentification" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}StandardItemIdentification" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CatalogueItemIdentification" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AdditionalItemIdentification" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CatalogueDocumentReference" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ItemSpecificationDocumentReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OriginCountry" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CommodityClassification" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TransactionConditions" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}HazardousItem" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ClassifiedTaxCategory" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AdditionalItemProperty" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ManufacturerParty" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}InformationContentProviderParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OriginAddress" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ItemInstance" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Certificate" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Dimension" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemType", propOrder = {
    "ublExtensions",
    "description",
    "packQuantity",
    "packSizeNumeric",
    "catalogueIndicator",
    "name",
    "hazardousRiskIndicator",
    "additionalInformation",
    "keyword",
    "brandName",
    "modelName",
    "buyersItemIdentification",
    "sellersItemIdentification",
    "manufacturersItemIdentification",
    "standardItemIdentification",
    "catalogueItemIdentification",
    "additionalItemIdentification",
    "catalogueDocumentReference",
    "itemSpecificationDocumentReference",
    "originCountry",
    "commodityClassification",
    "transactionConditions",
    "hazardousItem",
    "classifiedTaxCategory",
    "additionalItemProperty",
    "manufacturerParty",
    "informationContentProviderParty",
    "originAddress",
    "itemInstance",
    "certificate",
    "dimension"
})
public class ItemType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "Description", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DescriptionType> description;
    @XmlElement(name = "PackQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PackQuantityType packQuantity;
    @XmlElement(name = "PackSizeNumeric", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PackSizeNumericType packSizeNumeric;
    @XmlElement(name = "CatalogueIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CatalogueIndicatorType catalogueIndicator;
    @XmlElement(name = "Name", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NameType name;
    @XmlElement(name = "HazardousRiskIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected HazardousRiskIndicatorType hazardousRiskIndicator;
    @XmlElement(name = "AdditionalInformation", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<AdditionalInformationType> additionalInformation;
    @XmlElement(name = "Keyword", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<KeywordType> keyword;
    @XmlElement(name = "BrandName", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<BrandNameType> brandName;
    @XmlElement(name = "ModelName", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<ModelNameType> modelName;
    @XmlElement(name = "BuyersItemIdentification")
    protected ItemIdentificationType buyersItemIdentification;
    @XmlElement(name = "SellersItemIdentification")
    protected ItemIdentificationType sellersItemIdentification;
    @XmlElement(name = "ManufacturersItemIdentification")
    protected List<ItemIdentificationType> manufacturersItemIdentification;
    @XmlElement(name = "StandardItemIdentification")
    protected ItemIdentificationType standardItemIdentification;
    @XmlElement(name = "CatalogueItemIdentification")
    protected ItemIdentificationType catalogueItemIdentification;
    @XmlElement(name = "AdditionalItemIdentification")
    protected List<ItemIdentificationType> additionalItemIdentification;
    @XmlElement(name = "CatalogueDocumentReference")
    protected DocumentReferenceType catalogueDocumentReference;
    @XmlElement(name = "ItemSpecificationDocumentReference")
    protected List<DocumentReferenceType> itemSpecificationDocumentReference;
    @XmlElement(name = "OriginCountry")
    protected CountryType originCountry;
    @XmlElement(name = "CommodityClassification")
    protected List<CommodityClassificationType> commodityClassification;
    @XmlElement(name = "TransactionConditions")
    protected List<TransactionConditionsType> transactionConditions;
    @XmlElement(name = "HazardousItem")
    protected List<HazardousItemType> hazardousItem;
    @XmlElement(name = "ClassifiedTaxCategory")
    protected List<TaxCategoryType> classifiedTaxCategory;
    @XmlElement(name = "AdditionalItemProperty")
    protected List<ItemPropertyType> additionalItemProperty;
    @XmlElement(name = "ManufacturerParty")
    protected List<PartyType> manufacturerParty;
    @XmlElement(name = "InformationContentProviderParty")
    protected PartyType informationContentProviderParty;
    @XmlElement(name = "OriginAddress")
    protected List<AddressType> originAddress;
    @XmlElement(name = "ItemInstance")
    protected List<ItemInstanceType> itemInstance;
    @XmlElement(name = "Certificate")
    protected List<CertificateType> certificate;
    @XmlElement(name = "Dimension")
    protected List<DimensionType> dimension;

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
     * Recupera il valore della proprietà packQuantity.
     * 
     * @return
     *     possible object is
     *     {@link PackQuantityType }
     *     
     */
    public PackQuantityType getPackQuantity() {
        return packQuantity;
    }

    /**
     * Imposta il valore della proprietà packQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link PackQuantityType }
     *     
     */
    public void setPackQuantity(PackQuantityType value) {
        this.packQuantity = value;
    }

    /**
     * Recupera il valore della proprietà packSizeNumeric.
     * 
     * @return
     *     possible object is
     *     {@link PackSizeNumericType }
     *     
     */
    public PackSizeNumericType getPackSizeNumeric() {
        return packSizeNumeric;
    }

    /**
     * Imposta il valore della proprietà packSizeNumeric.
     * 
     * @param value
     *     allowed object is
     *     {@link PackSizeNumericType }
     *     
     */
    public void setPackSizeNumeric(PackSizeNumericType value) {
        this.packSizeNumeric = value;
    }

    /**
     * Recupera il valore della proprietà catalogueIndicator.
     * 
     * @return
     *     possible object is
     *     {@link CatalogueIndicatorType }
     *     
     */
    public CatalogueIndicatorType getCatalogueIndicator() {
        return catalogueIndicator;
    }

    /**
     * Imposta il valore della proprietà catalogueIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link CatalogueIndicatorType }
     *     
     */
    public void setCatalogueIndicator(CatalogueIndicatorType value) {
        this.catalogueIndicator = value;
    }

    /**
     * Recupera il valore della proprietà name.
     * 
     * @return
     *     possible object is
     *     {@link NameType }
     *     
     */
    public NameType getName() {
        return name;
    }

    /**
     * Imposta il valore della proprietà name.
     * 
     * @param value
     *     allowed object is
     *     {@link NameType }
     *     
     */
    public void setName(NameType value) {
        this.name = value;
    }

    /**
     * Recupera il valore della proprietà hazardousRiskIndicator.
     * 
     * @return
     *     possible object is
     *     {@link HazardousRiskIndicatorType }
     *     
     */
    public HazardousRiskIndicatorType getHazardousRiskIndicator() {
        return hazardousRiskIndicator;
    }

    /**
     * Imposta il valore della proprietà hazardousRiskIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link HazardousRiskIndicatorType }
     *     
     */
    public void setHazardousRiskIndicator(HazardousRiskIndicatorType value) {
        this.hazardousRiskIndicator = value;
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
     * Gets the value of the keyword property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the keyword property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getKeyword().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link KeywordType }
     * 
     * 
     */
    public List<KeywordType> getKeyword() {
        if (keyword == null) {
            keyword = new ArrayList<KeywordType>();
        }
        return this.keyword;
    }

    /**
     * Gets the value of the brandName property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the brandName property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getBrandName().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link BrandNameType }
     * 
     * 
     */
    public List<BrandNameType> getBrandName() {
        if (brandName == null) {
            brandName = new ArrayList<BrandNameType>();
        }
        return this.brandName;
    }

    /**
     * Gets the value of the modelName property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the modelName property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getModelName().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ModelNameType }
     * 
     * 
     */
    public List<ModelNameType> getModelName() {
        if (modelName == null) {
            modelName = new ArrayList<ModelNameType>();
        }
        return this.modelName;
    }

    /**
     * Recupera il valore della proprietà buyersItemIdentification.
     * 
     * @return
     *     possible object is
     *     {@link ItemIdentificationType }
     *     
     */
    public ItemIdentificationType getBuyersItemIdentification() {
        return buyersItemIdentification;
    }

    /**
     * Imposta il valore della proprietà buyersItemIdentification.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemIdentificationType }
     *     
     */
    public void setBuyersItemIdentification(ItemIdentificationType value) {
        this.buyersItemIdentification = value;
    }

    /**
     * Recupera il valore della proprietà sellersItemIdentification.
     * 
     * @return
     *     possible object is
     *     {@link ItemIdentificationType }
     *     
     */
    public ItemIdentificationType getSellersItemIdentification() {
        return sellersItemIdentification;
    }

    /**
     * Imposta il valore della proprietà sellersItemIdentification.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemIdentificationType }
     *     
     */
    public void setSellersItemIdentification(ItemIdentificationType value) {
        this.sellersItemIdentification = value;
    }

    /**
     * Gets the value of the manufacturersItemIdentification property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the manufacturersItemIdentification property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getManufacturersItemIdentification().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ItemIdentificationType }
     * 
     * 
     */
    public List<ItemIdentificationType> getManufacturersItemIdentification() {
        if (manufacturersItemIdentification == null) {
            manufacturersItemIdentification = new ArrayList<ItemIdentificationType>();
        }
        return this.manufacturersItemIdentification;
    }

    /**
     * Recupera il valore della proprietà standardItemIdentification.
     * 
     * @return
     *     possible object is
     *     {@link ItemIdentificationType }
     *     
     */
    public ItemIdentificationType getStandardItemIdentification() {
        return standardItemIdentification;
    }

    /**
     * Imposta il valore della proprietà standardItemIdentification.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemIdentificationType }
     *     
     */
    public void setStandardItemIdentification(ItemIdentificationType value) {
        this.standardItemIdentification = value;
    }

    /**
     * Recupera il valore della proprietà catalogueItemIdentification.
     * 
     * @return
     *     possible object is
     *     {@link ItemIdentificationType }
     *     
     */
    public ItemIdentificationType getCatalogueItemIdentification() {
        return catalogueItemIdentification;
    }

    /**
     * Imposta il valore della proprietà catalogueItemIdentification.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemIdentificationType }
     *     
     */
    public void setCatalogueItemIdentification(ItemIdentificationType value) {
        this.catalogueItemIdentification = value;
    }

    /**
     * Gets the value of the additionalItemIdentification property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the additionalItemIdentification property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAdditionalItemIdentification().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ItemIdentificationType }
     * 
     * 
     */
    public List<ItemIdentificationType> getAdditionalItemIdentification() {
        if (additionalItemIdentification == null) {
            additionalItemIdentification = new ArrayList<ItemIdentificationType>();
        }
        return this.additionalItemIdentification;
    }

    /**
     * Recupera il valore della proprietà catalogueDocumentReference.
     * 
     * @return
     *     possible object is
     *     {@link DocumentReferenceType }
     *     
     */
    public DocumentReferenceType getCatalogueDocumentReference() {
        return catalogueDocumentReference;
    }

    /**
     * Imposta il valore della proprietà catalogueDocumentReference.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentReferenceType }
     *     
     */
    public void setCatalogueDocumentReference(DocumentReferenceType value) {
        this.catalogueDocumentReference = value;
    }

    /**
     * Gets the value of the itemSpecificationDocumentReference property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the itemSpecificationDocumentReference property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getItemSpecificationDocumentReference().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    public List<DocumentReferenceType> getItemSpecificationDocumentReference() {
        if (itemSpecificationDocumentReference == null) {
            itemSpecificationDocumentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.itemSpecificationDocumentReference;
    }

    /**
     * Recupera il valore della proprietà originCountry.
     * 
     * @return
     *     possible object is
     *     {@link CountryType }
     *     
     */
    public CountryType getOriginCountry() {
        return originCountry;
    }

    /**
     * Imposta il valore della proprietà originCountry.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryType }
     *     
     */
    public void setOriginCountry(CountryType value) {
        this.originCountry = value;
    }

    /**
     * Gets the value of the commodityClassification property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the commodityClassification property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCommodityClassification().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CommodityClassificationType }
     * 
     * 
     */
    public List<CommodityClassificationType> getCommodityClassification() {
        if (commodityClassification == null) {
            commodityClassification = new ArrayList<CommodityClassificationType>();
        }
        return this.commodityClassification;
    }

    /**
     * Gets the value of the transactionConditions property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the transactionConditions property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTransactionConditions().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TransactionConditionsType }
     * 
     * 
     */
    public List<TransactionConditionsType> getTransactionConditions() {
        if (transactionConditions == null) {
            transactionConditions = new ArrayList<TransactionConditionsType>();
        }
        return this.transactionConditions;
    }

    /**
     * Gets the value of the hazardousItem property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the hazardousItem property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getHazardousItem().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link HazardousItemType }
     * 
     * 
     */
    public List<HazardousItemType> getHazardousItem() {
        if (hazardousItem == null) {
            hazardousItem = new ArrayList<HazardousItemType>();
        }
        return this.hazardousItem;
    }

    /**
     * Gets the value of the classifiedTaxCategory property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the classifiedTaxCategory property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getClassifiedTaxCategory().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TaxCategoryType }
     * 
     * 
     */
    public List<TaxCategoryType> getClassifiedTaxCategory() {
        if (classifiedTaxCategory == null) {
            classifiedTaxCategory = new ArrayList<TaxCategoryType>();
        }
        return this.classifiedTaxCategory;
    }

    /**
     * Gets the value of the additionalItemProperty property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the additionalItemProperty property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAdditionalItemProperty().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ItemPropertyType }
     * 
     * 
     */
    public List<ItemPropertyType> getAdditionalItemProperty() {
        if (additionalItemProperty == null) {
            additionalItemProperty = new ArrayList<ItemPropertyType>();
        }
        return this.additionalItemProperty;
    }

    /**
     * Gets the value of the manufacturerParty property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the manufacturerParty property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getManufacturerParty().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PartyType }
     * 
     * 
     */
    public List<PartyType> getManufacturerParty() {
        if (manufacturerParty == null) {
            manufacturerParty = new ArrayList<PartyType>();
        }
        return this.manufacturerParty;
    }

    /**
     * Recupera il valore della proprietà informationContentProviderParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getInformationContentProviderParty() {
        return informationContentProviderParty;
    }

    /**
     * Imposta il valore della proprietà informationContentProviderParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setInformationContentProviderParty(PartyType value) {
        this.informationContentProviderParty = value;
    }

    /**
     * Gets the value of the originAddress property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the originAddress property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getOriginAddress().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AddressType }
     * 
     * 
     */
    public List<AddressType> getOriginAddress() {
        if (originAddress == null) {
            originAddress = new ArrayList<AddressType>();
        }
        return this.originAddress;
    }

    /**
     * Gets the value of the itemInstance property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the itemInstance property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getItemInstance().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ItemInstanceType }
     * 
     * 
     */
    public List<ItemInstanceType> getItemInstance() {
        if (itemInstance == null) {
            itemInstance = new ArrayList<ItemInstanceType>();
        }
        return this.itemInstance;
    }

    /**
     * Gets the value of the certificate property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the certificate property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCertificate().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CertificateType }
     * 
     * 
     */
    public List<CertificateType> getCertificate() {
        if (certificate == null) {
            certificate = new ArrayList<CertificateType>();
        }
        return this.certificate;
    }

    /**
     * Gets the value of the dimension property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the dimension property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDimension().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DimensionType }
     * 
     * 
     */
    public List<DimensionType> getDimension() {
        if (dimension == null) {
            dimension = new ArrayList<DimensionType>();
        }
        return this.dimension;
    }

}
