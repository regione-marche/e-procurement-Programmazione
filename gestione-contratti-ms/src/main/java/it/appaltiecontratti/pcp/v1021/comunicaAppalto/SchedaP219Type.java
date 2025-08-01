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
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.AnacFormP219Type;
/**
 * Bando di concessione - direttiva sulle concessioni, regime ordinario, PPP. Articolo 31, paragrafo 1, della direttiva 2014/23/UE. Articolo 182 comma 11 primo periodo e comma 12 decreto legislativo 36/2023.
 */
@Schema(description = "Bando di concessione - direttiva sulle concessioni, regime ordinario, PPP. Articolo 31, paragrafo 1, della direttiva 2014/23/UE. Articolo 182 comma 11 primo periodo e comma 12 decreto legislativo 36/2023.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-25T10:09:50.369647700+02:00[Europe/Rome]")

public class SchedaP219Type implements OneOfSchedaComunicaAppaltoTypeBody {
  @JsonProperty("anacForm")
  private AnacFormP219Type anacForm = null;

  @JsonProperty("espd")
  private String espd = null;

  public SchedaP219Type anacForm(AnacFormP219Type anacForm) {
    this.anacForm = anacForm;
    return this;
  }

   /**
   * Get anacForm
   * @return anacForm
  **/
  @Schema(required = true, description = "")
  public AnacFormP219Type getAnacForm() {
    return anacForm;
  }

  public void setAnacForm(AnacFormP219Type anacForm) {
    this.anacForm = anacForm;
  }

  public SchedaP219Type espd(String espd) {
    this.espd = espd;
    return this;
  }

   /**
   * Get espd
   * @return espd
  **/
  @Schema(required = true, description = "")
  public String getEspd() {
    return espd;
  }

  public void setEspd(String espd) {
    this.espd = espd;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchedaP219Type schedaP219Type = (SchedaP219Type) o;
    return Objects.equals(this.anacForm, schedaP219Type.anacForm) &&
        Objects.equals(this.espd, schedaP219Type.espd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(anacForm, espd);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchedaP219Type {\n");
    
    sb.append("    anacForm: ").append(toIndentedString(anacForm)).append("\n");
    sb.append("    espd: ").append(toIndentedString(espd)).append("\n");
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
