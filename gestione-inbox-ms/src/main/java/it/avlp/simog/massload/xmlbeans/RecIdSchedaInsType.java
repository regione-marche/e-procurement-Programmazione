
package it.avlp.simog.massload.xmlbeans;

/**
 * <p>Classe Java per RecIdSchedaInsType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RecIdSchedaInsType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}SCHEDA use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_SCHEDA_SIMOG use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_SCHEDA_LOCALE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}OPERAZIONE use="required""/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class RecIdSchedaInsType {

    protected TipiSchedeType scheda;
    protected String idschedasimog;
    protected String idschedalocale;
    protected TipiOperazioneType operazione;

    /**
     * Recupera il valore della proprietà scheda.
     * 
     * @return
     *     possible object is
     *     {@link TipiSchedeType }
     *     
     */
    public TipiSchedeType getSCHEDA() {
        return scheda;
    }

    /**
     * Imposta il valore della proprietà scheda.
     * 
     * @param value
     *     allowed object is
     *     {@link TipiSchedeType }
     *     
     */
    public void setSCHEDA(TipiSchedeType value) {
        this.scheda = value;
    }

    /**
     * Recupera il valore della proprietà idschedasimog.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDSCHEDASIMOG() {
        return idschedasimog;
    }

    /**
     * Imposta il valore della proprietà idschedasimog.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDSCHEDASIMOG(String value) {
        this.idschedasimog = value;
    }

    /**
     * Recupera il valore della proprietà idschedalocale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDSCHEDALOCALE() {
        return idschedalocale;
    }

    /**
     * Imposta il valore della proprietà idschedalocale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDSCHEDALOCALE(String value) {
        this.idschedalocale = value;
    }

    /**
     * Recupera il valore della proprietà operazione.
     * 
     * @return
     *     possible object is
     *     {@link TipiOperazioneType }
     *     
     */
    public TipiOperazioneType getOPERAZIONE() {
        return operazione;
    }

    /**
     * Imposta il valore della proprietà operazione.
     * 
     * @param value
     *     allowed object is
     *     {@link TipiOperazioneType }
     *     
     */
    public void setOPERAZIONE(TipiOperazioneType value) {
        this.operazione = value;
    }

}
