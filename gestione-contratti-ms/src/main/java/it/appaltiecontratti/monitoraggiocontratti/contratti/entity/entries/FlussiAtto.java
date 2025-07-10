package it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries;

import java.util.Date;

public class FlussiAtto {

	private Long idFlusso;
	private Long codGara;
	private Long numAtto;
	private String autore;
	private String noteInvio;
	private Date dataInvio;
	private String xml;
	
	public Long getIdFlusso() {
		return idFlusso;
	}
	public void setIdFlusso(Long idFlusso) {
		this.idFlusso = idFlusso;
	}
	public Long getCodGara() {
		return codGara;
	}
	public void setCodGara(Long codGara) {
		this.codGara = codGara;
	}
	public Long getNumAtto() {
		return numAtto;
	}
	public void setNumAtto(Long numAtto) {
		this.numAtto = numAtto;
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
	
	
	
	
}
