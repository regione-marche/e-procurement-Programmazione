package it.appaltiecontratti.tabellati.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponseInfoCampo  extends BaseResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/** Codice indicante un errore inaspettato. */
	public static final String ERROR_UNEXPECTED = "unexpected-error";

	@ApiModelProperty(value="Risultato operazione di lettura")
	private boolean esito;

	
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati del campo")
	private InfoCampo data;

	

	

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

	public boolean isEsito() {
		return esito;
	}

	public InfoCampo getData() {
		return data;
	}

	public void setData(InfoCampo entry) {
		this.data = entry;
	}

}
