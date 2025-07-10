package it.appaltiecontratti.programmi.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="Contenitore per il risultato dell'operazione di reperimento lista cui")
public class ResponseListaCuiOptions extends BaseResponse implements Serializable {

	private static final long serialVersionUID = -8270025886468118908L;

	@ApiModelProperty(value="Risultato operazione di lettura")
	private boolean esito;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati dei CUI")
	private List<String> data;

	
	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public boolean isEsito() {
		return esito;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}
}
