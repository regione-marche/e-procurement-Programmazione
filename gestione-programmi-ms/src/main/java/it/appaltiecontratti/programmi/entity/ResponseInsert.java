package it.appaltiecontratti.programmi.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ResponseInsert extends BaseResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Id del programma creato")
	private Long data;
	
	public Long getData() {
		return data;
	}

	public void setData(Long data) {
		this.data = data;
	}
	
}
