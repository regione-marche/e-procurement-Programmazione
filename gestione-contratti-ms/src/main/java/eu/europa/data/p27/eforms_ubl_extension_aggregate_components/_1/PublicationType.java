//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.GazetteIDType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.NoticePublicationIDType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.PublicationDateType;


/**
 * &lt;p&gt;Classe Java per PublicationType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="PublicationType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}NoticePublicationID"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}GazetteID"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}PublicationDate"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PublicationType", propOrder = {
    "noticePublicationID",
    "gazetteID",
    "publicationDate"
})
public class PublicationType {

    @XmlElement(name = "NoticePublicationID", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1", required = true)
    protected NoticePublicationIDType noticePublicationID;
    @XmlElement(name = "GazetteID", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1", required = true)
    protected GazetteIDType gazetteID;
    @XmlElement(name = "PublicationDate", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1", required = true)
    protected PublicationDateType publicationDate;

    /**
     * Recupera il valore della proprietà noticePublicationID.
     * 
     * @return
     *     possible object is
     *     {@link NoticePublicationIDType }
     *     
     */
    public NoticePublicationIDType getNoticePublicationID() {
        return noticePublicationID;
    }

    /**
     * Imposta il valore della proprietà noticePublicationID.
     * 
     * @param value
     *     allowed object is
     *     {@link NoticePublicationIDType }
     *     
     */
    public void setNoticePublicationID(NoticePublicationIDType value) {
        this.noticePublicationID = value;
    }

    /**
     * Recupera il valore della proprietà gazetteID.
     * 
     * @return
     *     possible object is
     *     {@link GazetteIDType }
     *     
     */
    public GazetteIDType getGazetteID() {
        return gazetteID;
    }

    /**
     * Imposta il valore della proprietà gazetteID.
     * 
     * @param value
     *     allowed object is
     *     {@link GazetteIDType }
     *     
     */
    public void setGazetteID(GazetteIDType value) {
        this.gazetteID = value;
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
