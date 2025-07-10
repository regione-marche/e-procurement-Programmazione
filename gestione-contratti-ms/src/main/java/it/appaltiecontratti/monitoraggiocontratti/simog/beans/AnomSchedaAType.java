
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per AnomScheda_AType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AnomScheda_AType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Anomalia" type="{xmlbeans.massload.simog.avlp.it}AnomaliaType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="IdScheda" type="{xmlbeans.massload.simog.avlp.it}RecIdSchedaInsType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="CUPLOTTO" type="{xmlbeans.massload.simog.avlp.it}CUPLOTTOType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CIG use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}PROGRESSIVO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CUI"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class AnomSchedaAType {

    protected List<AnomaliaType> anomalia;
    protected List<RecIdSchedaInsType> idScheda;
    protected CUPLOTTOType cuplotto;
    protected String cig;
    protected String progressivo;
    protected String cui;

    /**
     * Gets the value of the anomalia property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the anomalia property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnomalia().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AnomaliaType }
     * 
     * 
     */
    public List<AnomaliaType> getAnomalia() {
        if (anomalia == null) {
            anomalia = new ArrayList<AnomaliaType>();
        }
        return this.anomalia;
    }

    /**
     * Gets the value of the idScheda property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idScheda property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdScheda().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RecIdSchedaInsType }
     * 
     * 
     */
    public List<RecIdSchedaInsType> getIdScheda() {
        if (idScheda == null) {
            idScheda = new ArrayList<RecIdSchedaInsType>();
        }
        return this.idScheda;
    }

    /**
     * Recupera il valore della proprietà cuplotto.
     * 
     * @return
     *     possible object is
     *     {@link CUPLOTTOType }
     *     
     */
    public CUPLOTTOType getCUPLOTTO() {
        return cuplotto;
    }

    /**
     * Imposta il valore della proprietà cuplotto.
     * 
     * @param value
     *     allowed object is
     *     {@link CUPLOTTOType }
     *     
     */
    public void setCUPLOTTO(CUPLOTTOType value) {
        this.cuplotto = value;
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

    /**
     * Recupera il valore della proprietà progressivo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROGRESSIVO() {
        return progressivo;
    }

    /**
     * Imposta il valore della proprietà progressivo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROGRESSIVO(String value) {
        this.progressivo = value;
    }

    /**
     * Recupera il valore della proprietà cui.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCUI() {
        return cui;
    }

    /**
     * Imposta il valore della proprietà cui.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCUI(String value) {
        this.cui = value;
    }

}
