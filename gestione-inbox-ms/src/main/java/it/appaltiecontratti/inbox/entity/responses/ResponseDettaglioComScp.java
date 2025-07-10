package it.appaltiecontratti.inbox.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.inbox.entity.ComScpEntry;

public class ResponseDettaglioComScp extends BaseResponse{

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati base delle comunicazioni di scp")
	private ComScpEntry data;

	

	public ComScpEntry getData() {
		return data;
	}

	public void setData(ComScpEntry data) {
		this.data = data;
	}


	
	
}
