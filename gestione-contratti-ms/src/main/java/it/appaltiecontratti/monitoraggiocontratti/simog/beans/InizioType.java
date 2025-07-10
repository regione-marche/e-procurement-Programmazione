
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

public class InizioType {

    protected XMLGregorianCalendar datastipula;
    protected XMLGregorianCalendar dataesecutivita;
    protected BigDecimal importocauzione;
    protected XMLGregorianCalendar datainiprogesec;
    protected XMLGregorianCalendar dataappprogesec;
    protected FlagSNType flagfrazionata;
    protected XMLGregorianCalendar dataverbalecons;
    protected XMLGregorianCalendar dataverbaledef;
    protected String flagriserva;
    protected XMLGregorianCalendar dataverbinizio;
    protected String datatermine;
    protected String idschedalocale;
    protected String idschedasimog;
    protected String idstatoscheda;

    /**
     * Recupera il valore della proprietà datastipula.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATASTIPULA() {
        return datastipula;
    }

    /**
     * Imposta il valore della proprietà datastipula.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATASTIPULA(XMLGregorianCalendar value) {
        this.datastipula = value;
    }

    /**
     * Recupera il valore della proprietà dataesecutivita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAESECUTIVITA() {
        return dataesecutivita;
    }

    /**
     * Imposta il valore della proprietà dataesecutivita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAESECUTIVITA(XMLGregorianCalendar value) {
        this.dataesecutivita = value;
    }

    /**
     * Recupera il valore della proprietà importocauzione.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPORTOCAUZIONE() {
        return importocauzione;
    }

    /**
     * Imposta il valore della proprietà importocauzione.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPORTOCAUZIONE(BigDecimal value) {
        this.importocauzione = value;
    }

    /**
     * Recupera il valore della proprietà datainiprogesec.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAINIPROGESEC() {
        return datainiprogesec;
    }

    /**
     * Imposta il valore della proprietà datainiprogesec.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAINIPROGESEC(XMLGregorianCalendar value) {
        this.datainiprogesec = value;
    }

    /**
     * Recupera il valore della proprietà dataappprogesec.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAAPPPROGESEC() {
        return dataappprogesec;
    }

    /**
     * Imposta il valore della proprietà dataappprogesec.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAAPPPROGESEC(XMLGregorianCalendar value) {
        this.dataappprogesec = value;
    }

    /**
     * Recupera il valore della proprietà flagfrazionata.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGFRAZIONATA() {
        return flagfrazionata;
    }

    /**
     * Imposta il valore della proprietà flagfrazionata.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGFRAZIONATA(FlagSNType value) {
        this.flagfrazionata = value;
    }

    /**
     * Recupera il valore della proprietà dataverbalecons.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAVERBALECONS() {
        return dataverbalecons;
    }

    /**
     * Imposta il valore della proprietà dataverbalecons.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAVERBALECONS(XMLGregorianCalendar value) {
        this.dataverbalecons = value;
    }

    /**
     * Recupera il valore della proprietà dataverbaledef.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAVERBALEDEF() {
        return dataverbaledef;
    }

    /**
     * Imposta il valore della proprietà dataverbaledef.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAVERBALEDEF(XMLGregorianCalendar value) {
        this.dataverbaledef = value;
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
     * Recupera il valore della proprietà dataverbinizio.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAVERBINIZIO() {
        return dataverbinizio;
    }

    /**
     * Imposta il valore della proprietà dataverbinizio.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAVERBINIZIO(XMLGregorianCalendar value) {
        this.dataverbinizio = value;
    }

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
