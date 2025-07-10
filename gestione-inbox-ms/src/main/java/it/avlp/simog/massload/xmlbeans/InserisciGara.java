//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.11 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2019.10.03 alle 11:25:07 AM CEST 
//


package it.avlp.simog.massload.xmlbeans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

public class InserisciGara {

    protected String ticket;
    protected String indexCollaborazione;
    protected InserisciGara.DatiGara datiGara;

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
     * Recupera il valore della proprietà datiGara.
     * 
     * @return
     *     possible object is
     *     {@link InserisciGara.DatiGara }
     *     
     */
    public InserisciGara.DatiGara getDatiGara() {
        return datiGara;
    }

    /**
     * Imposta il valore della proprietà datiGara.
     * 
     * @param value
     *     allowed object is
     *     {@link InserisciGara.DatiGara }
     *     
     */
    public void setDatiGara(InserisciGara.DatiGara value) {
        this.datiGara = value;
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
     *         &lt;element name="DatiGara" type="{xmlbeans.massload.simog.avlp.it}GaraType"/&gt;
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
        "datiGara"
    })
    public static class DatiGara {

        protected GaraType datiGara;

        /**
         * Recupera il valore della proprietà datiGara.
         * 
         * @return
         *     possible object is
         *     {@link GaraType }
         *     
         */
        public GaraType getDatiGara() {
            return datiGara;
        }

        /**
         * Imposta il valore della proprietà datiGara.
         * 
         * @param value
         *     allowed object is
         *     {@link GaraType }
         *     
         */
        public void setDatiGara(GaraType value) {
            this.datiGara = value;
        }

    }

}
