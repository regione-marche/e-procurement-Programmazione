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

package it.appaltiecontratti.pcp.v1021.comunicaAppalto;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.CategoriaEnum;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.DatiBaseAccessibilitaType;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.DatiBaseAggiudicazioneOptionalType;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.DatiBaseAggiuntiviType;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.DatiBaseCPVType;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.DatiBaseContrattoType;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.DatiBaseTerminiInvioSoloScadenzaType;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.IpotesiCollegamentoType;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.LottoP12Type;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.ModalitaAcquisizioneEnum;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.ParitaDiGenereGenerazionaleType;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.QuadroEconomicoType;
import java.util.List;
/**
 * LottoP212Type
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-25T10:09:50.369647700+02:00[Europe/Rome]")

public class LottoP212Type extends LottoP12Type {
  @JsonProperty("datiBase")
  private AllOfLottoP212TypeDatiBase datiBase = null;

  @JsonProperty("datiBaseContratto")
  private DatiBaseContrattoType datiBaseContratto = null;

  @JsonProperty("datiBaseAggiuntivi")
  private DatiBaseAggiuntiviType datiBaseAggiuntivi = null;

  @JsonProperty("datiBaseAggiudicazione")
  private DatiBaseAggiudicazioneOptionalType datiBaseAggiudicazione = null;

  @JsonProperty("datiBaseTerminiInvio")
  private DatiBaseTerminiInvioSoloScadenzaType datiBaseTerminiInvio = null;

  @JsonProperty("datiBaseCPV")
  private DatiBaseCPVType datiBaseCPV = null;

  @JsonProperty("datiBaseAccessibilita")
  private DatiBaseAccessibilitaType datiBaseAccessibilita = null;

  @JsonProperty("datiBaseDocumenti")
  private AllOfLottoP212TypeDatiBaseDocumenti datiBaseDocumenti = null;

  public LottoP212Type datiBase(AllOfLottoP212TypeDatiBase datiBase) {
    this.datiBase = datiBase;
    return this;
  }

   /**
   * Get datiBase
   * @return datiBase
  **/
  @Schema(required = true, description = "")
  public AllOfLottoP212TypeDatiBase getDatiBase() {
    return datiBase;
  }

  public void setDatiBase(AllOfLottoP212TypeDatiBase datiBase) {
    this.datiBase = datiBase;
  }

  public LottoP212Type datiBaseContratto(DatiBaseContrattoType datiBaseContratto) {
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

  public LottoP212Type datiBaseAggiuntivi(DatiBaseAggiuntiviType datiBaseAggiuntivi) {
    this.datiBaseAggiuntivi = datiBaseAggiuntivi;
    return this;
  }

   /**
   * Get datiBaseAggiuntivi
   * @return datiBaseAggiuntivi
  **/
  @Schema(required = true, description = "")
  public DatiBaseAggiuntiviType getDatiBaseAggiuntivi() {
    return datiBaseAggiuntivi;
  }

  public void setDatiBaseAggiuntivi(DatiBaseAggiuntiviType datiBaseAggiuntivi) {
    this.datiBaseAggiuntivi = datiBaseAggiuntivi;
  }

  public LottoP212Type datiBaseAggiudicazione(DatiBaseAggiudicazioneOptionalType datiBaseAggiudicazione) {
    this.datiBaseAggiudicazione = datiBaseAggiudicazione;
    return this;
  }

   /**
   * Get datiBaseAggiudicazione
   * @return datiBaseAggiudicazione
  **/
  @Schema(description = "")
  public DatiBaseAggiudicazioneOptionalType getDatiBaseAggiudicazione() {
    return datiBaseAggiudicazione;
  }

  public void setDatiBaseAggiudicazione(DatiBaseAggiudicazioneOptionalType datiBaseAggiudicazione) {
    this.datiBaseAggiudicazione = datiBaseAggiudicazione;
  }

  public LottoP212Type datiBaseTerminiInvio(DatiBaseTerminiInvioSoloScadenzaType datiBaseTerminiInvio) {
    this.datiBaseTerminiInvio = datiBaseTerminiInvio;
    return this;
  }

   /**
   * Get datiBaseTerminiInvio
   * @return datiBaseTerminiInvio
  **/
  @Schema(required = true, description = "")
  public DatiBaseTerminiInvioSoloScadenzaType getDatiBaseTerminiInvio() {
    return datiBaseTerminiInvio;
  }

  public void setDatiBaseTerminiInvio(DatiBaseTerminiInvioSoloScadenzaType datiBaseTerminiInvio) {
    this.datiBaseTerminiInvio = datiBaseTerminiInvio;
  }

  public LottoP212Type datiBaseCPV(DatiBaseCPVType datiBaseCPV) {
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

  public LottoP212Type datiBaseAccessibilita(DatiBaseAccessibilitaType datiBaseAccessibilita) {
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

  public LottoP212Type datiBaseDocumenti(AllOfLottoP212TypeDatiBaseDocumenti datiBaseDocumenti) {
    this.datiBaseDocumenti = datiBaseDocumenti;
    return this;
  }

   /**
   * Get datiBaseDocumenti
   * @return datiBaseDocumenti
  **/
  @Schema(required = true, description = "")
  public AllOfLottoP212TypeDatiBaseDocumenti getDatiBaseDocumenti() {
    return datiBaseDocumenti;
  }

  public void setDatiBaseDocumenti(AllOfLottoP212TypeDatiBaseDocumenti datiBaseDocumenti) {
    this.datiBaseDocumenti = datiBaseDocumenti;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LottoP212Type lottoP212Type = (LottoP212Type) o;
    return Objects.equals(this.datiBase, lottoP212Type.datiBase) &&
        Objects.equals(this.datiBaseContratto, lottoP212Type.datiBaseContratto) &&
        Objects.equals(this.datiBaseAggiuntivi, lottoP212Type.datiBaseAggiuntivi) &&
        Objects.equals(this.datiBaseAggiudicazione, lottoP212Type.datiBaseAggiudicazione) &&
        Objects.equals(this.datiBaseTerminiInvio, lottoP212Type.datiBaseTerminiInvio) &&
        Objects.equals(this.datiBaseCPV, lottoP212Type.datiBaseCPV) &&
        Objects.equals(this.datiBaseAccessibilita, lottoP212Type.datiBaseAccessibilita) &&
        Objects.equals(this.datiBaseDocumenti, lottoP212Type.datiBaseDocumenti) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(datiBase, datiBaseContratto, datiBaseAggiuntivi, datiBaseAggiudicazione, datiBaseTerminiInvio, datiBaseCPV, datiBaseAccessibilita, datiBaseDocumenti, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LottoP212Type {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    datiBase: ").append(toIndentedString(datiBase)).append("\n");
    sb.append("    datiBaseContratto: ").append(toIndentedString(datiBaseContratto)).append("\n");
    sb.append("    datiBaseAggiuntivi: ").append(toIndentedString(datiBaseAggiuntivi)).append("\n");
    sb.append("    datiBaseAggiudicazione: ").append(toIndentedString(datiBaseAggiudicazione)).append("\n");
    sb.append("    datiBaseTerminiInvio: ").append(toIndentedString(datiBaseTerminiInvio)).append("\n");
    sb.append("    datiBaseCPV: ").append(toIndentedString(datiBaseCPV)).append("\n");
    sb.append("    datiBaseAccessibilita: ").append(toIndentedString(datiBaseAccessibilita)).append("\n");
    sb.append("    datiBaseDocumenti: ").append(toIndentedString(datiBaseDocumenti)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
