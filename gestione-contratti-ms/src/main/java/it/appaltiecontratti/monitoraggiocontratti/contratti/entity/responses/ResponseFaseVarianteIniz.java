package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponseFaseVarianteIniz extends BaseResponse {

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Oggetto di inizializzazione di una fase di modifica contrattuale")
	FaseVarianteIniz data;

	public FaseVarianteIniz getData() {
		return data;
	}

	public void setData(FaseVarianteIniz data) {
		this.data = data;
	}

	

}
