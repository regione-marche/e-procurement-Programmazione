package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.exceptions;

public class AppaltoPcpCastException extends RuntimeException {

	public AppaltoPcpCastException(String errorMessage) {
		super(errorMessage);
	}

    public AppaltoPcpCastException() {
        super();
    }

    public AppaltoPcpCastException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}