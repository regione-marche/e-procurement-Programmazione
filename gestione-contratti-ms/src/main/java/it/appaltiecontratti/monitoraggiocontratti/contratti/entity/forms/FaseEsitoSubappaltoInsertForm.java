package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.MandanteEntry;

public class FaseEsitoSubappaltoInsertForm {
	
	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long num;
	private Date dataAuth;
	private Long motivoMancatoSub;
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
	public Date getDataAuth() {
		return dataAuth;
	}
	public void setDataAuth(Date dataAuth) {
		this.dataAuth = dataAuth;
	}
	public Long getMotivoMancatoSub() {
		return motivoMancatoSub;
	}
	public void setMotivoMancatoSub(Long motivoMancatoSub) {
		this.motivoMancatoSub = motivoMancatoSub;
	}
	
	
}
