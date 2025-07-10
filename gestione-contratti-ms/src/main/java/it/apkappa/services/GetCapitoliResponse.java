
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
 *         &lt;element name="GetCapitoliResult" type="{http://services.apkappa.it/}ArrayOfOutGetCapitoli" minOccurs="0"/&gt;
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
    "getCapitoliResult"
})
@XmlRootElement(name = "GetCapitoliResponse")
public class GetCapitoliResponse {

    @XmlElement(name = "GetCapitoliResult")
    protected ArrayOfOutGetCapitoli getCapitoliResult;

    /**
     * Gets the value of the getCapitoliResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOutGetCapitoli }
     *     
     */
    public ArrayOfOutGetCapitoli getGetCapitoliResult() {
        return getCapitoliResult;
    }

    /**
     * Sets the value of the getCapitoliResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOutGetCapitoli }
     *     
     */
    public void setGetCapitoliResult(ArrayOfOutGetCapitoli value) {
        this.getCapitoliResult = value;
    }

}
