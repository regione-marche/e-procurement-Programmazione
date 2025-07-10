package it.appaltiecontratti.inbox.entity.loaderappalto;

import java.util.Date;

public class DatiStipulaEntry {

	private Date dataStipula;
	private Date dataDecorrenza;
	private Date dataScadenza;
	private String idSchedaLocale;
	private String idSchedaSimog;

	public Date getDataStipula() {
		return dataStipula;
	}

	public void setDataStipula(Date dataStipula) {
		this.dataStipula = dataStipula;
	}

	public Date getDataDecorrenza() {
		return dataDecorrenza;
	}

	public void setDataDecorrenza(Date dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
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

}
