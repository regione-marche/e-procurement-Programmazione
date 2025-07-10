package it.maggioli.ssointegrms.exceptions.amministrazione;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class WMailTimeoutNotANumberException extends RuntimeException {

	private static final long serialVersionUID = 2797135371623048137L;

	public WMailTimeoutNotANumberException() {
		super();
	}

	public WMailTimeoutNotANumberException(final String message) {
		super(message);
	}

	public WMailTimeoutNotANumberException(final Throwable t) {
		super(t);
	}

	public WMailTimeoutNotANumberException(final String message, final Throwable t) {
		super(message, t);
	}

}