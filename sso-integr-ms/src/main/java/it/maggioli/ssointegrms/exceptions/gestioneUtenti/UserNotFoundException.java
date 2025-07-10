package it.maggioli.ssointegrms.exceptions.gestioneUtenti;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7169764269186650413L;

	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(final String message) {
		super(message);
	}

	public UserNotFoundException(final Throwable t) {
		super(t);
	}

	public UserNotFoundException(final String message, final Throwable t) {
		super(message, t);
	}

}