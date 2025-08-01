package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseInizialeEsecuzioneEntry;

public class ResponseFaseInizialeEsecuzione extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase iniziale di esecuzione")
    FaseInizialeEsecuzioneEntry data;
	
	public FaseInizialeEsecuzioneEntry getData() {
		return data;
	}

	public void setData(FaseInizialeEsecuzioneEntry data) {
		this.data = data;
	}
	
}
