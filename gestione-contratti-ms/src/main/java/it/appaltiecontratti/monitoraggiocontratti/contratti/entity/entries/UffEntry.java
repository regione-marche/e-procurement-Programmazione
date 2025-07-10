package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import io.swagger.annotations.ApiModelProperty;

public class UffEntry {

	@ApiModelProperty(value = "id dell'ufficio")
	private Long id;
	@ApiModelProperty(value = "codice stazione appaltante dell'ufficio")
	private String stazioneAppaltante;
	@ApiModelProperty(value = "denominazione dell'ufficio")
	private String denominazione;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}

	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

}
