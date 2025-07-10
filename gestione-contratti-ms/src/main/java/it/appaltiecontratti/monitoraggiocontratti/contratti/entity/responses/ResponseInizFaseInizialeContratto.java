package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.InizFaseInizialeContrattoEntry;

public class ResponseInizFaseInizialeContratto extends BaseResponse{
	
	private static final long serialVersionUID = -5804777992848156114L;

	private InizFaseInizialeContrattoEntry data;

	public InizFaseInizialeContrattoEntry getData() {
		return data;
	}

	public void setData(InizFaseInizialeContrattoEntry data) {
		this.data = data;
	}
	
}
