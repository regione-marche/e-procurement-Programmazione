package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.List;

public class AccorpamentoEntry {
	
	private String cigMaster;
	
	private List<String> cigAccorpati;

	public String getCigMaster() {
		return cigMaster;
	}

	public void setCigMaster(String cigMaster) {
		this.cigMaster = cigMaster;
	}

	public List<String> getCigAccorpati() {
		return cigAccorpati;
	}

	public void setCigAccorpati(List<String> cigAccorpati) {
		this.cigAccorpati = cigAccorpati;
	}
	
	

}
