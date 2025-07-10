package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpegnoEntry;

public class ResponseImpegni extends BaseResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Lista degli impegni")
    List<ImpegnoEntry> data;
	
	public List<ImpegnoEntry> getData() {
		return data;
	}

	public void setData(List<ImpegnoEntry> data) {
		this.data = data;
	}


}