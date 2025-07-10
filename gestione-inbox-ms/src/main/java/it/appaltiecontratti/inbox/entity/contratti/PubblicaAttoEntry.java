package it.appaltiecontratti.inbox.entity.contratti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contenitore per i dati di pubblicazione di un atto")
public class PubblicaAttoEntry implements Serializable {

	private static final long serialVersionUID = -4433185026855332865L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "*Codice della gara", hidden = true)
	private Long idGara;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "*Numero progressivo pubblicazione", hidden = true)
	private Long numeroPubblicazione;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "*Codice applicativo chiamante", hidden = true)
	private String clientId;
	@ApiModelProperty(value = "Tipologia pubblicazione", required = true)
	private Long tipoDocumento;
	@ApiModelProperty(value = "Eventuale specificazione")
	@Size(max = 100)
	private String eventualeSpecificazione;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Data pubblicazione")
	private Date dataPubblicazione;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Data scadenza")
	private Date dataScadenza;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Data decreto")
	private Date dataDecreto;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Data del provvedimento")
	private Date dataProvvedimento;
	@ApiModelProperty(value = "Numero provvedimento")
	@Size(max = 50)
	private String numeroProvvedimento;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Data di stipula")
	private Date dataStipula;
	@ApiModelProperty(value = "Numero repertorio")
	@Size(max = 50)
	private String numeroRepertorio;
	@ApiModelProperty(value = "Ribasso di aggiudicazione %")
	private Double ribassoAggiudicazione;
	@ApiModelProperty(value = "Offerta in aumento %")
	private Double offertaAumento;
	@ApiModelProperty(value = "Importo di aggiudicazione")
	private Double importoAggiudicazione;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Data di aggiudicazione definitiva")
	private Date dataAggiudicazione;
	@ApiModelProperty(value = "URL profilo del committente")
	@Size(max = 2000)
	private String urlCommittente;
	@ApiModelProperty(value = "URL piattaforma e-procurement di svolgimento gara")
	@Size(max = 2000)
	private String urlEProcurement;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Data pubblicazione atto su sito SCP (valorizzato solo nel metodo /Atti/DettaglioAtto)")
	private Date primaPubblicazioneSCP;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@ApiModelProperty(value = "Utima modifica pubblicazione atto su sito SCP (valorizzato solo nel metodo /Atti/DettaglioAtto)")
	private Date ultimaModificaSCP;

	@ApiModelProperty(value = "Documenti allegati alla pubblicazione", required = true)
	@Size(min = 1)
	private List<AllegatoAttiEntry> documenti = new ArrayList<AllegatoAttiEntry>();

	@ApiModelProperty(value = "Dati gara da pubblicare", required = true)
	private PubblicaGaraEntry gara;

	@ApiModelProperty(value = "Aggiudicatari obbligatori solo per Avviso di aggiudicazione o affidamento")
	@Size(min = 0)
	private List<AggiudicatarioEntry> aggiudicatari = new ArrayList<AggiudicatarioEntry>();

	@ApiModelProperty(value = "Id univoco generato dal sistema remoto; deve essere utilizzato per le chiamate successive")
	private Long idRicevuto;

	public void setIdGara(Long idGara) {
		this.idGara = idGara;
	}

	public Long getIdGara() {
		return idGara;
	}

	public void setNumeroPubblicazione(Long numeroPubblicazione) {
		this.numeroPubblicazione = numeroPubblicazione;
	}

	public Long getNumeroPubblicazione() {
		return numeroPubblicazione;
	}

	public void setTipoDocumento(Long tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Long getTipoDocumento() {
		return tipoDocumento;
	}

	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataDecreto(Date dataDecreto) {
		this.dataDecreto = dataDecreto;
	}

	public Date getDataDecreto() {
		return dataDecreto;
	}

	public Date getDataProvvedimento() {
		return dataProvvedimento;
	}

	public void setDataProvvedimento(Date dataProvvedimento) {
		this.dataProvvedimento = dataProvvedimento;
	}

	public String getNumeroProvvedimento() {
		return numeroProvvedimento;
	}

	public void setNumeroProvvedimento(String numeroProvvedimento) {
		this.numeroProvvedimento = StringUtils.stripToNull(numeroProvvedimento);
	}

	public Date getDataStipula() {
		return dataStipula;
	}

	public void setDataStipula(Date dataStipula) {
		this.dataStipula = dataStipula;
	}

	public String getNumeroRepertorio() {
		return numeroRepertorio;
	}

	public void setNumeroRepertorio(String numeroRepertorio) {
		this.numeroRepertorio = StringUtils.stripToNull(numeroRepertorio);
	}

	public void setDocumenti(List<AllegatoAttiEntry> documenti) {
		this.documenti = documenti;
	}

	public List<AllegatoAttiEntry> getDocumenti() {
		return documenti;
	}

	public void setRibassoAggiudicazione(Double ribassoAggiudicazione) {
		this.ribassoAggiudicazione = ribassoAggiudicazione;
	}

	public Double getRibassoAggiudicazione() {
		return ribassoAggiudicazione;
	}

	public void setOffertaAumento(Double offertaAumento) {
		this.offertaAumento = offertaAumento;
	}

	public Double getOffertaAumento() {
		return offertaAumento;
	}

	public void setImportoAggiudicazione(Double importoAggiudicazione) {
		this.importoAggiudicazione = importoAggiudicazione;
	}

	public Double getImportoAggiudicazione() {
		return importoAggiudicazione;
	}

	public void setDataAggiudicazione(Date dataAggiudicazione) {
		this.dataAggiudicazione = dataAggiudicazione;
	}

	public Date getDataAggiudicazione() {
		return dataAggiudicazione;
	}

	public void setGara(PubblicaGaraEntry gara) {
		this.gara = gara;
	}

	public PubblicaGaraEntry getGara() {
		return gara;
	}

	public void setIdRicevuto(Long idRicevuto) {
		this.idRicevuto = idRicevuto;
	}

	public Long getIdRicevuto() {
		return idRicevuto;
	}

	public void setAggiudicatari(List<AggiudicatarioEntry> aggiudicatari) {
		this.aggiudicatari = aggiudicatari;
	}

	public List<AggiudicatarioEntry> getAggiudicatari() {
		return aggiudicatari;
	}

	public void setEventualeSpecificazione(String eventualeSpecificazione) {
		this.eventualeSpecificazione = eventualeSpecificazione;
	}

	public String getEventualeSpecificazione() {
		return eventualeSpecificazione;
	}

	public void setUrlCommittente(String urlCommittente) {
		this.urlCommittente = urlCommittente;
	}

	public String getUrlCommittente() {
		return urlCommittente;
	}

	public void setUrlEProcurement(String urlEProcurement) {
		this.urlEProcurement = urlEProcurement;
	}

	public String getUrlEProcurement() {
		return urlEProcurement;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setPrimaPubblicazioneSCP(Date primaPubblicazioneSCP) {
		this.primaPubblicazioneSCP = primaPubblicazioneSCP;
	}

	public Date getPrimaPubblicazioneSCP() {
		return primaPubblicazioneSCP;
	}

	public void setUltimaModificaSCP(Date ultimaModificaSCP) {
		this.ultimaModificaSCP = ultimaModificaSCP;
	}

	public Date getUltimaModificaSCP() {
		return ultimaModificaSCP;
	}

}
