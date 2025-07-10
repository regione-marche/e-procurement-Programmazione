package it.appaltiecontratti.tabellati.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponseListaComuni extends BaseResponse implements Serializable{
	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";

	@ApiModelProperty(value="Risultato operazione di lettura")
	private boolean esito;

	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati dei'comuni")
	private List<TabellatoIstatEntry> data;

	public boolean isEsito() {
		return esito;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	

	
	public List<TabellatoIstatEntry> getData() {
		return data;
	}

	public void setData(List<TabellatoIstatEntry> data) {
		this.data = data;
	}
	
}
