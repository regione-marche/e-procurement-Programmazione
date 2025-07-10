package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseComunicazioneEntry;

public class ResponseFaseComunicazione extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase comunicazione")
	FaseComunicazioneEntry data;
	
	public FaseComunicazioneEntry getData() {
		return data;
	}

	public void setData(FaseComunicazioneEntry data) {
		this.data = data;
	}
	
}