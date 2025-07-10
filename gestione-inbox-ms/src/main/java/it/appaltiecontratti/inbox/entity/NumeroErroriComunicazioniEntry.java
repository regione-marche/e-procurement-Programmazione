package it.appaltiecontratti.inbox.entity;

public class NumeroErroriComunicazioniEntry {

	private Long cancellazioniNonEvase;
	private Long erroriFeedback;
	private Long erroriComunicazioniSCP;

	public Long getCancellazioniNonEvase() {
		return cancellazioniNonEvase;
	}

	public void setCancellazioniNonEvase(Long cancellazioniNonEvase) {
		this.cancellazioniNonEvase = cancellazioniNonEvase;
	}

	public Long getErroriFeedback() {
		return erroriFeedback;
	}

	public void setErroriFeedback(Long erroriFeedback) {
		this.erroriFeedback = erroriFeedback;
	}

	public Long getErroriComunicazioniSCP() {
		return erroriComunicazioniSCP;
	}

	public void setErroriComunicazioniSCP(Long erroriComunicazioniSCP) {
		this.erroriComunicazioniSCP = erroriComunicazioniSCP;
	}

}
