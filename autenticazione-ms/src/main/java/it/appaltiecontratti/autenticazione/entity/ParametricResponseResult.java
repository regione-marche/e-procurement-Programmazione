package it.appaltiecontratti.autenticazione.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
@ApiModel(description="Contenitore per il risultato dell'operazione REST")
public class ParametricResponseResult<T> implements Serializable {

	private static final long serialVersionUID = 7684247407087679569L;

	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";

	/**
	 * Codice indicante un elemento non trovato
	 */
	public static final String ERROR_NOT_FOUND = "not-found";

	@ApiModelProperty(value="Risultato operazione")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Eventuale messaggio di errore in caso di operazione fallita")
	private String errorData;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Oggetto in risposta")
	private T data;

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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ParametricResponseResult{" +
				"esito=" + esito +
				", errorData='" + errorData + '\'' +
				", data=" + data +
				'}';
	}
}