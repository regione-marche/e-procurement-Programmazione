package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAvanzamentoEntry;

public class ResponseFaseAvanzamento extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase avanzamento")
	FaseAvanzamentoEntry data;
	
	public FaseAvanzamentoEntry getData() {
		return data;
	}

	public void setData(FaseAvanzamentoEntry data) {
		this.data = data;
	}
	
}