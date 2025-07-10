package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseConclusioneSempEntry;

public class ResponseFaseConclusioneSempContratto extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase conclusione semplificata contratto")
	FaseConclusioneSempEntry data;
	
	public FaseConclusioneSempEntry getData() {
		return data;
	}

	public void setData(FaseConclusioneSempEntry data) {
		this.data = data;
	}

}
