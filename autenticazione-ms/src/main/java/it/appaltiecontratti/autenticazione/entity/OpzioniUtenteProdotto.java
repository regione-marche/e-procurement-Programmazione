package it.appaltiecontratti.autenticazione.entity;

import java.io.Serializable;
import java.util.List;

public class OpzioniUtenteProdotto implements Serializable {

	private static final long serialVersionUID = -8215886773497024513L;

	private List<String> ou;
	private List<String> op;

	public List<String> getOu() {
		return ou;
	}

	public void setOu(List<String> ou) {
		this.ou = ou;
	}

	public List<String> getOp() {
		return op;
	}

	public void setOp(List<String> op) {
		this.op = op;
	}

}
