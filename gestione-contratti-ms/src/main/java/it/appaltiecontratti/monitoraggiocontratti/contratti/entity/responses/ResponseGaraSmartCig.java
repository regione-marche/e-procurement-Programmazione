package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponseGaraSmartCig extends BaseResponse implements Serializable{
	
	private static final long serialVersionUID = -5580115533778541598L;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Gara SmartCIG")
    private boolean data;

	public boolean isData() {
		return data;
	}

	public void setData(boolean data) {
		this.data = data;
	}

}
