package it.maggioli.ssointegrms.exceptions.amministrazione;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class CodificaAutomaticaNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -877927538222090460L;

	public CodificaAutomaticaNotFoundException() {
		super();
	}

	public CodificaAutomaticaNotFoundException(final String message) {
		super(message);
	}

	public CodificaAutomaticaNotFoundException(final Throwable t) {
		super(t);
	}

	public CodificaAutomaticaNotFoundException(final String message, final Throwable t) {
		super(message, t);
	}

}