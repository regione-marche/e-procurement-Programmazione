package it.maggioli.ssointegrms.exceptions.amministrazione;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class ConfigurationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8959512661992079885L;

	public ConfigurationNotFoundException() {
		super();
	}

	public ConfigurationNotFoundException(final String message) {
		super(message);
	}

	public ConfigurationNotFoundException(final Throwable t) {
		super(t);
	}

	public ConfigurationNotFoundException(final String message, final Throwable t) {
		super(message, t);
	}

}