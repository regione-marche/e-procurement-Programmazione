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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.FinancingIdentifierType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FundingProgramCodeType;


/**
 * &lt;p&gt;Classe Java per FundingType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="FundingType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}FinancingIdentifier" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}FundingProgramCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Description" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FundingType", propOrder = {
    "financingIdentifier",
    "fundingProgramCode",
    "description"
})
public class FundingType {

    @XmlElement(name = "FinancingIdentifier", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected FinancingIdentifierType financingIdentifier;
    @XmlElement(name = "FundingProgramCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected FundingProgramCodeType fundingProgramCode;
    @XmlElement(name = "Description", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DescriptionType> description;

    /**
     * Recupera il valore della proprietà financingIdentifier.
     * 
     * @return
     *     possible object is
     *     {@link FinancingIdentifierType }
     *     
     */
    public FinancingIdentifierType getFinancingIdentifier() {
        return financingIdentifier;
    }

    /**
     * Imposta il valore della proprietà financingIdentifier.
     * 
     * @param value
     *     allowed object is
     *     {@link FinancingIdentifierType }
     *     
     */
    public void setFinancingIdentifier(FinancingIdentifierType value) {
        this.financingIdentifier = value;
    }

    /**
     * Recupera il valore della proprietà fundingProgramCode.
     * 
     * @return
     *     possible object is
     *     {@link FundingProgramCodeType }
     *     
     */
    public FundingProgramCodeType getFundingProgramCode() {
        return fundingProgramCode;
    }

    /**
     * Imposta il valore della proprietà fundingProgramCode.
     * 
     * @param value
     *     allowed object is
     *     {@link FundingProgramCodeType }
     *     
     */
    public void setFundingProgramCode(FundingProgramCodeType value) {
        this.fundingProgramCode = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the description property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DescriptionType }
     * 
     * 
     */
    public List<DescriptionType> getDescription() {
        if (description == null) {
            description = new ArrayList<DescriptionType>();
        }
        return this.description;
    }

}
