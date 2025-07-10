package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.PubblicaSmartCigEntry;

public class ResponseRequestPubblicazioneSmartCig extends BaseResponse{


	private static final long serialVersionUID = 1L;
	
	private PubblicaSmartCigEntry data;

	public PubblicaSmartCigEntry getData() {
		return data;
	}

	public void setData(PubblicaSmartCigEntry data) {
		this.data = data;
	}
	
	
}
