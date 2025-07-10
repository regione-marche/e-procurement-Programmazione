package it.appaltiecontratti.programmi.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contenitore per il risultato dell'operazione di accesso al servizio di Inserimento di un record")
public class ResponseResult implements Serializable {

	private static final long serialVersionUID = -6611269573839884401L;

	public static final String ERROR_UNEXPECTED = "unexpected-error";

	public static final String ERROR_NOT_FOUND = "NOT-FOUND";

	public static final String ERROR_PERMISSION = "ERROR-PERMISSION";

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Eventuale messaggio di errore in caso di operazione fallita")
	private String error;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Eventuale messaggio di accessrio all'errore")
	private String errorData;

	@ApiModelProperty(value = "Risultato operazione di inserimento")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "id record creato")
	private String data;

	public void setError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
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

	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}
	
	
}