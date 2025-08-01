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

package it.appaltiecontratti.pcp.v102.comunicaAppalto;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.AppaltoP12Type;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.DatiBaseStrumentiProceduraType1;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.MotivoUrgenzaEnum;
/**
 * AppaltoP212Type
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-05-23T10:13:15.678079400+02:00[Europe/Berlin]")

public class AppaltoP212Type extends AppaltoP12Type {
  @JsonProperty("datiBase")
  private AllOfAppaltoP212TypeDatiBase datiBase = null;

  @JsonProperty("datiBaseProcedura")
  private AllOfAppaltoP212TypeDatiBaseProcedura datiBaseProcedura = null;

  @JsonProperty("datiBaseStrumentiProcedura")
  private DatiBaseStrumentiProceduraType1 datiBaseStrumentiProcedura = null;

  public AppaltoP212Type datiBase(AllOfAppaltoP212TypeDatiBase datiBase) {
    this.datiBase = datiBase;
    return this;
  }

   /**
   * Get datiBase
   * @return datiBase
  **/
  @Schema(required = true, description = "")
  public AllOfAppaltoP212TypeDatiBase getDatiBase() {
    return datiBase;
  }

  public void setDatiBase(AllOfAppaltoP212TypeDatiBase datiBase) {
    this.datiBase = datiBase;
  }

  public AppaltoP212Type datiBaseProcedura(AllOfAppaltoP212TypeDatiBaseProcedura datiBaseProcedura) {
    this.datiBaseProcedura = datiBaseProcedura;
    return this;
  }

   /**
   * Get datiBaseProcedura
   * @return datiBaseProcedura
  **/
  @Schema(required = true, description = "")
  public AllOfAppaltoP212TypeDatiBaseProcedura getDatiBaseProcedura() {
    return datiBaseProcedura;
  }

  public void setDatiBaseProcedura(AllOfAppaltoP212TypeDatiBaseProcedura datiBaseProcedura) {
    this.datiBaseProcedura = datiBaseProcedura;
  }

  public AppaltoP212Type datiBaseStrumentiProcedura(DatiBaseStrumentiProceduraType1 datiBaseStrumentiProcedura) {
    this.datiBaseStrumentiProcedura = datiBaseStrumentiProcedura;
    return this;
  }

   /**
   * Get datiBaseStrumentiProcedura
   * @return datiBaseStrumentiProcedura
  **/
  @Schema(description = "")
  public DatiBaseStrumentiProceduraType1 getDatiBaseStrumentiProcedura() {
    return datiBaseStrumentiProcedura;
  }

  public void setDatiBaseStrumentiProcedura(DatiBaseStrumentiProceduraType1 datiBaseStrumentiProcedura) {
    this.datiBaseStrumentiProcedura = datiBaseStrumentiProcedura;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AppaltoP212Type appaltoP212Type = (AppaltoP212Type) o;
    return Objects.equals(this.datiBase, appaltoP212Type.datiBase) &&
        Objects.equals(this.datiBaseProcedura, appaltoP212Type.datiBaseProcedura) &&
        Objects.equals(this.datiBaseStrumentiProcedura, appaltoP212Type.datiBaseStrumentiProcedura) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(datiBase, datiBaseProcedura, datiBaseStrumentiProcedura, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppaltoP212Type {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    datiBase: ").append(toIndentedString(datiBase)).append("\n");
    sb.append("    datiBaseProcedura: ").append(toIndentedString(datiBaseProcedura)).append("\n");
    sb.append("    datiBaseStrumentiProcedura: ").append(toIndentedString(datiBaseStrumentiProcedura)).append("\n");
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
