package it.appaltiecontratti.tabellati.exception;

public class ExistingCFException extends RuntimeException {

	private static final long serialVersionUID = -6798517438106426824L;

	public ExistingCFException() {
	}

	public ExistingCFException(Throwable t) {
		super(t);
	}

	public ExistingCFException(String message) {
		super(message);
	}

	public ExistingCFException(String message, Throwable t) {
		super(message, t);
	}

}
