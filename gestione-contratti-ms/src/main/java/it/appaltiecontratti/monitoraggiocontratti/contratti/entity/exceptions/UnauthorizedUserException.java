package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.exceptions;

public class UnauthorizedUserException extends RuntimeException {
	
	public UnauthorizedUserException(String errorMessage) {
		super(errorMessage);
	}
	
    public UnauthorizedUserException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}