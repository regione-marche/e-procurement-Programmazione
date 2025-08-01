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
import it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.AnacFormM1Type;
/**
 * Modifica contrattuale, direttiva generale. Articolo 72, paragrafo 1, della direttiva 2014/24/UE. Articolo 89, paragrafo 1, della direttiva 2014/25/UE. Articolo 141 comma 3 lettera i) decreto legislativo 36/2023. Articolo 120 comma 14 e comma 15 decreto legislativo 36/2023. Articolo 5 comma 11 e comma 12 Allegato II.14.
 */
@Schema(description = "Modifica contrattuale, direttiva generale. Articolo 72, paragrafo 1, della direttiva 2014/24/UE. Articolo 89, paragrafo 1, della direttiva 2014/25/UE. Articolo 141 comma 3 lettera i) decreto legislativo 36/2023. Articolo 120 comma 14 e comma 15 decreto legislativo 36/2023. Articolo 5 comma 11 e comma 12 Allegato II.14.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-25T10:09:29.128631100+02:00[Europe/Rome]")

public class SchedaM1Type implements OneOfSchedaPostPubblicazioneTypeBody {
  @JsonProperty("anacForm")
  private AnacFormM1Type anacForm = null;

  @JsonProperty("eform")
  private String eform = null;

  public SchedaM1Type anacForm(AnacFormM1Type anacForm) {
    this.anacForm = anacForm;
    return this;
  }

   /**
   * Get anacForm
   * @return anacForm
  **/
  @Schema(required = true, description = "")
  public AnacFormM1Type getAnacForm() {
    return anacForm;
  }

  public void setAnacForm(AnacFormM1Type anacForm) {
    this.anacForm = anacForm;
  }

  public SchedaM1Type eform(String eform) {
    this.eform = eform;
    return this;
  }

   /**
   * Get eform
   * @return eform
  **/
  @Schema(required = true, description = "")
  public String getEform() {
    return eform;
  }

  public void setEform(String eform) {
    this.eform = eform;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SchedaM1Type schedaM1Type = (SchedaM1Type) o;
    return Objects.equals(this.anacForm, schedaM1Type.anacForm) &&
        Objects.equals(this.eform, schedaM1Type.eform);
  }

  @Override
  public int hashCode() {
    return Objects.hash(anacForm, eform);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SchedaM1Type {\n");
    
    sb.append("    anacForm: ").append(toIndentedString(anacForm)).append("\n");
    sb.append("    eform: ").append(toIndentedString(eform)).append("\n");
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
