package it.maggioli.ssointegrms.exceptions.amministrazione;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class QuartzNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2575264476905950026L;

	public QuartzNotFoundException() {
		super();
	}

	public QuartzNotFoundException(final String message) {
		super(message);
	}

	public QuartzNotFoundException(final Throwable t) {
		super(t);
	}

	public QuartzNotFoundException(final String message, final Throwable t) {
		super(message, t);
	}

}