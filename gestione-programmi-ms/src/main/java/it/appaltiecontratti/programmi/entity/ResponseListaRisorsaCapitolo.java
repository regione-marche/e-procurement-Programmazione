package it.appaltiecontratti.programmi.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponseListaRisorsaCapitolo extends BaseResponse{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati base delle risorse di capitolo")
	private List<RisorsaCapitoloBaseEntry> data;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Numero totale delle risorse di capitolo estraibili dalla form di ricerca")
	private int totalCount;

	public List<RisorsaCapitoloBaseEntry> getData() {
		return data;
	}

	public void setData(List<RisorsaCapitoloBaseEntry> data) {
		this.data = data;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	

}
