package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAccordoBonarioEntry;

public class ResponseFaseAccordoBonario extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase accordo bonario")
    FaseAccordoBonarioEntry data;
	
	public FaseAccordoBonarioEntry getData() {
		return data;
	}

	public void setData(FaseAccordoBonarioEntry data) {
		this.data = data;
	}
	
}
