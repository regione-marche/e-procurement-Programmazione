package it.maggioli.ssointegrms.exceptions.gestioneUtenti;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class UserDataUltimoAccessoNotNullException extends RuntimeException {

	private static final long serialVersionUID = 590343429213909038L;

	public UserDataUltimoAccessoNotNullException() {
		super();
	}

	public UserDataUltimoAccessoNotNullException(final String message) {
		super(message);
	}

	public UserDataUltimoAccessoNotNullException(final Throwable t) {
		super(t);
	}

	public UserDataUltimoAccessoNotNullException(final String message, final Throwable t) {
		super(message, t);
	}

}