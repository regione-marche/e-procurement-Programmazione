package it.maggioli.ssointegrms.exceptions.gestioneUtenti;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since mar 14, 2024
 */
public class UserPermissionException extends RuntimeException {

    private static final long serialVersionUID = 5546741367168043435L;

    public UserPermissionException() {
        super();
    }

    public UserPermissionException(final String message) {
        super(message);
    }

    public UserPermissionException(final Throwable t) {
        super(t);
    }

    public UserPermissionException(final String message, final Throwable t) {
        super(message, t);
    }

}