package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.DatiAccorpamentoEntry;

public class ResponseRiepilogoAccorpamenti extends BaseResponse implements Serializable {

	private static final long serialVersionUID = 5855720945505092990L;

	
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati accorpamentii")
	private DatiAccorpamentoEntry data;


	public DatiAccorpamentoEntry getData() {
		return data;
	}

	public void setData(DatiAccorpamentoEntry data) {
		this.data = data;
	}
}