package it.appaltiecontratti.tabellati.entity;

public class CentroDiCostoEntry {

	private Long id;
	private String stazioneAppaltante;
	private String codiceCentro;
	private String denominazione;
	private String note;
	private Long tipologia;
	private String nominativoStazioneAppaltante;
	private boolean deletable;
	
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
	public String getNominativoStazioneAppaltante() {
		return nominativoStazioneAppaltante;
	}
	public void setNominativoStazioneAppaltante(String nominativoStazioneAppaltante) {
		this.nominativoStazioneAppaltante = nominativoStazioneAppaltante;
	}
	public boolean isDeletable() {
		return deletable;
	}
	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}
	
	
}
