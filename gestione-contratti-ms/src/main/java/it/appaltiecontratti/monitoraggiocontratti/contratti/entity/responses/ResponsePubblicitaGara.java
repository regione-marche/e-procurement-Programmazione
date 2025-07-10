package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.PubblicitaGaraEntry;

public class ResponsePubblicitaGara extends BaseResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio pubblicità di gara")
	PubblicitaGaraEntry data;
	
	public PubblicitaGaraEntry getData() {
		return data;
	}

	public void setData(PubblicitaGaraEntry data) {
		this.data = data;
	}
}