package it.appaltiecontratti.inbox.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.inbox.entity.FlussoEntry;

public class ResponseDettaglioFlusso extends BaseResponse{

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati base dei flussi")
	private FlussoEntry data;

	public FlussoEntry getData() {
		return data;
	}

	public void setData(FlussoEntry data) {
		this.data = data;
	}
	
	
}
