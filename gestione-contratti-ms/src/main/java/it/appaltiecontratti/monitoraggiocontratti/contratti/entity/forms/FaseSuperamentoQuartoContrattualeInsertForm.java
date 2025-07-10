package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class FaseSuperamentoQuartoContrattualeInsertForm{

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long num;	
	private Date dataSuperQuarto;
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
	public Date getDataSuperQuarto() {
		return dataSuperQuarto;
	}
	public void setDataSuperQuarto(Date dataSuperQuarto) {
		this.dataSuperQuarto = dataSuperQuarto;
	}
	
	
}
