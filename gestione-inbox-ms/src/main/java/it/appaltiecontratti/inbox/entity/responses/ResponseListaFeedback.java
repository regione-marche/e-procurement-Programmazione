package it.appaltiecontratti.inbox.entity.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.inbox.entity.FeedbackListaEntry;

public class ResponseListaFeedback extends BaseResponse{

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati base dei feedback")
	private List<FeedbackListaEntry> data;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Numero totale dei feedback estraibili dalla form di ricerca")
	private int totalCount;

	public List<FeedbackListaEntry> getData() {
		return data;
	}

	public void setData(List<FeedbackListaEntry> data) {
		this.data = data;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	
}
