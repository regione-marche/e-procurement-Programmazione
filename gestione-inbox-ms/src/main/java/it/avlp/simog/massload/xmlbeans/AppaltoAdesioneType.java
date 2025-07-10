
package it.avlp.simog.massload.xmlbeans;

import java.math.BigDecimal;

/**
 * <p>Classe Java per AppaltoAdesioneType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AppaltoAdesioneType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}LUOGO_ISTAT"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}LUOGO_NUTS"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}COD_STRUMENTO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMPORTO_LAVORI use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMPORTO_SERVIZI use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMPORTO_FORNITURE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}PERC_RIBASSO_AGG"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}PERC_OFF_AUMENTO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMPORTO_AGGIUDICAZIONE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_AGGIUDICAZIONE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}FLAG_RICH_SUBAPPALTO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_SCHEDA_LOCALE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_SCHEDA_SIMOG"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_STATO_SCHEDA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ORIGINE_SCHEDA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMPORTO_ATTUAZIONE_SICUREZZA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMPORTO_PROGETTAZIONE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMP_NON_ASSOG"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class AppaltoAdesioneType {

    protected String luogoistat;
    protected String luogonuts;
    protected String codstrumento;
    protected String importolavori;
    protected String importoservizi;
    protected String importoforniture;
    protected String percribassoagg;
    protected String percoffaumento;
    protected String importoaggiudicazione;
    protected String dataaggiudicazione;
    protected String flagrichsubappalto;
    protected String idschedalocale;
    protected String idschedasimog;
    protected String idstatoscheda;
    protected String originescheda;
    protected BigDecimal importoattuazionesicurezza;
    protected String importoprogettazione;
    protected String impnonassog;

    /**
     * Recupera il valore della proprietà luogoistat.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLUOGOISTAT() {
        return luogoistat;
    }

    /**
     * Imposta il valore della proprietà luogoistat.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLUOGOISTAT(String value) {
        this.luogoistat = value;
    }

    /**
     * Recupera il valore della proprietà luogonuts.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLUOGONUTS() {
        return luogonuts;
    }

    /**
     * Imposta il valore della proprietà luogonuts.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLUOGONUTS(String value) {
        this.luogonuts = value;
    }

    /**
     * Recupera il valore della proprietà codstrumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODSTRUMENTO() {
        return codstrumento;
    }

    /**
     * Imposta il valore della proprietà codstrumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODSTRUMENTO(String value) {
        this.codstrumento = value;
    }

    /**
     * Recupera il valore della proprietà importolavori.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMPORTOLAVORI() {
        return importolavori;
    }

    /**
     * Imposta il valore della proprietà importolavori.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMPORTOLAVORI(String value) {
        this.importolavori = value;
    }

    /**
     * Recupera il valore della proprietà importoservizi.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMPORTOSERVIZI() {
        return importoservizi;
    }

    /**
     * Imposta il valore della proprietà importoservizi.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMPORTOSERVIZI(String value) {
        this.importoservizi = value;
    }

    /**
     * Recupera il valore della proprietà importoforniture.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMPORTOFORNITURE() {
        return importoforniture;
    }

    /**
     * Imposta il valore della proprietà importoforniture.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMPORTOFORNITURE(String value) {
        this.importoforniture = value;
    }

    /**
     * Recupera il valore della proprietà percribassoagg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERCRIBASSOAGG() {
        return percribassoagg;
    }

    /**
     * Imposta il valore della proprietà percribassoagg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERCRIBASSOAGG(String value) {
        this.percribassoagg = value;
    }

    /**
     * Recupera il valore della proprietà percoffaumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERCOFFAUMENTO() {
        return percoffaumento;
    }

    /**
     * Imposta il valore della proprietà percoffaumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERCOFFAUMENTO(String value) {
        this.percoffaumento = value;
    }

    /**
     * Recupera il valore della proprietà importoaggiudicazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMPORTOAGGIUDICAZIONE() {
        return importoaggiudicazione;
    }

    /**
     * Imposta il valore della proprietà importoaggiudicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMPORTOAGGIUDICAZIONE(String value) {
        this.importoaggiudicazione = value;
    }

    /**
     * Recupera il valore della proprietà dataaggiudicazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDATAAGGIUDICAZIONE() {
        return dataaggiudicazione;
    }

    /**
     * Imposta il valore della proprietà dataaggiudicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATAAGGIUDICAZIONE(String value) {
        this.dataaggiudicazione = value;
    }

    /**
     * Recupera il valore della proprietà flagrichsubappalto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLAGRICHSUBAPPALTO() {
        return flagrichsubappalto;
    }

    /**
     * Imposta il valore della proprietà flagrichsubappalto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLAGRICHSUBAPPALTO(String value) {
        this.flagrichsubappalto = value;
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
     * Recupera il valore della proprietà idstatoscheda.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDSTATOSCHEDA() {
        return idstatoscheda;
    }

    /**
     * Imposta il valore della proprietà idstatoscheda.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDSTATOSCHEDA(String value) {
        this.idstatoscheda = value;
    }

    /**
     * Recupera il valore della proprietà originescheda.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORIGINESCHEDA() {
        return originescheda;
    }

    /**
     * Imposta il valore della proprietà originescheda.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORIGINESCHEDA(String value) {
        this.originescheda = value;
    }

    /**
     * Recupera il valore della proprietà importoattuazionesicurezza.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPORTOATTUAZIONESICUREZZA() {
        return importoattuazionesicurezza;
    }

    /**
     * Imposta il valore della proprietà importoattuazionesicurezza.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPORTOATTUAZIONESICUREZZA(BigDecimal value) {
        this.importoattuazionesicurezza = value;
    }

    /**
     * Recupera il valore della proprietà importoprogettazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMPORTOPROGETTAZIONE() {
        return importoprogettazione;
    }

    /**
     * Imposta il valore della proprietà importoprogettazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMPORTOPROGETTAZIONE(String value) {
        this.importoprogettazione = value;
    }

    /**
     * Recupera il valore della proprietà impnonassog.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMPNONASSOG() {
        return impnonassog;
    }

    /**
     * Imposta il valore della proprietà impnonassog.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMPNONASSOG(String value) {
        this.impnonassog = value;
    }

}
