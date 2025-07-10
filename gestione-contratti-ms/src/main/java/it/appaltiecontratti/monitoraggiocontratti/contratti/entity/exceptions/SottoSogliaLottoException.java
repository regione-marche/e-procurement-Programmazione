package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.exceptions;

public class SottoSogliaLottoException extends RuntimeException {
	
	public SottoSogliaLottoException(String errorMessage) {
		super(errorMessage);
	}
	
    public SottoSogliaLottoException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}