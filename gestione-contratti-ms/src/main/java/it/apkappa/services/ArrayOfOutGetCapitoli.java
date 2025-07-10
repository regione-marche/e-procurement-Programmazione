
package it.apkappa.services;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOutGetCapitoli complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOutGetCapitoli"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OutGetCapitoli" type="{http://services.apkappa.it/}OutGetCapitoli" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOutGetCapitoli", propOrder = {
    "outGetCapitoli"
})
public class ArrayOfOutGetCapitoli {

    @XmlElement(name = "OutGetCapitoli", nillable = true)
    protected List<OutGetCapitoli> outGetCapitoli;

    /**
     * Gets the value of the outGetCapitoli property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outGetCapitoli property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutGetCapitoli().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OutGetCapitoli }
     * 
     * 
     */
    public List<OutGetCapitoli> getOutGetCapitoli() {
        if (outGetCapitoli == null) {
            outGetCapitoli = new ArrayList<OutGetCapitoli>();
        }
        return this.outGetCapitoli;
    }

}
