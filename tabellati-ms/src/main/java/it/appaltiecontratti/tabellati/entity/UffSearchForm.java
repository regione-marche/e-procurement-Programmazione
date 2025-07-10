package it.appaltiecontratti.tabellati.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



@ApiModel(description = "Contenitore per i dati uffici da filtrare nell'inserimento programma")
public class UffSearchForm {

	@ApiModelProperty(value = "Codice stazione appaltante ufficio da cercare")
	private String stazioneAppaltante;
	
	@ApiModelProperty(value = "Porzione di descrizione dell'ufficio da cercare")
	private String desc;

	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}

	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	
}
