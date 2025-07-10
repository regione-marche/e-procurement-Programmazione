package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.PubblicaAttoEntry;

public class ResponseRequestPubblicazioneAtti extends BaseResponse {

	
	private static final long serialVersionUID = 1916750307293411625L;
	
	private PubblicaAttoEntry data;

	public PubblicaAttoEntry getData() {
		return data;
	}

	public void setData(PubblicaAttoEntry data) {
		this.data = data;
	}
}
