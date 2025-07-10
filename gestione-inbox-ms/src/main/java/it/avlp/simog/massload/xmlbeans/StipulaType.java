
package it.avlp.simog.massload.xmlbeans;

import javax.xml.datatype.XMLGregorianCalendar;

public class StipulaType {

    protected XMLGregorianCalendar datastipula;
    protected XMLGregorianCalendar datadecorrrenza;
    protected XMLGregorianCalendar datascadenza;
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
     * Recupera il valore della proprietà datadecorrrenza.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATADECORRRENZA() {
        return datadecorrrenza;
    }

    /**
     * Imposta il valore della proprietà datadecorrrenza.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATADECORRRENZA(XMLGregorianCalendar value) {
        this.datadecorrrenza = value;
    }

    /**
     * Recupera il valore della proprietà datascadenza.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATASCADENZA() {
        return datascadenza;
    }

    /**
     * Imposta il valore della proprietà datascadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATASCADENZA(XMLGregorianCalendar value) {
        this.datascadenza = value;
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
