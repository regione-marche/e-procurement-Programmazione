package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

public class FlussiFase {

	private Long codGara;
	private Long codLotto;
	private Long numFase;
	private Long num;
	private String autore;
	private String noteInvio;
	private Date dataInvio;
	private String xml;
	private String idSchedaLocale;
	
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
	public Long getNumFase() {
		return numFase;
	}
	public void setNumFase(Long numFase) {
		this.numFase = numFase;
	}
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	public String getAutore() {
		return autore;
	}
	public void setAutore(String autore) {
		this.autore = autore;
	}
	public String getNoteInvio() {
		return noteInvio;
	}
	public void setNoteInvio(String noteInvio) {
		this.noteInvio = noteInvio;
	}
	public Date getDataInvio() {
		return dataInvio;
	}
	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public String getIdSchedaLocale() { return idSchedaLocale; }
	public void setIdSchedaLocale(String idSchedaLocale) { this.idSchedaLocale = idSchedaLocale; }
	
}
