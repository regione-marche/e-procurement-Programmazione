package it.appaltiecontratti.monitoraggiocontratti.simog.responses;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.ConsultaGaraEntry;

public class ResponseImportaGara extends BaseResponse{

	private static final long serialVersionUID = -8616663721395876079L;
	
	ConsultaGaraEntry data;

	public ConsultaGaraEntry getData() {
		return data;
	}

	public void setData(ConsultaGaraEntry data) {
		this.data = data;
	}
	
}
