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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CopyIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DistributionTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DistributionTypeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DocumentTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumCopiesNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumOriginalsNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PrintQualifierType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per DocumentDistributionType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="DocumentDistributionType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DocumentTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DistributionTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DistributionType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PrintQualifier" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CopyIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MaximumCopiesNumeric" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MaximumOriginalsNumeric" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Communication" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Party"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentDistributionType", propOrder = {
    "ublExtensions",
    "id",
    "documentTypeCode",
    "distributionTypeCode",
    "distributionType",
    "printQualifier",
    "copyIndicator",
    "maximumCopiesNumeric",
    "maximumOriginalsNumeric",
    "communication",
    "party"
})
public class DocumentDistributionType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "DocumentTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected DocumentTypeCodeType documentTypeCode;
    @XmlElement(name = "DistributionTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected DistributionTypeCodeType distributionTypeCode;
    @XmlElement(name = "DistributionType", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DistributionTypeType> distributionType;
    @XmlElement(name = "PrintQualifier", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PrintQualifierType printQualifier;
    @XmlElement(name = "CopyIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CopyIndicatorType copyIndicator;
    @XmlElement(name = "MaximumCopiesNumeric", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MaximumCopiesNumericType maximumCopiesNumeric;
    @XmlElement(name = "MaximumOriginalsNumeric", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MaximumOriginalsNumericType maximumOriginalsNumeric;
    @XmlElement(name = "Communication")
    protected CommunicationType communication;
    @XmlElement(name = "Party", required = true)
    protected PartyType party;

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
     * Recupera il valore della proprietà documentTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link DocumentTypeCodeType }
     *     
     */
    public DocumentTypeCodeType getDocumentTypeCode() {
        return documentTypeCode;
    }

    /**
     * Imposta il valore della proprietà documentTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentTypeCodeType }
     *     
     */
    public void setDocumentTypeCode(DocumentTypeCodeType value) {
        this.documentTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà distributionTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link DistributionTypeCodeType }
     *     
     */
    public DistributionTypeCodeType getDistributionTypeCode() {
        return distributionTypeCode;
    }

    /**
     * Imposta il valore della proprietà distributionTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link DistributionTypeCodeType }
     *     
     */
    public void setDistributionTypeCode(DistributionTypeCodeType value) {
        this.distributionTypeCode = value;
    }

    /**
     * Gets the value of the distributionType property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the distributionType property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDistributionType().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DistributionTypeType }
     * 
     * 
     */
    public List<DistributionTypeType> getDistributionType() {
        if (distributionType == null) {
            distributionType = new ArrayList<DistributionTypeType>();
        }
        return this.distributionType;
    }

    /**
     * Recupera il valore della proprietà printQualifier.
     * 
     * @return
     *     possible object is
     *     {@link PrintQualifierType }
     *     
     */
    public PrintQualifierType getPrintQualifier() {
        return printQualifier;
    }

    /**
     * Imposta il valore della proprietà printQualifier.
     * 
     * @param value
     *     allowed object is
     *     {@link PrintQualifierType }
     *     
     */
    public void setPrintQualifier(PrintQualifierType value) {
        this.printQualifier = value;
    }

    /**
     * Recupera il valore della proprietà copyIndicator.
     * 
     * @return
     *     possible object is
     *     {@link CopyIndicatorType }
     *     
     */
    public CopyIndicatorType getCopyIndicator() {
        return copyIndicator;
    }

    /**
     * Imposta il valore della proprietà copyIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link CopyIndicatorType }
     *     
     */
    public void setCopyIndicator(CopyIndicatorType value) {
        this.copyIndicator = value;
    }

    /**
     * Recupera il valore della proprietà maximumCopiesNumeric.
     * 
     * @return
     *     possible object is
     *     {@link MaximumCopiesNumericType }
     *     
     */
    public MaximumCopiesNumericType getMaximumCopiesNumeric() {
        return maximumCopiesNumeric;
    }

    /**
     * Imposta il valore della proprietà maximumCopiesNumeric.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumCopiesNumericType }
     *     
     */
    public void setMaximumCopiesNumeric(MaximumCopiesNumericType value) {
        this.maximumCopiesNumeric = value;
    }

    /**
     * Recupera il valore della proprietà maximumOriginalsNumeric.
     * 
     * @return
     *     possible object is
     *     {@link MaximumOriginalsNumericType }
     *     
     */
    public MaximumOriginalsNumericType getMaximumOriginalsNumeric() {
        return maximumOriginalsNumeric;
    }

    /**
     * Imposta il valore della proprietà maximumOriginalsNumeric.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumOriginalsNumericType }
     *     
     */
    public void setMaximumOriginalsNumeric(MaximumOriginalsNumericType value) {
        this.maximumOriginalsNumeric = value;
    }

    /**
     * Recupera il valore della proprietà communication.
     * 
     * @return
     *     possible object is
     *     {@link CommunicationType }
     *     
     */
    public CommunicationType getCommunication() {
        return communication;
    }

    /**
     * Imposta il valore della proprietà communication.
     * 
     * @param value
     *     allowed object is
     *     {@link CommunicationType }
     *     
     */
    public void setCommunication(CommunicationType value) {
        this.communication = value;
    }

    /**
     * Recupera il valore della proprietà party.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getParty() {
        return party;
    }

    /**
     * Imposta il valore della proprietà party.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setParty(PartyType value) {
        this.party = value;
    }

}
