package it.appaltiecontratti.programmi.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ResponseDettaglioOperaIncompiuta  extends BaseResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati dell'intervento")
	private OperaIncompiutaEntry data;

	public OperaIncompiutaEntry getData() {
		return data;
	}

	public void setData(OperaIncompiutaEntry entry) {
		this.data = entry;
	}
}