package it.maggioli.ssointegrms.exceptions.oneGateway;

/**
 * Classe per gestire l'eccezione di token scaduto
 * @author Cristiano Perin
 *
 */
public class TokenExpiredException extends RuntimeException {

	private static final long serialVersionUID = -5781507412211312303L;

	public TokenExpiredException() {
		super();
	}

	public TokenExpiredException(final String message) {
		super(message);
	}

	public TokenExpiredException(final Throwable t) {
		super(t);
	}

	public TokenExpiredException(final String message, final Throwable t) {
		super(message, t);
	}
}
