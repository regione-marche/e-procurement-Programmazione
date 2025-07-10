
package it.apkappa.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InGetCapitoli complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InGetCapitoli"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Esercizio" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Tipologia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Anno" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Capitolo" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="CodiceServizio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceSpesa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InGetCapitoli", propOrder = {
    "esercizio",
    "tipologia",
    "anno",
    "capitolo",
    "codiceServizio",
    "codiceSpesa"
})
public class InGetCapitoli {

    @XmlElement(name = "Esercizio")
    protected int esercizio;
    @XmlElement(name = "Tipologia")
    protected String tipologia;
    @XmlElement(name = "Anno")
    protected int anno;
    @XmlElement(name = "Capitolo")
    protected int capitolo;
    @XmlElement(name = "CodiceServizio")
    protected String codiceServizio;
    @XmlElement(name = "CodiceSpesa")
    protected String codiceSpesa;

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
     * Gets the value of the codiceServizio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceServizio() {
        return codiceServizio;
    }

    /**
     * Sets the value of the codiceServizio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceServizio(String value) {
        this.codiceServizio = value;
    }

    /**
     * Gets the value of the codiceSpesa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceSpesa() {
        return codiceSpesa;
    }

    /**
     * Sets the value of the codiceSpesa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceSpesa(String value) {
        this.codiceSpesa = value;
    }

}
