package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseStipulaAccordoQuadroEntry;

public class ResponseFaseStipulaAccordoQuadro extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase stipula accordo quadro di esecuzione")
    FaseStipulaAccordoQuadroEntry data;
	
	public FaseStipulaAccordoQuadroEntry getData() {
		return data;
	}

	public void setData(FaseStipulaAccordoQuadroEntry data) {
		this.data = data;
	}
	
}