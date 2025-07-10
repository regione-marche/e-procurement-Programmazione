package it.appaltiecontratti.programmi.entity;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @since giu 16, 2023
 */
public class ExportInterventiAquistiNonRipropostiForm {

    private Long contri;
    private boolean showCui;
    private boolean showCup;
    private boolean showDescrizione;
    private boolean showImporto;
    private boolean showPriorita;
    private boolean showMotivo;

    public Long getContri() {
        return contri;
    }

    public void setContri(Long contri) {
        this.contri = contri;
    }

    public boolean isShowCui() {
        return showCui;
    }

    public void setShowCui(boolean showCui) {
        this.showCui = showCui;
    }

    public boolean isShowCup() {
        return showCup;
    }

    public void setShowCup(boolean showCup) {
        this.showCup = showCup;
    }

    public boolean isShowDescrizione() {
        return showDescrizione;
    }

    public void setShowDescrizione(boolean showDescrizione) {
        this.showDescrizione = showDescrizione;
    }

    public boolean isShowImporto() {
        return showImporto;
    }

    public void setShowImporto(boolean showImporto) {
        this.showImporto = showImporto;
    }

    public boolean isShowPriorita() {
        return showPriorita;
    }

    public void setShowPriorita(boolean showPriorita) {
        this.showPriorita = showPriorita;
    }

    public boolean isShowMotivo() {
        return showMotivo;
    }

    public void setShowMotivo(boolean showMotivo) {
        this.showMotivo = showMotivo;
    }
}
