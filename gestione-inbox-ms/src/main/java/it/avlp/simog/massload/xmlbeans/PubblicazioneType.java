
package it.avlp.simog.massload.xmlbeans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per PubblicazioneType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PubblicazioneType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_GUCE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_GURI"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_ALBO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_BORE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}QUOTIDIANI_NAZ"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}QUOTIDIANI_REG"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}PERIODICI"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}PROFILO_COMMITTENTE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}SITO_MINISTERO_INF_TRASP"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}SITO_OSSERVATORIO_CP"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_SCHEDA_LOCALE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_SCHEDA_SIMOG"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}NUMERO_GUCE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}NUMERO_GURI"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}NUMERO_BORE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}LINK_SITO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}FLAG_BENICULT"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}FLAG_SOSPESO"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PubblicazioneType")
public class PubblicazioneType {

    protected XMLGregorianCalendar dataguce;
    protected XMLGregorianCalendar dataguri;
    protected XMLGregorianCalendar dataalbo;
    protected XMLGregorianCalendar databore;
    protected Integer quotidianinaz;
    protected Integer quotidianireg;
    protected Integer periodici;
    protected FlagSNType profilocommittente;
    protected FlagSNType sitoministeroinftrasp;
    protected FlagSNType sitoosservatoriocp;
    protected String idschedalocale;
    protected String idschedasimog;
    protected String numeroguce;
    protected String numeroguri;
    protected String numerobore;
    protected String linksito;
    protected FlagSNType flagbenicult;
    protected FlagSNType flagsospeso;
    protected String linkaffidamentodiretto;

    /**
     * Recupera il valore della proprietà dataguce.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAGUCE() {
        return dataguce;
    }

    /**
     * Imposta il valore della proprietà dataguce.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAGUCE(XMLGregorianCalendar value) {
        this.dataguce = value;
    }

    /**
     * Recupera il valore della proprietà dataguri.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAGURI() {
        return dataguri;
    }

    /**
     * Imposta il valore della proprietà dataguri.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAGURI(XMLGregorianCalendar value) {
        this.dataguri = value;
    }

    /**
     * Recupera il valore della proprietà dataalbo.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAALBO() {
        return dataalbo;
    }

    /**
     * Imposta il valore della proprietà dataalbo.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAALBO(XMLGregorianCalendar value) {
        this.dataalbo = value;
    }

    /**
     * Recupera il valore della proprietà databore.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATABORE() {
        return databore;
    }

    /**
     * Imposta il valore della proprietà databore.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATABORE(XMLGregorianCalendar value) {
        this.databore = value;
    }

    /**
     * Recupera il valore della proprietà quotidianinaz.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getQUOTIDIANINAZ() {
        return quotidianinaz;
    }

    /**
     * Imposta il valore della proprietà quotidianinaz.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setQUOTIDIANINAZ(Integer value) {
        this.quotidianinaz = value;
    }

    /**
     * Recupera il valore della proprietà quotidianireg.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getQUOTIDIANIREG() {
        return quotidianireg;
    }

    /**
     * Imposta il valore della proprietà quotidianireg.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setQUOTIDIANIREG(Integer value) {
        this.quotidianireg = value;
    }

    /**
     * Recupera il valore della proprietà periodici.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPERIODICI() {
        return periodici;
    }

    /**
     * Imposta il valore della proprietà periodici.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPERIODICI(Integer value) {
        this.periodici = value;
    }

    /**
     * Recupera il valore della proprietà profilocommittente.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getPROFILOCOMMITTENTE() {
        return profilocommittente;
    }

    /**
     * Imposta il valore della proprietà profilocommittente.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setPROFILOCOMMITTENTE(FlagSNType value) {
        this.profilocommittente = value;
    }

    /**
     * Recupera il valore della proprietà sitoministeroinftrasp.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getSITOMINISTEROINFTRASP() {
        return sitoministeroinftrasp;
    }

    /**
     * Imposta il valore della proprietà sitoministeroinftrasp.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setSITOMINISTEROINFTRASP(FlagSNType value) {
        this.sitoministeroinftrasp = value;
    }

    /**
     * Recupera il valore della proprietà sitoosservatoriocp.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getSITOOSSERVATORIOCP() {
        return sitoosservatoriocp;
    }

    /**
     * Imposta il valore della proprietà sitoosservatoriocp.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setSITOOSSERVATORIOCP(FlagSNType value) {
        this.sitoosservatoriocp = value;
    }

    /**
     * Recupera il valore della proprietà idschedalocale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDSCHEDALOCALE() {
        return idschedalocale;
    }

    /**
     * Imposta il valore della proprietà idschedalocale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDSCHEDALOCALE(String value) {
        this.idschedalocale = value;
    }

    /**
     * Recupera il valore della proprietà idschedasimog.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDSCHEDASIMOG() {
        return idschedasimog;
    }

    /**
     * Imposta il valore della proprietà idschedasimog.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDSCHEDASIMOG(String value) {
        this.idschedasimog = value;
    }

    /**
     * Recupera il valore della proprietà numeroguce.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMEROGUCE() {
        return numeroguce;
    }

    /**
     * Imposta il valore della proprietà numeroguce.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMEROGUCE(String value) {
        this.numeroguce = value;
    }

    /**
     * Recupera il valore della proprietà numeroguri.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMEROGURI() {
        return numeroguri;
    }

    /**
     * Imposta il valore della proprietà numeroguri.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMEROGURI(String value) {
        this.numeroguri = value;
    }

    /**
     * Recupera il valore della proprietà numerobore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMEROBORE() {
        return numerobore;
    }

    /**
     * Imposta il valore della proprietà numerobore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMEROBORE(String value) {
        this.numerobore = value;
    }

    /**
     * Recupera il valore della proprietà linksito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLINKSITO() {
        return linksito;
    }

    /**
     * Imposta il valore della proprietà linksito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLINKSITO(String value) {
        this.linksito = value;
    }

    /**
     * Recupera il valore della proprietà flagbenicult.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGBENICULT() {
        return flagbenicult;
    }

    /**
     * Imposta il valore della proprietà flagbenicult.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGBENICULT(FlagSNType value) {
        this.flagbenicult = value;
    }

    /**
     * Recupera il valore della proprietà flagsospeso.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGSOSPESO() {
        return flagsospeso;
    }

    /**
     * Imposta il valore della proprietà flagsospeso.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGSOSPESO(FlagSNType value) {
        this.flagsospeso = value;
    }
    
    /**
     * Recupera il valore della proprietà linkaffidamentodiretto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLINKAFFIDAMENTODIRETTO() {
        return linkaffidamentodiretto;
    }

    /**
     * Imposta il valore della proprietà linkaffidamentodiretto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLINKAFFIDAMENTODIRETTO(String value) {
        this.linkaffidamentodiretto = value;
    }

}
