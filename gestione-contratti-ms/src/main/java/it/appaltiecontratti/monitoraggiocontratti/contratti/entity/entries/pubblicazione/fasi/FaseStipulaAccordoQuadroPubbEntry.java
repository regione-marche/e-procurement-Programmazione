package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi;

import java.util.Date;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseStipulaPubbEsitoForm;

public class FaseStipulaAccordoQuadroPubbEntry extends BaseFasePubblicazioneEntry {

	public Date dataStipula;
	public Date dataDecorrenza;
	public Date dataScadenza;
	FaseStipulaPubbEsitoForm pubblicazioneEsito;
	private Long numAppa;

	public Date getDataStipula() {
		return dataStipula;
	}

	public void setDataStipula(Date dataStipula) {
		this.dataStipula = dataStipula;
	}

	public FaseStipulaPubbEsitoForm getPubblicazioneEsito() {
		return pubblicazioneEsito;
	}

	public void setPubblicazioneEsito(FaseStipulaPubbEsitoForm pubblicazioneEsito) {
		this.pubblicazioneEsito = pubblicazioneEsito;
	}

	public Date getDataDecorrenza() {
		return dataDecorrenza;
	}

	public void setDataDecorrenza(Date dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public Long getNumAppa() {
		return numAppa;
	}

	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}

}
