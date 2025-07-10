
package it.apkappa.services;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOutGetImpegni complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOutGetImpegni"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OutGetImpegni" type="{http://services.apkappa.it/}OutGetImpegni" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOutGetImpegni", propOrder = {
    "outGetImpegni"
})
public class ArrayOfOutGetImpegni {

    @XmlElement(name = "OutGetImpegni", nillable = true)
    protected List<OutGetImpegni> outGetImpegni;

    /**
     * Gets the value of the outGetImpegni property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outGetImpegni property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutGetImpegni().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OutGetImpegni }
     * 
     * 
     */
    public List<OutGetImpegni> getOutGetImpegni() {
        if (outGetImpegni == null) {
            outGetImpegni = new ArrayList<OutGetImpegni>();
        }
        return this.outGetImpegni;
    }

}
