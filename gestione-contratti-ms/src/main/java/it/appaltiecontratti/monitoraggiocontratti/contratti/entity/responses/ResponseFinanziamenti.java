package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FinanziamentiEntry;

public class ResponseFinanziamenti extends BaseResponse {

	private static final long serialVersionUID = 1L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Lista dei finanziamenti per la fase")
	List<FinanziamentiEntry> data;

	public List<FinanziamentiEntry> getData() {
		return data;
	}

	public void setData(List<FinanziamentiEntry> data) {
		this.data = data;
	}
	

}
