
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
 *         &lt;element name="retrieveUserDataReturn" type="{http://service.authservice.cedaf.it}AuthData"/&gt;
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
    "retrieveUserDataReturn"
})
@XmlRootElement(name = "retrieveUserDataResponse")
public class RetrieveUserDataResponse {

    @XmlElement(required = true)
    protected AuthData retrieveUserDataReturn;

    /**
     * Recupera il valore della proprietà retrieveUserDataReturn.
     * 
     * @return
     *     possible object is
     *     {@link AuthData }
     *     
     */
    public AuthData getRetrieveUserDataReturn() {
        return retrieveUserDataReturn;
    }

    /**
     * Imposta il valore della proprietà retrieveUserDataReturn.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthData }
     *     
     */
    public void setRetrieveUserDataReturn(AuthData value) {
        this.retrieveUserDataReturn = value;
    }

}
