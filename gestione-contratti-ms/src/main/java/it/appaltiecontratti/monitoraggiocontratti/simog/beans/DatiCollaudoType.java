
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


public class DatiCollaudoType {

    protected CollaudoType collaudo;
    protected List<IncaricatoType> incaricati;

    /**
     * Recupera il valore della proprietà collaudo.
     * 
     * @return
     *     possible object is
     *     {@link CollaudoType }
     *     
     */
    public CollaudoType getCollaudo() {
        return collaudo;
    }

    /**
     * Imposta il valore della proprietà collaudo.
     * 
     * @param value
     *     allowed object is
     *     {@link CollaudoType }
     *     
     */
    public void setCollaudo(CollaudoType value) {
        this.collaudo = value;
    }

    /**
     * Gets the value of the incaricati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the incaricati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIncaricati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IncaricatoType }
     * 
     * 
     */
    public List<IncaricatoType> getIncaricati() {
        if (incaricati == null) {
            incaricati = new ArrayList<IncaricatoType>();
        }
        return this.incaricati;
    }

}
