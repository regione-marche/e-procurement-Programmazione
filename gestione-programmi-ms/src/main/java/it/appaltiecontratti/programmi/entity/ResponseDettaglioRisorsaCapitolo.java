package it.appaltiecontratti.programmi.entity;

import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ResponseDettaglioRisorsaCapitolo extends BaseResponse{

	private static final long serialVersionUID = 1L;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati della risorsa di capitolo")
	private RisorsaCapitoloEntry data;

	public RisorsaCapitoloEntry getData() {
		return data;
	}

	public void setData(RisorsaCapitoloEntry entry) {
		this.data = entry;
	}

}
