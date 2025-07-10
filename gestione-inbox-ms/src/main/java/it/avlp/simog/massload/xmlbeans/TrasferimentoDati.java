
package it.avlp.simog.massload.xmlbeans;

import java.util.ArrayList;
import java.util.List;

public class TrasferimentoDati {

    protected TrasferimentoType infoTrasferimento;
    protected List<DatiAggiudicazioneType> schede;
    protected List<RecIdSchedaElimType> schedeEliminate;
    protected List<VarAnagType> variazioniAnag;
    protected ResponsabiliType responsabili;
    protected AggiudicatariType aggiudicatari;
    protected VariazioneSAType variazioniSA;

    /**
     * Recupera il valore della proprietà infoTrasferimento.
     * 
     * @return
     *     possible object is
     *     {@link TrasferimentoType }
     *     
     */
    public TrasferimentoType getInfoTrasferimento() {
        return infoTrasferimento;
    }

    /**
     * Imposta il valore della proprietà infoTrasferimento.
     * 
     * @param value
     *     allowed object is
     *     {@link TrasferimentoType }
     *     
     */
    public void setInfoTrasferimento(TrasferimentoType value) {
        this.infoTrasferimento = value;
    }

    /**
     * Gets the value of the schede property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the schede property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSchede().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiAggiudicazioneType }
     * 
     * 
     */
    public List<DatiAggiudicazioneType> getSchede() {
        if (schede == null) {
            schede = new ArrayList<DatiAggiudicazioneType>();
        }
        return this.schede;
    }

    /**
     * Gets the value of the schedeEliminate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the schedeEliminate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSchedeEliminate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RecIdSchedaElimType }
     * 
     * 
     */
    public List<RecIdSchedaElimType> getSchedeEliminate() {
        if (schedeEliminate == null) {
            schedeEliminate = new ArrayList<RecIdSchedaElimType>();
        }
        return this.schedeEliminate;
    }

    /**
     * Gets the value of the variazioniAnag property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the variazioniAnag property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVariazioniAnag().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VarAnagType }
     * 
     * 
     */
    public List<VarAnagType> getVariazioniAnag() {
        if (variazioniAnag == null) {
            variazioniAnag = new ArrayList<VarAnagType>();
        }
        return this.variazioniAnag;
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

    /**
     * Recupera il valore della proprietà variazioniSA.
     * 
     * @return
     *     possible object is
     *     {@link VariazioneSAType }
     *     
     */
    public VariazioneSAType getVariazioniSA() {
        return variazioniSA;
    }

    /**
     * Imposta il valore della proprietà variazioniSA.
     * 
     * @param value
     *     allowed object is
     *     {@link VariazioneSAType }
     *     
     */
    public void setVariazioniSA(VariazioneSAType value) {
        this.variazioniSA = value;
    }

}
