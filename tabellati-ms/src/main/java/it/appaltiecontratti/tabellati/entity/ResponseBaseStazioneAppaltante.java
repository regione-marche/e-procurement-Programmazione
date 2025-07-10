package it.appaltiecontratti.tabellati.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponseBaseStazioneAppaltante extends BaseResponse implements Serializable {

	private static final long serialVersionUID = -3059948745813116534L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati base delle stazioni appaltanti")
	private StazioneAppaltanteBaseEntry data;

	public StazioneAppaltanteBaseEntry getData() {
		return data;
	}

	public void setData(StazioneAppaltanteBaseEntry data) {
		this.data = data;
	}
}
