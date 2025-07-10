package it.appaltiecontratti.inbox.entity.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.inbox.entity.ComScpListaEntry;

public class ResponseListaComScp extends BaseResponse{

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati base delle comuniczioni di scp")
	private List<ComScpListaEntry> data;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Numero totale delle comuniczioni di scp estraibili dalla form di ricerca")
	private int totalCount;

	public List<ComScpListaEntry> getData() {
		return data;
	}

	public void setData(List<ComScpListaEntry> data) {
		this.data = data;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	
}
