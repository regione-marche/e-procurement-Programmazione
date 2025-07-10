package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseInidoneitaContributivaEntry;

public class ResponseFaseInidoneitaContributiva  extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase inidoneita contributiva")
    FaseInidoneitaContributivaEntry data;
	
	public FaseInidoneitaContributivaEntry getData() {
		return data;
	}

	public void setData(FaseInidoneitaContributivaEntry data) {
		this.data = data;
	}
}