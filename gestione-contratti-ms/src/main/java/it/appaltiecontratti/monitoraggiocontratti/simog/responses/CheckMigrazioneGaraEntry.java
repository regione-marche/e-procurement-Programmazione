package it.appaltiecontratti.monitoraggiocontratti.simog.responses;

import java.io.Serializable;

import it.appaltiecontratti.monitoraggiocontratti.simog.beans.SABaseEntry;

public class CheckMigrazioneGaraEntry implements Serializable {

	private static final long serialVersionUID = -2347783747677639307L;
	
	private SABaseEntry stazioneAppaltante;
	private String codiceFiscaleRupGara;

	public SABaseEntry getStazioneAppaltante() {
		return stazioneAppaltante;
	}

	public void setStazioneAppaltante(SABaseEntry stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}

	public String getCodiceFiscaleRupGara() {
		return codiceFiscaleRupGara;
	}

	public void setCodiceFiscaleRupGara(String codiceFiscaleRupGara) {
		this.codiceFiscaleRupGara = codiceFiscaleRupGara;
	}

}
