package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.AttoEntry;

public class ResponseAtti extends BaseResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Lista degli atti pubblicabili")
    List<AttoEntry> data;
	
	public List<AttoEntry> getData() {
		return data;
	}

	public void setData(List<AttoEntry> data) {
		this.data = data;
	}


}