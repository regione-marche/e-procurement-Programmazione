package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FaseAvanzamentoSempInsertForm {
	
	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long num;
	private Date dataCertificato;
	private Double importoCertificato;
	@JsonIgnore
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
