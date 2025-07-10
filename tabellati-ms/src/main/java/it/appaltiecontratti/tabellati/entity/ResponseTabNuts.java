package it.appaltiecontratti.tabellati.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponseTabNuts extends BaseResponse implements Serializable{
	
	@ApiModelProperty(value="Risultato operazione di lettura")
	private boolean esito;


	@ApiModelProperty(value="Valori delle tab nuts")
    Map<String, List<TabNuts>> data;

	public boolean isEsito() {
		return esito;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public Map<String, List<TabNuts>> getData() {
		return data;
	}

	public void setData(Map<String, List<TabNuts>> data) {
		this.data = data;
	}
	
	

}
