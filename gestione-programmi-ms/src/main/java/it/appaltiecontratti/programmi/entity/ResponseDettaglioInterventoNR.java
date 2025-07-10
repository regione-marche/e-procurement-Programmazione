package it.appaltiecontratti.programmi.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponseDettaglioInterventoNR  extends BaseResponse implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati dell'intervento non riproposto")
	private InterventoNonRipropostoEntry data;

	public InterventoNonRipropostoEntry getData() {
		return data;
	}

	public void setData(InterventoNonRipropostoEntry entry) {
		this.data = entry;
	}
}