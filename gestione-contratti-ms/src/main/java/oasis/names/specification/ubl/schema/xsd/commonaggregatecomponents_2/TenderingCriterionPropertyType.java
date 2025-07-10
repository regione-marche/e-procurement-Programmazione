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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CertificationLevelDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CopyQualityTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExpectedAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExpectedCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExpectedDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExpectedIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExpectedIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExpectedURIType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExpectedValueNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumValueNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MinimumAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MinimumQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MinimumValueNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TranslationTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValueCurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValueDataTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValueUnitCodeType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per TenderingCriterionPropertyType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TenderingCriterionPropertyType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Name" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Description" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ValueDataTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ValueUnitCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ValueCurrencyCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ExpectedAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ExpectedID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ExpectedIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ExpectedCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ExpectedValueNumeric" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ExpectedDescription" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ExpectedURI" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MaximumAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MinimumAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MaximumValueNumeric" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MinimumValueNumeric" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MaximumQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MinimumQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TranslationTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CertificationLevelDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CopyQualityTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ApplicablePeriod" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TemplateEvidence" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TenderingCriterionPropertyType", propOrder = {
    "ublExtensions",
    "id",
    "name",
    "description",
    "typeCode",
    "valueDataTypeCode",
    "valueUnitCode",
    "valueCurrencyCode",
    "expectedAmount",
    "expectedID",
    "expectedIndicator",
    "expectedCode",
    "expectedValueNumeric",
    "expectedDescription",
    "expectedURI",
    "maximumAmount",
    "minimumAmount",
    "maximumValueNumeric",
    "minimumValueNumeric",
    "maximumQuantity",
    "minimumQuantity",
    "translationTypeCode",
    "certificationLevelDescription",
    "copyQualityTypeCode",
    "applicablePeriod",
    "templateEvidence"
})
public class TenderingCriterionPropertyType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "Name", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NameType name;
    @XmlElement(name = "Description", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DescriptionType> description;
    @XmlElement(name = "TypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TypeCodeType typeCode;
    @XmlElement(name = "ValueDataTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ValueDataTypeCodeType valueDataTypeCode;
    @XmlElement(name = "ValueUnitCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ValueUnitCodeType valueUnitCode;
    @XmlElement(name = "ValueCurrencyCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ValueCurrencyCodeType valueCurrencyCode;
    @XmlElement(name = "ExpectedAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ExpectedAmountType expectedAmount;
    @XmlElement(name = "ExpectedID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ExpectedIDType expectedID;
    @XmlElement(name = "ExpectedIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ExpectedIndicatorType expectedIndicator;
    @XmlElement(name = "ExpectedCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ExpectedCodeType expectedCode;
    @XmlElement(name = "ExpectedValueNumeric", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ExpectedValueNumericType expectedValueNumeric;
    @XmlElement(name = "ExpectedDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ExpectedDescriptionType expectedDescription;
    @XmlElement(name = "ExpectedURI", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ExpectedURIType expectedURI;
    @XmlElement(name = "MaximumAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MaximumAmountType maximumAmount;
    @XmlElement(name = "MinimumAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MinimumAmountType minimumAmount;
    @XmlElement(name = "MaximumValueNumeric", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MaximumValueNumericType maximumValueNumeric;
    @XmlElement(name = "MinimumValueNumeric", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MinimumValueNumericType minimumValueNumeric;
    @XmlElement(name = "MaximumQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MaximumQuantityType maximumQuantity;
    @XmlElement(name = "MinimumQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MinimumQuantityType minimumQuantity;
    @XmlElement(name = "TranslationTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TranslationTypeCodeType translationTypeCode;
    @XmlElement(name = "CertificationLevelDescription", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<CertificationLevelDescriptionType> certificationLevelDescription;
    @XmlElement(name = "CopyQualityTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CopyQualityTypeCodeType copyQualityTypeCode;
    @XmlElement(name = "ApplicablePeriod")
    protected List<PeriodType> applicablePeriod;
    @XmlElement(name = "TemplateEvidence")
    protected List<EvidenceType> templateEvidence;

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
     * Recupera il valore della proprietà typeCode.
     * 
     * @return
     *     possible object is
     *     {@link TypeCodeType }
     *     
     */
    public TypeCodeType getTypeCode() {
        return typeCode;
    }

    /**
     * Imposta il valore della proprietà typeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeCodeType }
     *     
     */
    public void setTypeCode(TypeCodeType value) {
        this.typeCode = value;
    }

    /**
     * Recupera il valore della proprietà valueDataTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link ValueDataTypeCodeType }
     *     
     */
    public ValueDataTypeCodeType getValueDataTypeCode() {
        return valueDataTypeCode;
    }

    /**
     * Imposta il valore della proprietà valueDataTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ValueDataTypeCodeType }
     *     
     */
    public void setValueDataTypeCode(ValueDataTypeCodeType value) {
        this.valueDataTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà valueUnitCode.
     * 
     * @return
     *     possible object is
     *     {@link ValueUnitCodeType }
     *     
     */
    public ValueUnitCodeType getValueUnitCode() {
        return valueUnitCode;
    }

    /**
     * Imposta il valore della proprietà valueUnitCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ValueUnitCodeType }
     *     
     */
    public void setValueUnitCode(ValueUnitCodeType value) {
        this.valueUnitCode = value;
    }

    /**
     * Recupera il valore della proprietà valueCurrencyCode.
     * 
     * @return
     *     possible object is
     *     {@link ValueCurrencyCodeType }
     *     
     */
    public ValueCurrencyCodeType getValueCurrencyCode() {
        return valueCurrencyCode;
    }

    /**
     * Imposta il valore della proprietà valueCurrencyCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ValueCurrencyCodeType }
     *     
     */
    public void setValueCurrencyCode(ValueCurrencyCodeType value) {
        this.valueCurrencyCode = value;
    }

    /**
     * Recupera il valore della proprietà expectedAmount.
     * 
     * @return
     *     possible object is
     *     {@link ExpectedAmountType }
     *     
     */
    public ExpectedAmountType getExpectedAmount() {
        return expectedAmount;
    }

    /**
     * Imposta il valore della proprietà expectedAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpectedAmountType }
     *     
     */
    public void setExpectedAmount(ExpectedAmountType value) {
        this.expectedAmount = value;
    }

    /**
     * Recupera il valore della proprietà expectedID.
     * 
     * @return
     *     possible object is
     *     {@link ExpectedIDType }
     *     
     */
    public ExpectedIDType getExpectedID() {
        return expectedID;
    }

    /**
     * Imposta il valore della proprietà expectedID.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpectedIDType }
     *     
     */
    public void setExpectedID(ExpectedIDType value) {
        this.expectedID = value;
    }

    /**
     * Recupera il valore della proprietà expectedIndicator.
     * 
     * @return
     *     possible object is
     *     {@link ExpectedIndicatorType }
     *     
     */
    public ExpectedIndicatorType getExpectedIndicator() {
        return expectedIndicator;
    }

    /**
     * Imposta il valore della proprietà expectedIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpectedIndicatorType }
     *     
     */
    public void setExpectedIndicator(ExpectedIndicatorType value) {
        this.expectedIndicator = value;
    }

    /**
     * Recupera il valore della proprietà expectedCode.
     * 
     * @return
     *     possible object is
     *     {@link ExpectedCodeType }
     *     
     */
    public ExpectedCodeType getExpectedCode() {
        return expectedCode;
    }

    /**
     * Imposta il valore della proprietà expectedCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpectedCodeType }
     *     
     */
    public void setExpectedCode(ExpectedCodeType value) {
        this.expectedCode = value;
    }

    /**
     * Recupera il valore della proprietà expectedValueNumeric.
     * 
     * @return
     *     possible object is
     *     {@link ExpectedValueNumericType }
     *     
     */
    public ExpectedValueNumericType getExpectedValueNumeric() {
        return expectedValueNumeric;
    }

    /**
     * Imposta il valore della proprietà expectedValueNumeric.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpectedValueNumericType }
     *     
     */
    public void setExpectedValueNumeric(ExpectedValueNumericType value) {
        this.expectedValueNumeric = value;
    }

    /**
     * Recupera il valore della proprietà expectedDescription.
     * 
     * @return
     *     possible object is
     *     {@link ExpectedDescriptionType }
     *     
     */
    public ExpectedDescriptionType getExpectedDescription() {
        return expectedDescription;
    }

    /**
     * Imposta il valore della proprietà expectedDescription.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpectedDescriptionType }
     *     
     */
    public void setExpectedDescription(ExpectedDescriptionType value) {
        this.expectedDescription = value;
    }

    /**
     * Recupera il valore della proprietà expectedURI.
     * 
     * @return
     *     possible object is
     *     {@link ExpectedURIType }
     *     
     */
    public ExpectedURIType getExpectedURI() {
        return expectedURI;
    }

    /**
     * Imposta il valore della proprietà expectedURI.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpectedURIType }
     *     
     */
    public void setExpectedURI(ExpectedURIType value) {
        this.expectedURI = value;
    }

    /**
     * Recupera il valore della proprietà maximumAmount.
     * 
     * @return
     *     possible object is
     *     {@link MaximumAmountType }
     *     
     */
    public MaximumAmountType getMaximumAmount() {
        return maximumAmount;
    }

    /**
     * Imposta il valore della proprietà maximumAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumAmountType }
     *     
     */
    public void setMaximumAmount(MaximumAmountType value) {
        this.maximumAmount = value;
    }

    /**
     * Recupera il valore della proprietà minimumAmount.
     * 
     * @return
     *     possible object is
     *     {@link MinimumAmountType }
     *     
     */
    public MinimumAmountType getMinimumAmount() {
        return minimumAmount;
    }

    /**
     * Imposta il valore della proprietà minimumAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link MinimumAmountType }
     *     
     */
    public void setMinimumAmount(MinimumAmountType value) {
        this.minimumAmount = value;
    }

    /**
     * Recupera il valore della proprietà maximumValueNumeric.
     * 
     * @return
     *     possible object is
     *     {@link MaximumValueNumericType }
     *     
     */
    public MaximumValueNumericType getMaximumValueNumeric() {
        return maximumValueNumeric;
    }

    /**
     * Imposta il valore della proprietà maximumValueNumeric.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumValueNumericType }
     *     
     */
    public void setMaximumValueNumeric(MaximumValueNumericType value) {
        this.maximumValueNumeric = value;
    }

    /**
     * Recupera il valore della proprietà minimumValueNumeric.
     * 
     * @return
     *     possible object is
     *     {@link MinimumValueNumericType }
     *     
     */
    public MinimumValueNumericType getMinimumValueNumeric() {
        return minimumValueNumeric;
    }

    /**
     * Imposta il valore della proprietà minimumValueNumeric.
     * 
     * @param value
     *     allowed object is
     *     {@link MinimumValueNumericType }
     *     
     */
    public void setMinimumValueNumeric(MinimumValueNumericType value) {
        this.minimumValueNumeric = value;
    }

    /**
     * Recupera il valore della proprietà maximumQuantity.
     * 
     * @return
     *     possible object is
     *     {@link MaximumQuantityType }
     *     
     */
    public MaximumQuantityType getMaximumQuantity() {
        return maximumQuantity;
    }

    /**
     * Imposta il valore della proprietà maximumQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumQuantityType }
     *     
     */
    public void setMaximumQuantity(MaximumQuantityType value) {
        this.maximumQuantity = value;
    }

    /**
     * Recupera il valore della proprietà minimumQuantity.
     * 
     * @return
     *     possible object is
     *     {@link MinimumQuantityType }
     *     
     */
    public MinimumQuantityType getMinimumQuantity() {
        return minimumQuantity;
    }

    /**
     * Imposta il valore della proprietà minimumQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link MinimumQuantityType }
     *     
     */
    public void setMinimumQuantity(MinimumQuantityType value) {
        this.minimumQuantity = value;
    }

    /**
     * Recupera il valore della proprietà translationTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link TranslationTypeCodeType }
     *     
     */
    public TranslationTypeCodeType getTranslationTypeCode() {
        return translationTypeCode;
    }

    /**
     * Imposta il valore della proprietà translationTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TranslationTypeCodeType }
     *     
     */
    public void setTranslationTypeCode(TranslationTypeCodeType value) {
        this.translationTypeCode = value;
    }

    /**
     * Gets the value of the certificationLevelDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the certificationLevelDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCertificationLevelDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CertificationLevelDescriptionType }
     * 
     * 
     */
    public List<CertificationLevelDescriptionType> getCertificationLevelDescription() {
        if (certificationLevelDescription == null) {
            certificationLevelDescription = new ArrayList<CertificationLevelDescriptionType>();
        }
        return this.certificationLevelDescription;
    }

    /**
     * Recupera il valore della proprietà copyQualityTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link CopyQualityTypeCodeType }
     *     
     */
    public CopyQualityTypeCodeType getCopyQualityTypeCode() {
        return copyQualityTypeCode;
    }

    /**
     * Imposta il valore della proprietà copyQualityTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link CopyQualityTypeCodeType }
     *     
     */
    public void setCopyQualityTypeCode(CopyQualityTypeCodeType value) {
        this.copyQualityTypeCode = value;
    }

    /**
     * Gets the value of the applicablePeriod property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the applicablePeriod property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getApplicablePeriod().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PeriodType }
     * 
     * 
     */
    public List<PeriodType> getApplicablePeriod() {
        if (applicablePeriod == null) {
            applicablePeriod = new ArrayList<PeriodType>();
        }
        return this.applicablePeriod;
    }

    /**
     * Gets the value of the templateEvidence property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the templateEvidence property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTemplateEvidence().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link EvidenceType }
     * 
     * 
     */
    public List<EvidenceType> getTemplateEvidence() {
        if (templateEvidence == null) {
            templateEvidence = new ArrayList<EvidenceType>();
        }
        return this.templateEvidence;
    }

}
