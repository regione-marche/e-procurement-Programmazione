
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


public class CondizioneLtType {

    protected String idcondizione;

    /**
     * Recupera il valore della proprietà idcondizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDCONDIZIONE() {
        return idcondizione;
    }

    /**
     * Imposta il valore della proprietà idcondizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDCONDIZIONE(String value) {
        this.idcondizione = value;
    }

}
