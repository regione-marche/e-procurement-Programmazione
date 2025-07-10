package it.maggioli.ssointegrms.exceptions.internalAuthentication;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class BadCredentialsException extends RuntimeException {

	private static final long serialVersionUID = 764271599887839850L;

	public BadCredentialsException() {
		super();
	}

	public BadCredentialsException(final String message) {
		super(message);
	}

	public BadCredentialsException(final Throwable t) {
		super(t);
	}

	public BadCredentialsException(final String message, final Throwable t) {
		super(message, t);
	}

}
