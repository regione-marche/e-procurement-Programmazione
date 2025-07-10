package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.InizFaseSubappaltoEntry;

public class ResponseInizFaseSubappalto  extends BaseResponse implements Serializable {

	private static final long serialVersionUID = 2672710742076297302L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati di inizializzazione fase subappalto")
    InizFaseSubappaltoEntry data;
	
	public InizFaseSubappaltoEntry getData() {
		return data;
	}

	public void setData(InizFaseSubappaltoEntry	 data) {
		this.data = data;
	}
	
}
