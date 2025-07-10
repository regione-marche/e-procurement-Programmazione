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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CompanyIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CompanyLegalFormCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CompanyLegalFormType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CompanyLiquidationStatusCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CorporateStockAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FullyPaidSharesIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RegistrationDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RegistrationExpirationDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RegistrationNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SoleProprietorshipIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per PartyLegalEntityType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="PartyLegalEntityType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RegistrationName" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CompanyID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RegistrationDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RegistrationExpirationDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CompanyLegalFormCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CompanyLegalForm" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SoleProprietorshipIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CompanyLiquidationStatusCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CorporateStockAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}FullyPaidSharesIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RegistrationAddress" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CorporateRegistrationScheme" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}HeadOfficeParty" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ShareholderParty" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyLegalEntityType", propOrder = {
    "ublExtensions",
    "registrationName",
    "companyID",
    "registrationDate",
    "registrationExpirationDate",
    "companyLegalFormCode",
    "companyLegalForm",
    "soleProprietorshipIndicator",
    "companyLiquidationStatusCode",
    "corporateStockAmount",
    "fullyPaidSharesIndicator",
    "registrationAddress",
    "corporateRegistrationScheme",
    "headOfficeParty",
    "shareholderParty"
})
public class PartyLegalEntityType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "RegistrationName", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RegistrationNameType registrationName;
    @XmlElement(name = "CompanyID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CompanyIDType companyID;
    @XmlElement(name = "RegistrationDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RegistrationDateType registrationDate;
    @XmlElement(name = "RegistrationExpirationDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RegistrationExpirationDateType registrationExpirationDate;
    @XmlElement(name = "CompanyLegalFormCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CompanyLegalFormCodeType companyLegalFormCode;
    @XmlElement(name = "CompanyLegalForm", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<CompanyLegalFormType> companyLegalForm;
    @XmlElement(name = "SoleProprietorshipIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SoleProprietorshipIndicatorType soleProprietorshipIndicator;
    @XmlElement(name = "CompanyLiquidationStatusCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CompanyLiquidationStatusCodeType companyLiquidationStatusCode;
    @XmlElement(name = "CorporateStockAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CorporateStockAmountType corporateStockAmount;
    @XmlElement(name = "FullyPaidSharesIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected FullyPaidSharesIndicatorType fullyPaidSharesIndicator;
    @XmlElement(name = "RegistrationAddress")
    protected AddressType registrationAddress;
    @XmlElement(name = "CorporateRegistrationScheme")
    protected CorporateRegistrationSchemeType corporateRegistrationScheme;
    @XmlElement(name = "HeadOfficeParty")
    protected PartyType headOfficeParty;
    @XmlElement(name = "ShareholderParty")
    protected List<ShareholderPartyType> shareholderParty;

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
     * Recupera il valore della proprietà registrationName.
     * 
     * @return
     *     possible object is
     *     {@link RegistrationNameType }
     *     
     */
    public RegistrationNameType getRegistrationName() {
        return registrationName;
    }

    /**
     * Imposta il valore della proprietà registrationName.
     * 
     * @param value
     *     allowed object is
     *     {@link RegistrationNameType }
     *     
     */
    public void setRegistrationName(RegistrationNameType value) {
        this.registrationName = value;
    }

    /**
     * Recupera il valore della proprietà companyID.
     * 
     * @return
     *     possible object is
     *     {@link CompanyIDType }
     *     
     */
    public CompanyIDType getCompanyID() {
        return companyID;
    }

    /**
     * Imposta il valore della proprietà companyID.
     * 
     * @param value
     *     allowed object is
     *     {@link CompanyIDType }
     *     
     */
    public void setCompanyID(CompanyIDType value) {
        this.companyID = value;
    }

    /**
     * Recupera il valore della proprietà registrationDate.
     * 
     * @return
     *     possible object is
     *     {@link RegistrationDateType }
     *     
     */
    public RegistrationDateType getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Imposta il valore della proprietà registrationDate.
     * 
     * @param value
     *     allowed object is
     *     {@link RegistrationDateType }
     *     
     */
    public void setRegistrationDate(RegistrationDateType value) {
        this.registrationDate = value;
    }

    /**
     * Recupera il valore della proprietà registrationExpirationDate.
     * 
     * @return
     *     possible object is
     *     {@link RegistrationExpirationDateType }
     *     
     */
    public RegistrationExpirationDateType getRegistrationExpirationDate() {
        return registrationExpirationDate;
    }

    /**
     * Imposta il valore della proprietà registrationExpirationDate.
     * 
     * @param value
     *     allowed object is
     *     {@link RegistrationExpirationDateType }
     *     
     */
    public void setRegistrationExpirationDate(RegistrationExpirationDateType value) {
        this.registrationExpirationDate = value;
    }

    /**
     * Recupera il valore della proprietà companyLegalFormCode.
     * 
     * @return
     *     possible object is
     *     {@link CompanyLegalFormCodeType }
     *     
     */
    public CompanyLegalFormCodeType getCompanyLegalFormCode() {
        return companyLegalFormCode;
    }

    /**
     * Imposta il valore della proprietà companyLegalFormCode.
     * 
     * @param value
     *     allowed object is
     *     {@link CompanyLegalFormCodeType }
     *     
     */
    public void setCompanyLegalFormCode(CompanyLegalFormCodeType value) {
        this.companyLegalFormCode = value;
    }

    /**
     * Gets the value of the companyLegalForm property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the companyLegalForm property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCompanyLegalForm().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CompanyLegalFormType }
     * 
     * 
     */
    public List<CompanyLegalFormType> getCompanyLegalForm() {
        if (companyLegalForm == null) {
            companyLegalForm = new ArrayList<CompanyLegalFormType>();
        }
        return this.companyLegalForm;
    }

    /**
     * Recupera il valore della proprietà soleProprietorshipIndicator.
     * 
     * @return
     *     possible object is
     *     {@link SoleProprietorshipIndicatorType }
     *     
     */
    public SoleProprietorshipIndicatorType getSoleProprietorshipIndicator() {
        return soleProprietorshipIndicator;
    }

    /**
     * Imposta il valore della proprietà soleProprietorshipIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link SoleProprietorshipIndicatorType }
     *     
     */
    public void setSoleProprietorshipIndicator(SoleProprietorshipIndicatorType value) {
        this.soleProprietorshipIndicator = value;
    }

    /**
     * Recupera il valore della proprietà companyLiquidationStatusCode.
     * 
     * @return
     *     possible object is
     *     {@link CompanyLiquidationStatusCodeType }
     *     
     */
    public CompanyLiquidationStatusCodeType getCompanyLiquidationStatusCode() {
        return companyLiquidationStatusCode;
    }

    /**
     * Imposta il valore della proprietà companyLiquidationStatusCode.
     * 
     * @param value
     *     allowed object is
     *     {@link CompanyLiquidationStatusCodeType }
     *     
     */
    public void setCompanyLiquidationStatusCode(CompanyLiquidationStatusCodeType value) {
        this.companyLiquidationStatusCode = value;
    }

    /**
     * Recupera il valore della proprietà corporateStockAmount.
     * 
     * @return
     *     possible object is
     *     {@link CorporateStockAmountType }
     *     
     */
    public CorporateStockAmountType getCorporateStockAmount() {
        return corporateStockAmount;
    }

    /**
     * Imposta il valore della proprietà corporateStockAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link CorporateStockAmountType }
     *     
     */
    public void setCorporateStockAmount(CorporateStockAmountType value) {
        this.corporateStockAmount = value;
    }

    /**
     * Recupera il valore della proprietà fullyPaidSharesIndicator.
     * 
     * @return
     *     possible object is
     *     {@link FullyPaidSharesIndicatorType }
     *     
     */
    public FullyPaidSharesIndicatorType getFullyPaidSharesIndicator() {
        return fullyPaidSharesIndicator;
    }

    /**
     * Imposta il valore della proprietà fullyPaidSharesIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link FullyPaidSharesIndicatorType }
     *     
     */
    public void setFullyPaidSharesIndicator(FullyPaidSharesIndicatorType value) {
        this.fullyPaidSharesIndicator = value;
    }

    /**
     * Recupera il valore della proprietà registrationAddress.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getRegistrationAddress() {
        return registrationAddress;
    }

    /**
     * Imposta il valore della proprietà registrationAddress.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setRegistrationAddress(AddressType value) {
        this.registrationAddress = value;
    }

    /**
     * Recupera il valore della proprietà corporateRegistrationScheme.
     * 
     * @return
     *     possible object is
     *     {@link CorporateRegistrationSchemeType }
     *     
     */
    public CorporateRegistrationSchemeType getCorporateRegistrationScheme() {
        return corporateRegistrationScheme;
    }

    /**
     * Imposta il valore della proprietà corporateRegistrationScheme.
     * 
     * @param value
     *     allowed object is
     *     {@link CorporateRegistrationSchemeType }
     *     
     */
    public void setCorporateRegistrationScheme(CorporateRegistrationSchemeType value) {
        this.corporateRegistrationScheme = value;
    }

    /**
     * Recupera il valore della proprietà headOfficeParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getHeadOfficeParty() {
        return headOfficeParty;
    }

    /**
     * Imposta il valore della proprietà headOfficeParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setHeadOfficeParty(PartyType value) {
        this.headOfficeParty = value;
    }

    /**
     * Gets the value of the shareholderParty property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the shareholderParty property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getShareholderParty().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ShareholderPartyType }
     * 
     * 
     */
    public List<ShareholderPartyType> getShareholderParty() {
        if (shareholderParty == null) {
            shareholderParty = new ArrayList<ShareholderPartyType>();
        }
        return this.shareholderParty;
    }

}
