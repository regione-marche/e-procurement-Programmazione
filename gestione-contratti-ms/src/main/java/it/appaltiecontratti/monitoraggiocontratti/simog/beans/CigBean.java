//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.11 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2019.10.03 alle 11:25:07 AM CEST 
//


package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per cigBean complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="cigBean"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="applicazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cfAmministrazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cfStazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cfUtente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cig" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cigCicle" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="cigKKK" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class CigBean {

    protected String applicazione;
    protected String cfAmministrazione;
    protected String cfStazione;
    protected String cfUtente;
    protected String cig;
    protected int cigCicle;
    protected String cigKKK;

    /**
     * Recupera il valore della proprietà applicazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicazione() {
        return applicazione;
    }

    /**
     * Imposta il valore della proprietà applicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicazione(String value) {
        this.applicazione = value;
    }

    /**
     * Recupera il valore della proprietà cfAmministrazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCfAmministrazione() {
        return cfAmministrazione;
    }

    /**
     * Imposta il valore della proprietà cfAmministrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCfAmministrazione(String value) {
        this.cfAmministrazione = value;
    }

    /**
     * Recupera il valore della proprietà cfStazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCfStazione() {
        return cfStazione;
    }

    /**
     * Imposta il valore della proprietà cfStazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCfStazione(String value) {
        this.cfStazione = value;
    }

    /**
     * Recupera il valore della proprietà cfUtente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCfUtente() {
        return cfUtente;
    }

    /**
     * Imposta il valore della proprietà cfUtente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCfUtente(String value) {
        this.cfUtente = value;
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
     * Recupera il valore della proprietà cigCicle.
     * 
     */
    public int getCigCicle() {
        return cigCicle;
    }

    /**
     * Imposta il valore della proprietà cigCicle.
     * 
     */
    public void setCigCicle(int value) {
        this.cigCicle = value;
    }

    /**
     * Recupera il valore della proprietà cigKKK.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCigKKK() {
        return cigKKK;
    }

    /**
     * Imposta il valore della proprietà cigKKK.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCigKKK(String value) {
        this.cigKKK = value;
    }

}
