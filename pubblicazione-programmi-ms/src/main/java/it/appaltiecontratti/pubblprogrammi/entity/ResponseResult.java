package it.appaltiecontratti.pubblprogrammi.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Risultato accesso servizio pubblicazione
 *
 * @author Michele.DiNapoli
 */

@ApiModel(description="Contenitore per il risultato dell'operazione di accesso al servizio di Inserimento di un avviso")
public class ResponseResult implements Serializable {

	private static final long serialVersionUID = -6611269573839884401L;

	public static final String ERROR_UNEXPECTED = "unexpected-error";
	
	public static final String ERROR_NO_PUBBLICAZIONE_PROGRAMMA = "ERROR-NO-PUBBLICAZIONE-PROGRAMMA";
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Eventuale messaggio di errore in caso di operazione fallita")
	private String errorData;

	@ApiModelProperty(value="Risultato operazione di inserimento")
	private boolean esito;


	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="numero avviso creato")
	private String data;
	
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}