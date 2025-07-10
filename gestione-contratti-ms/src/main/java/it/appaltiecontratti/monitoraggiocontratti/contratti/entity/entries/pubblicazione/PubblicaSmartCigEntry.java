package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione;

import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.RecordFaseImpreseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.SmartCigEntry;

public class PubblicaSmartCigEntry {

	private SmartCigEntry smartcig;
	
	private List<RecordFaseImpreseEntry> imprese;

	public SmartCigEntry getSmartcig() {
		return smartcig;
	}

	public void setSmartcig(SmartCigEntry smartcig) {
		this.smartcig = smartcig;
	}

	public List<RecordFaseImpreseEntry> getImprese() {
		return imprese;
	}

	public void setImprese(List<RecordFaseImpreseEntry> imprese) {
		this.imprese = imprese;
	}
	
	
}
