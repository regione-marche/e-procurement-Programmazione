package it.maggioli.ssointegrms.exceptions.gestioneUtenti;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 7169764269186650413L;

	public UserAlreadyExistsException() {
		super();
	}

	public UserAlreadyExistsException(final String message) {
		super(message);
	}

	public UserAlreadyExistsException(final Throwable t) {
		super(t);
	}

	public UserAlreadyExistsException(final String message, final Throwable t) {
		super(message, t);
	}

}