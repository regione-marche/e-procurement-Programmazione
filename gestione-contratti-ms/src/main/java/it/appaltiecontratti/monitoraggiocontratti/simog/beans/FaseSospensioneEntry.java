package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.util.Date;

public class FaseSospensioneEntry {

	private Long codGara;
	private Long codLotto;
	private Long num;
	private Date dataVerbSosp;	
	private Date dataVerbRipr; 
	private Long motivoSosp;
	private String flagSuperoTempo;
	private String flagRiserve;
	private String flagVerbale;
	
	public Long getCodGara() {
		return codGara;
	}
	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}
	public Long getCodLotto() {
		return codLotto;
	}
	public void setCodLotto(Long codLotto) {
		this.codLotto = codLotto;
	}
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
	
}
