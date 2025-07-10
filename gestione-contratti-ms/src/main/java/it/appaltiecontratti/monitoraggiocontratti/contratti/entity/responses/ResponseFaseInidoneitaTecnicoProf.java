package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseInidoneitaTecnicoProfEntry;

public class ResponseFaseInidoneitaTecnicoProf  extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dettaglio fase idoneità tecnico professionale")
	FaseInidoneitaTecnicoProfEntry data;
	
	public FaseInidoneitaTecnicoProfEntry getData() {
		return data;
	}

	public void setData(FaseInidoneitaTecnicoProfEntry data) {
		this.data = data;
	}

}
