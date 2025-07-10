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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EndpointURIType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EnvelopeTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ProtocolIDType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per MessageDeliveryType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="MessageDeliveryType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ProtocolID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}EnvelopeTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}EndpointURI" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MessageDeliveryType", propOrder = {
    "ublExtensions",
    "protocolID",
    "envelopeTypeCode",
    "endpointURI"
})
public class MessageDeliveryType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ProtocolID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ProtocolIDType protocolID;
    @XmlElement(name = "EnvelopeTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected EnvelopeTypeCodeType envelopeTypeCode;
    @XmlElement(name = "EndpointURI", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected EndpointURIType endpointURI;

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
     * Recupera il valore della proprietà protocolID.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolIDType }
     *     
     */
    public ProtocolIDType getProtocolID() {
        return protocolID;
    }

    /**
     * Imposta il valore della proprietà protocolID.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolIDType }
     *     
     */
    public void setProtocolID(ProtocolIDType value) {
        this.protocolID = value;
    }

    /**
     * Recupera il valore della proprietà envelopeTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link EnvelopeTypeCodeType }
     *     
     */
    public EnvelopeTypeCodeType getEnvelopeTypeCode() {
        return envelopeTypeCode;
    }

    /**
     * Imposta il valore della proprietà envelopeTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link EnvelopeTypeCodeType }
     *     
     */
    public void setEnvelopeTypeCode(EnvelopeTypeCodeType value) {
        this.envelopeTypeCode = value;
    }

    /**
     * Recupera il valore della proprietà endpointURI.
     * 
     * @return
     *     possible object is
     *     {@link EndpointURIType }
     *     
     */
    public EndpointURIType getEndpointURI() {
        return endpointURI;
    }

    /**
     * Imposta il valore della proprietà endpointURI.
     * 
     * @param value
     *     allowed object is
     *     {@link EndpointURIType }
     *     
     */
    public void setEndpointURI(EndpointURIType value) {
        this.endpointURI = value;
    }

}
