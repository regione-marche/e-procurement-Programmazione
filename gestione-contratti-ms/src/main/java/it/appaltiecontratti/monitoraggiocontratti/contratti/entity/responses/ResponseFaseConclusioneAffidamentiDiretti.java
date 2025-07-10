package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseConclusioneAffidamentiDirettiEntry;

public class ResponseFaseConclusioneAffidamentiDiretti extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase conclusione affidamenti diretti")
	FaseConclusioneAffidamentiDirettiEntry data;
	
	public FaseConclusioneAffidamentiDirettiEntry getData() {
		return data;
	}

	public void setData(FaseConclusioneAffidamentiDirettiEntry data) {
		this.data = data;
	}

}
