package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.AccorpaMultilottoEntry;

public class ResponseAccorpaMultilotto extends BaseResponse implements Serializable {

	private static final long serialVersionUID = -7205349627694977356L;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Dati dei lotti")
	private AccorpaMultilottoEntry data;

	public AccorpaMultilottoEntry getData() {
		return data;
	}

	public void setData(AccorpaMultilottoEntry data) {
		this.data = data;
	}
}
