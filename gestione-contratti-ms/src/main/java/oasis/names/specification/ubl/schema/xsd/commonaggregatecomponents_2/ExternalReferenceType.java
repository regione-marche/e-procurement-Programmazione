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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CharacterSetCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DocumentHashType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EncodingCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExpiryDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExpiryTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FileNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FormatCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HashAlgorithmMethodType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MimeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.URIType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per ExternalReferenceType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ExternalReferenceType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}URI" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DocumentHash" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}HashAlgorithmMethod" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ExpiryDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ExpiryTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MimeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}FormatCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}EncodingCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CharacterSetCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}FileName" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Description" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExternalReferenceType", propOrder = {
    "ublExtensions",
    "uri",
    "documentHash",
    "hashAlgorithmMethod",
    "expiryDate",
    "expiryTime",
    "mimeCode",
    "formatCode",
    "encodingCode",
    "characterSetCode",
    "fileName",
    "description"
})
public class ExternalReferenceType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "URI", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected URIType uri;
    @XmlElement(name = "DocumentHash", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected DocumentHashType documentHash;
    @XmlElement(name = "HashAlgorithmMethod", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected HashAlgorithmMethodType hashAlgorithmMethod;
    @XmlElement(name = "ExpiryDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ExpiryDateType expiryDate;
    @XmlElement(name = "ExpiryTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ExpiryTimeType expiryTime;
    @XmlElement(name = "MimeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MimeCodeType mimeCode;
    @XmlElement(name = "FormatCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected FormatCodeType formatCode;
    @XmlElement(name = "EncodingCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected EncodingCodeType encodingCode;
    @XmlElement(name = "CharacterSetCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CharacterSetCodeType characterSetCode;
    @XmlElement(name = "FileName", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected FileNameType fileName;
    @XmlElement(name = "Description", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DescriptionType> description;

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
     * Recupera il valore della proprietà uri.
     * 
     * @return
     *     possible object is
     *     {@link URIType }
     *     
     */
    public URIType getURI() {
        return uri;
    }

    /**
     * Imposta il valore della proprietà uri.
     * 
     * @param value
     *     allowed object is
     *     {@link URIType }
     *     
     */
    public void setURI(URIType value) {
        this.uri = value;
    }

    /**
     * Recupera il valore della proprietà documentHash.
     * 
     * @return
     *     possible object is
     *     {@link DocumentHashType }
     *     
     */
    public DocumentHashType getDocumentHash() {
        return documentHash;
    }

    /**
     * Imposta il valore della proprietà documentHash.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentHashType }
     *     
     */
    public void setDocumentHash(DocumentHashType value) {
        this.documentHash = value;
    }

    /**
     * Recupera il valore della proprietà hashAlgorithmMethod.
     * 
     * @return
     *     possible object is
     *     {@link HashAlgorithmMethodType }
     *     
     */
    public HashAlgorithmMethodType getHashAlgorithmMethod() {
        return hashAlgorithmMethod;
    }

    /**
     * Imposta il valore della proprietà hashAlgorithmMethod.
     * 
     * @param value
     *     allowed object is
     *     {@link HashAlgorithmMethodType }
     *     
     */
    public void setHashAlgorithmMethod(HashAlgorithmMethodType value) {
        this.hashAlgorithmMethod = value;
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
     * Recupera il valore della proprietà expiryTime.
     * 
     * @return
     *     possible object is
     *     {@link ExpiryTimeType }
     *     
     */
    public ExpiryTimeType getExpiryTime() {
        return expiryTime;
    }

    /**
     * Imposta il valore della proprietà expiryTime.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpiryTimeType }
     *     
     */
    public void setExpiryTime(ExpiryTimeType value) {
        this.expiryTime = value;
    }

    /**
     * Recupera il valore della proprietà mimeCode.
     * 
     * @return
     *     possible object is
     *     {@link MimeCodeType }
     *     
     */
    public MimeCodeType getMimeCode() {
        return mimeCode;
    }

    /**
     * Imposta il valore della proprietà mimeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link MimeCodeType }
     *     
     */
    public void setMimeCode(MimeCodeType value) {
        this.mimeCode = value;
    }

    /**
     * Recupera il valore della proprietà formatCode.
     * 
     * @return
     *     possible object is
     *     {@link FormatCodeType }
     *     
     */
    public FormatCodeType getFormatCode() {
        return formatCode;
    }

    /**
     * Imposta il valore della proprietà formatCode.
     * 
     * @param value
     *     allowed object is
     *     {@link FormatCodeType }
     *     
     */
    public void setFormatCode(FormatCodeType value) {
        this.formatCode = value;
    }

    /**
     * Recupera il valore della proprietà encodingCode.
     * 
     * @return
     *     possible object is
     *     {@link EncodingCodeType }
     *     
     */
    public EncodingCodeType getEncodingCode() {
        return encodingCode;
    }

    /**
     * Imposta il valore della proprietà encodingCode.
     * 
     * @param value
     *     allowed object is
     *     {@link EncodingCodeType }
     *     
     */
    public void setEncodingCode(EncodingCodeType value) {
        this.encodingCode = value;
    }

    /**
     * Recupera il valore della proprietà characterSetCode.
     * 
     * @return
     *     possible object is
     *     {@link CharacterSetCodeType }
     *     
     */
    public CharacterSetCodeType getCharacterSetCode() {
        return characterSetCode;
    }

    /**
     * Imposta il valore della proprietà characterSetCode.
     * 
     * @param value
     *     allowed object is
     *     {@link CharacterSetCodeType }
     *     
     */
    public void setCharacterSetCode(CharacterSetCodeType value) {
        this.characterSetCode = value;
    }

    /**
     * Recupera il valore della proprietà fileName.
     * 
     * @return
     *     possible object is
     *     {@link FileNameType }
     *     
     */
    public FileNameType getFileName() {
        return fileName;
    }

    /**
     * Imposta il valore della proprietà fileName.
     * 
     * @param value
     *     allowed object is
     *     {@link FileNameType }
     *     
     */
    public void setFileName(FileNameType value) {
        this.fileName = value;
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

}
