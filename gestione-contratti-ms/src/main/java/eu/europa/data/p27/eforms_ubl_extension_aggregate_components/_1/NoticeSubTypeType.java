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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.SubTypeDescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SubTypeCodeType;


/**
 * &lt;p&gt;Classe Java per NoticeSubTypeType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="NoticeSubTypeType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SubTypeCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}SubTypeDescription" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NoticeSubTypeType", propOrder = {
    "subTypeCode",
    "subTypeDescription"
})
public class NoticeSubTypeType {

    @XmlElement(name = "SubTypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SubTypeCodeType subTypeCode;
    @XmlElement(name = "SubTypeDescription", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected List<SubTypeDescriptionType> subTypeDescription;

    /**
     * Recupera il valore della proprietà subTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link SubTypeCodeType }
     *     
     */
    public SubTypeCodeType getSubTypeCode() {
        return subTypeCode;
    }

    /**
     * Imposta il valore della proprietà subTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link SubTypeCodeType }
     *     
     */
    public void setSubTypeCode(SubTypeCodeType value) {
        this.subTypeCode = value;
    }

    /**
     * Gets the value of the subTypeDescription property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the subTypeDescription property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSubTypeDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link SubTypeDescriptionType }
     * 
     * 
     */
    public List<SubTypeDescriptionType> getSubTypeDescription() {
        if (subTypeDescription == null) {
            subTypeDescription = new ArrayList<SubTypeDescriptionType>();
        }
        return this.subTypeDescription;
    }

}
