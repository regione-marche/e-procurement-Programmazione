package it.appaltiecontratti.tabellati.entity;

import java.util.Date;

public class MessageEntry {
	
	private long id;
	private Date dataMessaggio;
	private String oggetto;
	private String corpoMessaggio;
	private Long sysconSender;

	private Long sysconRecipient;
	private String infoSender;
	private Long letto;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDataMessaggio() {
		return dataMessaggio;
	}
	public void setDataMessaggio(Date dataMessaggio) {
		this.dataMessaggio = dataMessaggio;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getCorpoMessaggio() {
		return corpoMessaggio;
	}
	public void setCorpoMessaggio(String corpoMessaggio) {
		this.corpoMessaggio = corpoMessaggio;
	}
	public Long getSysconSender() {
		return sysconSender;
	}
	public void setSysconSender(Long sysconSender) {
		this.sysconSender = sysconSender;
	}

	public Long getSysconRecipient() {
		return sysconRecipient;
	}

	public void setSysconRecipient(Long sysconRecipient) {
		this.sysconRecipient = sysconRecipient;
	}

	public String getInfoSender() {
		return infoSender;
	}
	public void setInfoSender(String infoSender) {
		this.infoSender = infoSender;
	}
	public Long getLetto() {
		return letto;
	}
	public void setLetto(Long letto) {
		this.letto = letto;
	}
	
	
}
