package it.appaltiecontratti.autenticazione.exceptions;

import java.util.List;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since feb 12, 2024
 */
public class UserPublicRegistrationPasswordException extends RuntimeException {
    private List<String> errors;

    public UserPublicRegistrationPasswordException(final List<String> errors) {
        super();
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
