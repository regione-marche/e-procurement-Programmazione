package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

public class InizFaseInizialeContrattoEntry {

	private boolean incontriVigilVisible;
	private Date dataVerbale;

	public Date getDataVerbale() {
		return dataVerbale;
	}

	public void setDataVerbale(Date dataVerbale) {
		this.dataVerbale = dataVerbale;
	}

	public boolean isIncontriVigilVisible() {
		return incontriVigilVisible;
	}

	public void setIncontriVigilVisible(boolean incontriVigilVisible) {
		this.incontriVigilVisible = incontriVigilVisible;
	}

}
