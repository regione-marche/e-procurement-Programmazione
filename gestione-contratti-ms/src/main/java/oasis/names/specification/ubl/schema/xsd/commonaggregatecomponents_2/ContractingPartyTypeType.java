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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PartyTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PartyTypeType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per ContractingPartyTypeType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ContractingPartyTypeType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PartyTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PartyType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContractingPartyTypeType", propOrder = {
    "ublExtensions",
    "partyTypeCode",
    "partyType"
})
public class ContractingPartyTypeType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "PartyTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PartyTypeCodeType partyTypeCode;
    @XmlElement(name = "PartyType", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<PartyTypeType> partyType;

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
     * Recupera il valore della proprietà partyTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link PartyTypeCodeType }
     *     
     */
    public PartyTypeCodeType getPartyTypeCode() {
        return partyTypeCode;
    }

    /**
     * Imposta il valore della proprietà partyTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyTypeCodeType }
     *     
     */
    public void setPartyTypeCode(PartyTypeCodeType value) {
        this.partyTypeCode = value;
    }

    /**
     * Gets the value of the partyType property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the partyType property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPartyType().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PartyTypeType }
     * 
     * 
     */
    public List<PartyTypeType> getPartyType() {
        if (partyType == null) {
            partyType = new ArrayList<PartyTypeType>();
        }
        return this.partyType;
    }

}
