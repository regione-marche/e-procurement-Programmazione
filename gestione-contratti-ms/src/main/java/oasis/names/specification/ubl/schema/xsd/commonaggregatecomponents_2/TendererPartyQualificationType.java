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
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per TendererPartyQualificationType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TendererPartyQualificationType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}InterestedProcurementProjectLot" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}MainQualifyingParty"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AdditionalQualifyingParty" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TendererPartyQualificationType", propOrder = {
    "ublExtensions",
    "interestedProcurementProjectLot",
    "mainQualifyingParty",
    "additionalQualifyingParty"
})
public class TendererPartyQualificationType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "InterestedProcurementProjectLot")
    protected List<ProcurementProjectLotType> interestedProcurementProjectLot;
    @XmlElement(name = "MainQualifyingParty", required = true)
    protected QualifyingPartyType mainQualifyingParty;
    @XmlElement(name = "AdditionalQualifyingParty")
    protected List<QualifyingPartyType> additionalQualifyingParty;

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
     * Gets the value of the interestedProcurementProjectLot property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the interestedProcurementProjectLot property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getInterestedProcurementProjectLot().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ProcurementProjectLotType }
     * 
     * 
     */
    public List<ProcurementProjectLotType> getInterestedProcurementProjectLot() {
        if (interestedProcurementProjectLot == null) {
            interestedProcurementProjectLot = new ArrayList<ProcurementProjectLotType>();
        }
        return this.interestedProcurementProjectLot;
    }

    /**
     * Recupera il valore della proprietà mainQualifyingParty.
     * 
     * @return
     *     possible object is
     *     {@link QualifyingPartyType }
     *     
     */
    public QualifyingPartyType getMainQualifyingParty() {
        return mainQualifyingParty;
    }

    /**
     * Imposta il valore della proprietà mainQualifyingParty.
     * 
     * @param value
     *     allowed object is
     *     {@link QualifyingPartyType }
     *     
     */
    public void setMainQualifyingParty(QualifyingPartyType value) {
        this.mainQualifyingParty = value;
    }

    /**
     * Gets the value of the additionalQualifyingParty property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the additionalQualifyingParty property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAdditionalQualifyingParty().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link QualifyingPartyType }
     * 
     * 
     */
    public List<QualifyingPartyType> getAdditionalQualifyingParty() {
        if (additionalQualifyingParty == null) {
            additionalQualifyingParty = new ArrayList<QualifyingPartyType>();
        }
        return this.additionalQualifyingParty;
    }

}
