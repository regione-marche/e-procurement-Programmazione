package it.appaltiecontratti.autenticazione.entity;

import java.io.Serializable;

public class WGenChiavi implements Serializable {

	private static final long serialVersionUID = 8339624839752138166L;

	private String tabella;
	private Long chiave;

	public String getTabella() {
		return tabella;
	}

	public void setTabella(String tabella) {
		this.tabella = tabella;
	}

	public Long getChiave() {
		return chiave;
	}

	public void setChiave(Long chiave) {
		this.chiave = chiave;
	}

}
