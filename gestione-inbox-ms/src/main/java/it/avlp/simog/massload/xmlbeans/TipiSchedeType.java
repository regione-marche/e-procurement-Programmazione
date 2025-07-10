
package it.avlp.simog.massload.xmlbeans;

public enum TipiSchedeType {

    DATI_COMUNI,
    AGGIUDICAZIONE,
    ESCLUSO,
    SOTTOSOGLIA,
    ADESIONE,
    FASE_INIZIALE,
    STIPULA,
    STATO_AVANZAMENTO,
    FINE_LAVORI,
    COLLAUDO,
    IPOTESI_RECESSO,
    ACCORDO_BONARIO,
    SOSPENSIONE,
    VARIANTE,
    SUBAPPALTO;

    public String value() {
        return name();
    }

    public static TipiSchedeType fromValue(String v) {
        return valueOf(v);
    }

}
