
package it.avlp.simog.massload.xmlbeans;

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
