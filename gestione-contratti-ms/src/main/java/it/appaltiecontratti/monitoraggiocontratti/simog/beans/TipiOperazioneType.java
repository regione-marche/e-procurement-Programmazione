
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

public enum TipiOperazioneType {

    INSERIMENTO,
    MODIFICA,
    ELIMINAZIONE,
    VARIAZIONE_ANAG,
    VARIAZIONE_SA;

    public String value() {
        return name();
    }

    public static TipiOperazioneType fromValue(String v) {
        return valueOf(v);
    }

}
