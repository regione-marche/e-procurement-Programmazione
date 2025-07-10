package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseSubappaltoEntry;

public class ResponseFaseSubappalto  extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase subappalto")
    FaseSubappaltoEntry data;
	
	public FaseSubappaltoEntry getData() {
		return data;
	}

	public void setData(FaseSubappaltoEntry	 data) {
		this.data = data;
	}
	
}
