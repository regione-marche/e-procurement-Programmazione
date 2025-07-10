package it.maggioli.ssointegrms.exceptions.amministrazione;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class WMailAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -3777807879245004200L;

	public WMailAlreadyExistsException() {
		super();
	}

	public WMailAlreadyExistsException(final String message) {
		super(message);
	}

	public WMailAlreadyExistsException(final Throwable t) {
		super(t);
	}

	public WMailAlreadyExistsException(final String message, final Throwable t) {
		super(message, t);
	}

}