package it.appaltiecontratti.autenticazione.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * Entry dei dati di base una stazione appaltante
 * 
 * @author Michele.DiNapoli
 */

public class SABaseEntry {

	@ApiModelProperty(name = "codice", value = "Codice stazione appaltante")
	private String codice;
	@ApiModelProperty(name = "nome", value = "Nome stazione appaltante")
	private String nome;
	
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}


}
