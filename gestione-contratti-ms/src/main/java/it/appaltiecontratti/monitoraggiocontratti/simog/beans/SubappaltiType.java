
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

public class SubappaltiType {

    protected List<SubappaltoType> subappalto;

    /**
     * Gets the value of the subappalto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subappalto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubappalto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubappaltoType }
     * 
     * 
     */
    public List<SubappaltoType> getSubappalto() {
        if (subappalto == null) {
            subappalto = new ArrayList<SubappaltoType>();
        }
        return this.subappalto;
    }

}
