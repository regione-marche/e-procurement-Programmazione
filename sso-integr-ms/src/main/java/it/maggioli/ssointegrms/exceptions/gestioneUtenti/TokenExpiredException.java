package it.maggioli.ssointegrms.exceptions.gestioneUtenti;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class TokenExpiredException extends Exception {

	private static final long serialVersionUID = -8488421276947032295L;

	public TokenExpiredException() {
		super();
	}

	public TokenExpiredException(String message) {
		super(message);
	}

	public TokenExpiredException(Throwable t) {
		super(t);
	}

	public TokenExpiredException(String message, Throwable t) {
		super(message, t);
	}

}
