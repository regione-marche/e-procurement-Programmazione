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
import it.appaltiecontratti.pcp.v102.comunicaAppalto.DatiBaseAppaltoType;
/**
 * Oggetto che riporta i dati dell&#x27;appalto.
 */
@Schema(description = "Oggetto che riporta i dati dell'appalto.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-05-23T10:13:15.678079400+02:00[Europe/Berlin]")

public class AppaltoP5Type {
  @JsonProperty("codiceAppalto")
  private String codiceAppalto = null;

  @JsonProperty("cigAccordoQuadro")
  private String cigAccordoQuadro = null;

  @JsonProperty("datiBase")
  private DatiBaseAppaltoType datiBase = null;

  public AppaltoP5Type codiceAppalto(String codiceAppalto) {
    this.codiceAppalto = codiceAppalto;
    return this;
  }

   /**
   * Identificativo univoco dell&#x27;appalto definito dalla Stazione Appaltante
   * @return codiceAppalto
  **/
  @Schema(required = true, description = "Identificativo univoco dell'appalto definito dalla Stazione Appaltante")
  public String getCodiceAppalto() {
    return codiceAppalto;
  }

  public void setCodiceAppalto(String codiceAppalto) {
    this.codiceAppalto = codiceAppalto;
  }

  public AppaltoP5Type cigAccordoQuadro(String cigAccordoQuadro) {
    this.cigAccordoQuadro = cigAccordoQuadro;
    return this;
  }

   /**
   * CIG relativo all’accordo quadro/convenzione cui si aderisce
   * @return cigAccordoQuadro
  **/
  @Schema(description = "CIG relativo all’accordo quadro/convenzione cui si aderisce")
  public String getCigAccordoQuadro() {
    return cigAccordoQuadro;
  }

  public void setCigAccordoQuadro(String cigAccordoQuadro) {
    this.cigAccordoQuadro = cigAccordoQuadro;
  }

  public AppaltoP5Type datiBase(DatiBaseAppaltoType datiBase) {
    this.datiBase = datiBase;
    return this;
  }

   /**
   * Get datiBase
   * @return datiBase
  **/
  @Schema(required = true, description = "")
  public DatiBaseAppaltoType getDatiBase() {
    return datiBase;
  }

  public void setDatiBase(DatiBaseAppaltoType datiBase) {
    this.datiBase = datiBase;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AppaltoP5Type appaltoP5Type = (AppaltoP5Type) o;
    return Objects.equals(this.codiceAppalto, appaltoP5Type.codiceAppalto) &&
        Objects.equals(this.cigAccordoQuadro, appaltoP5Type.cigAccordoQuadro) &&
        Objects.equals(this.datiBase, appaltoP5Type.datiBase);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceAppalto, cigAccordoQuadro, datiBase);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppaltoP5Type {\n");
    
    sb.append("    codiceAppalto: ").append(toIndentedString(codiceAppalto)).append("\n");
    sb.append("    cigAccordoQuadro: ").append(toIndentedString(cigAccordoQuadro)).append("\n");
    sb.append("    datiBase: ").append(toIndentedString(datiBase)).append("\n");
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
