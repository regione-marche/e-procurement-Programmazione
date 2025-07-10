
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

public class RitardoType {

    protected String datatermine;
    protected XMLGregorianCalendar dataconsegna;
    protected FlagTCType tipocomun;
    protected int duratasosp;
    protected String motivososp;
    protected XMLGregorianCalendar dataistrecesso;
    protected FlagSNType flagaccolta;
    protected FlagSNType flagtardiva;
    protected FlagSNType flagripresa;
    protected String flagriserva;
    protected BigDecimal importospese;
    protected BigDecimal importooneri;
    protected String idschedalocale;
    protected String idschedasimog;
    protected String idstatoscheda;

    /**
     * Recupera il valore della proprietà datatermine.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDATATERMINE() {
        return datatermine;
    }

    /**
     * Imposta il valore della proprietà datatermine.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATATERMINE(String value) {
        this.datatermine = value;
    }

    /**
     * Recupera il valore della proprietà dataconsegna.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATACONSEGNA() {
        return dataconsegna;
    }

    /**
     * Imposta il valore della proprietà dataconsegna.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATACONSEGNA(XMLGregorianCalendar value) {
        this.dataconsegna = value;
    }

    /**
     * Recupera il valore della proprietà tipocomun.
     * 
     * @return
     *     possible object is
     *     {@link FlagTCType }
     *     
     */
    public FlagTCType getTIPOCOMUN() {
        return tipocomun;
    }

    /**
     * Imposta il valore della proprietà tipocomun.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagTCType }
     *     
     */
    public void setTIPOCOMUN(FlagTCType value) {
        this.tipocomun = value;
    }

    /**
     * Recupera il valore della proprietà duratasosp.
     * 
     */
    public int getDURATASOSP() {
        return duratasosp;
    }

    /**
     * Imposta il valore della proprietà duratasosp.
     * 
     */
    public void setDURATASOSP(int value) {
        this.duratasosp = value;
    }

    /**
     * Recupera il valore della proprietà motivososp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMOTIVOSOSP() {
        return motivososp;
    }

    /**
     * Imposta il valore della proprietà motivososp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMOTIVOSOSP(String value) {
        this.motivososp = value;
    }

    /**
     * Recupera il valore della proprietà dataistrecesso.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAISTRECESSO() {
        return dataistrecesso;
    }

    /**
     * Imposta il valore della proprietà dataistrecesso.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAISTRECESSO(XMLGregorianCalendar value) {
        this.dataistrecesso = value;
    }

    /**
     * Recupera il valore della proprietà flagaccolta.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGACCOLTA() {
        return flagaccolta;
    }

    /**
     * Imposta il valore della proprietà flagaccolta.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGACCOLTA(FlagSNType value) {
        this.flagaccolta = value;
    }

    /**
     * Recupera il valore della proprietà flagtardiva.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGTARDIVA() {
        return flagtardiva;
    }

    /**
     * Imposta il valore della proprietà flagtardiva.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGTARDIVA(FlagSNType value) {
        this.flagtardiva = value;
    }

    /**
     * Recupera il valore della proprietà flagripresa.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGRIPRESA() {
        return flagripresa;
    }

    /**
     * Imposta il valore della proprietà flagripresa.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGRIPRESA(FlagSNType value) {
        this.flagripresa = value;
    }

    /**
     * Recupera il valore della proprietà flagriserva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLAGRISERVA() {
        return flagriserva;
    }

    /**
     * Imposta il valore della proprietà flagriserva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLAGRISERVA(String value) {
        this.flagriserva = value;
    }

    /**
     * Recupera il valore della proprietà importospese.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPORTOSPESE() {
        return importospese;
    }

    /**
     * Imposta il valore della proprietà importospese.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPORTOSPESE(BigDecimal value) {
        this.importospese = value;
    }

    /**
     * Recupera il valore della proprietà importooneri.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPORTOONERI() {
        return importooneri;
    }

    /**
     * Imposta il valore della proprietà importooneri.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPORTOONERI(BigDecimal value) {
        this.importooneri = value;
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
