
package it.avlp.simog.massload.xmlbeans;

public class ResponseConsultaGara {

    protected SchedaType garaXML;
    protected boolean success;
    protected String error;

    /**
     * Recupera il valore della proprietà garaXML.
     * 
     * @return
     *     possible object is
     *     {@link SchedaType }
     *     
     */
    public SchedaType getGaraXML() {
        return garaXML;
    }

    /**
     * Imposta il valore della proprietà garaXML.
     * 
     * @param value
     *     allowed object is
     *     {@link SchedaType }
     *     
     */
    public void setGaraXML(SchedaType value) {
        this.garaXML = value;
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

}
