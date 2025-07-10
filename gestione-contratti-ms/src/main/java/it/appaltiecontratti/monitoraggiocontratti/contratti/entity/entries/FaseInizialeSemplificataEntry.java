package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

public class FaseInizialeSemplificataEntry {

	private Long codGara;
	private Long codLotto;
	private Long num;
	private Date dataStipula;
	private String flagRiserva;
	private Date dataVerbale;
	private Date dataTermine;
	private boolean pubblicata;
	
	public boolean isPubblicata() {
		return pubblicata;
	}
	public void setPubblicata(boolean pubblicata) {
		this.pubblicata = pubblicata;
	}
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
	public Date getDataStipula() {
		return dataStipula;
	}
	public void setDataStipula(Date dataStipula) {
		this.dataStipula = dataStipula;
	}
	public String getFlagRiserva() {
		return flagRiserva;
	}
	public void setFlagRiserva(String flagRiserva) {
		this.flagRiserva = flagRiserva;
	}
	public Date getDataVerbale() {
		return dataVerbale;
	}
	public void setDataVerbale(Date dataVerbale) {
		this.dataVerbale = dataVerbale;
	}
	public Date getDataTermine() {
		return dataTermine;
	}
	public void setDataTermine(Date dataTermine) {
		this.dataTermine = dataTermine;
	}
	
}
