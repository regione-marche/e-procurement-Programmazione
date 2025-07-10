package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAdesioneAccordoQuadroEntry;

public class ResponseFaseAdesioneAccordoQuadro extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase adesione accordo quadro")
    FaseAdesioneAccordoQuadroEntry data;
	
	public FaseAdesioneAccordoQuadroEntry getData() {
		return data;
	}

	public void setData(FaseAdesioneAccordoQuadroEntry data) {
		this.data = data;
	}
	
}
