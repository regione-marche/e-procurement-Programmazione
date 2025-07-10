package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.List;

import javax.validation.constraints.NotNull;

public class IncarichiProfessionaliInsertForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long numAppa;
	private Long codFase;
	private List<IncaricoProfessionaleInsertForm> incarichi;
	private Long num;

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
	
	public Long getCodFase() {
		return codFase;
	}

	public void setCodFase(Long codFase) {
		this.codFase = codFase;
	}

	public List<IncaricoProfessionaleInsertForm> getIncarichi() {
		return incarichi;
	}

	public void setIncarichi(List<IncaricoProfessionaleInsertForm> incarichi) {
		this.incarichi = incarichi;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

}
