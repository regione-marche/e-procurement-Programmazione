package it.appaltiecontratti.tabellati.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contenitore per i dati da filtrare nella ricerca degli avvisi")
public class RupSearchForm {

	@ApiModelProperty(value = "Codice stazione appaltante RUP da cercare")
	private String stazioneAppaltante;
	
	@ApiModelProperty(value = "Porzione di nome/cognome/cf del RUP da cercare")
	private String rup;

	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}

	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}

	public String getRup() {
		return rup;
	}

	public void setRup(String rup) {
		this.rup = rup;
	}
	
	
}
