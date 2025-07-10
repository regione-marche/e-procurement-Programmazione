package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseCantieriEntry;

public class ResponseFaseCantieri  extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase cantieri")
	FaseCantieriEntry data;
	
	public FaseCantieriEntry getData() {
		return data;
	}

	public void setData(FaseCantieriEntry data) {
		this.data = data;
	}
}
