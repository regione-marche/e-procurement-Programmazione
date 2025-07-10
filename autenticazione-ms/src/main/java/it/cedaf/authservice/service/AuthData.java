
package it.cedaf.authservice.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per AuthData complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AuthData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="authId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aziendaDenominazione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aziendaPIVA" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aziendaSedelegale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aziendaSedelegaleCitta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cellulare" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataInserimento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="demo" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="documentoDataRilascio" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="documentoDataScadenza" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="documentoEnteRilascio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="documentoNumero" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="documentoTipo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="domicilioCap" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="domicilioCitta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="domicilioIndirizzo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="domicilioProvincia" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="domicilioStato" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fax" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="idCard" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lavoro" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="livelloAutenticazione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="livelloPasswordPolicy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="mailAddress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nascitaData" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="nascitaLuogo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nascitaProvincia" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pec" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="professionistaAlbo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="professionistaEstremiAlbo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="residenzaCap" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="residenzaCitta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="residenzaIndirizzo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="residenzaProvincia" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="residenzaStato" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="sesso" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tipoSoggetto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="titolo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthData", propOrder = {
    "authId",
    "aziendaDenominazione",
    "aziendaPIVA",
    "aziendaSedelegale",
    "aziendaSedelegaleCitta",
    "cellulare",
    "codiceFiscale",
    "cognome",
    "dataInserimento",
    "demo",
    "documentoDataRilascio",
    "documentoDataScadenza",
    "documentoEnteRilascio",
    "documentoNumero",
    "documentoTipo",
    "domicilioCap",
    "domicilioCitta",
    "domicilioIndirizzo",
    "domicilioProvincia",
    "domicilioStato",
    "fax",
    "idCard",
    "lavoro",
    "livelloAutenticazione",
    "livelloPasswordPolicy",
    "mailAddress",
    "nascitaData",
    "nascitaLuogo",
    "nascitaProvincia",
    "nome",
    "pec",
    "professionistaAlbo",
    "professionistaEstremiAlbo",
    "residenzaCap",
    "residenzaCitta",
    "residenzaIndirizzo",
    "residenzaProvincia",
    "residenzaStato",
    "sesso",
    "telefono",
    "tipoSoggetto",
    "titolo"
})
public class AuthData {

    @XmlElement(required = true, nillable = true)
    protected String authId;
    @XmlElement(required = true, nillable = true)
    protected String aziendaDenominazione;
    @XmlElement(required = true, nillable = true)
    protected String aziendaPIVA;
    @XmlElement(required = true, nillable = true)
    protected String aziendaSedelegale;
    @XmlElement(required = true, nillable = true)
    protected String aziendaSedelegaleCitta;
    @XmlElement(required = true, nillable = true)
    protected String cellulare;
    @XmlElement(required = true, nillable = true)
    protected String codiceFiscale;
    @XmlElement(required = true, nillable = true)
    protected String cognome;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInserimento;
    protected boolean demo;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar documentoDataRilascio;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar documentoDataScadenza;
    @XmlElement(required = true, nillable = true)
    protected String documentoEnteRilascio;
    @XmlElement(required = true, nillable = true)
    protected String documentoNumero;
    @XmlElement(required = true, nillable = true)
    protected String documentoTipo;
    @XmlElement(required = true, nillable = true)
    protected String domicilioCap;
    @XmlElement(required = true, nillable = true)
    protected String domicilioCitta;
    @XmlElement(required = true, nillable = true)
    protected String domicilioIndirizzo;
    @XmlElement(required = true, nillable = true)
    protected String domicilioProvincia;
    @XmlElement(required = true, nillable = true)
    protected String domicilioStato;
    @XmlElement(required = true, nillable = true)
    protected String fax;
    @XmlElement(required = true, nillable = true)
    protected String idCard;
    @XmlElement(required = true, nillable = true)
    protected String lavoro;
    @XmlElement(required = true, nillable = true)
    protected String livelloAutenticazione;
    @XmlElement(required = true, nillable = true)
    protected String livelloPasswordPolicy;
    @XmlElement(required = true, nillable = true)
    protected String mailAddress;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar nascitaData;
    @XmlElement(required = true, nillable = true)
    protected String nascitaLuogo;
    @XmlElement(required = true, nillable = true)
    protected String nascitaProvincia;
    @XmlElement(required = true, nillable = true)
    protected String nome;
    @XmlElement(required = true, nillable = true)
    protected String pec;
    @XmlElement(required = true, nillable = true)
    protected String professionistaAlbo;
    @XmlElement(required = true, nillable = true)
    protected String professionistaEstremiAlbo;
    @XmlElement(required = true, nillable = true)
    protected String residenzaCap;
    @XmlElement(required = true, nillable = true)
    protected String residenzaCitta;
    @XmlElement(required = true, nillable = true)
    protected String residenzaIndirizzo;
    @XmlElement(required = true, nillable = true)
    protected String residenzaProvincia;
    @XmlElement(required = true, nillable = true)
    protected String residenzaStato;
    @XmlElement(required = true, nillable = true)
    protected String sesso;
    @XmlElement(required = true, nillable = true)
    protected String telefono;
    @XmlElement(required = true, nillable = true)
    protected String tipoSoggetto;
    @XmlElement(required = true, nillable = true)
    protected String titolo;

    /**
     * Recupera il valore della proprietà authId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthId() {
        return authId;
    }

    /**
     * Imposta il valore della proprietà authId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthId(String value) {
        this.authId = value;
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
     * Recupera il valore della proprietà aziendaPIVA.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAziendaPIVA() {
        return aziendaPIVA;
    }

    /**
     * Imposta il valore della proprietà aziendaPIVA.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAziendaPIVA(String value) {
        this.aziendaPIVA = value;
    }

    /**
     * Recupera il valore della proprietà aziendaSedelegale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAziendaSedelegale() {
        return aziendaSedelegale;
    }

    /**
     * Imposta il valore della proprietà aziendaSedelegale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAziendaSedelegale(String value) {
        this.aziendaSedelegale = value;
    }

    /**
     * Recupera il valore della proprietà aziendaSedelegaleCitta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAziendaSedelegaleCitta() {
        return aziendaSedelegaleCitta;
    }

    /**
     * Imposta il valore della proprietà aziendaSedelegaleCitta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAziendaSedelegaleCitta(String value) {
        this.aziendaSedelegaleCitta = value;
    }

    /**
     * Recupera il valore della proprietà cellulare.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCellulare() {
        return cellulare;
    }

    /**
     * Imposta il valore della proprietà cellulare.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCellulare(String value) {
        this.cellulare = value;
    }

    /**
     * Recupera il valore della proprietà codiceFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Imposta il valore della proprietà codiceFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Recupera il valore della proprietà cognome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il valore della proprietà cognome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognome(String value) {
        this.cognome = value;
    }

    /**
     * Recupera il valore della proprietà dataInserimento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInserimento() {
        return dataInserimento;
    }

    /**
     * Imposta il valore della proprietà dataInserimento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInserimento(XMLGregorianCalendar value) {
        this.dataInserimento = value;
    }

    /**
     * Recupera il valore della proprietà demo.
     * 
     */
    public boolean isDemo() {
        return demo;
    }

    /**
     * Imposta il valore della proprietà demo.
     * 
     */
    public void setDemo(boolean value) {
        this.demo = value;
    }

    /**
     * Recupera il valore della proprietà documentoDataRilascio.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDocumentoDataRilascio() {
        return documentoDataRilascio;
    }

    /**
     * Imposta il valore della proprietà documentoDataRilascio.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDocumentoDataRilascio(XMLGregorianCalendar value) {
        this.documentoDataRilascio = value;
    }

    /**
     * Recupera il valore della proprietà documentoDataScadenza.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDocumentoDataScadenza() {
        return documentoDataScadenza;
    }

    /**
     * Imposta il valore della proprietà documentoDataScadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDocumentoDataScadenza(XMLGregorianCalendar value) {
        this.documentoDataScadenza = value;
    }

    /**
     * Recupera il valore della proprietà documentoEnteRilascio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentoEnteRilascio() {
        return documentoEnteRilascio;
    }

    /**
     * Imposta il valore della proprietà documentoEnteRilascio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentoEnteRilascio(String value) {
        this.documentoEnteRilascio = value;
    }

    /**
     * Recupera il valore della proprietà documentoNumero.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentoNumero() {
        return documentoNumero;
    }

    /**
     * Imposta il valore della proprietà documentoNumero.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentoNumero(String value) {
        this.documentoNumero = value;
    }

    /**
     * Recupera il valore della proprietà documentoTipo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentoTipo() {
        return documentoTipo;
    }

    /**
     * Imposta il valore della proprietà documentoTipo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentoTipo(String value) {
        this.documentoTipo = value;
    }

    /**
     * Recupera il valore della proprietà domicilioCap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomicilioCap() {
        return domicilioCap;
    }

    /**
     * Imposta il valore della proprietà domicilioCap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomicilioCap(String value) {
        this.domicilioCap = value;
    }

    /**
     * Recupera il valore della proprietà domicilioCitta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomicilioCitta() {
        return domicilioCitta;
    }

    /**
     * Imposta il valore della proprietà domicilioCitta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomicilioCitta(String value) {
        this.domicilioCitta = value;
    }

    /**
     * Recupera il valore della proprietà domicilioIndirizzo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomicilioIndirizzo() {
        return domicilioIndirizzo;
    }

    /**
     * Imposta il valore della proprietà domicilioIndirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomicilioIndirizzo(String value) {
        this.domicilioIndirizzo = value;
    }

    /**
     * Recupera il valore della proprietà domicilioProvincia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomicilioProvincia() {
        return domicilioProvincia;
    }

    /**
     * Imposta il valore della proprietà domicilioProvincia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomicilioProvincia(String value) {
        this.domicilioProvincia = value;
    }

    /**
     * Recupera il valore della proprietà domicilioStato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomicilioStato() {
        return domicilioStato;
    }

    /**
     * Imposta il valore della proprietà domicilioStato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomicilioStato(String value) {
        this.domicilioStato = value;
    }

    /**
     * Recupera il valore della proprietà fax.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax() {
        return fax;
    }

    /**
     * Imposta il valore della proprietà fax.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFax(String value) {
        this.fax = value;
    }

    /**
     * Recupera il valore della proprietà idCard.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * Imposta il valore della proprietà idCard.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdCard(String value) {
        this.idCard = value;
    }

    /**
     * Recupera il valore della proprietà lavoro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLavoro() {
        return lavoro;
    }

    /**
     * Imposta il valore della proprietà lavoro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLavoro(String value) {
        this.lavoro = value;
    }

    /**
     * Recupera il valore della proprietà livelloAutenticazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLivelloAutenticazione() {
        return livelloAutenticazione;
    }

    /**
     * Imposta il valore della proprietà livelloAutenticazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLivelloAutenticazione(String value) {
        this.livelloAutenticazione = value;
    }

    /**
     * Recupera il valore della proprietà livelloPasswordPolicy.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLivelloPasswordPolicy() {
        return livelloPasswordPolicy;
    }

    /**
     * Imposta il valore della proprietà livelloPasswordPolicy.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLivelloPasswordPolicy(String value) {
        this.livelloPasswordPolicy = value;
    }

    /**
     * Recupera il valore della proprietà mailAddress.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailAddress() {
        return mailAddress;
    }

    /**
     * Imposta il valore della proprietà mailAddress.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailAddress(String value) {
        this.mailAddress = value;
    }

    /**
     * Recupera il valore della proprietà nascitaData.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getNascitaData() {
        return nascitaData;
    }

    /**
     * Imposta il valore della proprietà nascitaData.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setNascitaData(XMLGregorianCalendar value) {
        this.nascitaData = value;
    }

    /**
     * Recupera il valore della proprietà nascitaLuogo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNascitaLuogo() {
        return nascitaLuogo;
    }

    /**
     * Imposta il valore della proprietà nascitaLuogo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNascitaLuogo(String value) {
        this.nascitaLuogo = value;
    }

    /**
     * Recupera il valore della proprietà nascitaProvincia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNascitaProvincia() {
        return nascitaProvincia;
    }

    /**
     * Imposta il valore della proprietà nascitaProvincia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNascitaProvincia(String value) {
        this.nascitaProvincia = value;
    }

    /**
     * Recupera il valore della proprietà nome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il valore della proprietà nome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Recupera il valore della proprietà pec.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPec() {
        return pec;
    }

    /**
     * Imposta il valore della proprietà pec.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPec(String value) {
        this.pec = value;
    }

    /**
     * Recupera il valore della proprietà professionistaAlbo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfessionistaAlbo() {
        return professionistaAlbo;
    }

    /**
     * Imposta il valore della proprietà professionistaAlbo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfessionistaAlbo(String value) {
        this.professionistaAlbo = value;
    }

    /**
     * Recupera il valore della proprietà professionistaEstremiAlbo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfessionistaEstremiAlbo() {
        return professionistaEstremiAlbo;
    }

    /**
     * Imposta il valore della proprietà professionistaEstremiAlbo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfessionistaEstremiAlbo(String value) {
        this.professionistaEstremiAlbo = value;
    }

    /**
     * Recupera il valore della proprietà residenzaCap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResidenzaCap() {
        return residenzaCap;
    }

    /**
     * Imposta il valore della proprietà residenzaCap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResidenzaCap(String value) {
        this.residenzaCap = value;
    }

    /**
     * Recupera il valore della proprietà residenzaCitta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResidenzaCitta() {
        return residenzaCitta;
    }

    /**
     * Imposta il valore della proprietà residenzaCitta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResidenzaCitta(String value) {
        this.residenzaCitta = value;
    }

    /**
     * Recupera il valore della proprietà residenzaIndirizzo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResidenzaIndirizzo() {
        return residenzaIndirizzo;
    }

    /**
     * Imposta il valore della proprietà residenzaIndirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResidenzaIndirizzo(String value) {
        this.residenzaIndirizzo = value;
    }

    /**
     * Recupera il valore della proprietà residenzaProvincia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResidenzaProvincia() {
        return residenzaProvincia;
    }

    /**
     * Imposta il valore della proprietà residenzaProvincia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResidenzaProvincia(String value) {
        this.residenzaProvincia = value;
    }

    /**
     * Recupera il valore della proprietà residenzaStato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResidenzaStato() {
        return residenzaStato;
    }

    /**
     * Imposta il valore della proprietà residenzaStato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResidenzaStato(String value) {
        this.residenzaStato = value;
    }

    /**
     * Recupera il valore della proprietà sesso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSesso() {
        return sesso;
    }

    /**
     * Imposta il valore della proprietà sesso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSesso(String value) {
        this.sesso = value;
    }

    /**
     * Recupera il valore della proprietà telefono.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Imposta il valore della proprietà telefono.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefono(String value) {
        this.telefono = value;
    }

    /**
     * Recupera il valore della proprietà tipoSoggetto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoSoggetto() {
        return tipoSoggetto;
    }

    /**
     * Imposta il valore della proprietà tipoSoggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoSoggetto(String value) {
        this.tipoSoggetto = value;
    }

    /**
     * Recupera il valore della proprietà titolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Imposta il valore della proprietà titolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitolo(String value) {
        this.titolo = value;
    }

}
