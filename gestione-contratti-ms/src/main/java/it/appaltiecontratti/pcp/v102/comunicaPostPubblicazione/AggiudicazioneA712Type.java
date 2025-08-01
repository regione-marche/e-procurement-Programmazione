/*
 * Specifiche Servizi Appalto - OpenAPI 3.0
 * Sono descritte le Specifiche dei servizi esposti dalla Piattaforma Contratti Pubblici (PCP), richiamabili dalle Stazioni Appaltanti, per la gestione e la raccolta delle informazioni rilevanti nei processi che compongono l’intero ciclo di vita degli appalti.    #### Documentazione   La documentazione a supporto delle specifiche di interfaccia con i sistemi che interoperano con il nuovo sistema di digitalizzazione è redatta con il linguaggio di markup Markdown ed è presente al segunete url:     [documento-specifiche-servizi-npa](https://github.com/anticorruzione/npa/blob/main/docs/specifiche-interfacce/documento-specifiche-servizi-npa.md) ```
 *
 * OpenAPI spec version: 1.0.2
 * Contact: ufficio.uscp@anticorruzione.it
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AggiudicazioneA7Type;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.DatiBaseAccessibilitaType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.DatiBaseAggiudicazioneAppaltoType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.FinanziamentoType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.ParitaDiGenereGenerazionaleType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.PartecipanteADType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.QuadroEconomicoConcessioniType;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.QuadroEconomicoType;
import java.util.List;
/**
 * AggiudicazioneA712Type
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-05-23T10:12:50.940473900+02:00[Europe/Berlin]")

public class AggiudicazioneA712Type extends AggiudicazioneA7Type {
  @JsonProperty("datiBaseAggiudicazioneAppalto")
  private DatiBaseAggiudicazioneAppaltoType datiBaseAggiudicazioneAppalto = null;

  @JsonProperty("datiBaseAccessibilita")
  private DatiBaseAccessibilitaType datiBaseAccessibilita = null;

  public AggiudicazioneA712Type datiBaseAggiudicazioneAppalto(DatiBaseAggiudicazioneAppaltoType datiBaseAggiudicazioneAppalto) {
    this.datiBaseAggiudicazioneAppalto = datiBaseAggiudicazioneAppalto;
    return this;
  }

   /**
   * Get datiBaseAggiudicazioneAppalto
   * @return datiBaseAggiudicazioneAppalto
  **/
  @Schema(description = "")
  public DatiBaseAggiudicazioneAppaltoType getDatiBaseAggiudicazioneAppalto() {
    return datiBaseAggiudicazioneAppalto;
  }

  public void setDatiBaseAggiudicazioneAppalto(DatiBaseAggiudicazioneAppaltoType datiBaseAggiudicazioneAppalto) {
    this.datiBaseAggiudicazioneAppalto = datiBaseAggiudicazioneAppalto;
  }

  public AggiudicazioneA712Type datiBaseAccessibilita(DatiBaseAccessibilitaType datiBaseAccessibilita) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AggiudicazioneA712Type aggiudicazioneA712Type = (AggiudicazioneA712Type) o;
    return Objects.equals(this.datiBaseAggiudicazioneAppalto, aggiudicazioneA712Type.datiBaseAggiudicazioneAppalto) &&
        Objects.equals(this.datiBaseAccessibilita, aggiudicazioneA712Type.datiBaseAccessibilita) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(datiBaseAggiudicazioneAppalto, datiBaseAccessibilita, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AggiudicazioneA712Type {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    datiBaseAggiudicazioneAppalto: ").append(toIndentedString(datiBaseAggiudicazioneAppalto)).append("\n");
    sb.append("    datiBaseAccessibilita: ").append(toIndentedString(datiBaseAccessibilita)).append("\n");
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
