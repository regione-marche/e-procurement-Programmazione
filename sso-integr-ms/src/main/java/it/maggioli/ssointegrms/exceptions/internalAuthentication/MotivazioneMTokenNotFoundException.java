package it.maggioli.ssointegrms.exceptions.internalAuthentication;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since apr 09, 2024
 */
public class MotivazioneMTokenNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 263388681502410033L;

    public MotivazioneMTokenNotFoundException() {
        super();
    }

    public MotivazioneMTokenNotFoundException(final String message) {
        super(message);
    }

    public MotivazioneMTokenNotFoundException(final Throwable t) {
        super(t);
    }

    public MotivazioneMTokenNotFoundException(final String message, final Throwable t) {
        super(message, t);
    }
}
