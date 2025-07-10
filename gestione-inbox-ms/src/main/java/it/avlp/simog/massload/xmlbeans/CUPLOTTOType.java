
package it.avlp.simog.massload.xmlbeans;

import java.util.ArrayList;
import java.util.List;

public class CUPLOTTOType {

    protected List<DatiCUPType> codicicup;
    protected String cig;

    /**
     * Gets the value of the codicicup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the codicicup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCODICICUP().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiCUPType }
     * 
     * 
     */
    public List<DatiCUPType> getCODICICUP() {
        if (codicicup == null) {
            codicicup = new ArrayList<DatiCUPType>();
        }
        return this.codicicup;
    }

    /**
     * Recupera il valore della proprietà cig.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCIG() {
        return cig;
    }

    /**
     * Imposta il valore della proprietà cig.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCIG(String value) {
        this.cig = value;
    }

}
