package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.InizFaseConclusioneContrattoEntry;

public class ResponseInizFaseConclusioneContratto extends BaseResponse{
	
	private static final long serialVersionUID = -5804777992848156114L;

	private InizFaseConclusioneContrattoEntry data;

	public InizFaseConclusioneContrattoEntry getData() {
		return data;
	}

	public void setData(InizFaseConclusioneContrattoEntry data) {
		this.data = data;
	}
	
}
