
package it.apkappa.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InGetMandati complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InGetMandati"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Esercizio" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Numero" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="SubNumero" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Capitolo" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="CodificaBilancio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceServizio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceSpesa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceBeneficiario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceCIG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceCUP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InGetMandati", propOrder = {
    "esercizio",
    "numero",
    "subNumero",
    "capitolo",
    "codificaBilancio",
    "codiceServizio",
    "codiceSpesa",
    "codiceBeneficiario",
    "codiceCIG",
    "codiceCUP"
})
public class InGetMandati {

    @XmlElement(name = "Esercizio")
    protected int esercizio;
    @XmlElement(name = "Numero")
    protected int numero;
    @XmlElement(name = "SubNumero")
    protected int subNumero;
    @XmlElement(name = "Capitolo")
    protected int capitolo;
    @XmlElement(name = "CodificaBilancio")
    protected String codificaBilancio;
    @XmlElement(name = "CodiceServizio")
    protected String codiceServizio;
    @XmlElement(name = "CodiceSpesa")
    protected String codiceSpesa;
    @XmlElement(name = "CodiceBeneficiario")
    protected String codiceBeneficiario;
    @XmlElement(name = "CodiceCIG")
    protected String codiceCIG;
    @XmlElement(name = "CodiceCUP")
    protected String codiceCUP;

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
     * Gets the value of the numero property.
     * 
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Sets the value of the numero property.
     * 
     */
    public void setNumero(int value) {
        this.numero = value;
    }

    /**
     * Gets the value of the subNumero property.
     * 
     */
    public int getSubNumero() {
        return subNumero;
    }

    /**
     * Sets the value of the subNumero property.
     * 
     */
    public void setSubNumero(int value) {
        this.subNumero = value;
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

    /**
     * Gets the value of the codiceBeneficiario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceBeneficiario() {
        return codiceBeneficiario;
    }

    /**
     * Sets the value of the codiceBeneficiario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceBeneficiario(String value) {
        this.codiceBeneficiario = value;
    }

    /**
     * Gets the value of the codiceCIG property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceCIG() {
        return codiceCIG;
    }

    /**
     * Sets the value of the codiceCIG property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceCIG(String value) {
        this.codiceCIG = value;
    }

    /**
     * Gets the value of the codiceCUP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceCUP() {
        return codiceCUP;
    }

    /**
     * Sets the value of the codiceCUP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceCUP(String value) {
        this.codiceCUP = value;
    }

}
