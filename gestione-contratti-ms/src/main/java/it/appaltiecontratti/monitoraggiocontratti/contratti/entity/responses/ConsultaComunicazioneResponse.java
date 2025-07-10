package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.ComunicazioneType;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.RisultatoType;

public class ConsultaComunicazioneResponse extends BaseResponse implements Serializable {
    private ComunicazioneType comunicazione;

    private RisultatoType codiceRisultato;

    public ConsultaComunicazioneResponse() {
    }

    public ConsultaComunicazioneResponse(
           ComunicazioneType comunicazione,
           RisultatoType codiceRisultato) {
           this.comunicazione = comunicazione;
           this.codiceRisultato = codiceRisultato;
    }


    /**
     * Gets the comunicazione value for this ConsultaComunicazioneResponse.
     * 
     * @return comunicazione
     */
    public ComunicazioneType getComunicazione() {
        return comunicazione;
    }


    /**
     * Sets the comunicazione value for this ConsultaComunicazioneResponse.
     * 
     * @param comunicazione
     */
    public void setComunicazione(ComunicazioneType comunicazione) {
        this.comunicazione = comunicazione;
    }


    /**
     * Gets the codiceRisultato value for this ConsultaComunicazioneResponse.
     * 
     * @return codiceRisultato
     */
    public RisultatoType getCodiceRisultato() {
        return codiceRisultato;
    }


    /**
     * Sets the codiceRisultato value for this ConsultaComunicazioneResponse.
     * 
     * @param codiceRisultato
     */
    public void setCodiceRisultato(RisultatoType codiceRisultato) {
        this.codiceRisultato = codiceRisultato;
    }

}
