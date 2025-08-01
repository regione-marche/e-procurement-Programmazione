/*
 * Specifiche Servizi Appalto - OpenAPI 3.0
 * Sono descritte le Specifiche dei servizi esposti dalla Piattaforma Contratti Pubblici (PCP), richiamabili dalle Stazioni Appaltanti, per la gestione e la raccolta delle informazioni rilevanti nei processi che compongono l’intero ciclo di vita degli appalti.    #### Documentazione   La documentazione a supporto delle specifiche di interfaccia con i sistemi che interoperano con il nuovo sistema di digitalizzazione è redatta con il linguaggio di markup Markdown ed è presente al segunete url:     [documento-specifiche-servizi-npa](https://github.com/anticorruzione/npa/blob/main/docs/specifiche-interfacce/documento-specifiche-servizi-npa.md) ```
 *
 * OpenAPI spec version: 1.0.3
 * Contact: ufficio.uscp@anticorruzione.it
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package it.appaltiecontratti.pcp.v102.comunicaAppalto;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.CategoriaEnum;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.CodIstatEnum;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.CondizioniNegoziataEnum;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.ContrattiDisposizioniParticolariEnum;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.DatiBaseAccessibilitaType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.DatiBaseCPVType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.DatiBaseContrattoType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.DatiBaseDocumentiType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.DatiTerminiInvioType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.FinanziamentoType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.IpotesiCollegamentoType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.LottoP3BaseType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.ModalitaAcquisizioneEnum;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.ParitaDiGenereGenerazionaleType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.QuadroEconomicoType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.TipoRealizzazioneContrattoEnum;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.TipologiaLavoroEnum;
import java.util.ArrayList;
import java.util.List;
/**
 * Oggetto che riporta i dati del lotto.
 */
@Schema(description = "Oggetto che riporta i dati del lotto.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-05-23T10:13:15.678079400+02:00[Europe/Berlin]")

public class LottoP35Type extends LottoP3BaseType {
  @JsonProperty("afferenteInvestimentiPNRR")
  private Boolean afferenteInvestimentiPNRR = null;

  @JsonProperty("categoria")
  private CategoriaEnum categoria = null;

  @JsonProperty("categoriaScorporabile")
  private List<CategoriaEnum> categoriaScorporabile = null;

  @JsonProperty("servizioPubblicoLocale")
  private Boolean servizioPubblicoLocale = null;

  @JsonProperty("ripetizioniEConsegneComplementari")
  private Boolean ripetizioniEConsegneComplementari = null;

  @JsonProperty("strumentiElettroniciSpecifici")
  private Boolean strumentiElettroniciSpecifici = null;

  @JsonProperty("condizioniNegoziata")
  private List<CondizioniNegoziataEnum> condizioniNegoziata = null;

  @JsonProperty("contrattiDisposizioniParticolari")
  private ContrattiDisposizioniParticolariEnum contrattiDisposizioniParticolari = null;

  @JsonProperty("lavoroOAcquistoPrevistoInProgrammazione")
  private Boolean lavoroOAcquistoPrevistoInProgrammazione = null;

  @JsonProperty("cui")
  private String cui = null;

  @JsonProperty("tipoRealizzazione")
  private TipoRealizzazioneContrattoEnum tipoRealizzazione = null;

  @JsonProperty("ipotesiCollegamento")
  private IpotesiCollegamentoType ipotesiCollegamento = null;

  @JsonProperty("opzioniRinnovi")
  private Boolean opzioniRinnovi = null;

  @JsonProperty("quadroEconomicoStandard")
  private QuadroEconomicoType quadroEconomicoStandard = null;

  @JsonProperty("datiBase")
  private AllOfLottoP35TypeDatiBase datiBase = null;

  @JsonProperty("datiBaseCPV")
  private DatiBaseCPVType datiBaseCPV = null;

  @JsonProperty("datiBaseContratto")
  private DatiBaseContrattoType datiBaseContratto = null;

  @JsonProperty("datiBaseAggiudicazione")
  private AllOfLottoP35TypeDatiBaseAggiudicazione datiBaseAggiudicazione = null;

  @JsonProperty("datiBaseTerminiInvio")
  private DatiTerminiInvioType datiBaseTerminiInvio = null;

  @JsonProperty("datiBaseAccessibilita")
  private DatiBaseAccessibilitaType datiBaseAccessibilita = null;

  @JsonProperty("datiBaseDocumenti")
  private DatiBaseDocumentiType datiBaseDocumenti = null;

  public LottoP35Type afferenteInvestimentiPNRR(Boolean afferenteInvestimentiPNRR) {
    this.afferenteInvestimentiPNRR = afferenteInvestimentiPNRR;
    return this;
  }

   /**
   * L’appalto o concessione è afferente gli investimenti pubblici finanziati, in tutto o in parte, con le risorse previste dal PNRR (Piano Nazionale di Ripresa e Resilienza) e/o dal PNC (Piano nazionale per gli investimenti complementari)?
   * @return afferenteInvestimentiPNRR
  **/
  @Schema(required = true, description = "L’appalto o concessione è afferente gli investimenti pubblici finanziati, in tutto o in parte, con le risorse previste dal PNRR (Piano Nazionale di Ripresa e Resilienza) e/o dal PNC (Piano nazionale per gli investimenti complementari)?")
  public Boolean isAfferenteInvestimentiPNRR() {
    return afferenteInvestimentiPNRR;
  }

  public void setAfferenteInvestimentiPNRR(Boolean afferenteInvestimentiPNRR) {
    this.afferenteInvestimentiPNRR = afferenteInvestimentiPNRR;
  }

  public LottoP35Type categoria(CategoriaEnum categoria) {
    this.categoria = categoria;
    return this;
  }

   /**
   * Get categoria
   * @return categoria
  **/
  @Schema(required = true, description = "")
  public CategoriaEnum getCategoria() {
    return categoria;
  }

  public void setCategoria(CategoriaEnum categoria) {
    this.categoria = categoria;
  }

  public LottoP35Type categoriaScorporabile(List<CategoriaEnum> categoriaScorporabile) {
    this.categoriaScorporabile = categoriaScorporabile;
    return this;
  }

  public LottoP35Type addCategoriaScorporabileItem(CategoriaEnum categoriaScorporabileItem) {
    if (this.categoriaScorporabile == null) {
      this.categoriaScorporabile = new ArrayList<>();
    }
    this.categoriaScorporabile.add(categoriaScorporabileItem);
    return this;
  }

   /**
   * Get categoriaScorporabile
   * @return categoriaScorporabile
  **/
  @Schema(description = "")
  public List<CategoriaEnum> getCategoriaScorporabile() {
    return categoriaScorporabile;
  }

  public void setCategoriaScorporabile(List<CategoriaEnum> categoriaScorporabile) {
    this.categoriaScorporabile = categoriaScorporabile;
  }

  public LottoP35Type servizioPubblicoLocale(Boolean servizioPubblicoLocale) {
    this.servizioPubblicoLocale = servizioPubblicoLocale;
    return this;
  }

   /**
   * Flag servizio pubblico locale
   * @return servizioPubblicoLocale
  **/
  @Schema(required = true, description = "Flag servizio pubblico locale")
  public Boolean isServizioPubblicoLocale() {
    return servizioPubblicoLocale;
  }

  public void setServizioPubblicoLocale(Boolean servizioPubblicoLocale) {
    this.servizioPubblicoLocale = servizioPubblicoLocale;
  }

  public LottoP35Type ripetizioniEConsegneComplementari(Boolean ripetizioniEConsegneComplementari) {
    this.ripetizioniEConsegneComplementari = ripetizioniEConsegneComplementari;
    return this;
  }

   /**
   * L’appalto prevede ripetizioni di servizi/forniture/lavori analoghi e consegne complementari?
   * @return ripetizioniEConsegneComplementari
  **/
  @Schema(required = true, description = "L’appalto prevede ripetizioni di servizi/forniture/lavori analoghi e consegne complementari?")
  public Boolean isRipetizioniEConsegneComplementari() {
    return ripetizioniEConsegneComplementari;
  }

  public void setRipetizioniEConsegneComplementari(Boolean ripetizioniEConsegneComplementari) {
    this.ripetizioniEConsegneComplementari = ripetizioniEConsegneComplementari;
  }

  public LottoP35Type strumentiElettroniciSpecifici(Boolean strumentiElettroniciSpecifici) {
    this.strumentiElettroniciSpecifici = strumentiElettroniciSpecifici;
    return this;
  }

   /**
   * Uso e metodi e strumenti elettronici specifici, quali quelli di modellazione per l’edilizia e le infrastrutture, nelle fasi di progettazione, costruzione e gestione delle opere e relative verifiche
   * @return strumentiElettroniciSpecifici
  **/
  @Schema(description = "Uso e metodi e strumenti elettronici specifici, quali quelli di modellazione per l’edilizia e le infrastrutture, nelle fasi di progettazione, costruzione e gestione delle opere e relative verifiche")
  public Boolean isStrumentiElettroniciSpecifici() {
    return strumentiElettroniciSpecifici;
  }

  public void setStrumentiElettroniciSpecifici(Boolean strumentiElettroniciSpecifici) {
    this.strumentiElettroniciSpecifici = strumentiElettroniciSpecifici;
  }

  public LottoP35Type condizioniNegoziata(List<CondizioniNegoziataEnum> condizioniNegoziata) {
    this.condizioniNegoziata = condizioniNegoziata;
    return this;
  }

  public LottoP35Type addCondizioniNegoziataItem(CondizioniNegoziataEnum condizioniNegoziataItem) {
    if (this.condizioniNegoziata == null) {
      this.condizioniNegoziata = new ArrayList<>();
    }
    this.condizioniNegoziata.add(condizioniNegoziataItem);
    return this;
  }

   /**
   * Get condizioniNegoziata
   * @return condizioniNegoziata
  **/
  @Schema(description = "")
  public List<CondizioniNegoziataEnum> getCondizioniNegoziata() {
    return condizioniNegoziata;
  }

  public void setCondizioniNegoziata(List<CondizioniNegoziataEnum> condizioniNegoziata) {
    this.condizioniNegoziata = condizioniNegoziata;
  }

  public LottoP35Type contrattiDisposizioniParticolari(ContrattiDisposizioniParticolariEnum contrattiDisposizioniParticolari) {
    this.contrattiDisposizioniParticolari = contrattiDisposizioniParticolari;
    return this;
  }

   /**
   * Get contrattiDisposizioniParticolari
   * @return contrattiDisposizioniParticolari
  **/
  @Schema(required = true, description = "")
  public ContrattiDisposizioniParticolariEnum getContrattiDisposizioniParticolari() {
    return contrattiDisposizioniParticolari;
  }

  public void setContrattiDisposizioniParticolari(ContrattiDisposizioniParticolariEnum contrattiDisposizioniParticolari) {
    this.contrattiDisposizioniParticolari = contrattiDisposizioniParticolari;
  }

  public LottoP35Type lavoroOAcquistoPrevistoInProgrammazione(Boolean lavoroOAcquistoPrevistoInProgrammazione) {
    this.lavoroOAcquistoPrevistoInProgrammazione = lavoroOAcquistoPrevistoInProgrammazione;
    return this;
  }

   /**
   * Il lavoro o l’acquisto di bene o servizio è stato previsto all’interno della programmazione
   * @return lavoroOAcquistoPrevistoInProgrammazione
  **/
  @Schema(required = true, description = "Il lavoro o l’acquisto di bene o servizio è stato previsto all’interno della programmazione")
  public Boolean isLavoroOAcquistoPrevistoInProgrammazione() {
    return lavoroOAcquistoPrevistoInProgrammazione;
  }

  public void setLavoroOAcquistoPrevistoInProgrammazione(Boolean lavoroOAcquistoPrevistoInProgrammazione) {
    this.lavoroOAcquistoPrevistoInProgrammazione = lavoroOAcquistoPrevistoInProgrammazione;
  }

  public LottoP35Type cui(String cui) {
    this.cui = cui;
    return this;
  }

   /**
   * CUI programma triennale lavori pubblici o programma biennale forniture e servizi
   * @return cui
  **/
  @Schema(description = "CUI programma triennale lavori pubblici o programma biennale forniture e servizi")
  public String getCui() {
    return cui;
  }

  public void setCui(String cui) {
    this.cui = cui;
  }

  public LottoP35Type tipoRealizzazione(TipoRealizzazioneContrattoEnum tipoRealizzazione) {
    this.tipoRealizzazione = tipoRealizzazione;
    return this;
  }

   /**
   * Get tipoRealizzazione
   * @return tipoRealizzazione
  **/
  @Schema(description = "")
  public TipoRealizzazioneContrattoEnum getTipoRealizzazione() {
    return tipoRealizzazione;
  }

  public void setTipoRealizzazione(TipoRealizzazioneContrattoEnum tipoRealizzazione) {
    this.tipoRealizzazione = tipoRealizzazione;
  }

  public LottoP35Type ipotesiCollegamento(IpotesiCollegamentoType ipotesiCollegamento) {
    this.ipotesiCollegamento = ipotesiCollegamento;
    return this;
  }

   /**
   * Get ipotesiCollegamento
   * @return ipotesiCollegamento
  **/
  @Schema(description = "")
  public IpotesiCollegamentoType getIpotesiCollegamento() {
    return ipotesiCollegamento;
  }

  public void setIpotesiCollegamento(IpotesiCollegamentoType ipotesiCollegamento) {
    this.ipotesiCollegamento = ipotesiCollegamento;
  }

  public LottoP35Type opzioniRinnovi(Boolean opzioniRinnovi) {
    this.opzioniRinnovi = opzioniRinnovi;
    return this;
  }

   /**
   * L’appalto prevede opzioni o rinnovi?
   * @return opzioniRinnovi
  **/
  @Schema(required = true, description = "L’appalto prevede opzioni o rinnovi?")
  public Boolean isOpzioniRinnovi() {
    return opzioniRinnovi;
  }

  public void setOpzioniRinnovi(Boolean opzioniRinnovi) {
    this.opzioniRinnovi = opzioniRinnovi;
  }

  public LottoP35Type quadroEconomicoStandard(QuadroEconomicoType quadroEconomicoStandard) {
    this.quadroEconomicoStandard = quadroEconomicoStandard;
    return this;
  }

   /**
   * Get quadroEconomicoStandard
   * @return quadroEconomicoStandard
  **/
  @Schema(description = "")
  public QuadroEconomicoType getQuadroEconomicoStandard() {
    return quadroEconomicoStandard;
  }

  public void setQuadroEconomicoStandard(QuadroEconomicoType quadroEconomicoStandard) {
    this.quadroEconomicoStandard = quadroEconomicoStandard;
  }

  public LottoP35Type datiBase(AllOfLottoP35TypeDatiBase datiBase) {
    this.datiBase = datiBase;
    return this;
  }

   /**
   * Get datiBase
   * @return datiBase
  **/
  @Schema(required = true, description = "")
  public AllOfLottoP35TypeDatiBase getDatiBase() {
    return datiBase;
  }

  public void setDatiBase(AllOfLottoP35TypeDatiBase datiBase) {
    this.datiBase = datiBase;
  }

  public LottoP35Type datiBaseCPV(DatiBaseCPVType datiBaseCPV) {
    this.datiBaseCPV = datiBaseCPV;
    return this;
  }

   /**
   * Get datiBaseCPV
   * @return datiBaseCPV
  **/
  @Schema(required = true, description = "")
  public DatiBaseCPVType getDatiBaseCPV() {
    return datiBaseCPV;
  }

  public void setDatiBaseCPV(DatiBaseCPVType datiBaseCPV) {
    this.datiBaseCPV = datiBaseCPV;
  }

  public LottoP35Type datiBaseContratto(DatiBaseContrattoType datiBaseContratto) {
    this.datiBaseContratto = datiBaseContratto;
    return this;
  }

   /**
   * Get datiBaseContratto
   * @return datiBaseContratto
  **/
  @Schema(description = "")
  public DatiBaseContrattoType getDatiBaseContratto() {
    return datiBaseContratto;
  }

  public void setDatiBaseContratto(DatiBaseContrattoType datiBaseContratto) {
    this.datiBaseContratto = datiBaseContratto;
  }

  public LottoP35Type datiBaseAggiudicazione(AllOfLottoP35TypeDatiBaseAggiudicazione datiBaseAggiudicazione) {
    this.datiBaseAggiudicazione = datiBaseAggiudicazione;
    return this;
  }

   /**
   * Get datiBaseAggiudicazione
   * @return datiBaseAggiudicazione
  **/
  @Schema(description = "")
  public AllOfLottoP35TypeDatiBaseAggiudicazione getDatiBaseAggiudicazione() {
    return datiBaseAggiudicazione;
  }

  public void setDatiBaseAggiudicazione(AllOfLottoP35TypeDatiBaseAggiudicazione datiBaseAggiudicazione) {
    this.datiBaseAggiudicazione = datiBaseAggiudicazione;
  }

  public LottoP35Type datiBaseTerminiInvio(DatiTerminiInvioType datiBaseTerminiInvio) {
    this.datiBaseTerminiInvio = datiBaseTerminiInvio;
    return this;
  }

   /**
   * Get datiBaseTerminiInvio
   * @return datiBaseTerminiInvio
  **/
  @Schema(required = true, description = "")
  public DatiTerminiInvioType getDatiBaseTerminiInvio() {
    return datiBaseTerminiInvio;
  }

  public void setDatiBaseTerminiInvio(DatiTerminiInvioType datiBaseTerminiInvio) {
    this.datiBaseTerminiInvio = datiBaseTerminiInvio;
  }

  public LottoP35Type datiBaseAccessibilita(DatiBaseAccessibilitaType datiBaseAccessibilita) {
    this.datiBaseAccessibilita = datiBaseAccessibilita;
    return this;
  }

   /**
   * Get datiBaseAccessibilita
   * @return datiBaseAccessibilita
  **/
  @Schema(description = "")
  public DatiBaseAccessibilitaType getDatiBaseAccessibilita() {
    return datiBaseAccessibilita;
  }

  public void setDatiBaseAccessibilita(DatiBaseAccessibilitaType datiBaseAccessibilita) {
    this.datiBaseAccessibilita = datiBaseAccessibilita;
  }

  public LottoP35Type datiBaseDocumenti(DatiBaseDocumentiType datiBaseDocumenti) {
    this.datiBaseDocumenti = datiBaseDocumenti;
    return this;
  }

   /**
   * Get datiBaseDocumenti
   * @return datiBaseDocumenti
  **/
  @Schema(required = true, description = "")
  public DatiBaseDocumentiType getDatiBaseDocumenti() {
    return datiBaseDocumenti;
  }

  public void setDatiBaseDocumenti(DatiBaseDocumentiType datiBaseDocumenti) {
    this.datiBaseDocumenti = datiBaseDocumenti;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LottoP35Type lottoP35Type = (LottoP35Type) o;
    return Objects.equals(this.afferenteInvestimentiPNRR, lottoP35Type.afferenteInvestimentiPNRR) &&
        Objects.equals(this.categoria, lottoP35Type.categoria) &&
        Objects.equals(this.categoriaScorporabile, lottoP35Type.categoriaScorporabile) &&
        Objects.equals(this.servizioPubblicoLocale, lottoP35Type.servizioPubblicoLocale) &&
        Objects.equals(this.ripetizioniEConsegneComplementari, lottoP35Type.ripetizioniEConsegneComplementari) &&
        Objects.equals(this.strumentiElettroniciSpecifici, lottoP35Type.strumentiElettroniciSpecifici) &&
        Objects.equals(this.condizioniNegoziata, lottoP35Type.condizioniNegoziata) &&
        Objects.equals(this.contrattiDisposizioniParticolari, lottoP35Type.contrattiDisposizioniParticolari) &&
        Objects.equals(this.lavoroOAcquistoPrevistoInProgrammazione, lottoP35Type.lavoroOAcquistoPrevistoInProgrammazione) &&
        Objects.equals(this.cui, lottoP35Type.cui) &&
        Objects.equals(this.tipoRealizzazione, lottoP35Type.tipoRealizzazione) &&
        Objects.equals(this.ipotesiCollegamento, lottoP35Type.ipotesiCollegamento) &&
        Objects.equals(this.opzioniRinnovi, lottoP35Type.opzioniRinnovi) &&
        Objects.equals(this.quadroEconomicoStandard, lottoP35Type.quadroEconomicoStandard) &&
        Objects.equals(this.datiBase, lottoP35Type.datiBase) &&
        Objects.equals(this.datiBaseCPV, lottoP35Type.datiBaseCPV) &&
        Objects.equals(this.datiBaseContratto, lottoP35Type.datiBaseContratto) &&
        Objects.equals(this.datiBaseAggiudicazione, lottoP35Type.datiBaseAggiudicazione) &&
        Objects.equals(this.datiBaseTerminiInvio, lottoP35Type.datiBaseTerminiInvio) &&
        Objects.equals(this.datiBaseAccessibilita, lottoP35Type.datiBaseAccessibilita) &&
        Objects.equals(this.datiBaseDocumenti, lottoP35Type.datiBaseDocumenti) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(afferenteInvestimentiPNRR, categoria, categoriaScorporabile, servizioPubblicoLocale, ripetizioniEConsegneComplementari, strumentiElettroniciSpecifici, condizioniNegoziata, contrattiDisposizioniParticolari, lavoroOAcquistoPrevistoInProgrammazione, cui, tipoRealizzazione, ipotesiCollegamento, opzioniRinnovi, quadroEconomicoStandard, datiBase, datiBaseCPV, datiBaseContratto, datiBaseAggiudicazione, datiBaseTerminiInvio, datiBaseAccessibilita, datiBaseDocumenti, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LottoP35Type {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    afferenteInvestimentiPNRR: ").append(toIndentedString(afferenteInvestimentiPNRR)).append("\n");
    sb.append("    categoria: ").append(toIndentedString(categoria)).append("\n");
    sb.append("    categoriaScorporabile: ").append(toIndentedString(categoriaScorporabile)).append("\n");
    sb.append("    servizioPubblicoLocale: ").append(toIndentedString(servizioPubblicoLocale)).append("\n");
    sb.append("    ripetizioniEConsegneComplementari: ").append(toIndentedString(ripetizioniEConsegneComplementari)).append("\n");
    sb.append("    strumentiElettroniciSpecifici: ").append(toIndentedString(strumentiElettroniciSpecifici)).append("\n");
    sb.append("    condizioniNegoziata: ").append(toIndentedString(condizioniNegoziata)).append("\n");
    sb.append("    contrattiDisposizioniParticolari: ").append(toIndentedString(contrattiDisposizioniParticolari)).append("\n");
    sb.append("    lavoroOAcquistoPrevistoInProgrammazione: ").append(toIndentedString(lavoroOAcquistoPrevistoInProgrammazione)).append("\n");
    sb.append("    cui: ").append(toIndentedString(cui)).append("\n");
    sb.append("    tipoRealizzazione: ").append(toIndentedString(tipoRealizzazione)).append("\n");
    sb.append("    ipotesiCollegamento: ").append(toIndentedString(ipotesiCollegamento)).append("\n");
    sb.append("    opzioniRinnovi: ").append(toIndentedString(opzioniRinnovi)).append("\n");
    sb.append("    quadroEconomicoStandard: ").append(toIndentedString(quadroEconomicoStandard)).append("\n");
    sb.append("    datiBase: ").append(toIndentedString(datiBase)).append("\n");
    sb.append("    datiBaseCPV: ").append(toIndentedString(datiBaseCPV)).append("\n");
    sb.append("    datiBaseContratto: ").append(toIndentedString(datiBaseContratto)).append("\n");
    sb.append("    datiBaseAggiudicazione: ").append(toIndentedString(datiBaseAggiudicazione)).append("\n");
    sb.append("    datiBaseTerminiInvio: ").append(toIndentedString(datiBaseTerminiInvio)).append("\n");
    sb.append("    datiBaseAccessibilita: ").append(toIndentedString(datiBaseAccessibilita)).append("\n");
    sb.append("    datiBaseDocumenti: ").append(toIndentedString(datiBaseDocumenti)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
