package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.CredenzialiSimog;

public class ResponseInizImportSmartcig  extends BaseResponse{
	
	
	private static final long serialVersionUID = 1L;
	
	private CredenzialiSimog data;

	public CredenzialiSimog getData() {
		return data;
	}

	public void setData(CredenzialiSimog data) {
		this.data = data;
	}

}
