package it.appaltiecontratti.tabellati.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponseStazioneAppaltante {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati della stazione appaltante")
	private StazioneAppaltanteEntry data;
	
	public static final String ERROR_UNEXPECTED = "UNEXPECTED-ERROR";
	
	public static final String ERROR_NOT_FOUND = "not-found";

	@ApiModelProperty(value="Risultato operazione")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Eventuale messaggio di errore in caso di operazione fallita")
	private String errorData;

	public StazioneAppaltanteEntry getData() {
		return data;
	}

	public void setData(StazioneAppaltanteEntry data) {
		this.data = data;
	}

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
	
	
}
