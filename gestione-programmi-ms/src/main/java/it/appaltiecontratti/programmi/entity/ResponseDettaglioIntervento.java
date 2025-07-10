package it.appaltiecontratti.programmi.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ResponseDettaglioIntervento  extends BaseResponse implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati dell'intervento")
	private InterventoEntry data;

	public InterventoEntry getData() {
		return data;
	}

	public void setData(InterventoEntry entry) {
		this.data = entry;
	}
}