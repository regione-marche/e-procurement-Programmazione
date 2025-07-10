
package it.avlp.simog.massload.xmlbeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;


public class LottoType {

    protected CategLottoType categorie;
    protected List<TipiAppaltoType> tipiAppaltoLav;
    protected List<TipiAppaltoType> tipiAppaltoForn;
    protected CUPLOTTOType cuplotto;
    protected List<CondizioneLtType> condizioni;
    protected List<CPVSecondariaType> cpvSecondaria;
    protected String cig;
    protected String oggetto;
    protected FlagSNType sommaurgenza;
    protected BigDecimal importolotto;
    protected BigDecimal importosa;
    protected BigDecimal importoimpresa;
    protected String cpv;
    protected String idsceltacontraente;
    protected String idcategoriaprevalente;
    protected XMLGregorianCalendar datapubblicazione;
    protected XMLGregorianCalendar datascadenzapagamenti;
    protected XMLGregorianCalendar datacomunicazione;
    protected XMLGregorianCalendar datainibpagamento;
    protected String idmotivazione;
    protected String notecanc;
    protected String tipocontratto;
    protected FlagSNType flagderogaadesione;    
    protected FlagSNType flagusometodiedilizia;    
    protected String derogaqualificazionesa;
    protected String flagescluso;
    protected String idesclusione;
    protected String luogoistat;
    protected String luogonuts;
    protected String importoattuazionesicurezza;
    protected Integer triennioannoinizio;
    protected Integer triennioannofine;
    protected Integer triennioprogressivo;
    protected String annualecuimininf;
    protected XMLGregorianCalendar datacreazionelotto;
    protected XMLGregorianCalendar datacancellazionelotto;
    protected FlagSNType flagprevederip;
    protected FlagSNType flagripetizione;
    protected String cigoriginerip;
    protected String orascadenza;
    protected String statoavcpass;
    protected String datascadenzarichiestainvito;
    protected XMLGregorianCalendar dataletterainvito;
    protected FlagSNType flagcup;
    protected FlagSNType flagdl50;
    protected String primaannualita;
    protected String idaffriservati;
    protected FlagSNType flagregime;
    protected String artregime;
    protected String idmotivocollcig;
    protected String categoriamerc;
    protected FlagSNType flagnoadesioneiniziativa;
    protected FlagSNType flagsanonclassificata;
    protected BigDecimal importoopzioni;
	protected Integer duratarinnovi;
	protected Integer durataaffidamento;
	protected FlagSNQType flagprevisionequota;
    protected FlagSNType flagpnrrpnc;
    protected BigDecimal quotafemminile;
    protected BigDecimal quotagiovanile;
    protected FlagSNType flagmisurepremiali;
    protected List<String> misuraPremiale;
    protected List<String> motivoDeroga;
    /**
     * Recupera il valore della proprietà categorie.
     * 
     * @return
     *     possible object is
     *     {@link CategLottoType }
     *     
     */
    public CategLottoType getCATEGORIE() {
        return categorie;
    }

    /**
     * Imposta il valore della proprietà categorie.
     * 
     * @param value
     *     allowed object is
     *     {@link CategLottoType }
     *     
     */
    public void setCATEGORIE(CategLottoType value) {
        this.categorie = value;
    }

    /**
     * Gets the value of the tipiAppaltoLav property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tipiAppaltoLav property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTipiAppaltoLav().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipiAppaltoType }
     * 
     * 
     */
    public List<TipiAppaltoType> getTipiAppaltoLav() {
        if (tipiAppaltoLav == null) {
            tipiAppaltoLav = new ArrayList<TipiAppaltoType>();
        }
        return this.tipiAppaltoLav;
    }

    /**
     * Gets the value of the tipiAppaltoForn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tipiAppaltoForn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTipiAppaltoForn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipiAppaltoType }
     * 
     * 
     */
    public List<TipiAppaltoType> getTipiAppaltoForn() {
        if (tipiAppaltoForn == null) {
            tipiAppaltoForn = new ArrayList<TipiAppaltoType>();
        }
        return this.tipiAppaltoForn;
    }

    /**
     * Recupera il valore della proprietà cuplotto.
     * 
     * @return
     *     possible object is
     *     {@link CUPLOTTOType }
     *     
     */
    public CUPLOTTOType getCUPLOTTO() {
        return cuplotto;
    }

    /**
     * Imposta il valore della proprietà cuplotto.
     * 
     * @param value
     *     allowed object is
     *     {@link CUPLOTTOType }
     *     
     */
    public void setCUPLOTTO(CUPLOTTOType value) {
        this.cuplotto = value;
    }

    /**
     * Gets the value of the condizioni property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the condizioni property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCondizioni().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CondizioneLtType }
     * 
     * 
     */
    public List<CondizioneLtType> getCondizioni() {
        if (condizioni == null) {
            condizioni = new ArrayList<CondizioneLtType>();
        }
        return this.condizioni;
    }

    /**
     * Gets the value of the cpvSecondaria property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cpvSecondaria property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCPVSecondaria().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CPVSecondariaType }
     * 
     * 
     */
    public List<CPVSecondariaType> getCPVSecondaria() {
        if (cpvSecondaria == null) {
            cpvSecondaria = new ArrayList<CPVSecondariaType>();
        }
        return this.cpvSecondaria;
    }

    /**
     * Gets the value of the misuraPremiale property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the misuraPremiale property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMisuraPremiale().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMisuraPremiale() {
        if (misuraPremiale == null) {
            misuraPremiale = new ArrayList<String>();
        }
        return this.misuraPremiale;
    }

    /**
     * Gets the value of the motivoDeroga property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the motivoDeroga property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMotivoDeroga().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMotivoDeroga() {
        if (motivoDeroga == null) {
            motivoDeroga = new ArrayList<String>();
        }
        return this.motivoDeroga;
    }

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
     * Recupera il valore della proprietà oggetto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOGGETTO() {
        return oggetto;
    }

    /**
     * Imposta il valore della proprietà oggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOGGETTO(String value) {
        this.oggetto = value;
    }

    /**
     * Recupera il valore della proprietà sommaurgenza.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getSOMMAURGENZA() {
        return sommaurgenza;
    }

    /**
     * Imposta il valore della proprietà sommaurgenza.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setSOMMAURGENZA(FlagSNType value) {
        this.sommaurgenza = value;
    }

    /**
     * Recupera il valore della proprietà importolotto.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPORTOLOTTO() {
        return importolotto;
    }

    /**
     * Imposta il valore della proprietà importolotto.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPORTOLOTTO(BigDecimal value) {
        this.importolotto = value;
    }

    /**
     * Recupera il valore della proprietà importosa.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPORTOSA() {
        return importosa;
    }

    /**
     * Imposta il valore della proprietà importosa.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPORTOSA(BigDecimal value) {
        this.importosa = value;
    }

    /**
     * Recupera il valore della proprietà importoimpresa.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPORTOIMPRESA() {
        return importoimpresa;
    }

    /**
     * Imposta il valore della proprietà importoimpresa.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPORTOIMPRESA(BigDecimal value) {
        this.importoimpresa = value;
    }

    /**
     * Recupera il valore della proprietà cpv.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCPV() {
        return cpv;
    }

    /**
     * Imposta il valore della proprietà cpv.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCPV(String value) {
        this.cpv = value;
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
     * Recupera il valore della proprietà idcategoriaprevalente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDCATEGORIAPREVALENTE() {
        return idcategoriaprevalente;
    }

    /**
     * Imposta il valore della proprietà idcategoriaprevalente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDCATEGORIAPREVALENTE(String value) {
        this.idcategoriaprevalente = value;
    }

    /**
     * Recupera il valore della proprietà datapubblicazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAPUBBLICAZIONE() {
        return datapubblicazione;
    }

    /**
     * Imposta il valore della proprietà datapubblicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAPUBBLICAZIONE(XMLGregorianCalendar value) {
        this.datapubblicazione = value;
    }

    /**
     * Recupera il valore della proprietà datascadenzapagamenti.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATASCADENZAPAGAMENTI() {
        return datascadenzapagamenti;
    }

    /**
     * Imposta il valore della proprietà datascadenzapagamenti.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATASCADENZAPAGAMENTI(XMLGregorianCalendar value) {
        this.datascadenzapagamenti = value;
    }

    /**
     * Recupera il valore della proprietà datacomunicazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATACOMUNICAZIONE() {
        return datacomunicazione;
    }

    /**
     * Imposta il valore della proprietà datacomunicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATACOMUNICAZIONE(XMLGregorianCalendar value) {
        this.datacomunicazione = value;
    }

    /**
     * Recupera il valore della proprietà datainibpagamento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAINIBPAGAMENTO() {
        return datainibpagamento;
    }

    /**
     * Imposta il valore della proprietà datainibpagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAINIBPAGAMENTO(XMLGregorianCalendar value) {
        this.datainibpagamento = value;
    }

    /**
     * Recupera il valore della proprietà idmotivazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDMOTIVAZIONE() {
        return idmotivazione;
    }

    /**
     * Imposta il valore della proprietà idmotivazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDMOTIVAZIONE(String value) {
        this.idmotivazione = value;
    }

    /**
     * Recupera il valore della proprietà notecanc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOTECANC() {
        return notecanc;
    }

    /**
     * Imposta il valore della proprietà notecanc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOTECANC(String value) {
        this.notecanc = value;
    }

    /**
     * Recupera il valore della proprietà tipocontratto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPOCONTRATTO() {
        return tipocontratto;
    }
    
    /**
     * Recupera il valore della proprietà flagderogaadesione.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGDEROGAADESIONE() {
        return flagderogaadesione;
    }

    /**
     * Imposta il valore della proprietà flagderogaadesione.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGDEROGAADESIONE(FlagSNType value) {
        this.flagderogaadesione = value;
    }

    /**
     * Recupera il valore della proprietà flagusometodiedilizia.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGUSOMETODIEDILIZIA() {
        return flagusometodiedilizia;
    }

    /**
     * Imposta il valore della proprietà flagusometodiedilizia.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGUSOMETODIEDILIZIA(FlagSNType value) {
        this.flagusometodiedilizia = value;
    }

    /**
     * Recupera il valore della proprietà derogaqualificazionesa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDEROGAQUALIFICAZIONESA() {
        return derogaqualificazionesa;
    }

    /**
     * Imposta il valore della proprietà derogaqualificazionesa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDEROGAQUALIFICAZIONESA(String value) {
        this.derogaqualificazionesa = value;
    }

    /**
     * Imposta il valore della proprietà tipocontratto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPOCONTRATTO(String value) {
        this.tipocontratto = value;
    }

    /**
     * Recupera il valore della proprietà flagescluso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLAGESCLUSO() {
        return flagescluso;
    }

    /**
     * Imposta il valore della proprietà flagescluso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLAGESCLUSO(String value) {
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
     * Recupera il valore della proprietà importoattuazionesicurezza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMPORTOATTUAZIONESICUREZZA() {
        return importoattuazionesicurezza;
    }

    /**
     * Imposta il valore della proprietà importoattuazionesicurezza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMPORTOATTUAZIONESICUREZZA(String value) {
        this.importoattuazionesicurezza = value;
    }

    /**
     * Recupera il valore della proprietà triennioannoinizio.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTRIENNIOANNOINIZIO() {
        return triennioannoinizio;
    }

    /**
     * Imposta il valore della proprietà triennioannoinizio.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTRIENNIOANNOINIZIO(Integer value) {
        this.triennioannoinizio = value;
    }

    /**
     * Recupera il valore della proprietà triennioannofine.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTRIENNIOANNOFINE() {
        return triennioannofine;
    }

    /**
     * Imposta il valore della proprietà triennioannofine.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTRIENNIOANNOFINE(Integer value) {
        this.triennioannofine = value;
    }

    /**
     * Recupera il valore della proprietà triennioprogressivo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTRIENNIOPROGRESSIVO() {
        return triennioprogressivo;
    }

    /**
     * Imposta il valore della proprietà triennioprogressivo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTRIENNIOPROGRESSIVO(Integer value) {
        this.triennioprogressivo = value;
    }

    /**
     * Recupera il valore della proprietà annualecuimininf.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getANNUALECUIMININF() {
        return annualecuimininf;
    }

    /**
     * Imposta il valore della proprietà annualecuimininf.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setANNUALECUIMININF(String value) {
        this.annualecuimininf = value;
    }

    /**
     * Recupera il valore della proprietà datacreazionelotto.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATACREAZIONELOTTO() {
        return datacreazionelotto;
    }

    /**
     * Imposta il valore della proprietà datacreazionelotto.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATACREAZIONELOTTO(XMLGregorianCalendar value) {
        this.datacreazionelotto = value;
    }

    /**
     * Recupera il valore della proprietà datacancellazionelotto.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATACANCELLAZIONELOTTO() {
        return datacancellazionelotto;
    }

    /**
     * Imposta il valore della proprietà datacancellazionelotto.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATACANCELLAZIONELOTTO(XMLGregorianCalendar value) {
        this.datacancellazionelotto = value;
    }

    /**
     * Recupera il valore della proprietà flagprevederip.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGPREVEDERIP() {
        return flagprevederip;
    }

    /**
     * Imposta il valore della proprietà flagprevederip.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGPREVEDERIP(FlagSNType value) {
        this.flagprevederip = value;
    }

    /**
     * Recupera il valore della proprietà flagripetizione.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGRIPETIZIONE() {
        return flagripetizione;
    }

    /**
     * Imposta il valore della proprietà flagripetizione.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGRIPETIZIONE(FlagSNType value) {
        this.flagripetizione = value;
    }

    /**
     * Recupera il valore della proprietà cigoriginerip.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCIGORIGINERIP() {
        return cigoriginerip;
    }

    /**
     * Imposta il valore della proprietà cigoriginerip.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCIGORIGINERIP(String value) {
        this.cigoriginerip = value;
    }

    /**
     * Recupera il valore della proprietà orascadenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORASCADENZA() {
        return orascadenza;
    }

    /**
     * Imposta il valore della proprietà orascadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORASCADENZA(String value) {
        this.orascadenza = value;
    }

    /**
     * Recupera il valore della proprietà statoavcpass.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATOAVCPASS() {
        return statoavcpass;
    }

    /**
     * Imposta il valore della proprietà statoavcpass.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATOAVCPASS(String value) {
        this.statoavcpass = value;
    }

    /**
     * Recupera il valore della proprietà datascadenzarichiestainvito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDATASCADENZARICHIESTAINVITO() {
        return datascadenzarichiestainvito;
    }

    /**
     * Imposta il valore della proprietà datascadenzarichiestainvito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATASCADENZARICHIESTAINVITO(String value) {
        this.datascadenzarichiestainvito = value;
    }

    /**
     * Recupera il valore della proprietà dataletterainvito.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATALETTERAINVITO() {
        return dataletterainvito;
    }

    /**
     * Imposta il valore della proprietà dataletterainvito.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATALETTERAINVITO(XMLGregorianCalendar value) {
        this.dataletterainvito = value;
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
     * Recupera il valore della proprietà flagpnrrpnc.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGPNRRPNC() {
        return flagpnrrpnc;
    }

    /**
     * Imposta il valore della proprietà flagpnrrpnc.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGPNRRPNC(FlagSNType value) {
        this.flagpnrrpnc = value;
    }

    /**
     * Recupera il valore della proprietà flagprevisionequota.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNQType }
     *     
     */
    public FlagSNQType getFLAGPREVISIONEQUOTA() {
        return flagprevisionequota;
    }

    /**
     * Imposta il valore della proprietà flagprevisionequota.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNQType }
     *     
     */
    public void setFLAGPREVISIONEQUOTA(FlagSNQType value) {
        this.flagprevisionequota = value;
    }

    /**
     * Recupera il valore della proprietà quotafemminile.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQUOTAFEMMINILE() {
        return quotafemminile;
    }

    /**
     * Imposta il valore della proprietà quotafemminile.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQUOTAFEMMINILE(BigDecimal value) {
        this.quotafemminile = value;
    }

    /**
     * Recupera il valore della proprietà quotagiovanile.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQUOTAGIOVANILE() {
        return quotagiovanile;
    }

    /**
     * Imposta il valore della proprietà quotagiovanile.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQUOTAGIOVANILE(BigDecimal value) {
        this.quotagiovanile = value;
    }

    /**
     * Recupera il valore della proprietà flagmisurepremiali.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGMISUREPREMIALI() {
        return flagmisurepremiali;
    }

    /**
     * Imposta il valore della proprietà flagmisurepremiali.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGMISUREPREMIALI(FlagSNType value) {
        this.flagmisurepremiali = value;
    }

    /**
     * Recupera il valore della proprietà flagdl50.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGDL50() {
        return flagdl50;
    }

    /**
     * Imposta il valore della proprietà flagdl50.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGDL50(FlagSNType value) {
        this.flagdl50 = value;
    }

    /**
     * Recupera il valore della proprietà primaannualita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRIMAANNUALITA() {
        return primaannualita;
    }

    /**
     * Imposta il valore della proprietà primaannualita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRIMAANNUALITA(String value) {
        this.primaannualita = value;
    }

    /**
     * Recupera il valore della proprietà idaffriservati.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDAFFRISERVATI() {
        return idaffriservati;
    }

    /**
     * Imposta il valore della proprietà idaffriservati.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDAFFRISERVATI(String value) {
        this.idaffriservati = value;
    }

    /**
     * Recupera il valore della proprietà flagregime.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGREGIME() {
        return flagregime;
    }

    /**
     * Imposta il valore della proprietà flagregime.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGREGIME(FlagSNType value) {
        this.flagregime = value;
    }

    /**
     * Recupera il valore della proprietà artregime.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getARTREGIME() {
        return artregime;
    }

    /**
     * Imposta il valore della proprietà artregime.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setARTREGIME(String value) {
        this.artregime = value;
    }

    /**
     * Recupera il valore della proprietà idmotivocollcig.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDMOTIVOCOLLCIG() {
        return idmotivocollcig;
    }

    /**
     * Imposta il valore della proprietà idmotivocollcig.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDMOTIVOCOLLCIG(String value) {
        this.idmotivocollcig = value;
    }

    /**
     * Recupera il valore della proprietà categoriamerc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCATEGORIAMERC() {
        return categoriamerc;
    }

    /**
     * Imposta il valore della proprietà categoriamerc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCATEGORIAMERC(String value) {
        this.categoriamerc = value;
    }

    /**
     * Recupera il valore della proprietà flagnoadesioneiniziativa.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGNOADESIONEINIZIATIVA() {
        return flagnoadesioneiniziativa;
    }

    /**
     * Imposta il valore della proprietà flagnoadesioneiniziativa.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGNOADESIONEINIZIATIVA(FlagSNType value) {
        this.flagnoadesioneiniziativa = value;
    }

    /**
     * Recupera il valore della proprietà flagsanonclassificata.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGSANONCLASSIFICATA() {
        return flagsanonclassificata;
    }

    /**
     * Imposta il valore della proprietà flagsanonclassificata.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGSANONCLASSIFICATA(FlagSNType value) {
        this.flagsanonclassificata = value;
    }

    /**
     * Recupera il valore della proprietà importoopzioni.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPORTOOPZIONI() {
        return importoopzioni;
    }

    /**
     * Imposta il valore della proprietà importoopzioni.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPORTOOPZIONI(BigDecimal value) {
        this.importoopzioni = value;
    }

    /**
     * Recupera il valore della proprietà duratarinnovi.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDURATARINNOVI() {
        return duratarinnovi;
    }

    /**
     * Imposta il valore della proprietà duratarinnovi.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDURATARINNOVI(Integer value) {
        this.duratarinnovi = value;
    }

    /**
     * Recupera il valore della proprietà durataaffidamento.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDURATAAFFIDAMENTO() {
        return durataaffidamento;
    }

    /**
     * Imposta il valore della proprietà durataaffidamento.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDURATAAFFIDAMENTO(Integer value) {
        this.durataaffidamento = value;
    }


}
