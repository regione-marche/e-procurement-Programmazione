//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.11 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2019.10.03 alle 11:25:07 AM CEST 
//


package it.avlp.simog.massload.xmlbeans;

/**
 * <p>Classe Java per cancellaGara complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="cancellaGara"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ticket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="indexCollaborazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="id_gara" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="id_motivazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="note_canc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class CancellaGara {

    protected String ticket;
    protected String indexCollaborazione;
    protected String idGara;
    protected String idMotivazione;
    protected String noteCanc;

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
     * Recupera il valore della proprietà idGara.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdGara() {
        return idGara;
    }

    /**
     * Imposta il valore della proprietà idGara.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdGara(String value) {
        this.idGara = value;
    }

    /**
     * Recupera il valore della proprietà idMotivazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdMotivazione() {
        return idMotivazione;
    }

    /**
     * Imposta il valore della proprietà idMotivazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdMotivazione(String value) {
        this.idMotivazione = value;
    }

    /**
     * Recupera il valore della proprietà noteCanc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoteCanc() {
        return noteCanc;
    }

    /**
     * Imposta il valore della proprietà noteCanc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoteCanc(String value) {
        this.noteCanc = value;
    }

}
