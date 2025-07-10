package it.appaltiecontratti.autenticazione.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponseSpidAccount {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";

	@ApiModelProperty(value="Risultato operazione di inserimento")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Eventuale messaggio di errore in caso di operazione fallita")
	private String errorData;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="dati account spid")
	private TokenContentInfo data;
	
	
	private String ssoJwtToken;

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

	public TokenContentInfo getData() {
		return data;
	}

	public void setData(TokenContentInfo data) {
		this.data = data;
	}


	
	
}
