package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.PubblicaSmartcigResult;

public class ResponsePubblicaSmartCig extends BaseResponse{
	
	private static final long serialVersionUID = 1L;
	
	PubblicaSmartcigResult data;

	public PubblicaSmartcigResult getData() {
		return data;
	}

	public void setData(PubblicaSmartcigResult data) {
		this.data = data;
	}

	
}
