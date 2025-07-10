
package it.avlp.simog.massload.xmlbeans;

/**
 * <p>Classe Java per DatiComuniType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DatiComuniType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CIG use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}FLAG_ENTE_SPECIALE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}TIPO_CONTRATTO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_CATEG_SA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CF_AMM_AGENTE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DEN_AMM_AGENTE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CF_AMM use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DEN_AMM use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CF_SA use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DEN_SA use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CODICE_CC use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DENOM_CC use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_TIPOLOGIA_SA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}FLAG_SA_AGENTE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CF_RUP use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ESITO_PROCEDURA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_SCHEDA_LOCALE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_SCHEDA_SIMOG"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}TIPOLOGIA_PROCEDURA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}FLAG_ACC_QUADRO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DURATA_ACCQUADRO_CONVENZIONE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}FLAG_CENTRALE_STIPULA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}MODO_REALIZZAZIONE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}FLAG_ESCLUSO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_ESCLUSIONE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_STATO_SCHEDA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ORIGINE_SCHEDA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}PROVV_PRESA_CARICO"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class DatiComuniType {

    protected String cig;
    protected FlagSOType flagentespeciale;
    protected TipoSchedaType tipocontratto;
    protected String idcategsa;
    protected String cfammagente;
    protected String denammagente;
    protected String cfamm;
    protected String denamm;
    protected String cfsa;
    protected String densa;
    protected String codicecc;
    protected String denomcc;
    protected String idtipologiasa;
    protected FlagSNType flagsaagente;
    protected String cfrup;
    protected String esitoprocedura;
    protected String idschedalocale;
    protected String idschedasimog;
    protected String tipologiaprocedura;
    protected FlagSNType flagaccquadro;
    protected Integer durataaccquadroconvenzione;
    protected FlagSNType flagcentralestipula;
    protected String modorealizzazione;
    protected FlagSNType flagescluso;
    protected String idesclusione;
    protected String idstatoscheda;
    protected String originescheda;
    protected String provvpresacarico;

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
     * Recupera il valore della proprietà flagentespeciale.
     * 
     * @return
     *     possible object is
     *     {@link FlagSOType }
     *     
     */
    public FlagSOType getFLAGENTESPECIALE() {
        return flagentespeciale;
    }

    /**
     * Imposta il valore della proprietà flagentespeciale.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSOType }
     *     
     */
    public void setFLAGENTESPECIALE(FlagSOType value) {
        this.flagentespeciale = value;
    }

    /**
     * Recupera il valore della proprietà tipocontratto.
     * 
     * @return
     *     possible object is
     *     {@link TipoSchedaType }
     *     
     */
    public TipoSchedaType getTIPOCONTRATTO() {
        return tipocontratto;
    }

    /**
     * Imposta il valore della proprietà tipocontratto.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoSchedaType }
     *     
     */
    public void setTIPOCONTRATTO(TipoSchedaType value) {
        this.tipocontratto = value;
    }

    /**
     * Recupera il valore della proprietà idcategsa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDCATEGSA() {
        return idcategsa;
    }

    /**
     * Imposta il valore della proprietà idcategsa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDCATEGSA(String value) {
        this.idcategsa = value;
    }

    /**
     * Recupera il valore della proprietà cfammagente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCFAMMAGENTE() {
        return cfammagente;
    }

    /**
     * Imposta il valore della proprietà cfammagente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCFAMMAGENTE(String value) {
        this.cfammagente = value;
    }

    /**
     * Recupera il valore della proprietà denammagente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDENAMMAGENTE() {
        return denammagente;
    }

    /**
     * Imposta il valore della proprietà denammagente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDENAMMAGENTE(String value) {
        this.denammagente = value;
    }

    /**
     * Recupera il valore della proprietà cfamm.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCFAMM() {
        return cfamm;
    }

    /**
     * Imposta il valore della proprietà cfamm.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCFAMM(String value) {
        this.cfamm = value;
    }

    /**
     * Recupera il valore della proprietà denamm.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDENAMM() {
        return denamm;
    }

    /**
     * Imposta il valore della proprietà denamm.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDENAMM(String value) {
        this.denamm = value;
    }

    /**
     * Recupera il valore della proprietà cfsa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCFSA() {
        return cfsa;
    }

    /**
     * Imposta il valore della proprietà cfsa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCFSA(String value) {
        this.cfsa = value;
    }

    /**
     * Recupera il valore della proprietà densa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDENSA() {
        return densa;
    }

    /**
     * Imposta il valore della proprietà densa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDENSA(String value) {
        this.densa = value;
    }

    /**
     * Recupera il valore della proprietà codicecc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICECC() {
        return codicecc;
    }

    /**
     * Imposta il valore della proprietà codicecc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICECC(String value) {
        this.codicecc = value;
    }

    /**
     * Recupera il valore della proprietà denomcc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDENOMCC() {
        return denomcc;
    }

    /**
     * Imposta il valore della proprietà denomcc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDENOMCC(String value) {
        this.denomcc = value;
    }

    /**
     * Recupera il valore della proprietà idtipologiasa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDTIPOLOGIASA() {
        return idtipologiasa;
    }

    /**
     * Imposta il valore della proprietà idtipologiasa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDTIPOLOGIASA(String value) {
        this.idtipologiasa = value;
    }

    /**
     * Recupera il valore della proprietà flagsaagente.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGSAAGENTE() {
        return flagsaagente;
    }

    /**
     * Imposta il valore della proprietà flagsaagente.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGSAAGENTE(FlagSNType value) {
        this.flagsaagente = value;
    }

    /**
     * Recupera il valore della proprietà cfrup.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCFRUP() {
        return cfrup;
    }

    /**
     * Imposta il valore della proprietà cfrup.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCFRUP(String value) {
        this.cfrup = value;
    }

    /**
     * Recupera il valore della proprietà esitoprocedura.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getESITOPROCEDURA() {
        return esitoprocedura;
    }

    /**
     * Imposta il valore della proprietà esitoprocedura.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setESITOPROCEDURA(String value) {
        this.esitoprocedura = value;
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
     * Recupera il valore della proprietà tipologiaprocedura.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPOLOGIAPROCEDURA() {
        return tipologiaprocedura;
    }

    /**
     * Imposta il valore della proprietà tipologiaprocedura.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPOLOGIAPROCEDURA(String value) {
        this.tipologiaprocedura = value;
    }

    /**
     * Recupera il valore della proprietà flagaccquadro.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGACCQUADRO() {
        return flagaccquadro;
    }

    /**
     * Imposta il valore della proprietà flagaccquadro.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGACCQUADRO(FlagSNType value) {
        this.flagaccquadro = value;
    }

    /**
     * Recupera il valore della proprietà durataaccquadroconvenzione.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDURATAACCQUADROCONVENZIONE() {
        return durataaccquadroconvenzione;
    }

    /**
     * Imposta il valore della proprietà durataaccquadroconvenzione.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDURATAACCQUADROCONVENZIONE(Integer value) {
        this.durataaccquadroconvenzione = value;
    }

    /**
     * Recupera il valore della proprietà flagcentralestipula.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGCENTRALESTIPULA() {
        return flagcentralestipula;
    }

    /**
     * Imposta il valore della proprietà flagcentralestipula.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGCENTRALESTIPULA(FlagSNType value) {
        this.flagcentralestipula = value;
    }

    /**
     * Recupera il valore della proprietà modorealizzazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMODOREALIZZAZIONE() {
        return modorealizzazione;
    }

    /**
     * Imposta il valore della proprietà modorealizzazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMODOREALIZZAZIONE(String value) {
        this.modorealizzazione = value;
    }

    /**
     * Recupera il valore della proprietà flagescluso.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGESCLUSO() {
        return flagescluso;
    }

    /**
     * Imposta il valore della proprietà flagescluso.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGESCLUSO(FlagSNType value) {
        this.flagescluso = value;
    }

    /**
     * Recupera il valore della proprietà idesclusione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDESCLUSIONE() {
        return idesclusione;
    }

    /**
     * Imposta il valore della proprietà idesclusione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDESCLUSIONE(String value) {
        this.idesclusione = value;
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
     * Recupera il valore della proprietà provvpresacarico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVVPRESACARICO() {
        return provvpresacarico;
    }

    /**
     * Imposta il valore della proprietà provvpresacarico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVVPRESACARICO(String value) {
        this.provvpresacarico = value;
    }

}
