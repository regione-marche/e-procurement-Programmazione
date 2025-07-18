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
import java.time.OffsetDateTime;
/**
 * Oggetto che riporta i dati di inizio prestazione
 */
@Schema(description = "Oggetto che riporta i dati di inizio prestazione")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-25T10:09:29.128631100+02:00[Europe/Rome]")

public class DatiInizioType {
  @JsonProperty("dataDisposizioneInizio")
  private OffsetDateTime dataDisposizioneInizio = null;

  @JsonProperty("dataApprovazione")
  private OffsetDateTime dataApprovazione = null;

  @JsonProperty("consegnaFrazionata")
  private Boolean consegnaFrazionata = null;

  @JsonProperty("avvioPerFasi")
  private Boolean avvioPerFasi = null;

  @JsonProperty("dataVerbalePrimaConsegna")
  private OffsetDateTime dataVerbalePrimaConsegna = null;

  @JsonProperty("dataAvvioPrimaFase")
  private OffsetDateTime dataAvvioPrimaFase = null;

  @JsonProperty("dataVerbaleConsegnaDefinitiva")
  private OffsetDateTime dataVerbaleConsegnaDefinitiva = null;

  @JsonProperty("consegnaSottoRiserva")
  private Boolean consegnaSottoRiserva = null;

  @JsonProperty("dataEffettivoInizio")
  private OffsetDateTime dataEffettivoInizio = null;

  @JsonProperty("dataFinePrevista")
  private OffsetDateTime dataFinePrevista = null;

  public DatiInizioType dataDisposizioneInizio(OffsetDateTime dataDisposizioneInizio) {
    this.dataDisposizioneInizio = dataDisposizioneInizio;
    return this;
  }

   /**
   * Data disposizione dell’inizio della prog. Esecutiva
   * @return dataDisposizioneInizio
  **/
  @Schema(description = "Data disposizione dell’inizio della prog. Esecutiva")
  public OffsetDateTime getDataDisposizioneInizio() {
    return dataDisposizioneInizio;
  }

  public void setDataDisposizioneInizio(OffsetDateTime dataDisposizioneInizio) {
    this.dataDisposizioneInizio = dataDisposizioneInizio;
  }

  public DatiInizioType dataApprovazione(OffsetDateTime dataApprovazione) {
    this.dataApprovazione = dataApprovazione;
    return this;
  }

   /**
   * Data approvazione del  progetto Esecutivo
   * @return dataApprovazione
  **/
  @Schema(description = "Data approvazione del  progetto Esecutivo")
  public OffsetDateTime getDataApprovazione() {
    return dataApprovazione;
  }

  public void setDataApprovazione(OffsetDateTime dataApprovazione) {
    this.dataApprovazione = dataApprovazione;
  }

  public DatiInizioType consegnaFrazionata(Boolean consegnaFrazionata) {
    this.consegnaFrazionata = consegnaFrazionata;
    return this;
  }

   /**
   * Consegna frazionata
   * @return consegnaFrazionata
  **/
  @Schema(description = "Consegna frazionata")
  public Boolean isConsegnaFrazionata() {
    return consegnaFrazionata;
  }

  public void setConsegnaFrazionata(Boolean consegnaFrazionata) {
    this.consegnaFrazionata = consegnaFrazionata;
  }

  public DatiInizioType avvioPerFasi(Boolean avvioPerFasi) {
    this.avvioPerFasi = avvioPerFasi;
    return this;
  }

   /**
   * L&#x27;avvio dell&#x27;esecuzione del contratto e&#x27; per fasi
   * @return avvioPerFasi
  **/
  @Schema(description = "L'avvio dell'esecuzione del contratto e' per fasi")
  public Boolean isAvvioPerFasi() {
    return avvioPerFasi;
  }

  public void setAvvioPerFasi(Boolean avvioPerFasi) {
    this.avvioPerFasi = avvioPerFasi;
  }

  public DatiInizioType dataVerbalePrimaConsegna(OffsetDateTime dataVerbalePrimaConsegna) {
    this.dataVerbalePrimaConsegna = dataVerbalePrimaConsegna;
    return this;
  }

   /**
   * Data verbale prima consegna lavori
   * @return dataVerbalePrimaConsegna
  **/
  @Schema(description = "Data verbale prima consegna lavori")
  public OffsetDateTime getDataVerbalePrimaConsegna() {
    return dataVerbalePrimaConsegna;
  }

  public void setDataVerbalePrimaConsegna(OffsetDateTime dataVerbalePrimaConsegna) {
    this.dataVerbalePrimaConsegna = dataVerbalePrimaConsegna;
  }

  public DatiInizioType dataAvvioPrimaFase(OffsetDateTime dataAvvioPrimaFase) {
    this.dataAvvioPrimaFase = dataAvvioPrimaFase;
    return this;
  }

   /**
   * Data di avvio della prima fase dell&#x27;esecuzione del contratto
   * @return dataAvvioPrimaFase
  **/
  @Schema(description = "Data di avvio della prima fase dell'esecuzione del contratto")
  public OffsetDateTime getDataAvvioPrimaFase() {
    return dataAvvioPrimaFase;
  }

  public void setDataAvvioPrimaFase(OffsetDateTime dataAvvioPrimaFase) {
    this.dataAvvioPrimaFase = dataAvvioPrimaFase;
  }

  public DatiInizioType dataVerbaleConsegnaDefinitiva(OffsetDateTime dataVerbaleConsegnaDefinitiva) {
    this.dataVerbaleConsegnaDefinitiva = dataVerbaleConsegnaDefinitiva;
    return this;
  }

   /**
   * Data verbale consegna definitiva
   * @return dataVerbaleConsegnaDefinitiva
  **/
  @Schema(description = "Data verbale consegna definitiva")
  public OffsetDateTime getDataVerbaleConsegnaDefinitiva() {
    return dataVerbaleConsegnaDefinitiva;
  }

  public void setDataVerbaleConsegnaDefinitiva(OffsetDateTime dataVerbaleConsegnaDefinitiva) {
    this.dataVerbaleConsegnaDefinitiva = dataVerbaleConsegnaDefinitiva;
  }

  public DatiInizioType consegnaSottoRiserva(Boolean consegnaSottoRiserva) {
    this.consegnaSottoRiserva = consegnaSottoRiserva;
    return this;
  }

   /**
   * Consegna sotto riserva di legge?
   * @return consegnaSottoRiserva
  **/
  @Schema(required = true, description = "Consegna sotto riserva di legge?")
  public Boolean isConsegnaSottoRiserva() {
    return consegnaSottoRiserva;
  }

  public void setConsegnaSottoRiserva(Boolean consegnaSottoRiserva) {
    this.consegnaSottoRiserva = consegnaSottoRiserva;
  }

  public DatiInizioType dataEffettivoInizio(OffsetDateTime dataEffettivoInizio) {
    this.dataEffettivoInizio = dataEffettivoInizio;
    return this;
  }

   /**
   * Data di effettivo inizio lavori/servizi/forniture (consegna completa/avvio di tutte le prestazioni del contratto)
   * @return dataEffettivoInizio
  **/
  @Schema(required = true, description = "Data di effettivo inizio lavori/servizi/forniture (consegna completa/avvio di tutte le prestazioni del contratto)")
  public OffsetDateTime getDataEffettivoInizio() {
    return dataEffettivoInizio;
  }

  public void setDataEffettivoInizio(OffsetDateTime dataEffettivoInizio) {
    this.dataEffettivoInizio = dataEffettivoInizio;
  }

  public DatiInizioType dataFinePrevista(OffsetDateTime dataFinePrevista) {
    this.dataFinePrevista = dataFinePrevista;
    return this;
  }

   /**
   * Data fine prevista per dare ultimazione ai lavori/servizi/forniture
   * @return dataFinePrevista
  **/
  @Schema(required = true, description = "Data fine prevista per dare ultimazione ai lavori/servizi/forniture")
  public OffsetDateTime getDataFinePrevista() {
    return dataFinePrevista;
  }

  public void setDataFinePrevista(OffsetDateTime dataFinePrevista) {
    this.dataFinePrevista = dataFinePrevista;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatiInizioType datiInizioType = (DatiInizioType) o;
    return Objects.equals(this.dataDisposizioneInizio, datiInizioType.dataDisposizioneInizio) &&
        Objects.equals(this.dataApprovazione, datiInizioType.dataApprovazione) &&
        Objects.equals(this.consegnaFrazionata, datiInizioType.consegnaFrazionata) &&
        Objects.equals(this.avvioPerFasi, datiInizioType.avvioPerFasi) &&
        Objects.equals(this.dataVerbalePrimaConsegna, datiInizioType.dataVerbalePrimaConsegna) &&
        Objects.equals(this.dataAvvioPrimaFase, datiInizioType.dataAvvioPrimaFase) &&
        Objects.equals(this.dataVerbaleConsegnaDefinitiva, datiInizioType.dataVerbaleConsegnaDefinitiva) &&
        Objects.equals(this.consegnaSottoRiserva, datiInizioType.consegnaSottoRiserva) &&
        Objects.equals(this.dataEffettivoInizio, datiInizioType.dataEffettivoInizio) &&
        Objects.equals(this.dataFinePrevista, datiInizioType.dataFinePrevista);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataDisposizioneInizio, dataApprovazione, consegnaFrazionata, avvioPerFasi, dataVerbalePrimaConsegna, dataAvvioPrimaFase, dataVerbaleConsegnaDefinitiva, consegnaSottoRiserva, dataEffettivoInizio, dataFinePrevista);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatiInizioType {\n");
    
    sb.append("    dataDisposizioneInizio: ").append(toIndentedString(dataDisposizioneInizio)).append("\n");
    sb.append("    dataApprovazione: ").append(toIndentedString(dataApprovazione)).append("\n");
    sb.append("    consegnaFrazionata: ").append(toIndentedString(consegnaFrazionata)).append("\n");
    sb.append("    avvioPerFasi: ").append(toIndentedString(avvioPerFasi)).append("\n");
    sb.append("    dataVerbalePrimaConsegna: ").append(toIndentedString(dataVerbalePrimaConsegna)).append("\n");
    sb.append("    dataAvvioPrimaFase: ").append(toIndentedString(dataAvvioPrimaFase)).append("\n");
    sb.append("    dataVerbaleConsegnaDefinitiva: ").append(toIndentedString(dataVerbaleConsegnaDefinitiva)).append("\n");
    sb.append("    consegnaSottoRiserva: ").append(toIndentedString(consegnaSottoRiserva)).append("\n");
    sb.append("    dataEffettivoInizio: ").append(toIndentedString(dataEffettivoInizio)).append("\n");
    sb.append("    dataFinePrevista: ").append(toIndentedString(dataFinePrevista)).append("\n");
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
