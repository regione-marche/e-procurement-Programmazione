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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InitiatingPartyIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PrivatePartyIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PublicPartyIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ServiceProviderPartyIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per ParticipantPartyType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ParticipantPartyType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}InitiatingPartyIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PrivatePartyIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PublicPartyIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ServiceProviderPartyIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Party"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}LegalContact" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TechnicalContact" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SupportContact" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CommercialContact" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParticipantPartyType", propOrder = {
    "ublExtensions",
    "initiatingPartyIndicator",
    "privatePartyIndicator",
    "publicPartyIndicator",
    "serviceProviderPartyIndicator",
    "party",
    "legalContact",
    "technicalContact",
    "supportContact",
    "commercialContact"
})
public class ParticipantPartyType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "InitiatingPartyIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected InitiatingPartyIndicatorType initiatingPartyIndicator;
    @XmlElement(name = "PrivatePartyIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PrivatePartyIndicatorType privatePartyIndicator;
    @XmlElement(name = "PublicPartyIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PublicPartyIndicatorType publicPartyIndicator;
    @XmlElement(name = "ServiceProviderPartyIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ServiceProviderPartyIndicatorType serviceProviderPartyIndicator;
    @XmlElement(name = "Party", required = true)
    protected PartyType party;
    @XmlElement(name = "LegalContact")
    protected ContactType legalContact;
    @XmlElement(name = "TechnicalContact")
    protected ContactType technicalContact;
    @XmlElement(name = "SupportContact")
    protected ContactType supportContact;
    @XmlElement(name = "CommercialContact")
    protected ContactType commercialContact;

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
     * Recupera il valore della proprietà initiatingPartyIndicator.
     * 
     * @return
     *     possible object is
     *     {@link InitiatingPartyIndicatorType }
     *     
     */
    public InitiatingPartyIndicatorType getInitiatingPartyIndicator() {
        return initiatingPartyIndicator;
    }

    /**
     * Imposta il valore della proprietà initiatingPartyIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link InitiatingPartyIndicatorType }
     *     
     */
    public void setInitiatingPartyIndicator(InitiatingPartyIndicatorType value) {
        this.initiatingPartyIndicator = value;
    }

    /**
     * Recupera il valore della proprietà privatePartyIndicator.
     * 
     * @return
     *     possible object is
     *     {@link PrivatePartyIndicatorType }
     *     
     */
    public PrivatePartyIndicatorType getPrivatePartyIndicator() {
        return privatePartyIndicator;
    }

    /**
     * Imposta il valore della proprietà privatePartyIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link PrivatePartyIndicatorType }
     *     
     */
    public void setPrivatePartyIndicator(PrivatePartyIndicatorType value) {
        this.privatePartyIndicator = value;
    }

    /**
     * Recupera il valore della proprietà publicPartyIndicator.
     * 
     * @return
     *     possible object is
     *     {@link PublicPartyIndicatorType }
     *     
     */
    public PublicPartyIndicatorType getPublicPartyIndicator() {
        return publicPartyIndicator;
    }

    /**
     * Imposta il valore della proprietà publicPartyIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link PublicPartyIndicatorType }
     *     
     */
    public void setPublicPartyIndicator(PublicPartyIndicatorType value) {
        this.publicPartyIndicator = value;
    }

    /**
     * Recupera il valore della proprietà serviceProviderPartyIndicator.
     * 
     * @return
     *     possible object is
     *     {@link ServiceProviderPartyIndicatorType }
     *     
     */
    public ServiceProviderPartyIndicatorType getServiceProviderPartyIndicator() {
        return serviceProviderPartyIndicator;
    }

    /**
     * Imposta il valore della proprietà serviceProviderPartyIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceProviderPartyIndicatorType }
     *     
     */
    public void setServiceProviderPartyIndicator(ServiceProviderPartyIndicatorType value) {
        this.serviceProviderPartyIndicator = value;
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

    /**
     * Recupera il valore della proprietà legalContact.
     * 
     * @return
     *     possible object is
     *     {@link ContactType }
     *     
     */
    public ContactType getLegalContact() {
        return legalContact;
    }

    /**
     * Imposta il valore della proprietà legalContact.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactType }
     *     
     */
    public void setLegalContact(ContactType value) {
        this.legalContact = value;
    }

    /**
     * Recupera il valore della proprietà technicalContact.
     * 
     * @return
     *     possible object is
     *     {@link ContactType }
     *     
     */
    public ContactType getTechnicalContact() {
        return technicalContact;
    }

    /**
     * Imposta il valore della proprietà technicalContact.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactType }
     *     
     */
    public void setTechnicalContact(ContactType value) {
        this.technicalContact = value;
    }

    /**
     * Recupera il valore della proprietà supportContact.
     * 
     * @return
     *     possible object is
     *     {@link ContactType }
     *     
     */
    public ContactType getSupportContact() {
        return supportContact;
    }

    /**
     * Imposta il valore della proprietà supportContact.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactType }
     *     
     */
    public void setSupportContact(ContactType value) {
        this.supportContact = value;
    }

    /**
     * Recupera il valore della proprietà commercialContact.
     * 
     * @return
     *     possible object is
     *     {@link ContactType }
     *     
     */
    public ContactType getCommercialContact() {
        return commercialContact;
    }

    /**
     * Imposta il valore della proprietà commercialContact.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactType }
     *     
     */
    public void setCommercialContact(ContactType value) {
        this.commercialContact = value;
    }

}
