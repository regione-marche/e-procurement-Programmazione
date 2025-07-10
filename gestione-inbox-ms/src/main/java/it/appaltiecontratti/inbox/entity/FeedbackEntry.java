package it.appaltiecontratti.inbox.entity;

import java.util.Date;
import java.util.List;

public class FeedbackEntry {

	private Long numEsportazione;
	private Date dataCreAzionePayload;
	private Date dataInvio;
	private String cig;
	private String denominazione;
	private Long tipologiaFaseEsecuzione; 
	private Long numProgFase;
	private String requestPayload;
	private String note;
	private Date dataElaborazione;
	private Long numeroErrore;
	private Long numeroWarning;
	private Date dataImportazione;
	private String feedbackAnalisi;
	private List<AnomaliaEntry> anomaliaEntry;
	private String esito;
		
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public Long getNumEsportazione() {
		return numEsportazione;
	}
	public void setNumEsportazione(Long numEsportazione) {
		this.numEsportazione = numEsportazione;
	}
	public Date getDataCreAzionePayload() {
		return dataCreAzionePayload;
	}
	public void setDataCreAzionePayload(Date dataCreAzionePayload) {
		this.dataCreAzionePayload = dataCreAzionePayload;
	}
	public Date getDataInvio() {
		return dataInvio;
	}
	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}
	public String getCig() {
		return cig;
	}
	public void setCig(String cig) {
		this.cig = cig;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public Long getTipologiaFaseEsecuzione() {
		return tipologiaFaseEsecuzione;
	}
	public void setTipologiaFaseEsecuzione(Long tipologiaFaseEsecuzione) {
		this.tipologiaFaseEsecuzione = tipologiaFaseEsecuzione;
	}
	public Long getNumProgFase() {
		return numProgFase;
	}
	public void setNumProgFase(Long numProgFase) {
		this.numProgFase = numProgFase;
	}
	public String getRequestPayload() {
		return requestPayload;
	}
	public void setRequestPayload(String requestPayload) {
		this.requestPayload = requestPayload;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getDataElaborazione() {
		return dataElaborazione;
	}
	public void setDataElaborazione(Date dataElaborazione) {
		this.dataElaborazione = dataElaborazione;
	}
	public Long getNumeroErrore() {
		return numeroErrore;
	}
	public void setNumeroErrore(Long numeroErrore) {
		this.numeroErrore = numeroErrore;
	}
	public Long getNumeroWarning() {
		return numeroWarning;
	}
	public void setNumeroWarning(Long numeroWarning) {
		this.numeroWarning = numeroWarning;
	}
	public Date getDataImportazione() {
		return dataImportazione;
	}
	public void setDataImportazione(Date dataImportazione) {
		this.dataImportazione = dataImportazione;
	}
	public String getFeedbackAnalisi() {
		return feedbackAnalisi;
	}
	public void setFeedbackAnalisi(String feedbackAnalisi) {
		this.feedbackAnalisi = feedbackAnalisi;
	}
	public List<AnomaliaEntry> getAnomaliaEntry() {
		return anomaliaEntry;
	}
	public void setAnomaliaEntry(List<AnomaliaEntry> anomaliaEntry) {
		this.anomaliaEntry = anomaliaEntry;
	}
	
	
}
