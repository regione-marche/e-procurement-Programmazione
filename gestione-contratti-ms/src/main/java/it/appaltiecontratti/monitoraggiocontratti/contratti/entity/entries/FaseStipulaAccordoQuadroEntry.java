package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseStipulaPubbEsitoForm;

public class FaseStipulaAccordoQuadroEntry {
	
	public Long codGara;
	public Long codLotto;
	public Date dataStipula;
	public Date dataDecorrenza;
	public Date dataScadenza;
	FaseStipulaPubbEsitoForm pubblicazioneEsito;
	private boolean pubblicata;
	
	public boolean isPubblicata() {
		return pubblicata;
	}
	public void setPubblicata(boolean pubblicata) {
		this.pubblicata = pubblicata;
	}
	public Long getCodGara() {
		return codGara;
	}
	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}
	public Long getCodLotto() {
		return codLotto;
	}
	public void setCodLotto(Long codLotto) {
		this.codLotto = codLotto;
	}
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
	
}
