
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

public class SchedaEsclusoType {

    protected SottoEsclusoType appalto;
    protected List<SoggAggiudicatarioType> aggiudicatari;
    protected List<IncaricatoType> incaricati;
    protected CUPLOTTOType cuplotto;

    /**
     * Recupera il valore della proprietà appalto.
     * 
     * @return
     *     possible object is
     *     {@link SottoEsclusoType }
     *     
     */
    public SottoEsclusoType getAppalto() {
        return appalto;
    }

    /**
     * Imposta il valore della proprietà appalto.
     * 
     * @param value
     *     allowed object is
     *     {@link SottoEsclusoType }
     *     
     */
    public void setAppalto(SottoEsclusoType value) {
        this.appalto = value;
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

    /**
     * Recupera il valore della proprietà cuplotto.
     * 
     * @return
     *     possible object is
     *     {@link CUPLOTTOType }
     *     
     */
    public CUPLOTTOType getCUPLOTTO() {
        return cuplotto;
    }

    /**
     * Imposta il valore della proprietà cuplotto.
     * 
     * @param value
     *     allowed object is
     *     {@link CUPLOTTOType }
     *     
     */
    public void setCUPLOTTO(CUPLOTTOType value) {
        this.cuplotto = value;
    }

}
