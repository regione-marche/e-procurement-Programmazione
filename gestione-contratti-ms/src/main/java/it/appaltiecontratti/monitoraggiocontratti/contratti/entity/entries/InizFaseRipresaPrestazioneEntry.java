package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.List;

public class InizFaseRipresaPrestazioneEntry {

	private List<FaseSospensioneEntry> listaSosp;

	public List<FaseSospensioneEntry> getListaSosp() {
		return listaSosp;
	}

	public void setListaSosp(List<FaseSospensioneEntry> listaSosp) {
		this.listaSosp = listaSosp;
	}

}
