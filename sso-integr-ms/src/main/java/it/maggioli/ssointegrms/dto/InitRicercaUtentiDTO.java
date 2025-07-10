package it.maggioli.ssointegrms.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since mar 13, 2024
 */
public class InitRicercaUtentiDTO implements Serializable {
    private static final long serialVersionUID = -2527079379762700604L;

    private boolean utenteDelegatoGestioneUtenti;
    private boolean registrazioneLoginCF;
    private List<String> stazioniAppaltantiAssociate;
    private List<String> profiliAssociati;

    public boolean isUtenteDelegatoGestioneUtenti() {
        return utenteDelegatoGestioneUtenti;
    }

    public void setUtenteDelegatoGestioneUtenti(boolean utenteDelegatoGestioneUtenti) {
        this.utenteDelegatoGestioneUtenti = utenteDelegatoGestioneUtenti;
    }

    public boolean isRegistrazioneLoginCF() {
        return registrazioneLoginCF;
    }

    public void setRegistrazioneLoginCF(boolean registrazioneLoginCF) {
        this.registrazioneLoginCF = registrazioneLoginCF;
    }

    public List<String> getStazioniAppaltantiAssociate() {
        return stazioniAppaltantiAssociate;
    }

    public void setStazioniAppaltantiAssociate(List<String> stazioniAppaltantiAssociate) {
        this.stazioniAppaltantiAssociate = stazioniAppaltantiAssociate;
    }

    public List<String> getProfiliAssociati() {
        return profiliAssociati;
    }

    public void setProfiliAssociati(List<String> profiliAssociati) {
        this.profiliAssociati = profiliAssociati;
    }
}
