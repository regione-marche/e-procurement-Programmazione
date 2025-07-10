package it.appaltiecontratti.tabellati.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponseTabellatoCpv extends BaseResponse implements Serializable{

@ApiModelProperty(value="Risultato operazione di lettura")
	private boolean esito;

	@ApiModelProperty(value="Valori delle tab nuts")
    List<TabellatoCPVEntry> data;

	public boolean isEsito() {
		return esito;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	
	public List<TabellatoCPVEntry> getData() {
		return data;
	}

	public void setData(List<TabellatoCPVEntry> data) {
		this.data = data;
	}


}
