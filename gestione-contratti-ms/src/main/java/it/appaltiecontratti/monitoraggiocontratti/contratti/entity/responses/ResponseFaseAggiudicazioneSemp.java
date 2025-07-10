package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAggiudicazioneSempEntry;

public class ResponseFaseAggiudicazioneSemp  extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase aggiudicazione semplificata")
    FaseAggiudicazioneSempEntry data;
	
	public FaseAggiudicazioneSempEntry getData() {
		return data;
	}

	public void setData(FaseAggiudicazioneSempEntry data) {
		this.data = data;
	}
	
}