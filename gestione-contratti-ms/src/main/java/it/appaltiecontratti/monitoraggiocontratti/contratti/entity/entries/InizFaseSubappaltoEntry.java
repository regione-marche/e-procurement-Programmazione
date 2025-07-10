package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.CPVSecondario;

public class InizFaseSubappaltoEntry {

	private boolean singolaImpresa;
	
	private List<CPVSecondario> listaCpv;
	
	private List<FaseSubappaltoEntry> listaSuba;

	public boolean isSingolaImpresa() {
		return singolaImpresa;
	}

	public void setSingolaImpresa(boolean singolaImpresa) {
		this.singolaImpresa = singolaImpresa;
	}

	public List<CPVSecondario> getListaCpv() {
		return listaCpv;
	}

	public void setListaCpv(List<CPVSecondario> listaCpv) {
		this.listaCpv = listaCpv;
	}

	public List<FaseSubappaltoEntry> getListaSuba() {
		return listaSuba;
	}

	public void setListaSuba(List<FaseSubappaltoEntry> listaSuba) {
		this.listaSuba = listaSuba;
	}

	

}
