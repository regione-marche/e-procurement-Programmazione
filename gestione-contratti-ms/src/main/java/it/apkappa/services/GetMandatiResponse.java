
package it.apkappa.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GetMandatiResult" type="{http://services.apkappa.it/}ArrayOfOutGetMandati" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getMandatiResult"
})
@XmlRootElement(name = "GetMandatiResponse")
public class GetMandatiResponse {

    @XmlElement(name = "GetMandatiResult")
    protected ArrayOfOutGetMandati getMandatiResult;

    /**
     * Gets the value of the getMandatiResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOutGetMandati }
     *     
     */
    public ArrayOfOutGetMandati getGetMandatiResult() {
        return getMandatiResult;
    }

    /**
     * Sets the value of the getMandatiResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOutGetMandati }
     *     
     */
    public void setGetMandatiResult(ArrayOfOutGetMandati value) {
        this.getMandatiResult = value;
    }

}
