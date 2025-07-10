package it.appaltiecontratti.programmi.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponseListaOpereIncompiute  extends BaseResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="List opere Incompiute")
    List<OperaIncompiutaBaseEntry> data;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Numero totale delle opere Incompiute estraibili dalla form di ricerca")
	private int totalCount;
	
	public List<OperaIncompiutaBaseEntry> getData() {
		return data;
	}

	public void setData(List<OperaIncompiutaBaseEntry> data) {
		this.data = data;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	
}
