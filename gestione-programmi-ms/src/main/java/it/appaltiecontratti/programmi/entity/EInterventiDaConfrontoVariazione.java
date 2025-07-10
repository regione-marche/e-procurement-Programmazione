package it.appaltiecontratti.programmi.entity;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since lug 06, 2023
 */
public enum EInterventiDaConfrontoVariazione {
    AGGIUNTO("AGGIUNTO"),
    ELIMINATO("ELIMINATO"),
    VARIATO_QUADRO_ECONOMICO("VARIATO_QUADRO_ECONOMICO"),
    VARIATA_ANNUALITA("VARIATA_ANNUALITA"),
    TABELLATO("TABELLATO");

    private final String value;

    private EInterventiDaConfrontoVariazione(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "EInterventiDaConfrontoVariazione{" +
                "value='" + value + '\'' +
                '}';
    }
}
