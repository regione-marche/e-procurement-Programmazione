package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

import javax.validation.constraints.NotNull;

public class SchedaSicurezzaInsertForm {
	
	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long num;
	private Long numIniz;
	@XSSValidation
	private String pianoSicurezza;
	@XSSValidation
	private String direttoreOperativo;
	@XSSValidation
	private String tutor;
	
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
	public String getPianoSicurezza() {
		return pianoSicurezza;
	}
	public void setPianoSicurezza(String pianoSicurezza) {
		this.pianoSicurezza = pianoSicurezza;
	}
	public String getDirettoreOperativo() {
		return direttoreOperativo;
	}
	public void setDirettoreOperativo(String direttoreOperativo) {
		this.direttoreOperativo = direttoreOperativo;
	}
	public String getTutor() {
		return tutor;
	}
	public void setTutor(String tutor) {
		this.tutor = tutor;
	}
	public Long getNumIniz() {
		return numIniz;
	}
	public void setNumIniz(Long numIniz) {
		this.numIniz = numIniz;
	}
	
	
}
