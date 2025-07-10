
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
 *         &lt;element name="GetImpegniResult" type="{http://services.apkappa.it/}ArrayOfOutGetImpegni" minOccurs="0"/&gt;
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
    "getImpegniResult"
})
@XmlRootElement(name = "GetImpegniResponse")
public class GetImpegniResponse {

    @XmlElement(name = "GetImpegniResult")
    protected ArrayOfOutGetImpegni getImpegniResult;

    /**
     * Gets the value of the getImpegniResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOutGetImpegni }
     *     
     */
    public ArrayOfOutGetImpegni getGetImpegniResult() {
        return getImpegniResult;
    }

    /**
     * Sets the value of the getImpegniResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOutGetImpegni }
     *     
     */
    public void setGetImpegniResult(ArrayOfOutGetImpegni value) {
        this.getImpegniResult = value;
    }

}
