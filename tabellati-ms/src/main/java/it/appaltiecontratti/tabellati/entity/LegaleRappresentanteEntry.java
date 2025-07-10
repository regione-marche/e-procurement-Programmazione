package it.appaltiecontratti.tabellati.entity;

import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class LegaleRappresentanteEntry {

	@XSSValidation
	private String nome;
	@XSSValidation
	private String cognome;
	@XSSValidation
	private String cf;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}

}
