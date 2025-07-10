
package it.avlp.simog.massload.xmlbeans;

/**
 * <p>Classe Java per RecIdSchedaElimType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RecIdSchedaElimType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CIG use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CUI use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}SCHEDA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_SCHEDA_SIMOG"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_SCHEDA_LOCALE"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class RecIdSchedaElimType {

    protected String cig;
    protected String cui;
    protected TipiSchedeType scheda;
    protected String idschedasimog;
    protected String idschedalocale;

    /**
     * Recupera il valore della proprietà cig.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCIG() {
        return cig;
    }

    /**
     * Imposta il valore della proprietà cig.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCIG(String value) {
        this.cig = value;
    }

    /**
     * Recupera il valore della proprietà cui.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCUI() {
        return cui;
    }

    /**
     * Imposta il valore della proprietà cui.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCUI(String value) {
        this.cui = value;
    }

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

}
