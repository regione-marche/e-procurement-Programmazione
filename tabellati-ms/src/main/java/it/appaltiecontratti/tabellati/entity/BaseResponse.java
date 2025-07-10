package it.appaltiecontratti.tabellati.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

public class BaseResponse implements Serializable {

	private static final long serialVersionUID = -7044735789012286681L;

	public static final String ERROR_UNEXPECTED = "UNEXPECTED-ERROR";

	public static final String ERROR_NOT_FOUND = "not-found";

	public static final String ERROR_PERMISSION = "ERROR-PERMISSION";

	public static final String ERROR_DISABLED = "ERROR-DISABLED";

	@ApiModelProperty(value = "Risultato operazione")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Eventuale messaggio di errore in caso di operazione fallita")
	private String errorData;

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

}
