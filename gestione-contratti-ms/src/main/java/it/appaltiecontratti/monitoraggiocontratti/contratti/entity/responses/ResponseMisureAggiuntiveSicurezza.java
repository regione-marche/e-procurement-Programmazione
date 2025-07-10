package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.MisureAggiuntiveSicurezzaEntry;

public class ResponseMisureAggiuntiveSicurezza   extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase misure aggiuntive di sicurezza semplificata")
    MisureAggiuntiveSicurezzaEntry data;
	
	public MisureAggiuntiveSicurezzaEntry getData() {
		return data;
	}

	public void setData(MisureAggiuntiveSicurezzaEntry data) {
		this.data = data;
	}
	
}
