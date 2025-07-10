package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponseInsert extends BaseResponse{
	
	private static final long serialVersionUID = 1L;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Id generato da restituire al client")
	private Long data;

	public Long getData() {
		return data;
	}

	public void setData(Long data) {
		this.data = data;
	}
	
	

}
