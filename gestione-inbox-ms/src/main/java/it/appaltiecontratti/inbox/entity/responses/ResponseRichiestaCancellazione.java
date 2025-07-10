package it.appaltiecontratti.inbox.entity.responses;

import java.io.Serializable;

public class ResponseRichiestaCancellazione extends BaseResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Boolean cancellaSchedePrecedenti;

	public Boolean getCancellaSchedePrecedenti() {
		return cancellaSchedePrecedenti;
	}

	public void setCancellaSchedePrecedenti(Boolean cancellaSchedePrecedenti) {
		this.cancellaSchedePrecedenti = cancellaSchedePrecedenti;
	}
	
	

}
