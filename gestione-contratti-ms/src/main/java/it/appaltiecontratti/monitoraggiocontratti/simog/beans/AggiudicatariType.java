
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

public class AggiudicatariType {

    protected List<AggiudicatarioType> aggiudicatario;

    /**
     * Gets the value of the aggiudicatario property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aggiudicatario property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAggiudicatario().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AggiudicatarioType }
     * 
     * 
     */
    public List<AggiudicatarioType> getAggiudicatario() {
        if (aggiudicatario == null) {
            aggiudicatario = new ArrayList<AggiudicatarioType>();
        }
        return this.aggiudicatario;
    }

}
