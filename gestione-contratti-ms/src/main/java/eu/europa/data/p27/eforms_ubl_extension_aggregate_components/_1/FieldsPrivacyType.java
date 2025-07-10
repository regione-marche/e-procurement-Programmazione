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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.FieldIdentifierCodeType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.PublicationDateType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ReasonDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReasonCodeType;


/**
 * &lt;p&gt;Classe Java per FieldsPrivacyType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="FieldsPrivacyType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}FieldIdentifierCode" maxOccurs="unbounded"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ReasonCode"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}ReasonDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}PublicationDate" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FieldsPrivacyType", propOrder = {
    "fieldIdentifierCode",
    "reasonCode",
    "reasonDescription",
    "publicationDate"
})
public class FieldsPrivacyType {

    @XmlElement(name = "FieldIdentifierCode", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1", required = true)
    protected List<FieldIdentifierCodeType> fieldIdentifierCode;
    @XmlElement(name = "ReasonCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected ReasonCodeType reasonCode;
    @XmlElement(name = "ReasonDescription", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected List<ReasonDescriptionType> reasonDescription;
    @XmlElement(name = "PublicationDate", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected PublicationDateType publicationDate;

    /**
     * Gets the value of the fieldIdentifierCode property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the fieldIdentifierCode property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getFieldIdentifierCode().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link FieldIdentifierCodeType }
     * 
     * 
     */
    public List<FieldIdentifierCodeType> getFieldIdentifierCode() {
        if (fieldIdentifierCode == null) {
            fieldIdentifierCode = new ArrayList<FieldIdentifierCodeType>();
        }
        return this.fieldIdentifierCode;
    }

    /**
     * Recupera il valore della proprietà reasonCode.
     * 
     * @return
     *     possible object is
     *     {@link ReasonCodeType }
     *     
     */
    public ReasonCodeType getReasonCode() {
        return reasonCode;
    }

    /**
     * Imposta il valore della proprietà reasonCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ReasonCodeType }
     *     
     */
    public void setReasonCode(ReasonCodeType value) {
        this.reasonCode = value;
    }

    /**
     * Gets the value of the reasonDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the reasonDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getReasonDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ReasonDescriptionType }
     * 
     * 
     */
    public List<ReasonDescriptionType> getReasonDescription() {
        if (reasonDescription == null) {
            reasonDescription = new ArrayList<ReasonDescriptionType>();
        }
        return this.reasonDescription;
    }

    /**
     * Recupera il valore della proprietà publicationDate.
     * 
     * @return
     *     possible object is
     *     {@link PublicationDateType }
     *     
     */
    public PublicationDateType getPublicationDate() {
        return publicationDate;
    }

    /**
     * Imposta il valore della proprietà publicationDate.
     * 
     * @param value
     *     allowed object is
     *     {@link PublicationDateType }
     *     
     */
    public void setPublicationDate(PublicationDateType value) {
        this.publicationDate = value;
    }

}
