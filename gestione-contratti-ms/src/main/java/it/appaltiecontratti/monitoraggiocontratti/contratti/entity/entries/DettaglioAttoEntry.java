package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.entries.AllegatoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.ExistingAttoDocument;

public class DettaglioAttoEntry {

	private Long codGara;
	private Long numPubb;
	private Date dataPubb;
	private String descriz;
	private Date dataScad;
	private Date dataDecreto;
	private Date dataProvvedimento;
	private String numProvvedimento;
	private Date dataStipula;
	private String numRepertorio;
	private Double percRibassoAgg;
	private Double percOffAumento;
	private Double importoAggiudicazione;
	private Date dataVerbAggiudicazione;
	private Long idGenerato;
	private Long idRicevuto;
	private String urlCommittente;
	private String urlEproc;
	private List<ExistingAttoDocument> existingDocuments;
	private Long tipDoc;
	private boolean deletable;
	private int countLotti;
	private String primaPubblicazione;
	private Date dataPubbSistema;
	private Long utenteCancellazione;
	private Date dataCancellazione;
	private String motivoCancellazione;
	private List<AllegatoEntry> documents;

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

	public Long getIdGenerato() {
		return idGenerato;
	}

	public void setIdGenerato(Long idGenerato) {
		this.idGenerato = idGenerato;
	}

	public Long getIdRicevuto() {
		return idRicevuto;
	}

	public void setIdRicevuto(Long idRicevuto) {
		this.idRicevuto = idRicevuto;
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

	public List<ExistingAttoDocument> getExistingDocuments() {
		return existingDocuments;
	}

	public void setExistingDocuments(List<ExistingAttoDocument> existingDocuments) {
		this.existingDocuments = existingDocuments;
	}

	public Long getTipDoc() {
		return tipDoc;
	}

	public void setTipDoc(Long tipDoc) {
		this.tipDoc = tipDoc;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public int getCountLotti() {
		return countLotti;
	}

	public void setCountLotti(int countLotti) {
		this.countLotti = countLotti;
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

	public List<AllegatoEntry> getDocuments() { return documents; }

	public void setDocuments(List<AllegatoEntry> documents) { this.documents = documents; }

}
