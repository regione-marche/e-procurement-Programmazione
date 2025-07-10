
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

public enum LivelloType {

    ERRORE,
    ATTENZIONE,
    AVVISO;

    public String value() {
        return name();
    }

    public static LivelloType fromValue(String v) {
        return valueOf(v);
    }

}
