
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


public class ReqGaraType {

    protected List<String> cig;
    protected List<ReqDocType> documento;
    protected String codiceDettaglio;
    protected String descrizione;
    protected String valore;
    protected FlagSNType flagEsclusione;
    protected FlagSNType flagComprovaOfferta;
    protected FlagSNType flagAvvalimento;
    protected FlagSNType flagBandoTipo;
    protected FlagSNType flagRiservatezza;

    /**
     * Gets the value of the cig property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cig property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCIG().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCIG() {
        if (cig == null) {
            cig = new ArrayList<String>();
        }
        return this.cig;
    }

    /**
     * Gets the value of the documento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDOCUMENTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReqDocType }
     * 
     * 
     */
    public List<ReqDocType> getDOCUMENTO() {
        if (documento == null) {
            documento = new ArrayList<ReqDocType>();
        }
        return this.documento;
    }

    /**
     * Recupera il valore della proprietà codiceDettaglio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceDettaglio() {
        return codiceDettaglio;
    }

    /**
     * Imposta il valore della proprietà codiceDettaglio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceDettaglio(String value) {
        this.codiceDettaglio = value;
    }

    /**
     * Recupera il valore della proprietà descrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprietà descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della proprietà valore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValore() {
        return valore;
    }

    /**
     * Imposta il valore della proprietà valore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValore(String value) {
        this.valore = value;
    }

    /**
     * Recupera il valore della proprietà flagEsclusione.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFlagEsclusione() {
        return flagEsclusione;
    }

    /**
     * Imposta il valore della proprietà flagEsclusione.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFlagEsclusione(FlagSNType value) {
        this.flagEsclusione = value;
    }

    /**
     * Recupera il valore della proprietà flagComprovaOfferta.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFlagComprovaOfferta() {
        return flagComprovaOfferta;
    }

    /**
     * Imposta il valore della proprietà flagComprovaOfferta.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFlagComprovaOfferta(FlagSNType value) {
        this.flagComprovaOfferta = value;
    }

    /**
     * Recupera il valore della proprietà flagAvvalimento.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFlagAvvalimento() {
        return flagAvvalimento;
    }

    /**
     * Imposta il valore della proprietà flagAvvalimento.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFlagAvvalimento(FlagSNType value) {
        this.flagAvvalimento = value;
    }

    /**
     * Recupera il valore della proprietà flagBandoTipo.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFlagBandoTipo() {
        return flagBandoTipo;
    }

    /**
     * Imposta il valore della proprietà flagBandoTipo.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFlagBandoTipo(FlagSNType value) {
        this.flagBandoTipo = value;
    }

    /**
     * Recupera il valore della proprietà flagRiservatezza.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getFlagRiservatezza() {
        return flagRiservatezza;
    }

    /**
     * Imposta il valore della proprietà flagRiservatezza.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setFlagRiservatezza(FlagSNType value) {
        this.flagRiservatezza = value;
    }

}
