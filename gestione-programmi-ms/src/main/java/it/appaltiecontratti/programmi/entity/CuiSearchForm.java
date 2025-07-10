package it.appaltiecontratti.programmi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contenitore per i dati da filtrare nella ricerca dei cui")
public class CuiSearchForm {

	@ApiModelProperty(value = "Codice della stazione appaltante")
	private String stazioneAppaltante;

	@ApiModelProperty(value = "Porzione di codice del cui da cercare")
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
