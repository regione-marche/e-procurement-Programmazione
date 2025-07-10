package it.appaltiecontratti.monitoraggiocontratti.simog.responses;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.RupEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.v100.BaseResponsePcp;

public class ResponseInfoDelegati extends BaseResponsePcp{

	private static final long serialVersionUID = -1907563612043101673L;

	private RupEntry delegato;

	public RupEntry getDelegato() {
		return delegato;
	}

	public void setDelegato(RupEntry delegato) {
		this.delegato = delegato;
	}

	

}
