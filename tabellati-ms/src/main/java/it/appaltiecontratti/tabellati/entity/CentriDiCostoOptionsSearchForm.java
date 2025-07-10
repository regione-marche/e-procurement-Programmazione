package it.appaltiecontratti.tabellati.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



@ApiModel(description = "Contenitore per i dati dei centri di costo da filtrare nell'inserimento di una gara")
public class CentriDiCostoOptionsSearchForm {

	@ApiModelProperty(value = "Codice stazione appaltante centro di costo da cercare")
	private String stazioneAppaltante;
	
	@ApiModelProperty(value = "Porzione di descrizione del centro di costo da cercare")
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
