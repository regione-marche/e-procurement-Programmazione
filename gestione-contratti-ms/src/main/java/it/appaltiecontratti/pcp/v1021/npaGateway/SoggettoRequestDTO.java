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
/**
 * SoggettoRequestDTO
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-25T10:09:57.938746400+02:00[Europe/Rome]")

public class SoggettoRequestDTO {
  @JsonProperty("codiceFiscale")
  private String codiceFiscale = null;

  @JsonProperty("idAppalto")
  private String idAppalto = null;

  @JsonProperty("cig")
  private String cig = null;

  @JsonProperty("ruolo")
  private String ruolo = null;

  @JsonProperty("servizio")
  private String servizio = null;

  @JsonProperty("operazione")
  private String operazione = null;

  @JsonProperty("dataInizio")
  private String dataInizio = null;

  @JsonProperty("dataFine")
  private String dataFine = null;

  public SoggettoRequestDTO codiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
    return this;
  }

   /**
   * codice fiscale del soggetto.
   * @return codiceFiscale
  **/
  @Schema(required = true, description = "codice fiscale del soggetto.")
  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public SoggettoRequestDTO idAppalto(String idAppalto) {
    this.idAppalto = idAppalto;
    return this;
  }

   /**
   * Identificativo univoco dell&#x27;Appalto definito da ANAC.
   * @return idAppalto
  **/
  @Schema(required = true, description = "Identificativo univoco dell'Appalto definito da ANAC.")
  public String getIdAppalto() {
    return idAppalto;
  }

  public void setIdAppalto(String idAppalto) {
    this.idAppalto = idAppalto;
  }

  public SoggettoRequestDTO cig(String cig) {
    this.cig = cig;
    return this;
  }

   /**
   * Non gestito nella prima release - Identificativo univoco Gara-Lotto generato da ANAC.
   * @return cig
  **/
  @Schema(description = "Non gestito nella prima release - Identificativo univoco Gara-Lotto generato da ANAC.")
  public String getCig() {
    return cig;
  }

  public void setCig(String cig) {
    this.cig = cig;
  }

  public SoggettoRequestDTO ruolo(String ruolo) {
    this.ruolo = ruolo;
    return this;
  }

   /**
   * Tipo di ruolo di un soggetto - fare riferimento ai valori contenuti nel file tipoRuolo.json
   * @return ruolo
  **/
  @Schema(required = true, description = "Tipo di ruolo di un soggetto - fare riferimento ai valori contenuti nel file tipoRuolo.json")
  public String getRuolo() {
    return ruolo;
  }

  public void setRuolo(String ruolo) {
    this.ruolo = ruolo;
  }

  public SoggettoRequestDTO servizio(String servizio) {
    this.servizio = servizio;
    return this;
  }

   /**
   * Non gestito nella prima release - Tipologia di Servizi delegabili ad un Soggetto - fare riferimento ai valori contenuti nel file tipoServizioSoggetto.json
   * @return servizio
  **/
  @Schema(description = "Non gestito nella prima release - Tipologia di Servizi delegabili ad un Soggetto - fare riferimento ai valori contenuti nel file tipoServizioSoggetto.json")
  public String getServizio() {
    return servizio;
  }

  public void setServizio(String servizio) {
    this.servizio = servizio;
  }

  public SoggettoRequestDTO operazione(String operazione) {
    this.operazione = operazione;
    return this;
  }

   /**
   * Non gestito nella prima release - Tipologia di Operazioni delegabili ad un Soggetto - fare riferimento ai valori contenuti nel file tipoOperazioneSoggetto.json
   * @return operazione
  **/
  @Schema(description = "Non gestito nella prima release - Tipologia di Operazioni delegabili ad un Soggetto - fare riferimento ai valori contenuti nel file tipoOperazioneSoggetto.json")
  public String getOperazione() {
    return operazione;
  }

  public void setOperazione(String operazione) {
    this.operazione = operazione;
  }

  public SoggettoRequestDTO dataInizio(String dataInizio) {
    this.dataInizio = dataInizio;
    return this;
  }

   /**
   * Data di inizio (A date-time specified by ISO 8601 as profiled by RFC 3339)
   * @return dataInizio
  **/
  @Schema(example = "2022-01-23T12:02:05Z", required = true, description = "Data di inizio (A date-time specified by ISO 8601 as profiled by RFC 3339)")
  public String getDataInizio() {
    return dataInizio;
  }

  public void setDataInizio(String dataInizio) {
    this.dataInizio = dataInizio;
  }

  public SoggettoRequestDTO dataFine(String dataFine) {
    this.dataFine = dataFine;
    return this;
  }

   /**
   * Data di fine (A date-time specified by ISO 8601 as profiled by RFC 3339). Se il valore non è indicato la delega è a tempo indeterminato.
   * @return dataFine
  **/
  @Schema(example = "2022-01-23T12:02:05Z", description = "Data di fine (A date-time specified by ISO 8601 as profiled by RFC 3339). Se il valore non è indicato la delega è a tempo indeterminato.")
  public String getDataFine() {
    return dataFine;
  }

  public void setDataFine(String dataFine) {
    this.dataFine = dataFine;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SoggettoRequestDTO soggettoRequestDTO = (SoggettoRequestDTO) o;
    return Objects.equals(this.codiceFiscale, soggettoRequestDTO.codiceFiscale) &&
        Objects.equals(this.idAppalto, soggettoRequestDTO.idAppalto) &&
        Objects.equals(this.cig, soggettoRequestDTO.cig) &&
        Objects.equals(this.ruolo, soggettoRequestDTO.ruolo) &&
        Objects.equals(this.servizio, soggettoRequestDTO.servizio) &&
        Objects.equals(this.operazione, soggettoRequestDTO.operazione) &&
        Objects.equals(this.dataInizio, soggettoRequestDTO.dataInizio) &&
        Objects.equals(this.dataFine, soggettoRequestDTO.dataFine);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceFiscale, idAppalto, cig, ruolo, servizio, operazione, dataInizio, dataFine);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SoggettoRequestDTO {\n");
    
    sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
    sb.append("    idAppalto: ").append(toIndentedString(idAppalto)).append("\n");
    sb.append("    cig: ").append(toIndentedString(cig)).append("\n");
    sb.append("    ruolo: ").append(toIndentedString(ruolo)).append("\n");
    sb.append("    servizio: ").append(toIndentedString(servizio)).append("\n");
    sb.append("    operazione: ").append(toIndentedString(operazione)).append("\n");
    sb.append("    dataInizio: ").append(toIndentedString(dataInizio)).append("\n");
    sb.append("    dataFine: ").append(toIndentedString(dataFine)).append("\n");
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
