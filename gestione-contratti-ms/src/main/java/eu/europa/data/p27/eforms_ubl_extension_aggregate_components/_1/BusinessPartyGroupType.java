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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.GroupTypeCodeType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.GroupTypeType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyType;


/**
 * &lt;p&gt;Classe Java per BusinessPartyGroupType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="BusinessPartyGroupType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}GroupTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}GroupType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Party" maxOccurs="unbounded"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessPartyGroupType", propOrder = {
    "groupTypeCode",
    "groupType",
    "party"
})
public class BusinessPartyGroupType {

    @XmlElement(name = "GroupTypeCode", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected GroupTypeCodeType groupTypeCode;
    @XmlElement(name = "GroupType", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected List<GroupTypeType> groupType;
    @XmlElement(name = "Party", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2", required = true)
    protected List<PartyType> party;

    /**
     * Recupera il valore della proprietà groupTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link GroupTypeCodeType }
     *     
     */
    public GroupTypeCodeType getGroupTypeCode() {
        return groupTypeCode;
    }

    /**
     * Imposta il valore della proprietà groupTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupTypeCodeType }
     *     
     */
    public void setGroupTypeCode(GroupTypeCodeType value) {
        this.groupTypeCode = value;
    }

    /**
     * Gets the value of the groupType property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the groupType property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getGroupType().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link GroupTypeType }
     * 
     * 
     */
    public List<GroupTypeType> getGroupType() {
        if (groupType == null) {
            groupType = new ArrayList<GroupTypeType>();
        }
        return this.groupType;
    }

    /**
     * Gets the value of the party property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the party property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getParty().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PartyType }
     * 
     * 
     */
    public List<PartyType> getParty() {
        if (party == null) {
            party = new ArrayList<PartyType>();
        }
        return this.party;
    }

}
