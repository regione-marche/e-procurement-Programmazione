package it.appaltiecontratti.autenticazione.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Risultato accesso servizio pubblicazione
 *
 * @author Michele.DiNapoli
 */

@ApiModel(description="Contenitore per il risultato dell'operazione di accesso al servizio di Inserimento di un avviso")
public class ResponseResult implements Serializable {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -6611269573839884401L;

	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";
	
	/** Codice indicante un errore causato da authId scaduto in fase di login. */
	public static final String AUTH_DENY = "Time-out expired token";

	@ApiModelProperty(value="Risultato operazione di inserimento")
	private boolean esito;



	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Eventuale messaggio di errore in caso di operazione fallita")
	private String errorData;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="numero avviso creato")
	private String data;
	
	

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public boolean isEsito() {
		return esito;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}
	
	
}