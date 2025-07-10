//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.11 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2019.10.03 alle 11:25:07 AM CEST 
//


package it.avlp.simog.massload.xmlbeans;

public class ResponseInserisciLotto {

    protected CigBean cig;
    protected boolean success;
    protected String error;
    protected CUPLOTTOType cuplotto;

    /**
     * Recupera il valore della proprietà cig.
     * 
     * @return
     *     possible object is
     *     {@link CigBean }
     *     
     */
    public CigBean getCig() {
        return cig;
    }

    /**
     * Imposta il valore della proprietà cig.
     * 
     * @param value
     *     allowed object is
     *     {@link CigBean }
     *     
     */
    public void setCig(CigBean value) {
        this.cig = value;
    }

    /**
     * Recupera il valore della proprietà success.
     * 
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Imposta il valore della proprietà success.
     * 
     */
    public void setSuccess(boolean value) {
        this.success = value;
    }

    /**
     * Recupera il valore della proprietà error.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getError() {
        return error;
    }

    /**
     * Imposta il valore della proprietà error.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setError(String value) {
        this.error = value;
    }

    /**
     * Recupera il valore della proprietà cuplotto.
     * 
     * @return
     *     possible object is
     *     {@link CUPLOTTOType }
     *     
     */
    public CUPLOTTOType getCUPLOTTO() {
        return cuplotto;
    }

    /**
     * Imposta il valore della proprietà cuplotto.
     * 
     * @param value
     *     allowed object is
     *     {@link CUPLOTTOType }
     *     
     */
    public void setCUPLOTTO(CUPLOTTOType value) {
        this.cuplotto = value;
    }

}
