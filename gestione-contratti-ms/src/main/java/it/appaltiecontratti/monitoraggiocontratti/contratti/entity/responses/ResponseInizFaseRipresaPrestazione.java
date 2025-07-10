package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.InizFaseRipresaPrestazioneEntry;

public class ResponseInizFaseRipresaPrestazione extends BaseResponse{
	
	private static final long serialVersionUID = -5804777992848156114L;

	private InizFaseRipresaPrestazioneEntry data;

	public InizFaseRipresaPrestazioneEntry getData() {
		return data;
	}

	public void setData(InizFaseRipresaPrestazioneEntry data) {
		this.data = data;
	}
	
}
