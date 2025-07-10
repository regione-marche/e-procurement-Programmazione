package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.InizFaseAggiudicazione;

public class ResponseInizFaseAggiudicazione extends BaseResponse{
	
	
	private static final long serialVersionUID = 1L;
	
	private InizFaseAggiudicazione data;

	public InizFaseAggiudicazione getData() {
		return data;
	}

	public void setData(InizFaseAggiudicazione data) {
		this.data = data;
	}
	
	
}
