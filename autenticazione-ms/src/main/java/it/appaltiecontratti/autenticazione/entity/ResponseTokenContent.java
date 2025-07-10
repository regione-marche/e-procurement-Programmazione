package it.appaltiecontratti.autenticazione.entity;

import java.io.Serializable;

public class ResponseTokenContent implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	private boolean esito;	
	private String errorData;
	private TokenContentInfo data;

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public boolean isEsito() {
		return esito;
	}

	public TokenContentInfo getData() {
		return data;
	}

	public void setData(TokenContentInfo data) {
		this.data = data;
	}

	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}

}
