package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.DettaglioAttoEntry;

public class ResponseDettaglioAtto   extends BaseResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio atto")
	DettaglioAttoEntry data;
	
	public DettaglioAttoEntry getData() {
		return data;
	}

	public void setData(DettaglioAttoEntry data) {
		this.data = data;
	}
}