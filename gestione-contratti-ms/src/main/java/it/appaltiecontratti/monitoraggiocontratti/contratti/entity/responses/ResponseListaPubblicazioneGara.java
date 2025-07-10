package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FlussiGara;

public class ResponseListaPubblicazioneGara extends BaseResponse{

	private static final long serialVersionUID = 1L;
	
	private List<FlussiGara> data;
	

	public List<FlussiGara> getData() {
		return data;
	}

	public void setData(List<FlussiGara> data) {
		this.data = data;
	}
}
