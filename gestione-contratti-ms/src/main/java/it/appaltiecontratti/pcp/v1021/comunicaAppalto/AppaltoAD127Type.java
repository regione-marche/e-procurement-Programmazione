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
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.MotivoUrgenzaEnum;
/**
 * Oggetto che riporta i dati inerenti l&#x27;affidamento diretto soprasoglia e sottosoglia con pubblicazione su TED - Avviso volontario per la trasparenza ex ante — direttiva per il settore della difesa - Articolo 64 della direttiva 2009/81/CE
 */
@Schema(description = "Oggetto che riporta i dati inerenti l'affidamento diretto soprasoglia e sottosoglia con pubblicazione su TED - Avviso volontario per la trasparenza ex ante — direttiva per il settore della difesa - Articolo 64 della direttiva 2009/81/CE")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-25T10:09:50.369647700+02:00[Europe/Rome]")

public class AppaltoAD127Type {
  @JsonProperty("codiceAppalto")
  private String codiceAppalto = null;

  @JsonProperty("motivoUrgenza")
  private MotivoUrgenzaEnum motivoUrgenza = null;

  @JsonProperty("linkDocumenti")
  private String linkDocumenti = null;

  public AppaltoAD127Type codiceAppalto(String codiceAppalto) {
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

  public AppaltoAD127Type motivoUrgenza(MotivoUrgenzaEnum motivoUrgenza) {
    this.motivoUrgenza = motivoUrgenza;
    return this;
  }

   /**
   * Get motivoUrgenza
   * @return motivoUrgenza
  **/
  @Schema(required = true, description = "")
  public MotivoUrgenzaEnum getMotivoUrgenza() {
    return motivoUrgenza;
  }

  public void setMotivoUrgenza(MotivoUrgenzaEnum motivoUrgenza) {
    this.motivoUrgenza = motivoUrgenza;
  }

  public AppaltoAD127Type linkDocumenti(String linkDocumenti) {
    this.linkDocumenti = linkDocumenti;
    return this;
  }

   /**
   * Link ai documenti relativi all’affidamento diretto in somma urgenza e protezione civile (co 10, art 140 nuovo codice)
   * @return linkDocumenti
  **/
  @Schema(description = "Link ai documenti relativi all’affidamento diretto in somma urgenza e protezione civile (co 10, art 140 nuovo codice)")
  public String getLinkDocumenti() {
    return linkDocumenti;
  }

  public void setLinkDocumenti(String linkDocumenti) {
    this.linkDocumenti = linkDocumenti;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AppaltoAD127Type appaltoAD127Type = (AppaltoAD127Type) o;
    return Objects.equals(this.codiceAppalto, appaltoAD127Type.codiceAppalto) &&
        Objects.equals(this.motivoUrgenza, appaltoAD127Type.motivoUrgenza) &&
        Objects.equals(this.linkDocumenti, appaltoAD127Type.linkDocumenti);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceAppalto, motivoUrgenza, linkDocumenti);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppaltoAD127Type {\n");
    
    sb.append("    codiceAppalto: ").append(toIndentedString(codiceAppalto)).append("\n");
    sb.append("    motivoUrgenza: ").append(toIndentedString(motivoUrgenza)).append("\n");
    sb.append("    linkDocumenti: ").append(toIndentedString(linkDocumenti)).append("\n");
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
