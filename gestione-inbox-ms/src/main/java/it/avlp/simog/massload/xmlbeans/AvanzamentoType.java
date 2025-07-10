
package it.avlp.simog.massload.xmlbeans;

import java.math.BigDecimal;

import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per AvanzamentoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AvanzamentoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}FLAG_PAGAMENTO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_ANTICIPAZIONE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMPORTO_ANTICIPAZIONE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_RAGGIUNGIMENTO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMPORTO_SAL use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_CERTIFICATO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMPORTO_CERTIFICATO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}FLAG_RITARDO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}NUM_GIORNI_SCOST"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}NUM_GIORNI_PROROGA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DENOM_AVANZAMENTO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_SCHEDA_LOCALE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_SCHEDA_SIMOG"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_STATO_SCHEDA"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */

public class AvanzamentoType {

    protected String flagpagamento;
    protected XMLGregorianCalendar dataanticipazione;
    protected BigDecimal importoanticipazione;
    protected XMLGregorianCalendar dataraggiungimento;
    protected BigDecimal importosal;
    protected XMLGregorianCalendar datacertificato;
    protected BigDecimal importocertificato;
    protected FlagRitardoType flagritardo;
    protected Integer numgiorniscost;
    protected Integer numgiorniproroga;
    protected String denomavanzamento;
    protected String idschedalocale;
    protected String idschedasimog;
    protected String idstatoscheda;

    /**
     * Recupera il valore della proprietà flagpagamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLAGPAGAMENTO() {
        return flagpagamento;
    }

    /**
     * Imposta il valore della proprietà flagpagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLAGPAGAMENTO(String value) {
        this.flagpagamento = value;
    }

    /**
     * Recupera il valore della proprietà dataanticipazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAANTICIPAZIONE() {
        return dataanticipazione;
    }

    /**
     * Imposta il valore della proprietà dataanticipazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAANTICIPAZIONE(XMLGregorianCalendar value) {
        this.dataanticipazione = value;
    }

    /**
     * Recupera il valore della proprietà importoanticipazione.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPORTOANTICIPAZIONE() {
        return importoanticipazione;
    }

    /**
     * Imposta il valore della proprietà importoanticipazione.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPORTOANTICIPAZIONE(BigDecimal value) {
        this.importoanticipazione = value;
    }

    /**
     * Recupera il valore della proprietà dataraggiungimento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATARAGGIUNGIMENTO() {
        return dataraggiungimento;
    }

    /**
     * Imposta il valore della proprietà dataraggiungimento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATARAGGIUNGIMENTO(XMLGregorianCalendar value) {
        this.dataraggiungimento = value;
    }

    /**
     * Recupera il valore della proprietà importosal.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPORTOSAL() {
        return importosal;
    }

    /**
     * Imposta il valore della proprietà importosal.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPORTOSAL(BigDecimal value) {
        this.importosal = value;
    }

    /**
     * Recupera il valore della proprietà datacertificato.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATACERTIFICATO() {
        return datacertificato;
    }

    /**
     * Imposta il valore della proprietà datacertificato.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATACERTIFICATO(XMLGregorianCalendar value) {
        this.datacertificato = value;
    }

    /**
     * Recupera il valore della proprietà importocertificato.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPORTOCERTIFICATO() {
        return importocertificato;
    }

    /**
     * Imposta il valore della proprietà importocertificato.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPORTOCERTIFICATO(BigDecimal value) {
        this.importocertificato = value;
    }

    /**
     * Recupera il valore della proprietà flagritardo.
     * 
     * @return
     *     possible object is
     *     {@link FlagRitardoType }
     *     
     */
    public FlagRitardoType getFLAGRITARDO() {
        return flagritardo;
    }

    /**
     * Imposta il valore della proprietà flagritardo.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagRitardoType }
     *     
     */
    public void setFLAGRITARDO(FlagRitardoType value) {
        this.flagritardo = value;
    }

    /**
     * Recupera il valore della proprietà numgiorniscost.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNUMGIORNISCOST() {
        return numgiorniscost;
    }

    /**
     * Imposta il valore della proprietà numgiorniscost.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNUMGIORNISCOST(Integer value) {
        this.numgiorniscost = value;
    }

    /**
     * Recupera il valore della proprietà numgiorniproroga.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNUMGIORNIPROROGA() {
        return numgiorniproroga;
    }

    /**
     * Imposta il valore della proprietà numgiorniproroga.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNUMGIORNIPROROGA(Integer value) {
        this.numgiorniproroga = value;
    }

    /**
     * Recupera il valore della proprietà denomavanzamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDENOMAVANZAMENTO() {
        return denomavanzamento;
    }

    /**
     * Imposta il valore della proprietà denomavanzamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDENOMAVANZAMENTO(String value) {
        this.denomavanzamento = value;
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
     * Recupera il valore della proprietà idstatoscheda.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDSTATOSCHEDA() {
        return idstatoscheda;
    }

    /**
     * Imposta il valore della proprietà idstatoscheda.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDSTATOSCHEDA(String value) {
        this.idstatoscheda = value;
    }

}
