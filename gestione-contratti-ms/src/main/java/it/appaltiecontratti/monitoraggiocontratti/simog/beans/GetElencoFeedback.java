//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.11 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2019.10.03 alle 09:59:18 AM CEST 
//


package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="cig" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="idgara" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cfrup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cfsa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tipoFeedBack" type="{http://rete.toscana.it/rfc/sitatort/}TipoFeedbackType"/&gt;
 *         &lt;element name="faseEsecuzione" type="{http://rete.toscana.it/rfc/sitatort/}FaseEsecuzioneType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class GetElencoFeedback {

    protected String cig;
    protected String idgara;
    protected String cfrup;
    protected String cfsa;
    protected String tipoFeedBack;
    protected FaseEsecuzioneType faseEsecuzione;

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
     * Recupera il valore della proprietà idgara.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdgara() {
        return idgara;
    }

    /**
     * Imposta il valore della proprietà idgara.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdgara(String value) {
        this.idgara = value;
    }

    /**
     * Recupera il valore della proprietà cfrup.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCfrup() {
        return cfrup;
    }

    /**
     * Imposta il valore della proprietà cfrup.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCfrup(String value) {
        this.cfrup = value;
    }

    /**
     * Recupera il valore della proprietà cfsa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCfsa() {
        return cfsa;
    }

    /**
     * Imposta il valore della proprietà cfsa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCfsa(String value) {
        this.cfsa = value;
    }

    /**
     * Recupera il valore della proprietà tipoFeedBack.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoFeedBack() {
        return tipoFeedBack;
    }

    /**
     * Imposta il valore della proprietà tipoFeedBack.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoFeedBack(String value) {
        this.tipoFeedBack = value;
    }

    /**
     * Recupera il valore della proprietà faseEsecuzione.
     * 
     * @return
     *     possible object is
     *     {@link FaseEsecuzioneType }
     *     
     */
    public FaseEsecuzioneType getFaseEsecuzione() {
        return faseEsecuzione;
    }

    /**
     * Imposta il valore della proprietà faseEsecuzione.
     * 
     * @param value
     *     allowed object is
     *     {@link FaseEsecuzioneType }
     *     
     */
    public void setFaseEsecuzione(FaseEsecuzioneType value) {
        this.faseEsecuzione = value;
    }

}
