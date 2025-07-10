package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi;

import java.util.Date;

public class FaseSospensionePubbEntry extends BaseFasePubblicazioneEntry {

	private Long num;
	private Date dataVerbSosp;
	private Date dataVerbRipr;
	private Long motivoSosp;
	private String flagSuperoTempo;
	private String flagRiserve;
	private String flagVerbale;
	private Long numAppa;

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

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

	public Long getMotivoSosp() {
		return motivoSosp;
	}

	public void setMotivoSosp(Long motivoSosp) {
		this.motivoSosp = motivoSosp;
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

	public Long getNumAppa() {
		return numAppa;
	}

	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}

}
