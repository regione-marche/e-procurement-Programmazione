package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseVarianteEntry;

public class ResponseFaseVariante extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase variante")
	FaseVarianteEntry data;
	
	public FaseVarianteEntry getData() {
		return data;
	}

	public void setData(FaseVarianteEntry data) {
		this.data = data;
	}
	
}