package it.appaltiecontratti.programmi.entity;

import java.math.BigDecimal;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since lug 06, 2023
 */
public class InterventiDaConfrontoQueryResult {

    private Long contri;
    private String cui;
    private Long tipoProgramma;
    private Long annualita;
    private String descrizione;
    private BigDecimal costiTotale;
    private Long variato;

    public Long getContri() {
        return contri;
    }

    public void setContri(Long contri) {
        this.contri = contri;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public Long getTipoProgramma() {
        return tipoProgramma;
    }

    public void setTipoProgramma(Long tipoProgramma) {
        this.tipoProgramma = tipoProgramma;
    }

    public Long getAnnualita() {
        return annualita;
    }

    public void setAnnualita(Long annualita) {
        this.annualita = annualita;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public BigDecimal getCostiTotale() {
        return costiTotale;
    }

    public void setCostiTotale(BigDecimal costiTotale) {
        this.costiTotale = costiTotale;
    }

    public Long getVariato() {
        return variato;
    }

    public void setVariato(Long variato) {
        this.variato = variato;
    }

    @Override
    public String toString() {
        return "InterventiDaConfrontoQueryResult{" +
                "contri=" + contri +
                ", cui='" + cui + '\'' +
                ", tipoProgramma=" + tipoProgramma +
                ", annualita=" + annualita +
                ", descrizione='" + descrizione + '\'' +
                ", costiTotale=" + costiTotale +
                ", variato=" + variato +
                '}';
    }
}
