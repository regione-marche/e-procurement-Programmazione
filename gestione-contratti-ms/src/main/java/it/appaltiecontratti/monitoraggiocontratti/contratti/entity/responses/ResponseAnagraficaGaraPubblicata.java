package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponseAnagraficaGaraPubblicata extends BaseResponse implements Serializable {

	private static final long serialVersionUID = -876534311787026711L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Anagrafica gara pubblicata")
	private Boolean data;

	public Boolean getData() {
		return data;
	}

	public void setData(Boolean data) {
		this.data = data;
	}

}
