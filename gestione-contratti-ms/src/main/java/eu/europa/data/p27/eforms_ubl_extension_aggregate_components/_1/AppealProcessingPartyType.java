//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.AppealProcessingPartyTypeCodeType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.AppealProcessingPartyTypeDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyType;


/**
 * &lt;p&gt;Classe Java per AppealProcessingPartyType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="AppealProcessingPartyType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}AppealProcessingPartyTypeCode"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}AppealProcessingPartyTypeDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
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
@XmlType(name = "AppealProcessingPartyType", propOrder = {
    "appealProcessingPartyTypeCode",
    "appealProcessingPartyTypeDescription",
    "party"
})
public class AppealProcessingPartyType {

    @XmlElement(name = "AppealProcessingPartyTypeCode", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1", required = true)
    protected AppealProcessingPartyTypeCodeType appealProcessingPartyTypeCode;
    @XmlElement(name = "AppealProcessingPartyTypeDescription", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected List<AppealProcessingPartyTypeDescriptionType> appealProcessingPartyTypeDescription;
    @XmlElement(name = "Party", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2", required = true)
    protected PartyType party;

    /**
     * Recupera il valore della proprietà appealProcessingPartyTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link AppealProcessingPartyTypeCodeType }
     *     
     */
    public AppealProcessingPartyTypeCodeType getAppealProcessingPartyTypeCode() {
        return appealProcessingPartyTypeCode;
    }

    /**
     * Imposta il valore della proprietà appealProcessingPartyTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link AppealProcessingPartyTypeCodeType }
     *     
     */
    public void setAppealProcessingPartyTypeCode(AppealProcessingPartyTypeCodeType value) {
        this.appealProcessingPartyTypeCode = value;
    }

    /**
     * Gets the value of the appealProcessingPartyTypeDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the appealProcessingPartyTypeDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAppealProcessingPartyTypeDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AppealProcessingPartyTypeDescriptionType }
     * 
     * 
     */
    public List<AppealProcessingPartyTypeDescriptionType> getAppealProcessingPartyTypeDescription() {
        if (appealProcessingPartyTypeDescription == null) {
            appealProcessingPartyTypeDescription = new ArrayList<AppealProcessingPartyTypeDescriptionType>();
        }
        return this.appealProcessingPartyTypeDescription;
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
