
package it.apkappa.services;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOutGetMandati complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOutGetMandati"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OutGetMandati" type="{http://services.apkappa.it/}OutGetMandati" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOutGetMandati", propOrder = {
    "outGetMandati"
})
public class ArrayOfOutGetMandati {

    @XmlElement(name = "OutGetMandati", nillable = true)
    protected List<OutGetMandati> outGetMandati;

    /**
     * Gets the value of the outGetMandati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outGetMandati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutGetMandati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OutGetMandati }
     * 
     * 
     */
    public List<OutGetMandati> getOutGetMandati() {
        if (outGetMandati == null) {
            outGetMandati = new ArrayList<OutGetMandati>();
        }
        return this.outGetMandati;
    }

}
