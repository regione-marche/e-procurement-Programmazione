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
import it.appaltiecontratti.pcp.v102.comunicaAppalto.BaseResponse;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.CigType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.PaginazioneType;
import java.util.ArrayList;
import java.util.List;
/**
 * Oggetto generico di ritorno di una risposta con paginazione
 */
@Schema(description = "Oggetto generico di ritorno di una risposta con paginazione")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-05-23T10:13:15.678079400+02:00[Europe/Berlin]")

public class CigListaResponse extends BaseResponse {
  @JsonProperty("totRows")
  private Integer totRows = null;

  @JsonProperty("totPages")
  private Integer totPages = null;

  @JsonProperty("currentPage")
  private Integer currentPage = null;

  @JsonProperty("elementPage")
  private Integer elementPage = null;

  @JsonProperty("result")
  private List<CigType> result = null;

  public CigListaResponse totRows(Integer totRows) {
    this.totRows = totRows;
    return this;
  }

   /**
   * Numero totale di record risultante dalla ricerca
   * @return totRows
  **/
  @Schema(description = "Numero totale di record risultante dalla ricerca")
  public Integer getTotRows() {
    return totRows;
  }

  public void setTotRows(Integer totRows) {
    this.totRows = totRows;
  }

  public CigListaResponse totPages(Integer totPages) {
    this.totPages = totPages;
    return this;
  }

   /**
   * Numero totale di pagine risultanti dalla ricerca
   * @return totPages
  **/
  @Schema(description = "Numero totale di pagine risultanti dalla ricerca")
  public Integer getTotPages() {
    return totPages;
  }

  public void setTotPages(Integer totPages) {
    this.totPages = totPages;
  }

  public CigListaResponse currentPage(Integer currentPage) {
    this.currentPage = currentPage;
    return this;
  }

   /**
   * Numero di pagina corrente
   * @return currentPage
  **/
  @Schema(description = "Numero di pagina corrente")
  public Integer getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
  }

  public CigListaResponse elementPage(Integer elementPage) {
    this.elementPage = elementPage;
    return this;
  }

   /**
   * Numero di elementi per pagina
   * @return elementPage
  **/
  @Schema(description = "Numero di elementi per pagina")
  public Integer getElementPage() {
    return elementPage;
  }

  public void setElementPage(Integer elementPage) {
    this.elementPage = elementPage;
  }

  public CigListaResponse result(List<CigType> result) {
    this.result = result;
    return this;
  }

  public CigListaResponse addResultItem(CigType resultItem) {
    if (this.result == null) {
      this.result = new ArrayList<>();
    }
    this.result.add(resultItem);
    return this;
  }

   /**
   * Lista lotIdentifier-cig
   * @return result
  **/
  @Schema(description = "Lista lotIdentifier-cig")
  public List<CigType> getResult() {
    return result;
  }

  public void setResult(List<CigType> result) {
    this.result = result;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CigListaResponse cigListaResponse = (CigListaResponse) o;
    return Objects.equals(this.totRows, cigListaResponse.totRows) &&
        Objects.equals(this.totPages, cigListaResponse.totPages) &&
        Objects.equals(this.currentPage, cigListaResponse.currentPage) &&
        Objects.equals(this.elementPage, cigListaResponse.elementPage) &&
        Objects.equals(this.result, cigListaResponse.result) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totRows, totPages, currentPage, elementPage, result, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CigListaResponse {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    totRows: ").append(toIndentedString(totRows)).append("\n");
    sb.append("    totPages: ").append(toIndentedString(totPages)).append("\n");
    sb.append("    currentPage: ").append(toIndentedString(currentPage)).append("\n");
    sb.append("    elementPage: ").append(toIndentedString(elementPage)).append("\n");
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
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
