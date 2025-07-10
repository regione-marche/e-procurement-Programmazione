
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


public enum FlagSNType {

    S,
    N;

    public String value() {
        return name();
    }

    public static FlagSNType fromValue(String v) {
        return valueOf(v);
    }

}
