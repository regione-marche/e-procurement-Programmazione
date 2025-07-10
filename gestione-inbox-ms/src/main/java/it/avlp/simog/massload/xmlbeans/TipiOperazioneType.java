
package it.avlp.simog.massload.xmlbeans;

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
