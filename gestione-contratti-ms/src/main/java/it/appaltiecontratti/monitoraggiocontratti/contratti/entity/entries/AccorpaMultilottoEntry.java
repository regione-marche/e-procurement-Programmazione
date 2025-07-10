package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.List;

public class AccorpaMultilottoEntry {

	private boolean anagraficaOk;
	
	private boolean aggiudicazioniOk;
	
	private List<String> cigInvalidi;

	public boolean isAnagraficaOk() {
		return anagraficaOk;
	}

	public void setAnagraficaOk(boolean anagraficaOk) {
		this.anagraficaOk = anagraficaOk;
	}

	public boolean isAggiudicazioniOk() {
		return aggiudicazioniOk;
	}

	public void setAggiudicazioniOk(boolean aggiudicazioniOk) {
		this.aggiudicazioniOk = aggiudicazioniOk;
	}

	public List<String> getCigInvalidi() {
		return cigInvalidi;
	}

	public void setCigInvalidi(List<String> cigInvalidi) {
		this.cigInvalidi = cigInvalidi;
	}
	
	
	
}
