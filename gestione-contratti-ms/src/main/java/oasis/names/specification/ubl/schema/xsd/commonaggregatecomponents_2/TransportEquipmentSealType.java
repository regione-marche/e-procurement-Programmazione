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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ConditionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SealIssuerTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SealStatusCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SealingPartyTypeType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per TransportEquipmentSealType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TransportEquipmentSealType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SealIssuerTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Condition" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SealStatusCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SealingPartyType" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransportEquipmentSealType", propOrder = {
    "ublExtensions",
    "id",
    "sealIssuerTypeCode",
    "condition",
    "sealStatusCode",
    "sealingPartyType"
})
public class TransportEquipmentSealType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected IDType id;
    @XmlElement(name = "SealIssuerTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SealIssuerTypeCodeType sealIssuerTypeCode;
    @XmlElement(name = "Condition", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ConditionType condition;
    @XmlElement(name = "SealStatusCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SealStatusCodeType sealStatusCode;
    @XmlElement(name = "SealingPartyType", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SealingPartyTypeType sealingPartyType;

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
     * Recupera il valore della proprietà sealIssuerTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link SealIssuerTypeCodeType }
     *     
     */
    public SealIssuerTypeCodeType getSealIssuerTypeCode() {
        return sealIssuerTypeCode;
    }

    /**
     * Imposta il valore della proprietà sealIssuerTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link SealIssuerTypeCodeType }
     *     
     */
    public void setSealIssuerTypeCode(SealIssuerTypeCodeType value) {
        this.sealIssuerTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà condition.
     * 
     * @return
     *     possible object is
     *     {@link ConditionType }
     *     
     */
    public ConditionType getCondition() {
        return condition;
    }

    /**
     * Imposta il valore della proprietà condition.
     * 
     * @param value
     *     allowed object is
     *     {@link ConditionType }
     *     
     */
    public void setCondition(ConditionType value) {
        this.condition = value;
    }

    /**
     * Recupera il valore della proprietà sealStatusCode.
     * 
     * @return
     *     possible object is
     *     {@link SealStatusCodeType }
     *     
     */
    public SealStatusCodeType getSealStatusCode() {
        return sealStatusCode;
    }

    /**
     * Imposta il valore della proprietà sealStatusCode.
     * 
     * @param value
     *     allowed object is
     *     {@link SealStatusCodeType }
     *     
     */
    public void setSealStatusCode(SealStatusCodeType value) {
        this.sealStatusCode = value;
    }

    /**
     * Recupera il valore della proprietà sealingPartyType.
     * 
     * @return
     *     possible object is
     *     {@link SealingPartyTypeType }
     *     
     */
    public SealingPartyTypeType getSealingPartyType() {
        return sealingPartyType;
    }

    /**
     * Imposta il valore della proprietà sealingPartyType.
     * 
     * @param value
     *     allowed object is
     *     {@link SealingPartyTypeType }
     *     
     */
    public void setSealingPartyType(SealingPartyTypeType value) {
        this.sealingPartyType = value;
    }

}
