package it.appaltiecontratti.inbox.entity.contratti;

import java.util.Date;

public class GaraFullEntry extends GaraEntry{

	private Date dataScadenza;
	private Long syscon;
	private String altreSA;

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public Long getSyscon() {
		return syscon;
	}

	public void setSyscon(Long syscon) {
		this.syscon = syscon;
	}

	public String getAltreSA() {
		return altreSA;
	}

	public void setAltreSA(String altreSA) {
		this.altreSA = altreSA;
	}
	
	
}
