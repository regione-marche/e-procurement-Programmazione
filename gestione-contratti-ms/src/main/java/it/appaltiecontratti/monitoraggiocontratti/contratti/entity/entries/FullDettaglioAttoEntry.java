package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import io.swagger.annotations.ApiModelProperty;

public class FullDettaglioAttoEntry extends DettaglioAttoEntry {

	@ApiModelProperty(value = "campi ulteriori per la creazione dell'atto")
	private String campiVisibili;
	@ApiModelProperty(value = "campi obbligatori per la creazione dell'atto")
	private String campiObbligatori;
	@ApiModelProperty(value = "Descrizione relativa al campo \"tipdoc\"")
	private String tipDocDesc;

	public String getCampiVisibili() {
		return campiVisibili;
	}

	public void setCampiVisibili(String campiVisibili) {
		this.campiVisibili = campiVisibili;
	}

	public String getCampiObbligatori() {
		return campiObbligatori;
	}

	public void setCampiObbligatori(String campiObbligatori) {
		this.campiObbligatori = campiObbligatori;
	}

	public String getTipDocDesc() {
		return tipDocDesc;
	}

	public void setTipDocDesc(String tipDocDesc) {
		this.tipDocDesc = tipDocDesc;
	}

}
