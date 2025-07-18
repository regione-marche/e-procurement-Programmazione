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
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.AnacFormBaseType;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.AppaltoP4BaseType;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.LottoP4BaseType;
import it.appaltiecontratti.pcp.v1021.comunicaAppalto.StazioneAppaltanteType;
import java.util.ArrayList;
import java.util.List;
/**
 * Contiene i dati per la scheda di Contratti di parternariato sociale
 */
@Schema(description = "Contiene i dati per la scheda di Contratti di parternariato sociale")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-25T10:09:50.369647700+02:00[Europe/Rome]")

public class AnacFormP46Type extends AnacFormBaseType {
  @JsonProperty("appalto")
  private AppaltoP4BaseType appalto = null;

  @JsonProperty("lotti")
  private List<LottoP4BaseType> lotti = new ArrayList<>();

  public AnacFormP46Type appalto(AppaltoP4BaseType appalto) {
    this.appalto = appalto;
    return this;
  }

   /**
   * Get appalto
   * @return appalto
  **/
  @Schema(required = true, description = "")
  public AppaltoP4BaseType getAppalto() {
    return appalto;
  }

  public void setAppalto(AppaltoP4BaseType appalto) {
    this.appalto = appalto;
  }

  public AnacFormP46Type lotti(List<LottoP4BaseType> lotti) {
    this.lotti = lotti;
    return this;
  }

  public AnacFormP46Type addLottiItem(LottoP4BaseType lottiItem) {
    this.lotti.add(lottiItem);
    return this;
  }

   /**
   * Dati relativi ai lotti
   * @return lotti
  **/
  @Schema(required = true, description = "Dati relativi ai lotti")
  public List<LottoP4BaseType> getLotti() {
    return lotti;
  }

  public void setLotti(List<LottoP4BaseType> lotti) {
    this.lotti = lotti;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AnacFormP46Type anacFormP46Type = (AnacFormP46Type) o;
    return Objects.equals(this.appalto, anacFormP46Type.appalto) &&
        Objects.equals(this.lotti, anacFormP46Type.lotti) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appalto, lotti, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AnacFormP46Type {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    appalto: ").append(toIndentedString(appalto)).append("\n");
    sb.append("    lotti: ").append(toIndentedString(lotti)).append("\n");
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
