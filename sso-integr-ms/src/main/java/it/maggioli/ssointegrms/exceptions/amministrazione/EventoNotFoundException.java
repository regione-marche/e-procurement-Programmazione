package it.maggioli.ssointegrms.exceptions.amministrazione;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class EventoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6529659880446531797L;

	public EventoNotFoundException() {
		super();
	}

	public EventoNotFoundException(final String message) {
		super(message);
	}

	public EventoNotFoundException(final Throwable t) {
		super(t);
	}

	public EventoNotFoundException(final String message, final Throwable t) {
		super(message, t);
	}

}