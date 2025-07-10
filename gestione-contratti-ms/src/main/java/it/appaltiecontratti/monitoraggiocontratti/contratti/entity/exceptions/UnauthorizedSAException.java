package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.exceptions;

public class UnauthorizedSAException extends RuntimeException {
	
	public UnauthorizedSAException(String errorMessage) {
		super(errorMessage);
	}
	
    public UnauthorizedSAException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}