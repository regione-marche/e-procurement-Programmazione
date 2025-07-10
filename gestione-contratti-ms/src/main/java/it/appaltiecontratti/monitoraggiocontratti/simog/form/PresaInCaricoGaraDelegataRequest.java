package it.appaltiecontratti.monitoraggiocontratti.simog.form;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.PresaInCaricoGaraDelegataForm;

public class PresaInCaricoGaraDelegataRequest extends BaseConsultaAnacRequest {

	private PresaInCaricoGaraDelegataForm data;

	public PresaInCaricoGaraDelegataForm getData() {
		return data;
	}

	public void setData(PresaInCaricoGaraDelegataForm data) {
		this.data = data;
	}

}
