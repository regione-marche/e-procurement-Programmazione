
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

public enum SezioneType {

    PA,
    RA,
    IN,
    CO,
    RE,
    RS,
    RQ;

    public String value() {
        return name();
    }

    public static SezioneType fromValue(String v) {
        return valueOf(v);
    }

}
