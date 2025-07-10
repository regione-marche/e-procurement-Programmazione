
package it.avlp.simog.massload.xmlbeans;

public enum LivelloType {

    ERRORE,
    ATTENZIONE,
    AVVISO;

    public String value() {
        return name();
    }

    public static LivelloType fromValue(String v) {
        return valueOf(v);
    }

}
