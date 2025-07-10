package it.appaltiecontratti.autenticazione.entity;

public class OggettoEntry {

    private String tipo;
    private String oggetto;
    private String descr;
    private Long ord;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getOrd() {
        return ord;
    }

    public void setOrd(Long ord) {
        this.ord = ord;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }
}
