package it.maggioli.ssointegrms.exceptions.amministrazione;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class CodificaAutomaticaSintassiNonCorrettaException extends RuntimeException {

	private static final long serialVersionUID = -4314830355510354405L;

	public CodificaAutomaticaSintassiNonCorrettaException() {
		super();
	}

	public CodificaAutomaticaSintassiNonCorrettaException(final String message) {
		super(message);
	}

	public CodificaAutomaticaSintassiNonCorrettaException(final Throwable t) {
		super(t);
	}

	public CodificaAutomaticaSintassiNonCorrettaException(final String message, final Throwable t) {
		super(message, t);
	}

}