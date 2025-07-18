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

package it.appaltiecontratti.pcp.v102.gestioneUtenti;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import it.appaltiecontratti.pcp.v102.gestioneUtenti.BaseResponse;
import it.appaltiecontratti.pcp.v102.gestioneUtenti.ProfiloSAType;
/**
 * RecuperaProfiloResponse
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-05-23T10:13:29.528744400+02:00[Europe/Berlin]")

public class RecuperaProfiloResponse extends BaseResponse {
  @JsonProperty("profilo")
  private ProfiloSAType profilo = null;

  public RecuperaProfiloResponse profilo(ProfiloSAType profilo) {
    this.profilo = profilo;
    return this;
  }

   /**
   * Get profilo
   * @return profilo
  **/
  @Schema(description = "")
  public ProfiloSAType getProfilo() {
    return profilo;
  }

  public void setProfilo(ProfiloSAType profilo) {
    this.profilo = profilo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RecuperaProfiloResponse recuperaProfiloResponse = (RecuperaProfiloResponse) o;
    return Objects.equals(this.profilo, recuperaProfiloResponse.profilo) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(profilo, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecuperaProfiloResponse {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    profilo: ").append(toIndentedString(profilo)).append("\n");
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
