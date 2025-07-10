package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.exceptions;

public class ImportImpresaAggiudicatariaException extends RuntimeException {

	private static final long serialVersionUID = -1252939008637525757L;

	public ImportImpresaAggiudicatariaException() {
		super();
	}

	public ImportImpresaAggiudicatariaException(String message) {
		super(message);
	}

	public ImportImpresaAggiudicatariaException(Throwable t) {
		super(t);
	}

	public ImportImpresaAggiudicatariaException(String message, Throwable t) {
		super(message, t);
	}
}
