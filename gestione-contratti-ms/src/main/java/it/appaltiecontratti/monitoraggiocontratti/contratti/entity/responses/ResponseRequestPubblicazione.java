package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.PubblicaGaraEntry;

public class ResponseRequestPubblicazione extends BaseResponse{

	private static final long serialVersionUID = 8718877317004420319L;

	private PubblicaGaraEntry data;

	public PubblicaGaraEntry getData() {
		return data;
	}

	public void setData(PubblicaGaraEntry data) {
		this.data = data;
	}
	
}
