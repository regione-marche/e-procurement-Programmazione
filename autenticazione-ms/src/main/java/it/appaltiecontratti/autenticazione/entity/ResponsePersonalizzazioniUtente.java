package it.appaltiecontratti.autenticazione.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponsePersonalizzazioniUtente {
	
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	public static final String ERROR_UNEXPECTED = "unexpected-error";
	
	public static final String AUTH_DENY = "Time-out expired token";

	@ApiModelProperty(value="Risultato operazione di inserimento")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Eventuale messaggio di errore in caso di operazione fallita")
	private String errorData;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="numero avviso creato")
	private PersonalizzazioneUtente data;

	public boolean isEsito() {
		return esito;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}

	public PersonalizzazioneUtente getData() {
		return data;
	}

	public void setData(PersonalizzazioneUtente data) {
		this.data = data;
	}

}
