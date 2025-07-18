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

package it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.RuoloOEEnum;
import it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.TipoOEEnum;
/**
 * Dati dell&#x27;invitato
 */
@Schema(description = "Dati dell'invitato")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-25T10:09:29.128631100+02:00[Europe/Rome]")

public class InvitatoType {
  @JsonProperty("codiceFiscale")
  private String codiceFiscale = null;

  @JsonProperty("denominazione")
  private String denominazione = null;

  @JsonProperty("ruoloOE")
  private RuoloOEEnum ruoloOE = null;

  @JsonProperty("tipoOE")
  private TipoOEEnum tipoOE = null;

  public InvitatoType codiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
    return this;
  }

   /**
   * codice fiscale del soggetto interessato
   * @return codiceFiscale
  **/
  @Schema(description = "codice fiscale del soggetto interessato")
  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public InvitatoType denominazione(String denominazione) {
    this.denominazione = denominazione;
    return this;
  }

   /**
   * denominazione del soggetto interessato
   * @return denominazione
  **/
  @Schema(description = "denominazione del soggetto interessato")
  public String getDenominazione() {
    return denominazione;
  }

  public void setDenominazione(String denominazione) {
    this.denominazione = denominazione;
  }

  public InvitatoType ruoloOE(RuoloOEEnum ruoloOE) {
    this.ruoloOE = ruoloOE;
    return this;
  }

   /**
   * Get ruoloOE
   * @return ruoloOE
  **/
  @Schema(description = "")
  public RuoloOEEnum getRuoloOE() {
    return ruoloOE;
  }

  public void setRuoloOE(RuoloOEEnum ruoloOE) {
    this.ruoloOE = ruoloOE;
  }

  public InvitatoType tipoOE(TipoOEEnum tipoOE) {
    this.tipoOE = tipoOE;
    return this;
  }

   /**
   * Get tipoOE
   * @return tipoOE
  **/
  @Schema(description = "")
  public TipoOEEnum getTipoOE() {
    return tipoOE;
  }

  public void setTipoOE(TipoOEEnum tipoOE) {
    this.tipoOE = tipoOE;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InvitatoType invitatoType = (InvitatoType) o;
    return Objects.equals(this.codiceFiscale, invitatoType.codiceFiscale) &&
        Objects.equals(this.denominazione, invitatoType.denominazione) &&
        Objects.equals(this.ruoloOE, invitatoType.ruoloOE) &&
        Objects.equals(this.tipoOE, invitatoType.tipoOE);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceFiscale, denominazione, ruoloOE, tipoOE);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InvitatoType {\n");
    
    sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
    sb.append("    denominazione: ").append(toIndentedString(denominazione)).append("\n");
    sb.append("    ruoloOE: ").append(toIndentedString(ruoloOE)).append("\n");
    sb.append("    tipoOE: ").append(toIndentedString(tipoOE)).append("\n");
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
