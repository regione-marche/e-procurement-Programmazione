package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.InizFaseAggiudicazioneSemp;

public class ResponseInizFaseAggiudicazioneSemp extends BaseResponse{
	
	private static final long serialVersionUID = 1L;
	
	private InizFaseAggiudicazioneSemp data;

	public InizFaseAggiudicazioneSemp getData() {
		return data;
	}

	public void setData(InizFaseAggiudicazioneSemp data) {
		this.data = data;
	}
	
	
}
