package it.appaltiecontratti.inbox.entity.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.inbox.entity.FlussiListaEntry;


public class ResponseListaFlussi extends BaseResponse{

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati base dei flussi")
	private List<FlussiListaEntry> data;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Numero totale dei flussi estraibili dalla form di ricerca")
	private int totalCount;

	public List<FlussiListaEntry> getData() {
		return data;
	}

	public void setData(List<FlussiListaEntry> data) {
		this.data = data;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	
}
