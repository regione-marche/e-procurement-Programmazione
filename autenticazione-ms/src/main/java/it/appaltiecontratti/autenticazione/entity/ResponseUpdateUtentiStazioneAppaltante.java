package it.appaltiecontratti.autenticazione.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contenitore per il risultato dell'operazione di aggiornamento utenti stazione appaltante")
public class ResponseUpdateUtentiStazioneAppaltante implements Serializable {

	private static final long serialVersionUID = -7894845866503773955L;

	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";

	@ApiModelProperty(value = "Risultato operazione di inserimento")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Eventuale messaggio di errore in caso di operazione fallita")
	private String errorData;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "risulato associazione")
	private boolean data;

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

	public boolean isData() {
		return data;
	}

	public void setData(boolean data) {
		this.data = data;
	}

}