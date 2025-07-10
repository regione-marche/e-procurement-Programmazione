package it.appaltiecontratti.tabellati.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;


@ApiModel(description="Contenitore per il risultato dell'operazione di reperimento lista RUP")
public class ResponseListaUtente extends BaseResponse implements Serializable{

	
	private static final long serialVersionUID = -6611269573839884401L;

	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";

	@ApiModelProperty(value="Risultato operazione di lettura")
	private boolean esito;

	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati dei RUP")
	private List<RupEntry> data;


	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public boolean isEsito() {
		return esito;
	}

	public List<RupEntry> getData() {
		return data;
	}

	public void setData(List<RupEntry> entry) {
		this.data = entry;
	}
}

