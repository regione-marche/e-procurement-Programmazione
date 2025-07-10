package it.appaltiecontratti.tabellati.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponseListaUffici extends BaseResponse implements Serializable{

	private static final long serialVersionUID = 4008542875797483704L;

	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";

	@ApiModelProperty(value="Risultato operazione di lettura")
	private boolean esito;

	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati dell'ufficio")
	private List<UffEntry> data;


	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public boolean isEsito() {
		return esito;
	}

	public List<UffEntry> getData() {
		return data;
	}

	public void setData(List<UffEntry> entry) {
		this.data = entry;
	}
}
