package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.InizFaseAdesioneAccordoQuadro;

public class ResponseInizFaseAdesioneAccordoQuadro extends BaseResponse{
	
	
	public static final String INIZ_ADESIONE_GARA_NOT_FOUND = "INIZ_ADESIONE_GARA_NOT_FOUND";
	
	public static final String INIZ_ADESIONE_AGGIUDICAZIONE_NOT_FOUND = "INIZ_ADESIONE_AGGIUDICAZIONE_NOT_FOUND";
	
	public static final String INIZ_ADESIONE_ERRORE_SIMOG_004 = "SIMOG_RIC_004"; 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private InizFaseAdesioneAccordoQuadro data;

	public InizFaseAdesioneAccordoQuadro getData() {
		return data;
	}

	public void setData(InizFaseAdesioneAccordoQuadro data) {
		this.data = data;
	}
	

}
