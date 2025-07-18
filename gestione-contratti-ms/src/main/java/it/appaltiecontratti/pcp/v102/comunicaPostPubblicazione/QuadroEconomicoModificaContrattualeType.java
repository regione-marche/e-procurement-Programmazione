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
import java.math.BigDecimal;
/**
 * Oggetto che riporta tutti i dati del quadro economico standard per la modifica contrattuale
 */
@Schema(description = "Oggetto che riporta tutti i dati del quadro economico standard per la modifica contrattuale")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-05-23T10:12:50.940473900+02:00[Europe/Berlin]")

public class QuadroEconomicoModificaContrattualeType {
  @JsonProperty("impLavori")
  private Double impLavori = null;

  @JsonProperty("impServizi")
  private Double impServizi = null;

  @JsonProperty("impForniture")
  private Double impForniture = null;

  @JsonProperty("impTotaleSicurezza")
  private Double impTotaleSicurezza = null;

  @JsonProperty("impProgettazione")
  private Double impProgettazione = null;

  @JsonProperty("ulterioriSommeNoRibasso")
  private Double ulterioriSommeNoRibasso = null;

  @JsonProperty("sommeADisposizione")
  private Double sommeADisposizione = null;

  @JsonProperty("numGiorniProroga")
  private BigDecimal numGiorniProroga = null;

  public QuadroEconomicoModificaContrattualeType impLavori(Double impLavori) {
    this.impLavori = impLavori;
    return this;
  }

   /**
   * Importo contrattuale componente lavori in € (al netto dell’IVA e degli oneri di sicurezza)
   * @return impLavori
  **/
  @Schema(description = "Importo contrattuale componente lavori in € (al netto dell’IVA e degli oneri di sicurezza)")
  public Double getImpLavori() {
    return impLavori;
  }

  public void setImpLavori(Double impLavori) {
    this.impLavori = impLavori;
  }

  public QuadroEconomicoModificaContrattualeType impServizi(Double impServizi) {
    this.impServizi = impServizi;
    return this;
  }

   /**
   * Importo contrattuale componente servizi in € (al netto dell’IVA e degli oneri di sicurezza)
   * @return impServizi
  **/
  @Schema(description = "Importo contrattuale componente servizi in € (al netto dell’IVA e degli oneri di sicurezza)")
  public Double getImpServizi() {
    return impServizi;
  }

  public void setImpServizi(Double impServizi) {
    this.impServizi = impServizi;
  }

  public QuadroEconomicoModificaContrattualeType impForniture(Double impForniture) {
    this.impForniture = impForniture;
    return this;
  }

   /**
   * Importo contrattuale componente forniture in € (al netto dell’IVA e degli oneri di sicurezza)
   * @return impForniture
  **/
  @Schema(description = "Importo contrattuale componente forniture in € (al netto dell’IVA e degli oneri di sicurezza)")
  public Double getImpForniture() {
    return impForniture;
  }

  public void setImpForniture(Double impForniture) {
    this.impForniture = impForniture;
  }

  public QuadroEconomicoModificaContrattualeType impTotaleSicurezza(Double impTotaleSicurezza) {
    this.impTotaleSicurezza = impTotaleSicurezza;
    return this;
  }

   /**
   * Nuovo importo totale per l’attuazione della sicurezza (non soggetto a ribasso)
   * @return impTotaleSicurezza
  **/
  @Schema(description = "Nuovo importo totale per l’attuazione della sicurezza (non soggetto a ribasso)")
  public Double getImpTotaleSicurezza() {
    return impTotaleSicurezza;
  }

  public void setImpTotaleSicurezza(Double impTotaleSicurezza) {
    this.impTotaleSicurezza = impTotaleSicurezza;
  }

  public QuadroEconomicoModificaContrattualeType impProgettazione(Double impProgettazione) {
    this.impProgettazione = impProgettazione;
    return this;
  }

   /**
   * Importo progettazione
   * @return impProgettazione
  **/
  @Schema(description = "Importo progettazione")
  public Double getImpProgettazione() {
    return impProgettazione;
  }

  public void setImpProgettazione(Double impProgettazione) {
    this.impProgettazione = impProgettazione;
  }

  public QuadroEconomicoModificaContrattualeType ulterioriSommeNoRibasso(Double ulterioriSommeNoRibasso) {
    this.ulterioriSommeNoRibasso = ulterioriSommeNoRibasso;
    return this;
  }

   /**
   * Ulteriori somme non soggette a ribasso
   * @return ulterioriSommeNoRibasso
  **/
  @Schema(description = "Ulteriori somme non soggette a ribasso")
  public Double getUlterioriSommeNoRibasso() {
    return ulterioriSommeNoRibasso;
  }

  public void setUlterioriSommeNoRibasso(Double ulterioriSommeNoRibasso) {
    this.ulterioriSommeNoRibasso = ulterioriSommeNoRibasso;
  }

  public QuadroEconomicoModificaContrattualeType sommeADisposizione(Double sommeADisposizione) {
    this.sommeADisposizione = sommeADisposizione;
    return this;
  }

   /**
   * Nuovo importo totale somme a disposizione
   * @return sommeADisposizione
  **/
  @Schema(description = "Nuovo importo totale somme a disposizione")
  public Double getSommeADisposizione() {
    return sommeADisposizione;
  }

  public void setSommeADisposizione(Double sommeADisposizione) {
    this.sommeADisposizione = sommeADisposizione;
  }

  public QuadroEconomicoModificaContrattualeType numGiorniProroga(BigDecimal numGiorniProroga) {
    this.numGiorniProroga = numGiorniProroga;
    return this;
  }

   /**
   * Numero di giorni di proroga concessi/tempo aggiuntivo rispetto ai termini contrattuali
   * @return numGiorniProroga
  **/
  @Schema(description = "Numero di giorni di proroga concessi/tempo aggiuntivo rispetto ai termini contrattuali")
  public BigDecimal getNumGiorniProroga() {
    return numGiorniProroga;
  }

  public void setNumGiorniProroga(BigDecimal numGiorniProroga) {
    this.numGiorniProroga = numGiorniProroga;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QuadroEconomicoModificaContrattualeType quadroEconomicoModificaContrattualeType = (QuadroEconomicoModificaContrattualeType) o;
    return Objects.equals(this.impLavori, quadroEconomicoModificaContrattualeType.impLavori) &&
        Objects.equals(this.impServizi, quadroEconomicoModificaContrattualeType.impServizi) &&
        Objects.equals(this.impForniture, quadroEconomicoModificaContrattualeType.impForniture) &&
        Objects.equals(this.impTotaleSicurezza, quadroEconomicoModificaContrattualeType.impTotaleSicurezza) &&
        Objects.equals(this.impProgettazione, quadroEconomicoModificaContrattualeType.impProgettazione) &&
        Objects.equals(this.ulterioriSommeNoRibasso, quadroEconomicoModificaContrattualeType.ulterioriSommeNoRibasso) &&
        Objects.equals(this.sommeADisposizione, quadroEconomicoModificaContrattualeType.sommeADisposizione) &&
        Objects.equals(this.numGiorniProroga, quadroEconomicoModificaContrattualeType.numGiorniProroga);
  }

  @Override
  public int hashCode() {
    return Objects.hash(impLavori, impServizi, impForniture, impTotaleSicurezza, impProgettazione, ulterioriSommeNoRibasso, sommeADisposizione, numGiorniProroga);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QuadroEconomicoModificaContrattualeType {\n");
    
    sb.append("    impLavori: ").append(toIndentedString(impLavori)).append("\n");
    sb.append("    impServizi: ").append(toIndentedString(impServizi)).append("\n");
    sb.append("    impForniture: ").append(toIndentedString(impForniture)).append("\n");
    sb.append("    impTotaleSicurezza: ").append(toIndentedString(impTotaleSicurezza)).append("\n");
    sb.append("    impProgettazione: ").append(toIndentedString(impProgettazione)).append("\n");
    sb.append("    ulterioriSommeNoRibasso: ").append(toIndentedString(ulterioriSommeNoRibasso)).append("\n");
    sb.append("    sommeADisposizione: ").append(toIndentedString(sommeADisposizione)).append("\n");
    sb.append("    numGiorniProroga: ").append(toIndentedString(numGiorniProroga)).append("\n");
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
