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
import it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.CollaudoType;
import java.util.UUID;
/**
 * Contiene tutti i dati di collaudo
 */
@Schema(description = "Contiene tutti i dati di collaudo")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-25T10:09:29.128631100+02:00[Europe/Rome]")

public class AnacFormCL1Type {
  @JsonProperty("idContratto")
  private UUID idContratto = null;

  @JsonProperty("collaudo")
  private CollaudoType collaudo = null;

  public AnacFormCL1Type idContratto(UUID idContratto) {
    this.idContratto = idContratto;
    return this;
  }

   /**
   * identificativo del contratto restituito in response alla scheda di Sottoscrizione del contratto.
   * @return idContratto
  **/
  @Schema(example = "f81d4fae-7dec-11d0-a765-00a0c91e6bf6", required = true, description = "identificativo del contratto restituito in response alla scheda di Sottoscrizione del contratto.")
  public UUID getIdContratto() {
    return idContratto;
  }

  public void setIdContratto(UUID idContratto) {
    this.idContratto = idContratto;
  }

  public AnacFormCL1Type collaudo(CollaudoType collaudo) {
    this.collaudo = collaudo;
    return this;
  }

   /**
   * Get collaudo
   * @return collaudo
  **/
  @Schema(required = true, description = "")
  public CollaudoType getCollaudo() {
    return collaudo;
  }

  public void setCollaudo(CollaudoType collaudo) {
    this.collaudo = collaudo;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AnacFormCL1Type anacFormCL1Type = (AnacFormCL1Type) o;
    return Objects.equals(this.idContratto, anacFormCL1Type.idContratto) &&
        Objects.equals(this.collaudo, anacFormCL1Type.collaudo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idContratto, collaudo);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AnacFormCL1Type {\n");
    
    sb.append("    idContratto: ").append(toIndentedString(idContratto)).append("\n");
    sb.append("    collaudo: ").append(toIndentedString(collaudo)).append("\n");
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
