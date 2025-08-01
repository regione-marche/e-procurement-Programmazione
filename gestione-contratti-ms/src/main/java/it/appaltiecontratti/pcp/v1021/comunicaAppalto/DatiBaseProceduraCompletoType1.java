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
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.MultilinguaType;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.TipoProceduraEnum;
/**
 * Dato in comune con il Business Group Procedure delle eforms
 */
@Schema(description = "Dato in comune con il Business Group Procedure delle eforms")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-25T10:09:50.369647700+02:00[Europe/Rome]")

public class DatiBaseProceduraCompletoType1 {
  @JsonProperty("tipoProcedura")
  private TipoProceduraEnum tipoProcedura = null;

  @JsonProperty("proceduraAccelerata")
  private Boolean proceduraAccelerata = null;

  @JsonProperty("giustificazioneProceduraAccelerata")
  private String giustificazioneProceduraAccelerata = null;

  @JsonProperty("giustificazioneProceduraAccelerataMl")
  private MultilinguaType giustificazioneProceduraAccelerataMl = null;

  public DatiBaseProceduraCompletoType1 tipoProcedura(TipoProceduraEnum tipoProcedura) {
    this.tipoProcedura = tipoProcedura;
    return this;
  }

   /**
   * Get tipoProcedura
   * @return tipoProcedura
  **/
  @Schema(required = true, description = "")
  public TipoProceduraEnum getTipoProcedura() {
    return tipoProcedura;
  }

  public void setTipoProcedura(TipoProceduraEnum tipoProcedura) {
    this.tipoProcedura = tipoProcedura;
  }

  public DatiBaseProceduraCompletoType1 proceduraAccelerata(Boolean proceduraAccelerata) {
    this.proceduraAccelerata = proceduraAccelerata;
    return this;
  }

   /**
   * E&#x27; stata utilizzata la procedura accelerata per ragioni di urgenza? - nei casi in cui la scheda preveda un eform, corrisponde al campo bt-106 - Procedure Accelerated del TED
   * @return proceduraAccelerata
  **/
  @Schema(required = true, description = "E' stata utilizzata la procedura accelerata per ragioni di urgenza? - nei casi in cui la scheda preveda un eform, corrisponde al campo bt-106 - Procedure Accelerated del TED")
  public Boolean isProceduraAccelerata() {
    return proceduraAccelerata;
  }

  public void setProceduraAccelerata(Boolean proceduraAccelerata) {
    this.proceduraAccelerata = proceduraAccelerata;
  }

  public DatiBaseProceduraCompletoType1 giustificazioneProceduraAccelerata(String giustificazioneProceduraAccelerata) {
    this.giustificazioneProceduraAccelerata = giustificazioneProceduraAccelerata;
    return this;
  }

   /**
   * Giustificazione procedura accelerata
   * @return giustificazioneProceduraAccelerata
  **/
  @Schema(description = "Giustificazione procedura accelerata")
  public String getGiustificazioneProceduraAccelerata() {
    return giustificazioneProceduraAccelerata;
  }

  public void setGiustificazioneProceduraAccelerata(String giustificazioneProceduraAccelerata) {
    this.giustificazioneProceduraAccelerata = giustificazioneProceduraAccelerata;
  }

  public DatiBaseProceduraCompletoType1 giustificazioneProceduraAccelerataMl(MultilinguaType giustificazioneProceduraAccelerataMl) {
    this.giustificazioneProceduraAccelerataMl = giustificazioneProceduraAccelerataMl;
    return this;
  }

   /**
   * Get giustificazioneProceduraAccelerataMl
   * @return giustificazioneProceduraAccelerataMl
  **/
  @Schema(description = "")
  public MultilinguaType getGiustificazioneProceduraAccelerataMl() {
    return giustificazioneProceduraAccelerataMl;
  }

  public void setGiustificazioneProceduraAccelerataMl(MultilinguaType giustificazioneProceduraAccelerataMl) {
    this.giustificazioneProceduraAccelerataMl = giustificazioneProceduraAccelerataMl;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatiBaseProceduraCompletoType1 datiBaseProceduraCompletoType1 = (DatiBaseProceduraCompletoType1) o;
    return Objects.equals(this.tipoProcedura, datiBaseProceduraCompletoType1.tipoProcedura) &&
        Objects.equals(this.proceduraAccelerata, datiBaseProceduraCompletoType1.proceduraAccelerata) &&
        Objects.equals(this.giustificazioneProceduraAccelerata, datiBaseProceduraCompletoType1.giustificazioneProceduraAccelerata) &&
        Objects.equals(this.giustificazioneProceduraAccelerataMl, datiBaseProceduraCompletoType1.giustificazioneProceduraAccelerataMl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tipoProcedura, proceduraAccelerata, giustificazioneProceduraAccelerata, giustificazioneProceduraAccelerataMl);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatiBaseProceduraCompletoType1 {\n");
    
    sb.append("    tipoProcedura: ").append(toIndentedString(tipoProcedura)).append("\n");
    sb.append("    proceduraAccelerata: ").append(toIndentedString(proceduraAccelerata)).append("\n");
    sb.append("    giustificazioneProceduraAccelerata: ").append(toIndentedString(giustificazioneProceduraAccelerata)).append("\n");
    sb.append("    giustificazioneProceduraAccelerataMl: ").append(toIndentedString(giustificazioneProceduraAccelerataMl)).append("\n");
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
