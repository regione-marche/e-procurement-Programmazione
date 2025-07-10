package it.appaltiecontratti.autenticazione.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Risultato accesso servizio pubblicazione
 *
 * @author Michele.DiNapoli
 */

@ApiModel(description = "Contenitore per il risultato dell'operazione di reperimento abilitazioni di un utente e opzioni prodotto")
public class ResponseAbilitazioni implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";

	@ApiModelProperty(value = "Risultato operazione di get")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Eventuale messaggio di errore in caso di operazione fallita")
	private String errorData;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Opzioni utente e prodotto")
	private OpzioniUtenteProdotto data;

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}

	public String getErrorData() {
		return errorData;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public boolean isEsito() {
		return esito;
	}

	public OpzioniUtenteProdotto getData() {
		return data;
	}

	public void setData(OpzioniUtenteProdotto data) {
		this.data = data;
	}

}