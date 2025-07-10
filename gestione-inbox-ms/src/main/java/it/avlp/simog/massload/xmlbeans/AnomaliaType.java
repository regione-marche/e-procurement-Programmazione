
package it.avlp.simog.massload.xmlbeans;

/**
 * <p>Classe Java per AnomaliaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AnomaliaType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CODICE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DESCRIZIONE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}LIVELLO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ELEMENTO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}SCHEDA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}PROGRESSIVO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CAMPO_XML"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_SCHEDA_SIMOG"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_SCHEDA_LOCALE"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class AnomaliaType {

    protected String codice;
    protected String descrizione;
    protected LivelloType livello;
    protected int elemento;
    protected TipiSchedeType scheda;
    protected String progressivo;
    protected String campoxml;
    protected String idschedasimog;
    protected String idschedalocale;

    /**
     * Recupera il valore della proprietà codice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICE() {
        return codice;
    }

    /**
     * Imposta il valore della proprietà codice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICE(String value) {
        this.codice = value;
    }

    /**
     * Recupera il valore della proprietà descrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRIZIONE() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprietà descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRIZIONE(String value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della proprietà livello.
     * 
     * @return
     *     possible object is
     *     {@link LivelloType }
     *     
     */
    public LivelloType getLIVELLO() {
        return livello;
    }

    /**
     * Imposta il valore della proprietà livello.
     * 
     * @param value
     *     allowed object is
     *     {@link LivelloType }
     *     
     */
    public void setLIVELLO(LivelloType value) {
        this.livello = value;
    }

    /**
     * Recupera il valore della proprietà elemento.
     * 
     */
    public int getELEMENTO() {
        return elemento;
    }

    /**
     * Imposta il valore della proprietà elemento.
     * 
     */
    public void setELEMENTO(int value) {
        this.elemento = value;
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
     * Recupera il valore della proprietà progressivo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROGRESSIVO() {
        return progressivo;
    }

    /**
     * Imposta il valore della proprietà progressivo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROGRESSIVO(String value) {
        this.progressivo = value;
    }

    /**
     * Recupera il valore della proprietà campoxml.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCAMPOXML() {
        return campoxml;
    }

    /**
     * Imposta il valore della proprietà campoxml.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCAMPOXML(String value) {
        this.campoxml = value;
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
