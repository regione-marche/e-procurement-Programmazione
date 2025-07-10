
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
 *         &lt;element name="retrieveUserDataReturnV3" type="{http://service.authservice.cedaf.it}AuthDataV3"/&gt;
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
    "retrieveUserDataReturnV3"
})
@XmlRootElement(name = "retrieveUserDataV3Response")
public class RetrieveUserDataV3Response {

    @XmlElement(required = true)
    protected AuthDataV3 retrieveUserDataReturnV3;

    /**
     * Recupera il valore della proprietà retrieveUserDataReturnV3.
     * 
     * @return
     *     possible object is
     *     {@link AuthDataV3 }
     *     
     */
    public AuthDataV3 getRetrieveUserDataReturnV3() {
        return retrieveUserDataReturnV3;
    }

    /**
     * Imposta il valore della proprietà retrieveUserDataReturnV3.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthDataV3 }
     *     
     */
    public void setRetrieveUserDataReturnV3(AuthDataV3 value) {
        this.retrieveUserDataReturnV3 = value;
    }

}
