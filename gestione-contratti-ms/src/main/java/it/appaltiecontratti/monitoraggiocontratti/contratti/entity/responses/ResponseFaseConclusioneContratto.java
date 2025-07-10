package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseConclusioneEntry;

public class ResponseFaseConclusioneContratto extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase conclusione contratto")
	FaseConclusioneEntry data;
	
	public FaseConclusioneEntry getData() {
		return data;
	}

	public void setData(FaseConclusioneEntry data) {
		this.data = data;
	}

}
