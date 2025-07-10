package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAvanzamentoSempEntry;

public class ResponseFaseAvanzamentoSemp extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase avanzamento semplificata")
	FaseAvanzamentoSempEntry data;
	
	public FaseAvanzamentoSempEntry getData() {
		return data;
	}

	public void setData(FaseAvanzamentoSempEntry data) {
		this.data = data;
	}
	
}