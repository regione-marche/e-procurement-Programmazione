
package it.avlp.simog.massload.xmlbeans;

/**
 * <p>Classe Java per ReqDocType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ReqDocType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}codice_tipo_doc use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}descrizione_documento use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}emettitore use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}fax use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}telefono use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}mail use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}mail_pec use="required""/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class ReqDocType {

    protected String codiceTipoDoc;
    protected String descrizioneDocumento;
    protected String emettitore;
    protected String fax;
    protected String telefono;
    protected String mail;
    protected String mailPec;

    /**
     * Recupera il valore della proprietà codiceTipoDoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceTipoDoc() {
        return codiceTipoDoc;
    }

    /**
     * Imposta il valore della proprietà codiceTipoDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceTipoDoc(String value) {
        this.codiceTipoDoc = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneDocumento() {
        return descrizioneDocumento;
    }

    /**
     * Imposta il valore della proprietà descrizioneDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneDocumento(String value) {
        this.descrizioneDocumento = value;
    }

    /**
     * Recupera il valore della proprietà emettitore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmettitore() {
        return emettitore;
    }

    /**
     * Imposta il valore della proprietà emettitore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmettitore(String value) {
        this.emettitore = value;
    }

    /**
     * Recupera il valore della proprietà fax.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax() {
        return fax;
    }

    /**
     * Imposta il valore della proprietà fax.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFax(String value) {
        this.fax = value;
    }

    /**
     * Recupera il valore della proprietà telefono.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Imposta il valore della proprietà telefono.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefono(String value) {
        this.telefono = value;
    }

    /**
     * Recupera il valore della proprietà mail.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMail() {
        return mail;
    }

    /**
     * Imposta il valore della proprietà mail.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMail(String value) {
        this.mail = value;
    }

    /**
     * Recupera il valore della proprietà mailPec.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailPec() {
        return mailPec;
    }

    /**
     * Imposta il valore della proprietà mailPec.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailPec(String value) {
        this.mailPec = value;
    }

}
