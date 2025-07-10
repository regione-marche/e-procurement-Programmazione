
package it.apkappa.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for OutGetImpegni complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OutGetImpegni"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Esercizio" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Anno" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Numero" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Subnumero" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
 *         &lt;element name="BeneficiarioCodice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BeneficiarioNominativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BeneficiarioCF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BeneficiarioPIVA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AttoTipologia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AttoNumero" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="AttoData" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="AttoDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceCIG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceCUP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ImportoImpegno" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ImportoLiquidato" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ImportoMandati" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ImportoDaLiquidare" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ImportoMandatiDaEmettere" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutGetImpegni", propOrder = {
    "esercizio",
    "anno",
    "numero",
    "subnumero",
    "descrizione",
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
    "beneficiarioCodice",
    "beneficiarioNominativo",
    "beneficiarioCF",
    "beneficiarioPIVA",
    "attoTipologia",
    "attoNumero",
    "attoData",
    "attoDescrizione",
    "codiceCIG",
    "codiceCUP",
    "importoImpegno",
    "importoLiquidato",
    "importoMandati",
    "importoDaLiquidare",
    "importoMandatiDaEmettere"
})
public class OutGetImpegni {

    @XmlElement(name = "Esercizio")
    protected int esercizio;
    @XmlElement(name = "Anno")
    protected int anno;
    @XmlElement(name = "Numero")
    protected int numero;
    @XmlElement(name = "Subnumero")
    protected int subnumero;
    @XmlElement(name = "Descrizione")
    protected String descrizione;
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
    @XmlElement(name = "BeneficiarioCodice")
    protected String beneficiarioCodice;
    @XmlElement(name = "BeneficiarioNominativo")
    protected String beneficiarioNominativo;
    @XmlElement(name = "BeneficiarioCF")
    protected String beneficiarioCF;
    @XmlElement(name = "BeneficiarioPIVA")
    protected String beneficiarioPIVA;
    @XmlElement(name = "AttoTipologia")
    protected String attoTipologia;
    @XmlElement(name = "AttoNumero")
    protected int attoNumero;
    @XmlElement(name = "AttoData", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar attoData;
    @XmlElement(name = "AttoDescrizione")
    protected String attoDescrizione;
    @XmlElement(name = "CodiceCIG")
    protected String codiceCIG;
    @XmlElement(name = "CodiceCUP")
    protected String codiceCUP;
    @XmlElement(name = "ImportoImpegno")
    protected double importoImpegno;
    @XmlElement(name = "ImportoLiquidato")
    protected double importoLiquidato;
    @XmlElement(name = "ImportoMandati")
    protected double importoMandati;
    @XmlElement(name = "ImportoDaLiquidare")
    protected double importoDaLiquidare;
    @XmlElement(name = "ImportoMandatiDaEmettere")
    protected double importoMandatiDaEmettere;

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
     * Gets the value of the subnumero property.
     * 
     */
    public int getSubnumero() {
        return subnumero;
    }

    /**
     * Sets the value of the subnumero property.
     * 
     */
    public void setSubnumero(int value) {
        this.subnumero = value;
    }

    /**
     * Gets the value of the descrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Sets the value of the descrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
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
     * Gets the value of the beneficiarioCodice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneficiarioCodice() {
        return beneficiarioCodice;
    }

    /**
     * Sets the value of the beneficiarioCodice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneficiarioCodice(String value) {
        this.beneficiarioCodice = value;
    }

    /**
     * Gets the value of the beneficiarioNominativo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneficiarioNominativo() {
        return beneficiarioNominativo;
    }

    /**
     * Sets the value of the beneficiarioNominativo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneficiarioNominativo(String value) {
        this.beneficiarioNominativo = value;
    }

    /**
     * Gets the value of the beneficiarioCF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneficiarioCF() {
        return beneficiarioCF;
    }

    /**
     * Sets the value of the beneficiarioCF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneficiarioCF(String value) {
        this.beneficiarioCF = value;
    }

    /**
     * Gets the value of the beneficiarioPIVA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneficiarioPIVA() {
        return beneficiarioPIVA;
    }

    /**
     * Sets the value of the beneficiarioPIVA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneficiarioPIVA(String value) {
        this.beneficiarioPIVA = value;
    }

    /**
     * Gets the value of the attoTipologia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttoTipologia() {
        return attoTipologia;
    }

    /**
     * Sets the value of the attoTipologia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttoTipologia(String value) {
        this.attoTipologia = value;
    }

    /**
     * Gets the value of the attoNumero property.
     * 
     */
    public int getAttoNumero() {
        return attoNumero;
    }

    /**
     * Sets the value of the attoNumero property.
     * 
     */
    public void setAttoNumero(int value) {
        this.attoNumero = value;
    }

    /**
     * Gets the value of the attoData property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAttoData() {
        return attoData;
    }

    /**
     * Sets the value of the attoData property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAttoData(XMLGregorianCalendar value) {
        this.attoData = value;
    }

    /**
     * Gets the value of the attoDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttoDescrizione() {
        return attoDescrizione;
    }

    /**
     * Sets the value of the attoDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttoDescrizione(String value) {
        this.attoDescrizione = value;
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

    /**
     * Gets the value of the importoImpegno property.
     * 
     */
    public double getImportoImpegno() {
        return importoImpegno;
    }

    /**
     * Sets the value of the importoImpegno property.
     * 
     */
    public void setImportoImpegno(double value) {
        this.importoImpegno = value;
    }

    /**
     * Gets the value of the importoLiquidato property.
     * 
     */
    public double getImportoLiquidato() {
        return importoLiquidato;
    }

    /**
     * Sets the value of the importoLiquidato property.
     * 
     */
    public void setImportoLiquidato(double value) {
        this.importoLiquidato = value;
    }

    /**
     * Gets the value of the importoMandati property.
     * 
     */
    public double getImportoMandati() {
        return importoMandati;
    }

    /**
     * Sets the value of the importoMandati property.
     * 
     */
    public void setImportoMandati(double value) {
        this.importoMandati = value;
    }

    /**
     * Gets the value of the importoDaLiquidare property.
     * 
     */
    public double getImportoDaLiquidare() {
        return importoDaLiquidare;
    }

    /**
     * Sets the value of the importoDaLiquidare property.
     * 
     */
    public void setImportoDaLiquidare(double value) {
        this.importoDaLiquidare = value;
    }

    /**
     * Gets the value of the importoMandatiDaEmettere property.
     * 
     */
    public double getImportoMandatiDaEmettere() {
        return importoMandatiDaEmettere;
    }

    /**
     * Sets the value of the importoMandatiDaEmettere property.
     * 
     */
    public void setImportoMandatiDaEmettere(double value) {
        this.importoMandatiDaEmettere = value;
    }

}
