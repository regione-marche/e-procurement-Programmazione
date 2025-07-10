
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


public class ConsultaGaraResponse {

    protected ResponseConsultaGara _return;

    /**
     * Recupera il valore della proprietà return.
     * 
     * @return
     *     possible object is
     *     {@link ResponseConsultaGara }
     *     
     */
    public ResponseConsultaGara getReturn() {
        return _return;
    }

    /**
     * Imposta il valore della proprietà return.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseConsultaGara }
     *     
     */
    public void setReturn(ResponseConsultaGara value) {
        this._return = value;
    }

}
