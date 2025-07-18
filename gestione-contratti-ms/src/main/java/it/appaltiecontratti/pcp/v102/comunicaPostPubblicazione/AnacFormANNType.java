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

package it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import it.appaltiecontratti.pcp.v102.comunicaPostPubblicazione.AnnullamentoType;
/**
 * AnacFormANNType
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-05-23T10:12:50.940473900+02:00[Europe/Berlin]")

public class AnacFormANNType {
  @JsonProperty("cig")
  private String cig = null;

  @JsonProperty("annullamento")
  private AnnullamentoType annullamento = null;

  public AnacFormANNType cig(String cig) {
    this.cig = cig;
    return this;
  }

   /**
   * codice identificativo lotto
   * @return cig
  **/
  @Schema(required = true, description = "codice identificativo lotto")
  public String getCig() {
    return cig;
  }

  public void setCig(String cig) {
    this.cig = cig;
  }

  public AnacFormANNType annullamento(AnnullamentoType annullamento) {
    this.annullamento = annullamento;
    return this;
  }

   /**
   * Get annullamento
   * @return annullamento
  **/
  @Schema(required = true, description = "")
  public AnnullamentoType getAnnullamento() {
    return annullamento;
  }

  public void setAnnullamento(AnnullamentoType annullamento) {
    this.annullamento = annullamento;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AnacFormANNType anacFormANNType = (AnacFormANNType) o;
    return Objects.equals(this.cig, anacFormANNType.cig) &&
        Objects.equals(this.annullamento, anacFormANNType.annullamento);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cig, annullamento);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AnacFormANNType {\n");
    
    sb.append("    cig: ").append(toIndentedString(cig)).append("\n");
    sb.append("    annullamento: ").append(toIndentedString(annullamento)).append("\n");
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
