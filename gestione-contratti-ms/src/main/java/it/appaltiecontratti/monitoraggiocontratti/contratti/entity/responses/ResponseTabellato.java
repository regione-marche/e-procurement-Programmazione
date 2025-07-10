package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.TabellatoEntry;

public class ResponseTabellato extends BaseResponse {
	
	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Righe del tabellato")
	List<TabellatoEntry> data;
	
	public List<TabellatoEntry> getData() {
		return data;
	}

	public void setData(List<TabellatoEntry> data) {
		this.data = data;
	}
}
