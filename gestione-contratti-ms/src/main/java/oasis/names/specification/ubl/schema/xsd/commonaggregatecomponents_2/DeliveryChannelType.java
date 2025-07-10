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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NetworkIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ParticipantIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TestIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per DeliveryChannelType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="DeliveryChannelType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}NetworkID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ParticipantID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TestIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DigitalCertificate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DigitalMessageDelivery" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeliveryChannelType", propOrder = {
    "ublExtensions",
    "networkID",
    "participantID",
    "testIndicator",
    "digitalCertificate",
    "digitalMessageDelivery"
})
public class DeliveryChannelType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "NetworkID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NetworkIDType networkID;
    @XmlElement(name = "ParticipantID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ParticipantIDType participantID;
    @XmlElement(name = "TestIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TestIndicatorType testIndicator;
    @XmlElement(name = "DigitalCertificate")
    protected CertificateType digitalCertificate;
    @XmlElement(name = "DigitalMessageDelivery")
    protected MessageDeliveryType digitalMessageDelivery;

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
     * Recupera il valore della proprietà participantID.
     * 
     * @return
     *     possible object is
     *     {@link ParticipantIDType }
     *     
     */
    public ParticipantIDType getParticipantID() {
        return participantID;
    }

    /**
     * Imposta il valore della proprietà participantID.
     * 
     * @param value
     *     allowed object is
     *     {@link ParticipantIDType }
     *     
     */
    public void setParticipantID(ParticipantIDType value) {
        this.participantID = value;
    }

    /**
     * Recupera il valore della proprietà testIndicator.
     * 
     * @return
     *     possible object is
     *     {@link TestIndicatorType }
     *     
     */
    public TestIndicatorType getTestIndicator() {
        return testIndicator;
    }

    /**
     * Imposta il valore della proprietà testIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link TestIndicatorType }
     *     
     */
    public void setTestIndicator(TestIndicatorType value) {
        this.testIndicator = value;
    }

    /**
     * Recupera il valore della proprietà digitalCertificate.
     * 
     * @return
     *     possible object is
     *     {@link CertificateType }
     *     
     */
    public CertificateType getDigitalCertificate() {
        return digitalCertificate;
    }

    /**
     * Imposta il valore della proprietà digitalCertificate.
     * 
     * @param value
     *     allowed object is
     *     {@link CertificateType }
     *     
     */
    public void setDigitalCertificate(CertificateType value) {
        this.digitalCertificate = value;
    }

    /**
     * Recupera il valore della proprietà digitalMessageDelivery.
     * 
     * @return
     *     possible object is
     *     {@link MessageDeliveryType }
     *     
     */
    public MessageDeliveryType getDigitalMessageDelivery() {
        return digitalMessageDelivery;
    }

    /**
     * Imposta il valore della proprietà digitalMessageDelivery.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageDeliveryType }
     *     
     */
    public void setDigitalMessageDelivery(MessageDeliveryType value) {
        this.digitalMessageDelivery = value;
    }

}
