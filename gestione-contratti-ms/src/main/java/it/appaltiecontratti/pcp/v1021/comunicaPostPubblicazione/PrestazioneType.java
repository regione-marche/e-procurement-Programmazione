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

package it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.DatiPersonaFisicaType;
import it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.DatiPersonaGiuridicaType;
import it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.TipoProgettazioneEnum;
import it.appaltiecontratti.pcp.v1021.comunicaPostPubblicazione.TipoSoggettoEnum;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * Dati relativi alle prestazioni progettuali
 */
@Schema(description = "Dati relativi alle prestazioni progettuali")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-25T10:09:29.128631100+02:00[Europe/Rome]")

public class PrestazioneType {
  /**
   * La progettazione è interna o esterna?
   */
  public enum ProgettazioneInternaEsternaEnum {
    INTERNA("INTERNA"),
    ESTERNA("ESTERNA");

    private String value;

    ProgettazioneInternaEsternaEnum(String value) {
      this.value = value;
    }
    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    @JsonCreator
    public static ProgettazioneInternaEsternaEnum fromValue(String input) {
      for (ProgettazioneInternaEsternaEnum b : ProgettazioneInternaEsternaEnum.values()) {
        if (b.value.equals(input)) {
          return b;
        }
      }
      return null;
    }

  }  @JsonProperty("progettazioneInternaEsterna")
  private ProgettazioneInternaEsternaEnum progettazioneInternaEsterna = null;

  @JsonProperty("tipoSoggetto")
  private TipoSoggettoEnum tipoSoggetto = null;

  @JsonProperty("datiPersonaFisica")
  private DatiPersonaFisicaType datiPersonaFisica = null;

  @JsonProperty("datiPersonaGiuridica")
  private List<DatiPersonaGiuridicaType> datiPersonaGiuridica = null;

  @JsonProperty("tipoProgettazione")
  private TipoProgettazioneEnum tipoProgettazione = null;

  @JsonProperty("cig")
  private String cig = null;

  @JsonProperty("dataAffidamentoIncarico")
  private OffsetDateTime dataAffidamentoIncarico = null;

  @JsonProperty("dataConsegna")
  private OffsetDateTime dataConsegna = null;

  public PrestazioneType progettazioneInternaEsterna(ProgettazioneInternaEsternaEnum progettazioneInternaEsterna) {
    this.progettazioneInternaEsterna = progettazioneInternaEsterna;
    return this;
  }

   /**
   * La progettazione è interna o esterna?
   * @return progettazioneInternaEsterna
  **/
  @Schema(description = "La progettazione è interna o esterna?")
  public ProgettazioneInternaEsternaEnum getProgettazioneInternaEsterna() {
    return progettazioneInternaEsterna;
  }

  public void setProgettazioneInternaEsterna(ProgettazioneInternaEsternaEnum progettazioneInternaEsterna) {
    this.progettazioneInternaEsterna = progettazioneInternaEsterna;
  }

  public PrestazioneType tipoSoggetto(TipoSoggettoEnum tipoSoggetto) {
    this.tipoSoggetto = tipoSoggetto;
    return this;
  }

   /**
   * Get tipoSoggetto
   * @return tipoSoggetto
  **/
  @Schema(description = "")
  public TipoSoggettoEnum getTipoSoggetto() {
    return tipoSoggetto;
  }

  public void setTipoSoggetto(TipoSoggettoEnum tipoSoggetto) {
    this.tipoSoggetto = tipoSoggetto;
  }

  public PrestazioneType datiPersonaFisica(DatiPersonaFisicaType datiPersonaFisica) {
    this.datiPersonaFisica = datiPersonaFisica;
    return this;
  }

   /**
   * Get datiPersonaFisica
   * @return datiPersonaFisica
  **/
  @Schema(description = "")
  public DatiPersonaFisicaType getDatiPersonaFisica() {
    return datiPersonaFisica;
  }

  public void setDatiPersonaFisica(DatiPersonaFisicaType datiPersonaFisica) {
    this.datiPersonaFisica = datiPersonaFisica;
  }

  public PrestazioneType datiPersonaGiuridica(List<DatiPersonaGiuridicaType> datiPersonaGiuridica) {
    this.datiPersonaGiuridica = datiPersonaGiuridica;
    return this;
  }

  public PrestazioneType addDatiPersonaGiuridicaItem(DatiPersonaGiuridicaType datiPersonaGiuridicaItem) {
    if (this.datiPersonaGiuridica == null) {
      this.datiPersonaGiuridica = new ArrayList<>();
    }
    this.datiPersonaGiuridica.add(datiPersonaGiuridicaItem);
    return this;
  }

   /**
   * Get datiPersonaGiuridica
   * @return datiPersonaGiuridica
  **/
  @Schema(description = "")
  public List<DatiPersonaGiuridicaType> getDatiPersonaGiuridica() {
    return datiPersonaGiuridica;
  }

  public void setDatiPersonaGiuridica(List<DatiPersonaGiuridicaType> datiPersonaGiuridica) {
    this.datiPersonaGiuridica = datiPersonaGiuridica;
  }

  public PrestazioneType tipoProgettazione(TipoProgettazioneEnum tipoProgettazione) {
    this.tipoProgettazione = tipoProgettazione;
    return this;
  }

   /**
   * Get tipoProgettazione
   * @return tipoProgettazione
  **/
  @Schema(description = "")
  public TipoProgettazioneEnum getTipoProgettazione() {
    return tipoProgettazione;
  }

  public void setTipoProgettazione(TipoProgettazioneEnum tipoProgettazione) {
    this.tipoProgettazione = tipoProgettazione;
  }

  public PrestazioneType cig(String cig) {
    this.cig = cig;
    return this;
  }

   /**
   * CIG affidamento incarico esterno di progettazione (in caso di progettista esterno)
   * @return cig
  **/
  @Schema(description = "CIG affidamento incarico esterno di progettazione (in caso di progettista esterno)")
  public String getCig() {
    return cig;
  }

  public void setCig(String cig) {
    this.cig = cig;
  }

  public PrestazioneType dataAffidamentoIncarico(OffsetDateTime dataAffidamentoIncarico) {
    this.dataAffidamentoIncarico = dataAffidamentoIncarico;
    return this;
  }

   /**
   * Data di affidamento incarico (per progettazione esterna)
   * @return dataAffidamentoIncarico
  **/
  @Schema(description = "Data di affidamento incarico (per progettazione esterna)")
  public OffsetDateTime getDataAffidamentoIncarico() {
    return dataAffidamentoIncarico;
  }

  public void setDataAffidamentoIncarico(OffsetDateTime dataAffidamentoIncarico) {
    this.dataAffidamentoIncarico = dataAffidamentoIncarico;
  }

  public PrestazioneType dataConsegna(OffsetDateTime dataConsegna) {
    this.dataConsegna = dataConsegna;
    return this;
  }

   /**
   * Data di consegna progetto (per progettazione esterna)
   * @return dataConsegna
  **/
  @Schema(description = "Data di consegna progetto (per progettazione esterna)")
  public OffsetDateTime getDataConsegna() {
    return dataConsegna;
  }

  public void setDataConsegna(OffsetDateTime dataConsegna) {
    this.dataConsegna = dataConsegna;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PrestazioneType prestazioneType = (PrestazioneType) o;
    return Objects.equals(this.progettazioneInternaEsterna, prestazioneType.progettazioneInternaEsterna) &&
        Objects.equals(this.tipoSoggetto, prestazioneType.tipoSoggetto) &&
        Objects.equals(this.datiPersonaFisica, prestazioneType.datiPersonaFisica) &&
        Objects.equals(this.datiPersonaGiuridica, prestazioneType.datiPersonaGiuridica) &&
        Objects.equals(this.tipoProgettazione, prestazioneType.tipoProgettazione) &&
        Objects.equals(this.cig, prestazioneType.cig) &&
        Objects.equals(this.dataAffidamentoIncarico, prestazioneType.dataAffidamentoIncarico) &&
        Objects.equals(this.dataConsegna, prestazioneType.dataConsegna);
  }

  @Override
  public int hashCode() {
    return Objects.hash(progettazioneInternaEsterna, tipoSoggetto, datiPersonaFisica, datiPersonaGiuridica, tipoProgettazione, cig, dataAffidamentoIncarico, dataConsegna);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PrestazioneType {\n");
    
    sb.append("    progettazioneInternaEsterna: ").append(toIndentedString(progettazioneInternaEsterna)).append("\n");
    sb.append("    tipoSoggetto: ").append(toIndentedString(tipoSoggetto)).append("\n");
    sb.append("    datiPersonaFisica: ").append(toIndentedString(datiPersonaFisica)).append("\n");
    sb.append("    datiPersonaGiuridica: ").append(toIndentedString(datiPersonaGiuridica)).append("\n");
    sb.append("    tipoProgettazione: ").append(toIndentedString(tipoProgettazione)).append("\n");
    sb.append("    cig: ").append(toIndentedString(cig)).append("\n");
    sb.append("    dataAffidamentoIncarico: ").append(toIndentedString(dataAffidamentoIncarico)).append("\n");
    sb.append("    dataConsegna: ").append(toIndentedString(dataConsegna)).append("\n");
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
