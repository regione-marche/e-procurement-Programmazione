package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.TabellatoFaseEntry;

public class ResponseFasiAbilitate extends BaseResponse {

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Lista delle fasi visibili per il lotto")
    List<TabellatoFaseEntry> data;

	public List<TabellatoFaseEntry> getData() {
		return data;
	}

	public void setData(List<TabellatoFaseEntry> data) {
		this.data = data;
	}
	
	
}
