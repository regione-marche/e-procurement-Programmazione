
package it.avlp.simog.massload.xmlbeans;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Classe Java per AdesioneType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AdesioneType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Appalto" type="{xmlbeans.massload.simog.avlp.it}AppaltoAdesioneType"/&gt;
 *         &lt;element name="Finanziamenti" type="{xmlbeans.massload.simog.avlp.it}FinanziamentoType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Aggiudicatari" type="{xmlbeans.massload.simog.avlp.it}SoggAggiudicatarioType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Incaricati" type="{xmlbeans.massload.simog.avlp.it}IncaricatoType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="DitteAusiliarie" type="{xmlbeans.massload.simog.avlp.it}DittaAusiliariaType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class AdesioneType {

    protected AppaltoAdesioneType appalto;
    protected List<FinanziamentoType> finanziamenti;
    protected List<SoggAggiudicatarioType> aggiudicatari;
    protected List<IncaricatoType> incaricati;
    protected List<DittaAusiliariaType> ditteAusiliarie;

    /**
     * Recupera il valore della proprietà appalto.
     * 
     * @return
     *     possible object is
     *     {@link AppaltoAdesioneType }
     *     
     */
    public AppaltoAdesioneType getAppalto() {
        return appalto;
    }

    /**
     * Imposta il valore della proprietà appalto.
     * 
     * @param value
     *     allowed object is
     *     {@link AppaltoAdesioneType }
     *     
     */
    public void setAppalto(AppaltoAdesioneType value) {
        this.appalto = value;
    }

    /**
     * Gets the value of the finanziamenti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the finanziamenti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFinanziamenti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FinanziamentoType }
     * 
     * 
     */
    public List<FinanziamentoType> getFinanziamenti() {
        if (finanziamenti == null) {
            finanziamenti = new ArrayList<FinanziamentoType>();
        }
        return this.finanziamenti;
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

}
