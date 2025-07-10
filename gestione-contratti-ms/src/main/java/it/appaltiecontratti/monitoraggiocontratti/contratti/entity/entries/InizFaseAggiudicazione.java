package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;
import java.util.List;

public class InizFaseAggiudicazione {
	
	private Double percRibassoAgg;
	private Date dataVerbaleAgg;
	private Double importoAgg;
	private String inibita;
	
	
	public Double getPercRibassoAgg() {
		return percRibassoAgg;
	}
	public void setPercRibassoAgg(Double percRibassoAgg) {
		this.percRibassoAgg = percRibassoAgg;
	}
	public Date getDataVerbaleAgg() {
		return dataVerbaleAgg;
	}
	public void setDataVerbaleAgg(Date dataVerbaleAgg) {
		this.dataVerbaleAgg = dataVerbaleAgg;
	}
	public Double getImportoAgg() {
		return importoAgg;
	}
	public void setImportoAgg(Double importoAgg) {
		this.importoAgg = importoAgg;
	}
	public String getInibita() {
		return inibita;
	}
	public void setInibita(String inibita) {
		this.inibita = inibita;
	}
	
	
}
