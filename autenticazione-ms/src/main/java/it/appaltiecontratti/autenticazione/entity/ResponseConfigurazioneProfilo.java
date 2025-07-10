package it.appaltiecontratti.autenticazione.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;


@ApiModel(description="Contenitore per il risultato dell'operazione di dettaglio del profilo")
public class ResponseConfigurazioneProfilo implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";

	@ApiModelProperty(value="Risultato operazione di lettura")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Eventuale messaggio di errore in caso di operazione fallita")
	private String errorData;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Configurazione del profilo")
	private ProfiloFullInfo data;

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

	public ProfiloFullInfo getData() {
		return data;
	}

	public void setData(ProfiloFullInfo entry) {
		this.data = entry;
	}
}
