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


public class IntegraCUP {

    protected String ticket;
    protected String indexCollaborazione;
    protected String cig;
    protected String flagCUP;
    protected List<TipiAppaltoType> tipiappaltol;
    protected List<TipiAppaltoType> tipiappaltofs;
    protected CUPLOTTOType cuplotto;

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
     * Recupera il valore della proprietà flagCUP.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagCUP() {
        return flagCUP;
    }

    /**
     * Imposta il valore della proprietà flagCUP.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagCUP(String value) {
        this.flagCUP = value;
    }

    /**
     * Gets the value of the tipiappaltol property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tipiappaltol property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTIPIAPPALTOL().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipiAppaltoType }
     * 
     * 
     */
    public List<TipiAppaltoType> getTIPIAPPALTOL() {
        if (tipiappaltol == null) {
            tipiappaltol = new ArrayList<TipiAppaltoType>();
        }
        return this.tipiappaltol;
    }

    /**
     * Gets the value of the tipiappaltofs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tipiappaltofs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTIPIAPPALTOFS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipiAppaltoType }
     * 
     * 
     */
    public List<TipiAppaltoType> getTIPIAPPALTOFS() {
        if (tipiappaltofs == null) {
            tipiappaltofs = new ArrayList<TipiAppaltoType>();
        }
        return this.tipiappaltofs;
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
