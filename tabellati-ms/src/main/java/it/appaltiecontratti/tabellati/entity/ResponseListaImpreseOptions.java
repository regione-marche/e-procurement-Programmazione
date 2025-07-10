package it.appaltiecontratti.tabellati.entity;


import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description="Contenitore per il risultato dell'operazione di reperimento lista imprese")
public class ResponseListaImpreseOptions extends BaseResponse implements Serializable{

	private static final long serialVersionUID = 2653501092399064669L;

	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";

	@ApiModelProperty(value="Risultato operazione di lettura")
	private boolean esito;

	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati dei RUP")
	private List<ImpresaEntry> data;

	
	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public boolean isEsito() {
		return esito;
	}

	public List<ImpresaEntry> getData() {
		return data;
	}

	public void setData(List<ImpresaEntry> data) {
		this.data = data;
	}

}

