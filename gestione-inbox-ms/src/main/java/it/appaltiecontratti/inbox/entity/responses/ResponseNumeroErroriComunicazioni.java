package it.appaltiecontratti.inbox.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.inbox.entity.NumeroErroriComunicazioniEntry;

public class ResponseNumeroErroriComunicazioni extends BaseResponse {

	private static final long serialVersionUID = 1267387394532946827L;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "Numero Errori Comunicazioni")
	private NumeroErroriComunicazioniEntry data;

	public NumeroErroriComunicazioniEntry getData() {
		return data;
	}

	public void setData(NumeroErroriComunicazioniEntry data) {
		this.data = data;
	}

}
