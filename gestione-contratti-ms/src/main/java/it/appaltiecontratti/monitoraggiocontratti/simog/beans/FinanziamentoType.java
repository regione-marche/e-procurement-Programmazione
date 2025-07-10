
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

public class FinanziamentoType {

    protected String idfinanziamento;
    protected BigDecimal importofinanziamento;

    /**
     * Recupera il valore della proprietà idfinanziamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDFINANZIAMENTO() {
        return idfinanziamento;
    }

    /**
     * Imposta il valore della proprietà idfinanziamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDFINANZIAMENTO(String value) {
        this.idfinanziamento = value;
    }

    /**
     * Recupera il valore della proprietà importofinanziamento.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPORTOFINANZIAMENTO() {
        return importofinanziamento;
    }

    /**
     * Imposta il valore della proprietà importofinanziamento.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPORTOFINANZIAMENTO(BigDecimal value) {
        this.importofinanziamento = value;
    }

}
