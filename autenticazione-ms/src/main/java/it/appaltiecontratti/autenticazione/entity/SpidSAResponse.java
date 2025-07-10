package it.appaltiecontratti.autenticazione.entity;

public class SpidSAResponse {

	public static final String ERROR_UNEXPECTED = "unexpected-error";

	private boolean esito;

	private String errorData;

	private SpidSAData data;
	
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

	public SpidSAData getData() {
		return data;
	}

	public void setData(SpidSAData data) {
		this.data = data;
	}


}
