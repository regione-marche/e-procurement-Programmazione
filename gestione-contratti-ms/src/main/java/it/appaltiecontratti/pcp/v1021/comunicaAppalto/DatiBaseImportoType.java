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
/**
 * Dato in comune con il Business Group Prize delle eforms
 */
@Schema(description = "Dato in comune con il Business Group Prize delle eforms")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-25T10:09:50.369647700+02:00[Europe/Rome]")

public class DatiBaseImportoType {
  @JsonProperty("contrattiSuccessivi")
  private Boolean contrattiSuccessivi = null;

  public DatiBaseImportoType contrattiSuccessivi(Boolean contrattiSuccessivi) {
    this.contrattiSuccessivi = contrattiSuccessivi;
    return this;
  }

   /**
   * contratti successivi - nei casi in cui la scheda preveda un eform, corrisponde al campo bt-41 - Following Contract del TED
   * @return contrattiSuccessivi
  **/
  @Schema(required = true, description = "contratti successivi - nei casi in cui la scheda preveda un eform, corrisponde al campo bt-41 - Following Contract del TED")
  public Boolean isContrattiSuccessivi() {
    return contrattiSuccessivi;
  }

  public void setContrattiSuccessivi(Boolean contrattiSuccessivi) {
    this.contrattiSuccessivi = contrattiSuccessivi;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatiBaseImportoType datiBaseImportoType = (DatiBaseImportoType) o;
    return Objects.equals(this.contrattiSuccessivi, datiBaseImportoType.contrattiSuccessivi);
  }

  @Override
  public int hashCode() {
    return Objects.hash(contrattiSuccessivi);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatiBaseImportoType {\n");
    
    sb.append("    contrattiSuccessivi: ").append(toIndentedString(contrattiSuccessivi)).append("\n");
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
