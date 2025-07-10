package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.InizFaseCantieriEntry;

public class ResponseInizFaseCantieri extends BaseResponse {

	private static final long serialVersionUID = -8189095366647691858L;
	
	private InizFaseCantieriEntry data;

	public InizFaseCantieriEntry getData() {
		return data;
	}

	public void setData(InizFaseCantieriEntry data) {
		this.data = data;
	}

}
