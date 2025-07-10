
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


public enum FlagTCType {

    R,
    S;

    public String value() {
        return name();
    }

    public static FlagTCType fromValue(String v) {
        return valueOf(v);
    }

}
