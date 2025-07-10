package it.maggioli.ssointegrms.exceptions.oneGateway;

/**
 * Classe per gestire le eccezioni multiple in fase di decrittazione della risposta
 * @author Cristiano Perin
 *
 */
public class DecryptException extends RuntimeException {

	private static final long serialVersionUID = -5781507412211312303L;

	public DecryptException() {
		super();
	}

	public DecryptException(final String message) {
		super(message);
	}

	public DecryptException(final Throwable t) {
		super(t);
	}

	public DecryptException(final String message, final Throwable t) {
		super(message, t);
	}
}
