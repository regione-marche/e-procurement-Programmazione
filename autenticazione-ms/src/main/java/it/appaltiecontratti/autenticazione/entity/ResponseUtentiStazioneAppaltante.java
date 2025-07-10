package it.appaltiecontratti.autenticazione.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contenitore per il risultato dell'operazione di recupero utenti stazione appaltante")
public class ResponseUtentiStazioneAppaltante implements Serializable {

	private static final long serialVersionUID = -4846897663099402743L;

	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";
	
	public static final String ERROR_PERMISSION = "ERROR-PERMISSION";

	@ApiModelProperty(value = "Risultato operazione di inserimento")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Eventuale messaggio di errore in caso di operazione fallita")
	private String errorData;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "lista utenti stazione appaltante")
	private List<UsrSysconEntry> data;

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

	public List<UsrSysconEntry> getData() {
		return data;
	}

	public void setData(List<UsrSysconEntry> data) {
		this.data = data;
	}

}