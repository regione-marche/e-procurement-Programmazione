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
/**
 * Dato in comune con il Business Group Received Submissions delle eforms
 */
@Schema(description = "Dato in comune con il Business Group Received Submissions delle eforms")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-25T10:09:29.128631100+02:00[Europe/Rome]")

public class DatiBaseSottomissioniRicevuteType {
  @JsonProperty("offertaMinimoRibasso")
  private Double offertaMinimoRibasso = null;

  @JsonProperty("offertaMassimoRibasso")
  private Double offertaMassimoRibasso = null;

  public DatiBaseSottomissioniRicevuteType offertaMinimoRibasso(Double offertaMinimoRibasso) {
    this.offertaMinimoRibasso = offertaMinimoRibasso;
    return this;
  }

   /**
   * Offerta di minimo ribasso - nei casi in cui la scheda preveda un eform, corrisponde al campo bt-710 del TED.
   * @return offertaMinimoRibasso
  **/
  @Schema(description = "Offerta di minimo ribasso - nei casi in cui la scheda preveda un eform, corrisponde al campo bt-710 del TED.")
  public Double getOffertaMinimoRibasso() {
    return offertaMinimoRibasso;
  }

  public void setOffertaMinimoRibasso(Double offertaMinimoRibasso) {
    this.offertaMinimoRibasso = offertaMinimoRibasso;
  }

  public DatiBaseSottomissioniRicevuteType offertaMassimoRibasso(Double offertaMassimoRibasso) {
    this.offertaMassimoRibasso = offertaMassimoRibasso;
    return this;
  }

   /**
   * Offerta di massimo ribasso - nei casi in cui la scheda preveda un eform, corrisponde al campo bt-711 del TED.
   * @return offertaMassimoRibasso
  **/
  @Schema(description = "Offerta di massimo ribasso - nei casi in cui la scheda preveda un eform, corrisponde al campo bt-711 del TED.")
  public Double getOffertaMassimoRibasso() {
    return offertaMassimoRibasso;
  }

  public void setOffertaMassimoRibasso(Double offertaMassimoRibasso) {
    this.offertaMassimoRibasso = offertaMassimoRibasso;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatiBaseSottomissioniRicevuteType datiBaseSottomissioniRicevuteType = (DatiBaseSottomissioniRicevuteType) o;
    return Objects.equals(this.offertaMinimoRibasso, datiBaseSottomissioniRicevuteType.offertaMinimoRibasso) &&
        Objects.equals(this.offertaMassimoRibasso, datiBaseSottomissioniRicevuteType.offertaMassimoRibasso);
  }

  @Override
  public int hashCode() {
    return Objects.hash(offertaMinimoRibasso, offertaMassimoRibasso);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatiBaseSottomissioniRicevuteType {\n");
    
    sb.append("    offertaMinimoRibasso: ").append(toIndentedString(offertaMinimoRibasso)).append("\n");
    sb.append("    offertaMassimoRibasso: ").append(toIndentedString(offertaMassimoRibasso)).append("\n");
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
