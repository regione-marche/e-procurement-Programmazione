package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses;

import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.TabellatoEntry;

public class ResponseInizInsertLotto extends BaseResponse{
	
	private static final long serialVersionUID = 1L;
	
	private List<TabellatoEntry>  modalitaAcquisizione;
	private List<TabellatoEntry>  modalitaTipologiaLavoro;
	private List<TabellatoEntry>  condizioniRicorsoProcNeg;
	public List<TabellatoEntry> getModalitaAcquisizione() {
		return modalitaAcquisizione;
	}
	public void setModalitaAcquisizione(List<TabellatoEntry> modalitaAcquisizione) {
		this.modalitaAcquisizione = modalitaAcquisizione;
	}
	public List<TabellatoEntry> getModalitaTipologiaLavoro() {
		return modalitaTipologiaLavoro;
	}
	public void setModalitaTipologiaLavoro(List<TabellatoEntry> modalitaTipologiaLavoro) {
		this.modalitaTipologiaLavoro = modalitaTipologiaLavoro;
	}
	public List<TabellatoEntry> getCondizioniRicorsoProcNeg() {
		return condizioniRicorsoProcNeg;
	}
	public void setCondizioniRicorsoProcNeg(List<TabellatoEntry> condizioniRicorsoProcNeg) {
		this.condizioniRicorsoProcNeg = condizioniRicorsoProcNeg;
	}
}
