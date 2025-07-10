package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseSospensioneEntry;

public class ResponseFaseSospensione extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase sospensione")
	FaseSospensioneEntry data;
	
	public FaseSospensioneEntry getData() {
		return data;
	}

	public void setData(FaseSospensioneEntry data) {
		this.data = data;
	}
	
}