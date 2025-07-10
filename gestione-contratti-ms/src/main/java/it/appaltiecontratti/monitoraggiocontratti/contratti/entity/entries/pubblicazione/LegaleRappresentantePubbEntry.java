package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione;

import io.swagger.annotations.ApiModelProperty;

public class LegaleRappresentantePubbEntry {

	@ApiModelProperty(value = "Nome legale rappresentante")
	private String nome;
	@ApiModelProperty(value = "Cogome legale rappresentante")
	private String cognome;
	@ApiModelProperty(value = "Codice fiscale legale rappresentante")
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
