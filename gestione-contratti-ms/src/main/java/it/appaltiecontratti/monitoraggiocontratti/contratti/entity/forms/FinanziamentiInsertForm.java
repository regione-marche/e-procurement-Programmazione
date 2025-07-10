package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.List;

import javax.validation.constraints.NotNull;

public class FinanziamentiInsertForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long numAppa;
	private List<FinanziamentoInsertForm> finanziamenti;

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

	public Long getNumAppa() {
		return numAppa;
	}

	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}

	public List<FinanziamentoInsertForm> getFinanziamenti() {
		return finanziamenti;
	}

	public void setFinanziamenti(List<FinanziamentoInsertForm> finanziamenti) {
		this.finanziamenti = finanziamenti;
	}

}