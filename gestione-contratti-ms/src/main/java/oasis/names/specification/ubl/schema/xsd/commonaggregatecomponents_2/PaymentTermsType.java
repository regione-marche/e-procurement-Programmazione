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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InstallmentDueDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InvoicingPartyReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NoteType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaymentDueDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaymentMeansIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaymentPercentType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaymentTermsDetailsURIType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PenaltyAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PenaltySurchargePercentType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PrepaidPaymentReferenceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReferenceEventCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SettlementDiscountAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SettlementDiscountPercentType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per PaymentTermsType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="PaymentTermsType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PaymentMeansID" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PrepaidPaymentReferenceID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Note" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ReferenceEventCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SettlementDiscountPercent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PenaltySurchargePercent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PaymentPercent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Amount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SettlementDiscountAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PenaltyAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PaymentTermsDetailsURI" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PaymentDueDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}InstallmentDueDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}InvoicingPartyReference" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SettlementPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PenaltyPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ExchangeRate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ValidityPeriod" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentTermsType", propOrder = {
    "ublExtensions",
    "id",
    "paymentMeansID",
    "prepaidPaymentReferenceID",
    "note",
    "referenceEventCode",
    "settlementDiscountPercent",
    "penaltySurchargePercent",
    "paymentPercent",
    "amount",
    "settlementDiscountAmount",
    "penaltyAmount",
    "paymentTermsDetailsURI",
    "paymentDueDate",
    "installmentDueDate",
    "invoicingPartyReference",
    "settlementPeriod",
    "penaltyPeriod",
    "exchangeRate",
    "validityPeriod"
})
public class PaymentTermsType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "PaymentMeansID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<PaymentMeansIDType> paymentMeansID;
    @XmlElement(name = "PrepaidPaymentReferenceID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PrepaidPaymentReferenceIDType prepaidPaymentReferenceID;
    @XmlElement(name = "Note", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<NoteType> note;
    @XmlElement(name = "ReferenceEventCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ReferenceEventCodeType referenceEventCode;
    @XmlElement(name = "SettlementDiscountPercent", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SettlementDiscountPercentType settlementDiscountPercent;
    @XmlElement(name = "PenaltySurchargePercent", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PenaltySurchargePercentType penaltySurchargePercent;
    @XmlElement(name = "PaymentPercent", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PaymentPercentType paymentPercent;
    @XmlElement(name = "Amount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected AmountType amount;
    @XmlElement(name = "SettlementDiscountAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SettlementDiscountAmountType settlementDiscountAmount;
    @XmlElement(name = "PenaltyAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PenaltyAmountType penaltyAmount;
    @XmlElement(name = "PaymentTermsDetailsURI", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PaymentTermsDetailsURIType paymentTermsDetailsURI;
    @XmlElement(name = "PaymentDueDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PaymentDueDateType paymentDueDate;
    @XmlElement(name = "InstallmentDueDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected InstallmentDueDateType installmentDueDate;
    @XmlElement(name = "InvoicingPartyReference", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected InvoicingPartyReferenceType invoicingPartyReference;
    @XmlElement(name = "SettlementPeriod")
    protected PeriodType settlementPeriod;
    @XmlElement(name = "PenaltyPeriod")
    protected PeriodType penaltyPeriod;
    @XmlElement(name = "ExchangeRate")
    protected ExchangeRateType exchangeRate;
    @XmlElement(name = "ValidityPeriod")
    protected PeriodType validityPeriod;

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
     * Gets the value of the paymentMeansID property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the paymentMeansID property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPaymentMeansID().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentMeansIDType }
     * 
     * 
     */
    public List<PaymentMeansIDType> getPaymentMeansID() {
        if (paymentMeansID == null) {
            paymentMeansID = new ArrayList<PaymentMeansIDType>();
        }
        return this.paymentMeansID;
    }

    /**
     * Recupera il valore della proprietà prepaidPaymentReferenceID.
     * 
     * @return
     *     possible object is
     *     {@link PrepaidPaymentReferenceIDType }
     *     
     */
    public PrepaidPaymentReferenceIDType getPrepaidPaymentReferenceID() {
        return prepaidPaymentReferenceID;
    }

    /**
     * Imposta il valore della proprietà prepaidPaymentReferenceID.
     * 
     * @param value
     *     allowed object is
     *     {@link PrepaidPaymentReferenceIDType }
     *     
     */
    public void setPrepaidPaymentReferenceID(PrepaidPaymentReferenceIDType value) {
        this.prepaidPaymentReferenceID = value;
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
     * Recupera il valore della proprietà referenceEventCode.
     * 
     * @return
     *     possible object is
     *     {@link ReferenceEventCodeType }
     *     
     */
    public ReferenceEventCodeType getReferenceEventCode() {
        return referenceEventCode;
    }

    /**
     * Imposta il valore della proprietà referenceEventCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenceEventCodeType }
     *     
     */
    public void setReferenceEventCode(ReferenceEventCodeType value) {
        this.referenceEventCode = value;
    }

    /**
     * Recupera il valore della proprietà settlementDiscountPercent.
     * 
     * @return
     *     possible object is
     *     {@link SettlementDiscountPercentType }
     *     
     */
    public SettlementDiscountPercentType getSettlementDiscountPercent() {
        return settlementDiscountPercent;
    }

    /**
     * Imposta il valore della proprietà settlementDiscountPercent.
     * 
     * @param value
     *     allowed object is
     *     {@link SettlementDiscountPercentType }
     *     
     */
    public void setSettlementDiscountPercent(SettlementDiscountPercentType value) {
        this.settlementDiscountPercent = value;
    }

    /**
     * Recupera il valore della proprietà penaltySurchargePercent.
     * 
     * @return
     *     possible object is
     *     {@link PenaltySurchargePercentType }
     *     
     */
    public PenaltySurchargePercentType getPenaltySurchargePercent() {
        return penaltySurchargePercent;
    }

    /**
     * Imposta il valore della proprietà penaltySurchargePercent.
     * 
     * @param value
     *     allowed object is
     *     {@link PenaltySurchargePercentType }
     *     
     */
    public void setPenaltySurchargePercent(PenaltySurchargePercentType value) {
        this.penaltySurchargePercent = value;
    }

    /**
     * Recupera il valore della proprietà paymentPercent.
     * 
     * @return
     *     possible object is
     *     {@link PaymentPercentType }
     *     
     */
    public PaymentPercentType getPaymentPercent() {
        return paymentPercent;
    }

    /**
     * Imposta il valore della proprietà paymentPercent.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentPercentType }
     *     
     */
    public void setPaymentPercent(PaymentPercentType value) {
        this.paymentPercent = value;
    }

    /**
     * Recupera il valore della proprietà amount.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getAmount() {
        return amount;
    }

    /**
     * Imposta il valore della proprietà amount.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setAmount(AmountType value) {
        this.amount = value;
    }

    /**
     * Recupera il valore della proprietà settlementDiscountAmount.
     * 
     * @return
     *     possible object is
     *     {@link SettlementDiscountAmountType }
     *     
     */
    public SettlementDiscountAmountType getSettlementDiscountAmount() {
        return settlementDiscountAmount;
    }

    /**
     * Imposta il valore della proprietà settlementDiscountAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link SettlementDiscountAmountType }
     *     
     */
    public void setSettlementDiscountAmount(SettlementDiscountAmountType value) {
        this.settlementDiscountAmount = value;
    }

    /**
     * Recupera il valore della proprietà penaltyAmount.
     * 
     * @return
     *     possible object is
     *     {@link PenaltyAmountType }
     *     
     */
    public PenaltyAmountType getPenaltyAmount() {
        return penaltyAmount;
    }

    /**
     * Imposta il valore della proprietà penaltyAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link PenaltyAmountType }
     *     
     */
    public void setPenaltyAmount(PenaltyAmountType value) {
        this.penaltyAmount = value;
    }

    /**
     * Recupera il valore della proprietà paymentTermsDetailsURI.
     * 
     * @return
     *     possible object is
     *     {@link PaymentTermsDetailsURIType }
     *     
     */
    public PaymentTermsDetailsURIType getPaymentTermsDetailsURI() {
        return paymentTermsDetailsURI;
    }

    /**
     * Imposta il valore della proprietà paymentTermsDetailsURI.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentTermsDetailsURIType }
     *     
     */
    public void setPaymentTermsDetailsURI(PaymentTermsDetailsURIType value) {
        this.paymentTermsDetailsURI = value;
    }

    /**
     * Recupera il valore della proprietà paymentDueDate.
     * 
     * @return
     *     possible object is
     *     {@link PaymentDueDateType }
     *     
     */
    public PaymentDueDateType getPaymentDueDate() {
        return paymentDueDate;
    }

    /**
     * Imposta il valore della proprietà paymentDueDate.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentDueDateType }
     *     
     */
    public void setPaymentDueDate(PaymentDueDateType value) {
        this.paymentDueDate = value;
    }

    /**
     * Recupera il valore della proprietà installmentDueDate.
     * 
     * @return
     *     possible object is
     *     {@link InstallmentDueDateType }
     *     
     */
    public InstallmentDueDateType getInstallmentDueDate() {
        return installmentDueDate;
    }

    /**
     * Imposta il valore della proprietà installmentDueDate.
     * 
     * @param value
     *     allowed object is
     *     {@link InstallmentDueDateType }
     *     
     */
    public void setInstallmentDueDate(InstallmentDueDateType value) {
        this.installmentDueDate = value;
    }

    /**
     * Recupera il valore della proprietà invoicingPartyReference.
     * 
     * @return
     *     possible object is
     *     {@link InvoicingPartyReferenceType }
     *     
     */
    public InvoicingPartyReferenceType getInvoicingPartyReference() {
        return invoicingPartyReference;
    }

    /**
     * Imposta il valore della proprietà invoicingPartyReference.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoicingPartyReferenceType }
     *     
     */
    public void setInvoicingPartyReference(InvoicingPartyReferenceType value) {
        this.invoicingPartyReference = value;
    }

    /**
     * Recupera il valore della proprietà settlementPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getSettlementPeriod() {
        return settlementPeriod;
    }

    /**
     * Imposta il valore della proprietà settlementPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setSettlementPeriod(PeriodType value) {
        this.settlementPeriod = value;
    }

    /**
     * Recupera il valore della proprietà penaltyPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getPenaltyPeriod() {
        return penaltyPeriod;
    }

    /**
     * Imposta il valore della proprietà penaltyPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setPenaltyPeriod(PeriodType value) {
        this.penaltyPeriod = value;
    }

    /**
     * Recupera il valore della proprietà exchangeRate.
     * 
     * @return
     *     possible object is
     *     {@link ExchangeRateType }
     *     
     */
    public ExchangeRateType getExchangeRate() {
        return exchangeRate;
    }

    /**
     * Imposta il valore della proprietà exchangeRate.
     * 
     * @param value
     *     allowed object is
     *     {@link ExchangeRateType }
     *     
     */
    public void setExchangeRate(ExchangeRateType value) {
        this.exchangeRate = value;
    }

    /**
     * Recupera il valore della proprietà validityPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getValidityPeriod() {
        return validityPeriod;
    }

    /**
     * Imposta il valore della proprietà validityPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setValidityPeriod(PeriodType value) {
        this.validityPeriod = value;
    }

}
