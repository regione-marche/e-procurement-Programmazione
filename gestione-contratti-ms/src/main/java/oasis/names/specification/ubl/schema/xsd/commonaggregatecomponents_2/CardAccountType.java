//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CV2IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CardChipCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CardTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ChipApplicationIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExpiryDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HolderNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IssueNumberIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IssuerIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NetworkIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PrimaryAccountNumberIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RoleCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValidityStartDateType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per CardAccountType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="CardAccountType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PrimaryAccountNumberID"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}NetworkID"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CardTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ValidityStartDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ExpiryDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}IssuerID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}IssueNumberID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CV2ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CardChipCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ChipApplicationID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HolderName" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RoleCode" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardAccountType", propOrder = {
    "ublExtensions",
    "primaryAccountNumberID",
    "networkID",
    "cardTypeCode",
    "validityStartDate",
    "expiryDate",
    "issuerID",
    "issueNumberID",
    "cv2ID",
    "cardChipCode",
    "chipApplicationID",
    "holderName",
    "roleCode"
})
public class CardAccountType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "PrimaryAccountNumberID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected PrimaryAccountNumberIDType primaryAccountNumberID;
    @XmlElement(name = "NetworkID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected NetworkIDType networkID;
    @XmlElement(name = "CardTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CardTypeCodeType cardTypeCode;
    @XmlElement(name = "ValidityStartDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ValidityStartDateType validityStartDate;
    @XmlElement(name = "ExpiryDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ExpiryDateType expiryDate;
    @XmlElement(name = "IssuerID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IssuerIDType issuerID;
    @XmlElement(name = "IssueNumberID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IssueNumberIDType issueNumberID;
    @XmlElement(name = "CV2ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CV2IDType cv2ID;
    @XmlElement(name = "CardChipCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CardChipCodeType cardChipCode;
    @XmlElement(name = "ChipApplicationID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ChipApplicationIDType chipApplicationID;
    @XmlElement(name = "HolderName", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected HolderNameType holderName;
    @XmlElement(name = "RoleCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RoleCodeType roleCode;

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
     * Recupera il valore della proprietà primaryAccountNumberID.
     * 
     * @return
     *     possible object is
     *     {@link PrimaryAccountNumberIDType }
     *     
     */
    public PrimaryAccountNumberIDType getPrimaryAccountNumberID() {
        return primaryAccountNumberID;
    }

    /**
     * Imposta il valore della proprietà primaryAccountNumberID.
     * 
     * @param value
     *     allowed object is
     *     {@link PrimaryAccountNumberIDType }
     *     
     */
    public void setPrimaryAccountNumberID(PrimaryAccountNumberIDType value) {
        this.primaryAccountNumberID = value;
    }

    /**
     * Recupera il valore della proprietà networkID.
     * 
     * @return
     *     possible object is
     *     {@link NetworkIDType }
     *     
     */
    public NetworkIDType getNetworkID() {
        return networkID;
    }

    /**
     * Imposta il valore della proprietà networkID.
     * 
     * @param value
     *     allowed object is
     *     {@link NetworkIDType }
     *     
     */
    public void setNetworkID(NetworkIDType value) {
        this.networkID = value;
    }

    /**
     * Recupera il valore della proprietà cardTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link CardTypeCodeType }
     *     
     */
    public CardTypeCodeType getCardTypeCode() {
        return cardTypeCode;
    }

    /**
     * Imposta il valore della proprietà cardTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link CardTypeCodeType }
     *     
     */
    public void setCardTypeCode(CardTypeCodeType value) {
        this.cardTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà validityStartDate.
     * 
     * @return
     *     possible object is
     *     {@link ValidityStartDateType }
     *     
     */
    public ValidityStartDateType getValidityStartDate() {
        return validityStartDate;
    }

    /**
     * Imposta il valore della proprietà validityStartDate.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidityStartDateType }
     *     
     */
    public void setValidityStartDate(ValidityStartDateType value) {
        this.validityStartDate = value;
    }

    /**
     * Recupera il valore della proprietà expiryDate.
     * 
     * @return
     *     possible object is
     *     {@link ExpiryDateType }
     *     
     */
    public ExpiryDateType getExpiryDate() {
        return expiryDate;
    }

    /**
     * Imposta il valore della proprietà expiryDate.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpiryDateType }
     *     
     */
    public void setExpiryDate(ExpiryDateType value) {
        this.expiryDate = value;
    }

    /**
     * Recupera il valore della proprietà issuerID.
     * 
     * @return
     *     possible object is
     *     {@link IssuerIDType }
     *     
     */
    public IssuerIDType getIssuerID() {
        return issuerID;
    }

    /**
     * Imposta il valore della proprietà issuerID.
     * 
     * @param value
     *     allowed object is
     *     {@link IssuerIDType }
     *     
     */
    public void setIssuerID(IssuerIDType value) {
        this.issuerID = value;
    }

    /**
     * Recupera il valore della proprietà issueNumberID.
     * 
     * @return
     *     possible object is
     *     {@link IssueNumberIDType }
     *     
     */
    public IssueNumberIDType getIssueNumberID() {
        return issueNumberID;
    }

    /**
     * Imposta il valore della proprietà issueNumberID.
     * 
     * @param value
     *     allowed object is
     *     {@link IssueNumberIDType }
     *     
     */
    public void setIssueNumberID(IssueNumberIDType value) {
        this.issueNumberID = value;
    }

    /**
     * Recupera il valore della proprietà cv2ID.
     * 
     * @return
     *     possible object is
     *     {@link CV2IDType }
     *     
     */
    public CV2IDType getCV2ID() {
        return cv2ID;
    }

    /**
     * Imposta il valore della proprietà cv2ID.
     * 
     * @param value
     *     allowed object is
     *     {@link CV2IDType }
     *     
     */
    public void setCV2ID(CV2IDType value) {
        this.cv2ID = value;
    }

    /**
     * Recupera il valore della proprietà cardChipCode.
     * 
     * @return
     *     possible object is
     *     {@link CardChipCodeType }
     *     
     */
    public CardChipCodeType getCardChipCode() {
        return cardChipCode;
    }

    /**
     * Imposta il valore della proprietà cardChipCode.
     * 
     * @param value
     *     allowed object is
     *     {@link CardChipCodeType }
     *     
     */
    public void setCardChipCode(CardChipCodeType value) {
        this.cardChipCode = value;
    }

    /**
     * Recupera il valore della proprietà chipApplicationID.
     * 
     * @return
     *     possible object is
     *     {@link ChipApplicationIDType }
     *     
     */
    public ChipApplicationIDType getChipApplicationID() {
        return chipApplicationID;
    }

    /**
     * Imposta il valore della proprietà chipApplicationID.
     * 
     * @param value
     *     allowed object is
     *     {@link ChipApplicationIDType }
     *     
     */
    public void setChipApplicationID(ChipApplicationIDType value) {
        this.chipApplicationID = value;
    }

    /**
     * Recupera il valore della proprietà holderName.
     * 
     * @return
     *     possible object is
     *     {@link HolderNameType }
     *     
     */
    public HolderNameType getHolderName() {
        return holderName;
    }

    /**
     * Imposta il valore della proprietà holderName.
     * 
     * @param value
     *     allowed object is
     *     {@link HolderNameType }
     *     
     */
    public void setHolderName(HolderNameType value) {
        this.holderName = value;
    }

    /**
     * Recupera il valore della proprietà roleCode.
     * 
     * @return
     *     possible object is
     *     {@link RoleCodeType }
     *     
     */
    public RoleCodeType getRoleCode() {
        return roleCode;
    }

    /**
     * Imposta il valore della proprietà roleCode.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleCodeType }
     *     
     */
    public void setRoleCode(RoleCodeType value) {
        this.roleCode = value;
    }

}
