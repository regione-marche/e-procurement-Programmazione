
package it.avlp.simog.massload.xmlbeans;

import java.util.ArrayList;
import java.util.List;

public class DatiAggiudicazioneType {

    protected DatiComuniType datiComuni;
    protected PubblicazioneType pubblicazione;
    protected List<SchedaCompletaType> schedaCompleta;

    /**
     * Recupera il valore della proprietà datiComuni.
     * 
     * @return
     *     possible object is
     *     {@link DatiComuniType }
     *     
     */
    public DatiComuniType getDatiComuni() {
        return datiComuni;
    }

    /**
     * Imposta il valore della proprietà datiComuni.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiComuniType }
     *     
     */
    public void setDatiComuni(DatiComuniType value) {
        this.datiComuni = value;
    }

    /**
     * Recupera il valore della proprietà pubblicazione.
     * 
     * @return
     *     possible object is
     *     {@link PubblicazioneType }
     *     
     */
    public PubblicazioneType getPubblicazione() {
        return pubblicazione;
    }

    /**
     * Imposta il valore della proprietà pubblicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link PubblicazioneType }
     *     
     */
    public void setPubblicazione(PubblicazioneType value) {
        this.pubblicazione = value;
    }

    /**
     * Gets the value of the schedaCompleta property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the schedaCompleta property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSchedaCompleta().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SchedaCompletaType }
     * 
     * 
     */
    public List<SchedaCompletaType> getSchedaCompleta() {
        if (schedaCompleta == null) {
            schedaCompleta = new ArrayList<SchedaCompletaType>();
        }
        return this.schedaCompleta;
    }

}
