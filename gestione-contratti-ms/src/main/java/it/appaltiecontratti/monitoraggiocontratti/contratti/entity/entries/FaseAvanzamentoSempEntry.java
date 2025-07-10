package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

public class FaseAvanzamentoSempEntry {
	
	private Long codGara;
	private Long codLotto;
	private Long num;
	private Date dataCertificato;
	private Double importoCertificato;
	private boolean pubblicata;
	
	public boolean isPubblicata() {
		return pubblicata;
	}
	public void setPubblicata(boolean pubblicata) {
		this.pubblicata = pubblicata;
	}
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
	public Date getDataCertificato() {
		return dataCertificato;
	}
	public void setDataCertificato(Date dataCertificato) {
		this.dataCertificato = dataCertificato;
	}
	public Double getImportoCertificato() {
		return importoCertificato;
	}
	public void setImportoCertificato(Double importoCertificato) {
		this.importoCertificato = importoCertificato;
	}
}
