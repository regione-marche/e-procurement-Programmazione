package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponseUpdateRupGara extends BaseResponse implements Serializable {

	private static final long serialVersionUID = -1559734366064182532L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Risposta dell'update RUP della gara")
	private boolean data;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Eventuale messaggio di errore")
	private String errorMessage;

	public boolean getData() {
		return data;
	}

	public void setData(boolean data) {
		this.data = data;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
