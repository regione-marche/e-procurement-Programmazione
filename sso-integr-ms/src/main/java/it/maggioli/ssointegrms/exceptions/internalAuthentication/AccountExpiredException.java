package it.maggioli.ssointegrms.exceptions.internalAuthentication;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class AccountExpiredException extends RuntimeException {

	private static final long serialVersionUID = 2297549813502173002L;

	public AccountExpiredException() {
		super();
	}

	public AccountExpiredException(final String message) {
		super(message);
	}

	public AccountExpiredException(final Throwable t) {
		super(t);
	}

	public AccountExpiredException(final String message, final Throwable t) {
		super(message, t);
	}

}
