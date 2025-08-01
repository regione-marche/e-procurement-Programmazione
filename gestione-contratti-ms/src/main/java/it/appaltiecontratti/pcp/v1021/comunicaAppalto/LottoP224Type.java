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
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.CodIstatEnum;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.ContrattiDisposizioniParticolariEnum;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.DatiBaseAccessibilitaType;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.DatiBaseAggiudicazioneOptionalType;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.DatiBaseCPVType;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.DatiBaseContrattoType;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.DatiBaseImportoType;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.DatiTerminiInvioType;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.FinanziamentoType;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.LottoP24Type;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.MotivoEsclusioneOrdinarioSpecialeEnum;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.QuadroEconomicoConcorsiProgettazioneType;
import java.util.List;
/**
 * LottoP224Type
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-25T10:09:50.369647700+02:00[Europe/Rome]")

public class LottoP224Type extends LottoP24Type {
  @JsonProperty("datiBase")
  private AllOfLottoP224TypeDatiBase datiBase = null;

  @JsonProperty("datiBaseContratto")
  private DatiBaseContrattoType datiBaseContratto = null;

  @JsonProperty("datiBaseAggiudicazione")
  private DatiBaseAggiudicazioneOptionalType datiBaseAggiudicazione = null;

  @JsonProperty("datiBaseTerminiInvio")
  private DatiTerminiInvioType datiBaseTerminiInvio = null;

  @JsonProperty("datiBaseImporto")
  private DatiBaseImportoType datiBaseImporto = null;

  @JsonProperty("datiBaseCPV")
  private DatiBaseCPVType datiBaseCPV = null;

  @JsonProperty("datiBaseAccessibilita")
  private DatiBaseAccessibilitaType datiBaseAccessibilita = null;

  @JsonProperty("datiBaseDocumenti")
  private AllOfLottoP224TypeDatiBaseDocumenti datiBaseDocumenti = null;

  public LottoP224Type datiBase(AllOfLottoP224TypeDatiBase datiBase) {
    this.datiBase = datiBase;
    return this;
  }

   /**
   * Get datiBase
   * @return datiBase
  **/
  @Schema(required = true, description = "")
  public AllOfLottoP224TypeDatiBase getDatiBase() {
    return datiBase;
  }

  public void setDatiBase(AllOfLottoP224TypeDatiBase datiBase) {
    this.datiBase = datiBase;
  }

  public LottoP224Type datiBaseContratto(DatiBaseContrattoType datiBaseContratto) {
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

  public LottoP224Type datiBaseAggiudicazione(DatiBaseAggiudicazioneOptionalType datiBaseAggiudicazione) {
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

  public LottoP224Type datiBaseTerminiInvio(DatiTerminiInvioType datiBaseTerminiInvio) {
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

  public LottoP224Type datiBaseImporto(DatiBaseImportoType datiBaseImporto) {
    this.datiBaseImporto = datiBaseImporto;
    return this;
  }

   /**
   * Get datiBaseImporto
   * @return datiBaseImporto
  **/
  @Schema(required = true, description = "")
  public DatiBaseImportoType getDatiBaseImporto() {
    return datiBaseImporto;
  }

  public void setDatiBaseImporto(DatiBaseImportoType datiBaseImporto) {
    this.datiBaseImporto = datiBaseImporto;
  }

  public LottoP224Type datiBaseCPV(DatiBaseCPVType datiBaseCPV) {
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

  public LottoP224Type datiBaseAccessibilita(DatiBaseAccessibilitaType datiBaseAccessibilita) {
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

  public LottoP224Type datiBaseDocumenti(AllOfLottoP224TypeDatiBaseDocumenti datiBaseDocumenti) {
    this.datiBaseDocumenti = datiBaseDocumenti;
    return this;
  }

   /**
   * Get datiBaseDocumenti
   * @return datiBaseDocumenti
  **/
  @Schema(required = true, description = "")
  public AllOfLottoP224TypeDatiBaseDocumenti getDatiBaseDocumenti() {
    return datiBaseDocumenti;
  }

  public void setDatiBaseDocumenti(AllOfLottoP224TypeDatiBaseDocumenti datiBaseDocumenti) {
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
    LottoP224Type lottoP224Type = (LottoP224Type) o;
    return Objects.equals(this.datiBase, lottoP224Type.datiBase) &&
        Objects.equals(this.datiBaseContratto, lottoP224Type.datiBaseContratto) &&
        Objects.equals(this.datiBaseAggiudicazione, lottoP224Type.datiBaseAggiudicazione) &&
        Objects.equals(this.datiBaseTerminiInvio, lottoP224Type.datiBaseTerminiInvio) &&
        Objects.equals(this.datiBaseImporto, lottoP224Type.datiBaseImporto) &&
        Objects.equals(this.datiBaseCPV, lottoP224Type.datiBaseCPV) &&
        Objects.equals(this.datiBaseAccessibilita, lottoP224Type.datiBaseAccessibilita) &&
        Objects.equals(this.datiBaseDocumenti, lottoP224Type.datiBaseDocumenti) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(datiBase, datiBaseContratto, datiBaseAggiudicazione, datiBaseTerminiInvio, datiBaseImporto, datiBaseCPV, datiBaseAccessibilita, datiBaseDocumenti, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LottoP224Type {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    datiBase: ").append(toIndentedString(datiBase)).append("\n");
    sb.append("    datiBaseContratto: ").append(toIndentedString(datiBaseContratto)).append("\n");
    sb.append("    datiBaseAggiudicazione: ").append(toIndentedString(datiBaseAggiudicazione)).append("\n");
    sb.append("    datiBaseTerminiInvio: ").append(toIndentedString(datiBaseTerminiInvio)).append("\n");
    sb.append("    datiBaseImporto: ").append(toIndentedString(datiBaseImporto)).append("\n");
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
