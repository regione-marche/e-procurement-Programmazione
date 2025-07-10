package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FaseInfortuniInsertForm {

	@NotNull
	private Long codGara;
	@NotNull
	private Long codLotto;
	private Long num;
	private Long totaleInfortuni;
	private Long giornateRiconosciute; 
	private Long infortuniMortali;
	private Long infortuniPermanenti;
	private String codImpresa;
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
	public Long getTotaleInfortuni() {
		return totaleInfortuni;
	}
	public void setTotaleInfortuni(Long totaleInfortuni) {
		this.totaleInfortuni = totaleInfortuni;
	}
	public Long getGiornateRiconosciute() {
		return giornateRiconosciute;
	}
	public void setGiornateRiconosciute(Long giornateRiconosciute) {
		this.giornateRiconosciute = giornateRiconosciute;
	}
	public Long getInfortuniMortali() {
		return infortuniMortali;
	}
	public void setInfortuniMortali(Long infortuniMortali) {
		this.infortuniMortali = infortuniMortali;
	}
	public Long getInfortuniPermanenti() {
		return infortuniPermanenti;
	}
	public void setInfortuniPermanenti(Long infortuniPermanenti) {
		this.infortuniPermanenti = infortuniPermanenti;
	}
	public String getCodImpresa() {
		return codImpresa;
	}
	public void setCodImpresa(String codImpresa) {
		this.codImpresa = codImpresa;
	}
	public Long getNumAppa() {
		return numAppa;
	}
	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}
	
}
