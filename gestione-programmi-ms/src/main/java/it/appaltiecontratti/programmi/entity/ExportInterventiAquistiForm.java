package it.appaltiecontratti.programmi.entity;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since giu 15, 2023
 */
public class ExportInterventiAquistiForm {
    private Long contri;
    // Comuni a tutti i tipi programma
    private boolean showCUI;
    private boolean showAnnualita;
    private boolean showCup;
    private boolean showLottoF;
    private boolean showDescrizione;
    private boolean showRup;
    private boolean showImportiAnnualita;
    private boolean showApportoCapitalePrivato;
    private boolean showPriorita;
    private boolean showVariato;

    // Tipo programma 1
    private boolean showCodInt;
    private boolean showLavoroC;
    private boolean showCodIstat;
    private boolean showNuts;
    private boolean showTipologia;
    private boolean showCategoria;
    private boolean showImmobili;
    private boolean showScadenza;

    // Tipo programma NOT 1
    private boolean showAcquistoRicompreso;
    private boolean showCodCuiLavoroCollegato;
    private boolean showAmbitoGeografico;
    private boolean showSettore;
    private boolean showCpv;
    private boolean showDurataContratto;
    private boolean showContrattoEssere;
    private boolean showAusa;

    private InterventiSearchForm searchForm;

    public Long getContri() {
        return contri;
    }

    public void setContri(Long contri) {
        this.contri = contri;
    }

    public boolean isShowCUI() {
        return showCUI;
    }

    public void setShowCUI(boolean showCUI) {
        this.showCUI = showCUI;
    }

    public boolean isShowAnnualita() {
        return showAnnualita;
    }

    public void setShowAnnualita(boolean showAnnualita) {
        this.showAnnualita = showAnnualita;
    }

    public boolean isShowCup() {
        return showCup;
    }

    public void setShowCup(boolean showCup) {
        this.showCup = showCup;
    }

    public boolean isShowLottoF() {
        return showLottoF;
    }

    public void setShowLottoF(boolean showLottoF) {
        this.showLottoF = showLottoF;
    }

    public boolean isShowDescrizione() {
        return showDescrizione;
    }

    public void setShowDescrizione(boolean showDescrizione) {
        this.showDescrizione = showDescrizione;
    }

    public boolean isShowRup() {
        return showRup;
    }

    public void setShowRup(boolean showRup) {
        this.showRup = showRup;
    }

    public boolean isShowImportiAnnualita() {
        return showImportiAnnualita;
    }

    public void setShowImportiAnnualita(boolean showImportiAnnualita) {
        this.showImportiAnnualita = showImportiAnnualita;
    }

    public boolean isShowApportoCapitalePrivato() {
        return showApportoCapitalePrivato;
    }

    public void setShowApportoCapitalePrivato(boolean showApportoCapitalePrivato) {
        this.showApportoCapitalePrivato = showApportoCapitalePrivato;
    }

    public boolean isShowPriorita() {
        return showPriorita;
    }

    public void setShowPriorita(boolean showPriorita) {
        this.showPriorita = showPriorita;
    }

    public boolean isShowVariato() {
        return showVariato;
    }

    public void setShowVariato(boolean showVariato) {
        this.showVariato = showVariato;
    }

    public boolean isShowCodInt() {
        return showCodInt;
    }

    public void setShowCodInt(boolean showCodInt) {
        this.showCodInt = showCodInt;
    }

    public boolean isShowLavoroC() {
        return showLavoroC;
    }

    public void setShowLavoroC(boolean showLavoroC) {
        this.showLavoroC = showLavoroC;
    }

    public boolean isShowCodIstat() {
        return showCodIstat;
    }

    public void setShowCodIstat(boolean showCodIstat) {
        this.showCodIstat = showCodIstat;
    }

    public boolean isShowNuts() {
        return showNuts;
    }

    public void setShowNuts(boolean showNuts) {
        this.showNuts = showNuts;
    }

    public boolean isShowTipologia() {
        return showTipologia;
    }

    public void setShowTipologia(boolean showTipologia) {
        this.showTipologia = showTipologia;
    }

    public boolean isShowCategoria() {
        return showCategoria;
    }

    public void setShowCategoria(boolean showCategoria) {
        this.showCategoria = showCategoria;
    }

    public boolean isShowImmobili() {
        return showImmobili;
    }

    public void setShowImmobili(boolean showImmobili) {
        this.showImmobili = showImmobili;
    }

    public boolean isShowScadenza() {
        return showScadenza;
    }

    public void setShowScadenza(boolean showScadenza) {
        this.showScadenza = showScadenza;
    }

    public boolean isShowAcquistoRicompreso() {
        return showAcquistoRicompreso;
    }

    public void setShowAcquistoRicompreso(boolean showAcquistoRicompreso) {
        this.showAcquistoRicompreso = showAcquistoRicompreso;
    }

    public boolean isShowCodCuiLavoroCollegato() {
        return showCodCuiLavoroCollegato;
    }

    public void setShowCodCuiLavoroCollegato(boolean showCodCuiLavoroCollegato) {
        this.showCodCuiLavoroCollegato = showCodCuiLavoroCollegato;
    }

    public boolean isShowAmbitoGeografico() {
        return showAmbitoGeografico;
    }

    public void setShowAmbitoGeografico(boolean showAmbitoGeografico) {
        this.showAmbitoGeografico = showAmbitoGeografico;
    }

    public boolean isShowSettore() {
        return showSettore;
    }

    public void setShowSettore(boolean showSettore) {
        this.showSettore = showSettore;
    }

    public boolean isShowCpv() {
        return showCpv;
    }

    public void setShowCpv(boolean showCpv) {
        this.showCpv = showCpv;
    }

    public boolean isShowDurataContratto() {
        return showDurataContratto;
    }

    public void setShowDurataContratto(boolean showDurataContratto) {
        this.showDurataContratto = showDurataContratto;
    }

    public boolean isShowContrattoEssere() {
        return showContrattoEssere;
    }

    public void setShowContrattoEssere(boolean showContrattoEssere) {
        this.showContrattoEssere = showContrattoEssere;
    }

    public boolean isShowAusa() {
        return showAusa;
    }

    public void setShowAusa(boolean showAusa) {
        this.showAusa = showAusa;
    }

    public InterventiSearchForm getSearchForm() {
        return searchForm;
    }

    public void setSearchForm(InterventiSearchForm searchForm) {
        this.searchForm = searchForm;
    }
}
