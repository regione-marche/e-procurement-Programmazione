package it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Risultato base di risposta per atti generali.
 *
 * @author andrea.chinellato
 */

@ApiModel(description="Contenitore per il risultato dell'operazione legata agli atti generali")
public class ResponseResult implements Serializable {

	private static final long serialVersionUID = 115984871566422862L;

	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";

	@ApiModelProperty(value="Risultato operazione")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Eventuale messaggio d'errore")
	private String errorData;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati risposta")
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