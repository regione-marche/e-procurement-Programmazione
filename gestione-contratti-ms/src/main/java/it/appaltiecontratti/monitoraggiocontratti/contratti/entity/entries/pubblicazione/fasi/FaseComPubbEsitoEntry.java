package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi;

import java.util.Date;

public class FaseComPubbEsitoEntry {

	private Long codGara;
	private Long codLotto;
	private Long esito;
	private Date dataAggiudicazione;
	private Double importoAggiudicazione;
	private String binary;
	private byte[] fileAllegato;

	public Long getCodGara() {
		return codGara;
	}
	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}
	public Long getCodLotto() {
		return codLotto;
	}
	public void setCodLotto(Long codLotto) {
		this.codLotto = codLotto;
	}
	public Long getEsito() {
		return esito;
	}
	public void setEsito(Long esito) {
		this.esito = esito;
	}
	public Date getDataAggiudicazione() {
		return dataAggiudicazione;
	}
	public void setDataAggiudicazione(Date dataAggiudicazione) {
		this.dataAggiudicazione = dataAggiudicazione;
	}
	public Double getImportoAggiudicazione() {
		return importoAggiudicazione;
	}
	public void setImportoAggiudicazione(Double importoAggiudicazione) {
		this.importoAggiudicazione = importoAggiudicazione;
	}
	public String getBinary() {
		return binary;
	}
	public void setBinary(String binary) {
		this.binary = binary;
	}
	public byte[] getFileAllegato() {
		return fileAllegato;
	}
	public void setFileAllegato(byte[] fileAllegato) {
		this.fileAllegato = fileAllegato;
	}

}
