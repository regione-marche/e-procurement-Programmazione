package it.appaltiecontratti.tabellati.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponseListaStazioneAppaltante {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati base delle stazioni appaltanti")
	private List<StazioneAppaltanteListaEntry> data;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Numero totale delle stazioni appaltanti estraibili dalla form di ricerca")
	private int totalCount;

	public static final String ERROR_UNEXPECTED = "UNEXPECTED-ERROR";
	
	public static final String ERROR_NOT_FOUND = "not-found";

	@ApiModelProperty(value="Risultato operazione")
	private boolean esito;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Eventuale messaggio di errore in caso di operazione fallita")
	private String errorData;

	public List<StazioneAppaltanteListaEntry> getData() {
		return data;
	}

	public void setData(List<StazioneAppaltanteListaEntry> data) {
		this.data = data;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
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
