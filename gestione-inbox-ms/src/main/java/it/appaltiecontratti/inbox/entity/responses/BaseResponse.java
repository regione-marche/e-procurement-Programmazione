package it.appaltiecontratti.inbox.entity.responses;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

public class BaseResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_UNEXPECTED = "UNEXPECTED-ERROR";

	public static final String ERROR_NOT_FOUND = "NOT-FOUND";

	public static final String ERROR_PERMISSION = "NO-PERMISSION";
	
	public static final String ERROR_NO_DELETE = "FEEDBACK-NO-DELETE";

	public static final String BAD_REQUEST = "BAD_REQUEST";

	public static final String ERROR_XML_VALIDATION = "XML-VALIDATION";

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
