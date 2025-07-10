
package it.avlp.simog.massload.xmlbeans;

/**
 * <p>Classe Java per RecVariazioneSAType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RecVariazioneSAType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_GARA use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}MOTIVO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CF_AMMINISTRAZIONE use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_CENTRO_COSTO use="required""/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class RecVariazioneSAType {

    protected long idgara;
    protected String motivo;
    protected String cfamministrazione;
    protected String idcentrocosto;

    /**
     * Recupera il valore della proprietà idgara.
     * 
     */
    public long getIDGARA() {
        return idgara;
    }

    /**
     * Imposta il valore della proprietà idgara.
     * 
     */
    public void setIDGARA(long value) {
        this.idgara = value;
    }

    /**
     * Recupera il valore della proprietà motivo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMOTIVO() {
        return motivo;
    }

    /**
     * Imposta il valore della proprietà motivo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMOTIVO(String value) {
        this.motivo = value;
    }

    /**
     * Recupera il valore della proprietà cfamministrazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCFAMMINISTRAZIONE() {
        return cfamministrazione;
    }

    /**
     * Imposta il valore della proprietà cfamministrazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCFAMMINISTRAZIONE(String value) {
        this.cfamministrazione = value;
    }

    /**
     * Recupera il valore della proprietà idcentrocosto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDCENTROCOSTO() {
        return idcentrocosto;
    }

    /**
     * Imposta il valore della proprietà idcentrocosto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDCENTROCOSTO(String value) {
        this.idcentrocosto = value;
    }

}
