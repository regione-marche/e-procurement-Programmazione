package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseComEsitoForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseComPubbEsitoForm;

public class FaseComunicazioneEntry {

	FaseComEsitoForm esito;
	FaseComPubbEsitoForm pubblicazioneEsito;
	private boolean pubblicata;
	private boolean hasAggiudicazione;
	
	public boolean isPubblicata() {
		return pubblicata;
	}
	public void setPubblicata(boolean pubblicata) {
		this.pubblicata = pubblicata;
	}
	public FaseComEsitoForm getEsito() {
		return esito;
	}
	public void setEsito(FaseComEsitoForm esito) {
		this.esito = esito;
	}
	public FaseComPubbEsitoForm getPubblicazioneEsito() {
		return pubblicazioneEsito;
	}
	public void setPubblicazioneEsito(FaseComPubbEsitoForm pubblicazioneEsito) {
		this.pubblicazioneEsito = pubblicazioneEsito;
	}
	public boolean isHasAggiudicazione() {
		return hasAggiudicazione;
	}
	public void setHasAggiudicazione(boolean hasAggiudicazione) {
		this.hasAggiudicazione = hasAggiudicazione;
	}
	
	
}
