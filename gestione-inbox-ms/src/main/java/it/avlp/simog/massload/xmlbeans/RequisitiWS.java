//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.11 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2019.10.03 alle 11:25:07 AM CEST 
//


package it.avlp.simog.massload.xmlbeans;

import java.util.ArrayList;
import java.util.List;

public class RequisitiWS {

    protected List<ReqGaraType> requisito;

    /**
     * Gets the value of the requisito property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requisito property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequisito().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReqGaraType }
     * 
     * 
     */
    public List<ReqGaraType> getRequisito() {
        if (requisito == null) {
            requisito = new ArrayList<ReqGaraType>();
        }
        return this.requisito;
    }

}
