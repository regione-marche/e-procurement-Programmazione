
package it.avlp.simog.massload.xmlbeans;

/**
 * <p>Classe Java per PosizioneType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PosizioneType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CODICE_FISCALE_AGGIUDICATARIO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CODICE_STATO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CODICE_INPS"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CODICE_INAIL"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CODICE_CASSA"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class PosizioneType {

    protected String codicefiscaleaggiudicatario;
    protected String codicestato;
    protected String codiceinps;
    protected String codiceinail;
    protected String codicecassa;

    /**
     * Recupera il valore della proprietà codicefiscaleaggiudicatario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICEFISCALEAGGIUDICATARIO() {
        return codicefiscaleaggiudicatario;
    }

    /**
     * Imposta il valore della proprietà codicefiscaleaggiudicatario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICEFISCALEAGGIUDICATARIO(String value) {
        this.codicefiscaleaggiudicatario = value;
    }

    /**
     * Recupera il valore della proprietà codicestato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICESTATO() {
        return codicestato;
    }

    /**
     * Imposta il valore della proprietà codicestato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICESTATO(String value) {
        this.codicestato = value;
    }

    /**
     * Recupera il valore della proprietà codiceinps.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICEINPS() {
        return codiceinps;
    }

    /**
     * Imposta il valore della proprietà codiceinps.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICEINPS(String value) {
        this.codiceinps = value;
    }

    /**
     * Recupera il valore della proprietà codiceinail.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICEINAIL() {
        return codiceinail;
    }

    /**
     * Imposta il valore della proprietà codiceinail.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICEINAIL(String value) {
        this.codiceinail = value;
    }

    /**
     * Recupera il valore della proprietà codicecassa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICECASSA() {
        return codicecassa;
    }

    /**
     * Imposta il valore della proprietà codicecassa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICECASSA(String value) {
        this.codicecassa = value;
    }

}
