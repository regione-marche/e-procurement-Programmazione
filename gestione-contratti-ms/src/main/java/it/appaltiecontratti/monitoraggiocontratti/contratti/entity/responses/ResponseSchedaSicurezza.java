package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.SchedaSicurezzaEntry;

public class ResponseSchedaSicurezza extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="dati della scheda sicurezza")
	SchedaSicurezzaEntry data;

	public SchedaSicurezzaEntry getData() {
		return data;
	}

	public void setData(SchedaSicurezzaEntry data) {
		this.data = data;
	}
	
}
