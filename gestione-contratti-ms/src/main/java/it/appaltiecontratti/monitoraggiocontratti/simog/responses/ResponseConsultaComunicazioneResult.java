package it.appaltiecontratti.monitoraggiocontratti.simog.responses;

import java.io.Serializable;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.ComunicazioneType;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;

public class ResponseConsultaComunicazioneResult extends BaseResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private ComunicazioneType comunicazione;

	public ComunicazioneType getComunicazione() {
		return comunicazione;
	}

	public void setComunicazione(ComunicazioneType comunicazione) {
		this.comunicazione = comunicazione;
	}

}
