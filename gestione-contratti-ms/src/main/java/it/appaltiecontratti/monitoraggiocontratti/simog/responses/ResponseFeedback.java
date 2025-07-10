package it.appaltiecontratti.monitoraggiocontratti.simog.responses;

import java.io.Serializable;
import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.ResponseElencoFeedback;

public class ResponseFeedback extends BaseResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private ResponseElencoFeedback data;

	public ResponseElencoFeedback getData() {
		return data;
	}

	public void setData(ResponseElencoFeedback data) {
		this.data = data;
	}
}