
package it.avlp.simog.massload.xmlbeans;

import java.math.BigDecimal;

import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per AppaltoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AppaltoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}PROCEDURA_ACC use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}PREINFORMAZIONE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}TERMINE_RIDOTTO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_MODO_INDIZIONE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_MODO_GARA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}NUM_IMPRESE_INVITATE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}NUM_IMPRESE_RICHIEDENTI"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}NUM_IMPRESE_OFFERENTI use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}NUM_OFFERTE_AMMESSE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_VERB_AGGIUDICAZIONE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_SCADENZA_RICHIESTA_INVITO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_SCADENZA_PRES_OFFERTA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMPORTO_AGGIUDICAZIONE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_SCELTA_CONTRAENTE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMPORTO_LAVORI use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMPORTO_SERVIZI use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMPORTO_FORNITURE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMPORTO_ATTUAZIONE_SICUREZZA use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMPORTO_DISPOSIZIONE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMPORTO_PROGETTAZIONE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMP_NON_ASSOG"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}SISTEMA_QUALIFICAZIONE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CRITERI_SELEZIONE_STABILITI_SA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_TIPO_PRESTAZIONE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CUP use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}FLAG_ACCORDO_QUADRO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}LUOGO_ISTAT"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}LUOGO_NUTS"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ASTA_ELETTRONICA use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}PERC_RIBASSO_AGG"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}PERC_OFF_AUMENTO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_INVITO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}NUM_MANIF_INTERESSE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_MANIF_INTERESSE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}FLAG_RICH_SUBAPPALTO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}NUM_OFFERTE_ESCLUSE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}OFFERTA_MASSIMO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}OFFERTA_MINIMA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}VAL_SOGLIA_ANOMALIA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}NUM_OFFERTE_FUORI_SOGLIA use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}NUM_IMP_ESCL_INSUF_GIUST use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}COD_STRUMENTO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_SCHEDA_LOCALE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_SCHEDA_SIMOG"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}OPERE_URBANIZ_SCOMPUTO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}MODALITA_RIAGGIUDICAZIONE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CODICE_CONTRATTO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}FLAG_AGGIUD_PRINCIPALE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}PROG_CUI_RIAGGIUDICATO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_STATO_SCHEDA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ORIGINE_SCHEDA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}FLAG_CUP"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class AppaltoType {

    protected FlagSNType proceduraacc;
    protected FlagSNType preinformazione;
    protected FlagSNType termineridotto;
    protected String idmodoindizione;
    protected String idmodogara;
    protected int numimpreseinvitate;
    protected Integer numimpreserichiedenti;
    protected int numimpreseofferenti;
    protected int numofferteammesse;
    protected XMLGregorianCalendar dataverbaggiudicazione;
    protected XMLGregorianCalendar datascadenzarichiestainvito;
    protected XMLGregorianCalendar datascadenzapresofferta;
    protected String importoaggiudicazione;
    protected String idsceltacontraente;
    protected String importolavori;
    protected String importoservizi;
    protected String importoforniture;
    protected BigDecimal importoattuazionesicurezza;
    protected String importodisposizione;
    protected String importoprogettazione;
    protected String impnonassog;
    protected FlagSNType sistemaqualificazione;
    protected FlagSNType criteriselezionestabilitisa;
    protected String idtipoprestazione;
    protected String cup;
    protected FlagSNType flagaccordoquadro;
    protected String luogoistat;
    protected String luogonuts;
    protected String astaelettronica;
    protected String percribassoagg;
    protected String percoffaumento;
    protected XMLGregorianCalendar datainvito;
    protected int nummanifinteresse;
    protected XMLGregorianCalendar datamanifinteresse;
    protected String flagrichsubappalto;
    protected int numofferteescluse;
    protected BigDecimal offertamassimo;
    protected BigDecimal offertaminima;
    protected BigDecimal valsogliaanomalia;
    protected int numoffertefuorisoglia;
    protected int numimpesclinsufgiust;
    protected String codstrumento;
    protected String idschedalocale;
    protected String idschedasimog;
    protected FlagSNType opereurbanizscomputo;
    protected String modalitariaggiudicazione;
    protected String codicecontratto;
    protected FlagSNType flagaggiudprincipale;
    protected Integer progcuiriaggiudicato;
    protected String idstatoscheda;
    protected String originescheda;
    protected FlagSNType flagcup;
    protected FlagSNType relazioneunica;

    /**
     * Recupera il valore della proprietà proceduraacc.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getPROCEDURAACC() {
        return proceduraacc;
    }

    /**
     * Imposta il valore della proprietà proceduraacc.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setPROCEDURAACC(FlagSNType value) {
        this.proceduraacc = value;
    }

    /**
     * Recupera il valore della proprietà preinformazione.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getPREINFORMAZIONE() {
        return preinformazione;
    }

    /**
     * Imposta il valore della proprietà preinformazione.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setPREINFORMAZIONE(FlagSNType value) {
        this.preinformazione = value;
    }

    /**
     * Recupera il valore della proprietà termineridotto.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getTERMINERIDOTTO() {
        return termineridotto;
    }

    /**
     * Imposta il valore della proprietà termineridotto.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setTERMINERIDOTTO(FlagSNType value) {
        this.termineridotto = value;
    }

    /**
     * Recupera il valore della proprietà idmodoindizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDMODOINDIZIONE() {
        return idmodoindizione;
    }

    /**
     * Imposta il valore della proprietà idmodoindizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDMODOINDIZIONE(String value) {
        this.idmodoindizione = value;
    }

    /**
     * Recupera il valore della proprietà idmodogara.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDMODOGARA() {
        return idmodogara;
    }

    /**
     * Imposta il valore della proprietà idmodogara.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDMODOGARA(String value) {
        this.idmodogara = value;
    }

    /**
     * Recupera il valore della proprietà numimpreseinvitate.
     * 
     */
    public int getNUMIMPRESEINVITATE() {
        return numimpreseinvitate;
    }

    /**
     * Imposta il valore della proprietà numimpreseinvitate.
     * 
     */
    public void setNUMIMPRESEINVITATE(int value) {
        this.numimpreseinvitate = value;
    }

    /**
     * Recupera il valore della proprietà numimpreserichiedenti.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNUMIMPRESERICHIEDENTI() {
        return numimpreserichiedenti;
    }

    /**
     * Imposta il valore della proprietà numimpreserichiedenti.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNUMIMPRESERICHIEDENTI(Integer value) {
        this.numimpreserichiedenti = value;
    }

    /**
     * Recupera il valore della proprietà numimpreseofferenti.
     * 
     */
    public int getNUMIMPRESEOFFERENTI() {
        return numimpreseofferenti;
    }

    /**
     * Imposta il valore della proprietà numimpreseofferenti.
     * 
     */
    public void setNUMIMPRESEOFFERENTI(int value) {
        this.numimpreseofferenti = value;
    }

    /**
     * Recupera il valore della proprietà numofferteammesse.
     * 
     */
    public int getNUMOFFERTEAMMESSE() {
        return numofferteammesse;
    }

    /**
     * Imposta il valore della proprietà numofferteammesse.
     * 
     */
    public void setNUMOFFERTEAMMESSE(int value) {
        this.numofferteammesse = value;
    }

    /**
     * Recupera il valore della proprietà dataverbaggiudicazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAVERBAGGIUDICAZIONE() {
        return dataverbaggiudicazione;
    }

    /**
     * Imposta il valore della proprietà dataverbaggiudicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAVERBAGGIUDICAZIONE(XMLGregorianCalendar value) {
        this.dataverbaggiudicazione = value;
    }

    /**
     * Recupera il valore della proprietà datascadenzarichiestainvito.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATASCADENZARICHIESTAINVITO() {
        return datascadenzarichiestainvito;
    }

    /**
     * Imposta il valore della proprietà datascadenzarichiestainvito.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATASCADENZARICHIESTAINVITO(XMLGregorianCalendar value) {
        this.datascadenzarichiestainvito = value;
    }

    /**
     * Recupera il valore della proprietà datascadenzapresofferta.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATASCADENZAPRESOFFERTA() {
        return datascadenzapresofferta;
    }

    /**
     * Imposta il valore della proprietà datascadenzapresofferta.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATASCADENZAPRESOFFERTA(XMLGregorianCalendar value) {
        this.datascadenzapresofferta = value;
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
     * Recupera il valore della proprietà idsceltacontraente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDSCELTACONTRAENTE() {
        return idsceltacontraente;
    }

    /**
     * Imposta il valore della proprietà idsceltacontraente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDSCELTACONTRAENTE(String value) {
        this.idsceltacontraente = value;
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
     * Recupera il valore della proprietà importodisposizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMPORTODISPOSIZIONE() {
        return importodisposizione;
    }

    /**
     * Imposta il valore della proprietà importodisposizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMPORTODISPOSIZIONE(String value) {
        this.importodisposizione = value;
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

    /**
     * Recupera il valore della proprietà sistemaqualificazione.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getSISTEMAQUALIFICAZIONE() {
        return sistemaqualificazione;
    }

    /**
     * Imposta il valore della proprietà sistemaqualificazione.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setSISTEMAQUALIFICAZIONE(FlagSNType value) {
        this.sistemaqualificazione = value;
    }

    /**
     * Recupera il valore della proprietà criteriselezionestabilitisa.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getCRITERISELEZIONESTABILITISA() {
        return criteriselezionestabilitisa;
    }

    /**
     * Imposta il valore della proprietà criteriselezionestabilitisa.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setCRITERISELEZIONESTABILITISA(FlagSNType value) {
        this.criteriselezionestabilitisa = value;
    }

    /**
     * Recupera il valore della proprietà idtipoprestazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDTIPOPRESTAZIONE() {
        return idtipoprestazione;
    }

    /**
     * Imposta il valore della proprietà idtipoprestazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDTIPOPRESTAZIONE(String value) {
        this.idtipoprestazione = value;
    }

    /**
     * Recupera il valore della proprietà cup.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCUP() {
        return cup;
    }

    /**
     * Imposta il valore della proprietà cup.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCUP(String value) {
        this.cup = value;
    }

    /**
     * Recupera il valore della proprietà flagaccordoquadro.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGACCORDOQUADRO() {
        return flagaccordoquadro;
    }

    /**
     * Imposta il valore della proprietà flagaccordoquadro.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGACCORDOQUADRO(FlagSNType value) {
        this.flagaccordoquadro = value;
    }

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
     * Recupera il valore della proprietà astaelettronica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASTAELETTRONICA() {
        return astaelettronica;
    }

    /**
     * Imposta il valore della proprietà astaelettronica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASTAELETTRONICA(String value) {
        this.astaelettronica = value;
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
     * Recupera il valore della proprietà datainvito.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAINVITO() {
        return datainvito;
    }

    /**
     * Imposta il valore della proprietà datainvito.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAINVITO(XMLGregorianCalendar value) {
        this.datainvito = value;
    }

    /**
     * Recupera il valore della proprietà nummanifinteresse.
     * 
     */
    public int getNUMMANIFINTERESSE() {
        return nummanifinteresse;
    }

    /**
     * Imposta il valore della proprietà nummanifinteresse.
     * 
     */
    public void setNUMMANIFINTERESSE(int value) {
        this.nummanifinteresse = value;
    }

    /**
     * Recupera il valore della proprietà datamanifinteresse.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAMANIFINTERESSE() {
        return datamanifinteresse;
    }

    /**
     * Imposta il valore della proprietà datamanifinteresse.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAMANIFINTERESSE(XMLGregorianCalendar value) {
        this.datamanifinteresse = value;
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
     * Recupera il valore della proprietà numofferteescluse.
     * 
     */
    public int getNUMOFFERTEESCLUSE() {
        return numofferteescluse;
    }

    /**
     * Imposta il valore della proprietà numofferteescluse.
     * 
     */
    public void setNUMOFFERTEESCLUSE(int value) {
        this.numofferteescluse = value;
    }

    /**
     * Recupera il valore della proprietà offertamassimo.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOFFERTAMASSIMO() {
        return offertamassimo;
    }

    /**
     * Imposta il valore della proprietà offertamassimo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOFFERTAMASSIMO(BigDecimal value) {
        this.offertamassimo = value;
    }

    /**
     * Recupera il valore della proprietà offertaminima.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOFFERTAMINIMA() {
        return offertaminima;
    }

    /**
     * Imposta il valore della proprietà offertaminima.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOFFERTAMINIMA(BigDecimal value) {
        this.offertaminima = value;
    }

    /**
     * Recupera il valore della proprietà valsogliaanomalia.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVALSOGLIAANOMALIA() {
        return valsogliaanomalia;
    }

    /**
     * Imposta il valore della proprietà valsogliaanomalia.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVALSOGLIAANOMALIA(BigDecimal value) {
        this.valsogliaanomalia = value;
    }

    /**
     * Recupera il valore della proprietà numoffertefuorisoglia.
     * 
     */
    public int getNUMOFFERTEFUORISOGLIA() {
        return numoffertefuorisoglia;
    }

    /**
     * Imposta il valore della proprietà numoffertefuorisoglia.
     * 
     */
    public void setNUMOFFERTEFUORISOGLIA(int value) {
        this.numoffertefuorisoglia = value;
    }

    /**
     * Recupera il valore della proprietà numimpesclinsufgiust.
     * 
     */
    public int getNUMIMPESCLINSUFGIUST() {
        return numimpesclinsufgiust;
    }

    /**
     * Imposta il valore della proprietà numimpesclinsufgiust.
     * 
     */
    public void setNUMIMPESCLINSUFGIUST(int value) {
        this.numimpesclinsufgiust = value;
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
     * Recupera il valore della proprietà opereurbanizscomputo.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getOPEREURBANIZSCOMPUTO() {
        return opereurbanizscomputo;
    }

    /**
     * Imposta il valore della proprietà opereurbanizscomputo.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setOPEREURBANIZSCOMPUTO(FlagSNType value) {
        this.opereurbanizscomputo = value;
    }

    /**
     * Recupera il valore della proprietà modalitariaggiudicazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMODALITARIAGGIUDICAZIONE() {
        return modalitariaggiudicazione;
    }

    /**
     * Imposta il valore della proprietà modalitariaggiudicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMODALITARIAGGIUDICAZIONE(String value) {
        this.modalitariaggiudicazione = value;
    }

    /**
     * Recupera il valore della proprietà codicecontratto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICECONTRATTO() {
        return codicecontratto;
    }

    /**
     * Imposta il valore della proprietà codicecontratto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICECONTRATTO(String value) {
        this.codicecontratto = value;
    }

    /**
     * Recupera il valore della proprietà flagaggiudprincipale.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGAGGIUDPRINCIPALE() {
        return flagaggiudprincipale;
    }

    /**
     * Imposta il valore della proprietà flagaggiudprincipale.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGAGGIUDPRINCIPALE(FlagSNType value) {
        this.flagaggiudprincipale = value;
    }

    /**
     * Recupera il valore della proprietà progcuiriaggiudicato.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPROGCUIRIAGGIUDICATO() {
        return progcuiriaggiudicato;
    }

    /**
     * Imposta il valore della proprietà progcuiriaggiudicato.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPROGCUIRIAGGIUDICATO(Integer value) {
        this.progcuiriaggiudicato = value;
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
     * Recupera il valore della proprietà flagcup.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGCUP() {
        return flagcup;
    }

    /**
     * Imposta il valore della proprietà flagcup.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGCUP(FlagSNType value) {
        this.flagcup = value;
    }
    
    /**
     * Recupera il valore della proprietà relazioneunica.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getRELAZIONEUNICA() {
        return relazioneunica;
    }

    /**
     * Imposta il valore della proprietà relazioneunica.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setRELAZIONEUNICA(FlagSNType value) {
        this.relazioneunica = value;
    }

}
