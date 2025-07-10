package it.maggioli.ssointegrms.exceptions.gestioneUtenti;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @since mar 18, 2024
 */
public class NoUffintSelectedException extends RuntimeException {
    private static final long serialVersionUID = 674586846832291837L;

    public NoUffintSelectedException() {
        super();
    }

    public NoUffintSelectedException(final String message) {
        super(message);
    }

    public NoUffintSelectedException(final Throwable t) {
        super(t);
    }

    public NoUffintSelectedException(final String message, final Throwable t) {
        super(message, t);
    }
}
