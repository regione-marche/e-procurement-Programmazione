
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per collaborazione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="collaborazione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="azienda_codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="azienda_denominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="idOsservatorio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ufficio_denominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ufficio_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ufficio_profilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class Collaborazione {

    protected String aziendaCodiceFiscale;
    protected String aziendaDenominazione;
    protected String idOsservatorio;
    protected String index;
    protected String ufficioDenominazione;
    protected String ufficioId;
    protected String ufficioProfilo;

    /**
     * Recupera il valore della proprietà aziendaCodiceFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAziendaCodiceFiscale() {
        return aziendaCodiceFiscale;
    }

    /**
     * Imposta il valore della proprietà aziendaCodiceFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAziendaCodiceFiscale(String value) {
        this.aziendaCodiceFiscale = value;
    }

    /**
     * Recupera il valore della proprietà aziendaDenominazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAziendaDenominazione() {
        return aziendaDenominazione;
    }

    /**
     * Imposta il valore della proprietà aziendaDenominazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAziendaDenominazione(String value) {
        this.aziendaDenominazione = value;
    }

    /**
     * Recupera il valore della proprietà idOsservatorio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdOsservatorio() {
        return idOsservatorio;
    }

    /**
     * Imposta il valore della proprietà idOsservatorio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdOsservatorio(String value) {
        this.idOsservatorio = value;
    }

    /**
     * Recupera il valore della proprietà index.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndex() {
        return index;
    }

    /**
     * Imposta il valore della proprietà index.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndex(String value) {
        this.index = value;
    }

    /**
     * Recupera il valore della proprietà ufficioDenominazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUfficioDenominazione() {
        return ufficioDenominazione;
    }

    /**
     * Imposta il valore della proprietà ufficioDenominazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUfficioDenominazione(String value) {
        this.ufficioDenominazione = value;
    }

    /**
     * Recupera il valore della proprietà ufficioId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUfficioId() {
        return ufficioId;
    }

    /**
     * Imposta il valore della proprietà ufficioId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUfficioId(String value) {
        this.ufficioId = value;
    }

    /**
     * Recupera il valore della proprietà ufficioProfilo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUfficioProfilo() {
        return ufficioProfilo;
    }

    /**
     * Imposta il valore della proprietà ufficioProfilo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUfficioProfilo(String value) {
        this.ufficioProfilo = value;
    }

}
