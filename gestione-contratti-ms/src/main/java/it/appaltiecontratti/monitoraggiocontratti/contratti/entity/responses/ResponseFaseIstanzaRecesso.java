package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseIstanzaRecessoEntry;

public class ResponseFaseIstanzaRecesso extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase istanza recesso")
	FaseIstanzaRecessoEntry data;
	
	public FaseIstanzaRecessoEntry getData() {
		return data;
	}

	public void setData(FaseIstanzaRecessoEntry data) {
		this.data = data;
	}
	
}