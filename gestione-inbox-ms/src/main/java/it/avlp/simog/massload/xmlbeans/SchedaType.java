
package it.avlp.simog.massload.xmlbeans;

public class SchedaType {

    protected DatiGaraType datiGara;
    protected DatiAggiudicazioneType datiScheda;
    protected ResponsabiliType responsabili;
    protected AggiudicatariType aggiudicatari;

    /**
     * Recupera il valore della proprietà datiGara.
     * 
     * @return
     *     possible object is
     *     {@link DatiGaraType }
     *     
     */
    public DatiGaraType getDatiGara() {
        return datiGara;
    }

    /**
     * Imposta il valore della proprietà datiGara.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiGaraType }
     *     
     */
    public void setDatiGara(DatiGaraType value) {
        this.datiGara = value;
    }

    /**
     * Recupera il valore della proprietà datiScheda.
     * 
     * @return
     *     possible object is
     *     {@link DatiAggiudicazioneType }
     *     
     */
    public DatiAggiudicazioneType getDatiScheda() {
        return datiScheda;
    }

    /**
     * Imposta il valore della proprietà datiScheda.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiAggiudicazioneType }
     *     
     */
    public void setDatiScheda(DatiAggiudicazioneType value) {
        this.datiScheda = value;
    }

    /**
     * Recupera il valore della proprietà responsabili.
     * 
     * @return
     *     possible object is
     *     {@link ResponsabiliType }
     *     
     */
    public ResponsabiliType getResponsabili() {
        return responsabili;
    }

    /**
     * Imposta il valore della proprietà responsabili.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponsabiliType }
     *     
     */
    public void setResponsabili(ResponsabiliType value) {
        this.responsabili = value;
    }

    /**
     * Recupera il valore della proprietà aggiudicatari.
     * 
     * @return
     *     possible object is
     *     {@link AggiudicatariType }
     *     
     */
    public AggiudicatariType getAggiudicatari() {
        return aggiudicatari;
    }

    /**
     * Imposta il valore della proprietà aggiudicatari.
     * 
     * @param value
     *     allowed object is
     *     {@link AggiudicatariType }
     *     
     */
    public void setAggiudicatari(AggiudicatariType value) {
        this.aggiudicatari = value;
    }

}
