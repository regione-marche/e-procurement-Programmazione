package it.maggioli.ssointegrms.exceptions.internalAuthentication;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since apr 09, 2024
 */
public class UnexpectedErrorException extends RuntimeException {

    private static final long serialVersionUID = -2332891953076928987L;

    public UnexpectedErrorException() {
        super();
    }

    public UnexpectedErrorException(final String message) {
        super(message);
    }

    public UnexpectedErrorException(final Throwable t) {
        super(t);
    }

    public UnexpectedErrorException(final String message, final Throwable t) {
        super(message, t);
    }
}
