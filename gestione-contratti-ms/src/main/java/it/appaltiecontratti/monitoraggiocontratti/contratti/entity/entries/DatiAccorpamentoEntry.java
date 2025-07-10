package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.List;

public class DatiAccorpamentoEntry {
	
	private List<AccorpamentoEntry> accorpamenti;
	
	private Boolean accorpamentiDisponibili;

	public List<AccorpamentoEntry> getAccorpamenti() {
		return accorpamenti;
	}

	public void setAccorpamenti(List<AccorpamentoEntry> accorpamenti) {
		this.accorpamenti = accorpamenti;
	}

	public Boolean getAccorpamentiDisponibili() {
		return accorpamentiDisponibili;
	}

	public void setAccorpamentiDisponibili(Boolean accorpamentiDisponibili) {
		this.accorpamentiDisponibili = accorpamentiDisponibili;
	}
	
	

}
