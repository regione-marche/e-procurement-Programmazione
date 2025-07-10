
package it.avlp.simog.massload.xmlbeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

public class SubappaltoType {

    protected List<SoggSubappaltatoreType> subappaltatore;
    protected String cfditta;
    protected XMLGregorianCalendar dataautorizzazione;
    protected String oggettosubappalto;
    protected BigDecimal importopresunto;
    protected BigDecimal importoeffettivo;
    protected String idcategoria;
    protected String idcpv;
    protected String idschedalocale;
    protected String idschedasimog;
    protected String codicefiscaleaggiudicatario;
    protected String codicestato;
    protected String idstatoscheda;
    protected FlagSNType flagdittasubestera;

    /**
     * Gets the value of the subappaltatore property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subappaltatore property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubappaltatore().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SoggSubappaltatoreType }
     * 
     * 
     */
    public List<SoggSubappaltatoreType> getSubappaltatore() {
        if (subappaltatore == null) {
            subappaltatore = new ArrayList<SoggSubappaltatoreType>();
        }
        return this.subappaltatore;
    }

    /**
     * Recupera il valore della proprietà cfditta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCFDITTA() {
        return cfditta;
    }

    /**
     * Imposta il valore della proprietà cfditta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCFDITTA(String value) {
        this.cfditta = value;
    }

    /**
     * Recupera il valore della proprietà dataautorizzazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAAUTORIZZAZIONE() {
        return dataautorizzazione;
    }

    /**
     * Imposta il valore della proprietà dataautorizzazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAAUTORIZZAZIONE(XMLGregorianCalendar value) {
        this.dataautorizzazione = value;
    }

    /**
     * Recupera il valore della proprietà oggettosubappalto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOGGETTOSUBAPPALTO() {
        return oggettosubappalto;
    }

    /**
     * Imposta il valore della proprietà oggettosubappalto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOGGETTOSUBAPPALTO(String value) {
        this.oggettosubappalto = value;
    }

    /**
     * Recupera il valore della proprietà importopresunto.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPORTOPRESUNTO() {
        return importopresunto;
    }

    /**
     * Imposta il valore della proprietà importopresunto.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPORTOPRESUNTO(BigDecimal value) {
        this.importopresunto = value;
    }

    /**
     * Recupera il valore della proprietà importoeffettivo.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPORTOEFFETTIVO() {
        return importoeffettivo;
    }

    /**
     * Imposta il valore della proprietà importoeffettivo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPORTOEFFETTIVO(BigDecimal value) {
        this.importoeffettivo = value;
    }

    /**
     * Recupera il valore della proprietà idcategoria.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDCATEGORIA() {
        return idcategoria;
    }

    /**
     * Imposta il valore della proprietà idcategoria.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDCATEGORIA(String value) {
        this.idcategoria = value;
    }

    /**
     * Recupera il valore della proprietà idcpv.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDCPV() {
        return idcpv;
    }

    /**
     * Imposta il valore della proprietà idcpv.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDCPV(String value) {
        this.idcpv = value;
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
     * Recupera il valore della proprietà codicefiscaleaggiudicatario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICEFISCALEAGGIUDICATARIO() {
        return codicefiscaleaggiudicatario;
    }

    /**
     * Imposta il valore della proprietà codicefiscaleaggiudicatario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICEFISCALEAGGIUDICATARIO(String value) {
        this.codicefiscaleaggiudicatario = value;
    }

    /**
     * Recupera il valore della proprietà codicestato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICESTATO() {
        return codicestato;
    }

    /**
     * Imposta il valore della proprietà codicestato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICESTATO(String value) {
        this.codicestato = value;
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
     * Recupera il valore della proprietà flagdittasubestera.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFLAGDITTASUBESTERA() {
        return flagdittasubestera;
    }

    /**
     * Imposta il valore della proprietà flagdittasubestera.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFLAGDITTASUBESTERA(FlagSNType value) {
        this.flagdittasubestera = value;
    }

}
