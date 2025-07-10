package it.appaltiecontratti.monitoraggiocontratti.sicraweb.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiToken {

    @JsonProperty("utente")
    private String utente;

    @JsonProperty("maggioli-api-token")
    private String maggioliApiToken;

    @JsonProperty("ente")
    private String ente;

    // Getters and Setters
    public String getUtente() {
        return utente;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public String getMaggioliApiToken() {
        return maggioliApiToken;
    }

    public void setMaggioliApiToken(String maggioliApiToken) {
        this.maggioliApiToken = maggioliApiToken;
    }

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }
}