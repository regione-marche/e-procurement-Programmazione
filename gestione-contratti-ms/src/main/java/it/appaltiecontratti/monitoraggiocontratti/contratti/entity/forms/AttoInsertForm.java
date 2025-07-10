package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.entries.AllegatoEntry;
import it.appaltiecontratti.sicurezza.annotations.XSSValidation;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.AttoDocument;

public class AttoInsertForm {

	@ApiModelProperty(value = "Codice gara dell'atto")
	@NotNull
	private Long codGara;
	@ApiModelProperty(value = "numero pubblicazione dell'atto")
	private Long numPubb;
	@ApiModelProperty(value = "data pubblicazione dell'atto")
	private Date dataPubb;
	@ApiModelProperty(value = "descrizione dell'atto")
	@XSSValidation
	private String descriz;
	@ApiModelProperty(value = "data scadenza dell'atto")
	private Date dataScad;
	@ApiModelProperty(value = "data decreto dell'atto")
	private Date dataDecreto;
	@ApiModelProperty(value = "data provvedimento dell'atto")
	private Date dataProvvedimento;
	@ApiModelProperty(value = "numero provvedimento dell'atto")
	@XSSValidation
	private String numProvvedimento;
	@ApiModelProperty(value = "data stipula dell'atto")
	private Date dataStipula;
	@ApiModelProperty(value = "numero repertorio dell'atto")
	@XSSValidation
	private String numRepertorio;
	@ApiModelProperty(value = "percentuale ribasso aggiudicazione dell'atto")
	private Double percRibassoAgg;
	@ApiModelProperty(value = "percentuale offerta aumento dell'atto")
	private Double percOffAumento;
	@ApiModelProperty(value = "importo aggiudicazione dell'atto")
	private Double importoAggiudicazione;
	@ApiModelProperty(value = "data verbale aggiudicazione dell'atto")
	private Date dataVerbAggiudicazione;
	@ApiModelProperty(value = "url del committente dell'atto")
	@XSSValidation
	private String urlCommittente;
	@ApiModelProperty(value = "url eproc dell'atto")
	@XSSValidation
	private String urlEproc;
	@ApiModelProperty(value = "documenti dell'atto")
	private List<AttoDocument> documents;
	@ApiModelProperty(value = "tipologia di'atto")
	private Long tipDoc;
	@ApiModelProperty(value = "codice lotto dell'atto")
	private Long codLotto;
	@ApiModelProperty(value = "Prima pubblicazione?")
	private String primaPubblicazione;
	@ApiModelProperty(value = "Data di pubblicazione sul sistema")
	private Date dataPubbSistema;
	@ApiModelProperty(value = "Syscon dell'utente che annulla la pubblicazione dell'atto")
	private Long utenteCancellazione;
	@ApiModelProperty(value = "Data di annullamento di pubblicazione di un atto")
	private Date dataCancellazione;
	@ApiModelProperty(value = "Motivo annullamento pubblicazione di un atto")
	private String motivoCancellazione;
	@ApiModelProperty(value = "Nuovi documenti/Documenti aggiornati dell'atto")
	private List<AllegatoEntry> updateDocuments;

	public Long getCodGara() {
		return codGara;
	}

	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}

	public Long getNumPubb() {
		return numPubb;
	}

	public void setNumPubb(Long numPubb) {
		this.numPubb = numPubb;
	}

	public Date getDataPubb() {
		return dataPubb;
	}

	public void setDataPubb(Date dataPubb) {
		this.dataPubb = dataPubb;
	}

	public String getDescriz() {
		return descriz;
	}

	public void setDescriz(String descriz) {
		this.descriz = descriz;
	}

	public Date getDataScad() {
		return dataScad;
	}

	public void setDataScad(Date dataScad) {
		this.dataScad = dataScad;
	}

	public Date getDataDecreto() {
		return dataDecreto;
	}

	public void setDataDecreto(Date dataDecreto) {
		this.dataDecreto = dataDecreto;
	}

	public Date getDataProvvedimento() {
		return dataProvvedimento;
	}

	public void setDataProvvedimento(Date dataProvvedimento) {
		this.dataProvvedimento = dataProvvedimento;
	}

	public String getNumProvvedimento() {
		return numProvvedimento;
	}

	public void setNumProvvedimento(String numProvvedimento) {
		this.numProvvedimento = numProvvedimento;
	}

	public Date getDataStipula() {
		return dataStipula;
	}

	public void setDataStipula(Date dataStipula) {
		this.dataStipula = dataStipula;
	}

	public String getNumRepertorio() {
		return numRepertorio;
	}

	public void setNumRepertorio(String numRepertorio) {
		this.numRepertorio = numRepertorio;
	}

	public Double getPercRibassoAgg() {
		return percRibassoAgg;
	}

	public void setPercRibassoAgg(Double percRibassoAgg) {
		this.percRibassoAgg = percRibassoAgg;
	}

	public Double getPercOffAumento() {
		return percOffAumento;
	}

	public void setPercOffAumento(Double percOffAumento) {
		this.percOffAumento = percOffAumento;
	}

	public Double getImportoAggiudicazione() {
		return importoAggiudicazione;
	}

	public void setImportoAggiudicazione(Double importoAggiudicazione) {
		this.importoAggiudicazione = importoAggiudicazione;
	}

	public Date getDataVerbAggiudicazione() {
		return dataVerbAggiudicazione;
	}

	public void setDataVerbAggiudicazione(Date dataVerbAggiudicazione) {
		this.dataVerbAggiudicazione = dataVerbAggiudicazione;
	}

	public String getUrlCommittente() {
		return urlCommittente;
	}

	public void setUrlCommittente(String urlCommittente) {
		this.urlCommittente = urlCommittente;
	}

	public String getUrlEproc() {
		return urlEproc;
	}

	public void setUrlEproc(String urlEproc) {
		this.urlEproc = urlEproc;
	}

	public List<AttoDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(List<AttoDocument> documents) {
		this.documents = documents;
	}

	public Long getTipDoc() {
		return tipDoc;
	}

	public void setTipDoc(Long tipDoc) {
		this.tipDoc = tipDoc;
	}

	public Long getCodLotto() {
		return codLotto;
	}

	public void setCodLotto(Long codLotto) {
		this.codLotto = codLotto;
	}

	public String getPrimaPubblicazione() { return primaPubblicazione; }

	public void setPrimaPubblicazione(String primaPubblicazione) { this.primaPubblicazione = primaPubblicazione; }

	public Date getDataPubbSistema() { return dataPubbSistema; }

	public void setDataPubbSistema(Date dataPubbSistema) { this.dataPubbSistema = dataPubbSistema; }

	public Long getUtenteCancellazione() { return utenteCancellazione; }

	public void setUtenteCancellazione(Long utenteCancellazione) { this.utenteCancellazione = utenteCancellazione; }

	public Date getDataCancellazione() { return dataCancellazione; }

	public void setDataCancellazione(Date dataCancellazione) { this.dataCancellazione = dataCancellazione; }

	public String getMotivoCancellazione() { return motivoCancellazione; }

	public void setMotivoCancellazione(String motivoCancellazione) { this.motivoCancellazione = motivoCancellazione; }

	public List<AllegatoEntry> getUpdateDocuments() { return updateDocuments; }

	public void setUpdateDocuments(List<AllegatoEntry> updateDocuments) { this.updateDocuments = updateDocuments; }

}
