package it.appaltiecontratti.monitoraggiocontratti.simog.form;

import it.appaltiecontratti.monitoraggiocontratti.simog.beans.GetElencoFeedback;

public class ElencoFeedbackRequest extends BaseConsultaAnacRequest {

	private GetElencoFeedback data;

	public GetElencoFeedback getData() {
		return data;
	}

	public void setData(GetElencoFeedback data) {
		this.data = data;
	}

}
