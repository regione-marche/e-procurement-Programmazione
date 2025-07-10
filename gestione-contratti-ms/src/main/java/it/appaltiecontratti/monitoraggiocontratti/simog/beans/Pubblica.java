//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.11 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2019.10.03 alle 11:25:07 AM CEST 
//


package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

public class Pubblica {

    protected String ticket;
    protected String indexCollaborazione;
    protected String cig;
    protected String dataPubblicazione;
    protected String dataScadenzaPag;
    protected String oraScadenza;
    protected String progCui;
    protected String tipoOperazione;
    protected List<AllegatoType> allegato;
    protected Pubblica.DatiPubblicazione datiPubblicazione;
    protected String dataScadenzaRichiestaInvito;
    protected String dataLetteraInvito;
    protected List<CUPLOTTOType> cuplotto;

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
     * Recupera il valore della proprietà cig.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCig() {
        return cig;
    }

    /**
     * Imposta il valore della proprietà cig.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCig(String value) {
        this.cig = value;
    }

    /**
     * Recupera il valore della proprietà dataPubblicazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataPubblicazione() {
        return dataPubblicazione;
    }

    /**
     * Imposta il valore della proprietà dataPubblicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataPubblicazione(String value) {
        this.dataPubblicazione = value;
    }

    /**
     * Recupera il valore della proprietà dataScadenzaPag.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataScadenzaPag() {
        return dataScadenzaPag;
    }

    /**
     * Imposta il valore della proprietà dataScadenzaPag.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataScadenzaPag(String value) {
        this.dataScadenzaPag = value;
    }

    /**
     * Recupera il valore della proprietà oraScadenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOraScadenza() {
        return oraScadenza;
    }

    /**
     * Imposta il valore della proprietà oraScadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOraScadenza(String value) {
        this.oraScadenza = value;
    }

    /**
     * Recupera il valore della proprietà progCui.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgCui() {
        return progCui;
    }

    /**
     * Imposta il valore della proprietà progCui.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgCui(String value) {
        this.progCui = value;
    }

    /**
     * Recupera il valore della proprietà tipoOperazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoOperazione() {
        return tipoOperazione;
    }

    /**
     * Imposta il valore della proprietà tipoOperazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoOperazione(String value) {
        this.tipoOperazione = value;
    }

    /**
     * Gets the value of the allegato property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allegato property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllegato().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AllegatoType }
     * 
     * 
     */
    public List<AllegatoType> getAllegato() {
        if (allegato == null) {
            allegato = new ArrayList<AllegatoType>();
        }
        return this.allegato;
    }

    /**
     * Recupera il valore della proprietà datiPubblicazione.
     * 
     * @return
     *     possible object is
     *     {@link Pubblica.DatiPubblicazione }
     *     
     */
    public Pubblica.DatiPubblicazione getDatiPubblicazione() {
        return datiPubblicazione;
    }

    /**
     * Imposta il valore della proprietà datiPubblicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link Pubblica.DatiPubblicazione }
     *     
     */
    public void setDatiPubblicazione(Pubblica.DatiPubblicazione value) {
        this.datiPubblicazione = value;
    }

    /**
     * Recupera il valore della proprietà dataScadenzaRichiestaInvito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataScadenzaRichiestaInvito() {
        return dataScadenzaRichiestaInvito;
    }

    /**
     * Imposta il valore della proprietà dataScadenzaRichiestaInvito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataScadenzaRichiestaInvito(String value) {
        this.dataScadenzaRichiestaInvito = value;
    }

    /**
     * Recupera il valore della proprietà dataLetteraInvito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataLetteraInvito() {
        return dataLetteraInvito;
    }

    /**
     * Imposta il valore della proprietà dataLetteraInvito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataLetteraInvito(String value) {
        this.dataLetteraInvito = value;
    }

    /**
     * Gets the value of the cuplotto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cuplotto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCUPLOTTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CUPLOTTOType }
     * 
     * 
     */
    public List<CUPLOTTOType> getCUPLOTTO() {
        if (cuplotto == null) {
            cuplotto = new ArrayList<CUPLOTTOType>();
        }
        return this.cuplotto;
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
     *         &lt;element name="Pubblicazione" type="{xmlbeans.massload.simog.avlp.it}PubblicazioneType"/&gt;
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
        "pubblicazione"
    })
    public static class DatiPubblicazione {

        protected PubblicazioneType pubblicazione;

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

    }

}
