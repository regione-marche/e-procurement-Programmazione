package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.exceptions;

public class AppaltoAnnullatoEliminareException extends RuntimeException {

	public AppaltoAnnullatoEliminareException(String errorMessage) {
		super(errorMessage);
	}

    public AppaltoAnnullatoEliminareException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}