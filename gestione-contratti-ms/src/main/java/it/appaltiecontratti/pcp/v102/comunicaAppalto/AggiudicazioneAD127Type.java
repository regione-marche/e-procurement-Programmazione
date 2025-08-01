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
import it.appaltiecontratti.pcp.v102.comunicaAppalto.CategoriaEnum;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.CodIstatEnum;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.FinanziamentoType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.PartecipanteADType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.QuadroEconomicoConcessioniType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.QuadroEconomicoType;
import java.util.ArrayList;
import java.util.List;
/**
 * Oggetto che riporta i dati delle aggiudicazioni per gli affidamenti diretti.
 */
@Schema(description = "Oggetto che riporta i dati delle aggiudicazioni per gli affidamenti diretti.")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-05-23T10:13:15.678079400+02:00[Europe/Berlin]")

public class AggiudicazioneAD127Type {
  @JsonProperty("lotIdentifier")
  private String lotIdentifier = null;

  @JsonProperty("ccnl")
  private String ccnl = null;

  @JsonProperty("categoria")
  private CategoriaEnum categoria = null;

  @JsonProperty("categoriaScorporabile")
  private List<CategoriaEnum> categoriaScorporabile = null;

  @JsonProperty("codIstat")
  private CodIstatEnum codIstat = null;

  @JsonProperty("finanziamenti")
  private List<FinanziamentoType> finanziamenti = null;

  @JsonProperty("partecipanti")
  private List<PartecipanteADType> partecipanti = new ArrayList<>();

  @JsonProperty("quadroEconomicoStandard")
  private QuadroEconomicoType quadroEconomicoStandard = null;

  @JsonProperty("quadroEconomicoConcessioni")
  private QuadroEconomicoConcessioniType quadroEconomicoConcessioni = null;

  public AggiudicazioneAD127Type lotIdentifier(String lotIdentifier) {
    this.lotIdentifier = lotIdentifier;
    return this;
  }

   /**
   * Id univoco del lotto generato dalla stazione appaltante - nei casi in cui la scheda preveda un eform, corrisponde al campo bt-137 - Purpose Lot Identifier del TED
   * @return lotIdentifier
  **/
  @Schema(required = true, description = "Id univoco del lotto generato dalla stazione appaltante - nei casi in cui la scheda preveda un eform, corrisponde al campo bt-137 - Purpose Lot Identifier del TED")
  public String getLotIdentifier() {
    return lotIdentifier;
  }

  public void setLotIdentifier(String lotIdentifier) {
    this.lotIdentifier = lotIdentifier;
  }

  public AggiudicazioneAD127Type ccnl(String ccnl) {
    this.ccnl = ccnl;
    return this;
  }

   /**
   * indicare il codice CNEL o non applicabile
   * @return ccnl
  **/
  @Schema(required = true, description = "indicare il codice CNEL o non applicabile")
  public String getCcnl() {
    return ccnl;
  }

  public void setCcnl(String ccnl) {
    this.ccnl = ccnl;
  }

  public AggiudicazioneAD127Type categoria(CategoriaEnum categoria) {
    this.categoria = categoria;
    return this;
  }

   /**
   * Get categoria
   * @return categoria
  **/
  @Schema(required = true, description = "")
  public CategoriaEnum getCategoria() {
    return categoria;
  }

  public void setCategoria(CategoriaEnum categoria) {
    this.categoria = categoria;
  }

  public AggiudicazioneAD127Type categoriaScorporabile(List<CategoriaEnum> categoriaScorporabile) {
    this.categoriaScorporabile = categoriaScorporabile;
    return this;
  }

  public AggiudicazioneAD127Type addCategoriaScorporabileItem(CategoriaEnum categoriaScorporabileItem) {
    if (this.categoriaScorporabile == null) {
      this.categoriaScorporabile = new ArrayList<>();
    }
    this.categoriaScorporabile.add(categoriaScorporabileItem);
    return this;
  }

   /**
   * Get categoriaScorporabile
   * @return categoriaScorporabile
  **/
  @Schema(description = "")
  public List<CategoriaEnum> getCategoriaScorporabile() {
    return categoriaScorporabile;
  }

  public void setCategoriaScorporabile(List<CategoriaEnum> categoriaScorporabile) {
    this.categoriaScorporabile = categoriaScorporabile;
  }

  public AggiudicazioneAD127Type codIstat(CodIstatEnum codIstat) {
    this.codIstat = codIstat;
    return this;
  }

   /**
   * Get codIstat
   * @return codIstat
  **/
  @Schema(required = true, description = "")
  public CodIstatEnum getCodIstat() {
    return codIstat;
  }

  public void setCodIstat(CodIstatEnum codIstat) {
    this.codIstat = codIstat;
  }

  public AggiudicazioneAD127Type finanziamenti(List<FinanziamentoType> finanziamenti) {
    this.finanziamenti = finanziamenti;
    return this;
  }

  public AggiudicazioneAD127Type addFinanziamentiItem(FinanziamentoType finanziamentiItem) {
    if (this.finanziamenti == null) {
      this.finanziamenti = new ArrayList<>();
    }
    this.finanziamenti.add(finanziamentiItem);
    return this;
  }

   /**
   * Dati relativi ai finanziamenti
   * @return finanziamenti
  **/
  @Schema(description = "Dati relativi ai finanziamenti")
  public List<FinanziamentoType> getFinanziamenti() {
    return finanziamenti;
  }

  public void setFinanziamenti(List<FinanziamentoType> finanziamenti) {
    this.finanziamenti = finanziamenti;
  }

  public AggiudicazioneAD127Type partecipanti(List<PartecipanteADType> partecipanti) {
    this.partecipanti = partecipanti;
    return this;
  }

  public AggiudicazioneAD127Type addPartecipantiItem(PartecipanteADType partecipantiItem) {
    this.partecipanti.add(partecipantiItem);
    return this;
  }

   /**
   * Get partecipanti
   * @return partecipanti
  **/
  @Schema(required = true, description = "")
  public List<PartecipanteADType> getPartecipanti() {
    return partecipanti;
  }

  public void setPartecipanti(List<PartecipanteADType> partecipanti) {
    this.partecipanti = partecipanti;
  }

  public AggiudicazioneAD127Type quadroEconomicoStandard(QuadroEconomicoType quadroEconomicoStandard) {
    this.quadroEconomicoStandard = quadroEconomicoStandard;
    return this;
  }

   /**
   * Get quadroEconomicoStandard
   * @return quadroEconomicoStandard
  **/
  @Schema(description = "")
  public QuadroEconomicoType getQuadroEconomicoStandard() {
    return quadroEconomicoStandard;
  }

  public void setQuadroEconomicoStandard(QuadroEconomicoType quadroEconomicoStandard) {
    this.quadroEconomicoStandard = quadroEconomicoStandard;
  }

  public AggiudicazioneAD127Type quadroEconomicoConcessioni(QuadroEconomicoConcessioniType quadroEconomicoConcessioni) {
    this.quadroEconomicoConcessioni = quadroEconomicoConcessioni;
    return this;
  }

   /**
   * Get quadroEconomicoConcessioni
   * @return quadroEconomicoConcessioni
  **/
  @Schema(description = "")
  public QuadroEconomicoConcessioniType getQuadroEconomicoConcessioni() {
    return quadroEconomicoConcessioni;
  }

  public void setQuadroEconomicoConcessioni(QuadroEconomicoConcessioniType quadroEconomicoConcessioni) {
    this.quadroEconomicoConcessioni = quadroEconomicoConcessioni;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AggiudicazioneAD127Type aggiudicazioneAD127Type = (AggiudicazioneAD127Type) o;
    return Objects.equals(this.lotIdentifier, aggiudicazioneAD127Type.lotIdentifier) &&
        Objects.equals(this.ccnl, aggiudicazioneAD127Type.ccnl) &&
        Objects.equals(this.categoria, aggiudicazioneAD127Type.categoria) &&
        Objects.equals(this.categoriaScorporabile, aggiudicazioneAD127Type.categoriaScorporabile) &&
        Objects.equals(this.codIstat, aggiudicazioneAD127Type.codIstat) &&
        Objects.equals(this.finanziamenti, aggiudicazioneAD127Type.finanziamenti) &&
        Objects.equals(this.partecipanti, aggiudicazioneAD127Type.partecipanti) &&
        Objects.equals(this.quadroEconomicoStandard, aggiudicazioneAD127Type.quadroEconomicoStandard) &&
        Objects.equals(this.quadroEconomicoConcessioni, aggiudicazioneAD127Type.quadroEconomicoConcessioni);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lotIdentifier, ccnl, categoria, categoriaScorporabile, codIstat, finanziamenti, partecipanti, quadroEconomicoStandard, quadroEconomicoConcessioni);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AggiudicazioneAD127Type {\n");
    
    sb.append("    lotIdentifier: ").append(toIndentedString(lotIdentifier)).append("\n");
    sb.append("    ccnl: ").append(toIndentedString(ccnl)).append("\n");
    sb.append("    categoria: ").append(toIndentedString(categoria)).append("\n");
    sb.append("    categoriaScorporabile: ").append(toIndentedString(categoriaScorporabile)).append("\n");
    sb.append("    codIstat: ").append(toIndentedString(codIstat)).append("\n");
    sb.append("    finanziamenti: ").append(toIndentedString(finanziamenti)).append("\n");
    sb.append("    partecipanti: ").append(toIndentedString(partecipanti)).append("\n");
    sb.append("    quadroEconomicoStandard: ").append(toIndentedString(quadroEconomicoStandard)).append("\n");
    sb.append("    quadroEconomicoConcessioni: ").append(toIndentedString(quadroEconomicoConcessioni)).append("\n");
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
