package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.io.Serializable;
import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.simog.beans.Collaborazione;

public class ResponseListaCollaborazioni extends BaseResponse implements Serializable {

	private static final long serialVersionUID = 1884683909896115311L;
	
	private List<Collaborazione> data;

	public List<Collaborazione> getData() {
		return data;
	}

	public void setData(List<Collaborazione> data) {
		this.data = data;
	}

}
