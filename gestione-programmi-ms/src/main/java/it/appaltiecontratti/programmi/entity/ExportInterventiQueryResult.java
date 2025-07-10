package it.appaltiecontratti.programmi.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since giu 15, 2023
 *
 * SELECT
 *         coalesce(IT.CONTRI,0) as CONTRI,
 * 		coalesce(IT.CUIINT,'') AS CUI,
 * 		coalesce(IT.CODINT,'') AS COD_INT,
 * 		coalesce(IT.CUPPRG,'') AS COD_CUP,
 *         coalesce(PI.ANNTRI,0)  AS PRIMA_ANN,
 * 		coalesce(IT.ANNRIF,0)  AS ANN_RIF,
 * 		coalesce(TE.NOMTEC,'') AS RESPONSABILE,
 * 		coalesce(IT.LOTFUNZ,'') AS LOTTO_F,
 * 		coalesce(IT.LAVCOMPL,'') AS LAVORO_C,
 * 		coalesce(IT.COMINT,'') AS COD_ISTAT,
 * 		coalesce(IT.NUTS,'') AS COD_NUTS,
 * 		coalesce(TAB3.tab3desc,'') AS TIPOLOGIA,
 * 		coalesce(TAB5.tab5desc,'') AS CATEGORIA,
 * 		coalesce(IT.DESINT,'') AS DESCRIZIONE,
 * 		coalesce(IT.PRGINT,0)  AS PRIORITA,
 * 		(coalesce(IT.DI1INT,0.00) + coalesce(IT.PR1TRI,0.00)) AS COSTI_1ANNO,
 * 		(coalesce(IT.DI2INT,0.00) + coalesce(IT.PR2TRI,0.00)) AS COSTI_2ANNO,
 *         (coalesce(IT.DI3INT,0.00) + coalesce(IT.PR3TRI,0.00)) AS COSTI_3ANNO,
 * 		(coalesce(IT.DI9INT,0.00) + coalesce(IT.PR9TRI,0.00)) AS COSTI_ALANNO,
 * 		coalesce(IT.TOTINT,0.00) AS COSTI_TOTALE,
 * 		(coalesce(IT.IM1TRI,0.00) + coalesce(IT.IM2TRI,0.00) + coalesce(IT.IM3TRI,0.00) + coalesce(IT.IM9TRI,0.00)) AS IMMOBILI,
 * 		IT.SCAMUT AS SCADENZA,
 * 		coalesce(IT.ICPINT,0.00) AS CAP_PRIV_IMP,
 * 		coalesce(IT.TCPINT,'') AS TIPO_IMP,
 * 		IT.VARIATO AS VARIATO
 * 	FROM PIATRI PI LEFT JOIN INTTRI IT ON PI.CONTRI=IT.CONTRI
 *     LEFT JOIN TECNI TE ON IT.CODRUP = TE.CODTEC
 *     LEFT JOIN TAB3 ON IT.SEZINT = TAB3.tab3tip AND tab3cod='PTx01'
 *     LEFT JOIN TAB5 ON IT.INTERV = TAB5.tab5tip AND tab5cod='PTj01'
 *     WHERE PI.CONTRI = 1
 *     ORDER BY IT.ANNRIF, IT.NPROGINT
 */
public class ExportInterventiQueryResult {
    private Long contri;
    private String cui;
    private String codInt;
    private String codCup;
    private Long primaAnn;
    private Long annRif;
    private String responsabile;
    private String lottoF;
    private String lavoroC;
    private String codIstat;
    private String codNuts;
    private String tipologia;
    private String categoria;
    private String descrizione;
    private Long priorita;
    private BigDecimal costi1Anno;
    private BigDecimal costi2Anno;
    private BigDecimal costi3Anno;
    private BigDecimal costiAlAnno;
    private BigDecimal costiTotale;
    private BigDecimal immobili;
    private Date scadenza;
    private BigDecimal capPrivImp;
    private String tipoImp;
    private Long variato;
    private String cigAccordoQuadro;

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

    public String getCodInt() {
        return codInt;
    }

    public void setCodInt(String codInt) {
        this.codInt = codInt;
    }

    public String getCodCup() {
        return codCup;
    }

    public void setCodCup(String codCup) {
        this.codCup = codCup;
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

    public String getResponsabile() {
        return responsabile;
    }

    public void setResponsabile(String responsabile) {
        this.responsabile = responsabile;
    }

    public String getLottoF() {
        return lottoF;
    }

    public void setLottoF(String lottoF) {
        this.lottoF = lottoF;
    }

    public String getLavoroC() {
        return lavoroC;
    }

    public void setLavoroC(String lavoroC) {
        this.lavoroC = lavoroC;
    }

    public String getCodIstat() {
        return codIstat;
    }

    public void setCodIstat(String codIstat) {
        this.codIstat = codIstat;
    }

    public String getCodNuts() {
        return codNuts;
    }

    public void setCodNuts(String codNuts) {
        this.codNuts = codNuts;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public BigDecimal getImmobili() {
        return immobili;
    }

    public void setImmobili(BigDecimal immobili) {
        this.immobili = immobili;
    }

    public Date getScadenza() {
        return scadenza;
    }

    public void setScadenza(Date scadenza) {
        this.scadenza = scadenza;
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

    public Long getVariato() {
        return variato;
    }

    public void setVariato(Long variato) {
        this.variato = variato;
    }

    @Override
    public String toString() {
        return "ExportInterventiTipoProg1QueryResult{" +
                "contri=" + contri +
                ", cui='" + cui + '\'' +
                ", codInt='" + codInt + '\'' +
                ", codCup='" + codCup + '\'' +
                ", primaAnn=" + primaAnn +
                ", annRif=" + annRif +
                ", responsabile='" + responsabile + '\'' +
                ", lottoF='" + lottoF + '\'' +
                ", lavoroC='" + lavoroC + '\'' +
                ", codIstat='" + codIstat + '\'' +
                ", codNuts='" + codNuts + '\'' +
                ", tipologia='" + tipologia + '\'' +
                ", categoria='" + categoria + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", priorita=" + priorita +
                ", costi1Anno=" + costi1Anno +
                ", costi2Anno=" + costi2Anno +
                ", costi3Anno=" + costi3Anno +
                ", costiAlAnno=" + costiAlAnno +
                ", costiTotale=" + costiTotale +
                ", immobili=" + immobili +
                ", scadenza=" + scadenza +
                ", capPrivImp=" + capPrivImp +
                ", tipoImp='" + tipoImp + '\'' +
                ", variato=" + variato +
                '}';
    }

	public String getCigAccordoQuadro() {
		return cigAccordoQuadro;
	}

	public void setCigAccordoQuadro(String cigAccordoQuadro) {
		this.cigAccordoQuadro = cigAccordoQuadro;
	}


}
