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
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ChangedNoticeIdentifierType;


/**
 * &lt;p&gt;Classe Java per ChangesType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ChangesType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}ChangedNoticeIdentifier" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}Change" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}ChangeReason" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChangesType", propOrder = {
    "changedNoticeIdentifier",
    "change",
    "changeReason"
})
public class ChangesType {

    @XmlElement(name = "ChangedNoticeIdentifier", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected ChangedNoticeIdentifierType changedNoticeIdentifier;
    @XmlElement(name = "Change")
    protected List<ChangeType> change;
    @XmlElement(name = "ChangeReason")
    protected ReasonType changeReason;

    /**
     * Recupera il valore della proprietà changedNoticeIdentifier.
     * 
     * @return
     *     possible object is
     *     {@link ChangedNoticeIdentifierType }
     *     
     */
    public ChangedNoticeIdentifierType getChangedNoticeIdentifier() {
        return changedNoticeIdentifier;
    }

    /**
     * Imposta il valore della proprietà changedNoticeIdentifier.
     * 
     * @param value
     *     allowed object is
     *     {@link ChangedNoticeIdentifierType }
     *     
     */
    public void setChangedNoticeIdentifier(ChangedNoticeIdentifierType value) {
        this.changedNoticeIdentifier = value;
    }

    /**
     * Gets the value of the change property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the change property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getChange().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ChangeType }
     * 
     * 
     */
    public List<ChangeType> getChange() {
        if (change == null) {
            change = new ArrayList<ChangeType>();
        }
        return this.change;
    }

    /**
     * Recupera il valore della proprietà changeReason.
     * 
     * @return
     *     possible object is
     *     {@link ReasonType }
     *     
     */
    public ReasonType getChangeReason() {
        return changeReason;
    }

    /**
     * Imposta il valore della proprietà changeReason.
     * 
     * @param value
     *     allowed object is
     *     {@link ReasonType }
     *     
     */
    public void setChangeReason(ReasonType value) {
        this.changeReason = value;
    }

}
