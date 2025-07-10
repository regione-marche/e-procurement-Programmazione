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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MessageFormatType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per EncryptionDataType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="EncryptionDataType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MessageFormat"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}EncryptionCertificateAttachment" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}EncryptionCertificatePathChain" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}EncryptionSymmetricAlgorithm" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EncryptionDataType", propOrder = {
    "ublExtensions",
    "messageFormat",
    "encryptionCertificateAttachment",
    "encryptionCertificatePathChain",
    "encryptionSymmetricAlgorithm"
})
public class EncryptionDataType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "MessageFormat", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected MessageFormatType messageFormat;
    @XmlElement(name = "EncryptionCertificateAttachment")
    protected AttachmentType encryptionCertificateAttachment;
    @XmlElement(name = "EncryptionCertificatePathChain")
    protected List<EncryptionCertificatePathChainType> encryptionCertificatePathChain;
    @XmlElement(name = "EncryptionSymmetricAlgorithm")
    protected List<EncryptionSymmetricAlgorithmType> encryptionSymmetricAlgorithm;

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
     * Recupera il valore della proprietà messageFormat.
     * 
     * @return
     *     possible object is
     *     {@link MessageFormatType }
     *     
     */
    public MessageFormatType getMessageFormat() {
        return messageFormat;
    }

    /**
     * Imposta il valore della proprietà messageFormat.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageFormatType }
     *     
     */
    public void setMessageFormat(MessageFormatType value) {
        this.messageFormat = value;
    }

    /**
     * Recupera il valore della proprietà encryptionCertificateAttachment.
     * 
     * @return
     *     possible object is
     *     {@link AttachmentType }
     *     
     */
    public AttachmentType getEncryptionCertificateAttachment() {
        return encryptionCertificateAttachment;
    }

    /**
     * Imposta il valore della proprietà encryptionCertificateAttachment.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachmentType }
     *     
     */
    public void setEncryptionCertificateAttachment(AttachmentType value) {
        this.encryptionCertificateAttachment = value;
    }

    /**
     * Gets the value of the encryptionCertificatePathChain property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the encryptionCertificatePathChain property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getEncryptionCertificatePathChain().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link EncryptionCertificatePathChainType }
     * 
     * 
     */
    public List<EncryptionCertificatePathChainType> getEncryptionCertificatePathChain() {
        if (encryptionCertificatePathChain == null) {
            encryptionCertificatePathChain = new ArrayList<EncryptionCertificatePathChainType>();
        }
        return this.encryptionCertificatePathChain;
    }

    /**
     * Gets the value of the encryptionSymmetricAlgorithm property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the encryptionSymmetricAlgorithm property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getEncryptionSymmetricAlgorithm().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link EncryptionSymmetricAlgorithmType }
     * 
     * 
     */
    public List<EncryptionSymmetricAlgorithmType> getEncryptionSymmetricAlgorithm() {
        if (encryptionSymmetricAlgorithm == null) {
            encryptionSymmetricAlgorithm = new ArrayList<EncryptionSymmetricAlgorithmType>();
        }
        return this.encryptionSymmetricAlgorithm;
    }

}
