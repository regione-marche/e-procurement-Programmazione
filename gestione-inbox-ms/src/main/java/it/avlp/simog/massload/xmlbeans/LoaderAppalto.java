
package it.avlp.simog.massload.xmlbeans;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


public class LoaderAppalto {

    protected String ticket;
    protected String indexCollaborazione;
    protected LoaderAppalto.TrasferimentoDati trasferimentoDati;

    /**
     * Recupera il valore della proprietà ticket.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * Imposta il valore della proprietà ticket.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicket(String value) {
        this.ticket = value;
    }

    /**
     * Recupera il valore della proprietà indexCollaborazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndexCollaborazione() {
        return indexCollaborazione;
    }

    /**
     * Imposta il valore della proprietà indexCollaborazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndexCollaborazione(String value) {
        this.indexCollaborazione = value;
    }

    /**
     * Recupera il valore della proprietà trasferimentoDati.
     * 
     * @return
     *     possible object is
     *     {@link LoaderAppalto.TrasferimentoDati }
     *     
     */
    public LoaderAppalto.TrasferimentoDati getTrasferimentoDati() {
        return trasferimentoDati;
    }

    /**
     * Imposta il valore della proprietà trasferimentoDati.
     * 
     * @param value
     *     allowed object is
     *     {@link LoaderAppalto.TrasferimentoDati }
     *     
     */
    public void setTrasferimentoDati(LoaderAppalto.TrasferimentoDati value) {
        this.trasferimentoDati = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="InfoTrasferimento" type="{xmlbeans.massload.simog.avlp.it}TrasferimentoType"/&gt;
     *         &lt;element name="Schede" type="{xmlbeans.massload.simog.avlp.it}DatiAggiudicazioneType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="SchedeEliminate" type="{xmlbeans.massload.simog.avlp.it}RecIdSchedaElimType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="VariazioniAnag" type="{xmlbeans.massload.simog.avlp.it}VarAnagType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="Responsabili" type="{xmlbeans.massload.simog.avlp.it}ResponsabiliType" minOccurs="0"/&gt;
     *         &lt;element name="Aggiudicatari" type="{xmlbeans.massload.simog.avlp.it}AggiudicatariType" minOccurs="0"/&gt;
     *         &lt;element name="VariazioniSA" type="{xmlbeans.massload.simog.avlp.it}VariazioneSAType" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "infoTrasferimento",
        "schede",
        "schedeEliminate",
        "variazioniAnag",
        "responsabili",
        "aggiudicatari",
        "variazioniSA"
    })
    public static class TrasferimentoDati {

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

}
