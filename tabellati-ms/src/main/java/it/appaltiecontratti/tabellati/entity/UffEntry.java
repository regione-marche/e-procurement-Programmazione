package it.appaltiecontratti.tabellati.entity;

import io.swagger.annotations.ApiModelProperty;

public class UffEntry {

	@ApiModelProperty(value = "id dell'ufficio")
	private Long id;
	@ApiModelProperty(value = "codice stazione appaltante dell'ufficio")
	private String stazioneAppaltante;
	@ApiModelProperty(value = "denominazione dell'ufficio")
	private String denominazione;
	@ApiModelProperty(value = "Parametro che dice se l'ufficio e' cancellabile")
	private boolean deletable;
	@ApiModelProperty(value="È centro di costo per la programmazione?")
	private Long centroCosto;

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

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public Long getCentroCosto() {
		return centroCosto;
	}

	public void setCentroCosto(Long centroCosto) {
		this.centroCosto = centroCosto;
	}

	
}
