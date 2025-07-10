package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.AttiPubblicatiEntry;

public class ResponsePubbAtti extends BaseResponse{

	public static final String PUBBLICAZIONI_NO_ATTI = "PUBBLICAZIONI-NO-ATTI";
	
	private List<AttiPubblicatiEntry> data;
	
	private boolean esito;

	public List<AttiPubblicatiEntry> getData() {
		return data;
	}

	public void setData(List<AttiPubblicatiEntry> data) {
		this.data = data;
	}

	public boolean isEsito() {
		return esito;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
	}
	
	
}
