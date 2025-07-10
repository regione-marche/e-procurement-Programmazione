
package it.apkappa.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OutGetCapitoli complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OutGetCapitoli"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Esercizio" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Tipologia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Anno" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Capitolo" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="CapitoloDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ServizioCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ServizioDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SpesaCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SpesaDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodificaBilancio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodificaBilancioDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodificaPCFin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodificaPCFinDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodificaPCEco" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodificaPCEcoDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ImportoIniziale" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ImportoVariazioni" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ImportoAttuale" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ResiduoDaImpegnareAccertare" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutGetCapitoli", propOrder = {
    "esercizio",
    "tipologia",
    "anno",
    "capitolo",
    "capitoloDescrizione",
    "servizioCodice",
    "servizioDescrizione",
    "spesaCodice",
    "spesaDescrizione",
    "codificaBilancio",
    "codificaBilancioDescrizione",
    "codificaPCFin",
    "codificaPCFinDescrizione",
    "codificaPCEco",
    "codificaPCEcoDescrizione",
    "importoIniziale",
    "importoVariazioni",
    "importoAttuale",
    "residuoDaImpegnareAccertare"
})
public class OutGetCapitoli {

    @XmlElement(name = "Esercizio")
    protected int esercizio;
    @XmlElement(name = "Tipologia")
    protected String tipologia;
    @XmlElement(name = "Anno")
    protected int anno;
    @XmlElement(name = "Capitolo")
    protected int capitolo;
    @XmlElement(name = "CapitoloDescrizione")
    protected String capitoloDescrizione;
    @XmlElement(name = "ServizioCodice")
    protected String servizioCodice;
    @XmlElement(name = "ServizioDescrizione")
    protected String servizioDescrizione;
    @XmlElement(name = "SpesaCodice")
    protected String spesaCodice;
    @XmlElement(name = "SpesaDescrizione")
    protected String spesaDescrizione;
    @XmlElement(name = "CodificaBilancio")
    protected String codificaBilancio;
    @XmlElement(name = "CodificaBilancioDescrizione")
    protected String codificaBilancioDescrizione;
    @XmlElement(name = "CodificaPCFin")
    protected String codificaPCFin;
    @XmlElement(name = "CodificaPCFinDescrizione")
    protected String codificaPCFinDescrizione;
    @XmlElement(name = "CodificaPCEco")
    protected String codificaPCEco;
    @XmlElement(name = "CodificaPCEcoDescrizione")
    protected String codificaPCEcoDescrizione;
    @XmlElement(name = "ImportoIniziale")
    protected double importoIniziale;
    @XmlElement(name = "ImportoVariazioni")
    protected double importoVariazioni;
    @XmlElement(name = "ImportoAttuale")
    protected double importoAttuale;
    @XmlElement(name = "ResiduoDaImpegnareAccertare")
    protected double residuoDaImpegnareAccertare;

    /**
     * Gets the value of the esercizio property.
     * 
     */
    public int getEsercizio() {
        return esercizio;
    }

    /**
     * Sets the value of the esercizio property.
     * 
     */
    public void setEsercizio(int value) {
        this.esercizio = value;
    }

    /**
     * Gets the value of the tipologia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipologia() {
        return tipologia;
    }

    /**
     * Sets the value of the tipologia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipologia(String value) {
        this.tipologia = value;
    }

    /**
     * Gets the value of the anno property.
     * 
     */
    public int getAnno() {
        return anno;
    }

    /**
     * Sets the value of the anno property.
     * 
     */
    public void setAnno(int value) {
        this.anno = value;
    }

    /**
     * Gets the value of the capitolo property.
     * 
     */
    public int getCapitolo() {
        return capitolo;
    }

    /**
     * Sets the value of the capitolo property.
     * 
     */
    public void setCapitolo(int value) {
        this.capitolo = value;
    }

    /**
     * Gets the value of the capitoloDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapitoloDescrizione() {
        return capitoloDescrizione;
    }

    /**
     * Sets the value of the capitoloDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapitoloDescrizione(String value) {
        this.capitoloDescrizione = value;
    }

    /**
     * Gets the value of the servizioCodice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServizioCodice() {
        return servizioCodice;
    }

    /**
     * Sets the value of the servizioCodice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServizioCodice(String value) {
        this.servizioCodice = value;
    }

    /**
     * Gets the value of the servizioDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServizioDescrizione() {
        return servizioDescrizione;
    }

    /**
     * Sets the value of the servizioDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServizioDescrizione(String value) {
        this.servizioDescrizione = value;
    }

    /**
     * Gets the value of the spesaCodice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpesaCodice() {
        return spesaCodice;
    }

    /**
     * Sets the value of the spesaCodice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpesaCodice(String value) {
        this.spesaCodice = value;
    }

    /**
     * Gets the value of the spesaDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpesaDescrizione() {
        return spesaDescrizione;
    }

    /**
     * Sets the value of the spesaDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpesaDescrizione(String value) {
        this.spesaDescrizione = value;
    }

    /**
     * Gets the value of the codificaBilancio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodificaBilancio() {
        return codificaBilancio;
    }

    /**
     * Sets the value of the codificaBilancio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodificaBilancio(String value) {
        this.codificaBilancio = value;
    }

    /**
     * Gets the value of the codificaBilancioDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodificaBilancioDescrizione() {
        return codificaBilancioDescrizione;
    }

    /**
     * Sets the value of the codificaBilancioDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodificaBilancioDescrizione(String value) {
        this.codificaBilancioDescrizione = value;
    }

    /**
     * Gets the value of the codificaPCFin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodificaPCFin() {
        return codificaPCFin;
    }

    /**
     * Sets the value of the codificaPCFin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodificaPCFin(String value) {
        this.codificaPCFin = value;
    }

    /**
     * Gets the value of the codificaPCFinDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodificaPCFinDescrizione() {
        return codificaPCFinDescrizione;
    }

    /**
     * Sets the value of the codificaPCFinDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodificaPCFinDescrizione(String value) {
        this.codificaPCFinDescrizione = value;
    }

    /**
     * Gets the value of the codificaPCEco property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodificaPCEco() {
        return codificaPCEco;
    }

    /**
     * Sets the value of the codificaPCEco property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodificaPCEco(String value) {
        this.codificaPCEco = value;
    }

    /**
     * Gets the value of the codificaPCEcoDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodificaPCEcoDescrizione() {
        return codificaPCEcoDescrizione;
    }

    /**
     * Sets the value of the codificaPCEcoDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodificaPCEcoDescrizione(String value) {
        this.codificaPCEcoDescrizione = value;
    }

    /**
     * Gets the value of the importoIniziale property.
     * 
     */
    public double getImportoIniziale() {
        return importoIniziale;
    }

    /**
     * Sets the value of the importoIniziale property.
     * 
     */
    public void setImportoIniziale(double value) {
        this.importoIniziale = value;
    }

    /**
     * Gets the value of the importoVariazioni property.
     * 
     */
    public double getImportoVariazioni() {
        return importoVariazioni;
    }

    /**
     * Sets the value of the importoVariazioni property.
     * 
     */
    public void setImportoVariazioni(double value) {
        this.importoVariazioni = value;
    }

    /**
     * Gets the value of the importoAttuale property.
     * 
     */
    public double getImportoAttuale() {
        return importoAttuale;
    }

    /**
     * Sets the value of the importoAttuale property.
     * 
     */
    public void setImportoAttuale(double value) {
        this.importoAttuale = value;
    }

    /**
     * Gets the value of the residuoDaImpegnareAccertare property.
     * 
     */
    public double getResiduoDaImpegnareAccertare() {
        return residuoDaImpegnareAccertare;
    }

    /**
     * Sets the value of the residuoDaImpegnareAccertare property.
     * 
     */
    public void setResiduoDaImpegnareAccertare(double value) {
        this.residuoDaImpegnareAccertare = value;
    }

}
