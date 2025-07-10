
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


public class SchedaCompletaType {

    protected String cui;
    protected AggiudicazioneType aggiudicazione;
    protected SchedaSottosogliaType sottosoglia;
    protected SchedaEsclusoType escluso;
    protected AdesioneType adesione;
    protected DatiInizioType datiInizio;
    protected DatiStipulaType datiStipula;
    protected AvanzamentiType datiAvanzamenti;
    protected ConclusioneType datiConclusione;
    protected DatiCollaudoType datiCollaudo;
    protected RitardiType datiRitardi;
    protected AccordiBonariType datiAccordi;
    protected SospensioniType datiSospensioni;
    protected VariantiType datiVarianti;
    protected SubappaltiType datiSubappalti;

    /**
     * Recupera il valore della proprietà cui.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCUI() {
        return cui;
    }

    /**
     * Imposta il valore della proprietà cui.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCUI(String value) {
        this.cui = value;
    }

    /**
     * Recupera il valore della proprietà aggiudicazione.
     * 
     * @return
     *     possible object is
     *     {@link AggiudicazioneType }
     *     
     */
    public AggiudicazioneType getAggiudicazione() {
        return aggiudicazione;
    }

    /**
     * Imposta il valore della proprietà aggiudicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link AggiudicazioneType }
     *     
     */
    public void setAggiudicazione(AggiudicazioneType value) {
        this.aggiudicazione = value;
    }

    /**
     * Recupera il valore della proprietà sottosoglia.
     * 
     * @return
     *     possible object is
     *     {@link SchedaSottosogliaType }
     *     
     */
    public SchedaSottosogliaType getSottosoglia() {
        return sottosoglia;
    }

    /**
     * Imposta il valore della proprietà sottosoglia.
     * 
     * @param value
     *     allowed object is
     *     {@link SchedaSottosogliaType }
     *     
     */
    public void setSottosoglia(SchedaSottosogliaType value) {
        this.sottosoglia = value;
    }

    /**
     * Recupera il valore della proprietà escluso.
     * 
     * @return
     *     possible object is
     *     {@link SchedaEsclusoType }
     *     
     */
    public SchedaEsclusoType getEscluso() {
        return escluso;
    }

    /**
     * Imposta il valore della proprietà escluso.
     * 
     * @param value
     *     allowed object is
     *     {@link SchedaEsclusoType }
     *     
     */
    public void setEscluso(SchedaEsclusoType value) {
        this.escluso = value;
    }

    /**
     * Recupera il valore della proprietà adesione.
     * 
     * @return
     *     possible object is
     *     {@link AdesioneType }
     *     
     */
    public AdesioneType getAdesione() {
        return adesione;
    }

    /**
     * Imposta il valore della proprietà adesione.
     * 
     * @param value
     *     allowed object is
     *     {@link AdesioneType }
     *     
     */
    public void setAdesione(AdesioneType value) {
        this.adesione = value;
    }

    /**
     * Recupera il valore della proprietà datiInizio.
     * 
     * @return
     *     possible object is
     *     {@link DatiInizioType }
     *     
     */
    public DatiInizioType getDatiInizio() {
        return datiInizio;
    }

    /**
     * Imposta il valore della proprietà datiInizio.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiInizioType }
     *     
     */
    public void setDatiInizio(DatiInizioType value) {
        this.datiInizio = value;
    }

    /**
     * Recupera il valore della proprietà datiStipula.
     * 
     * @return
     *     possible object is
     *     {@link DatiStipulaType }
     *     
     */
    public DatiStipulaType getDatiStipula() {
        return datiStipula;
    }

    /**
     * Imposta il valore della proprietà datiStipula.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiStipulaType }
     *     
     */
    public void setDatiStipula(DatiStipulaType value) {
        this.datiStipula = value;
    }

    /**
     * Recupera il valore della proprietà datiAvanzamenti.
     * 
     * @return
     *     possible object is
     *     {@link AvanzamentiType }
     *     
     */
    public AvanzamentiType getDatiAvanzamenti() {
        return datiAvanzamenti;
    }

    /**
     * Imposta il valore della proprietà datiAvanzamenti.
     * 
     * @param value
     *     allowed object is
     *     {@link AvanzamentiType }
     *     
     */
    public void setDatiAvanzamenti(AvanzamentiType value) {
        this.datiAvanzamenti = value;
    }

    /**
     * Recupera il valore della proprietà datiConclusione.
     * 
     * @return
     *     possible object is
     *     {@link ConclusioneType }
     *     
     */
    public ConclusioneType getDatiConclusione() {
        return datiConclusione;
    }

    /**
     * Imposta il valore della proprietà datiConclusione.
     * 
     * @param value
     *     allowed object is
     *     {@link ConclusioneType }
     *     
     */
    public void setDatiConclusione(ConclusioneType value) {
        this.datiConclusione = value;
    }

    /**
     * Recupera il valore della proprietà datiCollaudo.
     * 
     * @return
     *     possible object is
     *     {@link DatiCollaudoType }
     *     
     */
    public DatiCollaudoType getDatiCollaudo() {
        return datiCollaudo;
    }

    /**
     * Imposta il valore della proprietà datiCollaudo.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiCollaudoType }
     *     
     */
    public void setDatiCollaudo(DatiCollaudoType value) {
        this.datiCollaudo = value;
    }

    /**
     * Recupera il valore della proprietà datiRitardi.
     * 
     * @return
     *     possible object is
     *     {@link RitardiType }
     *     
     */
    public RitardiType getDatiRitardi() {
        return datiRitardi;
    }

    /**
     * Imposta il valore della proprietà datiRitardi.
     * 
     * @param value
     *     allowed object is
     *     {@link RitardiType }
     *     
     */
    public void setDatiRitardi(RitardiType value) {
        this.datiRitardi = value;
    }

    /**
     * Recupera il valore della proprietà datiAccordi.
     * 
     * @return
     *     possible object is
     *     {@link AccordiBonariType }
     *     
     */
    public AccordiBonariType getDatiAccordi() {
        return datiAccordi;
    }

    /**
     * Imposta il valore della proprietà datiAccordi.
     * 
     * @param value
     *     allowed object is
     *     {@link AccordiBonariType }
     *     
     */
    public void setDatiAccordi(AccordiBonariType value) {
        this.datiAccordi = value;
    }

    /**
     * Recupera il valore della proprietà datiSospensioni.
     * 
     * @return
     *     possible object is
     *     {@link SospensioniType }
     *     
     */
    public SospensioniType getDatiSospensioni() {
        return datiSospensioni;
    }

    /**
     * Imposta il valore della proprietà datiSospensioni.
     * 
     * @param value
     *     allowed object is
     *     {@link SospensioniType }
     *     
     */
    public void setDatiSospensioni(SospensioniType value) {
        this.datiSospensioni = value;
    }

    /**
     * Recupera il valore della proprietà datiVarianti.
     * 
     * @return
     *     possible object is
     *     {@link VariantiType }
     *     
     */
    public VariantiType getDatiVarianti() {
        return datiVarianti;
    }

    /**
     * Imposta il valore della proprietà datiVarianti.
     * 
     * @param value
     *     allowed object is
     *     {@link VariantiType }
     *     
     */
    public void setDatiVarianti(VariantiType value) {
        this.datiVarianti = value;
    }

    /**
     * Recupera il valore della proprietà datiSubappalti.
     * 
     * @return
     *     possible object is
     *     {@link SubappaltiType }
     *     
     */
    public SubappaltiType getDatiSubappalti() {
        return datiSubappalti;
    }

    /**
     * Imposta il valore della proprietà datiSubappalti.
     * 
     * @param value
     *     allowed object is
     *     {@link SubappaltiType }
     *     
     */
    public void setDatiSubappalti(SubappaltiType value) {
        this.datiSubappalti = value;
    }

}
