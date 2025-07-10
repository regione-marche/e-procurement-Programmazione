package it.maggioli.ssointegrms.exceptions.internalAuthentication;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since apr 09, 2024
 */
public class MTokenLoginFailedException extends RuntimeException {
    private static final long serialVersionUID = -238823659329619525L;

    public MTokenLoginFailedException() {
        super();
    }

    public MTokenLoginFailedException(final String message) {
        super(message);
    }

    public MTokenLoginFailedException(final Throwable t) {
        super(t);
    }

    public MTokenLoginFailedException(final String message, final Throwable t) {
        super(message, t);
    }
}
