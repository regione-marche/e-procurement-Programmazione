package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import javax.validation.constraints.NotNull;

public class ImportImpresaAgiudicatariaForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long num;
	private Long numRagg;
	
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
	public Long getNumRagg() {
		return numRagg;
	}
	public void setNumRagg(Long numRagg) {
		this.numRagg = numRagg;
	}
	
	
}
