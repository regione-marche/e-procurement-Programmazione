
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

public class RecVarianteType {

    protected XMLGregorianCalendar dataverbappr;
    protected String altremotivazioni;
    protected BigDecimal impridetlavori;
    protected BigDecimal impridetservizi;
    protected BigDecimal impridetfornit;
    protected BigDecimal impsicurezza;
    protected String impprogettazione;
    protected String impdisposizione;
    protected BigDecimal ulteriorisomme;
    protected XMLGregorianCalendar dataattoaggiuntivo;
    protected Integer numgiorniproroga;
    protected String idschedalocale;
    protected String idschedasimog;
    protected String idstatoscheda;
    protected String cigprocedura;
    protected String linkvarianti;
    protected String idmotivorevprezzi;

    /**
     * Recupera il valore della proprietà dataverbappr.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAVERBAPPR() {
        return dataverbappr;
    }

    /**
     * Imposta il valore della proprietà dataverbappr.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAVERBAPPR(XMLGregorianCalendar value) {
        this.dataverbappr = value;
    }

    /**
     * Recupera il valore della proprietà altremotivazioni.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getALTREMOTIVAZIONI() {
        return altremotivazioni;
    }

    /**
     * Imposta il valore della proprietà altremotivazioni.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setALTREMOTIVAZIONI(String value) {
        this.altremotivazioni = value;
    }

    /**
     * Recupera il valore della proprietà impridetlavori.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPRIDETLAVORI() {
        return impridetlavori;
    }

    /**
     * Imposta il valore della proprietà impridetlavori.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPRIDETLAVORI(BigDecimal value) {
        this.impridetlavori = value;
    }

    /**
     * Recupera il valore della proprietà impridetservizi.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPRIDETSERVIZI() {
        return impridetservizi;
    }

    /**
     * Imposta il valore della proprietà impridetservizi.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPRIDETSERVIZI(BigDecimal value) {
        this.impridetservizi = value;
    }

    /**
     * Recupera il valore della proprietà impridetfornit.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPRIDETFORNIT() {
        return impridetfornit;
    }

    /**
     * Imposta il valore della proprietà impridetfornit.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPRIDETFORNIT(BigDecimal value) {
        this.impridetfornit = value;
    }

    /**
     * Recupera il valore della proprietà impsicurezza.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPSICUREZZA() {
        return impsicurezza;
    }

    /**
     * Imposta il valore della proprietà impsicurezza.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPSICUREZZA(BigDecimal value) {
        this.impsicurezza = value;
    }

    /**
     * Recupera il valore della proprietà impprogettazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMPPROGETTAZIONE() {
        return impprogettazione;
    }

    /**
     * Imposta il valore della proprietà impprogettazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMPPROGETTAZIONE(String value) {
        this.impprogettazione = value;
    }

    /**
     * Recupera il valore della proprietà impdisposizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMPDISPOSIZIONE() {
        return impdisposizione;
    }

    /**
     * Imposta il valore della proprietà impdisposizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMPDISPOSIZIONE(String value) {
        this.impdisposizione = value;
    }

    /**
     * Recupera il valore della proprietà ulteriorisomme.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getULTERIORISOMME() {
        return ulteriorisomme;
    }

    /**
     * Imposta il valore della proprietà ulteriorisomme.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setULTERIORISOMME(BigDecimal value) {
        this.ulteriorisomme = value;
    }

    /**
     * Recupera il valore della proprietà dataattoaggiuntivo.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAATTOAGGIUNTIVO() {
        return dataattoaggiuntivo;
    }

    /**
     * Imposta il valore della proprietà dataattoaggiuntivo.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAATTOAGGIUNTIVO(XMLGregorianCalendar value) {
        this.dataattoaggiuntivo = value;
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

    /**
     * Recupera il valore della proprietà cigprocedura.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCIGPROCEDURA() {
        return cigprocedura;
    }

    /**
     * Imposta il valore della proprietà cigprocedura.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCIGPROCEDURA(String value) {
        this.cigprocedura = value;
    }

    /**
     * Recupera il valore della proprietà linkvarianti.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLINKVARIANTI() {
        return linkvarianti;
    }

    /**
     * Imposta il valore della proprietà linkvarianti.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLINKVARIANTI(String value) {
        this.linkvarianti = value;
    }

    /**
     * Recupera il valore della proprietà idmotivorevprezzi.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDMOTIVOREVPREZZI() {
        return idmotivorevprezzi;
    }

    /**
     * Imposta il valore della proprietà idmotivorevprezzi.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDMOTIVOREVPREZZI(String value) {
        this.idmotivorevprezzi = value;
    }
    
}
