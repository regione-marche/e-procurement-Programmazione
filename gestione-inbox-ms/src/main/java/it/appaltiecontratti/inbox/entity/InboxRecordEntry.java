package it.appaltiecontratti.inbox.entity;

import java.util.Date;

public class InboxRecordEntry {

	private Long idComunicazione;
	private String xml;
	private String idEGov;
	private String msg;
	private Date dataRicezione;
	private Long stato;

	public Long getIdComunicazione() {
		return idComunicazione;
	}

	public void setIdComunicazione(Long idComunicazione) {
		this.idComunicazione = idComunicazione;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getIdEGov() {
		return idEGov;
	}

	public void setIdEGov(String idEGov) {
		this.idEGov = idEGov;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getDataRicezione() {
		return dataRicezione;
	}

	public void setDataRicezione(Date dataRicezione) {
		this.dataRicezione = dataRicezione;
	}

	public Long getStato() {
		return stato;
	}

	public void setStato(Long stato) {
		this.stato = stato;
	}

}
