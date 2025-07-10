package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.IncarichiProfEntry;

public class ResponseIncarichiProfessionali extends BaseResponse{

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Lista degli incarichi professionali per la fase")
	List<IncarichiProfEntry> data;

	public List<IncarichiProfEntry> getData() {
		return data;
	}

	public void setData(List<IncarichiProfEntry> data) {
		this.data = data;
	}

}

