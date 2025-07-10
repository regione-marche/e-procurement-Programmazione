package it.appaltiecontratti.programmi.entity;

import java.util.Date;

public class FlussiProgrammi {

	private Long contri;
	private String autore;
	private String noteInvio;
	private Date dataInvio;
	private Long idFlusso;
	private String xml;
	
	public Long getContri() {
		return contri;
	}
	public void setContri(Long contri) {
		this.contri = contri;
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

	public Long getIdFlusso() {
		return idFlusso;
	}

	public void setIdFlusso(Long idFlusso) {
		this.idFlusso = idFlusso;
	}
}
