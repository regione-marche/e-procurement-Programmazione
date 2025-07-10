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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValidateProcessType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValidateToolType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValidateToolVersionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValidationDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValidationResultCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValidationTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValidatorIDType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per ResultOfVerificationType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ResultOfVerificationType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ValidatorID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ValidationResultCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ValidationDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ValidationTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ValidateProcess" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ValidateTool" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ValidateToolVersion" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SignatoryParty" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultOfVerificationType", propOrder = {
    "ublExtensions",
    "validatorID",
    "validationResultCode",
    "validationDate",
    "validationTime",
    "validateProcess",
    "validateTool",
    "validateToolVersion",
    "signatoryParty"
})
public class ResultOfVerificationType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ValidatorID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ValidatorIDType validatorID;
    @XmlElement(name = "ValidationResultCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ValidationResultCodeType validationResultCode;
    @XmlElement(name = "ValidationDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ValidationDateType validationDate;
    @XmlElement(name = "ValidationTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ValidationTimeType validationTime;
    @XmlElement(name = "ValidateProcess", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ValidateProcessType validateProcess;
    @XmlElement(name = "ValidateTool", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ValidateToolType validateTool;
    @XmlElement(name = "ValidateToolVersion", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ValidateToolVersionType validateToolVersion;
    @XmlElement(name = "SignatoryParty")
    protected PartyType signatoryParty;

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
     * Recupera il valore della proprietà validatorID.
     * 
     * @return
     *     possible object is
     *     {@link ValidatorIDType }
     *     
     */
    public ValidatorIDType getValidatorID() {
        return validatorID;
    }

    /**
     * Imposta il valore della proprietà validatorID.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidatorIDType }
     *     
     */
    public void setValidatorID(ValidatorIDType value) {
        this.validatorID = value;
    }

    /**
     * Recupera il valore della proprietà validationResultCode.
     * 
     * @return
     *     possible object is
     *     {@link ValidationResultCodeType }
     *     
     */
    public ValidationResultCodeType getValidationResultCode() {
        return validationResultCode;
    }

    /**
     * Imposta il valore della proprietà validationResultCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidationResultCodeType }
     *     
     */
    public void setValidationResultCode(ValidationResultCodeType value) {
        this.validationResultCode = value;
    }

    /**
     * Recupera il valore della proprietà validationDate.
     * 
     * @return
     *     possible object is
     *     {@link ValidationDateType }
     *     
     */
    public ValidationDateType getValidationDate() {
        return validationDate;
    }

    /**
     * Imposta il valore della proprietà validationDate.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidationDateType }
     *     
     */
    public void setValidationDate(ValidationDateType value) {
        this.validationDate = value;
    }

    /**
     * Recupera il valore della proprietà validationTime.
     * 
     * @return
     *     possible object is
     *     {@link ValidationTimeType }
     *     
     */
    public ValidationTimeType getValidationTime() {
        return validationTime;
    }

    /**
     * Imposta il valore della proprietà validationTime.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidationTimeType }
     *     
     */
    public void setValidationTime(ValidationTimeType value) {
        this.validationTime = value;
    }

    /**
     * Recupera il valore della proprietà validateProcess.
     * 
     * @return
     *     possible object is
     *     {@link ValidateProcessType }
     *     
     */
    public ValidateProcessType getValidateProcess() {
        return validateProcess;
    }

    /**
     * Imposta il valore della proprietà validateProcess.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidateProcessType }
     *     
     */
    public void setValidateProcess(ValidateProcessType value) {
        this.validateProcess = value;
    }

    /**
     * Recupera il valore della proprietà validateTool.
     * 
     * @return
     *     possible object is
     *     {@link ValidateToolType }
     *     
     */
    public ValidateToolType getValidateTool() {
        return validateTool;
    }

    /**
     * Imposta il valore della proprietà validateTool.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidateToolType }
     *     
     */
    public void setValidateTool(ValidateToolType value) {
        this.validateTool = value;
    }

    /**
     * Recupera il valore della proprietà validateToolVersion.
     * 
     * @return
     *     possible object is
     *     {@link ValidateToolVersionType }
     *     
     */
    public ValidateToolVersionType getValidateToolVersion() {
        return validateToolVersion;
    }

    /**
     * Imposta il valore della proprietà validateToolVersion.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidateToolVersionType }
     *     
     */
    public void setValidateToolVersion(ValidateToolVersionType value) {
        this.validateToolVersion = value;
    }

    /**
     * Recupera il valore della proprietà signatoryParty.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getSignatoryParty() {
        return signatoryParty;
    }

    /**
     * Imposta il valore della proprietà signatoryParty.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setSignatoryParty(PartyType value) {
        this.signatoryParty = value;
    }

}
