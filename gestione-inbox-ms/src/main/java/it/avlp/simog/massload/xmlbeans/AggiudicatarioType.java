
package it.avlp.simog.massload.xmlbeans;

/**
 * <p>Classe Java per AggiudicatarioType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AggiudicatarioType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}SOGGETTO_ESTERO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CODICE_STATO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CODICE_FISCALE_AGGIUDICATARIO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DENOMINAZIONE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}COGNOME use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}NOME use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CAMERA_COMMERCIO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}PARTITA_IVA use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CF_RAPPRESENTANTE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}INDIRIZZO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CIVICO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CAP use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CITTA use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}PROVINCIA use="required""/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class AggiudicatarioType {

    protected FlagSNType soggettoestero;
    protected String codicestato;
    protected String codicefiscaleaggiudicatario;
    protected String denominazione;
    protected String cognome;
    protected String nome;
    protected String cameracommercio;
    protected String partitaiva;
    protected String cfrappresentante;
    protected String indirizzo;
    protected String civico;
    protected String cap;
    protected String citta;
    protected String provincia;

    /**
     * Recupera il valore della proprietà soggettoestero.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getSOGGETTOESTERO() {
        return soggettoestero;
    }

    /**
     * Imposta il valore della proprietà soggettoestero.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setSOGGETTOESTERO(FlagSNType value) {
        this.soggettoestero = value;
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
     * Recupera il valore della proprietà codicefiscaleaggiudicatario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICEFISCALEAGGIUDICATARIO() {
        return codicefiscaleaggiudicatario;
    }

    /**
     * Imposta il valore della proprietà codicefiscaleaggiudicatario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICEFISCALEAGGIUDICATARIO(String value) {
        this.codicefiscaleaggiudicatario = value;
    }

    /**
     * Recupera il valore della proprietà denominazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDENOMINAZIONE() {
        return denominazione;
    }

    /**
     * Imposta il valore della proprietà denominazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDENOMINAZIONE(String value) {
        this.denominazione = value;
    }

    /**
     * Recupera il valore della proprietà cognome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOGNOME() {
        return cognome;
    }

    /**
     * Imposta il valore della proprietà cognome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOGNOME(String value) {
        this.cognome = value;
    }

    /**
     * Recupera il valore della proprietà nome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOME() {
        return nome;
    }

    /**
     * Imposta il valore della proprietà nome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOME(String value) {
        this.nome = value;
    }

    /**
     * Recupera il valore della proprietà cameracommercio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCAMERACOMMERCIO() {
        return cameracommercio;
    }

    /**
     * Imposta il valore della proprietà cameracommercio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCAMERACOMMERCIO(String value) {
        this.cameracommercio = value;
    }

    /**
     * Recupera il valore della proprietà partitaiva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPARTITAIVA() {
        return partitaiva;
    }

    /**
     * Imposta il valore della proprietà partitaiva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPARTITAIVA(String value) {
        this.partitaiva = value;
    }

    /**
     * Recupera il valore della proprietà cfrappresentante.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCFRAPPRESENTANTE() {
        return cfrappresentante;
    }

    /**
     * Imposta il valore della proprietà cfrappresentante.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCFRAPPRESENTANTE(String value) {
        this.cfrappresentante = value;
    }

    /**
     * Recupera il valore della proprietà indirizzo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINDIRIZZO() {
        return indirizzo;
    }

    /**
     * Imposta il valore della proprietà indirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINDIRIZZO(String value) {
        this.indirizzo = value;
    }

    /**
     * Recupera il valore della proprietà civico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCIVICO() {
        return civico;
    }

    /**
     * Imposta il valore della proprietà civico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCIVICO(String value) {
        this.civico = value;
    }

    /**
     * Recupera il valore della proprietà cap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCAP() {
        return cap;
    }

    /**
     * Imposta il valore della proprietà cap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCAP(String value) {
        this.cap = value;
    }

    /**
     * Recupera il valore della proprietà citta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCITTA() {
        return citta;
    }

    /**
     * Imposta il valore della proprietà citta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCITTA(String value) {
        this.citta = value;
    }

    /**
     * Recupera il valore della proprietà provincia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIA() {
        return provincia;
    }

    /**
     * Imposta il valore della proprietà provincia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIA(String value) {
        this.provincia = value;
    }

}
