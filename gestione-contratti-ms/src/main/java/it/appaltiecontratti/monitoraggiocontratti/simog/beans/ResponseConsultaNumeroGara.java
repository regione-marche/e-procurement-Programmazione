
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

public class ResponseConsultaNumeroGara {

    protected SchedaGaraCig schedaGaraCig;
    protected boolean success;
    protected String error;

    /**
     * Recupera il valore della proprietà schedaGaraCig.
     * 
     * @return
     *     possible object is
     *     {@link SchedaGaraCig }
     *     
     */
    public SchedaGaraCig getSchedaGaraCig() {
        return schedaGaraCig;
    }

    /**
     * Imposta il valore della proprietà schedaGaraCig.
     * 
     * @param value
     *     allowed object is
     *     {@link SchedaGaraCig }
     *     
     */
    public void setSchedaGaraCig(SchedaGaraCig value) {
        this.schedaGaraCig = value;
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
