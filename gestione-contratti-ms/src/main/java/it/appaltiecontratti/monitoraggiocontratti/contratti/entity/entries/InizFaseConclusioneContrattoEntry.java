package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

public class InizFaseConclusioneContrattoEntry {

	private Date dataVerbale;
	private Date dataTermine;
	private Long numGiorniProroga;
	private boolean faseInizialeExists;

	public Date getDataVerbale() {
		return dataVerbale;
	}

	public void setDataVerbale(Date dataVerbale) {
		this.dataVerbale = dataVerbale;
	}

	public Date getDataTermine() {
		return dataTermine;
	}

	public void setDataTermine(Date dataTermine) {
		this.dataTermine = dataTermine;
	}

	public Long getNumGiorniProroga() {
		return numGiorniProroga;
	}

	public void setNumGiorniProroga(Long numGiorniProroga) {
		this.numGiorniProroga = numGiorniProroga;
	}

	public boolean isFaseInizialeExists() {
		return faseInizialeExists;
	}

	public void setFaseInizialeExists(boolean faseInizialeExists) {
		this.faseInizialeExists = faseInizialeExists;
	}

}
