package it.appaltiecontratti.programmi.entity;

import io.swagger.annotations.ApiModelProperty;

public class ChiaviIntervento {

	@ApiModelProperty(value = "Id del programma")
	private Long idProgramma;
	@ApiModelProperty(value = "Id dell'intervento")
	private Long idIntervento;

	public Long getIdProgramma() {
		return idProgramma;
	}

	public void setIdProgramma(Long idProgramma) {
		this.idProgramma = idProgramma;
	}

	public Long getIdIntervento() {
		return idIntervento;
	}

	public void setIdIntervento(Long idIntervento) {
		this.idIntervento = idIntervento;
	}
	
}
