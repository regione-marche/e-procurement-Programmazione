
package it.avlp.simog.massload.xmlbeans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per presaInCaricoGaraDelegataResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="presaInCaricoGaraDelegataResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="return" type="{xmlbeans.massload.simog.avlp.it}ResponsePresaInCarico" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "presaInCaricoGaraDelegataResponse", propOrder = {
    "_return"
})
public class PresaInCaricoGaraDelegataResponse {

    @XmlElement(name = "return")
    protected ResponsePresaInCarico _return;

    /**
     * Recupera il valore della proprietà return.
     * 
     * @return
     *     possible object is
     *     {@link ResponsePresaInCarico }
     *     
     */
    public ResponsePresaInCarico getReturn() {
        return _return;
    }

    /**
     * Imposta il valore della proprietà return.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponsePresaInCarico }
     *     
     */
    public void setReturn(ResponsePresaInCarico value) {
        this._return = value;
    }

}
