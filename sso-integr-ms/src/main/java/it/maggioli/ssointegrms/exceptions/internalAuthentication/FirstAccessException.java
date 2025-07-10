package it.maggioli.ssointegrms.exceptions.internalAuthentication;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class FirstAccessException extends RuntimeException {

	private static final long serialVersionUID = 2390776084421438230L;

	public FirstAccessException() {
		super();
	}

	public FirstAccessException(final String message) {
		super(message);
	}

	public FirstAccessException(final Throwable t) {
		super(t);
	}

	public FirstAccessException(final String message, final Throwable t) {
		super(message, t);
	}

}
