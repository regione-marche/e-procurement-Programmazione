
package it.cedaf.authservice.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="getAuthIdReturn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "getAuthIdReturn"
})
@XmlRootElement(name = "getAuthIdResponse")
public class GetAuthIdResponse {

    @XmlElement(required = true)
    protected String getAuthIdReturn;

    /**
     * Recupera il valore della proprietà getAuthIdReturn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetAuthIdReturn() {
        return getAuthIdReturn;
    }

    /**
     * Imposta il valore della proprietà getAuthIdReturn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetAuthIdReturn(String value) {
        this.getAuthIdReturn = value;
    }

}
