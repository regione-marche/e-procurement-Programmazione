package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.v100.BaseResponsePcp;

public class ResponseRiallineaAnac extends BaseResponsePcp{
	private boolean allineato;
	private boolean schedaAnn;

	public boolean isAllineato() {
		return allineato;
	}

	public void setAllineato(boolean allineato) {
		this.allineato = allineato;
	}

	public boolean isSchedaAnn() {
		return schedaAnn;
	}

	public void setSchedaAnn(boolean schedaAnn) {
		this.schedaAnn = schedaAnn;
	}
}
