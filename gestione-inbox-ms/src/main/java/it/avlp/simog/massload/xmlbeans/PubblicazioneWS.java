//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.11 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2019.10.03 alle 11:25:07 AM CEST 
//


package it.avlp.simog.massload.xmlbeans;

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
 *         &lt;element name="Pubblicazione" type="{xmlbeans.massload.simog.avlp.it}PubblicazioneType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class PubblicazioneWS {

    protected PubblicazioneType pubblicazione;

    /**
     * Recupera il valore della proprietà pubblicazione.
     * 
     * @return
     *     possible object is
     *     {@link PubblicazioneType }
     *     
     */
    public PubblicazioneType getPubblicazione() {
        return pubblicazione;
    }

    /**
     * Imposta il valore della proprietà pubblicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link PubblicazioneType }
     *     
     */
    public void setPubblicazione(PubblicazioneType value) {
        this.pubblicazione = value;
    }

}
