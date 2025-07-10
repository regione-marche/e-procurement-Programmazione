
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DittaAusiliariaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DittaAusiliariaType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}FLAG_AVVALIMENTO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CODICE_FISCALE_AGGIUDICATARIO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CODICE_STATO_AGGIUDICATARIO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CODICE_FISCALE_AUSILIARIA use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CODICE_STATO_AUSILIARIA use="required""/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class DittaAusiliariaType {

    protected String flagavvalimento;
    protected String codicefiscaleaggiudicatario;
    protected String codicestatoaggiudicatario;
    protected String codicefiscaleausiliaria;
    protected String codicestatoausiliaria;

    /**
     * Recupera il valore della proprietà flagavvalimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFLAGAVVALIMENTO() {
        return flagavvalimento;
    }

    /**
     * Imposta il valore della proprietà flagavvalimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFLAGAVVALIMENTO(String value) {
        this.flagavvalimento = value;
    }

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
     * Recupera il valore della proprietà codicestatoaggiudicatario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICESTATOAGGIUDICATARIO() {
        return codicestatoaggiudicatario;
    }

    /**
     * Imposta il valore della proprietà codicestatoaggiudicatario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICESTATOAGGIUDICATARIO(String value) {
        this.codicestatoaggiudicatario = value;
    }

    /**
     * Recupera il valore della proprietà codicefiscaleausiliaria.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICEFISCALEAUSILIARIA() {
        return codicefiscaleausiliaria;
    }

    /**
     * Imposta il valore della proprietà codicefiscaleausiliaria.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICEFISCALEAUSILIARIA(String value) {
        this.codicefiscaleausiliaria = value;
    }

    /**
     * Recupera il valore della proprietà codicestatoausiliaria.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODICESTATOAUSILIARIA() {
        return codicestatoausiliaria;
    }

    /**
     * Imposta il valore della proprietà codicestatoausiliaria.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODICESTATOAUSILIARIA(String value) {
        this.codicestatoausiliaria = value;
    }

}
