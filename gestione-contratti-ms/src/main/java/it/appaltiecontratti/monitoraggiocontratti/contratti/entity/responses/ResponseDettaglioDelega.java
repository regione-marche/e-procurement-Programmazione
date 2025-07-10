package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.DelegaEntry;

public class ResponseDettaglioDelega extends BaseResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Dettaglio delega")
	DelegaEntry data;

	public DelegaEntry getData() {
		return data;
	}

	public void setData(DelegaEntry data) {
		this.data = data;
	}
}