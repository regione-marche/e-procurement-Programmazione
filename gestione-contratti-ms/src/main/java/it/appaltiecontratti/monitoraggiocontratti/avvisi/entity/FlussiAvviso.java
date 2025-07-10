package it.appaltiecontratti.monitoraggiocontratti.avvisi.entity;

import java.util.Date;

public class FlussiAvviso {

	private Long numeroAvviso;
	private String autore;
	private String noteInvio;
	private Date dataInvio;
	private String stazioneAppaltante;
	private String xml;

	public Long getNumeroAvviso() {
		return numeroAvviso;
	}
	public void setNumeroAvviso(Long numeroAvviso) {
		this.numeroAvviso = numeroAvviso;
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
	public String getStazioneAppaltante() {
		return stazioneAppaltante;
	}
	public void setStazioneAppaltante(String stazioneAppaltante) {
		this.stazioneAppaltante = stazioneAppaltante;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
}
