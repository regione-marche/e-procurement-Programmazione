package it.appaltiecontratti.programmi.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ResponseDettaglioProgrammi extends BaseResponse implements Serializable{

		
	private static final long serialVersionUID = 1L;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati del programma")
	private ProgrammaEntry data;

	public ProgrammaEntry getData() {
		return data;
	}

	public void setData(ProgrammaEntry entry) {
		this.data = entry;
	}
}
