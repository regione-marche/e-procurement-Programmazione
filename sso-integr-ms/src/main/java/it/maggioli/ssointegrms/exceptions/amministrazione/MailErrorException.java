package it.maggioli.ssointegrms.exceptions.amministrazione;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class MailErrorException extends RuntimeException {

	private static final long serialVersionUID = -5217897586313908443L;

	public MailErrorException() {
		super();
	}

	public MailErrorException(final String message) {
		super(message);
	}

	public MailErrorException(final Throwable t) {
		super(t);
	}

	public MailErrorException(final String message, final Throwable t) {
		super(message, t);
	}

}