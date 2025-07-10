package it.appaltiecontratti.gestionereportsms.exceptions;

import java.io.Serial;

/**
 * @author andrea.chinellato
 * */

public class UserEditForbiddenException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 8354485067012610785L;

    public UserEditForbiddenException() {
        super();
    }

    public UserEditForbiddenException(final String message) {
        super(message);
    }

    public UserEditForbiddenException(final Throwable t) {
        super(t);
    }

    public UserEditForbiddenException(final String message, final Throwable t) {
        super(message, t);
    }
}
