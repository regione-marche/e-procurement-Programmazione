package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.exceptions;

public class AppaltoAnnullatoException extends RuntimeException {

	public AppaltoAnnullatoException(String errorMessage) {
		super(errorMessage);
	}

    public AppaltoAnnullatoException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}