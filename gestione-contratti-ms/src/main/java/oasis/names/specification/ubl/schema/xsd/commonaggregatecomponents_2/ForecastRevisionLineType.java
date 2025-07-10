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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AdjustmentReasonCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NoteType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RevisedForecastLineIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SourceForecastIssueDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SourceForecastIssueTimeType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per ForecastRevisionLineType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ForecastRevisionLineType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Note" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Description" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RevisedForecastLineID"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SourceForecastIssueDate"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SourceForecastIssueTime"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AdjustmentReasonCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ForecastPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SalesItem" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ForecastRevisionLineType", propOrder = {
    "ublExtensions",
    "id",
    "note",
    "description",
    "revisedForecastLineID",
    "sourceForecastIssueDate",
    "sourceForecastIssueTime",
    "adjustmentReasonCode",
    "forecastPeriod",
    "salesItem"
})
public class ForecastRevisionLineType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected IDType id;
    @XmlElement(name = "Note", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<NoteType> note;
    @XmlElement(name = "Description", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DescriptionType> description;
    @XmlElement(name = "RevisedForecastLineID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected RevisedForecastLineIDType revisedForecastLineID;
    @XmlElement(name = "SourceForecastIssueDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected SourceForecastIssueDateType sourceForecastIssueDate;
    @XmlElement(name = "SourceForecastIssueTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected SourceForecastIssueTimeType sourceForecastIssueTime;
    @XmlElement(name = "AdjustmentReasonCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected AdjustmentReasonCodeType adjustmentReasonCode;
    @XmlElement(name = "ForecastPeriod")
    protected PeriodType forecastPeriod;
    @XmlElement(name = "SalesItem")
    protected SalesItemType salesItem;

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
     * Recupera il valore della proprietà revisedForecastLineID.
     * 
     * @return
     *     possible object is
     *     {@link RevisedForecastLineIDType }
     *     
     */
    public RevisedForecastLineIDType getRevisedForecastLineID() {
        return revisedForecastLineID;
    }

    /**
     * Imposta il valore della proprietà revisedForecastLineID.
     * 
     * @param value
     *     allowed object is
     *     {@link RevisedForecastLineIDType }
     *     
     */
    public void setRevisedForecastLineID(RevisedForecastLineIDType value) {
        this.revisedForecastLineID = value;
    }

    /**
     * Recupera il valore della proprietà sourceForecastIssueDate.
     * 
     * @return
     *     possible object is
     *     {@link SourceForecastIssueDateType }
     *     
     */
    public SourceForecastIssueDateType getSourceForecastIssueDate() {
        return sourceForecastIssueDate;
    }

    /**
     * Imposta il valore della proprietà sourceForecastIssueDate.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceForecastIssueDateType }
     *     
     */
    public void setSourceForecastIssueDate(SourceForecastIssueDateType value) {
        this.sourceForecastIssueDate = value;
    }

    /**
     * Recupera il valore della proprietà sourceForecastIssueTime.
     * 
     * @return
     *     possible object is
     *     {@link SourceForecastIssueTimeType }
     *     
     */
    public SourceForecastIssueTimeType getSourceForecastIssueTime() {
        return sourceForecastIssueTime;
    }

    /**
     * Imposta il valore della proprietà sourceForecastIssueTime.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceForecastIssueTimeType }
     *     
     */
    public void setSourceForecastIssueTime(SourceForecastIssueTimeType value) {
        this.sourceForecastIssueTime = value;
    }

    /**
     * Recupera il valore della proprietà adjustmentReasonCode.
     * 
     * @return
     *     possible object is
     *     {@link AdjustmentReasonCodeType }
     *     
     */
    public AdjustmentReasonCodeType getAdjustmentReasonCode() {
        return adjustmentReasonCode;
    }

    /**
     * Imposta il valore della proprietà adjustmentReasonCode.
     * 
     * @param value
     *     allowed object is
     *     {@link AdjustmentReasonCodeType }
     *     
     */
    public void setAdjustmentReasonCode(AdjustmentReasonCodeType value) {
        this.adjustmentReasonCode = value;
    }

    /**
     * Recupera il valore della proprietà forecastPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getForecastPeriod() {
        return forecastPeriod;
    }

    /**
     * Imposta il valore della proprietà forecastPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setForecastPeriod(PeriodType value) {
        this.forecastPeriod = value;
    }

    /**
     * Recupera il valore della proprietà salesItem.
     * 
     * @return
     *     possible object is
     *     {@link SalesItemType }
     *     
     */
    public SalesItemType getSalesItem() {
        return salesItem;
    }

    /**
     * Imposta il valore della proprietà salesItem.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesItemType }
     *     
     */
    public void setSalesItem(SalesItemType value) {
        this.salesItem = value;
    }

}
