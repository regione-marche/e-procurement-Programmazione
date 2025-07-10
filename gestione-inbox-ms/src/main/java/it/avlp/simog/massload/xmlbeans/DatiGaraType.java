
package it.avlp.simog.massload.xmlbeans;

import java.util.ArrayList;
import java.util.List;

public class DatiGaraType {

    protected GaraType gara;
    protected LottoType lotto;
    protected List<ReqGaraType> requisito;

    /**
     * Recupera il valore della proprietà gara.
     * 
     * @return
     *     possible object is
     *     {@link GaraType }
     *     
     */
    public GaraType getGara() {
        return gara;
    }

    /**
     * Imposta il valore della proprietà gara.
     * 
     * @param value
     *     allowed object is
     *     {@link GaraType }
     *     
     */
    public void setGara(GaraType value) {
        this.gara = value;
    }

    /**
     * Recupera il valore della proprietà lotto.
     * 
     * @return
     *     possible object is
     *     {@link LottoType }
     *     
     */
    public LottoType getLotto() {
        return lotto;
    }

    /**
     * Imposta il valore della proprietà lotto.
     * 
     * @param value
     *     allowed object is
     *     {@link LottoType }
     *     
     */
    public void setLotto(LottoType value) {
        this.lotto = value;
    }

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
