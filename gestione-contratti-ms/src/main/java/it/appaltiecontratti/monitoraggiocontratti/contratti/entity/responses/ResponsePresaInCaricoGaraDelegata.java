package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;

public class ResponsePresaInCaricoGaraDelegata extends BaseResponse implements Serializable {
	
	private static final long serialVersionUID = -8439083438998575866L;
	
	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
