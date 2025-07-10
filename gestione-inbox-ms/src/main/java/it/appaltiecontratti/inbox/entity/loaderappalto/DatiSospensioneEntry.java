package it.appaltiecontratti.inbox.entity.loaderappalto;

import java.util.Date;

public class DatiSospensioneEntry {
	private Date dataVerbSosp;
	private Date dataVerbRipr;
	private Long idMotivoSosp;
	private String flagSuperoTempo;
	private String flagRiserve;
	private String flagVerbale;
	private String idSchedaLocale;
	private String idSchedaSimog;
	private Long numSosp;

	public Date getDataVerbSosp() {
		return dataVerbSosp;
	}

	public void setDataVerbSosp(Date dataVerbSosp) {
		this.dataVerbSosp = dataVerbSosp;
	}

	public Date getDataVerbRipr() {
		return dataVerbRipr;
	}

	public void setDataVerbRipr(Date dataVerbRipr) {
		this.dataVerbRipr = dataVerbRipr;
	}

	public Long getIdMotivoSosp() {
		return idMotivoSosp;
	}

	public void setIdMotivoSosp(Long idMotivoSosp) {
		this.idMotivoSosp = idMotivoSosp;
	}

	public String getFlagSuperoTempo() {
		return flagSuperoTempo;
	}

	public void setFlagSuperoTempo(String flagSuperoTempo) {
		this.flagSuperoTempo = flagSuperoTempo;
	}

	public String getFlagRiserve() {
		return flagRiserve;
	}

	public void setFlagRiserve(String flagRiserve) {
		this.flagRiserve = flagRiserve;
	}

	public String getFlagVerbale() {
		return flagVerbale;
	}

	public void setFlagVerbale(String flagVerbale) {
		this.flagVerbale = flagVerbale;
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

	public Long getNumSosp() {
		return numSosp;
	}

	public void setNumSosp(Long numSosp) {
		this.numSosp = numSosp;
	}

}
