package it.appaltiecontratti.inbox.entity.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import it.appaltiecontratti.inbox.entity.FeedbackEntry;

public class ResponseDettaglioFeedback extends BaseResponse{

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value="Dati base dei feedback")
	private FeedbackEntry data;

	public FeedbackEntry getData() {
		return data;
	}

	public void setData(FeedbackEntry data) {
		this.data = data;
	}
	
	
}
