package it.appaltiecontratti.gestionereportsms.exceptions;

/**
 * @author Cristiano Perin
 */
public class TokenInvalidException extends Exception {

    private static final long serialVersionUID = 4395473139138882907L;

    public TokenInvalidException() {
        super();
    }

    public TokenInvalidException(String message) {
        super(message);
    }

    public TokenInvalidException(Throwable t) {
        super(t);
    }

    public TokenInvalidException(String message, Throwable t) {
        super(message, t);
    }

}
