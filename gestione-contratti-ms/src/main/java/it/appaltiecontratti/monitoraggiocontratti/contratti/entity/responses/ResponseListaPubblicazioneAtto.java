package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FlussiAtto;

public class ResponseListaPubblicazioneAtto  extends BaseResponse{

	private static final long serialVersionUID = 1L;

	private List<FlussiAtto> data;

	public List<FlussiAtto> getData() {
		return data;
	}

	public void setData(List<FlussiAtto> data) {
		this.data = data;
	}
	
	
	
}