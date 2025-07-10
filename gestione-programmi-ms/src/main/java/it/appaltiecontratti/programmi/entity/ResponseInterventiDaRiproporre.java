package it.appaltiecontratti.programmi.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

public class ResponseInterventiDaRiproporre  extends BaseResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="List interventi da importare")
    List<InterventiDaRiproporre> data;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Numero totale degli interventi non riproposti estraibili dalla form di ricerca")
	private int totalCount;
	
	public List<InterventiDaRiproporre> getData() {
		return data;
	}

	public void setData(List<InterventiDaRiproporre> data) {
		this.data = data;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	

}
