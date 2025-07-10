
package it.avlp.simog.massload.xmlbeans;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ResponseConsultaIniziativa complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ResponseConsultaIniziativa"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IniziativaXML" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Iniziativa" type="{xmlbeans.massload.simog.avlp.it}IniziativaType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="success" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="error" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseConsultaIniziativa", propOrder = {
    "iniziativaXML",
    "success",
    "error"
})
public class ResponseConsultaIniziativa {

    @XmlElement(name = "IniziativaXML")
    protected ResponseConsultaIniziativa.IniziativaXML iniziativaXML;
    protected boolean success;
    protected String error;

    /**
     * Recupera il valore della proprietà iniziativaXML.
     * 
     * @return
     *     possible object is
     *     {@link ResponseConsultaIniziativa.IniziativaXML }
     *     
     */
    public ResponseConsultaIniziativa.IniziativaXML getIniziativaXML() {
        return iniziativaXML;
    }

    /**
     * Imposta il valore della proprietà iniziativaXML.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseConsultaIniziativa.IniziativaXML }
     *     
     */
    public void setIniziativaXML(ResponseConsultaIniziativa.IniziativaXML value) {
        this.iniziativaXML = value;
    }

    /**
     * Recupera il valore della proprietà success.
     * 
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Imposta il valore della proprietà success.
     * 
     */
    public void setSuccess(boolean value) {
        this.success = value;
    }

    /**
     * Recupera il valore della proprietà error.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getError() {
        return error;
    }

    /**
     * Imposta il valore della proprietà error.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setError(String value) {
        this.error = value;
    }


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
     *         &lt;element name="Iniziativa" type="{xmlbeans.massload.simog.avlp.it}IniziativaType" maxOccurs="unbounded" minOccurs="0"/&gt;
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
        "iniziativa"
    })
    public static class IniziativaXML {

        @XmlElement(name = "Iniziativa")
        protected List<IniziativaType> iniziativa;

        /**
         * Gets the value of the iniziativa property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the iniziativa property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIniziativa().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link IniziativaType }
         * 
         * 
         */
        public List<IniziativaType> getIniziativa() {
            if (iniziativa == null) {
                iniziativa = new ArrayList<IniziativaType>();
            }
            return this.iniziativa;
        }

    }

}
