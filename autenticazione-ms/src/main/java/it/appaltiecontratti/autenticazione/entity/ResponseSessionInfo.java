package it.appaltiecontratti.autenticazione.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponseSessionInfo implements Serializable {

	private static final long serialVersionUID = 114866581852311848L;

	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";

	@ApiModelProperty(value="Risultato operazione di inserimento")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Eventuale messaggio di errore in caso di operazione fallita")
	private String errorData;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="numero avviso creato")
	private SSOTokenInfo data;
	
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

	public SSOTokenInfo getData() {
		return data;
	}

	public void setData(SSOTokenInfo data) {
		this.data = data;
	}
}
