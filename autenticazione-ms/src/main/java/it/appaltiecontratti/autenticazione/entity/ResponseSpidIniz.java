package it.appaltiecontratti.autenticazione.entity;

public class ResponseSpidIniz {
	
	public static final String ERROR_UNEXPECTED = "unexpected-error";

	private boolean esito;

	private String errorData;

	private SpidInizData data;
	
	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public boolean isEsito() {
		return esito;
	}

	public SpidInizData getData() {
		return data;
	}

	public void setData(SpidInizData data) {
		this.data = data;
	}
}
