
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


public class RecVarAnagType {

    protected String motivo;
    protected RecIdSchedaElimType riferimento;
    protected List<IncaricatoType> responsabili;
    protected List<SoggAggiudicatarioType> aggiudicatari;
    protected List<DittaAusiliariaType> ditteAusiliarie;
    protected List<PosizioneType> posizioni;

    /**
     * Recupera il valore della proprietà motivo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMOTIVO() {
        return motivo;
    }

    /**
     * Imposta il valore della proprietà motivo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMOTIVO(String value) {
        this.motivo = value;
    }

    /**
     * Recupera il valore della proprietà riferimento.
     * 
     * @return
     *     possible object is
     *     {@link RecIdSchedaElimType }
     *     
     */
    public RecIdSchedaElimType getRiferimento() {
        return riferimento;
    }

    /**
     * Imposta il valore della proprietà riferimento.
     * 
     * @param value
     *     allowed object is
     *     {@link RecIdSchedaElimType }
     *     
     */
    public void setRiferimento(RecIdSchedaElimType value) {
        this.riferimento = value;
    }

    /**
     * Gets the value of the responsabili property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the responsabili property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResponsabili().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IncaricatoType }
     * 
     * 
     */
    public List<IncaricatoType> getResponsabili() {
        if (responsabili == null) {
            responsabili = new ArrayList<IncaricatoType>();
        }
        return this.responsabili;
    }

    /**
     * Gets the value of the aggiudicatari property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aggiudicatari property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAggiudicatari().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SoggAggiudicatarioType }
     * 
     * 
     */
    public List<SoggAggiudicatarioType> getAggiudicatari() {
        if (aggiudicatari == null) {
            aggiudicatari = new ArrayList<SoggAggiudicatarioType>();
        }
        return this.aggiudicatari;
    }

    /**
     * Gets the value of the ditteAusiliarie property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ditteAusiliarie property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDitteAusiliarie().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DittaAusiliariaType }
     * 
     * 
     */
    public List<DittaAusiliariaType> getDitteAusiliarie() {
        if (ditteAusiliarie == null) {
            ditteAusiliarie = new ArrayList<DittaAusiliariaType>();
        }
        return this.ditteAusiliarie;
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

}
