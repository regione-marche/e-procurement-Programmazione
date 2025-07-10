
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



public class ConclusioneType {

    protected String idmotivointerr;
    protected String idmotivorisol;
    protected XMLGregorianCalendar datarisoluzione;
    protected String flagoneri;
    protected BigDecimal oneririsoluzione;
    protected FlagSNType flagpolizza;
    protected XMLGregorianCalendar dataultimazione;
    protected int numinfortuni;
    protected int numinfperm;
    protected int numinfmort;
    protected String idschedalocale;
    protected String idschedasimog;
    protected XMLGregorianCalendar terminecontrattultimazione;
    protected Integer numgiorniproroga;
    protected XMLGregorianCalendar dataverbconsegnaavvio;
    protected String idstatoscheda;

    /**
     * Recupera il valore della proprietà idmotivointerr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDMOTIVOINTERR() {
        return idmotivointerr;
    }

    /**
     * Imposta il valore della proprietà idmotivointerr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDMOTIVOINTERR(String value) {
        this.idmotivointerr = value;
    }

    /**
     * Recupera il valore della proprietà idmotivorisol.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDMOTIVORISOL() {
        return idmotivorisol;
    }

    /**
     * Imposta il valore della proprietà idmotivorisol.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDMOTIVORISOL(String value) {
        this.idmotivorisol = value;
    }

    /**
     * Recupera il valore della proprietà datarisoluzione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATARISOLUZIONE() {
        return datarisoluzione;
    }

    /**
     * Imposta il valore della proprietà datarisoluzione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATARISOLUZIONE(XMLGregorianCalendar value) {
        this.datarisoluzione = value;
    }

    /**
     * Recupera il valore della proprietà flagoneri.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLAGONERI() {
        return flagoneri;
    }

    /**
     * Imposta il valore della proprietà flagoneri.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLAGONERI(String value) {
        this.flagoneri = value;
    }

    /**
     * Recupera il valore della proprietà oneririsoluzione.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getONERIRISOLUZIONE() {
        return oneririsoluzione;
    }

    /**
     * Imposta il valore della proprietà oneririsoluzione.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setONERIRISOLUZIONE(BigDecimal value) {
        this.oneririsoluzione = value;
    }

    /**
     * Recupera il valore della proprietà flagpolizza.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGPOLIZZA() {
        return flagpolizza;
    }

    /**
     * Imposta il valore della proprietà flagpolizza.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGPOLIZZA(FlagSNType value) {
        this.flagpolizza = value;
    }

    /**
     * Recupera il valore della proprietà dataultimazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAULTIMAZIONE() {
        return dataultimazione;
    }

    /**
     * Imposta il valore della proprietà dataultimazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAULTIMAZIONE(XMLGregorianCalendar value) {
        this.dataultimazione = value;
    }

    /**
     * Recupera il valore della proprietà numinfortuni.
     * 
     */
    public int getNUMINFORTUNI() {
        return numinfortuni;
    }

    /**
     * Imposta il valore della proprietà numinfortuni.
     * 
     */
    public void setNUMINFORTUNI(int value) {
        this.numinfortuni = value;
    }

    /**
     * Recupera il valore della proprietà numinfperm.
     * 
     */
    public int getNUMINFPERM() {
        return numinfperm;
    }

    /**
     * Imposta il valore della proprietà numinfperm.
     * 
     */
    public void setNUMINFPERM(int value) {
        this.numinfperm = value;
    }

    /**
     * Recupera il valore della proprietà numinfmort.
     * 
     */
    public int getNUMINFMORT() {
        return numinfmort;
    }

    /**
     * Imposta il valore della proprietà numinfmort.
     * 
     */
    public void setNUMINFMORT(int value) {
        this.numinfmort = value;
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
     * Recupera il valore della proprietà terminecontrattultimazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTERMINECONTRATTULTIMAZIONE() {
        return terminecontrattultimazione;
    }

    /**
     * Imposta il valore della proprietà terminecontrattultimazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTERMINECONTRATTULTIMAZIONE(XMLGregorianCalendar value) {
        this.terminecontrattultimazione = value;
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
     * Recupera il valore della proprietà dataverbconsegnaavvio.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAVERBCONSEGNAAVVIO() {
        return dataverbconsegnaavvio;
    }

    /**
     * Imposta il valore della proprietà dataverbconsegnaavvio.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAVERBCONSEGNAAVVIO(XMLGregorianCalendar value) {
        this.dataverbconsegnaavvio = value;
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
