//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.11 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2019.10.03 alle 11:25:07 AM CEST 
//


package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

public class InserisciLotto {

    protected String ticket;
    protected String indexCollaborazione;
    protected String idGara;
    protected InserisciLotto.DatiLotto datiLotto;

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
     * Recupera il valore della proprietà idGara.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdGara() {
        return idGara;
    }

    /**
     * Imposta il valore della proprietà idGara.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdGara(String value) {
        this.idGara = value;
    }

    /**
     * Recupera il valore della proprietà datiLotto.
     * 
     * @return
     *     possible object is
     *     {@link InserisciLotto.DatiLotto }
     *     
     */
    public InserisciLotto.DatiLotto getDatiLotto() {
        return datiLotto;
    }

    /**
     * Imposta il valore della proprietà datiLotto.
     * 
     * @param value
     *     allowed object is
     *     {@link InserisciLotto.DatiLotto }
     *     
     */
    public void setDatiLotto(InserisciLotto.DatiLotto value) {
        this.datiLotto = value;
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
     *         &lt;element name="Lotto" type="{xmlbeans.massload.simog.avlp.it}LottoType"/&gt;
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
        "lotto"
    })
    public static class DatiLotto {

        protected LottoType lotto;

        /**
         * Recupera il valore della proprietà lotto.
         * 
         * @return
         *     possible object is
         *     {@link LottoType }
         *     
         */
        public LottoType getLotto() {
            return lotto;
        }

        /**
         * Imposta il valore della proprietà lotto.
         * 
         * @param value
         *     allowed object is
         *     {@link LottoType }
         *     
         */
        public void setLotto(LottoType value) {
            this.lotto = value;
        }

    }

}
