package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseInadempienzeSicurezzaEntry;

public class ResponseFaseInadempienzeSicurezza extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase inadempienze sicurezza")
    FaseInadempienzeSicurezzaEntry data;
	
	public FaseInadempienzeSicurezzaEntry getData() {
		return data;
	}

	public void setData(FaseInadempienzeSicurezzaEntry data) {
		this.data = data;
	}
	
}
