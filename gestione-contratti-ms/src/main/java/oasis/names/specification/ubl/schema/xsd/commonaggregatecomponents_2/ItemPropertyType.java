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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ImportanceCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ListValueType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NameCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TestMethodType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValueQualifierType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValueQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValueType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per ItemPropertyType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ItemPropertyType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Name"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}NameCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TestMethod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Value" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ValueQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ValueQualifier" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ImportanceCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ListValue" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}UsabilityPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ItemPropertyGroup" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RangeDimension" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ItemPropertyRange" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}StandardPropertyIdentification" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SubItemProperty" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemPropertyType", propOrder = {
    "ublExtensions",
    "id",
    "name",
    "nameCode",
    "testMethod",
    "value",
    "valueQuantity",
    "valueQualifier",
    "importanceCode",
    "listValue",
    "usabilityPeriod",
    "itemPropertyGroup",
    "rangeDimension",
    "itemPropertyRange",
    "standardPropertyIdentification",
    "subItemProperty"
})
public class ItemPropertyType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "Name", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected NameType name;
    @XmlElement(name = "NameCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NameCodeType nameCode;
    @XmlElement(name = "TestMethod", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TestMethodType testMethod;
    @XmlElement(name = "Value", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ValueType value;
    @XmlElement(name = "ValueQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ValueQuantityType valueQuantity;
    @XmlElement(name = "ValueQualifier", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<ValueQualifierType> valueQualifier;
    @XmlElement(name = "ImportanceCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ImportanceCodeType importanceCode;
    @XmlElement(name = "ListValue", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<ListValueType> listValue;
    @XmlElement(name = "UsabilityPeriod")
    protected PeriodType usabilityPeriod;
    @XmlElement(name = "ItemPropertyGroup")
    protected List<ItemPropertyGroupType> itemPropertyGroup;
    @XmlElement(name = "RangeDimension")
    protected DimensionType rangeDimension;
    @XmlElement(name = "ItemPropertyRange")
    protected ItemPropertyRangeType itemPropertyRange;
    @XmlElement(name = "StandardPropertyIdentification")
    protected PropertyIdentificationType standardPropertyIdentification;
    @XmlElement(name = "SubItemProperty")
    protected List<ItemPropertyType> subItemProperty;

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
     * Recupera il valore della proprietà nameCode.
     * 
     * @return
     *     possible object is
     *     {@link NameCodeType }
     *     
     */
    public NameCodeType getNameCode() {
        return nameCode;
    }

    /**
     * Imposta il valore della proprietà nameCode.
     * 
     * @param value
     *     allowed object is
     *     {@link NameCodeType }
     *     
     */
    public void setNameCode(NameCodeType value) {
        this.nameCode = value;
    }

    /**
     * Recupera il valore della proprietà testMethod.
     * 
     * @return
     *     possible object is
     *     {@link TestMethodType }
     *     
     */
    public TestMethodType getTestMethod() {
        return testMethod;
    }

    /**
     * Imposta il valore della proprietà testMethod.
     * 
     * @param value
     *     allowed object is
     *     {@link TestMethodType }
     *     
     */
    public void setTestMethod(TestMethodType value) {
        this.testMethod = value;
    }

    /**
     * Recupera il valore della proprietà value.
     * 
     * @return
     *     possible object is
     *     {@link ValueType }
     *     
     */
    public ValueType getValue() {
        return value;
    }

    /**
     * Imposta il valore della proprietà value.
     * 
     * @param value
     *     allowed object is
     *     {@link ValueType }
     *     
     */
    public void setValue(ValueType value) {
        this.value = value;
    }

    /**
     * Recupera il valore della proprietà valueQuantity.
     * 
     * @return
     *     possible object is
     *     {@link ValueQuantityType }
     *     
     */
    public ValueQuantityType getValueQuantity() {
        return valueQuantity;
    }

    /**
     * Imposta il valore della proprietà valueQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link ValueQuantityType }
     *     
     */
    public void setValueQuantity(ValueQuantityType value) {
        this.valueQuantity = value;
    }

    /**
     * Gets the value of the valueQualifier property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the valueQualifier property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getValueQualifier().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ValueQualifierType }
     * 
     * 
     */
    public List<ValueQualifierType> getValueQualifier() {
        if (valueQualifier == null) {
            valueQualifier = new ArrayList<ValueQualifierType>();
        }
        return this.valueQualifier;
    }

    /**
     * Recupera il valore della proprietà importanceCode.
     * 
     * @return
     *     possible object is
     *     {@link ImportanceCodeType }
     *     
     */
    public ImportanceCodeType getImportanceCode() {
        return importanceCode;
    }

    /**
     * Imposta il valore della proprietà importanceCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ImportanceCodeType }
     *     
     */
    public void setImportanceCode(ImportanceCodeType value) {
        this.importanceCode = value;
    }

    /**
     * Gets the value of the listValue property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the listValue property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getListValue().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ListValueType }
     * 
     * 
     */
    public List<ListValueType> getListValue() {
        if (listValue == null) {
            listValue = new ArrayList<ListValueType>();
        }
        return this.listValue;
    }

    /**
     * Recupera il valore della proprietà usabilityPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getUsabilityPeriod() {
        return usabilityPeriod;
    }

    /**
     * Imposta il valore della proprietà usabilityPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setUsabilityPeriod(PeriodType value) {
        this.usabilityPeriod = value;
    }

    /**
     * Gets the value of the itemPropertyGroup property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the itemPropertyGroup property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getItemPropertyGroup().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ItemPropertyGroupType }
     * 
     * 
     */
    public List<ItemPropertyGroupType> getItemPropertyGroup() {
        if (itemPropertyGroup == null) {
            itemPropertyGroup = new ArrayList<ItemPropertyGroupType>();
        }
        return this.itemPropertyGroup;
    }

    /**
     * Recupera il valore della proprietà rangeDimension.
     * 
     * @return
     *     possible object is
     *     {@link DimensionType }
     *     
     */
    public DimensionType getRangeDimension() {
        return rangeDimension;
    }

    /**
     * Imposta il valore della proprietà rangeDimension.
     * 
     * @param value
     *     allowed object is
     *     {@link DimensionType }
     *     
     */
    public void setRangeDimension(DimensionType value) {
        this.rangeDimension = value;
    }

    /**
     * Recupera il valore della proprietà itemPropertyRange.
     * 
     * @return
     *     possible object is
     *     {@link ItemPropertyRangeType }
     *     
     */
    public ItemPropertyRangeType getItemPropertyRange() {
        return itemPropertyRange;
    }

    /**
     * Imposta il valore della proprietà itemPropertyRange.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemPropertyRangeType }
     *     
     */
    public void setItemPropertyRange(ItemPropertyRangeType value) {
        this.itemPropertyRange = value;
    }

    /**
     * Recupera il valore della proprietà standardPropertyIdentification.
     * 
     * @return
     *     possible object is
     *     {@link PropertyIdentificationType }
     *     
     */
    public PropertyIdentificationType getStandardPropertyIdentification() {
        return standardPropertyIdentification;
    }

    /**
     * Imposta il valore della proprietà standardPropertyIdentification.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertyIdentificationType }
     *     
     */
    public void setStandardPropertyIdentification(PropertyIdentificationType value) {
        this.standardPropertyIdentification = value;
    }

    /**
     * Gets the value of the subItemProperty property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the subItemProperty property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSubItemProperty().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ItemPropertyType }
     * 
     * 
     */
    public List<ItemPropertyType> getSubItemProperty() {
        if (subItemProperty == null) {
            subItemProperty = new ArrayList<ItemPropertyType>();
        }
        return this.subItemProperty;
    }

}
