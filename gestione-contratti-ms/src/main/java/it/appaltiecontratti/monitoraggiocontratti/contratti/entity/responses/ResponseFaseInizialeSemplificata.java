package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseInizialeSemplificataEntry;

public class ResponseFaseInizialeSemplificata extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase iniziale semplificata")
    FaseInizialeSemplificataEntry data;
	
	public FaseInizialeSemplificataEntry getData() {
		return data;
	}

	public void setData(FaseInizialeSemplificataEntry data) {
		this.data = data;
	}
	
}
