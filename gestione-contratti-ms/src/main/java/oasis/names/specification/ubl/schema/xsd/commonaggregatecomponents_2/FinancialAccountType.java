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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AccountFormatCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AccountTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AliasNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaymentNoteType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per FinancialAccountType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="FinancialAccountType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Name" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AliasName" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AccountTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AccountFormatCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CurrencyCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PaymentNote" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}FinancialInstitutionBranch" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Country" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FinancialAccountType", propOrder = {
    "ublExtensions",
    "id",
    "name",
    "aliasName",
    "accountTypeCode",
    "accountFormatCode",
    "currencyCode",
    "paymentNote",
    "financialInstitutionBranch",
    "country"
})
public class FinancialAccountType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "Name", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NameType name;
    @XmlElement(name = "AliasName", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected AliasNameType aliasName;
    @XmlElement(name = "AccountTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected AccountTypeCodeType accountTypeCode;
    @XmlElement(name = "AccountFormatCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected AccountFormatCodeType accountFormatCode;
    @XmlElement(name = "CurrencyCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CurrencyCodeType currencyCode;
    @XmlElement(name = "PaymentNote", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<PaymentNoteType> paymentNote;
    @XmlElement(name = "FinancialInstitutionBranch")
    protected BranchType financialInstitutionBranch;
    @XmlElement(name = "Country")
    protected CountryType country;

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
     * Recupera il valore della proprietà aliasName.
     * 
     * @return
     *     possible object is
     *     {@link AliasNameType }
     *     
     */
    public AliasNameType getAliasName() {
        return aliasName;
    }

    /**
     * Imposta il valore della proprietà aliasName.
     * 
     * @param value
     *     allowed object is
     *     {@link AliasNameType }
     *     
     */
    public void setAliasName(AliasNameType value) {
        this.aliasName = value;
    }

    /**
     * Recupera il valore della proprietà accountTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link AccountTypeCodeType }
     *     
     */
    public AccountTypeCodeType getAccountTypeCode() {
        return accountTypeCode;
    }

    /**
     * Imposta il valore della proprietà accountTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountTypeCodeType }
     *     
     */
    public void setAccountTypeCode(AccountTypeCodeType value) {
        this.accountTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà accountFormatCode.
     * 
     * @return
     *     possible object is
     *     {@link AccountFormatCodeType }
     *     
     */
    public AccountFormatCodeType getAccountFormatCode() {
        return accountFormatCode;
    }

    /**
     * Imposta il valore della proprietà accountFormatCode.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountFormatCodeType }
     *     
     */
    public void setAccountFormatCode(AccountFormatCodeType value) {
        this.accountFormatCode = value;
    }

    /**
     * Recupera il valore della proprietà currencyCode.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyCodeType }
     *     
     */
    public CurrencyCodeType getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Imposta il valore della proprietà currencyCode.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyCodeType }
     *     
     */
    public void setCurrencyCode(CurrencyCodeType value) {
        this.currencyCode = value;
    }

    /**
     * Gets the value of the paymentNote property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the paymentNote property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPaymentNote().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentNoteType }
     * 
     * 
     */
    public List<PaymentNoteType> getPaymentNote() {
        if (paymentNote == null) {
            paymentNote = new ArrayList<PaymentNoteType>();
        }
        return this.paymentNote;
    }

    /**
     * Recupera il valore della proprietà financialInstitutionBranch.
     * 
     * @return
     *     possible object is
     *     {@link BranchType }
     *     
     */
    public BranchType getFinancialInstitutionBranch() {
        return financialInstitutionBranch;
    }

    /**
     * Imposta il valore della proprietà financialInstitutionBranch.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchType }
     *     
     */
    public void setFinancialInstitutionBranch(BranchType value) {
        this.financialInstitutionBranch = value;
    }

    /**
     * Recupera il valore della proprietà country.
     * 
     * @return
     *     possible object is
     *     {@link CountryType }
     *     
     */
    public CountryType getCountry() {
        return country;
    }

    /**
     * Imposta il valore della proprietà country.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryType }
     *     
     */
    public void setCountry(CountryType value) {
        this.country = value;
    }

}
