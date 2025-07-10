package it.appaltiecontratti.programmi.entity;

import java.math.BigDecimal;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @since giu 16, 2023
 * SELECT
 * coalesce(IT.CONTRI,0) as CONTRI,
 * coalesce(IT.CUIINT,'') AS CUI,
 * coalesce(UI.CFEIN,'') AS CF_AMM,
 * coalesce(PI.ANNTRI,0)  AS PRIMA_ANN,
 * coalesce(IT.ANNRIF,0)  AS ANN_RIF,
 * coalesce(IT.CUPPRG,'') AS COD_CUP,
 * coalesce(IT.ACQALTINT,0) AS ACQ_RIC,
 * coalesce(IT.CUICOLL,'') AS COD_CUI,
 * coalesce(IT.LOTFUNZ,'') AS LOTTO_F,
 * coalesce(NU.CODICE,'') AS AMBITO_GEO,
 * coalesce(IT.TIPOIN,'') AS SETTORE,
 * coalesce(IT.CODCPV,'') AS CPV,
 * coalesce(IT.DESINT,'') AS DESCRIZIONE_ACQ,
 * coalesce(IT.PRGINT,0)  AS PRIORITA,
 * coalesce(TE.COGTEI,'') AS RESPONSABILE_COGNOME,
 * coalesce(TE.NOMETEI,'') AS RESPONSABILE_NOME,
 * coalesce(IT.DURCONT,0)  AS DURATA,
 * coalesce(IT.CONTESS,'')  AS CONTRATTO_ESSERE,
 * (coalesce(IT.DI1INT,0.00) + coalesce(IT.PR1TRI,0.00)) AS COSTI_1ANNO,
 * (coalesce(IT.DI2INT,0.00) + coalesce(IT.PR2TRI,0.00)) AS COSTI_2ANNO,
 * (coalesce(IT.DI9INT,0.00) + coalesce(IT.PR9TRI,0.00)) AS COSTI_ALANNO,
 * coalesce(IT.TOTINT,0.00) AS COSTI_TOTALE,
 * coalesce(IT.ICPINT,0.00) AS CAP_PRIV_IMP,
 * coalesce(IT.TCPINT,'') AS TIPO_IMP,
 * coalesce(IT.CODAUSA,'') AS COD_AUSA,
 * coalesce(IT.SOGAGG,'') AS DENOMINAZIONE_AUSA,
 * IT.VARIATO AS VARIATO
 * FROM PIATRI PI LEFT JOIN INTTRI IT ON PI.CONTRI = IT.CONTRI
 * LEFT JOIN TECNI TE ON IT.CODRUP = TE.CODTEC
 * LEFT JOIN TABNUTS NU ON  IT.NUTS = NU.CODICE
 * LEFT JOIN (SELECT PT.CONTRI, UF.CFEIN FROM PIATRI PT JOIN UFFINT UF ON PT.CENINT = UF.CODEIN) UI ON UI.CONTRI = IT.CONTRI
 * WHERE PI.CONTRI = $P{PIATRI_CONTRI}
 * ORDER BY IT.ANNRIF, IT.NPROGINT
 */
public class ExportAcquistiQueryResult {
    private Long contri;
    private String cui;
    private String cfAmm;
    private Long primaAnn;
    private Long annRif;
    private String codCup;
    private Long acquistoRicompreso;
    private String codCuiLavoroCollegato;
    private String lottoF;
    private String ambitoGeografico;
    private String settore;
    private String cpv;
    private String descrizione;
    private Long priorita;
    private String responsabileCognome;
    private String responsabileNome;
    private Long durataContratto;
    private String contrattoEssere;
    private BigDecimal costi1Anno;
    private BigDecimal costi2Anno;
    private BigDecimal costi3Anno;
    private BigDecimal costiAlAnno;
    private BigDecimal costiTotale;
    private BigDecimal capPrivImp;
    private String tipoImp;
    private String codAusa;
    private String denominazioneAusa;
    private Long variato;
    private String codiceAusa;

    public Long getContri() {
        return contri;
    }

    public void setContri(Long contri) {
        this.contri = contri;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getCfAmm() {
        return cfAmm;
    }

    public void setCfAmm(String cfAmm) {
        this.cfAmm = cfAmm;
    }

    public Long getPrimaAnn() {
        return primaAnn;
    }

    public void setPrimaAnn(Long primaAnn) {
        this.primaAnn = primaAnn;
    }

    public Long getAnnRif() {
        return annRif;
    }

    public void setAnnRif(Long annRif) {
        this.annRif = annRif;
    }

    public String getCodCup() {
        return codCup;
    }

    public void setCodCup(String codCup) {
        this.codCup = codCup;
    }

    public Long getAcquistoRicompreso() {
        return acquistoRicompreso;
    }

    public void setAcquistoRicompreso(Long acquistoRicompreso) {
        this.acquistoRicompreso = acquistoRicompreso;
    }

    public String getCodCuiLavoroCollegato() {
        return codCuiLavoroCollegato;
    }

    public void setCodCuiLavoroCollegato(String codCuiLavoroCollegato) {
        this.codCuiLavoroCollegato = codCuiLavoroCollegato;
    }

    public String getLottoF() {
        return lottoF;
    }

    public void setLottoF(String lottoF) {
        this.lottoF = lottoF;
    }

    public String getAmbitoGeografico() {
        return ambitoGeografico;
    }

    public void setAmbitoGeografico(String ambitoGeografico) {
        this.ambitoGeografico = ambitoGeografico;
    }

    public String getSettore() {
        return settore;
    }

    public void setSettore(String settore) {
        this.settore = settore;
    }

    public String getCpv() {
        return cpv;
    }

    public void setCpv(String cpv) {
        this.cpv = cpv;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Long getPriorita() {
        return priorita;
    }

    public void setPriorita(Long priorita) {
        this.priorita = priorita;
    }

    public String getResponsabileCognome() {
        return responsabileCognome;
    }

    public void setResponsabileCognome(String responsabileCognome) {
        this.responsabileCognome = responsabileCognome;
    }

    public String getResponsabileNome() {
        return responsabileNome;
    }

    public void setResponsabileNome(String responsabileNome) {
        this.responsabileNome = responsabileNome;
    }

    public Long getDurataContratto() {
        return durataContratto;
    }

    public void setDurataContratto(Long durataContratto) {
        this.durataContratto = durataContratto;
    }

    public String getContrattoEssere() {
        return contrattoEssere;
    }

    public void setContrattoEssere(String contrattoEssere) {
        this.contrattoEssere = contrattoEssere;
    }

    public BigDecimal getCosti1Anno() {
        return costi1Anno;
    }

    public void setCosti1Anno(BigDecimal costi1Anno) {
        this.costi1Anno = costi1Anno;
    }

    public BigDecimal getCosti2Anno() {
        return costi2Anno;
    }

    public void setCosti2Anno(BigDecimal costi2Anno) {
        this.costi2Anno = costi2Anno;
    }

    public BigDecimal getCosti3Anno() {
        return costi3Anno;
    }

    public void setCosti3Anno(BigDecimal costi3Anno) {
        this.costi3Anno = costi3Anno;
    }

    public BigDecimal getCostiAlAnno() {
        return costiAlAnno;
    }

    public void setCostiAlAnno(BigDecimal costiAlAnno) {
        this.costiAlAnno = costiAlAnno;
    }

    public BigDecimal getCostiTotale() {
        return costiTotale;
    }

    public void setCostiTotale(BigDecimal costiTotale) {
        this.costiTotale = costiTotale;
    }

    public BigDecimal getCapPrivImp() {
        return capPrivImp;
    }

    public void setCapPrivImp(BigDecimal capPrivImp) {
        this.capPrivImp = capPrivImp;
    }

    public String getTipoImp() {
        return tipoImp;
    }

    public void setTipoImp(String tipoImp) {
        this.tipoImp = tipoImp;
    }

    public String getCodAusa() {
        return codAusa;
    }

    public void setCodAusa(String codAusa) {
        this.codAusa = codAusa;
    }

    public String getDenominazioneAusa() {
        return denominazioneAusa;
    }

    public void setDenominazioneAusa(String denominazioneAusa) {
        this.denominazioneAusa = denominazioneAusa;
    }

    public Long getVariato() {
        return variato;
    }

    public void setVariato(Long variato) {
        this.variato = variato;
    }

    @Override
    public String toString() {
        return "ExportAcquistiQueryResult{" +
                "contri=" + contri +
                ", cui='" + cui + '\'' +
                ", cfAmm='" + cfAmm + '\'' +
                ", primaAnn=" + primaAnn +
                ", annRif=" + annRif +
                ", codCup='" + codCup + '\'' +
                ", acquistoRicompreso=" + acquistoRicompreso +
                ", codCuiLavoroCollegato='" + codCuiLavoroCollegato + '\'' +
                ", lottoF='" + lottoF + '\'' +
                ", ambitoGeografico='" + ambitoGeografico + '\'' +
                ", settore='" + settore + '\'' +
                ", cpv='" + cpv + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", priorita=" + priorita +
                ", responsabileCognome='" + responsabileCognome + '\'' +
                ", responsabileNome='" + responsabileNome + '\'' +
                ", durataContratto=" + durataContratto +
                ", contrattoEssere='" + contrattoEssere + '\'' +
                ", costi1Anno=" + costi1Anno +
                ", costi2Anno=" + costi2Anno +
                ", costi3Anno=" + costi3Anno +
                ", costiAlAnno=" + costiAlAnno +
                ", costiTotale=" + costiTotale +
                ", capPrivImp=" + capPrivImp +
                ", tipoImp='" + tipoImp + '\'' +
                ", codAusa='" + codAusa + '\'' +
                ", denominazioneAusa='" + denominazioneAusa + '\'' +
                ", variato=" + variato +
                '}';
    }

    public String getCodiceAusa() {
        return codiceAusa;
    }

    public void setCodiceAusa(String codiceAusa) {
        this.codiceAusa = codiceAusa;
    }
}
