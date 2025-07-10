//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.11 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2019.10.03 alle 11:25:07 AM CEST 
//


package it.avlp.simog.massload.xmlbeans;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


public class InviaRequisiti {

    protected String ticket;
    protected String indexCollaborazione;
    protected String idGara;
    protected InviaRequisiti.Requisiti requisiti;

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
     * Recupera il valore della proprietà requisiti.
     * 
     * @return
     *     possible object is
     *     {@link InviaRequisiti.Requisiti }
     *     
     */
    public InviaRequisiti.Requisiti getRequisiti() {
        return requisiti;
    }

    /**
     * Imposta il valore della proprietà requisiti.
     * 
     * @param value
     *     allowed object is
     *     {@link InviaRequisiti.Requisiti }
     *     
     */
    public void setRequisiti(InviaRequisiti.Requisiti value) {
        this.requisiti = value;
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
     *         &lt;element name="Requisito" type="{xmlbeans.massload.simog.avlp.it}ReqGaraType" maxOccurs="unbounded" minOccurs="0"/&gt;
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
        "requisito"
    })
    public static class Requisiti {

        protected List<ReqGaraType> requisito;

        /**
         * Gets the value of the requisito property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the requisito property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRequisito().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ReqGaraType }
         * 
         * 
         */
        public List<ReqGaraType> getRequisito() {
            if (requisito == null) {
                requisito = new ArrayList<ReqGaraType>();
            }
            return this.requisito;
        }

    }

}
