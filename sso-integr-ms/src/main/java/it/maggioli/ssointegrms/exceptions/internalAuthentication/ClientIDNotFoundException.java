package it.maggioli.ssointegrms.exceptions.internalAuthentication;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class ClientIDNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5769239631390744397L;

	public ClientIDNotFoundException() {
		super();
	}

	public ClientIDNotFoundException(final String message) {
		super(message);
	}

	public ClientIDNotFoundException(final Throwable t) {
		super(t);
	}

	public ClientIDNotFoundException(final String message, final Throwable t) {
		super(message, t);
	}

}
