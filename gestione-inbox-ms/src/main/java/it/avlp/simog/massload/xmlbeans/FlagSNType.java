
package it.avlp.simog.massload.xmlbeans;

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
