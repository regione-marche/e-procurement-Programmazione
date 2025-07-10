
package it.avlp.simog.massload.xmlbeans;

import java.math.BigDecimal;

import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per AccordoBonarioType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AccordoBonarioType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_ACCORDO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ONERI_DERIVANTI"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}NUM_RISERVE use="required""/&gt;
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
public class AccordoBonarioType {

    protected XMLGregorianCalendar dataaccordo;
    protected BigDecimal oneriderivanti;
    protected int numriserve;
    protected String idschedalocale;
    protected String idschedasimog;
    protected String idstatoscheda;

    /**
     * Recupera il valore della proprietà dataaccordo.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAACCORDO() {
        return dataaccordo;
    }

    /**
     * Imposta il valore della proprietà dataaccordo.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAACCORDO(XMLGregorianCalendar value) {
        this.dataaccordo = value;
    }

    /**
     * Recupera il valore della proprietà oneriderivanti.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getONERIDERIVANTI() {
        return oneriderivanti;
    }

    /**
     * Imposta il valore della proprietà oneriderivanti.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setONERIDERIVANTI(BigDecimal value) {
        this.oneriderivanti = value;
    }

    /**
     * Recupera il valore della proprietà numriserve.
     * 
     */
    public int getNUMRISERVE() {
        return numriserve;
    }

    /**
     * Imposta il valore della proprietà numriserve.
     * 
     */
    public void setNUMRISERVE(int value) {
        this.numriserve = value;
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
