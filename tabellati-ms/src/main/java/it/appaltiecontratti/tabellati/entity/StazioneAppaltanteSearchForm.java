package it.appaltiecontratti.tabellati.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contenitore per i dati da filtrare nella ricerca delle stazioni appaltanti")
public class StazioneAppaltanteSearchForm {

	@ApiModelProperty(value = "Codice fiscale o ragione sociale della stazione appaltante da cercare")
	private String desc;

	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
