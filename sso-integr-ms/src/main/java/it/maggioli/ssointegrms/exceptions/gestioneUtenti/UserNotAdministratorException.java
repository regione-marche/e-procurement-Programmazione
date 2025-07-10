package it.maggioli.ssointegrms.exceptions.gestioneUtenti;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class UserNotAdministratorException extends RuntimeException {

	private static final long serialVersionUID = -8146057579986407515L;

	public UserNotAdministratorException() {
		super();
	}

	public UserNotAdministratorException(final String message) {
		super(message);
	}

	public UserNotAdministratorException(final Throwable t) {
		super(t);
	}

	public UserNotAdministratorException(final String message, final Throwable t) {
		super(message, t);
	}

}