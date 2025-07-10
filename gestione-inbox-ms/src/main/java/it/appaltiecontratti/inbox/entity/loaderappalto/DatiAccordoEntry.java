package it.appaltiecontratti.inbox.entity.loaderappalto;

import java.util.Date;

public class DatiAccordoEntry {

	private Date dataAccordo;
	private Double oneriDerivanti;
	private Long numRiserve;
	private String idSchedaLocale;
	private String idSchedaSimog;
	private Long numAcco;

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

	public String getIdSchedaLocale() {
		return idSchedaLocale;
	}

	public void setIdSchedaLocale(String idSchedaLocale) {
		this.idSchedaLocale = idSchedaLocale;
	}

	public String getIdSchedaSimog() {
		return idSchedaSimog;
	}

	public void setIdSchedaSimog(String idSchedaSimog) {
		this.idSchedaSimog = idSchedaSimog;
	}

	public Long getNumAcco() {
		return numAcco;
	}

	public void setNumAcco(Long numAcco) {
		this.numAcco = numAcco;
	}

}
