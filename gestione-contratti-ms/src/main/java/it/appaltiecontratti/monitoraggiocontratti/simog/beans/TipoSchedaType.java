
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

public enum TipoSchedaType {

    F,
    S,
    L;

    public String value() {
        return name();
    }

    public static TipoSchedaType fromValue(String v) {
        return valueOf(v);
    }

}
