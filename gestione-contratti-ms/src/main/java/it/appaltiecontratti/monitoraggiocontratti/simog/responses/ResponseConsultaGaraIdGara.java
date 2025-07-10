package it.appaltiecontratti.monitoraggiocontratti.simog.responses;

import java.io.Serializable;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.SchedaType;


public class ResponseConsultaGaraIdGara extends BaseResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private SchedaType data;

	public SchedaType getData() {
		return data;
	}

	public void setData(SchedaType data) {
		this.data = data;
	}
}