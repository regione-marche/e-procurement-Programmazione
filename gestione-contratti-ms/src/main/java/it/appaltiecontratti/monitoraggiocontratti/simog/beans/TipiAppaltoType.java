
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


public class TipiAppaltoType {

    protected String idappalto;

    /**
     * Recupera il valore della proprietà idappalto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDAPPALTO() {
        return idappalto;
    }

    /**
     * Imposta il valore della proprietà idappalto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDAPPALTO(String value) {
        this.idappalto = value;
    }

}
