
package it.avlp.simog.massload.xmlbeans;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TerritorioType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="TerritorioType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CodRegioneIstat" type="{xmlbeans.massload.simog.avlp.it}LuogoIstatType" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TerritorioType", propOrder = {
    "codRegioneIstat"
})
public class TerritorioType {

    @XmlElement(name = "CodRegioneIstat", required = true)
    protected List<String> codRegioneIstat;

    /**
     * Gets the value of the codRegioneIstat property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the codRegioneIstat property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCodRegioneIstat().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCodRegioneIstat() {
        if (codRegioneIstat == null) {
            codRegioneIstat = new ArrayList<String>();
        }
        return this.codRegioneIstat;
    }

}
