
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


public class ResponseLoaderAppalto {

    protected ResponseLoaderAppalto.FeedBack feedBack;

    /**
     * Recupera il valore della proprietà feedBack.
     * 
     * @return
     *     possible object is
     *     {@link ResponseLoaderAppalto.FeedBack }
     *     
     */
    public ResponseLoaderAppalto.FeedBack getFeedBack() {
        return feedBack;
    }

    /**
     * Imposta il valore della proprietà feedBack.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseLoaderAppalto.FeedBack }
     *     
     */
    public void setFeedBack(ResponseLoaderAppalto.FeedBack value) {
        this.feedBack = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="InfoFlusso" type="{xmlbeans.massload.simog.avlp.it}FlussoType"/&gt;
     *         &lt;element name="AnomalieSchede" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;extension base="{xmlbeans.massload.simog.avlp.it}AnomScheda_AType"&gt;
     *                 &lt;sequence&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/extension&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "infoFlusso",
        "anomalieSchede"
    })
    public static class FeedBack {

        protected FlussoType infoFlusso;
        protected List<ResponseLoaderAppalto.FeedBack.AnomalieSchede> anomalieSchede;

        /**
         * Recupera il valore della proprietà infoFlusso.
         * 
         * @return
         *     possible object is
         *     {@link FlussoType }
         *     
         */
        public FlussoType getInfoFlusso() {
            return infoFlusso;
        }

        /**
         * Imposta il valore della proprietà infoFlusso.
         * 
         * @param value
         *     allowed object is
         *     {@link FlussoType }
         *     
         */
        public void setInfoFlusso(FlussoType value) {
            this.infoFlusso = value;
        }

        /**
         * Gets the value of the anomalieSchede property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the anomalieSchede property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAnomalieSchede().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ResponseLoaderAppalto.FeedBack.AnomalieSchede }
         * 
         * 
         */
        public List<ResponseLoaderAppalto.FeedBack.AnomalieSchede> getAnomalieSchede() {
            if (anomalieSchede == null) {
                anomalieSchede = new ArrayList<ResponseLoaderAppalto.FeedBack.AnomalieSchede>();
            }
            return this.anomalieSchede;
        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;extension base="{xmlbeans.massload.simog.avlp.it}AnomScheda_AType"&gt;
         *       &lt;sequence&gt;
         *       &lt;/sequence&gt;
         *     &lt;/extension&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class AnomalieSchede
            extends AnomSchedaAType
        {


        }

    }

}
