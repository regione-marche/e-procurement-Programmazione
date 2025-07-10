
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per IncaricatoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IncaricatoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}SEZIONE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_RUOLO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CIG_PROG_ESTERNA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_AFF_PROG_ESTERNA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_CONS_PROG_ESTERNA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CODICE_FISCALE_RESPONSABILE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CODICE_STATO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}PERSONA_GIURIDICA"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class IncaricatoType {

    protected SezioneType sezione;
    protected String idruolo;
    protected String cigprogesterna;
    protected XMLGregorianCalendar dataaffprogesterna;
    protected XMLGregorianCalendar dataconsprogesterna;
    protected String codicefiscaleresponsabile;
    protected String codicestato;
    protected FlagSNType personagiuridica;
    protected Integer idgruppoincaricato;
    protected FlagSNType mandante;


    /**
     * Recupera il valore della proprietà sezione.
     * 
     * @return
     *     possible object is
     *     {@link SezioneType }
     *     
     */
    public SezioneType getSEZIONE() {
        return sezione;
    }

    /**
     * Imposta il valore della proprietà sezione.
     * 
     * @param value
     *     allowed object is
     *     {@link SezioneType }
     *     
     */
    public void setSEZIONE(SezioneType value) {
        this.sezione = value;
    }

    /**
     * Recupera il valore della proprietà idruolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDRUOLO() {
        return idruolo;
    }

    /**
     * Imposta il valore della proprietà idruolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDRUOLO(String value) {
        this.idruolo = value;
    }

    /**
     * Recupera il valore della proprietà cigprogesterna.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCIGPROGESTERNA() {
        return cigprogesterna;
    }

    /**
     * Imposta il valore della proprietà cigprogesterna.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCIGPROGESTERNA(String value) {
        this.cigprogesterna = value;
    }

    /**
     * Recupera il valore della proprietà dataaffprogesterna.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAAFFPROGESTERNA() {
        return dataaffprogesterna;
    }

    /**
     * Imposta il valore della proprietà dataaffprogesterna.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAAFFPROGESTERNA(XMLGregorianCalendar value) {
        this.dataaffprogesterna = value;
    }

    /**
     * Recupera il valore della proprietà dataconsprogesterna.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATACONSPROGESTERNA() {
        return dataconsprogesterna;
    }

    /**
     * Imposta il valore della proprietà dataconsprogesterna.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATACONSPROGESTERNA(XMLGregorianCalendar value) {
        this.dataconsprogesterna = value;
    }

    /**
     * Recupera il valore della proprietà codicefiscaleresponsabile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICEFISCALERESPONSABILE() {
        return codicefiscaleresponsabile;
    }

    /**
     * Imposta il valore della proprietà codicefiscaleresponsabile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICEFISCALERESPONSABILE(String value) {
        this.codicefiscaleresponsabile = value;
    }

    /**
     * Recupera il valore della proprietà codicestato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICESTATO() {
        return codicestato;
    }

    /**
     * Imposta il valore della proprietà codicestato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICESTATO(String value) {
        this.codicestato = value;
    }

    /**
     * Recupera il valore della proprietà personagiuridica.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getPERSONAGIURIDICA() {
        return personagiuridica;
    }

    /**
     * Imposta il valore della proprietà personagiuridica.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setPERSONAGIURIDICA(FlagSNType value) {
        this.personagiuridica = value;
    }
    
    /**
     * Recupera il valore della proprietà idgruppoincaricato.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIDGRUPPOINCARICATO() {
        return idgruppoincaricato;
    }

    /**
     * Imposta il valore della proprietà idgruppoincaricato.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIDGRUPPOINCARICATO(Integer value) {
        this.idgruppoincaricato = value;
    }

    /**
     * Recupera il valore della proprietà mandante.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getMANDANTE() {
        return mandante;
    }

    /**
     * Imposta il valore della proprietà mandante.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setMANDANTE(FlagSNType value) {
        this.mandante = value;
    }

}
