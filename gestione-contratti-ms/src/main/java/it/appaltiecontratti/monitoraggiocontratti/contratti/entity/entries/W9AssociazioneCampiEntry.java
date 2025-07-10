package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.W9AssociazioneCampi;

public class W9AssociazioneCampiEntry extends W9AssociazioneCampi {

	private String faseDesc;
	private String nomeCampoDesc;

	public String getFaseDesc() {
		return faseDesc;
	}

	public void setFaseDesc(String faseDesc) {
		this.faseDesc = faseDesc;
	}

	public String getNomeCampoDesc() {
		return nomeCampoDesc;
	}

	public void setNomeCampoDesc(String nomeCampoDesc) {
		this.nomeCampoDesc = nomeCampoDesc;
	}

}
