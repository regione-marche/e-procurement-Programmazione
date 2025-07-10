
package it.avlp.simog.massload.xmlbeans;

import java.util.ArrayList;
import java.util.List;

public class Collaborazioni {

    protected List<Collaborazione> collaborazioni;

    /**
     * Gets the value of the collaborazioni property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the collaborazioni property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCollaborazioni().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Collaborazione }
     * 
     * 
     */
    public List<Collaborazione> getCollaborazioni() {
        if (collaborazioni == null) {
            collaborazioni = new ArrayList<Collaborazione>();
        }
        return this.collaborazioni;
    }

}
