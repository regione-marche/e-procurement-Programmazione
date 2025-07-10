package it.maggioli.ssointegrms.exceptions.internalAuthentication;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class MaxTentativiException extends RuntimeException {

	private static final long serialVersionUID = -3582364572851598661L;

	public MaxTentativiException() {
		super();
	}

	public MaxTentativiException(final String message) {
		super(message);
	}

	public MaxTentativiException(final Throwable t) {
		super(t);
	}

	public MaxTentativiException(final String message, final Throwable t) {
		super(message, t);
	}

}
