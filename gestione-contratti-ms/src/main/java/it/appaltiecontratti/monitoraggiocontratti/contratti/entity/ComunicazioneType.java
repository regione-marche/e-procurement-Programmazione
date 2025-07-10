package it.appaltiecontratti.monitoraggiocontratti.contratti.entity;

import java.math.BigDecimal;

import javax.xml.datatype.XMLGregorianCalendar;

import it.appaltiecontratti.monitoraggiocontratti.simog.beans.ElencoCategMercType;
public class ComunicazioneType {

    protected String cig;
    protected String codiceFattispecieContrattuale;
    protected String fattispecieContrattuale;
    protected BigDecimal importo;
    protected String oggetto;
    protected String codiceProceduraSceltaContraente;
    protected String proceduraSceltaContraente;
    protected String codiceClassificazioneGara;
    protected String classificazioneGara;
    protected String cigAccordoQuadro;
    protected String cup;
    protected String motivoRichCigComuni;
    protected String motivoRichCigCatmerc;
    protected XMLGregorianCalendar dataOperazione;
    protected String stato;
    protected ElencoCategMercType categorieMerc;

    /**
     * Gets the value of the cig property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCig() {
        return cig;
    }

    /**
     * Sets the value of the cig property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCig(String value) {
        this.cig = value;
    }

    /**
     * Gets the value of the codiceFattispecieContrattuale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFattispecieContrattuale() {
        return codiceFattispecieContrattuale;
    }

    /**
     * Sets the value of the codiceFattispecieContrattuale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFattispecieContrattuale(String value) {
        this.codiceFattispecieContrattuale = value;
    }

    /**
     * Gets the value of the fattispecieContrattuale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFattispecieContrattuale() {
        return fattispecieContrattuale;
    }

    /**
     * Sets the value of the fattispecieContrattuale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFattispecieContrattuale(String value) {
        this.fattispecieContrattuale = value;
    }

    /**
     * Gets the value of the importo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImporto() {
        return importo;
    }

    /**
     * Sets the value of the importo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImporto(BigDecimal value) {
        this.importo = value;
    }

    /**
     * Gets the value of the oggetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * Sets the value of the oggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }

    /**
     * Gets the value of the codiceProceduraSceltaContraente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceProceduraSceltaContraente() {
        return codiceProceduraSceltaContraente;
    }

    /**
     * Sets the value of the codiceProceduraSceltaContraente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceProceduraSceltaContraente(String value) {
        this.codiceProceduraSceltaContraente = value;
    }

    /**
     * Gets the value of the proceduraSceltaContraente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProceduraSceltaContraente() {
        return proceduraSceltaContraente;
    }

    /**
     * Sets the value of the proceduraSceltaContraente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProceduraSceltaContraente(String value) {
        this.proceduraSceltaContraente = value;
    }

    /**
     * Gets the value of the codiceClassificazioneGara property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceClassificazioneGara() {
        return codiceClassificazioneGara;
    }

    /**
     * Sets the value of the codiceClassificazioneGara property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceClassificazioneGara(String value) {
        this.codiceClassificazioneGara = value;
    }

    /**
     * Gets the value of the classificazioneGara property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassificazioneGara() {
        return classificazioneGara;
    }

    /**
     * Sets the value of the classificazioneGara property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassificazioneGara(String value) {
        this.classificazioneGara = value;
    }

    /**
     * Gets the value of the cigAccordoQuadro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCigAccordoQuadro() {
        return cigAccordoQuadro;
    }

    /**
     * Sets the value of the cigAccordoQuadro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCigAccordoQuadro(String value) {
        this.cigAccordoQuadro = value;
    }

    /**
     * Gets the value of the cup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCup() {
        return cup;
    }

    /**
     * Sets the value of the cup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCup(String value) {
        this.cup = value;
    }

    /**
     * Gets the value of the motivoRichCigComuni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivoRichCigComuni() {
        return motivoRichCigComuni;
    }

    /**
     * Sets the value of the motivoRichCigComuni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivoRichCigComuni(String value) {
        this.motivoRichCigComuni = value;
    }

    /**
     * Gets the value of the motivoRichCigCatmerc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivoRichCigCatmerc() {
        return motivoRichCigCatmerc;
    }

    /**
     * Sets the value of the motivoRichCigCatmerc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivoRichCigCatmerc(String value) {
        this.motivoRichCigCatmerc = value;
    }

    /**
     * Gets the value of the dataOperazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOperazione() {
        return dataOperazione;
    }

    /**
     * Sets the value of the dataOperazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOperazione(XMLGregorianCalendar value) {
        this.dataOperazione = value;
    }

    /**
     * Gets the value of the stato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStato() {
        return stato;
    }

    /**
     * Sets the value of the stato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStato(String value) {
        this.stato = value;
    }

    /**
     * Gets the value of the categorieMerc property.
     * 
     * @return
     *     possible object is
     *     {@link ElencoCategMercType }
     *     
     */
    public ElencoCategMercType getCategorieMerc() {
        return categorieMerc;
    }

    /**
     * Sets the value of the categorieMerc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ElencoCategMercType }
     *     
     */
    public void setCategorieMerc(ElencoCategMercType value) {
        this.categorieMerc = value;
    }

}
