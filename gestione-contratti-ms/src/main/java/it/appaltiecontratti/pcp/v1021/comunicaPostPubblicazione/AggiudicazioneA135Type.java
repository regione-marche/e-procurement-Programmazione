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
import it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.AggiudicazioneBaseType;
import it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.EsitoProceduraAnnullataEnum;
import it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.OfferteType;
import it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.QuadroEconomicoConcessioniType;
import java.math.BigDecimal;
import java.util.List;
/**
 * Oggetto che riporta i dati inerenti la fase di aggiudicazione sopra soglia e sotto soglia
 */
@Schema(description = "Oggetto che riporta i dati inerenti la fase di aggiudicazione sopra soglia e sotto soglia")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-25T10:09:29.128631100+02:00[Europe/Rome]")

public class AggiudicazioneA135Type extends AggiudicazioneBaseType {
  @JsonProperty("numeroOfferteAmmesse")
  private BigDecimal numeroOfferteAmmesse = null;

  @JsonProperty("quadroEconomicoConcessioni")
  private QuadroEconomicoConcessioniType quadroEconomicoConcessioni = null;

  @JsonProperty("esitoProceduraAnnullata")
  private EsitoProceduraAnnullataEnum esitoProceduraAnnullata = null;

  public AggiudicazioneA135Type numeroOfferteAmmesse(BigDecimal numeroOfferteAmmesse) {
    this.numeroOfferteAmmesse = numeroOfferteAmmesse;
    return this;
  }

   /**
   * numero di offerte ammesse
   * @return numeroOfferteAmmesse
  **/
  @Schema(required = true, description = "numero di offerte ammesse")
  public BigDecimal getNumeroOfferteAmmesse() {
    return numeroOfferteAmmesse;
  }

  public void setNumeroOfferteAmmesse(BigDecimal numeroOfferteAmmesse) {
    this.numeroOfferteAmmesse = numeroOfferteAmmesse;
  }

  public AggiudicazioneA135Type quadroEconomicoConcessioni(QuadroEconomicoConcessioniType quadroEconomicoConcessioni) {
    this.quadroEconomicoConcessioni = quadroEconomicoConcessioni;
    return this;
  }

   /**
   * Get quadroEconomicoConcessioni
   * @return quadroEconomicoConcessioni
  **/
  @Schema(required = true, description = "")
  public QuadroEconomicoConcessioniType getQuadroEconomicoConcessioni() {
    return quadroEconomicoConcessioni;
  }

  public void setQuadroEconomicoConcessioni(QuadroEconomicoConcessioniType quadroEconomicoConcessioni) {
    this.quadroEconomicoConcessioni = quadroEconomicoConcessioni;
  }

  public AggiudicazioneA135Type esitoProceduraAnnullata(EsitoProceduraAnnullataEnum esitoProceduraAnnullata) {
    this.esitoProceduraAnnullata = esitoProceduraAnnullata;
    return this;
  }

   /**
   * Get esitoProceduraAnnullata
   * @return esitoProceduraAnnullata
  **/
  @Schema(description = "")
  public EsitoProceduraAnnullataEnum getEsitoProceduraAnnullata() {
    return esitoProceduraAnnullata;
  }

  public void setEsitoProceduraAnnullata(EsitoProceduraAnnullataEnum esitoProceduraAnnullata) {
    this.esitoProceduraAnnullata = esitoProceduraAnnullata;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AggiudicazioneA135Type aggiudicazioneA135Type = (AggiudicazioneA135Type) o;
    return Objects.equals(this.numeroOfferteAmmesse, aggiudicazioneA135Type.numeroOfferteAmmesse) &&
        Objects.equals(this.quadroEconomicoConcessioni, aggiudicazioneA135Type.quadroEconomicoConcessioni) &&
        Objects.equals(this.esitoProceduraAnnullata, aggiudicazioneA135Type.esitoProceduraAnnullata) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numeroOfferteAmmesse, quadroEconomicoConcessioni, esitoProceduraAnnullata, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AggiudicazioneA135Type {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    numeroOfferteAmmesse: ").append(toIndentedString(numeroOfferteAmmesse)).append("\n");
    sb.append("    quadroEconomicoConcessioni: ").append(toIndentedString(quadroEconomicoConcessioni)).append("\n");
    sb.append("    esitoProceduraAnnullata: ").append(toIndentedString(esitoProceduraAnnullata)).append("\n");
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
