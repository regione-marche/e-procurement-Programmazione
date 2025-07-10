package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseImpresaEntry;

public class ResponseFaseImprese    extends BaseResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Lista dei record della fase imprese")
    List<FaseImpresaEntry> data;
	
	public List<FaseImpresaEntry> getData() {
		return data;
	}

	public void setData(List<FaseImpresaEntry> data) {
		this.data = data;
	}
}