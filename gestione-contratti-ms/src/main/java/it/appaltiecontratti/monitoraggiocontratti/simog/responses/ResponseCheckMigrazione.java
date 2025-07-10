package it.appaltiecontratti.monitoraggiocontratti.simog.responses;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;

public class ResponseCheckMigrazione extends BaseResponse {

	private static final long serialVersionUID = 1L;
	
	public static final String GARA_NON_MIGRATA = "GARA-NON-MIGRATA";
	
	public static final String SA_NON_ESISTENTE = "SA-NON-ESISTENTE";
	
	public static final String SA_NON_ABILITATA = "SA-NON-ABILITATA";
	
	public static final String MIGRAZIONE_GARA_NO_LOTTI = "MIGRAZIONE-GARA-NO-LOTTI";

	private CheckMigrazioneGaraEntry data;

	public CheckMigrazioneGaraEntry getData() {
		return data;
	}

	public void setData(CheckMigrazioneGaraEntry data) {
		this.data = data;
	}
	
}
