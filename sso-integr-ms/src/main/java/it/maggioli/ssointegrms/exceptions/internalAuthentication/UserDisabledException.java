package it.maggioli.ssointegrms.exceptions.internalAuthentication;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class UserDisabledException extends RuntimeException {

	private static final long serialVersionUID = 5442973243458249344L;

	public UserDisabledException() {
		super();
	}

	public UserDisabledException(final String message) {
		super(message);
	}

	public UserDisabledException(final Throwable t) {
		super(t);
	}

	public UserDisabledException(final String message, final Throwable t) {
		super(message, t);
	}

}
