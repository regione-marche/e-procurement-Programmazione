
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per FlussoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="FlussoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_ELABORAZIONE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}NUM_ELABORATE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}NUM_ERRORE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}NUM_WARNING use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}NUM_CARICATE use="required""/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class FlussoType {

    protected XMLGregorianCalendar dataelaborazione;
    protected int numelaborate;
    protected int numerrore;
    protected int numwarning;
    protected int numcaricate;

    /**
     * Recupera il valore della proprietà dataelaborazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAELABORAZIONE() {
        return dataelaborazione;
    }

    /**
     * Imposta il valore della proprietà dataelaborazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAELABORAZIONE(XMLGregorianCalendar value) {
        this.dataelaborazione = value;
    }

    /**
     * Recupera il valore della proprietà numelaborate.
     * 
     */
    public int getNUMELABORATE() {
        return numelaborate;
    }

    /**
     * Imposta il valore della proprietà numelaborate.
     * 
     */
    public void setNUMELABORATE(int value) {
        this.numelaborate = value;
    }

    /**
     * Recupera il valore della proprietà numerrore.
     * 
     */
    public int getNUMERRORE() {
        return numerrore;
    }

    /**
     * Imposta il valore della proprietà numerrore.
     * 
     */
    public void setNUMERRORE(int value) {
        this.numerrore = value;
    }

    /**
     * Recupera il valore della proprietà numwarning.
     * 
     */
    public int getNUMWARNING() {
        return numwarning;
    }

    /**
     * Imposta il valore della proprietà numwarning.
     * 
     */
    public void setNUMWARNING(int value) {
        this.numwarning = value;
    }

    /**
     * Recupera il valore della proprietà numcaricate.
     * 
     */
    public int getNUMCARICATE() {
        return numcaricate;
    }

    /**
     * Imposta il valore della proprietà numcaricate.
     * 
     */
    public void setNUMCARICATE(int value) {
        this.numcaricate = value;
    }

}
