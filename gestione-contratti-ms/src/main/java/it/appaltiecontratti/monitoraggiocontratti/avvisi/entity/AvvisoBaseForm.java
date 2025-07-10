package it.appaltiecontratti.monitoraggiocontratti.avvisi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contenitore per i dati principali di un avviso")
public class AvvisoBaseForm {

	@ApiModelProperty(value = "Codice stazione appaltante dell'avviso")
	private String stazioneAppaltante;
	@ApiModelProperty(value = "Numero dell'avviso")
	private Integer numeroAvviso;

	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}

	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}

	public Integer getNumeroAvviso() {
		return numeroAvviso;
	}

	public void setNumeroAvviso(Integer numeroAvviso) {
		this.numeroAvviso = numeroAvviso;
	}
}
