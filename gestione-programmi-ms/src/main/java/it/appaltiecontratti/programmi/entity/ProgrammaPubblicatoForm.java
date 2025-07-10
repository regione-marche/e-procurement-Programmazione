package it.appaltiecontratti.programmi.entity;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="Contenitore per i dati necessari ad aggiornare l'id di pubblicazione di un programma")
public class ProgrammaPubblicatoForm {

	@NotNull
	@ApiModelProperty(value="Id programma")
	private Long id; 
	
	@NotNull
	@ApiModelProperty(value="Id generato in fase di pubblicazione")
    private Integer idRicevuto;
	
	@NotNull
	@ApiModelProperty(value="Codice stazione appaltante dell'avviso")
	private String stazioneAppaltante;

	@NotNull
	@ApiModelProperty(value="Syscon dell'autore della pubblicazione")
	private Long syscon;
	
	private String payload;
	
	public Integer getIdRicevuto() {
		return idRicevuto;
	}

	public void setIdRicevuto(Integer idRicevuto) {
		this.idRicevuto = idRicevuto;
	}

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

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
	
}
