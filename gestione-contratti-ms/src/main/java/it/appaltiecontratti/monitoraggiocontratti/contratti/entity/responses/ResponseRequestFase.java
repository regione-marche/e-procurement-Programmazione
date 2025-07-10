package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class ResponseRequestFase extends BaseResponse{
	
	private static final long serialVersionUID = 4156845043725569681L;

	private String data;

	@JsonRawValue
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
