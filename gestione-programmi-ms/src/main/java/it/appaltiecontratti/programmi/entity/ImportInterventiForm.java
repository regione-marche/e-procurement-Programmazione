package it.appaltiecontratti.programmi.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class ImportInterventiForm {

	@ApiModelProperty(value = "Id del programma su cui importare l'evento")
	@NotNull
	private Long idProgramma;
	
	@ApiModelProperty(value = "Chiavi idIntervento/idProgramma da importare")
	@NotNull
	private List<ChiaviIntervento> interventiToImport;
	
	
	private String choice;

	public Long getIdProgramma() {
		return idProgramma;
	}

	public void setIdProgramma(Long idProgramma) {
		this.idProgramma = idProgramma;
	}

	public List<ChiaviIntervento> getInterventiToImport() {
		return interventiToImport;
	}

	public void setInterventiToImport(List<ChiaviIntervento> interventiToImport) {
		this.interventiToImport = interventiToImport;
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}
	
	
	
}
