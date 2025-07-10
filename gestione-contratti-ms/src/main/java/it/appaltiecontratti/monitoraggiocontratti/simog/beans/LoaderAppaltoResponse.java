
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

public class LoaderAppaltoResponse {

    protected ResponseLoaderAppalto _return;

    /**
     * Recupera il valore della proprietà return.
     * 
     * @return
     *     possible object is
     *     {@link ResponseLoaderAppalto }
     *     
     */
    public ResponseLoaderAppalto getReturn() {
        return _return;
    }

    /**
     * Imposta il valore della proprietà return.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseLoaderAppalto }
     *     
     */
    public void setReturn(ResponseLoaderAppalto value) {
        this._return = value;
    }

}
