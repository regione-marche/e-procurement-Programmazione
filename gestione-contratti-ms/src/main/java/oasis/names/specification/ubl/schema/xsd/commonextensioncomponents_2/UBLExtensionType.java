//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java per UBLExtensionType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="UBLExtensionType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}ExtensionContent"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UBLExtensionType", propOrder = {
    "extensionContent"
})
public class UBLExtensionType {

    @XmlElement(name = "ExtensionContent", required = true)
    protected ExtensionContentType extensionContent;

    /**
     * 
     *             The definition of the extension content.
     *           
     * 
     * @return
     *     possible object is
     *     {@link ExtensionContentType }
     *     
     */
    public ExtensionContentType getExtensionContent() {
        return extensionContent;
    }

    /**
     * Imposta il valore della proprietà extensionContent.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionContentType }
     *     
     */
    public void setExtensionContent(ExtensionContentType value) {
        this.extensionContent = value;
    }

}
