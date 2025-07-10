
package it.avlp.simog.massload.xmlbeans;

import javax.xml.datatype.XMLGregorianCalendar;

public class TrasferimentoType {

    protected XMLGregorianCalendar datacreazioneflusso;
    protected int numschede;

    /**
     * Recupera il valore della proprietà datacreazioneflusso.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATACREAZIONEFLUSSO() {
        return datacreazioneflusso;
    }

    /**
     * Imposta il valore della proprietà datacreazioneflusso.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATACREAZIONEFLUSSO(XMLGregorianCalendar value) {
        this.datacreazioneflusso = value;
    }

    /**
     * Recupera il valore della proprietà numschede.
     * 
     */
    public int getNUMSCHEDE() {
        return numschede;
    }

    /**
     * Imposta il valore della proprietà numschede.
     * 
     */
    public void setNUMSCHEDE(int value) {
        this.numschede = value;
    }

}
