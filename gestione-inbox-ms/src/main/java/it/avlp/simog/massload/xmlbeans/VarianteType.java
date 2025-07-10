
package it.avlp.simog.massload.xmlbeans;

import java.util.ArrayList;
import java.util.List;

public class VarianteType {

    protected RecVarianteType variante;
    protected List<RecMotivoVarType> motivi;

    /**
     * Recupera il valore della proprietà variante.
     * 
     * @return
     *     possible object is
     *     {@link RecVarianteType }
     *     
     */
    public RecVarianteType getVariante() {
        return variante;
    }

    /**
     * Imposta il valore della proprietà variante.
     * 
     * @param value
     *     allowed object is
     *     {@link RecVarianteType }
     *     
     */
    public void setVariante(RecVarianteType value) {
        this.variante = value;
    }

    /**
     * Gets the value of the motivi property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the motivi property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMotivi().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RecMotivoVarType }
     * 
     * 
     */
    public List<RecMotivoVarType> getMotivi() {
        if (motivi == null) {
            motivi = new ArrayList<RecMotivoVarType>();
        }
        return this.motivi;
    }

}
