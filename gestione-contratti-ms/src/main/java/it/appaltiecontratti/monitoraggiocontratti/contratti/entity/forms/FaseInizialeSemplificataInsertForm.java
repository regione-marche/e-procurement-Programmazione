package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class FaseInizialeSemplificataInsertForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Date dataStipula;
	@XSSValidation
	private String flagRiserva;
	private Date dataVerbale;
	private Date dataTermine;
	@JsonIgnore
	private Long numAppa;
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
	public Date getDataStipula() {
		return dataStipula;
	}
	public void setDataStipula(Date dataStipula) {
		this.dataStipula = dataStipula;
	}
	public String getFlagRiserva() {
		return flagRiserva;
	}
	public void setFlagRiserva(String flagRiserva) {
		this.flagRiserva = flagRiserva;
	}
	public Date getDataVerbale() {
		return dataVerbale;
	}
	public void setDataVerbale(Date dataVerbale) {
		this.dataVerbale = dataVerbale;
	}
	public Date getDataTermine() {
		return dataTermine;
	}
	public void setDataTermine(Date dataTermine) {
		this.dataTermine = dataTermine;
	}
	public Long getNumAppa() {
		return numAppa;
	}
	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	
	
}
