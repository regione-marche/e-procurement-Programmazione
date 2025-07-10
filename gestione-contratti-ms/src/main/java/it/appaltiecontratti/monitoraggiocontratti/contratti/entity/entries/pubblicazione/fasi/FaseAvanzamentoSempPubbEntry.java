package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi;

import java.util.Date;

public class FaseAvanzamentoSempPubbEntry {

	private Long codGara;
	private Long codLotto;
	private Long num;
	private Date dataCertificato;
	private Double importoCertificato;
	private Long numAppa;

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
	public Long getNumAppa() {
		return numAppa;
	}
	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}

}
