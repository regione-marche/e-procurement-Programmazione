package it.appaltiecontratti.programmi.rl.dto;

import java.io.Serializable;

public class AcquistoNonRipropostoDto implements Serializable {

    private String cui;
    private String motivo;

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        return "AcquistoNonRipropostoDto{" +
                "cui='" + cui + '\'' +
                ", motivo='" + motivo + '\'' +
                '}';
    }
}