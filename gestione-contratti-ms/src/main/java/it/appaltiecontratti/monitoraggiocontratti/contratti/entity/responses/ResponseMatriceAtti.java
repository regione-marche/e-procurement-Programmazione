package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.MatriceAtti;

public class ResponseMatriceAtti extends  BaseResponse {

	private static final long serialVersionUID = 1547486693947955533L;

	private MatriceAtti data;

	public MatriceAtti getData() {
		return data;
	}

	public void setData(MatriceAtti data) {
		this.data = data;
	}
	
}
