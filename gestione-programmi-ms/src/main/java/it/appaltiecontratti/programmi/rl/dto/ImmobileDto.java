package it.appaltiecontratti.programmi.rl.dto;

import java.io.Serializable;

public class ImmobileDto implements Serializable {

    private String cui;
    private String cuiIntervento;
    private String cupRiferimento;
    private String descrizioneImmobile;
    private String cdRegione;
    private String siglaProvincia;
    private String cdCatastale;
    private String codiceNuts;
    private Long idCessione;
    private Long idConcessione;
    private String flAlienato;
    private Long idDismissione;
    private Long idDisponibilita;
    private Long importoAnno1;
    private Long importoAnno2;
    private Long importoAnno3;
    private Long importoAnnoSuccessivo;

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getCuiIntervento() {
        return cuiIntervento;
    }

    public void setCuiIntervento(String cuiIntervento) {
        this.cuiIntervento = cuiIntervento;
    }

    public String getCupRiferimento() {
        return cupRiferimento;
    }

    public void setCupRiferimento(String cupRiferimento) {
        this.cupRiferimento = cupRiferimento;
    }

    public String getDescrizioneImmobile() {
        return descrizioneImmobile;
    }

    public void setDescrizioneImmobile(String descrizioneImmobile) {
        this.descrizioneImmobile = descrizioneImmobile;
    }

    public String getCdRegione() {
        return cdRegione;
    }

    public void setCdRegione(String cdRegione) {
        this.cdRegione = cdRegione;
    }

    public String getSiglaProvincia() {
        return siglaProvincia;
    }

    public void setSiglaProvincia(String siglaProvincia) {
        this.siglaProvincia = siglaProvincia;
    }

    public String getCdCatastale() {
        return cdCatastale;
    }

    public void setCdCatastale(String cdCatastale) {
        this.cdCatastale = cdCatastale;
    }

    public String getCodiceNuts() {
        return codiceNuts;
    }

    public void setCodiceNuts(String codiceNuts) {
        this.codiceNuts = codiceNuts;
    }

    public Long getIdCessione() {
        return idCessione;
    }

    public void setIdCessione(Long idCessione) {
        this.idCessione = idCessione;
    }

    public Long getIdConcessione() {
        return idConcessione;
    }

    public void setIdConcessione(Long idConcessione) {
        this.idConcessione = idConcessione;
    }

    public String getFlAlienato() {
        return flAlienato;
    }

    public void setFlAlienato(String flAlienato) {
        this.flAlienato = flAlienato;
    }

    public Long getIdDismissione() {
        return idDismissione;
    }

    public void setIdDismissione(Long idDismissione) {
        this.idDismissione = idDismissione;
    }

    public Long getIdDisponibilita() {
        return idDisponibilita;
    }

    public void setIdDisponibilita(Long idDisponibilita) {
        this.idDisponibilita = idDisponibilita;
    }

    public Long getImportoAnno1() {
        return importoAnno1;
    }

    public void setImportoAnno1(Long importoAnno1) {
        this.importoAnno1 = importoAnno1;
    }

    public Long getImportoAnno2() {
        return importoAnno2;
    }

    public void setImportoAnno2(Long importoAnno2) {
        this.importoAnno2 = importoAnno2;
    }

    public Long getImportoAnno3() {
        return importoAnno3;
    }

    public void setImportoAnno3(Long importoAnno3) {
        this.importoAnno3 = importoAnno3;
    }

    public Long getImportoAnnoSuccessivo() {
        return importoAnnoSuccessivo;
    }

    public void setImportoAnnoSuccessivo(Long importoAnnoSuccessivo) {
        this.importoAnnoSuccessivo = importoAnnoSuccessivo;
    }

    @Override
    public String toString() {
        return "ImmobileDto{" +
                "cui='" + cui + '\'' +
                ", cuiIntervento='" + cuiIntervento + '\'' +
                ", cupRiferimento='" + cupRiferimento + '\'' +
                ", descrizioneImmobile='" + descrizioneImmobile + '\'' +
                ", cdRegione='" + cdRegione + '\'' +
                ", siglaProvincia='" + siglaProvincia + '\'' +
                ", cdCatastale='" + cdCatastale + '\'' +
                ", codiceNuts='" + codiceNuts + '\'' +
                ", idCessione=" + idCessione +
                ", idConcessione=" + idConcessione +
                ", flAlienato='" + flAlienato + '\'' +
                ", idDismissione=" + idDismissione +
                ", idDisponibilita=" + idDisponibilita +
                ", importoAnno1=" + importoAnno1 +
                ", importoAnno2=" + importoAnno2 +
                ", importoAnno3=" + importoAnno3 +
                ", importoAnnoSuccessivo=" + importoAnnoSuccessivo +
                '}';
    }
}