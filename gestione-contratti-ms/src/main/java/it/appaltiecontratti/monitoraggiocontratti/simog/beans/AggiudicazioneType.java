
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per AggiudicazioneType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AggiudicazioneType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Appalto" type="{xmlbeans.massload.simog.avlp.it}AppaltoType"/&gt;
 *         &lt;element name="TipiAppaltoLav" type="{xmlbeans.massload.simog.avlp.it}TipiAppaltoType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="TipiAppaltoForn" type="{xmlbeans.massload.simog.avlp.it}TipiAppaltoType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Condizioni" type="{xmlbeans.massload.simog.avlp.it}CondizioneType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Requisiti" type="{xmlbeans.massload.simog.avlp.it}RequisitoType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Finanziamenti" type="{xmlbeans.massload.simog.avlp.it}FinanziamentoType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Aggiudicatari" type="{xmlbeans.massload.simog.avlp.it}SoggAggiudicatarioType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Incaricati" type="{xmlbeans.massload.simog.avlp.it}IncaricatoType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="DitteAusiliarie" type="{xmlbeans.massload.simog.avlp.it}DittaAusiliariaType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="CUPLOTTO" type="{xmlbeans.massload.simog.avlp.it}CUPLOTTOType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class AggiudicazioneType {

    protected AppaltoType appalto;
    protected List<TipiAppaltoType> tipiAppaltoLav;
    protected List<TipiAppaltoType> tipiAppaltoForn;
    protected List<CondizioneType> condizioni;
    protected List<RequisitoType> requisiti;
    protected List<FinanziamentoType> finanziamenti;
    protected List<SoggAggiudicatarioType> aggiudicatari;
    protected List<IncaricatoType> incaricati;
    protected List<DittaAusiliariaType> ditteAusiliarie;
    protected CUPLOTTOType cuplotto;

    /**
     * Recupera il valore della proprietà appalto.
     * 
     * @return
     *     possible object is
     *     {@link AppaltoType }
     *     
     */
    public AppaltoType getAppalto() {
        return appalto;
    }

    /**
     * Imposta il valore della proprietà appalto.
     * 
     * @param value
     *     allowed object is
     *     {@link AppaltoType }
     *     
     */
    public void setAppalto(AppaltoType value) {
        this.appalto = value;
    }

    /**
     * Gets the value of the tipiAppaltoLav property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tipiAppaltoLav property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTipiAppaltoLav().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipiAppaltoType }
     * 
     * 
     */
    public List<TipiAppaltoType> getTipiAppaltoLav() {
        if (tipiAppaltoLav == null) {
            tipiAppaltoLav = new ArrayList<TipiAppaltoType>();
        }
        return this.tipiAppaltoLav;
    }

    /**
     * Gets the value of the tipiAppaltoForn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tipiAppaltoForn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTipiAppaltoForn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipiAppaltoType }
     * 
     * 
     */
    public List<TipiAppaltoType> getTipiAppaltoForn() {
        if (tipiAppaltoForn == null) {
            tipiAppaltoForn = new ArrayList<TipiAppaltoType>();
        }
        return this.tipiAppaltoForn;
    }

    /**
     * Gets the value of the condizioni property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the condizioni property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCondizioni().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CondizioneType }
     * 
     * 
     */
    public List<CondizioneType> getCondizioni() {
        if (condizioni == null) {
            condizioni = new ArrayList<CondizioneType>();
        }
        return this.condizioni;
    }

    /**
     * Gets the value of the requisiti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requisiti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequisiti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequisitoType }
     * 
     * 
     */
    public List<RequisitoType> getRequisiti() {
        if (requisiti == null) {
            requisiti = new ArrayList<RequisitoType>();
        }
        return this.requisiti;
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
