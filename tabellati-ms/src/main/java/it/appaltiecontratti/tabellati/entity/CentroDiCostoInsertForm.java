package it.appaltiecontratti.tabellati.entity;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

import javax.validation.constraints.NotNull;

public class CentroDiCostoInsertForm {

	private Long id;
	@NotNull
	@XSSValidation
	private String stazioneAppaltante;
	@XSSValidation
	private String codiceCentro;
	@NotNull
	@XSSValidation
	private String denominazione;
	@XSSValidation
	private String note;
	private Long tipologia;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}
	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}
	public String getCodiceCentro() {
		return codiceCentro;
	}
	public void setCodiceCentro(String codiceCentro) {
		this.codiceCentro = codiceCentro;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Long getTipologia() {
		return tipologia;
	}
	public void setTipologia(Long tipologia) {
		this.tipologia = tipologia;
	}
}
