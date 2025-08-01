/*
 * npa-gateway-ms API
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 1.0.2-SNAPSHOT
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package it.appaltiecontratti.pcp.v1021.npaGateway;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import it.appaltiecontratti.pcp.v1021.npaGateway.ETipologiaSchedaGroupType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * AvvisoRequestDTOMapStringObject
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-25T10:09:57.938746400+02:00[Europe/Rome]")

public class AvvisoRequestDTOMapStringObject {
  @JsonProperty("idAvviso")
  private String idAvviso = null;

  @JsonProperty("tipologiaSchedaGroupType")
  private ETipologiaSchedaGroupType tipologiaSchedaGroupType = null;

  @JsonProperty("idAppalto")
  private String idAppalto = null;

  @JsonProperty("idPianificazione")
  private String idPianificazione = null;

  @JsonProperty("scheda")
  private Map<String, Object> scheda = null;

  public AvvisoRequestDTOMapStringObject idAvviso(String idAvviso) {
    this.idAvviso = idAvviso;
    return this;
  }

   /**
   * identificativo univoco dell&#x27;Avviso generato da ANAC.
   * @return idAvviso
  **/
  @Schema(required = true, description = "identificativo univoco dell'Avviso generato da ANAC.")
  public String getIdAvviso() {
    return idAvviso;
  }

  public void setIdAvviso(String idAvviso) {
    this.idAvviso = idAvviso;
  }

  public AvvisoRequestDTOMapStringObject tipologiaSchedaGroupType(ETipologiaSchedaGroupType tipologiaSchedaGroupType) {
    this.tipologiaSchedaGroupType = tipologiaSchedaGroupType;
    return this;
  }

   /**
   * Get tipologiaSchedaGroupType
   * @return tipologiaSchedaGroupType
  **/
  @Schema(required = true, description = "")
  public ETipologiaSchedaGroupType getTipologiaSchedaGroupType() {
    return tipologiaSchedaGroupType;
  }

  public void setTipologiaSchedaGroupType(ETipologiaSchedaGroupType tipologiaSchedaGroupType) {
    this.tipologiaSchedaGroupType = tipologiaSchedaGroupType;
  }

  public AvvisoRequestDTOMapStringObject idAppalto(String idAppalto) {
    this.idAppalto = idAppalto;
    return this;
  }

   /**
   * identificativo univoco dell&#x27;Appalto generato da ANAC. Obbligatorio se idPianificazione non è stato valorizzato
   * @return idAppalto
  **/
  @Schema(description = "identificativo univoco dell'Appalto generato da ANAC. Obbligatorio se idPianificazione non è stato valorizzato")
  public String getIdAppalto() {
    return idAppalto;
  }

  public void setIdAppalto(String idAppalto) {
    this.idAppalto = idAppalto;
  }

  public AvvisoRequestDTOMapStringObject idPianificazione(String idPianificazione) {
    this.idPianificazione = idPianificazione;
    return this;
  }

   /**
   * identificativo univoco della Pianificazione definito da ANAC. Obbligatorio se idAppalto non è stato valorizzato
   * @return idPianificazione
  **/
  @Schema(description = "identificativo univoco della Pianificazione definito da ANAC. Obbligatorio se idAppalto non è stato valorizzato")
  public String getIdPianificazione() {
    return idPianificazione;
  }

  public void setIdPianificazione(String idPianificazione) {
    this.idPianificazione = idPianificazione;
  }

  public AvvisoRequestDTOMapStringObject scheda(Map<String, Object> scheda) {
    this.scheda = scheda;
    return this;
  }

  public AvvisoRequestDTOMapStringObject putSchedaItem(String key, Object schedaItem) {
    if (this.scheda == null) {
      this.scheda = new HashMap<>();
    }
    this.scheda.put(key, schedaItem);
    return this;
  }

   /**
   * Body della richiesta
   * @return scheda
  **/
  @Schema(description = "Body della richiesta")
  public Map<String, Object> getScheda() {
    return scheda;
  }

  public void setScheda(Map<String, Object> scheda) {
    this.scheda = scheda;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AvvisoRequestDTOMapStringObject avvisoRequestDTOMapStringObject = (AvvisoRequestDTOMapStringObject) o;
    return Objects.equals(this.idAvviso, avvisoRequestDTOMapStringObject.idAvviso) &&
        Objects.equals(this.tipologiaSchedaGroupType, avvisoRequestDTOMapStringObject.tipologiaSchedaGroupType) &&
        Objects.equals(this.idAppalto, avvisoRequestDTOMapStringObject.idAppalto) &&
        Objects.equals(this.idPianificazione, avvisoRequestDTOMapStringObject.idPianificazione) &&
        Objects.equals(this.scheda, avvisoRequestDTOMapStringObject.scheda);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idAvviso, tipologiaSchedaGroupType, idAppalto, idPianificazione, scheda);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AvvisoRequestDTOMapStringObject {\n");
    
    sb.append("    idAvviso: ").append(toIndentedString(idAvviso)).append("\n");
    sb.append("    tipologiaSchedaGroupType: ").append(toIndentedString(tipologiaSchedaGroupType)).append("\n");
    sb.append("    idAppalto: ").append(toIndentedString(idAppalto)).append("\n");
    sb.append("    idPianificazione: ").append(toIndentedString(idPianificazione)).append("\n");
    sb.append("    scheda: ").append(toIndentedString(scheda)).append("\n");
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
