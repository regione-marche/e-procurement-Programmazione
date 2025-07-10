package it.maggioli.ssointegrms.exceptions.internalAuthentication;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class PasswordExpiredException extends RuntimeException {

	private static final long serialVersionUID = 9094042872537423397L;

	public PasswordExpiredException() {
		super();
	}

	public PasswordExpiredException(final String message) {
		super(message);
	}

	public PasswordExpiredException(final Throwable t) {
		super(t);
	}

	public PasswordExpiredException(final String message, final Throwable t) {
		super(message, t);
	}

}
