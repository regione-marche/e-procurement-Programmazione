package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.exceptions;

public class InvalidImprFiscalCodeException extends RuntimeException {
	
	public InvalidImprFiscalCodeException(String errorMessage) {
		super(errorMessage);
	}
	
    public InvalidImprFiscalCodeException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}