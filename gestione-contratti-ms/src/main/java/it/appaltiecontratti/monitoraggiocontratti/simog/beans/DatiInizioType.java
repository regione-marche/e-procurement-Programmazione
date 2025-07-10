
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


public class DatiInizioType {

    protected PubblicazioneType pubblicazioneEsito;
    protected InizioType inizio;
    protected List<PosizioneType> posizioni;
    protected List<IncaricatoType> incaricati;

    /**
     * Recupera il valore della proprietà pubblicazioneEsito.
     * 
     * @return
     *     possible object is
     *     {@link PubblicazioneType }
     *     
     */
    public PubblicazioneType getPubblicazioneEsito() {
        return pubblicazioneEsito;
    }

    /**
     * Imposta il valore della proprietà pubblicazioneEsito.
     * 
     * @param value
     *     allowed object is
     *     {@link PubblicazioneType }
     *     
     */
    public void setPubblicazioneEsito(PubblicazioneType value) {
        this.pubblicazioneEsito = value;
    }

    /**
     * Recupera il valore della proprietà inizio.
     * 
     * @return
     *     possible object is
     *     {@link InizioType }
     *     
     */
    public InizioType getInizio() {
        return inizio;
    }

    /**
     * Imposta il valore della proprietà inizio.
     * 
     * @param value
     *     allowed object is
     *     {@link InizioType }
     *     
     */
    public void setInizio(InizioType value) {
        this.inizio = value;
    }

    /**
     * Gets the value of the posizioni property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the posizioni property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPosizioni().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PosizioneType }
     * 
     * 
     */
    public List<PosizioneType> getPosizioni() {
        if (posizioni == null) {
            posizioni = new ArrayList<PosizioneType>();
        }
        return this.posizioni;
    }

    /**
     * Gets the value of the incaricati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the incaricati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIncaricati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IncaricatoType }
     * 
     * 
     */
    public List<IncaricatoType> getIncaricati() {
        if (incaricati == null) {
            incaricati = new ArrayList<IncaricatoType>();
        }
        return this.incaricati;
    }

}
