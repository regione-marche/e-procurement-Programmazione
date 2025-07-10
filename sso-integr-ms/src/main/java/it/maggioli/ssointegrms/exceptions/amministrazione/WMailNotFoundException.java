package it.maggioli.ssointegrms.exceptions.amministrazione;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class WMailNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2547984287858330959L;

	public WMailNotFoundException() {
		super();
	}

	public WMailNotFoundException(final String message) {
		super(message);
	}

	public WMailNotFoundException(final Throwable t) {
		super(t);
	}

	public WMailNotFoundException(final String message, final Throwable t) {
		super(message, t);
	}

}