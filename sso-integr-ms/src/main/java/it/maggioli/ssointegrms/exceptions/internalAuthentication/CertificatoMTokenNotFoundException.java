package it.maggioli.ssointegrms.exceptions.internalAuthentication;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since apr 09, 2024
 */
public class CertificatoMTokenNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 7056198131230173410L;

    public CertificatoMTokenNotFoundException() {
        super();
    }

    public CertificatoMTokenNotFoundException(final String message) {
        super(message);
    }

    public CertificatoMTokenNotFoundException(final Throwable t) {
        super(t);
    }

    public CertificatoMTokenNotFoundException(final String message, final Throwable t) {
        super(message, t);
    }
}
