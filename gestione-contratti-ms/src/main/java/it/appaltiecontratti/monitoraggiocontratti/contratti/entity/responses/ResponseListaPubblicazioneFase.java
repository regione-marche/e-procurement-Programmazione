package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FullFlussiFase;

public class ResponseListaPubblicazioneFase extends BaseResponse{

	private static final long serialVersionUID = 1L;

	private List<FullFlussiFase> data;

	public List<FullFlussiFase> getData() {
		return data;
	}

	public void setData(List<FullFlussiFase> data) {
		this.data = data;
	}
	
	
	
}
