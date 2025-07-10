package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi;

import java.util.Date;
import java.util.List;

public class FaseInizialeSemplificataPubbEntry {

	private Long codGara;
	private Long codLotto;
	private Long num;
	private Date dataStipula;
	private String flagRiserva;
	private Date dataVerbale;
	private Date dataTermine;
	SchedaSicurezzaPubbEntry schedaSicurezza;
	List<IncarichiProfPubbEntry> incarichiProfessionali;
	MisureAggiuntiveSicurezzaPubbEntry misureaggsicurezza;
	private Long numAppa;

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
	public SchedaSicurezzaPubbEntry getSchedaSicurezza() {
		return schedaSicurezza;
	}
	public void setSchedaSicurezza(SchedaSicurezzaPubbEntry schedaSicurezza) {
		this.schedaSicurezza = schedaSicurezza;
	}
	public List<IncarichiProfPubbEntry> getIncarichiProfessionali() {
		return incarichiProfessionali;
	}
	public void setIncarichiProfessionali(List<IncarichiProfPubbEntry> incarichiProfessionali) {
		this.incarichiProfessionali = incarichiProfessionali;
	}
	public MisureAggiuntiveSicurezzaPubbEntry getMisureaggsicurezza() {
		return misureaggsicurezza;
	}
	public void setMisureaggsicurezza(MisureAggiuntiveSicurezzaPubbEntry misureaggsicurezza) {
		this.misureaggsicurezza = misureaggsicurezza;
	}
	public Long getNumAppa() {
		return numAppa;
	}
	public void setNumAppa(Long numAppa) {
		this.numAppa = numAppa;
	}

}
