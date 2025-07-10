package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

public class FaseConclusioneAffidamentiDirettiEntry extends FaseBaseEntry{

	private Long codGara;
	private Long codLotto;
	private Long num;
	private boolean pubblicata;
	private Date dataVerbInizio;
	private Date dataUltimazione;
	private Double importoCertificato;
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
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	public boolean isPubblicata() {
		return pubblicata;
	}
	public void setPubblicata(boolean pubblicata) {
		this.pubblicata = pubblicata;
	}
	public Date getDataVerbInizio() {
		return dataVerbInizio;
	}
	public void setDataVerbInizio(Date dataVerbInizio) {
		this.dataVerbInizio = dataVerbInizio;
	}
	public Date getDataUltimazione() {
		return dataUltimazione;
	}
	public void setDataUltimazione(Date dataUltimazione) {
		this.dataUltimazione = dataUltimazione;
	}
	public Double getImportoCertificato() {
		return importoCertificato;
	}
	public void setImportoCertificato(Double importoCertificato) {
		this.importoCertificato = importoCertificato;
	}

	

}
