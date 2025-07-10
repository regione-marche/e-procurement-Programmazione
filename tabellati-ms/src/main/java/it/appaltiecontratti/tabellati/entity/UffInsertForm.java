package it.appaltiecontratti.tabellati.entity;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.sicurezza.annotations.XSSValidation;

public class UffInsertForm {

	@ApiModelProperty(value="id dell'ufficio")
	private Long id;
	@ApiModelProperty(value="codice stazione appaltante dell'ufficio")
	@NotNull
	@XSSValidation
	private String stazioneAppaltante;
	@NotNull
	@ApiModelProperty(value="denominazione dell'ufficio")
	@XSSValidation
	private String denominazione;
	
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
	public Long getCentroCosto() {
		return centroCosto;
	}
	public void setCentroCosto(Long centroCosto) {
		this.centroCosto = centroCosto;
	}
	
	
}
