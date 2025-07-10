package it.appaltiecontratti.programmi.entity;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since lug 06, 2023
 */
public class InterventiDaConfrontoDTO {
    private String cui;
    private String descrizione;
    private EInterventiDaConfrontoVariazione variazione;
    private String descrizioneTabellato;
    private boolean warningVariatoNull;

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public EInterventiDaConfrontoVariazione getVariazione() {
        return variazione;
    }

    public void setVariazione(EInterventiDaConfrontoVariazione variazione) {
        this.variazione = variazione;
    }

    public String getDescrizioneTabellato() {
        return descrizioneTabellato;
    }

    public void setDescrizioneTabellato(String descrizioneTabellato) {
        this.descrizioneTabellato = descrizioneTabellato;
    }

    public boolean isWarningVariatoNull() {
        return warningVariatoNull;
    }

    public void setWarningVariatoNull(boolean warningVariatoNull) {
        this.warningVariatoNull = warningVariatoNull;
    }

    @Override
    public String toString() {
        return "InterventiDaConfrontoDTO{" +
                "cui='" + cui + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", variazione=" + variazione +
                ", descrizioneTabellato='" + descrizioneTabellato + '\'' +
                ", warningVariatoNull=" + warningVariatoNull +
                '}';
    }
}
