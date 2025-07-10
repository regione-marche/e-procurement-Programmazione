package it.maggioli.ssointegrms.exceptions.gestioneUtenti;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class UserEditForbiddenException extends RuntimeException {

	private static final long serialVersionUID = -8109449097492464029L;

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