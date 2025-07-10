
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
 *         &lt;element name="retrieveUserDataReturnV2" type="{http://service.authservice.cedaf.it}AuthDataV2"/&gt;
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
    "retrieveUserDataReturnV2"
})
@XmlRootElement(name = "retrieveUserDataV2Response")
public class RetrieveUserDataV2Response {

    @XmlElement(required = true)
    protected AuthDataV2 retrieveUserDataReturnV2;

    /**
     * Recupera il valore della proprietà retrieveUserDataReturnV2.
     * 
     * @return
     *     possible object is
     *     {@link AuthDataV2 }
     *     
     */
    public AuthDataV2 getRetrieveUserDataReturnV2() {
        return retrieveUserDataReturnV2;
    }

    /**
     * Imposta il valore della proprietà retrieveUserDataReturnV2.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthDataV2 }
     *     
     */
    public void setRetrieveUserDataReturnV2(AuthDataV2 value) {
        this.retrieveUserDataReturnV2 = value;
    }

}
