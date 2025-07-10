package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.GaraEntry;

public class ResponseListaGare   extends BaseResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="List gare")
    List<GaraEntry> data;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Numero totale delle gare estraibili dalla form di ricerca")
	private int totalCount;

	public List<GaraEntry> getData() {
		return data;
	}

	public void setData(List<GaraEntry> data) {
		this.data = data;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
