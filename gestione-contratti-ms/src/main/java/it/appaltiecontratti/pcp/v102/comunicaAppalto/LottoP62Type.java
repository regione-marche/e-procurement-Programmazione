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
import it.appaltiecontratti.pcp.v102.comunicaAppalto.ContrattiDisposizioniParticolariEnum;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.DatiBaseAccessibilitaType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.DatiBaseCPVType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.DatiBaseContrattoType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.DatiBaseDocumentiOptionalType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.DatiBaseLottoType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.DatiBaseTerminiInvioSoloOraType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.FinanziamentoType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.IpotesiCollegamentoType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.LottoP6BaseType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.ModalitaAcquisizioneEnum;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.QuadroEconomicoType;
import it.appaltiecontratti.pcp.v102.comunicaAppalto.TipologiaLavoroEnum;
import java.util.List;
/**
 * Oggetto che riporta i dati del lotto
 */
@Schema(description = "Oggetto che riporta i dati del lotto")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-05-23T10:13:15.678079400+02:00[Europe/Berlin]")

public class LottoP62Type extends LottoP6BaseType {
  @JsonProperty("ripetizioniEConsegneComplementari")
  private Boolean ripetizioniEConsegneComplementari = null;

  @JsonProperty("quadroEconomicoStandard")
  private QuadroEconomicoType quadroEconomicoStandard = null;

  @JsonProperty("datiBase")
  private DatiBaseLottoType datiBase = null;

  public LottoP62Type ripetizioniEConsegneComplementari(Boolean ripetizioniEConsegneComplementari) {
    this.ripetizioniEConsegneComplementari = ripetizioniEConsegneComplementari;
    return this;
  }

   /**
   * L’appalto prevede ripetizioni di servizi/forniture/lavori analoghi e consegne complementari?
   * @return ripetizioniEConsegneComplementari
  **/
  @Schema(required = true, description = "L’appalto prevede ripetizioni di servizi/forniture/lavori analoghi e consegne complementari?")
  public Boolean isRipetizioniEConsegneComplementari() {
    return ripetizioniEConsegneComplementari;
  }

  public void setRipetizioniEConsegneComplementari(Boolean ripetizioniEConsegneComplementari) {
    this.ripetizioniEConsegneComplementari = ripetizioniEConsegneComplementari;
  }

  public LottoP62Type quadroEconomicoStandard(QuadroEconomicoType quadroEconomicoStandard) {
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

  public LottoP62Type datiBase(DatiBaseLottoType datiBase) {
    this.datiBase = datiBase;
    return this;
  }

   /**
   * Get datiBase
   * @return datiBase
  **/
  @Schema(required = true, description = "")
  public DatiBaseLottoType getDatiBase() {
    return datiBase;
  }

  public void setDatiBase(DatiBaseLottoType datiBase) {
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
    LottoP62Type lottoP62Type = (LottoP62Type) o;
    return Objects.equals(this.ripetizioniEConsegneComplementari, lottoP62Type.ripetizioniEConsegneComplementari) &&
        Objects.equals(this.quadroEconomicoStandard, lottoP62Type.quadroEconomicoStandard) &&
        Objects.equals(this.datiBase, lottoP62Type.datiBase) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ripetizioniEConsegneComplementari, quadroEconomicoStandard, datiBase, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LottoP62Type {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    ripetizioniEConsegneComplementari: ").append(toIndentedString(ripetizioniEConsegneComplementari)).append("\n");
    sb.append("    quadroEconomicoStandard: ").append(toIndentedString(quadroEconomicoStandard)).append("\n");
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
