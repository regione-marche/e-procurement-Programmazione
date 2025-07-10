package it.appaltiecontratti.tabellati.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponseListaCentriDiCostoOptions extends BaseResponse implements Serializable{

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Dati base dei centrri di costo")
	private List<CentroDiCostoEntry> data;

	public static final String ERROR_UNEXPECTED = "UNEXPECTED-ERROR";

	public static final String ERROR_NOT_FOUND = "not-found";

	@ApiModelProperty(value = "Risultato operazione")
	private boolean esito;


	

	public List<CentroDiCostoEntry> getData() {
		return data;
	}

	public void setData(List<CentroDiCostoEntry> data) {
		this.data = data;
	}

	public boolean isEsito() {
		return esito;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	

}
