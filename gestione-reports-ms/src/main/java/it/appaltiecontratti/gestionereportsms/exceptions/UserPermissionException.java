package it.appaltiecontratti.gestionereportsms.exceptions;

import java.io.Serial;

/**
 * @author andrea.chinellato
 * */

public class UserPermissionException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -4581924079067937343L;

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
