package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.List;

public class FasiInfo {

	
	private List<FaseEntry> listaFasi;

	private List<Long> listaStoricoFasi;
	
	private boolean current;
	
	private boolean riaggiudicabile;

	public List<FaseEntry> getListaFasi() {
		return listaFasi;
	}

	public void setListaFasi(List<FaseEntry> listaFasi) {
		this.listaFasi = listaFasi;
	}

	public List<Long> getListaStoricoFasi() {
		return listaStoricoFasi;
	}

	public void setListaStoricoFasi(List<Long> listaStoricoFasi) {
		this.listaStoricoFasi = listaStoricoFasi;
	}

	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}

	public boolean isRiaggiudicabile() {
		return riaggiudicabile;
	}

	public void setRiaggiudicabile(boolean riaggiudicabile) {
		this.riaggiudicabile = riaggiudicabile;
	}
	

	
}
