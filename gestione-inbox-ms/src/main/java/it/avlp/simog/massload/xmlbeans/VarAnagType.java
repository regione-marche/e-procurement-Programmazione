
package it.avlp.simog.massload.xmlbeans;

import java.util.ArrayList;
import java.util.List;


public class VarAnagType {

    protected List<RecVarAnagType> variazioneAnag;

    /**
     * Gets the value of the variazioneAnag property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the variazioneAnag property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVariazioneAnag().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RecVarAnagType }
     * 
     * 
     */
    public List<RecVarAnagType> getVariazioneAnag() {
        if (variazioneAnag == null) {
            variazioneAnag = new ArrayList<RecVarAnagType>();
        }
        return this.variazioneAnag;
    }

}
