package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseCollaudoEntry;

public class ResponseFaseCollaudo  extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase collaudo")
	FaseCollaudoEntry data;
	
	public FaseCollaudoEntry getData() {
		return data;
	}

	public void setData(FaseCollaudoEntry data) {
		this.data = data;
	}
}
