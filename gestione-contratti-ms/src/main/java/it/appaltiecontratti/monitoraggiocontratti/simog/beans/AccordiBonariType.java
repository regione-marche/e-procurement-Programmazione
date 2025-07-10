
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.util.ArrayList;
import java.util.List;


public class AccordiBonariType {

    protected List<AccordoBonarioType> accordoBonario;

    /**
     * Gets the value of the accordoBonario property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accordoBonario property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccordoBonario().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AccordoBonarioType }
     * 
     * 
     */
    public List<AccordoBonarioType> getAccordoBonario() {
        if (accordoBonario == null) {
            accordoBonario = new ArrayList<AccordoBonarioType>();
        }
        return this.accordoBonario;
    }

}
