package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi;

import java.util.Date;

public class FaseAccordoBonarioPubbEntry extends BaseFasePubblicazioneEntry {

	private Long num;
	private Date dataAccordo;
	private Double oneriDerivanti;
	private Long numRiserve;
	private Long numAppa;

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public Date getDataAccordo() {
		return dataAccordo;
	}

	public void setDataAccordo(Date dataAccordo) {
		this.dataAccordo = dataAccordo;
	}

	public Double getOneriDerivanti() {
		return oneriDerivanti;
	}

	public void setOneriDerivanti(Double oneriDerivanti) {
		this.oneriDerivanti = oneriDerivanti;
	}

	public Long getNumRiserve() {
		return numRiserve;
	}

	public void setNumRiserve(Long numRiserve) {
		this.numRiserve = numRiserve;
	}

	public Long getNumAppa() {
		return numAppa;
	}

	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}

}
