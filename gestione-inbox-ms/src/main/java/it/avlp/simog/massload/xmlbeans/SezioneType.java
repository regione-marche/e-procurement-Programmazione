
package it.avlp.simog.massload.xmlbeans;

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
