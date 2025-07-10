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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MandateTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumPaidAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumPaymentInstructionsNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SignatureIDType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per PaymentMandateType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="PaymentMandateType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MandateTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MaximumPaymentInstructionsNumeric" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MaximumPaidAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SignatureID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PayerParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PayerFinancialAccount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ValidityPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PaymentReversalPeriod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Clause" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentMandateType", propOrder = {
    "ublExtensions",
    "id",
    "mandateTypeCode",
    "maximumPaymentInstructionsNumeric",
    "maximumPaidAmount",
    "signatureID",
    "payerParty",
    "payerFinancialAccount",
    "validityPeriod",
    "paymentReversalPeriod",
    "clause"
})
public class PaymentMandateType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "MandateTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MandateTypeCodeType mandateTypeCode;
    @XmlElement(name = "MaximumPaymentInstructionsNumeric", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MaximumPaymentInstructionsNumericType maximumPaymentInstructionsNumeric;
    @XmlElement(name = "MaximumPaidAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MaximumPaidAmountType maximumPaidAmount;
    @XmlElement(name = "SignatureID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SignatureIDType signatureID;
    @XmlElement(name = "PayerParty")
    protected PartyType payerParty;
    @XmlElement(name = "PayerFinancialAccount")
    protected FinancialAccountType payerFinancialAccount;
    @XmlElement(name = "ValidityPeriod")
    protected PeriodType validityPeriod;
    @XmlElement(name = "PaymentReversalPeriod")
    protected PeriodType paymentReversalPeriod;
    @XmlElement(name = "Clause")
    protected List<ClauseType> clause;

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
     * Recupera il valore della proprietà mandateTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link MandateTypeCodeType }
     *     
     */
    public MandateTypeCodeType getMandateTypeCode() {
        return mandateTypeCode;
    }

    /**
     * Imposta il valore della proprietà mandateTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link MandateTypeCodeType }
     *     
     */
    public void setMandateTypeCode(MandateTypeCodeType value) {
        this.mandateTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà maximumPaymentInstructionsNumeric.
     * 
     * @return
     *     possible object is
     *     {@link MaximumPaymentInstructionsNumericType }
     *     
     */
    public MaximumPaymentInstructionsNumericType getMaximumPaymentInstructionsNumeric() {
        return maximumPaymentInstructionsNumeric;
    }

    /**
     * Imposta il valore della proprietà maximumPaymentInstructionsNumeric.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumPaymentInstructionsNumericType }
     *     
     */
    public void setMaximumPaymentInstructionsNumeric(MaximumPaymentInstructionsNumericType value) {
        this.maximumPaymentInstructionsNumeric = value;
    }

    /**
     * Recupera il valore della proprietà maximumPaidAmount.
     * 
     * @return
     *     possible object is
     *     {@link MaximumPaidAmountType }
     *     
     */
    public MaximumPaidAmountType getMaximumPaidAmount() {
        return maximumPaidAmount;
    }

    /**
     * Imposta il valore della proprietà maximumPaidAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumPaidAmountType }
     *     
     */
    public void setMaximumPaidAmount(MaximumPaidAmountType value) {
        this.maximumPaidAmount = value;
    }

    /**
     * Recupera il valore della proprietà signatureID.
     * 
     * @return
     *     possible object is
     *     {@link SignatureIDType }
     *     
     */
    public SignatureIDType getSignatureID() {
        return signatureID;
    }

    /**
     * Imposta il valore della proprietà signatureID.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureIDType }
     *     
     */
    public void setSignatureID(SignatureIDType value) {
        this.signatureID = value;
    }

    /**
     * Recupera il valore della proprietà payerParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getPayerParty() {
        return payerParty;
    }

    /**
     * Imposta il valore della proprietà payerParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setPayerParty(PartyType value) {
        this.payerParty = value;
    }

    /**
     * Recupera il valore della proprietà payerFinancialAccount.
     * 
     * @return
     *     possible object is
     *     {@link FinancialAccountType }
     *     
     */
    public FinancialAccountType getPayerFinancialAccount() {
        return payerFinancialAccount;
    }

    /**
     * Imposta il valore della proprietà payerFinancialAccount.
     * 
     * @param value
     *     allowed object is
     *     {@link FinancialAccountType }
     *     
     */
    public void setPayerFinancialAccount(FinancialAccountType value) {
        this.payerFinancialAccount = value;
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

    /**
     * Recupera il valore della proprietà paymentReversalPeriod.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    public PeriodType getPaymentReversalPeriod() {
        return paymentReversalPeriod;
    }

    /**
     * Imposta il valore della proprietà paymentReversalPeriod.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setPaymentReversalPeriod(PeriodType value) {
        this.paymentReversalPeriod = value;
    }

    /**
     * Gets the value of the clause property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the clause property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getClause().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ClauseType }
     * 
     * 
     */
    public List<ClauseType> getClause() {
        if (clause == null) {
            clause = new ArrayList<ClauseType>();
        }
        return this.clause;
    }

}
