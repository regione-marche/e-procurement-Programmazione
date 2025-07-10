package it.maggioli.ssointegrms.exceptions.gestioneUtenti;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since mar 20, 2024
 */
public class NoProfilesSelectedException extends RuntimeException {
    private static final long serialVersionUID = -4316714563725948759L;
    public NoProfilesSelectedException() {
        super();
    }

    public NoProfilesSelectedException(final String message) {
        super(message);
    }

    public NoProfilesSelectedException(final Throwable t) {
        super(t);
    }

    public NoProfilesSelectedException(final String message, final Throwable t) {
        super(message, t);
    }
}
