package it.maggioli.ssointegrms.exceptions.oneGateway;

/**
 * Classe per gestire le eccezioni multiple in fase di crittazione della richiesta
 * @author Cristiano Perin
 *
 */
public class EncryptException extends RuntimeException {

	private static final long serialVersionUID = -3693709534662703078L;

	public EncryptException() {
		super();
	}

	public EncryptException(final String message) {
		super(message);
	}

	public EncryptException(final Throwable t) {
		super(t);
	}

	public EncryptException(final String message, final Throwable t) {
		super(message, t);
	}
}
