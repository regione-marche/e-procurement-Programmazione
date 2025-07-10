
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per FlagEsitoCollaudoType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="FlagEsitoCollaudoType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="P"/&gt;
 *     &lt;enumeration value="N"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
public enum FlagEsitoCollaudoType {

    P,
    N;

    public String value() {
        return name();
    }

    public static FlagEsitoCollaudoType fromValue(String v) {
        return valueOf(v);
    }

}
