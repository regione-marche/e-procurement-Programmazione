package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAggiudicazioneEntry;

public class ResponseFaseAggiudicazione extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase aggiudicazione")
    FaseAggiudicazioneEntry data;
	
	public FaseAggiudicazioneEntry getData() {
		return data;
	}

	public void setData(FaseAggiudicazioneEntry data) {
		this.data = data;
	}
	
}
