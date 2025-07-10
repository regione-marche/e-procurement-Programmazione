package it.appaltiecontratti.autenticazione.entity;

import io.swagger.annotations.ApiModelProperty;

public class UserExistsInfo {
	
	@ApiModelProperty(name = "flag che indica se l'utente esiste o meno", value = "flag che indica se l'utente esiste o meno")
	private String exist;
	
	@ApiModelProperty(name = "flag che indica se la registrazione è attiva", value = "flag che indica se la registrazione è attiva")
	private boolean abilitaRegistrazione;

	public String getExist() {
		return exist;
	}

	public void setExist(String exist) {
		this.exist = exist;
	}

	public boolean isAbilitaRegistrazione() {
		return abilitaRegistrazione;
	}

	public void setAbilitaRegistrazione(boolean abilitaRegistrazione) {
		this.abilitaRegistrazione = abilitaRegistrazione;
	}

}
