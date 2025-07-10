package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class FaseRipresaPrestazioneInsertForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long num;	
	private Double incCrono;	
	private Date dataVerbRipr;
	@XSSValidation
	private String flagRiserve;
	@XSSValidation
	private String flagVerbale;
	
	
	public Date getDataVerbRipr() {
		return dataVerbRipr;
	}
	public void setDataVerbRipr(Date dataVerbRipr) {
		this.dataVerbRipr = dataVerbRipr;
	}
	public String getFlagRiserve() {
		return flagRiserve;
	}
	public void setFlagRiserve(String flagRiserve) {
		this.flagRiserve = flagRiserve;
	}
	public String getFlagVerbale() {
		return flagVerbale;
	}
	public void setFlagVerbale(String flagVerbale) {
		this.flagVerbale = flagVerbale;
	}
	public Double getIncCrono() {
		return incCrono;
	}
	public void setIncCrono(Double incCrono) {
		this.incCrono = incCrono;
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
	
}
