
package it.avlp.simog.massload.xmlbeans;

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
