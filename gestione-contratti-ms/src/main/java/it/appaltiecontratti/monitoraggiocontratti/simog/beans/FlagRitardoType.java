
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


public enum FlagRitardoType {

    P,
    A,
    R;

    public String value() {
        return name();
    }

    public static FlagRitardoType fromValue(String v) {
        return valueOf(v);
    }

}
