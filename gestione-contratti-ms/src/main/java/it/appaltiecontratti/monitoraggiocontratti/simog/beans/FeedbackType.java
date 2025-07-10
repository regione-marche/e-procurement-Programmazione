//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.11 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2019.10.03 alle 09:59:18 AM CEST 
//


package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Feedback
 * 
 * <p>Classe Java per FeedbackType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="FeedbackType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Data" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Scheda" type="{http://rete.toscana.it/rfc/sitatort/}FaseType"/&gt;
 *         &lt;element name="Num" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Esito" type="{http://rete.toscana.it/rfc/sitatort/}EsitoType"/&gt;
 *         &lt;element name="Messaggio" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class FeedbackType {

    protected XMLGregorianCalendar data;
    protected String scheda;
    protected int num;
    protected String esito;
    protected List<String> messaggio;

    /**
     * Recupera il valore della proprietà data.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getData() {
        return data;
    }

    /**
     * Imposta il valore della proprietà data.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setData(XMLGregorianCalendar value) {
        this.data = value;
    }

    /**
     * Recupera il valore della proprietà scheda.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScheda() {
        return scheda;
    }

    /**
     * Imposta il valore della proprietà scheda.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScheda(String value) {
        this.scheda = value;
    }

    /**
     * Recupera il valore della proprietà num.
     * 
     */
    public int getNum() {
        return num;
    }

    /**
     * Imposta il valore della proprietà num.
     * 
     */
    public void setNum(int value) {
        this.num = value;
    }

    /**
     * Recupera il valore della proprietà esito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsito() {
        return esito;
    }

    /**
     * Imposta il valore della proprietà esito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsito(String value) {
        this.esito = value;
    }

    /**
     * Gets the value of the messaggio property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the messaggio property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessaggio().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMessaggio() {
        if (messaggio == null) {
            messaggio = new ArrayList<String>();
        }
        return this.messaggio;
    }

}
