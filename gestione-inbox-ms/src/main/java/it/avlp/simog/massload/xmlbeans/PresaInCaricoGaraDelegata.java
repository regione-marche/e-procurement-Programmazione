
package it.avlp.simog.massload.xmlbeans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per presaInCaricoGaraDelegata complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="presaInCaricoGaraDelegata"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ticket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="indexCollaborazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="idgara" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "presaInCaricoGaraDelegata", propOrder = {
    "ticket",
    "indexCollaborazione",
    "idgara"
})
public class PresaInCaricoGaraDelegata {

    protected String ticket;
    protected String indexCollaborazione;
    protected String idgara;

    /**
     * Recupera il valore della proprietà ticket.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * Imposta il valore della proprietà ticket.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicket(String value) {
        this.ticket = value;
    }

    /**
     * Recupera il valore della proprietà indexCollaborazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndexCollaborazione() {
        return indexCollaborazione;
    }

    /**
     * Imposta il valore della proprietà indexCollaborazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndexCollaborazione(String value) {
        this.indexCollaborazione = value;
    }

    /**
     * Recupera il valore della proprietà idgara.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdgara() {
        return idgara;
    }

    /**
     * Imposta il valore della proprietà idgara.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdgara(String value) {
        this.idgara = value;
    }

}
