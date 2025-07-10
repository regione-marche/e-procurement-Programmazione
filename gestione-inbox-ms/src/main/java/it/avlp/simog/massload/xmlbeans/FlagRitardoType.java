
package it.avlp.simog.massload.xmlbeans;

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
