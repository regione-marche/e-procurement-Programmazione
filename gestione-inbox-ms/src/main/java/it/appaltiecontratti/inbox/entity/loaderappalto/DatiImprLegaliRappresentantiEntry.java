package it.appaltiecontratti.inbox.entity.loaderappalto;

import java.sql.Timestamp;

public class DatiImprLegaliRappresentantiEntry {

	private String cogtim;
	private String nometim;
	private String cfRappresentante;
	private Timestamp legfin;

	public String getCogtim() {
		return cogtim;
	}

	public void setCogtim(String cogtim) {
		this.cogtim = cogtim;
	}

	public String getNometim() {
		return nometim;
	}

	public void setNometim(String nometim) {
		this.nometim = nometim;
	}

	public String getCfRappresentante() {
		return cfRappresentante;
	}

	public void setCfRappresentante(String cfRappresentante) {
		this.cfRappresentante = cfRappresentante;
	}

	public Timestamp getLegfin() {
		return legfin;
	}

	public void setLegfin(Timestamp legfin) {
		this.legfin = legfin;
	}

}
