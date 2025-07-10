package it.appaltiecontratti.monitoraggiocontratti.simog.responses;

import java.io.Serializable;
import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;

public class ResponseConsultaNumeroGaraCig extends BaseResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<String> data;

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

}
