
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

public class SchedaGaraCig {

    protected GaraType gara;
    protected List<String> cig;

    /**
     * Recupera il valore della proprietà gara.
     * 
     * @return
     *     possible object is
     *     {@link GaraType }
     *     
     */
    public GaraType getGara() {
        return gara;
    }

    /**
     * Imposta il valore della proprietà gara.
     * 
     * @param value
     *     allowed object is
     *     {@link GaraType }
     *     
     */
    public void setGara(GaraType value) {
        this.gara = value;
    }

    /**
     * Gets the value of the cig property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cig property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCIG().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCIG() {
        if (cig == null) {
            cig = new ArrayList<String>();
        }
        return this.cig;
    }

}
